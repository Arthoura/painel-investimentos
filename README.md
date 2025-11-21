# 📈 API de Simulação de Investimentos

API REST para simulação de investimentos com recomendação de produtos financeiros baseada no perfil de risco do cliente. A aplicação utiliza autenticação via Keycloak e registra métricas de desempenho com telemetria.

---

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Security + Keycloak**
- **JPA / Hibernate**
- **Lombok**
- **Swagger (OpenAPI 3)**
- **SQL Server**
- **Redis**
- **Circuit breaker**
- **Docker**
- **Keycloak + JWT**
- **Testes unitários e de integração (JUnit + Mockito)**

---

## 📂 Execução dos Scripts de Banco de Dados

Para inicializar o banco de dados corretamente, você deve executar **todos os arquivos SQL** disponíveis na pasta `resources/sql`. Esses arquivos contêm os comandos necessários para popular as tabelas com dados iniciais.

## 🚀 Passos

1. Localize a pasta `resources/sql` no projeto.
2. Dentro dela, você encontrará **3 arquivos SQL** (um para cada tabela principal).
3. Execute os arquivos na seguinte ordem:
* `cliente.sql` → popula a tabela de clientes
* `produto.sql` → popula a tabela de produtos
* `investimento.sql` → popula a tabela de investimentos

---

## 🐳 Como Rodar Localmente

1. **Gerar o snapshot, rodando:**

   ```bash
   ./mvnw package

2. **Subir os containers**:

   ```bash
   docker compose up -d

Isso irá subir:

* SQL Server
* Redis
* Keycloak (com realm e client configurados)


3. Após subir, a API estará disponível em:
   [Localhost](http://localhost:8080)


4. A documentação Swagger estará em:
   [Swagger UI](http://localhost:8080/swagger-ui.html)
---

## 🔐 Autenticação

A API utiliza **Keycloak** com autenticação via **JWT**.

* Para testar a aplicação com segurança baseada em JWT, é necessário configurar corretamente o **Keycloak**. O projeto espera que o token contenha roles dentro de `resource_access["investe-api"]`.


## 📂 Requisitos

* Instância do **Keycloak** rodando em `http://localhost:8081`

* Credenciais de administrador:

    * Usuário: `admin`
    * Senha: `admin`


## ⚙️ Passo a Passo

### 1. Criar o Realm

* Acesse o Keycloak com `admin/admin`.
* Vá em **Create Realm**.
* Nome: `investimentos`.

### 2. Criar o Client

* Dentro do realm `investimentos`, vá em **Clients → Create**.
* Client ID: `investe-api`.
* Tipo: OpenID Connect.
* Access type: _public_ (para testes simples).
* Client authentication: on
* Salve.
* Após criado, ir em credentials e copiar o Client Secret.

### 3. Criar a Role

* No client `investe-api`, vá em **Client Roles**.
* Crie uma role chamada: `investidor`.

### 4. Criar o Usuário

* Vá em **Users → Add user**.
* Username: `usuario-teste`.
* Defina uma senha (ex.: `teste123`) em **Credentials** e desmarque “Temporary”.

### 5. Atribuir a Role ao Usuário

* No usuário criado, vá em **Role Mappings**.
* Selecione o client `investe-api`.
* Adicione a role `investidor`.

### 6. Envie o token no header:

  ```plaintext
  Authorization: Bearer <seu_token>
  ```
# ⚠️ Atenção

Para fins de facilitar os **testes**, somente o endpoint **`/telemetria`** precisa de autenticação.
---

## 📂 Coletânea de Requisições HTTP

Na pasta `resources/coletania-requisicoes` há uma **coletânea de requisições HTTP** preparada para facilitar os testes dos endpoints da aplicação.

### 🚀 Como utilizar

* Baixe o arquivo disponível na pasta `resources/coletania-requisicoes`.
* Importe o arquivo na sua ferramenta de requisições HTTP, como **Postman**, **Insomnia** ou até mesmo via **cURL**.
* Essas requisições foram organizadas para cobrir os principais endpoints da API, permitindo validar rapidamente o funcionamento e a segurança da aplicação.

### 💡 Observação

* Certifique-se de que o servidor da aplicação esteja em execução antes de rodar as requisições.
* Ajuste URLs, tokens ou parâmetros conforme necessário para o seu ambiente local.

---

## 📚 Endpoints Principais

### 🔎 Simulação de Investimentos

| Método | Endpoint                | Descrição                                | 
| ------ | ----------------------- | ---------------------------------------- | 
| POST   | `/simular-investimento` | Realiza uma simulação com base no perfil | 
| GET    | `/simular-investimento` | Lista todas as simulações realizadas     | 

---

### 📊 Telemetria

| Método | Endpoint              | Descrição                                  | 
| ------ | --------------------- | ------------------------------------------ | 
| POST   | `/telemetria/periodo` | Retorna métricas de desempenho por período |

---

### 📁 Histórico de Simulações

| Método | Endpoint     | Descrição                              | 
| ------ | ------------ | -------------------------------------- | 
| GET    | `/historico` | Lista simulações resumidas por cliente |

---

### 📈 Produtos Recomendados

| Método | Endpoint    | Descrição                              | 
| ------ | ----------- | -------------------------------------- | 
| GET    | `/produtos` | Lista produtos recomendados por perfil | 


---

## ✅ Testes

* Testes unitários com JUnit e Mockito

* Testes de integração com contexto Spring Boot

* Para rodar:

  ```plaintext
  ./mvnw test
  ```

---

## 📎 Swagger

Acesse a documentação interativa da API:

🔗   [Swagger UI](http://localhost:8080/swagger-ui.html)

---

## 🧠 Motor de Recomendação de Produtos Financeiros

O motor de recomendação foi construído com base em **perfis de investidores** e em um sistema de **pontuação ponderada** que avalia risco, liquidez e rentabilidade de cada produto. A ideia central é alinhar as características dos produtos financeiros com o perfil do cliente, garantindo recomendações mais adequadas.

## 🎯 Perfis de Investidores

Cada perfil define **pesos diferentes** para risco, liquidez e rentabilidade:

* **Conservador**

    * Risco: **0.5**
    * Liquidez: **0.4**
    * Rentabilidade: **0.1**
    * ➝ Foco em segurança e acesso rápido ao dinheiro.

* **Moderado**

    * Risco: **0.3**
    * Liquidez: **0.3**
    * Rentabilidade: **0.4**
    * ➝ Busca equilíbrio entre segurança e retorno.

* **Agressivo**

* Risco: **0.1**

* Liquidez: **0.2**

* Rentabilidade: **0.7**

* ➝ Prioriza ganhos maiores, aceitando maior risco.

Se o perfil informado não for válido, o sistema lança uma exceção (`RecursoNaoEncontradoException`).



## ⚖️ Sistema de Scoring

O cálculo da pontuação de cada produto é feito pelo **ProdutoScoringService**, que atribui notas de 1 a 3 para cada critério:

* **Risco**

    * Baixo → 3
    * Médio → 2
    * Alto → 1

* **Liquidez**

    * Alta → 3
    * Média → 2
    * Baixa → 1

* **Rentabilidade**

  * ≥ 15% → 3

  * ≥ 10% → 2

  * \< 10% → 1

A fórmula final combina os pesos do perfil com as notas de cada critério:

**Score =  (pesoRisco x riscoScore) + (pesoLiquidez x liquidezScore) + (pesoRentabilidade x rentabilidadeScore)**



## 🔎 Processo de Recomendação

1. **Busca dos produtos**: todos os produtos são carregados do repositório (`produtoRepository.findAll()`).
2. **Cálculo do score**: cada produto recebe uma pontuação com base no perfil.
3. **Filtragem**: apenas produtos com score ≥ **2.0** são considerados.
4. **Ordenação**: os produtos são ordenados em ordem decrescente de score.

---


## 🧮 Motor de Cálculo de Perfil de Risco

O **PerfilRiscoService** foi desenvolvido para classificar clientes em **Conservador, Moderado ou Agressivo**, com base em três dimensões principais: **volume de investimentos, frequência de movimentações e preferência declarada entre liquidez e rentabilidade**.



## 🔎 Etapas do Cálculo

1. **Volume de Investimentos**

* Soma de todos os valores investidos pelo cliente.
* Regras de pontuação:
* `< 6.000` → +10 pontos
* `< 20.000` → +25 pontos
* `≥ 20.000` → +40 pontos

**👉 Quanto maior o volume, maior a propensão ao risco.**


2. **Frequência de Movimentações Mensais**

* Número de operações realizadas por mês.
* Regras de pontuação:
* `≤ 2` → +10 pontos
* `≤ 6` → +20 pontos
* `> 6` → +30 pontos

**👉 Clientes que movimentam mais tendem a ser mais agressivos.**


3. **Preferência Declarada**

* Baseada no campo `PreferenciaLiquidezRentabilidade` do cliente.
* Regras de pontuação:
* **LIQUIDEZ** → +10 pontos
* **EQUILÍBRIO** → +20 pontos
* **RENTABILIDADE** → +30 pontos
* **Outro valor** → +15 pontos

**👉 A preferência declarada influencia diretamente o perfil.**


## ⚖️ Classificação Final

Após somar todos os pontos:

* **Conservador** → pontuação \< 40
* **Moderado** → pontuação entre 40 e 69
* **Agressivo** → pontuação ≥ 70