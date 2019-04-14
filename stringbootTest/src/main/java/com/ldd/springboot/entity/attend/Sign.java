package com.ldd.springboot.entity.attend;

import lombok.Data;

@Data
public class Sign {
        /**
         * 签到日期
         */
        Integer day;

        /**
         * 签到时间
         */
        String signTime;

        /**
         * 签到次数
         */
        Integer signNum;
}
