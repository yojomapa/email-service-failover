package com.yojomapa.provider.strategy;

import com.yojomapa.dto.EmailDTO;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by yojomapa on 26/03/17.
 */
@Component
@Order(value = 2)
public class SendGridProviderStrategy extends EmailProviderStrategy {

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
