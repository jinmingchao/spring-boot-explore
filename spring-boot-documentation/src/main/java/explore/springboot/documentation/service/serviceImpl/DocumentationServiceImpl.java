package explore.springboot.documentation.service.serviceImpl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpATTRS;
import explore.springboot.documentation.mapper.DocumentationMapper;
import explore.springboot.documentation.mdm.entity.Documentation;
import explore.springboot.documentation.service.DocumentationService;
import explore.springboot.documentation.utils.JSchConnectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;

@Slf4j
@Service
public class DocumentationServiceImpl extends ServiceImpl<DocumentationMapper, Documentation> implements DocumentationService {

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

        this.sshConnection = new JSchConnectUtils(this.fileSystemHost, this.fileSystemPort, this.fileSystemUserName, this.fileSystemPassWord);
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
                        log.info("目录"+fileSystemWorkDir+"不存在, 尝试创建.");
                        sftp.mkdir(fileSystemWorkDir);
                    }
                }

                sftp.cd(fileSystemWorkDir); //存放文件目录
                InputStream is = doc.getInputStream();
                //上传文件名为: 用户id-时间戳-原始名称, 并且不超过文件系统最大文件长度
                String fileName = uid + "-" + System.currentTimeMillis() + "-" + originalFilename;
                sftp.put(is, originalFilename);
                is.close();

                //入库
                Documentation newDoc = Documentation.builder().path(fileSystemWorkDir).createTime(new Date()).updateTime(new Date()).name(fileName).build();
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
}

