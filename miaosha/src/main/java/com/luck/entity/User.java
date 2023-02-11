package com.luck.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;
    private String name;
    private String password;

    private Date creationDate;
    private Date lastUpdateDate;
}