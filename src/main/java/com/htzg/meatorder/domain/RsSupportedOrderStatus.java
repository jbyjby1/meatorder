package com.htzg.meatorder.domain;

import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.List;

public class RsSupportedOrderStatus {

    private List<SupportOrderStatus> supportedOrderStatusList;

    public List<SupportOrderStatus> getSupportedOrderStatusList() {
        return supportedOrderStatusList;
    }

    public void setSupportedOrderStatusList(List<SupportOrderStatus> supportedOrderStatusList) {
        this.supportedOrderStatusList = supportedOrderStatusList;
    }
}
