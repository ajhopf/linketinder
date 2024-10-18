package linketinder.repository

import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.model.dtos.CompetenciaDTO
import linketinder.model.enums.Afinidade
import linketinder.model.enums.TabelaCompetencia
import spock.lang.Shared

import java.sql.SQLException

class CompetenciaRepositoryTest extends SetupRepositoryTest {

    @Shared
    CompetenciaRepository competenciaRepository

    def setupSpec() {
        competenciaRepository = new CompetenciaRepository(sql)
    }

    def setup() {
        sql.execute("""
            INSERT INTO competencias (competencia)
            VALUES ('Groovy')
        """)

        //Adiciona Candidato e uma competencia associada
        sql.execute("""
                INSERT INTO usuarios (tipo, nome, email, senha, descricao)
                VALUES ('candidato', 'Andre', 'andre@example.com', 'password', 'Desenvolvedor')
            """)

        Integer userId = sql.firstRow("SELECT id FROM usuarios WHERE email = 'andre@example.com'").id as Integer

        sql.execute("""
                INSERT INTO candidatos (usuario_id, sobrenome, data_nascimento, cpf, telefone)
                VALUES (?, 'Hopf', '1990-01-01', '123.456.789-00', '(48) 99999-9999')
            """, [userId])

        Integer competenciaId = sql.firstRow("SELECT id FROM competencias WHERE competencia = 'Groovy'").id as Integer

        sql.execute("""
                INSERT INTO competencias_candidato(usuario_id, competencia_id, anos_experiencia, afinidade)
                VALUES (?, ?, 3, 5)
            """, [userId, competenciaId])

        //Adiciona empresa e uma competencia associada
        sql.execute("""
                    INSERT INTO usuarios (tipo, nome, email, senha, descricao)
                    VALUES ('empresa', 'Empresa', 'empresa@empresa.com', 'password', 'Empresa top')
                """)

        Integer empresaId = sql.firstRow("SELECT id FROM usuarios WHERE email = 'empresa@empresa.com'").id as Integer

        sql.execute("""
                    INSERT INTO empresas (usuario_id, cnpj)
                    VALUES (?, '123455123')
                """, [empresaId])

        sql.execute("""
                INSERT INTO enderecos(cep, cidade, estado, pais) VALUES ('88063-074', 'Florianópolis', 'Santa Catarina', 'Brasil')
            """)

        Integer enderecoId = sql.firstRow("SELECT id FROM enderecos WHERE cep = '88063-074'").id as Integer

        sql.execute("""
                INSERT INTO vagas (nome, descricao, empresa_id, endereco_id)
                VALUES ('vaga', 'descricao', ?, ?)
            """, [empresaId, enderecoId])

        Integer vagaId = sql.firstRow("SELECT id FROM vagas WHERE nome = 'vaga'").id as Integer
        Integer competenciaVagaId = sql.firstRow("SELECT id FROM competencias WHERE competencia = 'Groovy'").id as Integer

        sql.execute("""
                INSERT INTO competencias_vaga(vaga_id, competencia_id, anos_experiencia, afinidade)
                VALUES (?, ?, 3, 5)
            """, [vagaId, competenciaVagaId])
    }

    def cleanup() {
        sql.execute("DELETE FROM usuarios CASCADE")
        sql.execute("DELETE FROM competencias CASCADE")
        sql.execute("DELETE FROM vagas CASCADE")
    }

    def "listarCompetencias retorna lista com competencias"() {
        when:
            def result = competenciaRepository.listarCompetencias()

        then:
            result.size() == 1
            result[0].competencia == 'Groovy'
    }

    def "listarCompetenciasDeCandidatoOuVaga retorna competencia de candidato"() {
        given:
            Integer userId = sql.firstRow("SELECT id FROM usuarios WHERE email = 'andre@example.com'").id as Integer

        when:
            def result = competenciaRepository
                    .listarCompetenciasDeCandidatoOuVaga(userId, TabelaCompetencia.COMPETENCIAS_CANDIDATO.nomeTabela)

        then:
            result.size() == 1
            result[0].competencia == 'Groovy'
    }

    def "listarCompetenciasDeCandidatoOuVaga retorna competencia de vaga"() {
        given:
            Integer vagaId = sql.firstRow("SELECT id FROM vagas WHERE nome = 'vaga'").id as Integer

        when:
            def result = competenciaRepository
                    .listarCompetenciasDeCandidatoOuVaga(vagaId, TabelaCompetencia.COMPETENCIAS_VAGA.nomeTabela)

        then:
            result.size() == 1
            result[0].competencia == 'Groovy'
    }

    def "obterCompetenciaPeloId lança CompetenciaNotFoundException quando id é inexistente"() {
        when:
            competenciaRepository.obterCompetenciaPeloId(12344255)

        then:
            thrown CompetenciaNotFoundException
    }

    def "obterCompetenciaPeloId retorna competencia"() {
        given:
            List<CompetenciaDTO> competencias = competenciaRepository.listarCompetencias()
            Integer id = competencias[0].id

        when:
            def result = competenciaRepository.obterCompetenciaPeloId(id)

        then:
            result.competencia == 'Groovy'
    }

    def "adicionarCompetencia retorna id da nova competencia"() {
        given:
            String competencia = 'Java'

        when:
            def result = competenciaRepository.adicionarCompetencia(competencia)

        then:
            result != null
    }

    def "adicionarCompetenciaUsuario lança SQLException quando não encontra usuario"() {
        given:
            List<CompetenciaDTO> competencias = competenciaRepository.listarCompetencias()
            Integer id = competencias[0].id
            CompetenciaDTO competenciaDTO = new CompetenciaDTO()
            competenciaDTO.id = id
            competenciaDTO.afinidade = Afinidade.ALTA
            competenciaDTO.anosExperiencia = 3

        when:
            competenciaRepository.adicionarCompetenciaUsuario(competenciaDTO, 1233123123)

        then:
            thrown(SQLException)
    }

    def "adicionarCompetenciaUsuario adiciona Competencias ao usuario"() {
        given:
            sql.execute("""
                        INSERT INTO usuarios (tipo, nome, email, senha, descricao)
                        VALUES ('candidato', 'Andre', 'andre@email.com', 'password', 'Desenvolvedor')
                    """)
            Integer usuarioId = sql.firstRow("SELECT id FROM usuarios WHERE email = 'andre@email.com'").id as Integer

            sql.execute("""
                    INSERT INTO candidatos (usuario_id, sobrenome, data_nascimento, cpf, telefone)
                    VALUES (?, 'Hopf', '1990-01-01', '123.456.789-00', '(48) 99999-9999')
                """, [usuarioId])

            List<CompetenciaDTO> competencias = competenciaRepository.listarCompetencias()
            Integer id = competencias[0].id
            CompetenciaDTO competenciaDTO = new CompetenciaDTO()
            competenciaDTO.id = id
            competenciaDTO.afinidade = Afinidade.ALTA
            competenciaDTO.anosExperiencia = 3

        when:
            competenciaRepository.adicionarCompetenciaUsuario(competenciaDTO, usuarioId)

        then:
            def competenciaCandidato = sql.firstRow("SELECT * FROM competencias_candidato WHERE usuario_id = $usuarioId")

            competenciaCandidato.getProperty('usuario_id') as Integer == usuarioId
    }

    def "obterIdDeCompetencia lança CompetenciaNotFoundException quando competencia é inexistente"() {
        when:
            competenciaRepository.obterIdDeCompetencia('Competencia que nao existe')

        then:
            thrown(CompetenciaNotFoundException)
    }

    def "obterIdDeCompetencia retorna id da competencia"() {
        given:
            Integer expectedId = sql.firstRow("SELECT id FROM competencias WHERE competencia = 'Groovy'").id as Integer

        when:
            def result = competenciaRepository.obterIdDeCompetencia('Groovy')

        then:
            expectedId == result
    }

    def "updateCompetencia altera o nome da competencia"() {
        given:
            Integer competenciaId = sql.firstRow("SELECT id FROM competencias WHERE competencia = 'Groovy'").id as Integer
            CompetenciaDTO competenciaDTO = new CompetenciaDTO()
            competenciaDTO.competencia = 'Competencia Atualizada'

        when:
            competenciaRepository.updateCompetencia(competenciaId, competenciaDTO)

        then:
            String novaCompetencia = sql.firstRow("SELECT competencia FROM competencias").competencia

            novaCompetencia == 'Competencia Atualizada'
    }

    def "updateCompetencia lança CompetenciaNotFoundException quando competencia é inexistente"() {
        given:
            CompetenciaDTO competenciaDTO = new CompetenciaDTO()
            competenciaDTO.competencia = 'Competencia Atualizada'

        when:
            competenciaRepository.updateCompetencia(3342333, competenciaDTO)

        then:
            thrown(CompetenciaNotFoundException)
    }

    def "deleteCompetencia lança CompetenciaNotFoundException quando competencia é inexistente"() {
        when:
            competenciaRepository.deleteCompetencia(3342333)

        then:
            thrown(CompetenciaNotFoundException)
    }

    def "deleteCompetencia deleta competencia"() {
        given:
            Integer competenciaId = sql.firstRow("SELECT id FROM competencias WHERE competencia = 'Groovy'").id as Integer

        when:
            competenciaRepository.deleteCompetencia(competenciaId)

        then:
            null == sql.firstRow("SELECT id FROM competencias WHERE competencia = 'Groovy'")
    }

    def "deleteCompetenciasEntidade deleta todas competencias do usuario"() {
        given:
            Integer usuarioId = sql.firstRow("SELECT id FROM usuarios WHERE email = 'andre@example.com'").id as Integer

            sql.execute("""
                INSERT INTO competencias (competencia) VALUES ('Java')
            """)

            Integer competencia1 = sql.firstRow("SELECT id FROM competencias WHERE competencia = 'Groovy'").id as Integer
            Integer competencia2 = sql.firstRow("SELECT id FROM competencias WHERE competencia = 'Java'").id as Integer

            sql.execute("""
                    INSERT INTO competencias_candidato(usuario_id, competencia_id, anos_experiencia, afinidade)
                    VALUES (?, ?, 3, 5)
                """, [usuarioId, competencia1])

            sql.execute("""
                        INSERT INTO competencias_candidato(usuario_id, competencia_id, anos_experiencia, afinidade)
                        VALUES (?, ?, 3, 5)
                    """, [usuarioId, competencia2])
        when:
            competenciaRepository.deleteCompetenciasEntidade(usuarioId, TabelaCompetencia.COMPETENCIAS_CANDIDATO.nomeTabela)

        then:
            null == sql.firstRow("SELECT id FROM competencias_candidato WHERE usuario_id = $usuarioId")
    }

    def "deleteCompetenciasEntidade deleta todas competencias da vaga"() {
        given:
        Integer vagaId = sql.firstRow("SELECT id FROM vagas WHERE nome = 'vaga'").id as Integer

        sql.execute("""
                INSERT INTO competencias (competencia) VALUES ('Java')
            """)

        Integer competencia1 = sql.firstRow("SELECT id FROM competencias WHERE competencia = 'Groovy'").id as Integer
        Integer competencia2 = sql.firstRow("SELECT id FROM competencias WHERE competencia = 'Java'").id as Integer

        sql.execute("""
                    INSERT INTO competencias_vaga(vaga_id, competencia_id, anos_experiencia, afinidade)
                    VALUES (?, ?, 3, 5)
                """, [vagaId, competencia1])

        sql.execute("""
                        INSERT INTO competencias_vaga(vaga_id, competencia_id, anos_experiencia, afinidade)
                        VALUES (?, ?, 3, 5)
                    """, [vagaId, competencia2])
        when:
        competenciaRepository.deleteCompetenciasEntidade(vagaId, TabelaCompetencia.COMPETENCIAS_VAGA.nomeTabela)

        then:
        null == sql.firstRow("SELECT * FROM competencias_vaga WHERE vaga_id = $vagaId")
    }
}
