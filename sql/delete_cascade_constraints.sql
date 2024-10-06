delete from candidatos c where c.usuario_id > 20;
select * from enderecos_usuario;

-- Remove a constraint existente
ALTER TABLE enderecos_usuario
DROP CONSTRAINT enderecos_usuario_usuario_id_fkey;

-- Adiciona a constraint novamente com ON DELETE CASCADE
ALTER TABLE enderecos_usuario
    ADD CONSTRAINT enderecos_usuario_usuario_id_fkey
        FOREIGN KEY (usuario_id)
            REFERENCES usuarios(id)
            ON DELETE CASCADE;

ALTER TABLE candidatos
DROP CONSTRAINT candidatos_usuario_id_fkey;

ALTER TABLE candidatos
    ADD CONSTRAINT candidatos_usuario_id_fkey
        FOREIGN KEY (usuario_id)
            REFERENCES usuarios(id)
            ON DELETE CASCADE;

ALTER TABLE competencias_usuario
DROP CONSTRAINT competencias_usuario_usuario_id_fkey;

ALTER TABLE competencias_usuario
    ADD CONSTRAINT competencias_usuario_usuario_id_fkey
        FOREIGN KEY (usuario_id)
            REFERENCES usuarios(id)
            ON DELETE CASCADE;

ALTER TABLE curtidas_em_candidato
DROP CONSTRAINT curtidas_em_candidato_candidato_id_fkey;

ALTER TABLE curtidas_em_candidato
    ADD CONSTRAINT curtidas_em_candidato_candidato_id_fkey
        FOREIGN KEY (candidato_id)
            REFERENCES candidatos(usuario_id)
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