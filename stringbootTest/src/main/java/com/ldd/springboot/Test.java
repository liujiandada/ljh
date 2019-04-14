package com.ldd.springboot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ldd.springboot.util.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {


    String path = "";

    static String httpUrl = "http://timor.tech/api/holiday/info/";

    public static void main(String [] args) throws  Exception{

        FileInputStream fileIn = new FileInputStream("");
        Workbook wb = WorkbookFactory.create(fileIn);
        Sheet sht = wb.getSheetAt(0);
        a(sht);

//        b("2019-03-30");

    }

    public static void a(Sheet sht ){
        String[] strs = sht.getRow(2).getCell(2).getStringCellValue().trim().split("~");
        Date date = DateUtil.getDateStr(strs[0]);
        for (int i = 4 ;i<sht.getPhysicalNumberOfRows(); i++) {
            if(i%2 == 0){
                for(int ii = 0 ; ii < sht.getRow(i).getPhysicalNumberOfCells();ii++){
                   String str =  sht.getRow(i).getCell(ii).getStringCellValue();
                    //*************************
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    date = c.getTime();
                    //**************************
                    if(b(date)){//节假日
                        if(!str.equals("")){
                         //加班
                        }
                    }else{
                        if(str.equals("")){
                            //矿工
                        }else{

                        }
                    }

                }
            }else{

            }


        }

    }

    /**
     *判断是否 节假日
     */
    public static boolean b(Date date){
        SimpleDateFormat Hms = new SimpleDateFormat("yyyyMMdd");
        String str  = Hms.format(date);
        String jsonResult = request(httpUrl, str);
        JSONObject json = JSONObject.parseObject(jsonResult);
        if(json.getString("code").equals("0")){
            if(null != json.getString("holiday") || !"".equals(json.getString("holiday") )){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param httpUrl
     * @param httpArg
     * @return
     */
    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl +  httpArg;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
           // connection.setRequestProperty("", "");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
