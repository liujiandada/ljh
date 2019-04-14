package com.ldd.springboot.entity.attend;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Attend {
    public Attend(){

    }
    public Attend(Integer sumday){
        List<Sign> signList =new ArrayList<>();
        while (sumday>0){
            signList.add(new Sign());
            sumday--;
        }
        this.listSign=signList;
    }

    /**
     * 工号
     */
    Integer jobNum;

    /**
     * 姓名
     */
    String name;

    /**
     * 部门
     */
    String department;


    /**
     * 打卡记录
     */
    List<Sign> listSign;

}

