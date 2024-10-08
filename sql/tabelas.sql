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
	usuario_id INT PRIMARY KEY REFERENCES usuarios(id) ON DELETE CASCADE,
	cnpj VARCHAR(18) NOT NULL
);

CREATE TABLE candidatos (
	usuario_id INT PRIMARY KEY REFERENCES usuarios(id) ON DELETE CASCADE,
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
  candidato_id INT REFERENCES candidatos(usuario_id) ON DELETE CASCADE NOT NULL
);

CREATE TABLE enderecos_usuario (
	usuario_id INT REFERENCES usuarios(id ) ON DELETE CASCADE,
	endereco_id INT REFERENCES enderecos(id) ON DELETE CASCADE,
	PRIMARY KEY (usuario_id, endereco_id)
);

CREATE TABLE competencias_candidato (
	id SERIAL PRIMARY KEY,
	usuario_id INT REFERENCES candidatos(usuario_id) ON DELETE CASCADE NOT NULL,
	competencia_id INT REFERENCES competencias(id) NOT NULL,
	anos_experiencia NUMERIC(3,1) NOT NULL,
	afinidade INT NOT NULL
);

CREATE TABLE vagas (
	id SERIAL PRIMARY KEY,
	nome VARCHAR(100) NOT NULL,
	descricao VARCHAR(1000) NOT NULL,
	empresa_id INT REFERENCES empresas(usuario_id) ON DELETE CASCADE NOT NULL,
	endereco_id INT REFERENCES enderecos(id) ON DELETE CASCADE
);

CREATE TABLE competencias_vaga (
	vaga_id INT REFERENCES vagas(id) ON DELETE CASCADE NOT NULL,
	competencia_id INT REFERENCES competencias(id) ON DELETE CASCADE NOT NULL,
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
