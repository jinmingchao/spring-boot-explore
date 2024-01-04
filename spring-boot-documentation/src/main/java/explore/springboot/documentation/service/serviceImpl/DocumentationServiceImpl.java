package explore.springboot.documentation.service.serviceImpl;


import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jcraft.jsch.JSchException;
import explore.springboot.documentation.mapper.DocumentationMapper;
import explore.springboot.documentation.mdm.entity.Documentation;
import explore.springboot.documentation.service.DocumentationService;
import explore.springboot.documentation.utils.JSchConnectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    private JSchConnectUtils sshConnection;

    @Override
    public Object uploadDoc(MultipartFile doc, JSONObject res) {
        //TODO
        synchronized (this) { //锁方法防止重名文件
            // 获取当前时间戳
            String flag = System.currentTimeMillis() + "";
            // 获取文件原始名称
            String originalFilename = doc.getOriginalFilename();

            this.sshConnection = new JSchConnectUtils(this.fileSystemHost, this.fileSystemPort, this.fileSystemUserName, this.fileSystemPassWord);
            try {
                sshConnection.connect();
                if (null != this.sshConnection.getSession()) {
                    String result = this.sshConnection.execCommand("ls /data/doc");
                    if (null != result) {
                        log.info("result: " + result);
                        String content = this.sshConnection.readFile("/data/doc/" + result);
                        log.info("content: " + content);
//                        JSONArray deductionInfoArr = JSONArray.parseArray(deductionInfo);
//                        transformData(deductionInfoArr);
                    } else {
                        throw new Exception("fileName is null.");
                    }
                } else {
                    throw new Exception("SSH Session is null.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getLocalizedMessage());
            }
        }
        return null;
    }
}
