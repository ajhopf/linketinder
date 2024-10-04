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
