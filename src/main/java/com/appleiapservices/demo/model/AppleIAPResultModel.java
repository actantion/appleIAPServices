package com.appleiapservices.demo.model;

import lombok.Data;

@Data
public class AppleIAPResultModel {
    public ReceiptModel receipt;
    public Integer status;
    public String environment;
}
