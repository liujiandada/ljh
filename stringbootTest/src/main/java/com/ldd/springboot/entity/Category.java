package com.ldd.springboot.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Category implements Serializable {

    private int id;

    private String name;

}