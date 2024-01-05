package explore.springboot.documentation.controller;

import com.alibaba.fastjson2.JSONObject;
import explore.springboot.documentation.service.DocumentationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/documentation")
public class DocumentationController {

    @Autowired
    DocumentationService documentationService;

    @PostMapping("/upload")
    public Object uploadDoc(MultipartHttpServletRequest request) {

        try {
            JSONObject res = new JSONObject();
            res.put("msg", "ok");
            res.put("code", 200);

            String uid = request.getParameter("uid");
            MultipartFile doc = request.getFile("doc");
            if (null == uid) {
                res.put("msg", "uid cannot be null.");
                return res;
            }
            if (null == doc) {
                res.put("msg", "file cannot be null.");
                return res;
            }
            return documentationService.uploadDoc(doc, uid, res);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            JSONObject res = new JSONObject();
            res.put("msg", "error");
            res.put("code", 500);
            return res;
        }
    }

    @GetMapping("/download/{flag}")
    public Object downloadDoc(@PathVariable String flag, HttpServletResponse response) {

    }


//    public Object recoverDocToSpecificVersion(){
//
//    }

}
