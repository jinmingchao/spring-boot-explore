package explore.springboot.documentation.controller;

import com.alibaba.fastjson2.JSONObject;
import explore.springboot.documentation.service.DocumentationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/documentation")
public class DocumentationController {

    @Autowired
    DocumentationService documentationService;

    @PostMapping("/upload")
    public Object uploadDoc(MultipartFile doc) {
        try {
            JSONObject res = new JSONObject();
            res.put("msg", "ok");
            res.put("code", 200);
            return documentationService.uploadDoc(doc, res);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            JSONObject res = new JSONObject();
            res.put("msg", "error");
            res.put("code", 500);
            return res;
        }
    }


//    public Object downloadDoc(){
//
//    }


//    public Object recoverDocToSpecificVersion(){
//
//    }

}
