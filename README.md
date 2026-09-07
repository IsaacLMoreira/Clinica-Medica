# Clínica Médica IFBA

Projeto desenvolvido para fins acadêmicos no curso de **Bacharelado em Sistemas de Informação (BSI) — IFBA**.

## Tecnologias

* **Java:** 25
* **Banco de dados:** PostgreSQL 18.6

### Informações de configuração

| Configuração   | Valor            |
| -------------- | ---------------- |
| Banco de dados | PostgreSQL 18.6  |
| Porta          | 5432             |
| Locale         | Default          |
| Senha          | `gta6emnovembro` |

Durante a instalação do PostgreSQL, quando o instalador solicitar a criação da senha, utilize:

```text
gta6emnovembro
```

A senha deve ser mantida padronizada para permitir a integração entre os dois projetos acadêmicos.

## Requisitos

Antes de executar o projeto, certifique-se de possuir:

* Java 25 instalado;
* PostgreSQL 18.6 instalado;
* PostgreSQL configurado na porta `5432`;
* Locale definido como `Default`;
* Senha do PostgreSQL configurada como `gta6emnovembro`.

## Execução

Após instalar e configurar o PostgreSQL:

1. Inicie o serviço do PostgreSQL.
2. Configure o banco de dados conforme as instruções presentes no projeto.
3. Verifique se as configurações de conexão estão de acordo com as informações acima.
4. Execute o projeto utilizando **Java 25**.

## Objetivo

Este projeto foi desenvolvido **exclusivamente para fins acadêmicos**, como parte das atividades do curso de **Bacharelado em Sistemas de Informação do IFBA**.

## Autores

Projeto desenvolvido por estudantes do **IFBA — Campus Feira de Santana**.

**COLE NO BANCO DE DADOS**

CREATE TABLE pacientes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100),
	convenio VARCHAR(20)
);
