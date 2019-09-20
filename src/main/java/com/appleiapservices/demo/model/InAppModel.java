package com.appleiapservices.demo.model;

import lombok.Data;

@Data
public class InAppModel {
    public String quantity;
    public String product_id;
    public String transaction_id;
    public String original_transaction_id;
    public String purchase_date;
    public String purchase_date_ms;
    public String purchase_date_pst;
    public String original_purchase_date;
    public String original_purchase_date_ms;
    public String original_purchase_date_pst;
    public String is_trial_period;
}
