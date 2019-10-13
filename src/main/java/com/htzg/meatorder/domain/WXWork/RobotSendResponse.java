package com.htzg.meatorder.domain.WXWork;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RobotSendResponse {

    @JsonProperty("errcode")
    private int errorCode;

    @JsonProperty("errmsg")
    private String errorMsg;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
