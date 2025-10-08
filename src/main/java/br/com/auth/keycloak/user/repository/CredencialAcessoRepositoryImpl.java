package br.com.auth.keycloak.user.repository;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.keycloak.component.ComponentModel;

import br.com.auth.keycloak.user.dominio.CredencialAcesso;
import br.com.auth.keycloak.user.exception.CredencialException;
import br.com.auth.keycloak.user.util.DBUtil;

public class CredencialAcessoRepositoryImpl implements CredencialAcessoRepository {

  private final ComponentModel model;

  public CredencialAcessoRepositoryImpl(ComponentModel model) {
    this.model = model;
  }

  @Override
  public CredencialAcesso getUserByEmail(String email) {
    String sql = "select ID, USERNAME, EMAIL from credencial_acesso where EMAIL = ?";
    try (Connection connection = DBUtil.getConnection(model);
        PreparedStatement st = connection.prepareStatement(sql);) {

      st.setString(1, email);
      st.execute();
      ResultSet rs = st.getResultSet();
      if (rs.next()) {
        return mapUser(rs);
      } else {
        return null;
      }
    } catch (SQLException e) {
      throw new CredencialException(e.getMessage());
    }
  }

  @Override
  public CredencialAcesso getUserByUsername(String username) {
    String sql = "select ID, USERNAME, EMAIL from credencial_acesso where USERNAME = ?";
    try (Connection connection = DBUtil.getConnection(model);
        PreparedStatement st = connection.prepareStatement(sql);) {

      st.setString(1, username);
      st.execute();
      ResultSet rs = st.getResultSet();
      if (rs.next()) {
        return mapUser(rs);
      } else {
        return null;
      }
    } catch (SQLException e) {
      throw new CredencialException(e.getMessage());
    }
  }

  @Override
  public Integer getUsersCount() {
    int count = 0;
    try (Connection c = DBUtil.getConnection(model);
        Statement st = c.createStatement(); ) {
      st.execute("select count(*) from credencial_acesso");
      ResultSet rs = st.getResultSet();
      rs.next();
      count = rs.getInt(1);
    } catch (SQLException ex) {
      throw new CredencialException("Database error:" + ex.getMessage(), ex);
    }
    return count;
  }

  @Override
  public List<CredencialAcesso> getUsersStream(Integer firstResult, Integer maxResults) {
    String sql =
        "select ID, USERNAME, EMAIL"
            + " from credencial_acesso order by USERNAME OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    try (Connection c = DBUtil.getConnection(this.model);
        PreparedStatement st = c.prepareStatement(sql);) {
      st.setInt(1, firstResult);
      st.setInt(2, maxResults);
      st.execute();
      ResultSet rs = st.getResultSet();
      List<CredencialAcesso> users = new ArrayList<>();
      while (rs.next()) {
        users.add(mapUser(rs));
      }
      return users;
    } catch (SQLException ex) {
      throw new CredencialException("Database error:" + ex.getMessage(), ex);
    }
  }

  public List<CredencialAcesso> searchForAllUserStream(
      String search, Integer firstResult, Integer maxResults) {
    List<CredencialAcesso> users = new ArrayList<>();
    String sql =
        "select ID, USERNAME, EMAIL from credencial_acesso order by"
            + " USERNAME OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    try (Connection c = DBUtil.getConnection(model);
        PreparedStatement st = c.prepareStatement(sql);) {
      st.setInt(1, firstResult);
      st.setInt(2, maxResults);
      st.execute();
      ResultSet rs = st.getResultSet();
      while (rs.next()) {
        users.add(mapUser(rs));
      }
      return users;
    } catch (SQLException ex) {
      throw new CredencialException("Database error:" + ex.getMessage(), ex);
    }
  }

  @Override
  public List<CredencialAcesso> searchForUserStream(String search, Integer firstResult, Integer maxResults) {
    List<CredencialAcesso> users = new ArrayList<>();
    String sql =
        "select ID, USERNAME, EMAIL from credencial_acesso here"
            + " USERNAME like ? order by USERNAME OFFSET ? ROWS FETCH NEXT ? ROWS"
            + " ONLY";

    try (Connection c = DBUtil.getConnection(model);
        PreparedStatement st = c.prepareStatement(sql);) {
      st.setString(1, search);
      st.setInt(2, firstResult);
      st.setInt(3, maxResults);
      st.execute();
      ResultSet rs = st.getResultSet();
      while (rs.next()) {
        users.add(mapUser(rs));
      }
      return users;
    } catch (SQLException ex) {
      throw new CredencialException("Database error:" + ex.getMessage(), ex);
    }
  }

  @Override
  public boolean isValid(String username, String password) {
    String sql = "select password from credencial_acesso where username = ?";
    String senhaCriptografada = criptografarString(password);
    try (Connection c = DBUtil.getConnection(model);
        PreparedStatement st = c.prepareStatement(sql);) {
      st.setString(1, username);
      st.execute();
      ResultSet rs = st.getResultSet();
      if (rs.next()) {
        String pwd = rs.getString(1);
        return pwd.equals(senhaCriptografada);
      } else {
        return false;
      }
    } catch (SQLException ex) {
      throw new CredencialException("Database error:" + ex.getMessage(), ex);
    }
  }

  private CredencialAcesso mapUser(ResultSet rs) throws SQLException {
    long id = rs.getLong("ID");
    return CredencialAcesso.builder()
        .id(id)
        .username(rs.getString("USERNAME"))
        .email(rs.getString("EMAIL"))
        .roles(getRolesByUserId(id))
        .build();
  }

  private List<String> getRolesByUserId(Long userId) {
    String sql = "SELECT role FROM credencial_role WHERE credencial_id = ?";
    List<String> roles = new ArrayList<>();
    try (Connection c = DBUtil.getConnection(model);
        PreparedStatement st = c.prepareStatement(sql)) {
      st.setLong(1, userId);
      ResultSet rs = st.executeQuery();
      while (rs.next()) {
        roles.add(rs.getString("ROLE"));
      }
    } catch (SQLException e) {
      throw new CredencialException("Erro ao buscar roles: " + e.getMessage(), e);
    }
    return roles;
  }

  // private List<String> getGruposByUserId(Long userId) {
  //   String sql = "SELECT GRUPO FROM CREDENCIAL_GRUPO WHERE CREDENCIAL_ID = ?";
  //   List<String> grupos = new ArrayList<>();
  //   try (Connection c = DBUtil.getConnection(model);
  //       PreparedStatement st = c.prepareStatement(sql)) {
  //     st.setLong(1, userId);
  //     ResultSet rs = st.executeQuery();
  //     while (rs.next()) {
  //       grupos.add(rs.getString("GRUPO"));
  //     }
  //   } catch (SQLException e) {
  //     throw new CredencialException("Erro ao buscar grupos: " + e.getMessage(), e);
  //   }
  //   return grupos;
  // }

  private String criptografarString(final String strOriginal) {
    try {
      final byte[] plainText = strOriginal.getBytes("UTF-8");
      MessageDigest md1 = MessageDigest.getInstance("SHA-512");
      byte[] texto = md1.digest(plainText);
      return new String(Base64.encodeBase64(texto));
    } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
      throw new CredencialException(ex.getMessage(), ex);
    }
  }

  public ComponentModel getModel() {
    return model;
  }
}
