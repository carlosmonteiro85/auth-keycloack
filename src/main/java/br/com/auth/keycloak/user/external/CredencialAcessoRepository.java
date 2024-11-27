package br.com.auth.keycloak.user.external;

import java.util.List;

public interface CredencialAcessoRepository {

  public CredencialAcesso getUserByEmail(String email);

  public CredencialAcesso getUserByUsername(String username);

  public Integer getUsersCount();

  public List<CredencialAcesso> getUsersStream(Integer firstResult, Integer maxResults);

  public List<CredencialAcesso> searchForUserStream(String search, Integer firstResult, Integer maxResults);
  
  public List<CredencialAcesso> searchForAllUserStream(String search, Integer firstResult, Integer maxResults);

  public boolean isValid(String username, String password);

} 
