# 📊 Belo Prato RH
## 🧾 Introdução

Este projeto tem como objetivo o desenvolvimento de um **Sistema de Recursos Humanos (RH)** voltado para as necessidades específicas de restaurantes. A proposta é oferecer uma solução prática, intuitiva e eficiente para a **gestão de colaboradores**, contemplando funcionalidades como:

- Cadastro e gerenciamento de funcionários  
- Controle de escalas e turnos de trabalho  
- Registro de ponto e jornadas  
- Gestão de folgas e férias  
- Relatórios e indicadores de desempenho

Em um ambiente dinâmico como o de restaurantes, onde a rotatividade e a organização de escalas são grandes desafios, este sistema busca **automatizar processos**, reduzir erros manuais e facilitar o trabalho do setor de RH.


Projeto desenvolvido para disciplina de **Programação Modular** na PUC Minas.  
O objetivo é criar um sistema em **Java com Spring Boot** para gerenciamento de **folha de pagamento**, aplicando conceitos de Programação Orientada a Objetos, modularidade e boas práticas.

---

## ✅ Descrição do Projeto
O sistema deve permitir:
- Autenticação de usuário (login e senha)
- Cálculo da folha de pagamento com:
  - Salário por hora
  - Adicionais
  - Benefícios (vale transporte, vale alimentação)
  - Descontos (INSS, FGTS, IRRF)
- Exibição de relatório com valores calculados
- Persistência dos dados em banco
- Integração futura com frontend

---

## 🔍 Requisitos Funcionais
- RF1 - Calcular Salário Hora
- RF2 - Calcular Periculosidade
- RF3 - Calcular Insalubridade
- RF4 - Calcular Vale Transporte
- RF5 - Calcular Vale Alimentação
- RF6 - Calcular Desconto de INSS
- RF7 - Calcular FGTS
- RF8 - Calcular Desconto de IRRF
- RF9 - Calcular Salário Líquido
- RF10 - Exibir Relatório
  
---

## 🛠️ Tecnologias
- Java 17+
- Spring Boot
- Banco de dados relacional (MySQL, PostgreSQL, etc.)
- JUnit para testes

# 🧾 Sistema de Gerenciamento de RH

## 📘 Descrição Geral
Este sistema foi desenvolvido para **gerenciar funcionários do setor de RH**, permitindo o **cadastro e autenticação de gerentes responsáveis**, além do **registro de funcionários** e do **cálculo automatizado de salários** com base em encargos e adicionais (como **insalubridade, FGTS, IRRF e periculosidade**).

---

## 👥 Funcionalidades Principais

### 🔐 Login e Cadastro de Gerentes
O sistema possui uma **página de login** para os funcionários responsáveis pelo gerenciamento do RH.  
Caso o usuário ainda não tenha conta, ele pode **criar um login**, informando:
- Nome completo  
- Telefone  
- CPF  
- E-mail  
- Senha  
- Código de acesso numérico gerado automaticamente  

Após o login, o gerente tem acesso à área de gerenciamento do sistema.

---

### 👨‍🍳 Cadastro de Funcionários
Após o login, o gerente pode cadastrar novos funcionários, informando:
- Nome completo  
- Cargo (Cozinheiro, Garçom ou Limpeza)  
- Telefone  
- Salário base  
- CPF  
- Tipo de adicional (Insalubridade, Periculosidade etc.)

O sistema realiza **cálculos automáticos no salário final**, considerando:
- **IRRF (Imposto de Renda Retido na Fonte)**  
- **FGTS (Fundo de Garantia do Tempo de Serviço)**  
- **Adicional de Insalubridade e Periculosidade**  

---

## 🧬 Uso da Herança
A **herança** foi utilizada para **organizar as classes responsáveis pelo cálculo de salário** e **reaproveitar código comum** entre elas.

### Exemplo:
```java
// Classe base
class Salario {
    protected double salarioBase;

    public Salario(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public double calcularSalarioFinal() {
        return salarioBase;
    }
}

// Classe filha herda métodos e atributos da classe pai
class SalarioComInsalubridade extends Salario {
    public SalarioComInsalubridade(double salarioBase) {
        super(salarioBase);
    }

    @Override
    public double calcularSalarioFinal() {
        return salarioBase + (salarioBase * 0.10); // 10% de adicional
    }
}
````
## Polimorfismo no Sistema 
O polimorfismo foi utilizado no sistema de RH para permitir que diferentes tipos de funcionários ou cálculos de salário utilizem o mesmo método, mas com comportamentos distintos.

No contexto do sistema, cada funcionário pode ter um tipo diferente de adicional (como insalubridade, periculosidade, FGTS ou IRRF).
Mesmo assim, todos possuem o mesmo método para calcular o salário final, chamado calcularSalarioFinal().
### Exemplo:
```java
Salario salario1 = new SalarioComInsalubridade(2000);
Salario salario2 = new SalarioComPericulosidade(2000);
Salario salario3 = new SalarioComIRRF(2000);

System.out.println(salario1.calcularSalarioFinal()); // calcula com insalubridade
System.out.println(salario2.calcularSalarioFinal()); // calcula com periculosidade
System.out.println(salario3.calcularSalarioFinal()); // calcula com IRRF
````
## Uso de interface 
A interface foi usada para criar um contrato de comportamento que todas as classes responsáveis pelos cálculos de encargos devem seguir.

Com isso, garantimos que todas as classes de cálculo (como IRRF, FGTS, Insalubridade, Periculosidade) tenham o mesmo método, chamado calcular().
Essa padronização facilita a manutenção do sistema e permite que novas regras de cálculo sejam adicionadas facilmente.

### Exemplo
```java
interface Calculavel {
    double calcular();
}

class FGTS implements Calculavel {
    private double salarioBase;

    public FGTS(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    @Override
    public double calcular() {
        return salarioBase * 0.08; // 8% de FGTS
    }
}

class IRRF implements Calculavel {
    private double salarioBase;

    public IRRF(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    @Override
    public double calcular() {
        return salarioBase * 0.075; // 7,5% de IRRF
    }
}
```
Dessa forma, o sistema pode usar qualquer cálculo sem se preocupar com o tipo específico da classe:
```java
Calculavel c1 = new FGTS(2000);
Calculavel c2 = new IRRF(2000);

System.out.println(c1.calcular());
System.out.println(c2.calcular());
````

