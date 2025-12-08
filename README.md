# 📊 Belo Prato RH — Sistema de Gestão de Folha de Pagamento

## 🧾 Visão Geral

**Belo Prato RH** é um sistema completo de gestão de Recursos Humanos desenvolvido com **Java 17 + Spring Boot 3**, oferecendo:

- ✅ Cálculo automatizado da folha de pagamento (INSS, FGTS, IRRF, adicionais)
- ✅ Cadastro e atualização de funcionários
- ✅ Autenticação segura com JWT
- ✅ Interface web amigável com Thymeleaf + Bootstrap

---

## ⚙️ Tecnologias

| Camada | Tecnologia |
|--------|-----------|
| **Backend** | Java 17 • Spring Boot 3 • Spring Security |
| **Frontend** | Thymeleaf • HTML5 • Bootstrap |
| **Banco de Dados** | H2 |
| **Testes** | JUnit 5 • Mockito |
| **Build** | Maven |
| **Segurança** | JWT • BCrypt |

---

## 🚀 Como Rodar o Projeto

### ✨ Passo 1: Clonar o Repositório

```bash
git clone https://github.com/pm-puc-minas/calculo-folha-pagamento-lab2-grupo3.git
cd calculo-folha-pagamento-lab2-grupo3
```

### ✨ Passo 2: Configurar o Banco de Dados

Edite `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/folha_pagamento
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.thymeleaf.cache=false
```

### ✨ Passo 3: Instalar Dependências

```bash
mvn clean install
```

### ✨ Passo 4: Executar a Aplicação

```bash
mvn spring-boot:run
```

✅ **Aplicação rodando em:** `http://localhost:8080`

---

## 🧭 Navegação — URLs e Páginas

| URL | Método | Descrição | Página |
|-----|--------|-----------|--------|
| `http://localhost:8080/auth/login` | GET | Tela de login | **Login.html** |
| `http://localhost:8080/auth/register` | GET | Cadastro de usuário | **CadastroFuncionario.html** |
| `http://localhost:8080/home` | GET | Página inicial | **Home.html** |
| `http://localhost:8080/funcionarios` | GET | Listagem de funcionários | **ListarFuncionarios.html** |
| `http://localhost:8080/funcionarios/novo` | GET | Formulário de novo funcionário | **CadastroFuncionario.html** |
| `http://localhost:8080/Calcular` | GET | Cálculo de folha de pagamento | **CalcularSalario.html** |
| `http://localhost:8080/folha` | GET | Visualizar folhas geradas | **FolhaPagamento.html** |

---

## 🧮 Entidades Principais

### Funcionario

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | Long | Identificador único |
| `nome` | String | Nome completo |
| `cpf` | String | CPF único |
| `cargo` | String | Cargo do funcionário |
| `salarioBruto` | BigDecimal | Valor base |
| `grauInsalubridade` | Enum (MINIMA, MEDIA, MAXIMA) | Grau de insalubridade |
| `periculosidade` | boolean | Indica adicional |
| `pensaoAlimenticia` | BigDecimal | Valor de pensão |
| `outrasDeducoes` | BigDecimal | Descontos adicionais |
| `folhasPagamento` | List<FolhaPagamento> | Histórico de folhas |

### FolhaPagamento

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `codigoPagamento` | Long | Código único |
| `funcionario` | Funcionario | Funcionário vinculado |
| `mes` | int | Mês de referência |
| `horasTrabalhadas` | BigDecimal | Horas trabalhadas |
| `salarioLiquido` | BigDecimal | Valor líquido |
| `valorValeAlimentacao` | BigDecimal | Vale alimentação |
| `valorValeTransporte` | BigDecimal | Vale transporte |

---

## 🧪 Testes Unitários

Execute os testes com:

```bash
mvn test
```

Os testes validam:
- ✅ Autenticação e geração de tokens JWT
- ✅ Criação e atualização de funcionários
- ✅ Cálculo de folha de pagamento
- ✅ Validação de descontos e adicionais

---

## 🧠 Conceitos de POO Aplicados

- **Herança**: Reutilização de lógica de cálculo entre classes
- **Polimorfismo**: Diferentes tipos de cálculo salarial com o mesmo método
- **Encapsulamento**: Atributos protegidos e acessados via getters/setters
- **Interface**: Contrato de cálculos (Calculavel → calcular())

---