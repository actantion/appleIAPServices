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
    public appleIAPRespModel appleIAPAuth(String account,String receipt,int type){
        System.out.println("account："+account+"receipt："+receipt);
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
//                    String sha256_key = IosVerifyUtil.createRandomStr(64);
                    String gid = IosVerifyUtil.GenGid(IosVerifyUtil.createRandomStr(1024));
                    int retCode = 0;
                    if (type == 1){
                        // 月会员
                        retCode = IosVerifyUtil.sendPost(account,gid,1);
                    }else if (type == 2){
                        // 季度会员
                        retCode = IosVerifyUtil.sendPost(account,gid,2);
                    }else if (type == 3){
                        // 年会员
                        retCode = IosVerifyUtil.sendPost(account,gid,3);
                    }
                    if (retCode != 200){
                        respModel.status = retCode;
                        respModel.message = "服务器验证失败";
                    }
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
