# RequestHub

Sistema full stack para gerenciamento de solicitações internas, com fluxo de status controlado, autenticação e autorização por perfil.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-Framework-brightgreen)
![Angular](https://img.shields.io/badge/Angular-21-red)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED)
![AWS EC2](https://img.shields.io/badge/AWS-EC2-orange)
![Status](https://img.shields.io/badge/status-primeira%20vers%C3%A3o%20conclu%C3%ADda-brightgreen)

## Sobre o Projeto

O **RequestHub** é uma aplicação full stack que representa um fluxo simples de atendimento: um usuário registra uma solicitação e acompanha sua evolução até a conclusão.

O projeto conta com backend em **Java + Spring Boot**, frontend em **Angular**, persistência em **PostgreSQL**, containerização com **Docker/Docker Compose** e deploy em uma instância **AWS EC2**.

O sistema possui dois perfis principais:

| Perfil | Responsabilidade |
|---|---|
| `SOLICITANTE` | Cria e altera suas próprias solicitações |
| `ADMIN` | Gerencia administrativamente as solicitações |

## Fluxo de Status

```
ABERTA → EM_ANDAMENTO → FINALIZADA
```

As transições são controladas por regras de negócio, impedindo que uma solicitação avance etapas ou seja alterada/excluída fora das condições permitidas.

| Status | Pode alterar | Pode excluir |
|---|---|---|
| ABERTA | ✅ | ✅ |
| EM_ANDAMENTO | ✅ | ❌ |
| FINALIZADA | ❌ | ❌ |

## Arquitetura

```
Usuário → Angular → (HTTP) → Spring Boot → Spring Data JPA → PostgreSQL
```

```
RequestHub/
├── backend/        # API REST em Spring Boot
├── frontend/       # SPA em Angular servida via Nginx
└── docker-compose.yml
```

### Backend

Organização orientada a domínio:

```
com/RequestHub/request_hub/
├── infrastructure/
│   ├── exception/      # BusinessException, NotFoundException, GlobalExceptionHandler
│   └── security/       # SecurityConfig
└── solicitacao/
    ├── controller/
    ├── domain/          # Solicitacao, StatusSolicitacao
    ├── dto/
    ├── repository/
    └── service/
```

### Frontend

Angular 21 + TypeScript + RxJS + Angular Router/Forms, com testes via Vitest, compilado e servido por Nginx em produção.

## Funcionalidades

- Criar, consultar, alterar e excluir solicitações
- Fluxo controlado de status (`ABERTA` → `EM_ANDAMENTO` → `FINALIZADA`)
- Validação de dados de entrada (Jakarta Bean Validation)
- Autenticação com Spring Security (HTTP Basic)
- Autorização baseada em roles (`@PreAuthorize`)
- DTOs de entrada/saída para desacoplar API e domínio
- Tratamento global de exceções
- Testes automatizados no backend e frontend

## Endpoints

| Método | Rota | Perfil |
|---|---|---|
| `POST` | `/solicitacoes` | SOLICITANTE |
| `GET` | `/solicitacoes` | ADMIN |
| `PUT` | `/solicitacoes/{id}` | SOLICITANTE |
| `PUT` | `/solicitacoes/{id}/status` | ADMIN |
| `DELETE` | `/solicitacoes/{id}` | ADMIN |
| `GET` | `/solicitacoes/auth/me` | Autenticado |

## Tecnologias

**Backend:** Java, Spring Boot, Spring Web MVC, Spring Data JPA, Hibernate, Spring Security, Jakarta Validation, Lombok, H2, PostgreSQL, Maven

**Frontend:** Angular 21, TypeScript, RxJS, Angular Router, Angular Forms, Vitest

**Infraestrutura:** Docker, Docker Compose, Nginx, AWS EC2

## Como Executar

### Pré-requisitos

- **Local:** Java 21, Maven, Node.js, npm, Angular CLI
- **Docker:** Docker e Docker Compose

### Backend

```bash
cd backend
./mvnw spring-boot:run      # Linux/macOS
mvnw.cmd spring-boot:run    # Windows
```

### Frontend

```bash
cd frontend/requesthub-web-spa/requesthub-web
npm install
npm start
```

### Com Docker Compose

```bash
docker compose up --build       # executar
docker compose up --build -d    # em segundo plano
docker compose down              # parar
docker compose down -v           # parar e remover volumes
```

## Banco de Dados

- **Desenvolvimento e testes:** H2
- **Docker / produção:** PostgreSQL (com volume para persistência)

## Roadmap

- [ ] Evolução da cobertura de testes
- [ ] Melhorias de segurança
- [ ] CI/CD
- [ ] Melhorias de UX/UI
- [ ] Deploy automatizado

## Autor

Desenvolvido por **Luciano**

[GitHub](https://github.com/)
