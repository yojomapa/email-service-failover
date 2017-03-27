package com.yojomapa.service;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;

import com.yojomapa.dto.EmailAddressDTO;
import com.yojomapa.dto.EmailDTO;
import com.yojomapa.exception.ValidationException;
import com.yojomapa.provider.ProviderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yojomapa on 26/03/17.
 */
@Slf4j
@Service
public class EmailService {

  public static final String EMAIL_ADDRESSES_ARE_NOT_VALID = "These email addresses are not valid: { %s }";
  public static final String EMAIL_TO_LIST_EMPTY = "The email To list should not be empty";

  @Autowired
  private ProviderManager providerManager;

  public boolean sendEmail(EmailDTO emailDTO) {
    validaEmptyToList(emailDTO);
    validateEmailAddress(emailDTO);
    return providerManager.send(emailDTO);
  }

  private void validaEmptyToList(EmailDTO emailDTO) {
    if (emailDTO.getTo().isEmpty()) {
      String message = EMAIL_TO_LIST_EMPTY;
      log.error(message);
      throw new ValidationException(message);
    }
  }

  private void validateEmailAddress(EmailDTO emailDTO) {
    final EmailValidator instance = EmailValidator.getInstance();
    Predicate<String> emailIsNotValid = email -> !instance.isValid(email);
    Function<EmailAddressDTO, String> emailMapper = EmailAddressDTO::getEmail;

    List<EmailAddressDTO> allEmails = new ArrayList<>();
    allEmails.addAll(emailDTO.getTo());
    allEmails.addAll(emailDTO.getCc());
    allEmails.addAll(emailDTO.getBcc());

    if (allEmails.stream().map(emailMapper).anyMatch(emailIsNotValid)) {
      
      List<String> invalidEmails = allEmails
              .stream()
              .map(emailMapper)
              .filter(emailIsNotValid)
              .collect(Collectors.toList());

      String message = format(EMAIL_ADDRESSES_ARE_NOT_VALID, String.join(", ", invalidEmails));
      log.error(message);
      throw new ValidationException(message);
    }
  }
}
