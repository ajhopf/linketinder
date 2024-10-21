package linketinder.repository

import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification

class SetupRepositoryTest extends Specification {
    @Shared
    static Sql sql

    void setupSpec() {
        println 'Setting up database for all tests'
        final String url = 'jdbc:postgresql://localhost:5432/linketinder_tests'

        final String user = 'andre'

        final String password = '020917'

        final String driver = 'org.postgresql.Driver'

        sql = Sql.newInstance(url, user, password, driver)

        sql.execute("CREATE TYPE tipo_usuario AS ENUM ('empresa', 'candidato')")

        sql.execute("""
            CREATE TABLE IF NOT EXISTS usuarios (
                id SERIAL PRIMARY KEY,
                tipo tipo_usuario,
                nome VARCHAR(50),
                email VARCHAR(50),
                senha VARCHAR(50),
                descricao VARCHAR(255)
            )
        """)

        sql.execute("""
            CREATE TABLE IF NOT EXISTS candidatos (
                usuario_id INT PRIMARY KEY REFERENCES usuarios(id) ON DELETE CASCADE,
                sobrenome VARCHAR(50) NOT NULL,
                data_nascimento DATE NOT NULL,
                cpf VARCHAR(14) NOT NULL,
                telefone VARCHAR(30) NOT NULL
            )
        """)

        sql.execute("""
            CREATE TABLE IF NOT EXISTS empresas (
                usuario_id INT PRIMARY KEY REFERENCES usuarios(id) ON DELETE CASCADE,
                cnpj VARCHAR(18) NOT NULL
        );
        """)

        sql.execute("""
            CREATE TABLE IF NOT EXISTS competencias (
                id SERIAL PRIMARY KEY,
                competencia VARCHAR(100)
            );
        """)

        sql.execute("""
            CREATE TABLE IF NOT EXISTS enderecos (
                id SERIAL PRIMARY KEY,
                cep VARCHAR(9),
                cidade VARCHAR(100) NOT NULL,
                estado VARCHAR(100) NOT NULL,
                pais VARCHAR(100) NOT NULL
            );
        """)

        sql.execute("""
            CREATE TABLE IF NOT EXISTS enderecos_usuario (
                usuario_id INT REFERENCES usuarios(id ) ON DELETE CASCADE,
                endereco_id INT REFERENCES enderecos(id) ON DELETE CASCADE,
                PRIMARY KEY (usuario_id, endereco_id)
            );
        """)

        sql.execute("""
            CREATE TABLE IF NOT EXISTS vagas (
                id SERIAL PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                descricao VARCHAR(1000) NOT NULL,
                empresa_id INT REFERENCES empresas(usuario_id) ON DELETE CASCADE NOT NULL,
                endereco_id INT REFERENCES enderecos(id) ON DELETE CASCADE
        );
        """)

        sql.execute("""
            CREATE TABLE IF NOT EXISTS competencias_vaga (
                vaga_id INT REFERENCES vagas(id) ON DELETE CASCADE NOT NULL,
                competencia_id INT REFERENCES competencias(id) ON DELETE CASCADE NOT NULL,
                anos_experiencia NUMERIC(3,1) NOT NULL,
                afinidade INT NOT NULL
            );
        """)

        sql.execute("""
            CREATE TABLE IF NOT EXISTS competencias_candidato (
                id SERIAL PRIMARY KEY,
                usuario_id INT REFERENCES candidatos(usuario_id) ON DELETE CASCADE NOT NULL,
                competencia_id INT REFERENCES competencias(id) ON DELETE CASCADE NOT NULL,
                anos_experiencia NUMERIC(3,1) NOT NULL,
                afinidade INT NOT NULL
        );
        """)
    }

    void cleanupSpec() {
        sql.execute("DROP TABLE IF EXISTS usuarios CASCADE")
        sql.execute("DROP TABLE IF EXISTS candidatos CASCADE")
        sql.execute("DROP TABLE IF EXISTS competencias CASCADE")
        sql.execute("DROP TABLE IF EXISTS empresas CASCADE")
        sql.execute("DROP TABLE IF EXISTS enderecos CASCADE")
        sql.execute("DROP TABLE IF EXISTS enderecos_usuario CASCADE")
        sql.execute("DROP TABLE IF EXISTS vagas CASCADE")
        sql.execute("DROP TABLE IF EXISTS competencias_vaga CASCADE")
        sql.execute("DROP TABLE IF EXISTS competencias_candidato CASCADE")
        sql.execute("DROP TYPE IF EXISTS tipo_usuario")
    }
}
