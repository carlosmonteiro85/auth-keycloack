package br.com.ebix.keycloak.user;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.keycloak.common.util.MultivaluedHashMap;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.LegacyUserCredentialManager;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.SubjectCredentialManager;
import org.keycloak.models.UserModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapter;

import br.com.ebix.keycloak.user.external.Usuario;

public class UserAdapter extends AbstractUserAdapter{

  private final Usuario usuario;

  public UserAdapter(
      KeycloakSession session, RealmModel realm, ComponentModel model, Usuario usuario) {
    super(session, realm, model);
    this.storageId = new StorageId(storageProviderModel.getId(), usuario.getUsername());
    this.usuario = usuario;
  }

  @Override
  public String getUsername() {
    return usuario.getUsername();
  }

  @Override
  public String getFirstName() {
    return usuario.getFirstName();
  }
  
  @Override
  public String getLastName() {
    return usuario.getLastName();
  }

  @Override
  public String getEmail() {
    return usuario.getEmail();
  }

  @Override
  public Stream<String> getAttributeStream(String name) {
    Map<String, List<String>> attributes = getAttributes();
    return (attributes.containsKey(name)) ? attributes.get(name).stream() : Stream.empty();
  }

  @Override
  public String getFirstAttribute(String name) {
    List<String> list = getAttributes().getOrDefault(name, List.of());
    return list.isEmpty() ? null : list.get(0);
  }

  @Override
  public SubjectCredentialManager credentialManager() {
    return new LegacyUserCredentialManager(session, realm, this);
  }

  @Override
  public Map<String, List<String>> getAttributes() {
    MultivaluedHashMap<String, String> attributes = new MultivaluedHashMap<>();
    attributes.add(UserModel.USERNAME, getUsername());
    attributes.add(UserModel.EMAIL, getEmail());
    attributes.add(UserModel.FIRST_NAME, getFirstName());
    attributes.add(UserModel.LAST_NAME, getLastName());
    return attributes;
  }

  @Override
  protected Set<GroupModel> getGroupsInternal() {
    if (usuario.getRoles() != null) {
      return usuario.getGroups().stream().map(UserGroupModel::new).collect(Collectors.toSet());
    }
    return Set.of();
  }

  @Override
  protected Set<RoleModel> getRoleMappingsInternal() {
    if (usuario.getRoles() != null) {
      return usuario.getRoles().stream().map(roleName -> new UserRoleModel(roleName, realm)).collect(Collectors.toSet());
    }
    return Set.of();
  }


}
