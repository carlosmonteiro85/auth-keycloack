package br.com.auth.keycloak.user.external;

import java.util.List;
import lombok.Data;

@Data
public class CredencialAcesso {

  private Long id;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private List<String> groups;
  private List<String> roles;
}
