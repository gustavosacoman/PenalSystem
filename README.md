# PenalSystem

API REST em Java para gestão de um sistema penal, desenvolvida como avaliação A2-1 da disciplina **Integração de Sistemas de Software** (Prof. Marlon).

O sistema permite cadastrar presos e registrar suas atividades dentro da instituição (livros lidos, dias de trabalho e estudos), aplicando regras de **remição de pena** sobre a data de soltura conforme a legislação brasileira.

## Descrição do Sistema

O **PenalSystem** gerencia o histórico de cumprimento de pena de presos, com foco em atividades que geram remição (redução da pena). Cada atividade registrada (leitura de livro, dia de trabalho ou dia de estudo) impacta a data prevista de soltura do preso.

### Entidades

- **Prisoner** — preso (entidade principal)
- **Book** — livro lido pelo preso
- **Study** — dia de estudo
- **DayOfWork** — dia de trabalho

Todas as atividades (`Book`, `Study`, `DayOfWork`) herdam da classe abstrata `Activity` e estão relacionadas a um `Prisoner` por `prisonerId` (1:N).

### Regras de Negócio

- Validação de CPF único e datas obrigatórias no cadastro de preso.
- A data de chegada e a data original de soltura não podem estar no passado.
- Cada livro lido reduz a pena (remição por leitura), respeitando o limite máximo de livros por ano — exceder lança a exceção personalizada `MaxNumberOfBooksException`.
- Cada dia de trabalho e de estudo também atualiza a data de soltura conforme as regras de remição.
- Separação em camadas: `Controllers → Services → Repositories`.

## Arquitetura

```
src/main/java
├── api/controllers       # Handlers HTTP (HttpServer do JDK)
├── application
│   ├── dtos              # DTOs de entrada/saída
│   ├── services          # Regras de negócio
│   └── repositories      # Interfaces dos repositórios
├── domain
│   ├── entities          # Entidades de domínio
│   └── exceptions        # Exceções personalizadas
├── infrastructure
│   ├── ConnectionFactory # JDBC + Flyway
│   └── repositories      # Implementações JDBC
└── Main.java             # Bootstrap do servidor (porta 5000)
```

## Tecnologias

- **Java 26**
- **`com.sun.net.httpserver.HttpServer`** — servidor HTTP nativo do JDK
- **JDBC** + **MySQL Connector/J 9.1.0**
- **Flyway 10.22.0** — migrations
- **Jackson 2.17.0** — serialização/desserialização JSON (com `JavaTimeModule`)
- **Maven**

## Configuração

### Banco de Dados

1. Crie o banco no MySQL:
   ```sql
   CREATE DATABASE PenalSystem;
   ```
2. Configure as credenciais em `src/main/resources/application.properties`:
   ```properties
   db.url=jdbc:mysql://localhost:3306/PenalSystem
   db.user=root
   db.password=sua_senha
   ```
3. As migrations em `src/main/resources/db/migration` são aplicadas automaticamente pelo Flyway na inicialização.

### Execução

```bash
mvn clean package
mvn exec:java -Dexec.mainClass="Main"
```

Servidor disponível em `http://localhost:5000`.

Health-check: `GET /ping` → `pong`.

## Endpoints

### Prisoners — `/prisoners`

| Método | Path | Descrição |
|--------|------|-----------|
| GET    | `/prisoners` | Lista todos os presos |
| GET    | `/prisoners/{id\|cpf}` | Busca preso por UUID (36 chars) ou CPF |
| POST   | `/prisoners` | Cadastra novo preso |
| PUT    | `/prisoners/{id\|cpf}` | Atualiza preso |
| DELETE | `/prisoners/{id\|cpf}` | Remove preso |

**POST `/prisoners`** — body:
```json
{
  "name": "João Silva",
  "cpf": "12345678900",
  "birthDate": "1990-05-12",
  "arrivalDate": "2026-05-01",
  "originalReleaseDate": "2030-05-01"
}
```

### Books — `/books`

| Método | Path | Descrição |
|--------|------|-----------|
| GET    | `/books` | Lista todos os livros |
| GET    | `/books/{id}` | Busca livro por ID |
| GET    | `/books?prisonerId={uuid}` | Lista livros de um preso |
| POST   | `/books` | Registra livro lido |
| PUT    | `/books/{id}` | Atualiza livro |
| DELETE | `/books/{id}` | Remove livro |

**POST `/books`** — body:
```json
{
  "prisonerId": "uuid-do-preso",
  "isbn": "9788535914849",
  "title": "Dom Casmurro",
  "author": "Machado de Assis",
  "date": "2026-04-20"
}
```

### Studies — `/studies`

| Método | Path | Descrição |
|--------|------|-----------|
| GET    | `/studies` | Lista todos os estudos |
| GET    | `/studies/{id}` | Busca estudo por ID |
| GET    | `/studies?prisonerId={uuid}` | Lista estudos de um preso |
| POST   | `/studies` | Registra dia de estudo |
| PUT    | `/studies/{id}` | Atualiza estudo |
| DELETE | `/studies/{id}` | Remove estudo |

**POST `/studies`** — body:
```json
{
  "prisonerId": "uuid-do-preso",
  "subject": "Matemática",
  "date": "2026-04-20"
}
```

### Days of Work — `/days-of-work`

| Método | Path | Descrição |
|--------|------|-----------|
| GET    | `/days-of-work` | Lista todos os dias de trabalho |
| GET    | `/days-of-work/{id}` | Busca por ID |
| GET    | `/days-of-work?cpf={cpf}` | Lista dias de trabalho do preso pelo CPF |
| POST   | `/days-of-work` | Registra dia de trabalho |
| PUT    | `/days-of-work/{id}` | Atualiza dia de trabalho |
| DELETE | `/days-of-work/{id}` | Remove registro |

**POST `/days-of-work`** — body:
```json
{
  "cpf": "12345678900",
  "date": "2026-04-20",
  "description": "Trabalho na cozinha"
}
```

**PUT `/days-of-work/{id}`** — body (campos opcionais):
```json
{
  "date": "2026-04-21",
  "description": "Trabalho na biblioteca"
}
```

### Health-check

| Método | Path | Resposta |
|--------|------|----------|
| GET | `/ping` | `pong` |

## Testes

Os endpoints podem ser testados via **Postman**, **curl** ou código Java com `HttpURLConnection`.

Exemplo `curl`:
```bash
curl -X POST http://localhost:5000/prisoners \
  -H "Content-Type: application/json" \
  -d '{"name":"João","cpf":"12345678900","birthDate":"1990-01-01","arrivalDate":"2026-05-01","originalReleaseDate":"2030-05-01"}'
```

## Equipe

Projeto desenvolvido em equipe para a disciplina de Integração de Sistemas de Software.
