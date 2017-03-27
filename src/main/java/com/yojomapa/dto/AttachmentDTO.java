package com.yojomapa.dto;

import lombok.Data;

/**
 * Represents an attachment file.
 *
 * The content of the Attachment needs to be binary data encoded in base64
 * https://en.wikipedia.org/wiki/Base64
 *
 * Created by yojomapa on 26/03/17.
 */
@Data
public class AttachmentDTO {

  private String fileName;

  private String contentType;

  private String contentBase64;

}
