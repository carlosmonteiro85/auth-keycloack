package br.com.ebix.keycloak.user.external;

public class UsuarioException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public UsuarioException(String message) {
    super(message);
  }

  public UsuarioException(String message, Throwable cause) {
    super(message, cause);
  }
}
