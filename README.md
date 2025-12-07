📊 Belo Prato RH — Sistema de Gestão de Folha de Pagamento
🧾 Visão Geral

O Belo Prato RH é um sistema completo de gestão de Recursos Humanos, desenvolvido em Java 17 + Spring Boot 3, com autenticação JWT e interface em Thymeleaf.

Voltado para o setor de restaurantes, o sistema oferece:

Cálculo automatizado da folha de pagamento (INSS, FGTS, IRRF, adicionais).

Cadastro e atualização de funcionários.

Autenticação e controle de acesso de usuários (gerentes de RH).

Interface web amigável desenvolvida com Thymeleaf.

⚙️ Tecnologias Utilizadas
Camada	Tecnologia
Backend	Java 17 • Spring Boot 3 • Spring Web • Spring Security (JWT)
Banco de Dados	MySQL (ou outro relacional compatível)
Frontend	Thymeleaf + HTML + Bootstrap
Testes	JUnit 5 • Mockito
Build	Maven
Segurança	BCryptPasswordEncoder (criptografia de senhas)
🚀 Como Executar o Projeto
1️⃣ Clonar o Repositório
git clone https://github.com/seuusuario/gestao-pagamento.git
cd gestao-pagamento

2️⃣ Configurar o Banco de Dados

Edite src/main/resources/application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/folha_pagamento
spring.datasource.username=root
spring.datasource.password=123456
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.thymeleaf.cache=false

3️⃣ Compilar o Projeto
mvn clean install

4️⃣ Executar a Aplicação
mvn spring-boot:run


A aplicação estará disponível em:
👉 http://localhost:8080

🧩 Endpoints Principais
🔐 Autenticação (/auth)
1. Login

POST /auth/login

Body:

{
  "login": "admin",
  "password": "123456"
}


Resposta (200):

{
  "token": "jwt-gerado-aqui"
}

2. Cadastro de Usuário

POST /auth/register

Body:

{
  "login": "novo_user",
  "password": "senha123",
  "role": "ADMIN"
}


Respostas:

✅ 200 OK — Usuário criado

⚠️ 400 Bad Request — Login já existente

💡 O token JWT retornado deve ser enviado no header Authorization:
Authorization: Bearer <token>

👨‍🍳 Funcionários (/funcionarios)
1. Criar Funcionário

POST /funcionarios

Body:

{
  "nome": "Maria Souza",
  "cpf": "12345678901",
  "cargo": "Cozinheira",
  "salarioBruto": 2500.00,
  "periculosidade": false,
  "grauInsalubridade": "MEDIA",
  "pensaoAlimenticia": 0.0,
  "outrasDeducoes": 0.0
}


Resposta (201):

{
  "id": 1,
  "nome": "Maria Souza",
  "cpf": "12345678901",
  "salarioBruto": 2500.00
}

2. Listar Funcionários

GET /funcionarios/listarTodos

Resposta (200):

[
  {
    "id": 1,
    "nome": "Maria Souza",
    "cpf": "12345678901",
    "cargo": "Cozinheira"
  }
]

3. Atualizar Funcionário

PUT /funcionarios/atualizar

Body:

{
  "cpf": "12345678901",
  "cargo": "Gerente de Cozinha",
  "salarioBruto": 3200.00
}


Resposta (200):

{
  "cpf": "12345678901",
  "cargo": "Gerente de Cozinha",
  "salarioBruto": 3200.00
}

💰 Folha de Pagamento (/folha)
1. Calcular Folha

POST /folha/calcular

Body:

{
  "cpfFuncionario": "12345678901",
  "mes": 10,
  "horasTrabalhadas": 180
}


Resposta (200):

{
  "codigoPagamento": 12,
  "mes": 10,
  "salarioLiquido": 2135.00,
  "valorValeAlimentacao": 350.00,
  "valorValeTransporte": 220.00
}

2. Listar Folhas por Funcionário

GET /folha/mostrar_salario/{cpf}
Exemplo:
GET /folha/mostrar_salario/12345678901

Resposta (200):

[
  {
    "mes": 9,
    "salarioLiquido": 2150.00
  },
  {
    "mes": 10,
    "salarioLiquido": 2135.00
  }
]

🧮 Entidades Principais
Funcionario
Campo	Tipo	Descrição
id	Long	Identificador único
nome	String	Nome completo
cpf	String	CPF único
cargo	String	Cargo do funcionário
salarioBruto	BigDecimal	Valor base
grauInsalubridade	Enum (MINIMA, MEDIA, MAXIMA)	Grau de insalubridade
periculosidade	boolean	Indica adicional
pensaoAlimenticia	BigDecimal	Valor de pensão
outrasDeducoes	BigDecimal	Descontos adicionais
folhasPagamento	List<FolhaPagamento>	Histórico de folhas
FolhaPagamento
Campo	Tipo	Descrição
codigoPagamento	Long	Código único
funcionario	Funcionario	Funcionário vinculado
mes	int	Mês de referência
horasTrabalhadas	BigDecimal	Horas trabalhadas
salarioLiquido	BigDecimal	Valor líquido
valorValeAlimentacao	BigDecimal	Vale alimentação
valorValeTransporte	BigDecimal	Vale transporte
🧪 Testes Unitários

Execute:

mvn test


Os testes validam:

Autenticação e geração de tokens JWT

Criação e atualização de funcionários

Cálculo de folha de pagamento

🧠 Conceitos de POO Aplicados

Herança: Reutilização de lógica de cálculo entre classes.

Polimorfismo: Diferentes tipos de cálculo salarial com o mesmo método (calcularSalarioFinal()).

Encapsulamento: Atributos protegidos e acessados via getters/setters.

Interface: Contrato de cálculos (Calculavel → calcular()).

💡 Próximos Passos

🔐 Expandir autenticação com controle de roles (ADMIN, USER).

📊 Criar dashboards com gráficos de desempenho no Thymeleaf.

🧾 Adicionar exportação de relatórios em PDF.

💾 Integrar cache e logs de auditoria.

🖥️ Interface com Thymeleaf

O frontend utiliza Thymeleaf para renderização dinâmica de páginas HTML com dados do backend.
Exemplo de páginas planejadas:

Página	Descrição
/login	Tela de login do gerente (autenticação JWT)
/cadastro	Cadastro de novos usuários
/funcionarios	Lista e cadastro de funcionários
/folha	Cálculo e visualização da folha de pagamento
/relatorios	Exibição gráfica de estatísticas salariais
