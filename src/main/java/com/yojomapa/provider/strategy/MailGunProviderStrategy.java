package com.yojomapa.provider.strategy;

import com.yojomapa.dto.AttachmentDTO;
import com.yojomapa.dto.EmailDTO;
import lombok.extern.slf4j.Slf4j;
import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;
import net.sargue.mailgun.MultipartBuilder;
import net.sargue.mailgun.Response;
import org.springframework.beans.factory.annotation.Value;
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
public class MailGunProviderStrategy extends EmailProviderStrategy {

  @Value("${email.from}")
  private String fromEmail;

  @Value("${email.from-name}")
  private String fromName;

  private Configuration configuration;

  @Override
  public void init() {

    configuration = new Configuration()
    .domain(this.domain)
    .apiKey(this.apiKey)
    .from(this.fromName, this.fromEmail);
  }

  /**
   * Sends the email to the provider using its API.
   * @param emailDTO The email to send.
   * @return The status returned by the API of the provider.
   */
  @Override
  public String send(EmailDTO emailDTO) {
    Response response = addAttachments(Mail.using(configuration)
            .to(emailDTO.getTo().get(0).getEmail())
            .subject(emailDTO.getSubject())
            .text(emailDTO.getBody())
            .multipart(), emailDTO.getAttachments())
            .build()
            .send();

    log.info(format("Mail sent with status %s to MainGun ", response.responseCode()));
    return String.valueOf(response.responseCode());
  }

  private MultipartBuilder addAttachments(MultipartBuilder builder, List<AttachmentDTO> attachments) {
    attachments.forEach(
            attachDTO -> builder.attachment(attachDTO.getContentBase64(), attachDTO.getFileName()));
    return builder;
  }
}
