# api-contracheques
Projeto de aplicação responsável por criar o extrato da folha salarial dos funcionários.
Para acessar a versão de demonstração acesse [https://frontend-api-contracheques.herokuapp.com/](https://frontend-api-contracheques.herokuapp.com/)   
Usuário: everton.mfernandes@gmail.com   
Senha: 1234  
O repositório do frontend está [aqui](https://github.com/evertonmateusf/frontend-api-contracheques)  

# Arquitetura
Aplicação Java 8 utilizando JPA e Spring Boot para expor as APIs. As APIs estão protegidas com o padrão OAuth2 e JWT.     
O banco de dados principal da aplicação será MySql e o banco de dados de testes unitários será o H2 em memória.   
Testes unitários feitos com JUnit e MockMvc.   
A aplicação será hospedada no Heroku.  
# Variáveis de sistema para acesso ao DB
CLEARDB_DATABASE_URL ->Exemplo: `mysql://<usuario>:<senha>@<host>:<port>/<database_name>?reconnect=true&serverTimezone=America/Sao_Paulo`
# Funcionalidades
[x] Login  
[x] Buscar funcionário por código  
[x] Incluir funcionário  
[x] Altertar funcionário  
[x] Documentação de APIs com Swagger (https://api-contracheques.herokuapp.com/swagger-ui.html)  
[x] Buscar contracheque do funcionário  
[x] Hospedagem da aplicação no Heroku (https://api-contracheques.herokuapp.com/)  
[x] Deploy automatizado usando o Travis-CI
