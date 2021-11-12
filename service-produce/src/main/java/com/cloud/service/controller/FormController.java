package com.cloud.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: FormController
 * @description: TODO
 */
@RestController
public class FormController {


    /**
     * 附件下载
     *
     * @param: fileId 附件ID
     * @return: byte[] 文件的byte[]
     * @author: xjh
     */
    @GetMapping("/downloadFile")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, String fileId) {
        //设置Response的编码方式为UTF-8
        response.setCharacterEncoding("UTF-8");
        //向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8,其实设置了本句，也默认设置了Response的编码方式为UTF-8，但是开发中最好两句结合起来使用
        response.setHeader("Content-type","text/html;charset=UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try {
            if (1 == 1) {
                PrintWriter writer = response.getWriter();
                writer.write("有误");
                response.setStatus(500);
                return;
            }


            StringBuffer stringBuffer = new StringBuffer();
            if ("1b14042e".equals(fileId)) {
                stringBuffer.append("E:\\fuli\\福利1.docx");
            } else {
                stringBuffer.append("E:\\fuli\\福利2.docx");
            }
            File file = new File(stringBuffer.toString());

            //文件名
            String pathName = file.getName().substring(0, file.getName().indexOf("."));
            //文件后缀
            String fixName = file.getName().substring(file.getName().indexOf("."), file.getName().length());

            String filename = file.getName();
            String agent = request.getHeader("USER-AGENT");
            if (null != agent && -1 != agent.indexOf("MSIE") || null != agent && -1 != agent.indexOf("Trident")) {// ie
                String name = java.net.URLEncoder.encode(filename, "UTF8");
                filename = name;
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等
                filename = new String(filename.getBytes("UTF-8"), "iso-8859-1");
            }
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            response.addHeader("Content-Length", "" + file.length());
            OutputStream out = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            out.write(buffer);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
 fos = new FileOutputStream("E:\\nhang\\fuli\\" + pathName + fixName);
         int len;
         byte[] buffer = new byte[1024];
         while ((len = fileInputStream.read(buffer)) != -1) {
         fos.write(buffer, 0, len);
         }
         fileInputStream.close();*/
