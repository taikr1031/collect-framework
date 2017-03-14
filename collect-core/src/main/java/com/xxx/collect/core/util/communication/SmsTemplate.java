package com.xxx.collect.core.util.communication;

/**
 * Created by luju on 2016/3/10.
 */
public enum SmsTemplate {
  register("注册验证", "SMS_5670088"),
  check("身份验证", "SMS_5670092"),
  login("登录验证", "SMS_5670090"),
  change("变更验证", "SMS_5670085"),
  test("登录验证", "SMS_5670089");

  private String signName;//大鱼签名
  private String templateCode;//大鱼模版代码

  SmsTemplate(String signName, String templateCode) {
    this.signName = signName;
    this.templateCode = templateCode;
  }


  public String getCode() {
    return this.toString();
  }

  public String getSignName() {
    return signName;
  }

  public void setSignName(String signName) {
    this.signName = signName;
  }

  public String getTemplateCode() {
    return templateCode;
  }

  public void setTemplateCode(String templateCode) {
    this.templateCode = templateCode;
  }
}
