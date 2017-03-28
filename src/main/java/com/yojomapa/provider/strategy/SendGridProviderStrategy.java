package com.yojomapa.provider.strategy;

import com.sendgrid.*;
import com.yojomapa.dto.EmailDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.lang.String.format;

/**
 * Created by yojomapa on 26/03/17.
 */
@Component
@Slf4j
@Data
@Order(value = 1)
@ConfigurationProperties(prefix = "email.provider")
public class SendGridProviderStrategy extends EmailProviderStrategy {

  public static final String SERVER_ERROR = "500";
  public static final String THERE_WAS_A_PROBLEM_SENDING_THE_EMAIL = "There was a problem sending the email";
  public static final String SENDGRID_END_POINT = "mail/send";

  private String sendgridDomain;

  private String sendgridApiKey;

  private SendGrid sendGrid;

  @Override
  public void init() {

  }

  /**
   * Sends the email to the provider using its API.
   * @param emailDTO The email to send.
   * @return The status returned by the API of the provider.
   */
  @Override
  public String send(EmailDTO emailDTO) {

    if (sendGrid == null) {
      sendGrid = new SendGrid(this.sendgridApiKey);
    }

    log.info(format(SENDING_EMAIL_USING, "SendGrid"));

    Mail mail = new Mail(); //from, subject, to, content);
    mail.setFrom(new Email(super.getFromEmail(), super.getFromName()));
    mail.setSubject(emailDTO.getSubject());
    mail.addContent(new Content("text/plain", emailDTO.getBody()));

    Personalization personalization = new Personalization();
    
    emailDTO.getTo().forEach(emailAddressDTO ->
            personalization.addTo(new Email(emailAddressDTO.getEmail(), emailAddressDTO.getName())));
    emailDTO.getCc().forEach(emailAddressDTO ->
            personalization.addCc(new Email(emailAddressDTO.getEmail(), emailAddressDTO.getName())));
    emailDTO.getBcc().forEach(emailAddressDTO ->
            personalization.addBcc(new Email(emailAddressDTO.getEmail(), emailAddressDTO.getName())));

    mail.addPersonalization(personalization);

    if (emailDTO.getAttachments() != null && emailDTO.getAttachments().size() > 0) {
      log.info(SENDING_EMAIL_WITH_ATTACHMENTS);
      emailDTO.getAttachments().forEach(attachmentDTO -> {
        Attachments attach = new Attachments();
        attach.setContent(attachmentDTO.getContentBase64());
        attach.setType(attachmentDTO.getContentType());
        attach.setFilename(attachmentDTO.getFileName());
        mail.addAttachments(attach);
      });
    } else {
      log.info(SENDING_EMAIL_WITHOUT_ATTACHMENTS);
    }

    Request request = new Request();
    Response response = null;
    try {
      request.method = Method.POST;
      request.endpoint = SENDGRID_END_POINT;
      request.body = mail.build();
      response = sendGrid.api(request);
      System.out.println(response.statusCode);
      System.out.println(response.body);
      System.out.println(response.headers);
    } catch (IOException e) {
      log.error(THERE_WAS_A_PROBLEM_SENDING_THE_EMAIL, e);
    }

    String resultStatus = response != null? String.valueOf(response.statusCode) : SERVER_ERROR;
    log.info(format(EMAIL_SENT_WITH_STATUS, resultStatus));
    return resultStatus;
  }
}
