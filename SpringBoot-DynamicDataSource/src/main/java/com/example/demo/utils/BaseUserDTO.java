package com.example.demo.utils;

import lombok.Data;

import java.util.List;

@Data
public class BaseUserDTO {

    String userNmae;

    String userPermission;

    Integer id;

    List<String> permissions;

    public void addPeemission(String p) {
        this.permissions.add(p);
    }

}
