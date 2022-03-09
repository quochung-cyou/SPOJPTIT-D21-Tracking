package com.quochung.spojproptit_d21.Model;

public class Member {

    private String avatarurl;
    private String username;
    private int problemamount;
    private String xephang;
    private String realname;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Member(String avatarurl, String username, int problemamount, String realname) {
        this.avatarurl = avatarurl;
        this.username = username;
        this.problemamount = problemamount;
        this.realname = realname;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public String getXephang() {
        return xephang;
    }

    public void setXephang(String xephang) {
        this.xephang = xephang;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public int getProblemamount() {
        return problemamount;
    }

    public void setProblemamount(int problemamount) {
        this.problemamount = problemamount;
    }


}
