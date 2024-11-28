# Custon provider com Keycloak
Este projeto implementa um Custom Provider para autenticação utilizando o Keycloak com JWT.

 # Tecnologias e ferramentas usadas
* Keycloak 21: Gerenciador de autenticação e autorização.
* Docker: Utilizado para executar o container do Custom Provider.
* Java 11: Linguagem utilizada no desenvolvimento do código fonte.
* MySQL 8: Banco de dados utilizado para armazenar as informações de credenciais.

# Banco utilizado é o Mysql 8
O banco de dados utilizado no desenvolvimento é o MySQL 8. Antes de executar o projeto, siga os passos abaixo:

Na raiz do projeto, crie o banco de dados chamado auth-keycloak.
Execute o script SQL init.sql localizado na raiz do projeto para criar as tabelas de credenciais e adicionar um usuário de teste.

## Configuracao da base de dados
*Na classe Constants, insira as informações de conexão com o banco.
*No arquivo realm-export.json, no objeto org.keycloak.storage.UserStorageProvider, insira as configurações de conexão do banco.

* Obs: neste arquivo tem os dados do realm que será importado, nome do realm, url de redirect, roles.... e várias configurações que pode ser alterada de acordo com a necessidade.

# Executando o projeto
* Certifique-se de que as configurações adicionais foram realizadas.
* gerar o artefato ".jar" com o "mvn clean install", e toda vez que houver alteração no codigo o artefato tem que ser gerado novamente.
* Na raiz do projeto, executar o comando docker-compose up, para realizar a execução do projeto

# Login
 Após a execução do projeto, a tela pode ser chamada pela url: 
 http://localhost:8082/realms/Proffy/protocol/openid-connect/auth?client_id=proffy&redirect_uri=<adicionar a url de redirect caso o login for correto>

## Chamada Login
 curl --location 'http://localhost:8082/realms/Proffy/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=proffy' \
--data-urlencode 'username=<adicionar username>' \
--data-urlencode 'password=<adicionara senha>' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'scope=openid'


Obs: a coleção de endpoint esta disponivel no arquivo 'auth_keycloak.postman_collection.json'

## Chamada Refresh token 

curl --location 'http://localhost:8082/realms/Proffy/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=proffy' \
--data-urlencode 'grant_type=refresh_token' \
--data-urlencode 'refresh_token=<Adicionar refresh_token>'


# logout
O logout pode ser feito atravez da url:  
http://localhost:8082/realms/Proffy/protocol/openid-connect/logout
