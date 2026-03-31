package com.bedatadriven.jackson.datatype.jts.parsers;

import tools.jackson.core.JacksonException;

public class JacksonGeometryException extends JacksonException {

  public JacksonGeometryException(String message) {
    super(message);
  }

  public JacksonGeometryException(String message, Throwable cause) {
    super(message, cause);
  }

}
