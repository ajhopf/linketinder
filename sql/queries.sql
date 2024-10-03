CREATE TABLE competencias (
	id SERIAL PRIMARY KEY,
	competencia VARCHAR(100)
);

CREATE TABLE enderecos (
	id SERIAL PRIMARY KEY,
	cep VARCHAR(9),
	cidade VARCHAR(100) NOT NULL,
	estado VARCHAR(100) NOT NULL,
	pais VARCHAR(100) NOT NULL
);

CREATE TYPE tipo_usuario AS ENUM ('empresa', 'candidato');

CREATE TABLE usuarios (
   	id SERIAL PRIMARY KEY,
    tipo tipo_usuario NOT NULL,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    descricao VARCHAR NOT NULL
);

CREATE TABLE empresas (
	usuario_id INT PRIMARY KEY REFERENCES usuarios(id),
	cnpj VARCHAR(18) NOT NULL
);

CREATE TABLE candidatos (
	usuario_id INT PRIMARY KEY REFERENCES usuarios(id),
	sobrenome VARCHAR(50) NOT NULL,
	data_nascimento DATE NOT NULL,
	cpf VARCHAR(14) NOT NULL,
	telefone VARCHAR(30) NOT NULL
);

CREATE TABLE formacoes (
  id SERIAL PRIMARY KEY,
  formacao VARCHAR(100) NOT NULL,
  instituicao VARCHAR(100) NOT NULL,
  ano_de_finalizacao DATE NOT NULL,
  candidato_id INT REFERENCES candidatos(usuario_id) NOT NULL
);

CREATE TABLE enderecos_usuario (
	usuario_id INT REFERENCES usuarios(id ),
	endereco_id INT REFERENCES enderecos(id),
	PRIMARY KEY (usuario_id, endereco_id)
);

CREATE TABLE competencias_usuario (
	id SERIAL PRIMARY KEY,
	usuario_id INT REFERENCES usuarios(id) NOT NULL,
	competencia_id INT REFERENCES competencias(id) NOT NULL,
	anos_experiencia NUMERIC(3,1) NOT NULL,
	afinidade INT NOT NULL
);

CREATE TABLE vagas (
	id SERIAL PRIMARY KEY,
	nome VARCHAR(100) NOT NULL,
	descricao VARCHAR(1000) NOT NULL,
	empresa_id INT REFERENCES empresas(usuario_id),
	endereco_id INT REFERENCES enderecos(id)
);

CREATE TABLE competencias_vaga (
	vaga_id INT REFERENCES vagas(id),
	competencia_id INT REFERENCES competencias(id),
	anos_experiencia NUMERIC(3,1) NOT NULL,
	afinidade INT NOT NULL
);


INSERT INTO competencias (competencia) VALUES ('Java');
INSERT INTO competencias (competencia) VALUES ('JavaScript');
INSERT INTO competencias (competencia) VALUES ('C#');
INSERT INTO competencias (competencia) VALUES ('Node.js');
INSERT INTO competencias (competencia) VALUES ('PostgreSQL');
INSERT INTO competencias (competencia) VALUES ('MongoDB');
INSERT INTO competencias (competencia) VALUES ('React');
INSERT INTO competencias (competencia) VALUES ('PHP');
INSERT INTO competencias (competencia) VALUES ('Spring Boot');
INSERT INTO competencias (competencia) VALUES ('Groovy');

INSERT INTO enderecos (cep, cidade, estado, pais) VALUES ('88063-074', 'Florianópolis', 'Santa Catarina', 'Brasil');
INSERT INTO enderecos (cep, cidade, estado, pais) VALUES ('91920-010', 'Porto Alegre', 'Rio Grande do Sul', 'Brasil');
INSERT INTO enderecos (cep, cidade, estado, pais) VALUES ('74070-040', 'Goiânia', 'Goiás', 'Brasil');
INSERT INTO enderecos (cep, cidade, estado, pais) VALUES ('80030-000', 'Curitiba', 'Paraná', 'Brasil');
INSERT INTO enderecos (cep, cidade, estado, pais) VALUES ('41610-540', 'Salvador', 'Bahia', 'Brasil');

--Empresas
--Emp1
INSERT INTO usuarios (tipo, nome, email, senha, descricao) 
VALUES ('empresa', 'Data Masters Ltd.', 'info@datamasters.com.br', 'securepass', 'Experts in data analytics and machine learning.');
INSERT INTO empresas (usuario_id, cnpj) VALUES (1, '98.765.432/1098-76');
INSERT INTO competencias_usuario (usuario_id, competencia_id, anos_experiencia, afinidade) VALUES (1, 2, 3, 5);
INSERT INTO enderecos_usuario (usuario_id, endereco_id) VALUES (1, 1);
INSERT INTO vagas (nome, descricao, empresa_id, endereco_id) VALUES ('Back End Java', 'Programador Java', 1, 1);
INSERT INTO competencias_vaga (competencia_id, anos_experiencia, afinidade) VALUES (1, 3, 5);
--Emp2
INSERT INTO usuarios (tipo, nome, email, senha, descricao) 
VALUES ('empresa', 'Web Wizards Corp.', 'hello@webwizards.com.br', 'webpass', 'Crafting beautiful and functional websites.');
INSERT INTO empresas (usuario_id, cnpj) VALUES (2, '56.789.012/3456-78');
INSERT INTO competencias_usuario (usuario_id, competencia_id, anos_experiencia, afinidade) VALUES (2, 4, 2.5, 3);
INSERT INTO enderecos_usuario (usuario_id, endereco_id) VALUES (2, 2);
INSERT INTO vagas (nome, descricao, empresa_id, endereco_id) VALUES ('Front End JavaScript', 'Programador JS', 2, 2);
INSERT INTO competencias_vaga (competencia_id, anos_experiencia, afinidade) VALUES (2, 2, 5);
--Emp3
INSERT INTO usuarios (tipo, nome, email, senha, descricao) 
VALUES ('empresa', 'Mobile Mavericks LLC.', 'contact@mobilemavericks.com.br', 'mobile123', 'Building cutting-edge mobile applications.');
INSERT INTO empresas (usuario_id, cnpj) VALUES (3, '34.567.890/1234-56');
INSERT INTO competencias_usuario (usuario_id, competencia_id, anos_experiencia, afinidade) VALUES (3, 9, 5, 5);
INSERT INTO enderecos_usuario (usuario_id, endereco_id) VALUES (3, 3);
INSERT INTO vagas (nome, descricao, empresa_id, endereco_id) VALUES ('Spring Boot', 'Spring Senior', 3, 3);
INSERT INTO competencias_vaga (competencia_id, anos_experiencia, afinidade) VALUES (9, 5, 5);
--Emp4
INSERT INTO usuarios (tipo, nome, email, senha, descricao) 
VALUES ('empresa', 'Cloud Ninjas Inc.', 'info@cloudninjas.com.br', 'cloudpass', 'Masters of cloud infrastructure and DevOps.');
INSERT INTO empresas (usuario_id, cnpj) VALUES (4, '78.901.234/5678-90');
INSERT INTO competencias_usuario (usuario_id, competencia_id, anos_experiencia, afinidade) VALUES (4, 5, 5, 5);
INSERT INTO enderecos_usuario (usuario_id, endereco_id) VALUES (4, 4);
INSERT INTO vagas (nome, descricao, empresa_id, endereco_id) VALUES ('Devops', 'Devops intern', 4, 4);
INSERT INTO competencias_vaga (competencia_id, anos_experiencia, afinidade) VALUES (10, 1, 5);
--Emp5
INSERT INTO usuarios (tipo, nome, email, senha, descricao) 
VALUES ('empresa', 'Game Gurus Ltda.', 'contato@gamegurus.com.br', 'gameon', 'Developing immersive gaming experiences.');
INSERT INTO empresas (usuario_id, cnpj) VALUES (5, '24.681.357/9135-79');
INSERT INTO competencias_usuario (usuario_id, competencia_id, anos_experiencia, afinidade) VALUES (5, 7, 6, 5);
INSERT INTO enderecos_usuario (usuario_id, endereco_id) VALUES (5, 5);
INSERT INTO vagas (nome, descricao, empresa_id, endereco_id) VALUES ('C++', 'Sistemas embarcados', 5, 5);
INSERT INTO competencias_vaga (competencia_id, anos_experiencia, afinidade) VALUES (8, 4, 5);

--Candidatos
--Can1
INSERT INTO usuarios (tipo, nome, email, senha, descricao) 
VALUES ('candidato', 'Pedro Henrique', 'pedro.lima@example.com', '$2a$10$outra_senha_segura_aqui', 'Desenvolvedor Back-End Sênior.');
INSERT INTO candidatos (usuario_id, sobrenome, data_nascimento, cpf, telefone) VALUES (8, 'Lima', '1992-03-12', '022.567.432-01', '(48) 99904-3827');
INSERT INTO competencias_usuario (usuario_id, competencia_id, anos_experiencia, afinidade) VALUES (8, 6, 6, 3);
INSERT INTO competencias_usuario (usuario_id, competencia_id, anos_experiencia, afinidade) VALUES (8, 6, 6, 3);
INSERT INTO enderecos_usuario (usuario_id, endereco_id) VALUES (8, 1);
INSERT INTO formacoes (formacao, instituicao, ano_de_finalizacao, candidato_id) VALUES ('Ciência da Computação', 'UNISUL', '2015-12-01', 8);
--Can2
INSERT INTO usuarios (tipo, nome, email, senha, descricao) 
VALUES ('candidato', 'Laura', 'laura.ferreira@example.com', '$2a$10$outra_senha_segura_aqui', 'Desenvolvedora Front-End Júnior.');
INSERT INTO candidatos (usuario_id, sobrenome, data_nascimento, cpf, telefone) VALUES (9, 'Ferreia', '1998-03-12', '022.567.322-01', '(41) 99904-3827');
INSERT INTO competencias_usuario (usuario_id, competencia_id, anos_experiencia, afinidade) VALUES (9, 3, 1, 5);
INSERT INTO competencias_usuario (usuario_id, competencia_id, anos_experiencia, afinidade) VALUES (9, 10, 0.5, 4);
INSERT INTO enderecos_usuario (usuario_id, endereco_id) VALUES (9, 2);
INSERT INTO formacoes (formacao, instituicao, ano_de_finalizacao, candidato_id) VALUES ('Ciência da Computação', 'UNISUL', '2023-12-01', 9);
--Can3
INSERT INTO usuarios (tipo, nome, email, senha, descricao) 
VALUES ('candidato', 'Carlos Eduardo', 'carlos.costa@example.com', '$2a$10$outra_senha_segura_aqui', 'Desenvolvedor Back-End Pleno');
INSERT INTO candidatos (usuario_id, sobrenome, data_nascimento, cpf, telefone) VALUES (10, 'Ferreia', '1998-03-12', '321.654.987-00', '(51) 93204-3827');
INSERT INTO competencias_usuario (usuario_id, competencia_id, anos_experiencia, afinidade) VALUES (10, 4, 3, 5);
INSERT INTO competencias_usuario (usuario_id, competencia_id, anos_experiencia, afinidade) VALUES (10, 8, 2, 3);
INSERT INTO enderecos_usuario (usuario_id, endereco_id) VALUES (10, 2);
INSERT INTO formacoes (formacao, instituicao, ano_de_finalizacao, candidato_id) VALUES ('Análise de Sistemas', 'USP', '2020-12-01', 10);
--Can4
INSERT INTO usuarios (tipo, nome, email, senha, descricao) 
VALUES ('candidato', 'Fernando', 'fernando.silva@example.com', 'segurança_plena', 'Desenvolvedor Back-End Pleno');
INSERT INTO candidatos (usuario_id, sobrenome, data_nascimento, cpf, telefone) VALUES (11, 'Silva', '2002-08-12', '456.789.123-01', '(35) 93204-3227');
INSERT INTO competencias_usuario (usuario_id, competencia_id, anos_experiencia, afinidade) VALUES (11, 7, 3, 5);
INSERT INTO competencias_usuario (usuario_id, competencia_id, anos_experiencia, afinidade) VALUES (11, 5, 2, 3);
INSERT INTO enderecos_usuario (usuario_id, endereco_id) VALUES (11, 2);
INSERT INTO formacoes (formacao, instituicao, ano_de_finalizacao, candidato_id) VALUES ('Análise de Sistemas', 'PUC', '2019-12-01', 11);
--Can5
INSERT INTO usuarios (tipo, nome, email, senha, descricao) 
VALUES ('candidato', 'Juliana', 'juliana.santos@example.com', 'umasenhasegura', 'Desenvolvedora Mobile Júnior');
INSERT INTO candidatos (usuario_id, sobrenome, data_nascimento, cpf, telefone) VALUES (12, 'Santos', '1999-11-12', '336.789.123-01', '(47) 93204-3227');
INSERT INTO competencias_usuario (usuario_id, competencia_id, anos_experiencia, afinidade) VALUES (12, 3, 2, 5);
INSERT INTO competencias_usuario (usuario_id, competencia_id, anos_experiencia, afinidade) VALUES (12, 9, 1, 3);
INSERT INTO enderecos_usuario (usuario_id, endereco_id) VALUES (12, 2);
INSERT INTO formacoes (formacao, instituicao, ano_de_finalizacao, candidato_id) VALUES ('Análise de Sistemas', 'UFSC', '2019-12-01', 12);
