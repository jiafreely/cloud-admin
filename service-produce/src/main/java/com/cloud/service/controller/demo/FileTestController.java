package com.cloud.service.controller.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: FileTestController
 * @description: 获取文件路径controller
 * @date 2021/12/24 17:23
 */
@RestController
@RequestMapping("/file/test")
public class FileTestController {
    @GetMapping("fileTest")
    public void fileTest(HttpServletRequest request) {
        //linux下
        String realPath = request.getSession().getServletContext().getRealPath("");
        //获取项目路径
        String realPath1 = request.getSession().getServletContext().getContextPath();
        System.out.println(realPath);
        System.out.println(realPath1);
        //String imgPath = "." + File.separator + "temp_face_img" + File.separator + fileName + ".jpg";
        String property = System.getProperty("user.dir");
        String rulepath = property + File.separator + "flow-rule.json";
        System.out.println(rulepath);
    }
}
