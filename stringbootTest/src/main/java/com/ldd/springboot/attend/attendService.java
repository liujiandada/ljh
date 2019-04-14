package com.ldd.springboot.attend;

import com.ldd.springboot.util.DateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class attendService {

    static Workbook AttendExcel(String xlsPath) throws IOException {
        FileInputStream fileIn=null;
        Workbook wb = null;
        try {
            fileIn = new FileInputStream(xlsPath);

            wb = WorkbookFactory.create(fileIn);

            XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();

            updateStyle(cellStyle);

            for (Sheet sht:wb) {

                String str = sht.getRow(2).getCell(2).getStringCellValue();
                String[] strs = str.trim().split("~");
                String start = strs[0];
                String end = strs[1];
                Integer sumday = DateUtil.getDifference(DateUtil.getDateStr(start), DateUtil.getDateStr(end));

                CellRangeAddress cra =new CellRangeAddress(0, 2, 31, (sumday+4)); // 起始行, 终止行, 起始列, 终止列
                CellRangeAddress cra01 =new CellRangeAddress(0, 2, 0, 30);
                sht.addMergedRegion(cra);
                // 使用RegionUtil类为合并后的单元格添加边框
                RegionUtil.setBorderRight(BorderStyle.THIN,cra,sht);
                RegionUtil.setBorderRight(BorderStyle.NONE,cra01,sht);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(DateUtil.getDateStr(start));
                CellStyle style;
                for (Row r : sht) {
                    if(r.getRowNum()==3){
                        r.createCell(sumday).setCellValue("迟到");
                        r.createCell(sumday+1).setCellValue("早退");
                        r.createCell(sumday+2).setCellValue("矿工");
                        r.createCell(sumday+3).setCellValue("签到缺卡");
                        r.createCell(sumday+4).setCellValue("签退缺卡");
                        for(int i=0 ;i<5 ;i++){
                            XSSFCell cell= (XSSFCell) r.getCell(sumday+i);
                            cell.setCellStyle(cellStyle);
                            sht.setColumnWidth(sumday+i, 256*5);
                            if(i>2){
                                sht.setColumnWidth(sumday+i, 256*10);
                            }
                        }
                    }
                    if (r.getRowNum() < 4) {
                        continue;
                    }
                    if (r.getRowNum() % 2 == 1) {
                        Map<String, Integer> map = new HashMap<>();
                        map.put("迟到", 0);
                        map.put("早退", 0);
                        map.put("矿工", 0);
                        map.put("正常", 0);
                        map.put("签到缺卡", 0);
                        map.put("签退缺卡", 0);
                        for (int i = 0; i < sumday; i++, calendar.add(Calendar.DAY_OF_YEAR, 1)) {
                            if(i>=sumday-1){
                                calendar.setTime(DateUtil.getDateStr(start));
                                calendar.add(Calendar.DAY_OF_YEAR, -1);
                            }
                            if (!DateUtil.isHoliday(calendar.getTime()) && !DateUtil.isWeekend(calendar.getTime()) ) {
                                    String strTime = r.getCell(i).getStringCellValue();
                                    Cell cell = r.getCell((short) i);
                                    Font font = wb.createFont();
                                    font.setFontHeightInPoints((short) 8);
                                    font.setFontName("Arial");
                                    style = wb.createCellStyle();
                                    updateStyle(style);
                                    style.setWrapText(true);//自动换行
                                    style.setFont(font);
                                    if (!"".equals(strTime.trim()) && strTime.length() >= 5) {
                                        int a = ifAttend(strTime, map);
                                        switch (a) {
                                            case 1:
                                                //迟到
                                                style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
                                                break;
                                            case 2:
                                                //早退
                                                style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
                                                break;
                                            case 3:
                                                //迟到加早退
                                                style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
                                                break;
                                            case 5:
                                                //缺卡
                                                style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                                                break;
                                            default:
                                                break;
                                        }
                                    } else {
                                        int kuanggong = map.get("矿工");
                                        map.put("矿工", ++kuanggong);
                                        style.setFillForegroundColor(IndexedColors.OLIVE_GREEN.getIndex());
                                    }
                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    cell.setCellStyle(style);


                                r.createCell(sumday).setCellValue(map.get("迟到"));
                                r.createCell(sumday + 1).setCellValue(map.get("早退"));
                                r.createCell(sumday + 2).setCellValue(map.get("矿工"));
                                r.createCell(sumday + 3).setCellValue(map.get("签到缺卡"));
                                r.createCell(sumday + 4).setCellValue(map.get("签退缺卡"));

                                for (int j = 0; j < 5; j++) {
                                    XSSFCell cell01 = (XSSFCell) r.getCell(sumday + j);
                                    XSSFCellStyle cellStyle0 = (XSSFCellStyle) wb.createCellStyle();
                                    updateStyle(cellStyle0);
                                    switch (j) {
                                        case 0:
                                            //迟到
                                            cellStyle0.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
                                            break;
                                        case 1:
                                            //早退
                                            cellStyle0.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
                                            break;
                                        case 2:
                                            //矿工
                                            cellStyle0.setFillForegroundColor(IndexedColors.OLIVE_GREEN.getIndex());
                                            break;
                                        case 3:
                                            //缺卡
                                            cellStyle0.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                                            break;
                                        case 4:
                                            //缺卡
                                            cellStyle0.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                                            break;
                                        default:
                                            break;
                                    }
                                    cellStyle0.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    cell01.setCellStyle(cellStyle0);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fileIn!=null){
                fileIn.close();
            }
        }
        return wb;
    }

    /**
     * 考勤计算
     * @param strTime
     * @param map
     * @return
     */
    static int ifAttend(String strTime, Map<String, Integer> map){
        int chidao = map.get("迟到");
        int zaotui = map.get("早退");
        int AMqueka  = map.get("签到缺卡");
        int PMqueka  = map.get("签退缺卡");
        int is=0;
        int a=0;
        boolean bol=true;
        for(int i=0; i<strTime.length();i++){
            if(i>0 && (i+1)%5 == 0){
                String str = strTime.substring(i-4,i+1);
                if(bol && !DateUtil.EqTime(str,"12:00")){
                    is+=1;
                    if(DateUtil.EqTime(str,"08:30")){
                        chidao++;
                        a+=1;
                    }
                    bol=false;
                }else if(i+1>=strTime.length()){
                    is+=2;
                    if(!DateUtil.EqTime(str,"18:00")){
                        zaotui++;
                        a+=2;
                    }
                }
            }
        }
        switch (is){
            case 1:
                PMqueka++;
                a=5;
                break;
            case 2:
                AMqueka++;
                a=5;
                break;
            case 3:
                //不缺卡
                break;
            default:
                break;
        }
        map.put("迟到",chidao);
        map.put("早退",zaotui);
        map.put("签到缺卡",AMqueka);
        map.put("签退缺卡",PMqueka);
        return a;
    }

    /**
     * 增加统一样式
     * @param cellStyle
     */
    static void updateStyle(CellStyle cellStyle){
        cellStyle.setBorderBottom(BorderStyle.THIN); //底部边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//左右居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//上下居中
    }
}
