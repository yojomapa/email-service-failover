package com.yojomapa.controller;

import com.yojomapa.dto.EmailDTO;
import com.yojomapa.provider.ProviderManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yojomapa on 25/03/17.
 */
@RequestMapping(value = "email")
@RestController
@Slf4j
public class EmailController {

  @Autowired
  private ProviderManager providerManager;

  @RequestMapping(value = "/send", method = RequestMethod.POST,
          consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<String> sendEmail(@RequestBody @Validated EmailDTO emailDTO) {
    log.info("Log Test");
    //TODO: add validations
    return new ResponseEntity<String>("Test OK", HttpStatus.OK);
  }

}
