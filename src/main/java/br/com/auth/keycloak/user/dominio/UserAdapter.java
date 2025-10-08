package br.com.auth.keycloak.user.dominio;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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

public class UserAdapter extends AbstractUserAdapter {

  private final CredencialAcesso credencialAcesso;

  public UserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, CredencialAcesso credencialAcesso) {
    super(session, realm, model);
    this.storageId = new StorageId(storageProviderModel.getId(), credencialAcesso.getUsername());
    this.credencialAcesso = credencialAcesso;
  }

  @Override
  public String getUsername() {
    return credencialAcesso.getUsername();
  }

  @Override
  public String getFirstName() {
    return credencialAcesso.getFirstName();
  }

  @Override
  public String getLastName() {
    return credencialAcesso.getLastName();
  }

  @Override
  public String getEmail() {
    return credencialAcesso.getEmail();
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
    if (credencialAcesso.getRoles() != null && Objects.nonNull(credencialAcesso.getGroups())) {
      return credencialAcesso.getGroups().stream().map(UserGroupModel::new).collect(Collectors.toSet());
    }
    return Set.of();
  }

  @Override
  protected Set<RoleModel> getRoleMappingsInternal() {
    if (credencialAcesso.getRoles() != null) {
      return credencialAcesso.getRoles().stream().map(roleName -> new UserRoleModel(roleName, realm))
          .collect(Collectors.toSet());
    }
    return Set.of();
  }

  @Override
  public Stream<RoleModel> getRealmRoleMappingsStream() {
    return credencialAcesso.getRoles().stream()
        .map(roleName -> {
          RoleModel role = realm.getRole(roleName);
          if (role == null) {
            role = realm.addRole(roleName);
          }
          return role;
        });
  }

  /* 
  @Override
  public Stream<GroupModel> getGroupsStream() {
    return credencialAcesso.getGrupos().stream()
        .map(groupName -> {
          GroupModel group = realm.getGroupsStream()
              .filter(g -> g.getName().equals(groupName))
              .findFirst()
              .orElseGet(() -> realm.createGroup(groupName));
          return group;
        });
  }
  */

  @Override
  public boolean hasRole(RoleModel role) {
    return credencialAcesso.getRoles() != null && credencialAcesso.getRoles().contains(role.getName());
  }
}
