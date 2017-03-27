package com.yojomapa.provider;

import com.yojomapa.dto.EmailDTO;
import com.yojomapa.provider.strategy.EmailProviderStrategy;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Is responsible for managing the failover and switch between different configured providers
 * Created by yojomapa on 26/03/17.
 */
@Component
@Slf4j
public class ProviderManager {

  public static final String OK_STATUS = "200";

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

  private boolean trySend(EmailDTO emailDTO, EmailProviderStrategy emailProviderStrategy) {

    String responseStatus = null;

    for (int i = 0; i < properties.getRetryTimes() && !OK_STATUS.equals(responseStatus); i++) {
      responseStatus = emailProviderStrategy.send(emailDTO);

      if (OK_STATUS.equals(responseStatus)) break;

      try {
        Thread.sleep(properties.getRetryDuration());
      } catch (InterruptedException e) {
        log.error("Retry fails", e);
      }
    }

    return OK_STATUS.equals(responseStatus);
  }


}
