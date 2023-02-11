package com.luck.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class User implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private Integer role;
    private String salt;
    private List<Role> roles;
}
