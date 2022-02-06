package com.fancv.login;



public class UserContext {
    private static final ThreadLocal<BaseUserDTO> user = new ThreadLocal<>();

    private UserContext() {
    }

    public static Integer getUserId() throws Exception {
        BaseUserDTO baseUserDTO = getUser();
        return baseUserDTO.getId();
    }

    public static BaseUserDTO getUser() throws Exception {
        BaseUserDTO baseUser = user.get();
        if (null == baseUser) {
            throw new Exception("登录失效，请重新登录！");
        }
        return baseUser;
    }



    public static void setBaseUser(BaseUserDTO baseUser) {
        user.set(baseUser);
    }

    public static void remove() {
        user.remove();
    }

}