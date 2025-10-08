package br.com.auth.keycloak.user.dominio;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CredencialAcesso {
  private Long id;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private List<String> groups;
  private List<String> roles;
}
