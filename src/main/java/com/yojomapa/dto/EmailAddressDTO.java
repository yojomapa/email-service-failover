package com.yojomapa.dto;

import lombok.Data;

/**
 * Represents a email address with a personal name.
 *
 * Created by yojomapa on 26/03/17.
 */
@Data
public class EmailAddressDTO {

  private String name;

  private String email;

}
