package com.ty0207;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Random;

public class Request implements Serializable {
    private Integer num;
    private Long time;

    public Request()
    {
        Random r = new Random();
        this.num = r.nextInt(5) + 1;
        this.time = System.currentTimeMillis();
    }


    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
