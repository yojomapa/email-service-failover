package com.yojomapa.provider;

import com.yojomapa.dto.EmailDTO;
import com.yojomapa.provider.strategy.EmailProviderStrategy;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Is responsible for managing the failover and switch between different configured providers
 * Created by yojomapa on 26/03/17.
 */
@Component
@Slf4j
public class ProviderManager {

  @Getter
  @Setter
  @Autowired
  private List<EmailProviderStrategy> providers;

  @Autowired
  private ProviderManagerProperties properties;


  /**
   * Sends the email to the first configured provider that succeed.
   * @param emailDTO Email to send.
   * @return True if the email could be sent, false otherwise.
   */
  public boolean send(EmailDTO emailDTO) {
    boolean success = false;
    int providerIndex = 0;

    do {
      success = trySend(emailDTO, providers.get(providerIndex));
    } while (!success && ++providerIndex < providers.size());

    return success;
  }

  /**
   * Tries to send the Email
   * @param emailDTO
   * @param emailProviderStrategy
   * @return
   */
  private boolean trySend(EmailDTO emailDTO, EmailProviderStrategy emailProviderStrategy) {

    String responseStatus = null;

    for (int i = 0; i < properties.getRetryTimes() && !matchOkResponse(responseStatus); i++) {
      responseStatus = emailProviderStrategy.send(emailDTO);

      if (matchOkResponse(responseStatus)) break;

      try {
        Thread.sleep(properties.getRetryDuration());
      } catch (InterruptedException e) {
        log.error("Retry fails", e);
      }
    }

    return matchOkResponse(responseStatus);
  }

  /**
   * Match the response with any of the HTTP Codes 2xx
   * @param responseStatus
   * @return True if the code is of type 2xx, false otherwise
   */
  private boolean matchOkResponse(String responseStatus) {
    if (responseStatus != null) {
      return Pattern.matches("2..", responseStatus);
    } else {
      return false;
    }
  }


}
