
<h1 align="center">
  Insurance API
</h1>

<p align="center">
<img align="center" src="https://img.shields.io/static/v1?label=Type&message=Demo&color=8257E5&labelColor=000000" alt="Demo"/>
</p>

<p align="center">
  <i>API para gerenciar seguros.</i>
</p>

---

## Description

A **Insurance API** é uma aplicação que permite gerenciar e contratar seguros de forma eficiente. Através desta API, é possível realizar simulações e contratar seguros vinculados a um CPF. A aplicação utiliza **MongoDB** como banco de dados, garantindo escalabilidade e alta performance, e integra-se à **Customer API** para validar clientes antes da contratação.

---

## Technologies

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)&nbsp;
![Spring Boot](https://img.shields.io/badge/springboot-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white)&nbsp;
![Feign](https://img.shields.io/badge/feign-%2300A6D3.svg?style=for-the-badge&logoColor=white)&nbsp;
![Resilience4j](https://img.shields.io/badge/resilience4j-%234B8BBE.svg?style=for-the-badge&logoColor=white)&nbsp;
![MongoDB](https://img.shields.io/badge/mongodb-%2347A248.svg?style=for-the-badge&logo=mongodb&logoColor=white)&nbsp;
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)&nbsp;

---

## Features

- [x] Simulação de seguros com base nos tipos disponíveis.
- [x] Contratação de seguros vinculados a um CPF.
- [x] Validação de clientes através da **Customer API**.
- [x] Implementação de resiliência com **Resilience4j**.
- [x] Documentação com Swagger/OpenAPI.

---

## Roadmap

- Adicionar autenticação e autorização com JWT.
- Implementar suporte para múltiplos idiomas.
- Criar mais testes automatizados (unitários e de integração).
- Adicionar monitoramento de métricas com Prometheus/Grafana.

---

## How to Run

### Pré-requisitos

- **Java 17** ou superior.
- **Docker** (para o MongoDB).
- **Maven** (caso não utilize o wrapper).

### Passos

1. Clone o repositório:

```bash
git clone https://github.com/genorchiomento/insurance-api.git
```

2. Suba o banco de dados MongoDB utilizando Docker Compose:

```bash
docker-compose up -d
```

3. Compile o projeto:

```bash
./mvnw clean package
```

4. Execute o JAR gerado:

```bash
java -jar target/insurance-api-0.0.1.jar
```

5. Acesse a documentação da API no navegador:

```
http://localhost:8082/swagger-ui.html
```

---

## TODO

- [ ] Adicionar suporte a múltiplos idiomas.
- [ ] Melhorar a documentação da API com exemplos mais detalhados.
- [ ] Criar mais testes automatizados.
- [ ] Implementar autenticação e autorização.

---

## API Endpoints

### **Simulação de Seguros**

**GET** `/api/insurances/simulate`

**Response**:
```json
[
  {
    "type": "BRONZE",
    "description": "Cobertura básica para danos e roubos.",
    "monthlyCost": 50.0
  },
  {
    "type": "SILVER",
    "description": "Cobertura intermediária, incluindo desastres naturais.",
    "monthlyCost": 100.0
  },
  {
    "type": "GOLD",
    "description": "Cobertura completa e assistência 24h.",
    "monthlyCost": 150.0
  }
]
```

### **Contratar Seguro**

**POST** `/api/insurances/contract`

**Body**:
```json
{
  "cpf": "12345678900",
  "insuranceType": "GOLD"
}
```

**Response**:
```json
{
  "id": "64c9f0b565d0f9e7c02893bf",
  "cpf": "12345678900",
  "insuranceType": "GOLD",
  "contractDate": "2024-12-05"
}
```

---

## Test

- Use ferramentas como **Postman** ou **Swagger** para testar os endpoints.
- Exemplos de requisições estão documentados no Swagger UI.

---

## License

Este projeto está licenciado sob a [Licença MIT](LICENSE).
