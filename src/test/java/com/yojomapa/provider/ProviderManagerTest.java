package com.yojomapa.provider;

import com.yojomapa.bootstrap.ApplicationStart;
import com.yojomapa.dto.EmailDTO;
import com.yojomapa.provider.strategy.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by yojomapa on 26/03/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationStart.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProviderManagerTest {

  @Mock
  private MailGunProviderStrategy mailGunProvider;

  @Mock
  private SendGridProviderStrategy sendGridProvider;

  @Mock
  private MandrillProviderStrategy mandrillProvider;

  @Mock
  private ProviderManagerProperties properties;

  @Mock
  private EmailDTO emailDTOMock;

  @InjectMocks
  private ProviderManager providerManager;


  @Before
  public void setUp() throws Exception {
    initMocks(this);
    List<EmailProviderStrategy> providers = new ArrayList<EmailProviderStrategy>() {{
      add(mailGunProvider);
      add(sendGridProvider);
      add(mandrillProvider);
    }};

    providerManager.setProviders(providers);

    when(properties.getRetryDuration()).thenReturn(0L);
    when(properties.getRetryTimes()).thenReturn(2);
  }

  @Test
  public void TestProvidersAreInjected() {
    assertThat("Providers List should have 3 providers", providerManager.getProviders().size(), is(3));
  }

  @Test
  public void TestSwitchOverProviders() throws Exception {
    when(mailGunProvider.send(any(EmailDTO.class))).thenReturn("401"); // mailgun fails
    when(sendGridProvider.send(any(EmailDTO.class))).thenReturn("202"); // should try with sendgrid

    boolean success = providerManager.send(emailDTOMock);

    assertThat("send email should sucess", success, is(true));

  }

  @Test
  public void TestAllProvidersFail() throws Exception {
    when(mailGunProvider.send(any(EmailDTO.class))).thenReturn("401"); // mailgun fails
    when(sendGridProvider.send(any(EmailDTO.class))).thenReturn("404"); // sendgrid fails
    when(mandrillProvider.send(any(EmailDTO.class))).thenReturn("400"); // mandrill fails

    boolean success = providerManager.send(emailDTOMock);

    assertThat("send email should fail", success, is(false));

  }
}
