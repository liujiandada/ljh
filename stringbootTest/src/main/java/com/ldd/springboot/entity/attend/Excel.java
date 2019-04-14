package com.ldd.springboot.entity.attend;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class Excel {

    /**
     * 表名
     */
    String name ;

    /**
     * 制表时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    Date makeTime;

    /**
     * 开始时间
     */
    Date startTime;

    /**
     * 结束时间
     */
    Date endTime;

    /**
     * 统计总天数
     */
    Integer sumday;

    /**
     * 考勤记录
     */
    List<Attend> attendList ;
}
