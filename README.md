# 📊 Projeto de Gestão de Portfólio de Projetos

Este é um sistema de gestão de projetos desenvolvido como desafio prático para consolidar conhecimentos em Java, Spring Boot, JPA/Hibernate e boas práticas de arquitetura e desenvolvimento. O foco é simular um ambiente corporativo com regras de negócio reais e complexas.


## 🚀 Funcionalidades


CRUD completo de projetos

Cálculo dinâmico de classificação de risco (baixo, médio ou alto)

Controle rigoroso de transições de status dos projetos

Associação de membros aos projetos via API externa mockada

Restrições de alocação de membros (máx. 3 projetos simultâneos)

Relatório resumido do portfólio com dados agregados

Segurança básica com Spring Security (usuário/senha em memória)

Paginação e filtros para listagem de projetos

# 🧐 Regras de Negócio


## Projeto

Campos obrigatórios:

Nome

Data de início

Previsão de término

Data real de término

Orçamento total (BigDecimal)

Descrição

Gerente responsável (relacionado à entidade membro)

Status atual


## 📊 Classificação de Risco (calculada automaticamente)

Baixo risco: orçamento ≤ R$100.000 e prazo ≤ 3 meses

Médio risco: orçamento entre R$100.001 e R$500.000 ou prazo entre 3 a 6 meses

Alto risco: orçamento > R$500.000 ou prazo > 6 meses


## 🔄 Status dos Projetos

Os status possíveis seguem a ordem fixa abaixo (não cadastráveis):

em análise → análise realizada → análise aprovada → iniciado → planejado → em andamento → encerrado

Observação: O status cancelado pode ser aplicado a qualquer momento.
Não é permitido pular etapas da transição de status.


## 🔐 Restrições

Projetos com status iniciado, em andamento ou encerrado não podem ser excluídos

Cada projeto deve ter de 1 a 10 membros

Apenas membros com atribuição “funcionário” podem ser associados

Um mesmo membro pode participar de no máximo 3 projetos ativos (status ≠ encerrado ou cancelado)


## 📦 API Externa de Membros

Mock API para criar e consultar membros, com os campos:

Nome
Atribuição (cargo)


## 📈 Relatório de Portfólio (endpoint /relatorio)

Quantidade de projetos por status

Total orçado por status

Média de duração dos projetos encerrados

Total de membros únicos alocados


## 🧱 Tecnologias e Implementação


✅ Spring Boot + Spring Security

✅ JPA + Hibernate

✅ PostgreSQL

✅ Arquitetura MVC

✅ DTOs + ModelMapper/MapStruct

✅ Swagger/OpenAPI para documentação

✅ Tratamento global de exceções

✅ Clean Code & SOLID

✅ Testes unitários (mín. 70% de cobertura nas regras de negócio)

✅ Paginação e Filtros

✅ Separação clara entre Controller, Service e Repository


# ⚙️ Como executar o projeto


## Clone o repositório
git clone https://github.com/gguedes00/GestaoDeProjetos.git

## Acesse a pasta do projeto
cd nome-do-repo

## Configure o banco de dados PostgreSQL (arquivo application.yml)

## Compile e execute
./mvnw spring-boot:run


## 🔐 Acesso ao sistema

Usuário: admin

Senha: admin123

(definido via configuração em memória no Spring Security)


# 🧪 Testes


## Para executar os testes

./mvnw test
Cobertura mínima de 70% nas regras de negócio.

## 📚 Documentação da API

A documentação gerada via Swagger estará disponível em:

http://localhost:8080/swagger-ui.html
