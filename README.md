# BloDraft Blog API

## O que o projeto faz

É uma **API REST para blog** que permite:

- **Gerenciar posts** com título, conteúdo, excerpt, autor, status (rascunho/publicado), slug único, categoria e tags
- **Gerenciar categorias** para organizar os posts
- **Gerenciar tags** para marcar e filtrar conteúdo
- **Buscar posts** por termo no título, conteúdo ou nome das tags
- **Paginação** nas listagens de posts
- **Autenticação JWT**: registro de usuários e login com token
- **Proteção de rotas**: rotas de escrita exigem login; categorias e tags exigem role ADMIN

Tudo isso com validação de entrada, tratamento global de erros e autenticação via tokens JWT.

---

## Estrutura do projeto

```
src/main/java/com/blodraft/blog_api/
├── controller/        # AuthController, PostController, CategoryController, TagController
├── service/           # AuthService, PostService, CategoryService, TagService
├── repository/        # UserRepository, PostRepository, CategoryRepository, TagRepository
├── security/          # JwtTokenProvider, JwtAuthenticationFilter, CustomUserDetailsService
├── model/             # User, Post, Category, Tag, PostStatus e Role (enums)
├── dto/
│   ├── request/       # LoginRequest, RegisterRequest, PostRequest, CategoryRequest, TagRequest
│   └── response/      # JwtResponse, PostResponse, CategoryResponse, TagResponse, PagedResponse
├── config/            # SecurityConfig, WebConfig (CORS)
├── exception/         # GlobalExceptionHandler, ResourceNotFoundException, BadRequestException, ErrorResponse
└── utils/             # SlugUtils
```

---

## Como executar a aplicação

### Pré-requisitos

- Java 21
- Maven (ou use o `./mvnw` incluso)
- Docker (apenas se for usar MySQL)
- Variáveis de ambiente: `JWT_SECRET` (chave de pelo menos 32 caracteres) e `JWT_EXPIRATION` (padrão 86400000 se omitida)

### Passos

```bash
export JWT_SECRET=sua-chave-com-pelo-menos-32-caracteres
./mvnw spring-boot:run
```

---

## Funcionalidades da API

- **CRUD completo** de Posts, Categorias e Tags
- **Autenticação JWT**: registro e login com tokens
- **Proteção de rotas** baseada em role (USER / ADMIN)
- **Senhas hashizadas** com BCrypt
- **Validação de entrada** com Bean Validation (`@NotBlank`, `@NotNull`, `@Size`, `@Email`, `@Pattern`) nos requests
- **Slug automático**: gerado a partir do título e com verificação de unicidade (adiciona sufixo `-1`, `-2` se necessário)
- **Busca textual** em título, conteúdo e nome das tags via `@Query` personalizada
- **Paginação** nativa com `Pageable` e resposta padronizada (`PagedResponse`)
- **Tratamento global de erros** com `@RestControllerAdvice`: respostas JSON padronizadas para erros 400, 404 e 500
- **CORS liberado**
- **Timestamps automáticos**: `createdAt` e `updatedAt` gerenciados via callbacks JPA (`@PrePersist`/`@PreUpdate`)

---

## Tecnologias usadas

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 4.1.0 |
| Spring Data JPA | — |
| Spring Validation | — |
| Spring WebMVC | — |
| Spring Security | — |
| jjwt (JSON Web Token) | 0.12.6 |
| MySQL | 9.x |
| Lombok | 1.18.46 |
| Maven | — |

---

## Endpoints da API

### Autenticação

| Método | Rota | Descrição | Autenticação |
|---|---|---|---|
| POST | `/api/auth/register` | Registra um novo usuário | Publico |
| POST | `/api/auth/login` | Faz login e retorna token JWT | Publico |

### Posts

| Método | Rota | Descrição | Autenticação |
|---|---|---|---|
| GET | `/api/posts` | Lista posts (com paginacao e search opcional) | Publico |
| GET | `/api/posts/{id}` | Busca post por ID | Publico |
| GET | `/api/posts/slug/{slug}` | Busca post por slug | Publico |
| POST | `/api/posts` | Cria um novo post | Logado |
| PUT | `/api/posts/{id}` | Atualiza um post existente | Logado |
| DELETE | `/api/posts/{id}` | Remove um post | Logado |

### Categorias

| Metodo | Rota | Descricao | Autenticacao |
|---|---|---|---|
| GET | `/api/categories` | Lista todas as categorias | Publico |
| GET | `/api/categories/{id}` | Busca categoria por ID | Publico |
| POST | `/api/categories` | Cria uma nova categoria | ADMIN |
| PUT | `/api/categories/{id}` | Atualiza uma categoria | ADMIN |
| DELETE | `/api/categories/{id}` | Remove uma categoria | ADMIN |

### Tags

| Metodo | Rota | Descricao | Autenticacao |
|---|---|---|---|
| GET | `/api/tags` | Lista todas as tags | Publico |
| GET | `/api/tags/{id}` | Busca tag por ID | Publico |
| POST | `/api/tags` | Cria uma nova tag | ADMIN |
| PUT | `/api/tags/{id}` | Atualiza uma tag | ADMIN |
| DELETE | `/api/tags/{id}` | Remove uma tag | ADMIN |

---

## Variaveis de ambiente

| Variavel | Descricao | Valor padrao |
|---|---|---|
| `JWT_SECRET` | Chave secreta para assinar os tokens (minimo 32 caracteres) | Obrigatoria |
| `JWT_EXPIRATION` | Tempo de expiracao do token em milissegundos | `86400000` (24h) |
| `DB_URL` | URL de conexao com MySQL | Obrigatoria |
| `DB_USER` | Usuario do MySQL | Obrigatoria |
| `DB_PASSWORD` | Senha do MySQL | Obrigatoria |
| `DB_SHOW_SQL` | Exibir SQL no console | `true` |

---

## Observacoes

Este projeto foi criado como parte dos meus estudos em Java e Spring Boot. A implementacao de autenticacao JWT e protecao de rotas foi o primeiro contato que tive com seguranca em APIs REST. Pode conter erros ou abordagens que podem ser melhoradas — sugestoes sao bem-vindas.
