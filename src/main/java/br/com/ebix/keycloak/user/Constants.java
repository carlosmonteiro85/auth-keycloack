package br.com.ebix.keycloak.user;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
  public static final String CONFIG_KEY_JDBC_DRIVER =
      "com.mysql.cj.jdbc.Driver";
  public static final String CONFIG_KEY_JDBC_URL =
        "jdbc:mysql://192.168.100.6:3306/PROFFY?createDatabaseIfNotExist=true&serverTimezone=GMT-3";
  public static final String CONFIG_KEY_DB_USERNAME = "root";
  public static final String CONFIG_KEY_DB_PASSWORD = "root";
  public static final String CONFIG_KEY_VALIDATION_QUERY = "validationQuery";
}
