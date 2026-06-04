
# RequestHub - Front-end

Aplicação Web desenvolvida com **Angular** para consumo da API REST do RequestHub.

O projeto foi estruturado com foco em organização, escalabilidade e separação de responsabilidades, utilizando uma arquitetura baseada em **Core**, **Features** e **Shared**, semelhante ao padrão adotado em aplicações corporativas.

---

## Sobre o Projeto

O front-end possui dois perfis de acesso:

### SOLICITANTE

* Criar solicitações
* Editar nome e descrição das solicitações

### ADMIN

* Listar solicitações
* Alterar status
* Excluir solicitações quando permitido pelas regras de negócio

Toda a comunicação é realizada através da API desenvolvida em Spring Boot.

---

## Estrutura do Repositório

```text
backend/
└── Spring Boot API

frontend/
└── requesthub-web/
    └── Angular Application
```

A separação entre front-end e back-end permite desenvolvimento independente de cada aplicação.

---

## Execução Local

### Instalação

```bash
npm install
```

### Inicialização

```bash
npm start
```

ou

```bash
ng serve -o
```

Aplicação disponível em:

```text
http://localhost:4200
```

---

## Comunicação com o Back-end

### Back-end

```text
http://localhost:8080
```

### Front-end

```text
http://localhost:4200
```

Como as aplicações utilizam portas diferentes, é necessário habilitar CORS na API para permitir as requisições do Angular.

---

## Arquitetura do Front-end

Estrutura baseada em responsabilidades:

```text
src/app
│
├── core
│   ├── guards
│   ├── interceptors
│   ├── models
│   └── services
│
├── features
│   ├── auth
│   └── solicitacoes
│
└── shared
    └── components
```

### Core

Contém recursos globais da aplicação:

* Services
* Guards
* Interceptors
* Models

### Features

Contém os módulos de negócio do sistema:

* Autenticação
* Solicitações

### Shared

Contém componentes reutilizáveis utilizados em diferentes telas.

---

## Autenticação

O projeto utiliza **HTTP Basic Authentication** para integração com a API.

A autenticação é composta por:

### AuthService

Responsável por:

* Armazenar credenciais
* Identificar perfil do usuário
* Controlar estado de autenticação

### AuthInterceptor

Responsável por adicionar automaticamente o header:

```http
Authorization: Basic base64(usuario:senha)
```

em todas as requisições HTTP.

---

## Controle de Acesso

Além da proteção existente na API, o front-end implementa controle de acesso baseado em perfis.

### RoleGuard

Responsável por:

* Restringir acesso a rotas administrativas
* Direcionar usuários para áreas compatíveis com seu perfil

Perfis suportados:

* ADMIN
* SOLICITANTE

---

## Models

Os modelos TypeScript refletem os DTOs expostos pela API.

Exemplos:

```text
SolicitacaoResponse
CriarSolicitacaoRequest
AlterarSolicitacaoRequest
AlterarStatusRequest
StatusSolicitacao
```

Benefícios:

* Tipagem forte
* Menor uso de any
* Integração mais segura entre front-end e back-end

---

## Camada de Serviços

Todas as chamadas HTTP são centralizadas em serviços.

### SolicitacaoApiService

Principais operações:

```typescript
listarTodas()
criar()
alterar(id, dto)
alterarStatus(id, dto)
deletar(id)
```

Os componentes não acessam URLs diretamente, mantendo a responsabilidade de integração isolada na camada de serviços.

---

## Telas Implementadas

### Login

* Autenticação de usuários
* Redirecionamento conforme perfil

### Administração

* Listagem de solicitações
* Alteração de status
* Exclusão de registros

### Nova Solicitação

* Cadastro de solicitações
* Validação de formulário
* Feedback visual ao usuário

### Editar Solicitação

* Carregamento de dados existentes
* Atualização via API
* Retorno para listagem após salvamento

---

## Rotas

```text
/login

/admin/solicitacoes

/solicitacoes/nova

/solicitacoes/:id/editar
```

---

## Tecnologias Utilizadas

* Angular
* TypeScript
* RxJS
* Angular Router
* Angular Forms
* HTTP Client
* HTML5
* CSS3

---

## Próximas Evoluções

* JWT Authentication
* Refresh Token
* Loading Global
* Tratamento centralizado de erros
* Docker
* Deploy em AWS
* Integração contínua (CI/CD)

---

## Resumo

Aplicação Angular estruturada utilizando arquitetura baseada em Core, Features e Shared, com autenticação via Basic Auth, controle de acesso por perfil, comunicação com API Spring Boot e separação clara de responsabilidades.
