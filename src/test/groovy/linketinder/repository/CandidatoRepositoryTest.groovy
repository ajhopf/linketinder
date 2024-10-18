package linketinder.repository

import linketinder.exceptions.CandidatoNotFoundException
import linketinder.model.dtos.CandidatoDTO

import java.sql.SQLException
import java.time.LocalDate

class CandidatoRepositoryTest extends SetupRepositoryTest {
    CandidatoDTO candidatoDTO

    def setup() {
        sql.execute("""
            INSERT INTO usuarios (tipo, nome, email, senha, descricao)
            VALUES ('candidato', 'Andre', 'andre@example.com', 'password', 'Desenvolvedor')
        """)

        def userId = sql.firstRow("SELECT id FROM usuarios WHERE email = 'andre@example.com'").id

        sql.execute("""
            INSERT INTO candidatos (usuario_id, sobrenome, data_nascimento, cpf, telefone)
            VALUES (?, 'Silva', '1990-01-01', '123.456.789-00', '(48) 99999-9999')
        """, [userId])

        this.candidatoDTO = new CandidatoDTO(
                nome: 'Andre',
                email: 'andre@example.com',
                senha: 'password',
                descricao: 'Desenvolvedor',
                sobrenome: 'Silva',
                dataNascimento: new LocalDate(1991, 1, 11),
                cpf: '123.456.789-00',
                telefone: '(48) 99999-9999',
        )
    }

    def cleanup() {
        sql.execute("DELETE FROM usuarios")
    }

    def "listarCandidatos retorna todos candidatos"() {
        when:
        def result = repository.listarCandidatos()

        then:
        result.size() == 1
        result[0].nome == 'Andre'
        result[0].sobrenome == 'Silva'
    }

    def "obterCandidatoPeloId lança CandidatoNotFoundExcpetion quando não encontra candidato com o id fornecido"() {
        when:
        repository.obterCandidatoPeloId(304098)

        then:
        thrown(CandidatoNotFoundException)
    }

    def "obterCandidatoPeloId retorna candidato com o id fornecido"() {
        given: "Lista os candidatos e utiliza o id do único candidato existente"
        List<CandidatoDTO> candidatos = repository.listarCandidatos()
        Integer id = candidatos[0].id

        when:
        CandidatoDTO candidato = repository.obterCandidatoPeloId(id)

        then:
        candidato.id == id
        candidato.nome == "Andre"
    }

    def "adicionarCandidato insere candidato e retorna o ID do usuário"() {
        when: "adicionarCandidato é chamado utilizando o candidatoDTO"
        def userId = repository.adicionarCandidato(candidatoDTO)

        then: "O id retornado é válido"
        userId != null

        and: "Usuário criado na tabela de usuários"
        def usuario = sql.firstRow("SELECT * FROM usuarios WHERE id = ?", [userId])
        usuario.nome == 'Andre'
        usuario.email == 'andre@example.com'

        and: "Candidato foi criado na tabela de candidatos"
        def candidato = sql.firstRow("SELECT * FROM candidatos WHERE usuario_id = ?", [userId])
        candidato.sobrenome == 'Silva'
        candidato.data_nascimento.toString() == '1991-01-11'
        candidato.cpf == '123.456.789-00'
    }

    def "adicionarCandidato não adiciona usuário se informação de candidato é inválida"() {
        given: "CandidatoDTO inválido -> cpf null"
        candidatoDTO.cpf = null

        when: "adicionarCandidato é executado com o candidatoDTO"
        repository.adicionarCandidato(candidatoDTO)

        then: "Lança uma SQLException"
        thrown(SQLException)

        and: "Contém apenas o usuário inserido antes do início do teste"
        def count = sql.firstRow("SELECT COUNT(*) AS total FROM usuarios").total
        count == 1
    }

    def "updateCandidato lança CandidatoNotFoundException quando invocado com id inexistente"() {
        given:
        candidatoDTO.id = 1234555

        when:
        repository.updateCandidato(candidatoDTO)

        then:
        thrown(CandidatoNotFoundException)
    }

    def "updateCandidato faz atualização do Usuário e do Candidato"() {
        given: "Busca o candidato existente e altera o nome e o cpf"
        List<CandidatoDTO> candidatos = repository.listarCandidatos()
        CandidatoDTO candidatoDTOAtualizado = candidatos[0]
        candidatoDTOAtualizado.nome = 'Um novo nome'
        candidatoDTOAtualizado.cpf = '999.999.999-00'

        when:
        repository.updateCandidato(candidatoDTOAtualizado)

        then:
        "Um novo nome" == sql.firstRow("SELECT * FROM usuarios").nome
        '999.999.999-00' == sql.firstRow("SELECT * FROM candidatos").cpf
    }

    def "deletarCandidato lança CandidatoNotFoundException quando invocado com id inexistente"(){
        when:
        repository.deletarCandidatoPeloId(1234455)

        then:
        thrown(CandidatoNotFoundException)
    }
}
