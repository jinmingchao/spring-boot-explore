package explore.springboot.documentation.utils;

import cn.hutool.extra.ssh.JschUtil;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSchConnectUtils {
    private static final Logger log = LoggerFactory.getLogger(JSchConnectUtils.class);
    private String host;
    private Integer port;
    private String username;
    private String password;
    private Session session;
    private final int TIMEOUT = 60000000;
    private final String RUNNING_STATEMENT = "正在执行:\t";


    public JSchConnectUtils(String host, Integer port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public void connect() throws JSchException {
        JSch jsch = new JSch();
        if ((null == this.session) || (!this.session.isConnected()))
            synchronized (JSchConnectUtils.class) {
                if ((null == this.session) || (!this.session.isConnected())) {
                    this.session = jsch.getSession(this.username, this.host, this.port.intValue());
                    this.session.setPassword(this.password);
                    this.session.setTimeout(60000000);

                    Properties config = new Properties();
                    config.put("StrictHostKeyChecking", "no");
                    this.session.setConfig(config);
                    this.session.connect();
                }
            }
    }

    public String execCommand(String command)
            throws Exception {
        log.info(RUNNING_STATEMENT + command);
        connect();
        ChannelExec channelExec = (ChannelExec) this.session.openChannel("exec");
        channelExec.setCommand(command);
        channelExec.setInputStream(null);
        channelExec.setErrStream(System.err);
        channelExec.connect();
        InputStream in = channelExec.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String buf = null;

        while ((buf = reader.readLine()) != null) {
            log.info("执行指令 " + command + "打印: " + buf);
        }
        reader.close();
        channelExec.disconnect();
        return buf;
    }

    public String execCommandForQueryingDeductionFile(String command)
            throws Exception {
        log.info(RUNNING_STATEMENT + command);
        connect();
        ChannelExec channelExec = (ChannelExec) this.session.openChannel("exec");
        channelExec.setCommand(command);
        channelExec.setInputStream(null);
        channelExec.setErrStream(System.err);
        channelExec.connect();
        InputStream in = channelExec.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String buf = null;
        String fileName = null;
        int cnt = 0;

        while ((buf = reader.readLine()) != null) {
            log.debug(buf);
            String[] bfs = buf.split("\\.");
            if ((bfs.length == 2) && ("json".equals(bfs[1]))) {
                fileName = buf;
                cnt++;
            }
        }

        log.debug("execCommandForQueryingDeductionFile cnt: " + cnt);
        if (null == fileName)
            throw new Exception("文件不存在");
        if (cnt < 1)
            throw new Exception("文件行数小于1");
        if (cnt > 1) {
            throw new Exception("文件行数大于1");
        }

        reader.close();
        channelExec.disconnect();

        return fileName;
    }

    public List<String> execCommandForQueryingSynchronizedFile(String command) throws Exception {
        log.info(RUNNING_STATEMENT + command);
        connect();
        ChannelExec channelExec = (ChannelExec) this.session.openChannel("exec");
        channelExec.setCommand(command);
        channelExec.setInputStream(null);
        channelExec.setErrStream(System.err);
        channelExec.connect();
        InputStream in = channelExec.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String buf = null;
        int cnt = 0;
        List jsonFiles = new LinkedList();

        while ((buf = reader.readLine()) != null) {
            log.debug(buf);
            String[] bfs = buf.split("\\.");
            if ((bfs.length == 2) && ("json".equals(bfs[1]))) {
                jsonFiles.add(buf);
                cnt++;
            }
        }

        log.debug("execCommandForQueryingSynchronizedFile cnt: " + cnt);

        reader.close();
        channelExec.disconnect();

        return jsonFiles;
    }

    public String readFile(String absFilePath) {
        log.info("正在读取:\t" + absFilePath);
        try {
            connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }

        ChannelSftp channelSftp = null;
        try {
            channelSftp = (ChannelSftp) this.session.openChannel("sftp");
        } catch (JSchException e) {
            e.printStackTrace();
        }
        try {
            channelSftp.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            channelSftp.get(absFilePath, outputStream);
        } catch (SftpException e) {
            e.printStackTrace();
        }
        String fileInfo = new String(outputStream.toByteArray());

        JschUtil.close(channelSftp);

        return fileInfo;
    }

    public void writeFile(String fileInfo, String absFilePath) {
        log.info("正在写入:\t" + absFilePath);
        try {
            connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }

        ChannelSftp channelSftp = null;
        try {
            channelSftp = (ChannelSftp) this.session.openChannel("sftp");
        } catch (JSchException e) {
            e.printStackTrace();
        }
        try {
            channelSftp.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileInfo.getBytes());
        try {
            channelSftp.put(inputStream, absFilePath);
        } catch (SftpException e) {
            e.printStackTrace();
        }

        JschUtil.close(channelSftp);
    }

    public void closeSession() {
        if (null != this.session) {
            if (this.session.isConnected()) {
                this.session.disconnect();
            }
            JschUtil.close(this.session);
        }
    }

    public Session getSession() {
        return this.session;
    }
}