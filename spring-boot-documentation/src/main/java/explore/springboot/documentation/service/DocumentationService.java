package explore.springboot.documentation.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import explore.springboot.documentation.mdm.entity.Documentation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentationService extends IService<Documentation> {

        Object uploadDoc(MultipartFile doc, String uid, JSONObject result);

        Object downloadDoc(String flag, JSONObject result, HttpServletResponse response);
}
