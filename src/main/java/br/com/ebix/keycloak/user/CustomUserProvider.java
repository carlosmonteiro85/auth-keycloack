package br.com.ebix.keycloak.user;

import br.com.ebix.keycloak.user.external.DBUtil;
import br.com.ebix.keycloak.user.external.Usuario;
import br.com.ebix.keycloak.user.external.UsuarioRepository;
import br.com.ebix.keycloak.user.external.UsuarioRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.codec.binary.Base64;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;
import org.keycloak.storage.user.UserRegistrationProvider;

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
  private final UsuarioRepository repository;

  protected Map<String, UserModel> loadedUsers = new HashMap<>();

  public CustomUserProvider(KeycloakSession session, ComponentModel model) {
    this.session = session;
    this.model = model;
    this.repository = new UsuarioRepositoryImpl(model);
  }

  @Override
  public void close() {}

  @Override
  public UserModel getUserByEmail(RealmModel realm, String email) {
    log.info("[I48] getUserByEmail({})", email);
    UserModel adapter = loadedUsers.get(email);
    if (adapter == null) {
      Usuario usuario = repository.getUserByEmail(email);
      adapter = new UserAdapter(session, realm, model, usuario);
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
      Usuario usuario = repository.getUserByUsername(username);
      adapter = new UserAdapter(session, realm, model, usuario);
      loadedUsers.put(username, adapter);
    }
    return adapter;
  }

  @Override
  public Stream<UserModel> getUsersStream(RealmModel realm) {
    log.info("[I113] getUsers: realm={}", realm.getName());
    return getUsersStream(realm, 1, 5000);
  }

  @Override
  public Stream<UserModel> getUsersStream(RealmModel realm, Integer firstResult, Integer maxResults) {
    log.info("[I113] getUsers: realm={}", realm.getName());
    List<Usuario> usuarios = repository.getUsersStream(firstResult, maxResults);
    return usuarios.stream().map(usuario -> new UserAdapter(session, realm, model, usuario));
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
        .map(usuario -> new UserAdapter(session, realm, model, usuario));
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
        .map(usuario -> new UserAdapter(session, realm, model, usuario));
  }

  @Override
  public void disableCredentialType(RealmModel arg0, UserModel arg1, String arg2) {
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
