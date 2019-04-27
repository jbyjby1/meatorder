package com.htzg.meatorder.domain;

public enum OrderStatus {

    CREATED("已创建"),
    COMMITED("已提交到接口人"),
    APPLYED_REIMBURSEMENT("已提交报销"),
    GET_REIMBURSEMENT("报销完毕"),
    SETTLED("已结算");

    private String message;

    OrderStatus(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
