package com.zhw.ms.commons.notity;

public class AccessToken {

    private int errcode;	//出错返回码，为0表示成功，非0表示调用失败
    private String errmsg;	//返回码提示语
    private String access_token;	//获取到的凭证，最长为512字节
    private int expires_in;	//凭证的有效时间（秒）


    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
