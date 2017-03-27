package com.yojomapa.steps;

import com.yojomapa.bootstrap.ApplicationStart;
import com.yojomapa.dto.EmailDTO;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by yojomapa on 26/03/17.
 */
@ContextConfiguration(classes = ApplicationStart.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailServerSteps implements En {

  private EmailDTO emailDTO = new EmailDTO();

  public EmailServerSteps() {

    Given("^the email subject \"([^\"]*)\"$", (String subject) -> {
      emailDTO.setSubject(subject);
    });

    Given("^the body is$", (String body) -> {
      emailDTO.setBody(body);
    });

    Given("^the email address destination list is$", (DataTable arg1) -> {
      // Write code here that turns the phrase above into concrete actions
      // For automatic transformation, change DataTable to one of
      // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
      // E,K,V must be a scalar (String, Integer, Date, enum etc)
      throw new PendingException();
    });

    When("^the email service is called with the give data$", () -> {
      // Write code here that turns the phrase above into concrete actions
      throw new PendingException();
    });

    Then("^the response status is (\\d+)$", (Integer arg1) -> {
      // Write code here that turns the phrase above into concrete actions
      throw new PendingException();
    });
  }
}
