package br.com.auth.keycloak.user.external;

public class CredencialException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public CredencialException(String message) {
    super(message);
  }

  public CredencialException(String message, Throwable cause) {
    super(message, cause);
  }
}
