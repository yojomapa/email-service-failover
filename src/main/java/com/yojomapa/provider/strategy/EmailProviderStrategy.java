package com.yojomapa.provider.strategy;

import com.yojomapa.dto.AttachmentDTO;
import com.yojomapa.dto.EmailDTO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Represents an abstraction over providers
 *
 * Created by yojomapa on 26/03/17.
 */
@Data
public abstract class EmailProviderStrategy {

  protected String apiKey;

  @Value("${email.domain}")
  protected String domain;

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
