package com.avst.zk.web.req;

public class LoginParam {

    /**
     * 登陆账号
     */
    private String loginaccount;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户名称
     */
    private String username="测试用户";

    private boolean rememberpassword;//记住密码

    public boolean isRememberpassword() {
        return rememberpassword;
    }

    public void setRememberpassword(boolean rememberpassword) {
        this.rememberpassword = rememberpassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginaccount() {
        return loginaccount;
    }

    public void setLoginaccount(String loginaccount) {
        this.loginaccount = loginaccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
