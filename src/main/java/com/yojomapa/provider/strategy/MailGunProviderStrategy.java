package com.yojomapa.provider.strategy;

import com.yojomapa.dto.AttachmentDTO;
import com.yojomapa.dto.EmailDTO;
import lombok.extern.slf4j.Slf4j;
import net.sargue.mailgun.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.lang.String.format;

/**
 * Created by yojomapa on 26/03/17.
 */
@Component
@Slf4j
@Order(value = 1)
@ConfigurationProperties(prefix = "email.provider")
public class MailGunProviderStrategy extends EmailProviderStrategy {

  private String mailgunDomain;

  private String mailgunApiKey;

  private Configuration configuration;


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

    if (configuration == null) {
      configuration = new Configuration()
              .domain(this.mailgunDomain)
              .apiKey(this.mailgunApiKey)
              .from(super.getFromName(), super.getFromEmail());
    }

    MailBuilder mailBuilder = Mail.using(configuration);
    emailDTO.getTo().forEach(emailAddressDTO -> mailBuilder.to(emailAddressDTO.getName(), emailAddressDTO.getEmail()));
    emailDTO.getCc().forEach(emailAddressDTO -> mailBuilder.cc(emailAddressDTO.getName(), emailAddressDTO.getEmail()));
    emailDTO.getBcc().forEach(emailAddressDTO -> mailBuilder.bcc(emailAddressDTO.getName(), emailAddressDTO.getEmail()));

    Response response = null;

    if (emailDTO.getAttachments() != null && emailDTO.getAttachments().size() > 0) {
      log.info("Sending Email with attachments");
      response = addAttachments(mailBuilder.subject(emailDTO.getSubject())
              .text(emailDTO.getBody()).multipart(),  emailDTO.getAttachments())
              .build()
              .send();

    } else {
      log.info("Sending Email without attachments");
      response = mailBuilder.subject(emailDTO.getSubject())
              .text(emailDTO.getBody())
              .build()
              .send();
    }

    log.info(format("Email sent with status %s to MainGun ", response.responseCode()));
    return String.valueOf(response.responseCode());
  }

  private MultipartBuilder addAttachments(MultipartBuilder builder, List<AttachmentDTO> attachments) {
    attachments.forEach(
            attachDTO -> builder.attachment(attachDTO.getContentBase64(), attachDTO.getFileName()));
    return builder;
  }
}
