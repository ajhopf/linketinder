CREATE TABLE curtidas_em_candidato (
	id SERIAL PRIMARY KEY,
	candidato_id INT REFERENCES candidatos(usuario_id),
	empresa_id INT REFERENCES empresas(usuario_id)
);

CREATE TABLE curtidas_em_vaga (
	id SERIAL PRIMARY KEY,
	candidato_id INT REFERENCES candidatos(usuario_id),
	vaga_id INT REFERENCES vagas(id)
);

INSERT INTO curtidas_em_candidato (candidato_id, empresa_id) VALUES (8, 1);
INSERT INTO curtidas_em_candidato (candidato_id, empresa_id) VALUES (9, 2);
INSERT INTO curtidas_em_candidato (candidato_id, empresa_id) VALUES (11, 3);
--VAGA 1 É DA EMPRESA 1, VAGA 2 É DA EMPRESA 2...
INSERT INTO curtidas_em_vaga (candidato_id, vaga_id) VALUES (8, 1);
INSERT INTO curtidas_em_vaga (candidato_id, vaga_id) VALUES (8, 2);
INSERT INTO curtidas_em_vaga (candidato_id, vaga_id) VALUES (11, 2);

CREATE VIEW matches AS
WITH vagas_por_empresa AS (
	SELECT 
		e.usuario_id as empresa_id, 
		v.id as vaga_id,
		cv.candidato_id
	FROM empresas e 
		INNER JOIN vagas v ON v.empresa_id = e.usuario_id
		INNER JOIN curtidas_em_vaga cv ON cv.vaga_id = v.id
)
SELECT
    ve.vaga_id,
    ve.candidato_id,
    cc.empresa_id
FROM curtidas_em_candidato cc
	INNER JOIN vagas_por_empresa ve
		ON ve.empresa_id = cc.empresa_id AND ve.candidato_id = cc.candidato_id;

SELECT * FROM matches;


--segundo match
INSERT INTO curtidas_em_vaga (candidato_id, vaga_id) VALUES (9, 2);

