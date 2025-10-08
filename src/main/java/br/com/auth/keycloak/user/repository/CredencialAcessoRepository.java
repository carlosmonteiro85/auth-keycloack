package br.com.auth.keycloak.user.repository;

import java.util.List;

import br.com.auth.keycloak.user.dominio.CredencialAcesso;

public interface CredencialAcessoRepository {
  public CredencialAcesso getUserByEmail(String email);
  public CredencialAcesso getUserByUsername(String username);
  public Integer getUsersCount();
  public List<CredencialAcesso> getUsersStream(Integer firstResult, Integer maxResults);
  public List<CredencialAcesso> searchForUserStream(String search, Integer firstResult, Integer maxResults);
  public List<CredencialAcesso> searchForAllUserStream(String search, Integer firstResult, Integer maxResults);
  public boolean isValid(String username, String password);
} 
