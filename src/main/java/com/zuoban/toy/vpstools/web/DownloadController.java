package com.zuoban.toy.vpstools.web;

import com.zuoban.toy.vpstools.consts.URLConsts;
import com.zuoban.toy.vpstools.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Properties;

/**
 * 下载 controller
 * @author wangjinqiang
 */
@Controller
public class DownloadController {
    @Autowired
    private DownloadService downloadService;

    @GetMapping({"/download","/"})
    private String downloadPage(Model model){
        model.addAttribute("now", LocalDateTime.now());
        return "download";
    }

    @GetMapping("/login")
    private String login(){
        return "login";
    }


    @PostMapping("/download")
    private String downlaod(String url){
        try {
            downloadService.download(url);
            return URLConsts.SUCCESS_PAGE;
        } catch (Exception e) {
            return URLConsts.ERROR_PAGE;
        }
    }
    @GetMapping("/properties")
    @ResponseBody
    private Properties getProperties(){
        return System.getProperties();
    }
}
