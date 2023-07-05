package br.com.ebix.keycloak.user.external;

import java.util.List;

public interface UsuarioRepository {

  public Usuario getUserByEmail(String email);

  public Usuario getUserByUsername(String username);

  public Integer getUsersCount();

  public List<Usuario> getUsersStream(Integer firstResult, Integer maxResults);

  public List<Usuario> searchForUserStream(String search, Integer firstResult, Integer maxResults);
  
  public List<Usuario> searchForAllUserStream(String search, Integer firstResult, Integer maxResults);

  public boolean isValid(String username, String password);

} 
