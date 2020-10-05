# api-contracheques
Projeto de aplicação responsável por criar o extrato da folha salarial dos funcionários.

# Arquiterura
Aplicação Java 8 utilizando JPA e Spring Boot para expor as APIs. As APIs estão protegidas com o padrão OAuth2 e JWT.     
O banco de dados principal da aplicação será MySql e o banco de dados de testes unitários será o H2 em memória.   
Testes unitários feitos com JUnit e MockMvc.   
A aplicação será hospedada no Heroku.  
# Funcionaliades
[x] Login  
[x] Buscar funcionário por código  
[x] Incluir funcionário  
[x] Altertar funcionário  
[-] Documentação de APIs com Swagger (http://localhost:8080/swagger-ui.html)  
[] Buscar contracheque do funcionário  
