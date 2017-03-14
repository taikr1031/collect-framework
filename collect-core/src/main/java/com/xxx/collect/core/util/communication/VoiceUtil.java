package com.xxx.collect.core.util.communication;

import com.xxx.collect.core.util.JsonUtil;
import com.xxx.collect.core.util.MailUtil;
import com.xxx.collect.core.util.date.DateCalcUtil;
import com.xxx.collect.core.util.math.RandomUtils;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcTtsNumSinglecallRequest;
import com.taobao.api.response.AlibabaAliqinFcTtsNumSinglecallResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonNode;

import java.util.Date;

/**
 * 电话语音发送接口,未完成,必须先注册为支付宝企业账户
 * Created by luju on 2016/3/10.
 */
public class VoiceUtil {
  public static void main(String[] args) throws SmsSendException {
//    sendVerifyCode("13396061821", SmsTemplate.test);
//    SmsUtil.sendSysWarn("error", "大量出错,1h>30");
    VoiceUtil.sendVerifyCode("13396061821", SmsTemplate.check);
  }

  public final static String SESSION_KEY_VERIFICATION_CODE = "SMS_VERIFICATION_CODE";
  private static final Log log = LogFactory.getLog(VoiceUtil.class);

  public static String sendVerifyCode(String phone, SmsTemplate template) throws SmsSendException {
    String verifyCode = createVerifyCode();
    log.info("发送短信验证码:" + verifyCode + " phone:" + phone);
    send(phone, template, verifyCode, "爱给网");
    return verifyCode;
  }

  /**
   * 最近一次系统报警发送时间
   */
  public static Date lastWarnSendTime = null;

  /**
   * 发送系统报警
   *
   * @param code
   * @param msg
   */
  public static void sendSysWarn(String code, String msg) throws SmsSendException {
    if (lastWarnSendTime != null && DateCalcUtil.betweenSecond(lastWarnSendTime, new Date()) < 3600) {
      MailUtil.sendMy("短信报警 code=" + code + " msg=" + msg, "系统报警短信1小时内只发一次，停止发送");
      return;
    }
    send("13396061821", SmsTemplate.test, code, "'" + msg + "'");
  }

  private static void send(String phone, SmsTemplate template, String code, String product) throws SmsSendException {
    TaobaoClient client = new DefaultTaobaoClient(
        "http://gw.api.taobao.com/router/rest",
        "23323939",
        "a90d55e18a8c155081b212113ca92e02",
        "json"
    );
    AlibabaAliqinFcTtsNumSinglecallRequest req = new AlibabaAliqinFcTtsNumSinglecallRequest();
    req.setExtend("12345");
    req.setTtsParamString("{\"AckNum\":\"123456\"}");
    req.setCalledNum(phone);
    req.setCalledShowNum("4001112222");
    req.setTtsCode("TTS_5670096");
    try {
      AlibabaAliqinFcTtsNumSinglecallResponse rsp = client.execute(req);
      JsonNode content = JsonUtil.readTree(rsp.getBody());
      JsonNode error = content.get("error_response");
      if (error != null) {
        String sub_msg = error.get("sub_msg").asText();
        if (sub_msg != null) {
          if (sub_msg.equals("触发业务流控")) {
            throw new SmsSendException("发送过于频繁,请稍等再试！");
          }
          if (sub_msg.equals("号码格式错误")) {
            throw new SmsSendException(sub_msg);
          }
          log.error("阿里大鱼语音接口出错:" + sub_msg);
        }
        throw new RuntimeException();
      }
    } catch (ApiException e) {
      throw new RuntimeException(e);
    }

  }

  public static String createVerifyCode() {
    String result = "";
    for (int i = 0; i < 6; i++) {
      result += RandomUtils.nextInt(9);
    }
    return result;
  }
}
