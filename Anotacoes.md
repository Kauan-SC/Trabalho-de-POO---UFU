# Trabalho de POO - Passo a Passo

1- Etapa:
[x] - Criação do sistema básico de biblioteca (Cadastro de Biblioteca, Serviço, Opções, Livos)
[X] - Reestruturação do sistema adicionando Objeto Pessoa(Aluno, Bibliotecário, Professor)
[X] - Nomear e separar de forma congruente cada ação para fácil identificação
[X] - Conectar java a um Banco de Dados
[X] - Criar Front-End para projeto
[ ] - Realizar testes de Adição, Emprestimo e outros...


## Vídeos/Tutoriais Assistidos

### Criação do código

Como fazer um sistema de biblioteca em Java [Parte 1/3]
Link -> https://www.youtube.com/watch?v=fKVW1yAukeM

------------------------------------------------------------------------------------------------------------------------------------------------------------------

Como fazer um sistema de biblioteca em Java [Parte 2/3]
Link -> https://www.youtube.com/watch?v=kKKOiYzBYX4

------------------------------------------------------------------------------------------------------------------------------------------------------------------

Como fazer um sistema de biblioteca em Java [Parte 3/3]
Link -> https://www.youtube.com/watch?v=xVNPmjhfgtg

------------------------------------------------------------------------------------------------------------------------------------------------------------------

Como fazer Herança de classes no Java - Curso Java POO Aula #11
Link -> https://www.youtube.com/watch?v=MemZOGOrjag

------------------------------------------------------------------------------------------------------------------------------------------------------------------

### Criação do Banco de Dados

Como instalar e configurar o Apache Maven Java em 5 minutos
Link -> https://www.youtube.com/watch?v=rfhTnfbBQcY

------------------------------------------------------------------------------------------------------------------------------------------------------------------

Como conectar o Java com banco de dados MySQL do ZERO usando JDBC no Visual Studio Code
Link -> https://www.youtube.com/watch?v=-2gEoKtkxW4

------------------------------------------------------------------------------------------------------------------------------------------------------------------

Como INSTALAR e CONFIGURAR o MYSQL - O Guia Completo
Link -> https://www.youtube.com/watch?v=oi3UHWXLxLs

------------------------------------------------------------------------------------------------------------------------------------------------------------------

# Passo a passo para rodar o projeto

## Primeiro passo

Baixar o JDK(Se não tiver. Sendo obrigatório o JDK 17) ->  https://adoptium.net/temurin/releases/?version=17&os=windows
Baixar o Maven -> https://maven.apache.org
Baixar o MySQL -> https://www.mysql.com

## Segundo passo

Conectando o Banco de dados(Como é um projeto local, então crie o Arquivo ".env" e cole esses dados nele):

DB_HOST=localhost
DB_PORT=3306
DB_NAME=biblioteca_ufu
DB_USER=root
DB_PASSWORD=Matte@123

## Terceiro passo

Após baixar e criar o seu SCHEMA no MySQL, cria a tabela, ela precisa bater com as informações de cada objeto.
Copie o código abaixo e execute no MySQL ->

=---------------------------------------------------=

CREATE DATABASE IF NOT EXISTS biblioteca_ufu;
USE biblioteca_ufu;

CREATE TABLE livros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(150) NOT NULL,
    ano INT NOT NULL,
    disponivel BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE pessoas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    cpf VARCHAR(20) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    matricula VARCHAR(30),
    curso VARCHAR(100),
    departamento VARCHAR(100),
    disciplina VARCHAR(100),
    matricula_funcional VARCHAR(30)
);

CREATE TABLE emprestimos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    livro_id INT NOT NULL,
    pessoa_id INT NOT NULL,
    data_emprestimo DATE NOT NULL,
    data_devolucao DATE,
    FOREIGN KEY (livro_id) REFERENCES livros(id),
    FOREIGN KEY (pessoa_id) REFERENCES pessoas(id)
);

## Quarto passo

Para realizar os testes, use o código abaixo para cadastrar alguns usuários no banco de dados
Esse código cadastra ->

3 Livros
5 Alunos
1 Professor
1 Bibliotecário

Valores e dados gerados de forma aleatória

=---------------------------------------------------=

USE biblioteca_ufu;

-- Livros
INSERT INTO livros (titulo, autor, ano, disponivel) VALUES
('Dom Casmurro', 'Machado de Assis', 1899, TRUE),
('Clean Code', 'Robert C. Martin', 2008, TRUE),
('Introdução à Programação com Python', 'Nilo Ney Coutinho Menezes', 2019, TRUE);

-- Professor
INSERT INTO pessoas (nome, cpf, tipo, matricula, curso, departamento, disciplina, matricula_funcional) VALUES
('Carlos Eduardo Ramos', '111.111.111-11', 'Professor', NULL, NULL, 'Ciência da Computação', 'Estrutura de Dados', NULL);

-- Alunos (5)
INSERT INTO pessoas (nome, cpf, tipo, matricula, curso, departamento, disciplina, matricula_funcional) VALUES
('Ana Beatriz Lima', '222.222.222-22', 'Aluno', '2024001', 'Engenharia de Software', NULL, NULL, NULL),
('Bruno Henrique Costa', '333.333.333-33', 'Aluno', '2024002', 'Ciência da Computação', NULL, NULL, NULL),
('Carla Fernanda Souza', '444.444.444-44', 'Aluno', '2024003', 'Sistemas de Informação', NULL, NULL, NULL),
('Diego Alves Pereira', '555.555.555-55', 'Aluno', '2024004', 'Engenharia de Software', NULL, NULL, NULL),
('Elisa Martins Rocha', '666.666.666-66', 'Aluno', '2024005', 'Ciência da Computação', NULL, NULL, NULL);

-- Bibliotecário
INSERT INTO pessoas (nome, cpf, tipo, matricula, curso, departamento, disciplina, matricula_funcional) VALUES
('Fernanda Ribeiro Santos', '777.777.777-77', 'Bibliotecario', NULL, NULL, NULL, NULL, 'FUNC-0001');

## Último passo

Execute o programa com esse código ->

=---------------------------------------------------=

mvn exec:java

=---------------------------------------------------=

Após isso, realize o teste de pegar emprestado um livro com um aluno e depois tentar pegar emprestado com outro
Ou tente cadastrar mais Professores, Bibliotecários ou Alunos
Feche o programa e abra novamente e verifique se salvou...