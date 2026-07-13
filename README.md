# BloDraft Blog API

## O que o projeto faz

É uma **API REST para blog** que permite:

- **Gerenciar posts** com título, conteúdo, excerpt, autor, status (rascunho/publicado), slug único, categoria e tags
- **Gerenciar categorias** para organizar os posts
- **Gerenciar tags** para marcar e filtrar conteúdo
- **Buscar posts** por termo no título, conteúdo ou nome das tags
- **Paginação** nas listagens de posts

Tudo isso com validação de entrada, tratamento global de erros e suporte a dois ambientes (H2 em memória para desenvolvimento e MySQL para produção).

---

## Estrutura do projeto

```
src/main/java/com/blodraft/blog_api/
├── controller/        # PostController, CategoryController, TagController
├── service/           # PostService, CategoryService, TagService
├── repository/        # PostRepository, CategoryRepository, TagRepository
├── model/             # Post, Category, Tag, PostStatus (enum)
├── dto/
│   ├── request/       # PostRequest, CategoryRequest, TagRequest
│   └── response/      # PostResponse, CategoryResponse, TagResponse, PagedResponse
├── config/            # WebConfig (CORS)
└── exception/         # GlobalExceptionHandler, ResourceNotFoundException, ErrorResponse
```

---

## Como executar a aplicação

### Pré-requisitos

- Java 21
- Maven (ou use o `./mvnw` incluso)
- Docker (apenas se for usar MySQL)
- **Nenhuma configuração extra é necessária para começar** — o profile padrão já usa H2 em memória

### Passos

#### Desenvolvimento (H2 em memória)

```bash
# Execute a aplicação (sem necessidade de Docker, mas é bem facil migrar para qualquer outro banco, basta configurar)
./mvnw spring-boot:run
```

## Funcionalidades da API

- **CRUD completo** de Posts, Categorias e Tags
- **Validação de entrada** com Bean Validation (`@NotBlank`, `@NotNull`, `@Size`, `@Pattern`) nos requests
- **Slug automático**: gerado a partir do título e com verificação de unicidade (adiciona sufixo `-1`, `-2` se necessário)
- **Busca textual** em título, conteúdo e nome das tags via `@Query` personalizada
- **Paginação** nativa com `Pageable` e resposta padronizada (`PagedResponse`)
- **Tratamento global de erros** com `@RestControllerAdvice`: respostas JSON padronizadas para erros 400, 404 e 500
- **CORS liberado**
- **Timestamps automáticos**: `createdAt` e `updatedAt` gerenciados via callbacks JPA (`@PrePersist`/`@PreUpdate`)
- **Dois ambientes**: H2 em memória (dev) e MySQL (produção) via profiles do Spring

---

## Tecnologias usadas

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 4.1.0 |
| Spring Data JPA | — |
| Spring Validation | — |
| Spring WebMVC | — |
| H2 Database | — |
| MySQL | 9.x |
| Lombok | 1.18.46 |
| Maven | — |

---

## Endpoints da API

### Posts

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/api/posts` | Lista posts (com paginação e search opcional) |
| `GET` | `/api/posts/{id}` | Busca post por ID |
| `GET` | `/api/posts/slug/{slug}` | Busca post por slug |
| `POST` | `/api/posts` | Cria um novo post |
| `PUT` | `/api/posts/{id}` | Atualiza um post existente |
| `DELETE` | `/api/posts/{id}` | Remove um post |

### Categorias

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/api/categories` | Lista todas as categorias |
| `GET` | `/api/categories/{id}` | Busca categoria por ID |
| `POST` | `/api/categories` | Cria uma nova categoria |
| `PUT` | `/api/categories/{id}` | Atualiza uma categoria |
| `DELETE` | `/api/categories/{id}` | Remove uma categoria |

### Tags

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/api/tags` | Lista todas as tags |
| `GET` | `/api/tags/{id}` | Busca tag por ID |
| `POST` | `/api/tags` | Cria uma nova tag |
| `PUT` | `/api/tags/{id}` | Atualiza uma tag |
| `DELETE` | `/api/tags/{id}` | Remove uma tag |

---

## Como testar

### 1. Criar uma categoria

```bash
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -d '{"name": "Programação", "slug": "programacao"}'
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "name": "Programação",
  "slug": "programacao"
}
```

### 2. Criar tags

```bash
curl -X POST http://localhost:8080/api/tags \
  -H "Content-Type: application/json" \
  -d '{"name": "java", "slug": "java"}'
```

### 3. Criar um post

```bash
curl -X POST http://localhost:8080/api/posts \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Aprendendo Spring Boot do Zero",
    "content": "Neste post vamos aprender os fundamentos do Spring Boot...",
    "excerpt": "Um guia completo para iniciar com Spring Boot",
    "authorName": "João",
    "categoryId": 1,
    "tagIds": [1, 2]
  }'
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "title": "Aprendendo Spring Boot do Zero",
  "slug": "aprendendo-spring-boot-do-zero",
  "content": "Neste post vamos aprender os fundamentos do Spring Boot...",
  "excerpt": "Um guia completo para iniciar com Spring Boot",
  "authorName": "João",
  "status": "PUBLISHED",
  "createdAt": "2026-07-12T...",
  "updatedAt": "2026-07-12T...",
  "category": { "id": 1, "name": "Programação", "slug": "programacao" },
  "tags": [
    { "id": 1, "name": "java", "slug": "java" },
    { "id": 2, "name": "spring-boot", "slug": "spring-boot" }
  ]
}
```

### 4. Listar posts com paginação e busca

```bash
# Listar todos (página 0, 10 itens)
curl http://localhost:8080/api/posts

# Buscar por termo
curl "http://localhost:8080/api/posts?search=spring"

# Paginação customizada
curl "http://localhost:8080/api/posts?page=0&size=5&sort=createdAt,desc"
```

### 5. Buscar post por slug

```bash
curl http://localhost:8080/api/posts/slug/aprendendo-spring-boot-do-zero
```

### 6. Erro 404 — recurso inexistente

```bash
curl http://localhost:8080/api/posts/999
```

**Resposta (404 Not Found):**
```json
{
  "message": "Post not found: 999"
}
```

### 7. Erro 400 — validação

```bash
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -d '{"slug": "sem-nome"}'
```

**Resposta (400 Bad Request):**
```json
{
  "message": "name: Name is required"
}
```
