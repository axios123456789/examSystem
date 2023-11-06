package com.example.examsystem.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.example.examsystem.util.Result;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

//图片的上传与下载
@RestController
public class FileController {
    //上传接口
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException{
        //图片名称
        String originalFilename = file.getOriginalFilename();
        System.out.println("图片名称："+originalFilename);

        //唯一标识 避免图片名字重复而被覆盖
        String uid = IdUtil.simpleUUID();

        //上传到指定文件夹
        String path = "D://learnJava//varyjs//img//"+uid+"_"+originalFilename;
        FileUtil.writeBytes(file.getBytes(),path);
        return Result.success(uid+"_"+originalFilename);
    }

    //下载接口 参数 图片名称
    @RequestMapping("/downLoad/{fileName}")
    public void downLoad(@PathVariable String fileName, HttpServletResponse resp) throws IOException {
        //图片路径
        String path = "D://learnJava//varyjs//img//"+fileName;

        //file对象
        File file = new File(path);

        //判断文件对象是否存在 并且是标准文件
        if (file.exists() && file.isFile()){//文件存在 且是标准文件
            //file文件输入流
            InputStream in = new FileInputStream(file);

            //输出流
            ServletOutputStream outputStream = resp.getOutputStream();

            //定义字节数组
            byte[] bytes = new byte[1024];

            //定义长度
            int len = 0;
            while ((len = in.read(bytes)) != -1){
                //输出
                outputStream.write(bytes,0,len);
            }

            //关闭资源
            outputStream.close();
            in.close();
        }else{//文件不存在 或不是标准文件
            resp.getWriter().write("文件不存在");
            resp.getWriter().close();
        }
    }
}
