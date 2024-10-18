package linketinder.repository

import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification

class SetupRepositoryTest extends Specification {
    @Shared
    static Sql sql

    @Shared
    static CandidatoRepository repository

    void setupSpec() {
        println 'Setting up database for all tests'
        final String url = 'jdbc:postgresql://localhost:5432/linketinder_tests'

        final String user = 'andre'

        final String password = '020917'

        final String driver = 'org.postgresql.Driver'

        sql = Sql.newInstance(url, user, password, driver)

        repository = new CandidatoRepository(sql)

        // Criação do tipo e das tabelas, caso não existam
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
    }

    void cleanupSpec() {
        sql.execute("DROP TABLE IF EXISTS candidatos")
        sql.execute("DROP TABLE IF EXISTS usuarios")
        sql.execute("DROP TYPE IF EXISTS tipo_usuario")
    }
}
