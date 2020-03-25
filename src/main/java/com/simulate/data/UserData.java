package com.simulate.data;

import java.io.Serializable;

public class UserData implements Serializable {

    private String userName;
    private String passwd;

    public UserData(String userName, String passwd) {
        this.userName = userName;
        this.passwd = passwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
