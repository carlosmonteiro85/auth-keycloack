package br.com.auth.keycloak.user;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleContainerModel;
import org.keycloak.models.RoleModel;
import org.keycloak.storage.ReadOnlyException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRoleModel implements RoleModel{

  private final String nome;
  private final RealmModel realm;

  @Override
  public void addCompositeRole(RoleModel roleModel) {
    throw new ReadOnlyException("role is read only");   
  }

  @Override
  public Stream<String> getAttributeStream(String s) {
    return Stream.empty();
  }

  @Override
  public Map<String, List<String>> getAttributes() {
    return Map.of();
  }

  @Override
  public Stream<RoleModel> getCompositesStream(String s, Integer integer, Integer integer1) {
    return Stream.empty();
  }

  @Override
  public RoleContainerModel getContainer() {
    return realm;
  }

  @Override
  public String getContainerId() {
    return realm.getId();
  }
  
  @Override
  public String getDescription() {
    return null;
  }

  @Override
  public String getId() {
    return nome;
  }

  @Override
  public String getName() {
    return nome;
  }

  @Override
  public boolean hasRole(RoleModel roleModel) {
    return this.equals(roleModel) || this.nome.equals(roleModel.getName());
  }

  @Override
  public boolean isClientRole() {
    return false;
  }

  @Override
  public boolean isComposite() {
    return false;
  }
  
  @Override
  public void removeAttribute(String arg0) {
   throw new ReadOnlyException("role is read only"); 
  }

  @Override
  public void removeCompositeRole(RoleModel roleModel) {
   throw new ReadOnlyException("role is read only"); 
  }

  @Override
  public void setAttribute(String s, List<String> list) {
   throw new ReadOnlyException("role is read only"); 
  }
  
  @Override
  public void setDescription(String s) {
   throw new ReadOnlyException("role is read only"); 
  }

  @Override
  public void setName(String s) {
   throw new ReadOnlyException("role is read only"); 
  }

  @Override
  public void setSingleAttribute(String s, String s1) {
   throw new ReadOnlyException("role is read only"); 
  }


  
}
