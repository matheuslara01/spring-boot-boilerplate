# Spring Boot Boilerplate - JWT Authentication, MySQL, Swagger, JUnit

https://medium.com/@mateus.delara65/spring-boot-boilerplate-jwt-authentication-mysql-swagger-junit-56bd75bef708

Este projeto é um boilerplate (modelo inicial) em Spring Boot para REST APIs que inclui configurações básicas para autenticação com JWT (JSON Web Token), integração com banco de dados MySQL, documentação de API com Swagger, testes unitários com JUnit, cadastro básico de usuário, tratamento de exceções através do Exception Handler e logs utilizando o SLF4J.

![Spring Boot](https://img.icons8.com/color/48/000000/spring-logo.png)
![JWT](https://img.icons8.com/color/48/java-web-token.png)
![MySQL](https://img.icons8.com/color/48/mysql-logo.png)
![Swagger](https://img.icons8.com/color/48/cloud-function.png) 

## Pré-requisitos

Antes de executar o projeto, certifique-se de ter o seguinte instalado:

- Java Development Kit (JDK) 8 ou superior
- Maven
- MySQL Server

## Configuração

1. Clone este repositório em sua máquina local:
- ``git clone 'https://github.com/matheuslara01/spring-boot-boilerplate.git'``

2. Navegue até o diretório do projeto:
- ``cd spring-boot-boilerplate``

3. Crie um banco de dados no MySQL para o projeto.

4. No arquivo `application.properties` (localizado em `src/main/resources`), atualize as seguintes propriedades de acordo com sua configuração do MySQL:
- ``spring.datasource.url=jdbc:mysql://localhost:3306/nome_do_banco_de_dados``
- ``spring.datasource.username=seu_usuario``
- ``spring.datasource.password=sua_senha``

5. Execute o seguinte comando para compilar e executar o projeto:
- ``mvn spring-boot:run``

## Uso

Após iniciar o projeto, você pode acessar a documentação da API usando o Swagger:

``http://localhost:8080/base/swagger-ui/index.html``

Para autenticar e acessar as rotas protegidas, você precisará gerar um token JWT de autenticação. Para isso, faça uma solicitação POST para o endpoint `/base/auth` com as credenciais de usuário e senha. O token JWT será retornado na resposta. Em seguida, inclua o token JWT nas solicitações subsequentes no cabeçalho `Authorization`.

## Cadastro de Usuário

O projeto inclui um cadastro básico de usuário com as operações de criação, leitura, atualização e exclusão (CRUD). O endpoint para as operações de usuário é `/base/user`.

## Testes

O projeto inclui testes unitários utilizando JUnit. Para executar os testes, execute o seguinte comando:

- ``mvn test``

Os testes são executados e os resultados serão exibidos no console.

## Tratamento de Exceções

O projeto possui um Exception Handler para tratar exceções de forma centralizada. Isso permite retornar respostas padronizadas e adequadas em caso de erros ou exceções ocorrerem durante a execução da API.

## Logs

O projeto utiliza o SLF4J para logs. Os logs são registrados e podem ser configurados de acordo com as necessidades do projeto.

## Contribuição

Sinta-se à vontade para contribuir com melhorias para este projeto. Fique à vontade para abrir problemas (issues) ou enviar pull requests com suas sugestões.

---

## Licença

Este projeto está licenciado sob a [MIT License](https://opensource.org/licenses/MIT).
