package explore.springboot.documentation.service.serviceImpl;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jcraft.jsch.*;
import explore.springboot.documentation.mapper.DocumentationMapper;
import explore.springboot.documentation.mdm.entity.Documentation;
import explore.springboot.documentation.service.DocumentationService;
import explore.springboot.documentation.utils.JSchConnectUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;

@Slf4j
@Service
public class DocumentationServiceImpl extends ServiceImpl<DocumentationMapper, Documentation> implements DocumentationService, ApplicationRunner {

    @Value("${fileSystemHost}")
    String fileSystemHost;

    @Value("${fileSystemPort}")
    Integer fileSystemPort;

    @Value("${fileSystemUserName}")
    String fileSystemUserName;

    @Value("${fileSystemPassWord}")
    String fileSystemPassWord;

    @Value("${fileSystemWorkDir:/data/doc}")
    String fileSystemWorkDir;

    @Autowired
    DocumentationMapper documentationMapper;

    private JSchConnectUtils sshConnection;

    @Transactional
    public Object uploadDoc(MultipartFile doc, String uid, JSONObject res) {

        // 获取文件原始名称
        String originalFilename = doc.getOriginalFilename();

        try {
            sshConnection.connect();
            if (null != this.sshConnection.getSession()) {
                Channel channel = sshConnection.getSession().openChannel("sftp");
                channel.connect();
                ChannelSftp sftp = (ChannelSftp) channel;

                try {
                    SftpATTRS s = sftp.lstat(fileSystemWorkDir);
                    if (!s.isDir()) {
                        sftp.mkdir(fileSystemWorkDir);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (e.getMessage().toLowerCase().equals("no such file")) {
                        log.info("目录" + fileSystemWorkDir + "不存在, 尝试创建.");
                        sftp.mkdir(fileSystemWorkDir);
                    }
                }

                sftp.cd(fileSystemWorkDir); //存放文件目录
                InputStream is = doc.getInputStream();
                String uuid = UUID.randomUUID().toString().trim();

                //上传文件名为: 用户id-时间戳-uuid, 并且不超过文件系统最大文件长度
                String flag = uid + "-" + System.currentTimeMillis() + "-" + uuid;
                sftp.put(is, flag);
                is.close();

                //入库
                Documentation newDoc = Documentation.builder().oriName(originalFilename).flag(flag).createTime(new Date()).updateTime(new Date()).build();

                try {
                    documentationMapper.insert(newDoc);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
            } else {
                throw new Exception("SSH Session is null.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return res;
    }

    @Override
    public Object downloadDoc(String flag, JSONObject result, HttpServletResponse response) {

        Documentation doc = documentationMapper.selectById(flag);
        if (null == doc || null == doc.getFlag() || null == doc.getOriName()) {
            result.put("msg", "文件不存在");
            return result;
        }

        try {
            sshConnection.connect();
            if (null != this.sshConnection.getSession()) {
                Channel channel = sshConnection.getSession().openChannel("sftp");
                channel.connect();
                ChannelSftp sftp = (ChannelSftp) channel;
                sftp.cd(fileSystemWorkDir);

                String fileName = new String(doc.getOriName().getBytes(), "ISO-8859-1");
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                response.setContentType("application/octet-stream");

                OutputStream os = response.getOutputStream();

                sftp.get(doc.getFlag(), os);
                os.flush();
                os.close();
            }
            return result;
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.sshConnection = new JSchConnectUtils(this.fileSystemHost, this.fileSystemPort, this.fileSystemUserName, this.fileSystemPassWord);
    }
}

