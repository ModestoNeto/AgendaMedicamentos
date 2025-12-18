# 📱 MediCare – Sistema de Agenda de Medicamentos

## 1. Visão Geral
O **MediCare** é um sistema completo (front-end + back-end) para gerenciamento de medicamentos e lembretes.
O objetivo é auxiliar usuários no controle de tratamentos médicos, reduzindo esquecimentos e organizando horários de medicação.

O projeto foi desenvolvido com fins **acadêmicos**, seguindo boas práticas de arquitetura, separação de responsabilidades e integração entre sistemas.

---

## 2. Arquitetura do Sistema

```
Flutter (Mobile App)
        |
        | HTTP / JSON
        v
Spring Boot API (Kotlin)
        |
        v
PostgreSQL
```

- Arquitetura cliente-servidor
- API REST
- Comunicação via JSON
- Persistência relacional

---

## 3. Tecnologias Utilizadas

### Front-end
- Flutter
- Dart
- Material Design 3
- Navegação por rotas nomeadas
- Gerenciamento simples de sessão (`AppSession`)

### Back-end
- Spring Boot
- Kotlin
- Spring Data JPA
- Hibernate
- PostgreSQL
- Swagger (OpenAPI)
- Docker / Docker Compose

---

## 4. Estrutura do Projeto

```
AgendaMedicamentos/
├── back/
│   ├── Dockerfile
│   ├── docker-compose.yml
│   └── src/main/kotlin/com/br/ApiReme
│       ├── controller
│       ├── service
│       ├── domain
│       ├── Mapper
│       ├── Dtos
│       └── Port
│
└── front/
    └── lib/
        ├── screens
        ├── services
        ├── api
        ├── app_session.dart
        └── main.dart
```

---

## 5. Funcionalidades do Sistema

- Cadastro de usuários
- Login de usuários
- Cadastro de medicamentos
- Associação de medicamentos ao usuário
- Criação automática de lembretes
- Listagem de medicamentos por usuário
- Histórico de lembretes
- Logout

---

## 6. Sessão do Usuário (Front-end)

A sessão é controlada localmente no aplicativo Flutter através da classe:

```dart
AppSession
```

Armazena:
- `userId`
- `userName`
- `userEmail`

Esses dados são utilizados para filtrar medicamentos e lembretes por usuário.

---

## 7. Endpoints Principais da API

### Usuários
- `POST /users`
- `GET /users`

### Medicamentos
- `POST /medications`
- `GET /medications`
- `GET /medications/user/{userId}`
- `DELETE /medications/{id}`

### Lembretes
- `POST /reminders`
- `GET /reminders`
- `GET /reminders/medication/{medicationId}`
- `PUT /reminders/{id}`
- `DELETE /reminders/{id}`

---

## 8. Subindo o Back-end com Docker

### Pré-requisitos
- Docker instalado
- Docker Compose instalado
- Portas **8080** (API) e **5434** (PostgreSQL) livres

### Comandos

```bash
# Subir API + Banco
docker-compose up --build

# Parar containers
docker-compose down

# Parar e remover volumes (reset total)
docker-compose down -v
```

### Acessos
- API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html

---

## 9. Configuração do Front-end

### Emulador Android
```
http://10.0.2.2:8080
```

### Dispositivo físico
```
http://IP_DA_MAQUINA:8080
```

### Execução
```bash
flutter pub get
flutter run
```

---

## 10. Regras de Negócio Importantes

- Todo medicamento pertence a um usuário
- Todo lembrete pertence a um medicamento e a um usuário
- Não é permitido salvar medicamentos ou lembretes sem vínculo
- O front sempre filtra dados pelo `userId`

---

## 11. Considerações Finais

O sistema MediCare atende aos requisitos propostos, apresentando uma solução funcional, organizada e escalável.
A arquitetura adotada permite futuras evoluções, como autenticação JWT, notificações push e deploy em nuvem.

---

## 12. Licença

Projeto desenvolvido exclusivamente para fins acadêmicos.
