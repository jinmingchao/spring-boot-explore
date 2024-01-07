package explore.springboot.documentation.controller;

import com.alibaba.fastjson2.JSONObject;
import explore.springboot.documentation.service.DocumentationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/documentation")
public class DocumentationController {

    @Autowired
    DocumentationService documentationService;

    @PostMapping("/upload")
    @ResponseBody
    public Object uploadDoc(MultipartHttpServletRequest request) {

        try {
            JSONObject res = new JSONObject();
            res.put("msg", "ok");
            res.put("code", 200);

            String uid = request.getParameter("uid");
            String pid = request.getParameter("pid");
            MultipartFile doc = request.getFile("doc");

            if (null == uid) {
                res.put("msg", "uid cannot be null.");
                return res;
            }
            if (null == doc) {
                res.put("msg", "file cannot be null.");
                return res;
            }

            Long pid_long = -1L;
            try {
                pid_long = null == pid ? -1L : Long.parseLong(pid);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                res.put("msg", "pid format error.");
            }

            return documentationService.uploadDoc(doc, uid, pid_long, res);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            JSONObject res = new JSONObject();
            res.put("msg", "error");
            res.put("code", 500);
            return res;
        }
    }

    @PostMapping("/download/{id}")
    public void downloadDoc(@PathVariable Long id, HttpServletResponse response) {
        try {
            JSONObject res = new JSONObject();
            res.put("msg", "ok");
            res.put("code", 200);
            documentationService.downloadDoc(id, res, response);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            JSONObject res = new JSONObject();
            res.put("msg", "internal error");
            res.put("code", 500);
//            return res;
        }
    }

    @PostMapping("/recover/{id}")
    public void recoverDoc(@PathVariable Long id, HttpServletResponse response) {
        try {
            JSONObject res = new JSONObject();
            res.put("msg", "ok");
            res.put("code", 200);
            documentationService.recoverDoc(id, res, response);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            JSONObject res = new JSONObject();
            res.put("msg", "internal error");
            res.put("code", 500);
//            return res;
        }
    }

//    public Object recoverDocToSpecificVersion(){
//
//    }

}
