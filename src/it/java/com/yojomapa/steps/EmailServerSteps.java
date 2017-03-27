package com.yojomapa.steps;

import com.yojomapa.bootstrap.ApplicationStart;
import com.yojomapa.dto.EmailAddressDTO;
import com.yojomapa.dto.EmailDTO;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

/**
 * Created by yojomapa on 26/03/17.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = ApplicationStart.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailServerSteps implements En {

  @Autowired
  private TestRestTemplate testRestTemplate;

  private EmailDTO emailDTO = new EmailDTO();

  private Integer responseStatus;

  public EmailServerSteps() {

    Given("^the email subject \"([^\"]*)\"$", (String subject) -> {
      this.emailDTO.setSubject(subject);
    });

    Given("^the body is$", (String body) -> {
      this.emailDTO.setBody(body);
    });

    Given("^the email address destination list is$", (DataTable dataTable) -> {
      List<EmailAddressDTO> addressDTOList = dataTable.asList(EmailAddressDTO.class);
      this.emailDTO.getTo().addAll(addressDTOList);
    });

    When("^the email service is called with the give data$", () -> {
      // Write code here that turns the phrase above into concrete actions

      ResponseEntity<String> response = testRestTemplate.postForEntity("/email/send", this.emailDTO, String.class);
      responseStatus = response.getStatusCodeValue();
    });

    Then("^the response status is (\\d+)$", (Integer statusCode) -> {
      Assert.assertEquals(statusCode, responseStatus);
    });
  }
}
