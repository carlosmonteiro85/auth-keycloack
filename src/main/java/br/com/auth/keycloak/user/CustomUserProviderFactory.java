package br.com.auth.keycloak.user;

import java.util.List;

import org.keycloak.component.ComponentModel;
import org.keycloak.component.ComponentValidationException;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.storage.UserStorageProviderFactory;
import org.keycloak.utils.StringUtil;

public class CustomUserProviderFactory implements UserStorageProviderFactory<CustomUserProvider>{
  
  public static final String PROVIDER_ID = "custom-user-provider";

  @Override
  public List<ProviderConfigProperty> getConfigProperties() {
    return ProviderConfigurationBuilder.create()
      .property(Constants.CONFIG_KEY_JDBC_URL, "JDBC URL", "URL do banco de dados", ProviderConfigProperty.STRING_TYPE,"", null)
      .property(Constants.CONFIG_KEY_DB_PASSWORD, "DataBase PASSWORD", "Senha do banco de dados", ProviderConfigProperty.STRING_TYPE,"", null)
      .property(Constants.CONFIG_KEY_DB_USERNAME, "DataBase USERNAME", "Usuario do banco de dados", ProviderConfigProperty.STRING_TYPE,"", null)
      .property(Constants.CONFIG_KEY_JDBC_DRIVER, "JDBC DRIVER", "Drive do banco de dados", ProviderConfigProperty.STRING_TYPE,"", null)
      .property(Constants.CONFIG_KEY_VALIDATION_QUERY, "Query Validation", "Query de validacao do banco de dados", ProviderConfigProperty.STRING_TYPE,"", null)
      .build();
  }

  @Override
  public String getHelpText() {
    return "Custom User Provider";
  }

  @Override
  public String getId() {
    return PROVIDER_ID;
  }

  @Override
  public void validateConfiguration(KeycloakSession session, RealmModel realm, ComponentModel config)
      throws ComponentValidationException {
    if(StringUtil.isBlank(config.get(Constants.CONFIG_KEY_JDBC_URL))
      || StringUtil.isBlank(config.get(Constants.CONFIG_KEY_DB_PASSWORD))
      || StringUtil.isBlank(config.get(Constants.CONFIG_KEY_DB_USERNAME))
      || StringUtil.isBlank(config.get(Constants.CONFIG_KEY_JDBC_DRIVER))
      || StringUtil.isBlank(config.get(Constants.CONFIG_KEY_VALIDATION_QUERY)) 
      ) { 
          throw new ComponentValidationException("Configuration not properly set, please verify.");
      }
    }

  @Override
  public CustomUserProvider create(KeycloakSession session, ComponentModel model) {
    return new CustomUserProvider(session, model);
  }
}
