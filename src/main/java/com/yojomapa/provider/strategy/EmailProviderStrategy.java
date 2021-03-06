package com.yojomapa.provider.strategy;

import com.yojomapa.dto.EmailDTO;
import lombok.Data;

/**
 * Represents an abstraction over providers.
 * This enables the Strategy Pattern over different email providers.
 *
 * Created by yojomapa on 26/03/17.
 */
@Data
public abstract class EmailProviderStrategy {

  public static final String EMAIL_SENT_WITH_STATUS = "Email sent with status %s";
  public static final String SENDING_EMAIL_WITH_ATTACHMENTS = "Sending Email with attachments";
  public static final String SENDING_EMAIL_WITHOUT_ATTACHMENTS = "Sending Email without attachments";
  public static final String SENDING_EMAIL_USING = "Sending Email using %s";

  public EmailProviderStrategy() {
    init();
  }

  protected String fromEmail;

  protected String fromName;

  /**
   * Excecutes the required initialization of the provider API
   */
  public abstract void init();

  /**
   * Sends the email to the provider using its API.
   * @param emailDTO The email to send.
   * @return The status returned by the API of the provider.
   */
  public abstract String send(EmailDTO emailDTO);

}
