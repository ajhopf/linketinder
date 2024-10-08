CREATE TABLE curtidas_em_candidato (
	id SERIAL PRIMARY KEY,
	candidato_id INT REFERENCES candidatos(usuario_id) ON DELETE CASCADE NOT NULL,
	empresa_id INT REFERENCES empresas(usuario_id) ON DELETE CASCADE NOT NULL
);

CREATE TABLE curtidas_em_vaga (
	id SERIAL PRIMARY KEY,
	candidato_id INT REFERENCES candidatos(usuario_id) ON DELETE CASCADE NOT NULL,
	vaga_id INT REFERENCES vagas(id) ON DELETE CASCADE NOT NULL
);

INSERT INTO curtidas_em_candidato (candidato_id, empresa_id) VALUES (7, 1);
INSERT INTO curtidas_em_candidato (candidato_id, empresa_id) VALUES (8, 2);
INSERT INTO curtidas_em_candidato (candidato_id, empresa_id) VALUES (10, 3);
--VAGA 1 É DA EMPRESA 1, VAGA 2 É DA EMPRESA 2...
INSERT INTO curtidas_em_vaga (candidato_id, vaga_id) VALUES (8, 1);
INSERT INTO curtidas_em_vaga (candidato_id, vaga_id) VALUES (8, 2);
INSERT INTO curtidas_em_vaga (candidato_id, vaga_id) VALUES (6, 2);

CREATE VIEW matches AS
WITH vagas_por_empresa AS (
    SELECT
        v.empresa_id as empresa_id,
        v.id as vaga_id,
        cv.candidato_id
    FROM
        vagas v
            INNER JOIN curtidas_em_vaga cv
                ON cv.vaga_id = v.id
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
INSERT INTO curtidas_em_vaga (candidato_id, vaga_id) VALUES (10, 3);

