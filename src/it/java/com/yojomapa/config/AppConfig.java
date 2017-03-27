package com.yojomapa.config;

import com.yojomapa.provider.strategy.MailGunProviderStrategy;
import com.yojomapa.provider.strategy.MandrillProviderStrategy;
import com.yojomapa.provider.strategy.SendGridProviderStrategy;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by yojomapa on 26/03/17.
 */
@Profile("test")
@Configuration
public class AppConfig {

  @Bean
  public MailGunProviderStrategy mailGunProviderStrategy() {
    return Mockito.mock(MailGunProviderStrategy.class);
  }

  @Bean
  public SendGridProviderStrategy sendGridProviderStrategy() {
    return Mockito.mock(SendGridProviderStrategy.class);
  }

  @Bean
  MandrillProviderStrategy mandrillProviderStrategy() {
    return Mockito.mock(MandrillProviderStrategy.class);
  }
}
