package com.yojomapa.provider.strategy;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.yojomapa.dto.EmailDTO;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by yojomapa on 26/03/17.
 */
@Component
@Slf4j
@Data
@Order(value = 3)
@ConfigurationProperties(prefix = "email.provider")
public class MandrillProviderStrategy extends EmailProviderStrategy {

  private String mandrillDomain;

  private String mandrillApiKey;

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
    //TODO: Implement
    return "200";
  }
}
