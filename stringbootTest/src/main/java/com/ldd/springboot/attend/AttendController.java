package com.ldd.springboot.attend;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value="/api/attend")
@CrossOrigin
@PropertySource(value = "classpath:upload/upload.properties")
public class AttendController{
    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;

    @PostMapping("/singlefile")
    public String singleFileUpload(MultipartFile file) {

        if (Objects.isNull(file) || file.isEmpty()) {
            return "文件为空，请重新选择";
        }
        FileOutputStream output = null;
        try {
            String  Property=System.getProperty("user.dir")+ File.separator + UPLOAD_FOLDER + File.separator;
            byte[] bytes = file.getBytes();
            Path path = Paths.get(Property + file.getOriginalFilename());
            //如果没有files文件夹，则创建
            if (!Files.isWritable(path)) {
                Files.createDirectories(Paths.get(Property));
            }
            //文件写入指定路径
            Files.write(path, bytes);

            Workbook wb= attendService.AttendExcel(path.toString());
            if(wb!=null){
                output = new FileOutputStream(Property + "考勤情况统计.xlsx");
                wb.write(output);
            }

            return "统计成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "后端异常...";
        }finally {
            try {
                if(output!=null){
                    output.flush();
                    output.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}