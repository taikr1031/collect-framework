package com.xxx.collect.core.util;

import jj.play.ns.nl.captcha.Captcha;

public class CaptchaUtil {
  
  public final static String SESSION_KEY_VERIFICATION_CODE = "VERIFICATION_CODE";
  
  public static Captcha getCaptcha(){
    Captcha captcha = new Captcha.Builder(150, 50).addText().addBackground().addNoise().build();
    return captcha;
  }

}
