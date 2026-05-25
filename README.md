# RequestHub

Sistema de gerenciamento de solicitações internas desenvolvido com Java, Spring Boot e Angular.

## Sobre o projeto

O RequestHub é uma aplicação voltada para gerenciamento de solicitações e fluxo interno de atendimento, permitindo controle de usuários, autenticação, autorização e gerenciamento de status das solicitações.

O projeto foi desenvolvido com foco em organização de arquitetura, separação de responsabilidades e boas práticas utilizadas em aplicações corporativas.

---

## Tecnologias

### Backend
- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- Validation
- Lombok
- H2 Database

### Frontend
- Angular
- TypeScript
- Angular Material

---

## Funcionalidades

- Cadastro de solicitações
- Controle de status
- Autenticação e autorização
- Perfis de usuário e administrador
- Validação de dados
- API REST
- Fluxo de gerenciamento de solicitações

---

## Estrutura do projeto

O backend segue uma arquitetura organizada por domínio, separando responsabilidades entre:

```text
controller
service
repository
dto
domain
security
config
exception
```

---

## Segurança

A autenticação e autorização serão implementadas utilizando Spring Security, com controle de acesso baseado em perfis de usuário.

---

## Executando o projeto

### Backend

```bash
./mvnw spring-boot:run
```

### Frontend

```bash
npm install
ng serve
```

---

## Banco de dados

Durante o desenvolvimento, o projeto utiliza H2 Database para testes locais.

Console H2:

```text
http://localhost:8080/h2-console
```

---

## Objetivo

O objetivo deste projeto é aplicar conceitos modernos de desenvolvimento backend e frontend, utilizando práticas comuns em aplicações corporativas e arquitetura organizada por domínio.

---

## Autor

Luciano
