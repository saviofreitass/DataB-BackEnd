# Datacheque

Este projeto é uma aplicação web desenvolvida para gerenciamento de contracheques. A aplicação oferece uma interface amigável para os usuários e uma API robusta para acessar os dados.

## Objetivo do Projeto

Este sistema foi desenvolvido como parte do **Projeto Integrador III** da UNIFAGOC. O objetivo foi criar uma aplicação completa com backend e frontend utilizando boas práticas de desenvolvimento.

## Tecnologias Utilizadas

### Backend
- **Kotlin**
- **Spring Boot**
  - Spring Security
  - Spring Web Services
- **PostgreSQL**

### Frontend
- **React.js**
- **JavaScript**
- **HTML**
- **CSS**

## Arquitetura do Projeto

O sistema foi desenvolvido seguindo a **Arquitetura Hexagonal**, também conhecida como Ports and Adapters. Essa abordagem permite maior flexibilidade e desacoplamento entre as camadas do sistema, facilitando testes e manutenção.

### Estrutura do Backend
- **Domain**: Contém as entidades e as regras de negócio principais.
- **Application**: Responsável por coordenar os casos de uso do sistema.
- **Adapters**:
  - **Entrada**: Camada REST com endpoints expostos, desenvolvida com Spring Boot.
  - **Saída**: Comunicação com o banco de dados PostgreSQL e outros serviços externos.

### Benefícios da Arquitetura Hexagonal
- Separação clara entre lógica de negócios, infraestrutura e interfaces.
- Facilidade para substituir tecnologias ou implementar novas integrações.
- Foco na lógica central do sistema.

## Funcionalidades do Sistema

- **Contador**: O sistema permite que o contador crie empregadores e funcionários. Além disso, podendo gerar contracheques e deixar disponível para os funcionários.
- **Funcionário**: Os funcionários conseguem consultar seus contracheques de forma detalhada.


## Passo a Passo: Configuração do Backend

### 1. Clonar o Repositório
Primeiramente, clone o repositório do projeto para sua máquina local

### 2. Configurar o Banco de Dados PostgreSQL
O banco de dados PostgreSQL já está incluído no repositório. Siga os passos abaixo para restaurá-lo:

#### 2.1 Restaurar o Banco de Dados
Abra o PostgreSQL e crie um novo banco de dados chamado data_cheque e faça o restore do banco de dados no Postgres.
#### 2.2 Atualizar as Credenciais do Banco de Dados
No repositório clonado, localize o arquivo src/main/resources/application.yml.
Abra o arquivo e atualize as configurações de conexão com o banco de dados para corresponder ao seu PostgreSQL:
```yml
  spring:
  datasource:
    name: data_cheque
    url: jdbc:postgresql://localhost:5432/data_cheque
    username: <seu-usuario>
    password: <sua-senha>
    driver-class-name: org.postgresql.Driver
```
#### 3. Ajustar Dependências com Gradle
* Abra o projeto no seu IDE (como IntelliJ IDEA ou VS Code).
* Se estiver utilizando IntelliJ IDEA, após abrir o projeto, execute o seguinte:
* Vá até View > Tool Windows > Gradle.
* Clique com o botão direito sobre o nome do projeto no painel Gradle e selecione Reload All Gradle Projects.

Após o reload, a IDE irá baixar e configurar todas as dependências necessárias.

#### 4. Testando a API com Postman

A aplicação fornece uma **API RESTful** no qual atende um CRUD de contadores, empregadores, funcionários e contracheque. Para facilitar o teste das rotas, você pode usar o **Postman**, uma ferramenta popular para testar APIs. 

##### Acessando as Rotas da API

1. **Link para o Postman**: 
   Para visualizar e testar as rotas da API, você pode acessar a documentação no Postman através do link abaixo:
   [Link Postman](https://app.getpostman.com/join-team?invite_code=18536bb83fb9332a16f5fe3b7b226799cd859d9ebe9ade13cd1f11973652b5b6&target_code=efc0f6ae13b55d1781eae0d7acd13356)

3. Autenticação

  - A API utiliza **Spring Security** para autenticação. Para testar os endpoints protegidos, você precisará incluir um token JWT (ou outro método de autenticação) nas requisições.

  - **Exemplo de Header de Autenticação**:
  ```bash
  Authorization: Bearer <seu-token-jwt-aqui>
  ```

## Considerações Finais

Este projeto foi uma excelente oportunidade para aplicar os conhecimentos adquiridos durante o curso de **Ciência da Computação** na **UNIFAGOC**. A escolha da **Arquitetura Hexagonal** e das tecnologias utilizadas foi crucial para garantir a escalabilidade e a facilidade de manutenção do sistema.

A aplicação fornece um serviço útil para escritórios de contabilidade e foi construída com foco em boas práticas de desenvolvimento e segurança.

Agradecemos a todos os membros do grupo pela dedicação e trabalho em equipe!

---

**Integrantes do Projeto:**
- João Pedro 
- Kaio 
- Savio 
- Renato


