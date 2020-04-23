package io.github.socraticphoenix.jamfx;

/** An exception thrown when {@link JamProperties#require(String, Class)} fails. */
public class JamPropertyRequiredException extends RuntimeException {

  public JamPropertyRequiredException(String message) {
    super(message);
  }
}
