package com.fancv.login;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class BaseUserDTO implements Serializable {

    @NotNull(message = "用户姓名不能为空")
    String userNmae;

    String userPermission;

    Integer id;

    List<String> permissions;

    @Valid
    EnterDTO enterDTO;

    public void addPeemission(String p) {
        this.permissions.add(p);
    }

}
