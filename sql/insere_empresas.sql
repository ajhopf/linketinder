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
