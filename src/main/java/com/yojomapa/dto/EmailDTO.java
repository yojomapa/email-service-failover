package com.yojomapa.dto;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Email that is being send.
 * Created by yojomapa on 26/03/17.
 */
@Data
public class EmailDTO {

  public static final String EMPTY_MESSAGE = "--Empty Message--";
  public static final String NO_SUBJECT = "No Subject";

  @NotNull
  private final List<EmailAddressDTO> to = new ArrayList<>();

  @Getter(lazy = true)
  private final List<EmailAddressDTO> cc = new ArrayList<>();

  @Getter(lazy = true)
  private final List<EmailAddressDTO> bcc = new ArrayList<>();

  private String subject = NO_SUBJECT;

  private String replyTo;

  private String body = EMPTY_MESSAGE;

  private List<AttachmentDTO> attachments;


}
