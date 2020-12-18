package com.appleiapservices.demo;

import com.appleiapservices.demo.natives.INativeXXX;
import com.appleiapservices.demo.util.JsonUtil;
import com.appleiapservices.demo.util.LoadTenonLib;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.appleiapservices.demo.model.AppleIAPResultModel;
@RestController
public class appleIAPController{

    @RequestMapping("/appleIAPAuth")
    public boolean appleIAPAuth(String transactionID,String receipt){
        System.out.println("transactionID："+transactionID+"receipt："+receipt);
        System.out.println(INativeXXX.INSTANCE.getRandom());

        try {
            String verifyResult =  IosVerifyUtil.buyAppVerify(receipt,0);
            if (verifyResult != null) {
                System.out.println("线上，苹果平台返回JSON:"+verifyResult);

                AppleIAPResultModel obj = JsonUtil.decodingJSON(verifyResult, AppleIAPResultModel.class);
                if (obj.status == 0){
                    System.out.println("验证成功");

                    return true;
                }else{
                    System.out.println("验证失败");
                }
            } else {  	    														// 苹果验证有返回结果
                System.out.println("无订单信息!");
            }
            return false;
        } catch (Exception ex) {
            System.out.println("Exception");
            return false;
        }
    }

    @RequestMapping("/getOrderInfo")
    public String getOrderInfo(String productID,String userToken){
        System.out.println("productID："+productID+" userToken："+userToken);
        System.out.println(INativeXXX.INSTANCE.getRandom());

        try {
            return "1234567890gg";
        } catch (Exception ex) {
            System.out.println("Exception");
        }
    }
    public float getBalance() {
        return 0;
    }
}
