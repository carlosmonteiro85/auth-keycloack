package br.com.auth.keycloak.user.provider;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.ReadOnlyException;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;
import org.keycloak.storage.user.UserRegistrationProvider;

import br.com.auth.keycloak.user.dominio.CredencialAcesso;
import br.com.auth.keycloak.user.dominio.UserAdapter;
import br.com.auth.keycloak.user.repository.CredencialAcessoRepository;
import br.com.auth.keycloak.user.repository.CredencialAcessoRepositoryImpl;

@Slf4j
public class CustomUserProvider
    implements UserStorageProvider,
        UserLookupProvider,
        UserQueryProvider,
        CredentialInputUpdater,
        CredentialInputValidator,
        UserRegistrationProvider {

  private final KeycloakSession session;
  private final ComponentModel model;
  private final CredencialAcessoRepository repository;

  protected Map<String, UserModel> loadedUsers = new HashMap<>();

  public CustomUserProvider(KeycloakSession session, ComponentModel model) {
    this.session = session;
    this.model = model;
    this.repository = new CredencialAcessoRepositoryImpl(model);
  }

  @Override
  public void close() {
    throw new ReadOnlyException("Não há implementação para esse método");
  }

  @Override
  public UserModel getUserByEmail(RealmModel realm, String email) {
    log.info("[I48] getUserByEmail({})", email);
    UserModel adapter = loadedUsers.get(email);
    if (adapter == null) {
      CredencialAcesso credencialAcesso = repository.getUserByEmail(email);
      adapter = new UserAdapter(session, realm, model, credencialAcesso);
      loadedUsers.put(email, adapter);
    }
    return adapter;
  }

  @Override
  public UserModel getUserById(RealmModel realm, String id) {
    log.info("[I35] getUserById({})", id);
    String username = StorageId.externalId(id);
    return getUserByUsername(realm, username);
  }

  @Override
  public UserModel getUserByUsername(RealmModel realm, String username) {
    log.info("[I41] getUserByUsername({})", username);
    UserModel adapter = loadedUsers.get(username);
    if (adapter == null) {
      CredencialAcesso credencialAcesso = repository.getUserByUsername(username);
      adapter = new UserAdapter(session, realm, model, credencialAcesso);
      loadedUsers.put(username, adapter);
    }
    return adapter;
  }

  @Override
  public Stream<UserModel> getGroupMembersStream(
      RealmModel realm, GroupModel group, Integer firstResult, Integer maxResults) {
    return Stream.empty();
  }

  @Override
  public int getUsersCount(RealmModel realm) {
    log.info("[I93] getUsersCount: realm={}", realm.getName());
    return repository.getUsersCount();
  }

  @Override
  public Stream<UserModel> searchForUserStream(
      RealmModel realm, String search, Integer firstResult, Integer maxResults) {
    log.info("searchForUserStream, search={}, first={}, max={}", search, firstResult, maxResults);
    if (search.equals("*")) {
      return repository.searchForAllUserStream(search, firstResult, maxResults).stream()
          .map(user -> new UserAdapter(session, realm, model, user));
    }
    return repository.searchForUserStream(search, firstResult, maxResults).stream()
        .map(credencialAcesso -> new UserAdapter(session, realm, model, credencialAcesso));
  }

  @Override
  public Stream<UserModel> searchForUserByUserAttributeStream(
      RealmModel arg0, String arg1, String arg2) {
    return Stream.empty();
  }

  @Override
  public Stream<UserModel> searchForUserStream(
      RealmModel realm, Map<String, String> params, Integer firstResult, Integer maxResults) {
    log.info("searchForUserStream, search={}, first={}, max={}", params, firstResult, maxResults);
    return repository.searchForUserStream(null, firstResult, maxResults).stream()
        .map(credencialAcesso -> new UserAdapter(session, realm, model, credencialAcesso));
  }

  @Override
  public void disableCredentialType(RealmModel arg0, UserModel arg1, String arg2) {
    throw new ReadOnlyException("Não há implementação para esse método");
  }

  @Override
  public Stream<String> getDisableableCredentialTypesStream(RealmModel arg0, UserModel arg1) {
    return Stream.empty();
  }

  @Override
  public boolean supportsCredentialType(String credentialType) {
    return PasswordCredentialModel.TYPE.equals(credentialType);
  }

  @Override
  public boolean updateCredential(RealmModel arg0, UserModel arg1, CredentialInput arg2) {
    return false;
  }

  @Override
  public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
    return supportsCredentialType(credentialType);
  }

  @Override
  public boolean isValid(RealmModel realm, UserModel user, CredentialInput input) {
    if (!this.supportsCredentialType(input.getType())) {
      return false;
    }
    StorageId sid = new StorageId(user.getId());
    String username = sid.getExternalId();
    return repository.isValid(username, input.getChallengeResponse());
  }

  @Override
  public UserModel addUser(RealmModel arg0, String arg1) {
    return null;
  }

  @Override
  public boolean removeUser(RealmModel arg0, UserModel arg1) {
    return false;
  }
}
