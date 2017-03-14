package com.xxx.collect.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;
import java.io.File;
import java.util.List;
import java.util.Properties;

public class MailUtil {
  public static void main(String[] args) throws MessagingException {
//    send("akakafei1@163.com", "ttt12", "fullUrl:/mock/showAllPara?gmt_create=2016-01-15 09:55:20&seller_email=tony8587@qq.com&subject=赞助爱给网0.03元&use_coupon=N&sign=922b19bf2b44b2a97095f6c8dca85b2c&discount=0.00&body=赞助爱给网0.03元&buyer_id=2088202678861224&is_success=T&logistics_fee=0.00&notify_id=RqPnCoPT3K9%2Fvwbh3InUFP3ldCaHFzFzrLOBgavKO3pUAnyv9ygBMxfvVSah1mAm0LM%2B&notify_type=trade_status_sync&price=0.03&total_fee=0.03&trade_status=WAIT_SELLER_SEND_GOODS&receive_name=测试帐号&logistics_type=EXPRESS&sign_type=MD5&receive_address=赞助爱给网0.03元&seller_id=2088202967792464&is_total_fee_adjust=N&buyer_email=joka198542@126.com&returnStr=success&gmt_payment=2016-01-15 09:55:39&notify_time=2016-01-15 09:55:43&quantity=1&gmt_logistics_modify=2016-01-15 09:55:20&optType=return&payment_type=1&out_trade_no=20160115-095503-610-9&trade_no=2016011500001000220065457237&logistics_payment=SELLER_PAY&seller_actions=SEND_GOODS\n" +
//        "");
    sendWithAttachment("astarusername1@163.com", "ttt12", "文件", ConstructUtils.list(new File("d:\\Encoder.java"), new File("d:\\Encoder.java")));
  }

  private static final Log log = LogFactory.getLog(MailUtil.class);

  static Properties props;
  static Session mailSession;

  private static final String HOST = "smtp.163.com";
  public static final String USER = "astarusername1@163.com";
  private static final String PASSWORD = "astarusername";

  static {
    // 配置发送邮件的环境属性
    props = new Properties();
        /*
         * 可用的属性： mail.store.protocol / mail.transport.protocol / mail.host /
         * mail.user / mail.from
         */
    // 表示SMTP发送邮件，需要进行身份验证
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.host", HOST);
    // 发件人的账号
    props.put("mail.user", USER);
    // 访问SMTP服务时需要提供的密码
    props.put("mail.password", PASSWORD);

    // 构建授权信息，用于进行SMTP进行身份验证
    Authenticator authenticator = new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        // 用户名、密码
        String userName = props.getProperty("mail.user");
        String password = props.getProperty("mail.password");
        return new PasswordAuthentication(userName, password);
      }
    };
    // 使用环境属性和授权信息，创建邮件会话
    mailSession = Session.getInstance(props, authenticator);
  }

  /**
   * 给自己发送邮件
   *
   * @param title
   * @param content
   * @throws MessagingException
   */
  public static void sendMy(String title, String content) {
    try {
      send("akakafei1@163.com", title, content);
    } catch (MessagingException e) {
      log.error(e, e);
    }
  }

  public static void send(String targetMailAddress, String title, String content) throws MessagingException {

    // 创建邮件消息
    MimeMessage message = new MimeMessage(mailSession);
    // 设置发件人
    InternetAddress form = new InternetAddress(USER);
    message.setFrom(form);

    // 设置收件人
    InternetAddress to = new InternetAddress(targetMailAddress);
    message.setRecipient(RecipientType.TO, to);

    // 设置抄送
//    InternetAddress cc = new InternetAddress("luo_aaaaa@yeah.net");
//    message.setRecipient(RecipientType.CC, cc);

    // 设置密送，其他的收件人不能看到密送的邮件地址
//    InternetAddress bcc = new InternetAddress("aaaaa@163.com");
//    message.setRecipient(RecipientType.CC, bcc);

    // 设置邮件标题
    message.setSubject(title);

    // 设置邮件的内容体
    message.setContent(content, "text/html;charset=UTF-8");

    // 发送邮件
    Transport.send(message);

  }

  /**
   * 发送邮件,带附件
   *
   * @param title       邮件主题
   * @param sendHtml    邮件内容
   * @param receiveUser 收件人地址
   * @param attachments 附件
   */
  public static void sendWithAttachment(String receiveUser, String title, String sendHtml, List<File> attachments) {
    Transport transport = null;
    try {
      // 发件人
      InternetAddress from = new InternetAddress(USER);
      MimeMessage message = new MimeMessage(mailSession);
      message.setFrom(from);

      // 收件人
      InternetAddress to = new InternetAddress(receiveUser);
      message.setRecipient(Message.RecipientType.TO, to);

      // 邮件主题
      message.setSubject(title);

      // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
      Multipart multipart = new MimeMultipart();

      // 添加邮件正文
      BodyPart contentPart = new MimeBodyPart();
      contentPart.setContent(sendHtml, "text/html;charset=UTF-8");
      multipart.addBodyPart(contentPart);

      // 添加附件的内容
      for (File attachment : attachments) {
        BodyPart attachmentBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(attachment);
        attachmentBodyPart.setDataHandler(new DataHandler(source));

        // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
        // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
        //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
        //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");

        //MimeUtility.encodeWord可以避免文件名乱码
        attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
        multipart.addBodyPart(attachmentBodyPart);
      }

      // 将multipart对象放到message中
      message.setContent(multipart);
      // 保存邮件
      message.saveChanges();

      transport = mailSession.getTransport("smtp");
      // smtp验证，就是你用来发邮件的邮箱用户名密码
      transport.connect(HOST, USER, PASSWORD);
      // 发送
      transport.sendMessage(message, message.getAllRecipients());

    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      if (transport != null) {
        try {
          transport.close();
        } catch (MessagingException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

}
