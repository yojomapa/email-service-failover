package com.yojomapa.provider;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by yojomapa on 26/03/17.
 */
@ConfigurationProperties(prefix = "provider.manager")
@Component
@Data
public class ProviderManagerProperties {

  private int retryTimes;

  private long retryDuration;

}
