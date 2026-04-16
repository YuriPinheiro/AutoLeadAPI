# AutoLead API

API REST para captação e gerenciamento de leads de veículos.  
Usuários podem cadastrar carros para venda e administradores podem gerenciar todos os leads.

---
## Tecnologias

- Java
- Spring Boot
- Spring Security
- JWT (jjwt)
- PostgreSQL
- Gradle

## Arquitetura

Projeto baseado em **Spring Boot + PostgreSQL**, seguindo padrão:

- Controller → entrada HTTP
- Service → regras de negócio
- Repository → acesso ao banco
- DTO → comunicação segura
- Mapper → conversão DTO ↔ Entity

---

## Autenticação

- Autenticação via **JWT**
- Login retorna um token com:
  - `userId`
  - `email`
  - `role`

Exemplo de uso:

```http
Authorization: Bearer <token>

POST /auth/register
POST /auth/login

POST /leads
GET /leads
GET /leads/{id}
GET /leads/{id}/history

GET /admin/leads
GET /admin/leads/{id}
PATCH /admin/leads/{id}/status
```


