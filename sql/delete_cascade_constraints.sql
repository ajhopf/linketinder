-- Remove a constraint existente
ALTER TABLE enderecos_usuario
DROP CONSTRAINT enderecos_usuario_usuario_id_fkey;

-- Adiciona a constraint novamente com ON DELETE CASCADE
ALTER TABLE enderecos_usuario
    ADD CONSTRAINT enderecos_usuario_usuario_id_fkey
        FOREIGN KEY (usuario_id)
            REFERENCES usuarios(id)
            ON DELETE CASCADE;

ALTER TABLE enderecos_usuario
DROP CONSTRAINT enderecos_usuario_endereco_id_fkey;

ALTER TABLE enderecos_usuario
    ADD CONSTRAINT enderecos_usuario_endereco_id_fkey
        FOREIGN KEY (endereco_id)
            REFERENCES enderecos(id)
            ON DELETE CASCADE;

ALTER TABLE candidatos
DROP CONSTRAINT candidatos_usuario_id_fkey;

ALTER TABLE candidatos
    ADD CONSTRAINT candidatos_usuario_id_fkey
        FOREIGN KEY (usuario_id)
            REFERENCES usuarios(id)
            ON DELETE CASCADE;

ALTER TABLE empresas
DROP CONSTRAINT empresas_usuario_id_fkey;

ALTER TABLE empresas
    ADD CONSTRAINT empresas_usuario_id_fkey
        FOREIGN KEY (usuario_id)
            REFERENCES usuarios(id)
            ON DELETE CASCADE;

ALTER TABLE vagas
DROP CONSTRAINT vagas_empresa_id_fkey;

ALTER TABLE vagas
    ADD CONSTRAINT vagas_empresa_id_fkey
        FOREIGN KEY (empresa_id)
            REFERENCES empresas(usuario_id)
            ON DELETE CASCADE;


ALTER TABLE competencias_usuario
DROP CONSTRAINT competencias_usuario_usuario_id_fkey;

ALTER TABLE competencias_usuario
    ADD CONSTRAINT competencias_usuario_usuario_id_fkey
        FOREIGN KEY (usuario_id)
            REFERENCES usuarios(id)
            ON DELETE CASCADE;

ALTER TABLE competencias_usuario
DROP CONSTRAINT competencias_usuario_competencia_id_fkey;

ALTER TABLE competencias_usuario
    ADD CONSTRAINT competencias_usuario_competencia_id_fkey
        FOREIGN KEY (competencia_id)
            REFERENCES competencias(id)
            ON DELETE CASCADE;

ALTER TABLE competencias_vaga
DROP CONSTRAINT competencias_vaga_competencia_id_fkey;

ALTER TABLE competencias_vaga
    ADD CONSTRAINT competencias_vaga_competencia_id_fkey
        FOREIGN KEY (competencia_id)
            REFERENCES competencias(id)
            ON DELETE CASCADE;

ALTER TABLE competencias_vaga
DROP CONSTRAINT competencias_vaga_vaga_id_fkey;

ALTER TABLE competencias_vaga
    ADD CONSTRAINT competencias_vaga_vaga_id_fkey
        FOREIGN KEY (vaga_id)
            REFERENCES vagas(id)
            ON DELETE CASCADE;

ALTER TABLE curtidas_em_candidato
DROP CONSTRAINT curtidas_em_candidato_candidato_id_fkey;

ALTER TABLE curtidas_em_candidato
    ADD CONSTRAINT curtidas_em_candidato_candidato_id_fkey
        FOREIGN KEY (candidato_id)
            REFERENCES candidatos(usuario_id)
            ON DELETE CASCADE;

ALTER TABLE curtidas_em_candidato
DROP CONSTRAINT curtidas_em_candidato_empresa_id_fkey;

ALTER TABLE curtidas_em_candidato
    ADD CONSTRAINT curtidas_em_candidato_empresa_id_fkey
        FOREIGN KEY (empresa_id)
            REFERENCES empresas(usuario_id)
            ON DELETE CASCADE;

ALTER TABLE formacoes
DROP CONSTRAINT formacoes_candidato_id_fkey;

ALTER TABLE formacoes
    ADD CONSTRAINT formacoes_candidato_id_fkey
        FOREIGN KEY (candidato_id)
            REFERENCES candidatos(usuario_id)
            ON DELETE CASCADE;

ALTER TABLE curtidas_em_vaga
DROP CONSTRAINT curtidas_em_vaga_candidato_id_fkey;

ALTER TABLE curtidas_em_vaga
    ADD CONSTRAINT curtidas_em_vaga_candidato_id_fkey
        FOREIGN KEY (candidato_id)
            REFERENCES candidatos(usuario_id)
            ON DELETE CASCADE;

ALTER TABLE curtidas_em_vaga
DROP CONSTRAINT curtidas_em_vaga_vaga_id_fkey;

ALTER TABLE curtidas_em_vaga
    ADD CONSTRAINT curtidas_em_vaga_vaga_id_fkey
        FOREIGN KEY (vaga_id)
            REFERENCES vagas(id)
            ON DELETE CASCADE;
