package com.ldd.springboot.util;

import com.ldd.springboot.entity.attend.Attend;
import com.ldd.springboot.entity.attend.Excel;
import com.ldd.springboot.entity.attend.Sign;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@PropertySource(value = "classpath:upload/upload.properties")
@Component
public class ExcelUtil {

    private static String UPLOAD_FOLDER;

    @Value("${prop.upload-folder}")
    public void setUPLOAD_FOLDER(String port) {
        ExcelUtil.UPLOAD_FOLDER = port;
    }

    public static  List<Excel> importExcel(String xlsPath) throws IOException {
        FileInputStream fileIn=null;
        List<Excel> excelList = new ArrayList<>();
        try {
            fileIn = new FileInputStream(xlsPath);

            Workbook wb = WorkbookFactory.create(fileIn);

            for (Sheet sht:wb) {
                Excel excel =new Excel();
                List<Attend> temp = new ArrayList();
                String str = sht.getRow(2).getCell(2).getStringCellValue();
                String[] strs = str.trim().split("~");
                String start = strs[0];
                String end = strs[1];
                Integer sumday = DateUtil.getDifference(DateUtil.getDateStr(start), DateUtil.getDateStr(end));
                int num = 0;
                Attend attend = new Attend(sumday);
                excel.setName(sht.getSheetName());
                excel.setStartTime(DateUtil.getDateStr(start));
                excel.setEndTime(DateUtil.getDateStr(end));
                excel.setMakeTime(DateUtil.getDateStr(sht.getRow(2).getCell(11).getStringCellValue()));
                excel.setSumday(sumday);
                excel.setAttendList(temp);
                for (Row r : sht) {
                    if (r.getRowNum() < 4) {
                        continue;
                    }
                    num++;
                    if (num > 2) {
                        temp.add(attend);
                        attend = new Attend(sumday);
                        num = 1;
                    }
                    switch (num) {
                        case 1:
                            attend.setJobNum(Integer.parseInt(r.getCell(2).getStringCellValue()));
                            attend.setName(r.getCell(10).getStringCellValue());
                            attend.setDepartment(r.getCell(20).getStringCellValue());
                            break;
                        case 2:
                            List<Sign> signList = attend.getListSign();
                            for (int i = 0; i < signList.size(); i++) {
                                Sign sign = signList.get(i);
                                sign.setDay(i + 1);
                                sign.setSignTime(r.getCell(i).getStringCellValue());
                            }
                            break;
                        default:
                            break;
                    }
                }
                excelList.add(excel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            fileIn.close();
        }
        return excelList;
    }

    public static void exportExcel( List<Excel> excelList) throws IOException {

        Workbook wb = new XSSFWorkbook();
        //字体
        XSSFFont fontStyle= (XSSFFont) wb.createFont();
        //单元格背景
        XSSFCellStyle cellStyle= (XSSFCellStyle) wb.createCellStyle();
        for (Excel excel:excelList){

        Sheet sheet = wb.createSheet(excel.getName());
        //设置背景颜色
            cellStyle.setFillBackgroundColor(HSSFColor.PINK.index);
        //设置单元格属性
            sheet.setDefaultColumnWidth(100*256);
        //设置字体样式
            fontStyle.setFontName("Arial");
        //设置字体高度
            fontStyle.setFontHeightInPoints((short)8);


//----------------------------------------------------head-------------------------------------------
        Row row4 = sheet.createRow(3);

        for(int i=0;i<excel.getSumday();i++){
            row4.createCell(i).setCellValue(i+1);
        }
            row4.createCell(excel.getSumday()).setCellValue("正常打卡（天）");
            row4.createCell(excel.getSumday()+1).setCellValue("迟到（天）");
            row4.createCell(excel.getSumday()+2).setCellValue("早退（天）");
            row4.createCell(excel.getSumday()+3).setCellValue("旷工（天）");


            for (int i=0 , x=0 ;i<excel.getAttendList().size();i++,x+=2) {
                Attend attend = excel.getAttendList().get(i);
                Row row = sheet.createRow(x+4);
                Row rows = sheet.createRow(x+5);
                row.createCell(0).setCellValue("工号：");
                row.createCell(2).setCellValue(attend.getJobNum());
                row.createCell(8).setCellValue("姓名：");
                row.createCell(10).setCellValue(attend.getName());
                row.createCell(18).setCellValue("部门：");
                row.createCell(20).setCellValue(attend.getDepartment());
                for(int j=0;j<attend.getListSign().size();j++){
                    Sign sign=attend.getListSign().get(j);
                    rows.createCell(j).setCellValue(sign.getSignTime());
                }
            }
        }


        String  Property=System.getProperty("user.dir")+ File.separator + UPLOAD_FOLDER + File.separator;
        FileOutputStream output = null;
        try {
            Path path = Paths.get(Property + "考勤情况统计.xlsx");
            //如果没有files文件夹，则创建
            if (!Files.isWritable(path)) {
                Files.createDirectories(Paths.get(Property));
            }
            output = new FileOutputStream(Property + "考勤情况统计.xlsx");
            wb.write(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            output.flush();
            output.close();
        }
    }

}
