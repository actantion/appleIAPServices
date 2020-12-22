package com.appleiapservices.demo;

import com.alibaba.fastjson.JSONObject;
import com.appleiapservices.demo.natives.INativeXXX;
import com.appleiapservices.demo.model.appleIAPRespModel;
import com.appleiapservices.demo.util.LoadTenonLib;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.appleiapservices.demo.model.AppleIAPResultModel;
@RestController
public class appleIAPController{

    @RequestMapping("/appleIAPAuth")
    public appleIAPRespModel appleIAPAuth(String transactionID,String receipt){
        System.out.println("transactionID："+transactionID+"receipt："+receipt);
        appleIAPRespModel respModel = new appleIAPRespModel();
        respModel.status = -1;
        try {
            String verifyResult =  IosVerifyUtil.buyAppVerify(receipt,0);
            if (verifyResult != null) {
                System.out.println("线上，苹果平台返回JSON:"+verifyResult);

                AppleIAPResultModel obj = JSONObject.parseObject(verifyResult, AppleIAPResultModel.class);
                if (obj.status == 0){
                    System.out.println("验证成功");
                    respModel.message = "验证成功";
                    respModel.status = 1;
                    return respModel;
                }else{
                    System.out.println("验证失败");
                    respModel.message = "验证失败";
                }
            } else {  	    														// 苹果验证有返回结果
                System.out.println("无订单信息!");
                respModel.message = "无订单信息!";
            }
            return respModel;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            respModel.message = ex.getMessage();
            return respModel;
        }
    }
    public float getBalance() {
        return 0;
    }
}
