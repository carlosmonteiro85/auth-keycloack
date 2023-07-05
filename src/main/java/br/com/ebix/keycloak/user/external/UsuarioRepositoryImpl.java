package br.com.ebix.keycloak.user.external;

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

public class UsuarioRepositoryImpl implements UsuarioRepository {

  private final ComponentModel model;

  public UsuarioRepositoryImpl(ComponentModel model) {
    this.model = model;
  }

  @Override
  public Usuario getUserByEmail(String email) {
    String sql = "select ID, USERNAME, EMAIL from USUARIO where EMAIL = ?";
    try (Connection connection = DBUtil.getConnection(model);
        PreparedStatement st = connection.prepareStatement(sql); ) {

      st.setString(1, email);
      st.execute();
      ResultSet rs = st.getResultSet();
      if (rs.next()) {
        return mapUser(rs);
      } else {
        return null;
      }
    } catch (SQLException e) {
      throw new UsuarioException(e.getMessage());
    }
  }

  @Override
  public Usuario getUserByUsername(String username) {
    String sql = "select ID, USERNAME, EMAIL from USUARIO where USERNAME = ?";
    try (Connection connection = DBUtil.getConnection(model);
        PreparedStatement st = connection.prepareStatement(sql); ) {

      st.setString(1, username);
      st.execute();
      ResultSet rs = st.getResultSet();
      if (rs.next()) {
        return mapUser(rs);
      } else {
        return null;
      }
    } catch (SQLException e) {
      throw new UsuarioException(e.getMessage());
    }
  }

  @Override
  public Integer getUsersCount() {
    int count = 0;
    try (Connection c = DBUtil.getConnection(model);
        Statement st = c.createStatement(); ) {
      st.execute("select count(*) from USUARIO");
      ResultSet rs = st.getResultSet();
      rs.next();
      count = rs.getInt(1);
    } catch (SQLException ex) {
      throw new UsuarioException("Database error:" + ex.getMessage(), ex);
    }
    return count;
  }

  @Override
  public List<Usuario> getUsersStream(Integer firstResult, Integer maxResults) {
    String sql =
        "select ID, USERNAME, EMAIL"
            + " from USUARIO order by USERNAME OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    try (Connection c = DBUtil.getConnection(this.model);
        PreparedStatement st = c.prepareStatement(sql); ) {
      st.setInt(1, firstResult);
      st.setInt(2, maxResults);
      st.execute();
      ResultSet rs = st.getResultSet();
      List<Usuario> users = new ArrayList<>();
      while (rs.next()) {
        users.add(mapUser(rs));
      }
      return users;
    } catch (SQLException ex) {
      throw new UsuarioException("Database error:" + ex.getMessage(), ex);
    }
  }

  public List<Usuario> searchForAllUserStream(
      String search, Integer firstResult, Integer maxResults) {
    List<Usuario> users = new ArrayList<>();
    String sql =
        "select ID, USERNAME, EMAIL from USUARIO order by"
            + " USERNAME OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    try (Connection c = DBUtil.getConnection(model);
        PreparedStatement st = c.prepareStatement(sql); ) {
      st.setInt(1, firstResult);
      st.setInt(2, maxResults);
      st.execute();
      ResultSet rs = st.getResultSet();
      while (rs.next()) {
        users.add(mapUser(rs));
      }
      return users;
    } catch (SQLException ex) {
      throw new UsuarioException("Database error:" + ex.getMessage(), ex);
    }
  }

  @Override
  public List<Usuario> searchForUserStream(String search, Integer firstResult, Integer maxResults) {
    List<Usuario> users = new ArrayList<>();
    String sql =
        "select ID, USERNAME, EMAIL from USUARIO here"
            + " USERNAME like ? order by USERNAME OFFSET ? ROWS FETCH NEXT ? ROWS"
            + " ONLY";

    try (Connection c = DBUtil.getConnection(model);
        PreparedStatement st = c.prepareStatement(sql); ) {
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
      throw new UsuarioException("Database error:" + ex.getMessage(), ex);
    }
  }

  @Override
  public boolean isValid(String username, String password) {
    String sql = "select PASSWORD from USUARIO where USERNAME = ?";
    String senhaCriptografada = criptografarString(password);
    try (Connection c = DBUtil.getConnection(model);
        PreparedStatement st = c.prepareStatement(sql); ) {
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
      throw new UsuarioException("Database error:" + ex.getMessage(), ex);
    }
  }

  private Usuario mapUser(ResultSet rs) {
    try {
      Usuario usuario = new Usuario();
      usuario.setId(rs.getLong("ID"));
      usuario.setUsername(rs.getString("USERNAME"));
      usuario.setEmail(rs.getString("EMAIL"));
      return usuario;
    } catch (SQLException ex) {
      throw new UsuarioException(ex.getMessage());
    }
  }

  private String criptografarString(final String strOriginal) {
    try {
      final byte[] plainText = strOriginal.getBytes("UTF-8");
      // Transforma o digest em uma String legível
      MessageDigest md1 = MessageDigest.getInstance("SHA-512");
      byte[] texto = md1.digest(plainText);
      final String strCripto = new String(Base64.encodeBase64(texto));
      return strCripto;
    } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
      throw new UsuarioException(ex.getMessage(), ex);
    }
  }

  public ComponentModel getModel() {
    return model;
  }
}
