# RequestHub — Documentação do Back-end (Java / Spring Boot)

# RequestHub

API REST desenvolvida com **Java + Spring Boot** para gerenciamento de solicitações internas, utilizando regras de negócio centralizadas no domínio, controle de acesso por perfis e arquitetura organizada por contexto de negócio.

---

## Sobre o Projeto

O RequestHub foi desenvolvido com foco em boas práticas utilizadas no mercado, priorizando:

- Separação de responsabilidades
- Arquitetura orientada ao domínio
- Controllers enxutos
- Regras de negócio centralizadas
- Segurança com Spring Security
- Contrato de API utilizando DTOs
- Testes unitários com Mockito

---

## Arquitetura

### Controller

Responsável por:

- Receber requisições HTTP
- Validar dados de entrada
- Chamar os serviços
- Retornar respostas HTTP

**Não contém regras de negócio.**

### Service

Responsável por:

- Orquestrar os casos de uso
- Buscar entidades
- Aplicar regras do domínio
- Persistir alterações
- Remover registros

### Domain

Responsável por:

- Representar o estado da aplicação
- Definir regras de negócio
- Controlar transições de status
- Validar operações permitidas

### Repository

Responsável exclusivamente pela persistência dos dados através do Spring Data JPA.

---

## Estrutura do Projeto

```text
com.requesthub.request_hub
│
├── infrastructure
│   ├── exception
│   │   └── GlobalExceptionHandler
│   │
│   └── security
│       └── SecurityConfig
│
└── solicitacao
    ├── controller
    │   └── SolicitacaoController
    │
    ├── domain
    │   ├── Solicitacao
    │   └── StatusSolicitacao
    │
    ├── dto
    │   ├── CriarSolicitacaoRequest
    │   ├── AlterarSolicitacaoRequest
    │   ├── AlterarStatusSolicitacaoRequest
    │   └── SolicitacaoResponse
    │
    ├── repository
    │   └── SolicitacaoRepository
    │
    └── service
        └── SolicitacaoService
```

---

## Segurança

### Autenticação

- HTTP Basic Authentication
- Session Stateless
- CSRF desabilitado

### Usuários de Desenvolvimento

| Usuário | Senha | Perfil |
|----------|----------|----------|
| admin | admin123 | ADMIN |
| luciano | 123 | SOLICITANTE |

### Permissões

#### ADMIN

- Listar solicitações
- Alterar status
- Excluir solicitações

#### SOLICITANTE

- Criar solicitações
- Alterar nome e descrição

> A camada de segurança controla quem pode executar uma ação.  
> O domínio controla se a ação é válida.

---

## Regras de Negócio

### Status da Solicitação

```text
ABERTA
EM_ANDAMENTO
FINALIZADA
```

### Fluxo Permitido

```text
ABERTA → EM_ANDAMENTO → FINALIZADA
```

### Exclusão

Apenas solicitações com status `ABERTA` podem ser removidas.

### Alteração

Solicitações com status `FINALIZADA` não podem ser editadas.

### Mudança de Status

A transição deve respeitar o fluxo definido.

Implementação atual:

```java
this.ordinal() + 1 == novoStatus.ordinal()
```

Caso a regra seja violada, uma `BusinessException` é lançada.

---

## Endpoints

Base URL:

```http
/solicitacoes
```

### Criar Solicitação

```http
POST /solicitacoes
```

#### Request

```json
{
  "nome": "Solicitação X",
  "descricao": "Descrição da solicitação"
}
```

#### Response

```http
201 Created
```

---

### Alterar Solicitação

```http
PUT /solicitacoes/{id}
```

#### Request

```json
{
  "nome": "Novo nome",
  "descricao": "Nova descrição"
}
```

#### Response

```http
200 OK
```

---

### Listar Solicitações

```http
GET /solicitacoes
```

#### Response

```http
200 OK
```

---

### Alterar Status

```http
PUT /solicitacoes/{id}/status
```

#### Request

```json
{
  "novoStatus": "EM_ANDAMENTO"
}
```

#### Response

```http
200 OK
```

---

### Excluir Solicitação

```http
DELETE /solicitacoes/{id}
```

#### Response

```http
204 No Content
```

---

## DTOs

### Request DTOs

- CriarSolicitacaoRequest
- AlterarSolicitacaoRequest
- AlterarStatusSolicitacaoRequest

### Response DTO

- SolicitacaoResponse

A utilização de DTOs evita a exposição direta das entidades e mantém o contrato da API desacoplado da camada de domínio.

---

## Tratamento de Exceções

O projeto utiliza um `GlobalExceptionHandler` para centralizar respostas de erro.

| Exceção | Status HTTP |
|----------|----------|
| NotFoundException | 404 |
| BusinessException | 409 |

Exemplo de evolução futura:

```json
{
  "timestamp": "2026-06-04T12:00:00",
  "status": 404,
  "message": "Solicitação não encontrada",
  "path": "/solicitacoes/1"
}
```

---

## Testes

Tecnologias utilizadas:

- JUnit 5
- Mockito
- ArgumentCaptor

### Cenários Cobertos

#### alterarStatus()

- Sucesso
- Recurso inexistente
- Violação de regra de negócio

#### alterarSolicitacao()

- Sucesso
- Recurso inexistente

#### deletarSolicitacao()

- Sucesso
- Recurso inexistente
- Exclusão não permitida

### Destaque

Utilização de `ArgumentCaptor` para validar o estado da entidade persistida após a execução do caso de uso.

---

## Execução Local

### Back-end

```bash
mvn spring-boot:run
```

Aplicação disponível em:

```text
http://localhost:8080
```

### Front-end

```text
http://localhost:4200
```

Necessário configurar CORS para permitir requisições do Angular.

---

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Jakarta Validation
- JUnit 5
- Mockito
- Maven
- MySQL
- Angular
- Git
- GitHub

---

## Próximas Evoluções

- JWT Authentication
- ErrorResponse padronizado
- Docker
- Docker Compose
- Testes de Integração
- Deploy na AWS
- CI/CD com GitHub Actions

---

## Resumo

API REST construída com Java e Spring Boot utilizando arquitetura organizada por domínio, regras de negócio centralizadas, RBAC com Spring Security, DTOs para contrato da API, tratamento global de exceções e testes unitários com Mockito.
