# 🍽️ API Cloud Kitchen Users

API responsável pelo gerenciamento de usuários da plataforma **Cloud Kitchen**.

A aplicação permite:

* ✅ Cadastro de usuários
* ✅ Login
* ✅ Atualização de dados
* ✅ Recuperação e alteração de senha
* ✅ Remoção lógica de usuários
* ✅ Busca e paginação
* ✅ Integração com PostgreSQL e Docker

---

# 🚀 Tecnologias Utilizadas

| Tecnologia         | Descrição                     |
| ------------------ | ----------------------------- |
| Java 21            | Linguagem principal           |
| Spring Boot        | Framework principal           |
| Spring Web MVC     | APIs REST                     |
| Spring Data JPA    | Persistência de dados         |
| Jakarta Validation | Validação de dados            |
| Lombok             | Redução de boilerplate        |
| H2 Database        | Banco em memória              |
| PostgreSQL         | Banco relacional              |
| Swagger / OpenAPI  | Documentação da API           |
| Docker             | Containerização               |
| Docker Compose     | Orquestração                  |
| Maven              | Gerenciamento de dependências |

---

# 📁 Estrutura do Projeto

```bash
api-cloud-kitchen-users
├── src
│   ├── main
│   │   ├── java
│   │   │   └── br/com/fiap/tech/challenge/cloud/kitchen/user
│   │   │       ├── config
│   │   │       ├── controller
│   │   │       ├── exception
│   │   │       ├── model
│   │   │       ├── repository
│   │   │       ├── service
│   │   │       └── ApiCloudKitchenUsersApplication.java
│   │   └── resources
│   │       ├── application.yaml
│   │       └── data.sql
│   └── test
├── docker-compose.yml
├── Dockerfile
├── pom.xml
├── mvnw
└── mvnw.cmd
```

---

# ⚙️ Pré-requisitos

Antes de executar a aplicação, tenha instalado:

* Java 21
* Docker
* Docker Compose

> 💡 O projeto utiliza Maven Wrapper (`mvnw`), portanto não é necessário instalar o Maven globalmente.

---

# ▶️ Como Executar Localmente

## 1️⃣ Clonar o Repositório

```bash
git clone https://github.com/felipeobc/api-cloud-kitchen-users.git
cd api-cloud-kitchen-users
```

---

## 2️⃣ Executar com Maven Wrapper

### Linux / macOS

```bash
./mvnw spring-boot:run
```

### Windows

```bash
mvnw.cmd spring-boot:run
```

---

## 3️⃣ Executar com Docker Compose

```bash
docker compose up
```

---

# 🐳 Docker Compose

A aplicação sobe junto com o PostgreSQL.

## Serviços disponíveis

| Serviço    | URL                   |
| ---------- | --------------------- |
| API        | http://localhost:8080/swagger-ui/index.html#/ |
| PostgreSQL | localhost:5432        |

---

## Configuração PostgreSQL

| Campo    | Valor               |
| -------- | ------------------- |
| Database | cloud_kitchen_users |
| Usuário  | cloud_kitchen       |
| Senha    | cloud_kitchen_pass  |

---

## Parar os containers

```bash
docker compose down
```

## Remover containers + volumes

```bash
docker compose down -v
```

---

# 🗄️ Banco de Dados Local (H2)

A aplicação utiliza H2 em memória por padrão.

## Configurações

| Campo      | Valor                            |
| ---------- | -------------------------------- |
| JDBC URL   | jdbc:h2:mem:testdb               |
| Usuário    | sa                               |
| Senha      | test                             |
| Console H2 | http://localhost:8080/h2-console |

---

# 📚 Documentação da API

## Swagger UI

```text
http://localhost:8080/swagger-ui.html
```

## OpenAPI JSON

```text
http://localhost:8080/v3/api-docs
```

---

# 🔗 Endpoints Principais

## Base Path

```text
/api/v1/users
```

| Método | Endpoint              | Descrição        |
| ------ | --------------------- | ---------------- |
| POST   | `/login`              | Realiza login    |
| GET    | `/`                   | Lista usuários   |
| GET    | `/search?name={nome}` | Busca usuários   |
| POST   | `/`                   | Cria usuário     |
| PUT    | `/{id}`               | Atualiza usuário |
| DELETE | `/{id}`               | Remove usuário   |
| PATCH  | `/{id}/password`      | Altera senha     |
| PATCH  | `/recover-password`   | Recupera senha   |

---

# 🧪 Exemplos de Requisições

## Criar Usuário

```bash
curl -X POST http://localhost:8080/api/v1/users \
-H "Content-Type: application/json" \
-d '{
  "name": "João Silva",
  "email": "joao@email.com",
  "login": "joaosilva",
  "password": "123456",
  "address": "Rua das Flores, 100",
  "owner": true
}'
```

---

## Login

```bash
curl -X POST http://localhost:8080/api/v1/users/login \
-H "Content-Type: application/json" \
-d '{
  "login": "joaosilva",
  "password": "123456"
}'
```

---

## Listar Usuários

```bash
curl http://localhost:8080/api/v1/users
```

---

## Listar com Paginação

```bash
curl "http://localhost:8080/api/v1/users?page=0&size=10"
```

---

## Buscar Usuário por Nome

```bash
curl "http://localhost:8080/api/v1/users/search?name=João"
```

---

## Atualizar Usuário

```bash
curl -X PUT http://localhost:8080/api/v1/users/1 \
-H "Content-Type: application/json" \
-d '{
  "name": "João Atualizado",
  "email": "joao.atualizado@email.com",
  "login": "joaoatualizado",
  "address": "Av. Paulista, 1000",
  "owner": true
}'
```

---

## Alterar Senha

```bash
curl -X PATCH http://localhost:8080/api/v1/users/1/password \
-H "Content-Type: application/json" \
-d '{
  "currentPassword": "123456",
  "newPassword": "novaSenha123"
}'
```

---

## Recuperar Senha

```bash
curl -X PATCH http://localhost:8080/api/v1/users/recover-password \
-H "Content-Type: application/json" \
-d '{
  "email": "joao@email.com"
}'
```

---

## Remover Usuário

```bash
curl -X DELETE http://localhost:8080/api/v1/users/1
```

---

# ✅ Executando os Testes

## Linux / macOS

```bash
./mvnw test
```

## Windows

```bash
mvnw.cmd test
```

---

# 📦 Gerando o Build da Aplicação

## Linux / macOS

```bash
./mvnw package
```

## Windows

```bash
mvnw.cmd package
```

O artefato `.jar` será gerado em:

```text
target/
```

---

# 🌎 Variáveis de Ambiente

| Variável                            | Descrição            | Valor padrão                                        |
| ----------------------------------- | -------------------- | --------------------------------------------------- |
| SPRING_DATASOURCE_URL               | URL do banco         | jdbc:postgresql://postgres:5432/cloud_kitchen_users |
| SPRING_DATASOURCE_USERNAME          | Usuário do banco     | cloud_kitchen                                       |
| SPRING_DATASOURCE_PASSWORD          | Senha do banco       | cloud_kitchen_pass                                  |
| SPRING_DATASOURCE_DRIVER_CLASS_NAME | Driver JDBC          | org.postgresql.Driver                               |
| SPRING_JPA_HIBERNATE_DDL_AUTO       | Estratégia do schema | update                                              |
| SPRING_SQL_INIT_MODE                | Inicialização SQL    | always                                              |

---

# 📄 Licença

Este projeto utiliza a licença **Apache 2.0**.

---

# 👨‍💻 Autor

Projeto desenvolvido para o Tech Challenge FIAP — Cloud Kitchen.
