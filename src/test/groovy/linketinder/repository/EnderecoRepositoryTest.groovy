package linketinder.repository

import linketinder.model.dtos.EnderecoDTO
import spock.lang.Shared

import java.sql.SQLException

class EnderecoRepositoryTest extends SetupRepositoryTest {

    @Shared
    EnderecoRepository enderecoRepository

    def setupSpec() {
        enderecoRepository = new EnderecoRepository(sql)
    }

    def setup() {
        sql.execute("""
            INSERT INTO usuarios (tipo, nome, email, senha, descricao)
            VALUES ('empresa', 'Empresa', 'empresa@empresa.com', 'password', 'Empresa top')
        """)

        Integer empresaId = sql.firstRow("SELECT id FROM usuarios WHERE email = 'empresa@empresa.com'").id as Integer

        sql.execute("""
            INSERT INTO empresas (usuario_id, cnpj)
            VALUES (?, '00.623.904/0001-73')
        """, [empresaId])

        sql.execute("""
            INSERT INTO enderecos (cep, cidade, estado, pais)
            VALUES ('88063-074', 'Florianópolis', 'Santa Catarina', 'Brasil')
        """)

        Integer enderecoId = sql.firstRow("SELECT id FROM enderecos WHERE cep = '88063-074'").id as Integer

        sql.execute("""
            INSERT INTO enderecos_usuario (usuario_id, endereco_id)
            VALUES (?, ?)
        """, [empresaId, enderecoId])
    }

    def cleanup() {
        sql.execute("DELETE FROM usuarios CASCADE")
        sql.execute("DELETE FROM enderecos CASCADE")
    }

    def "obterEnderecoPeloId retorna enderecoDTO vazio quando id não é localizado"() {
        when:
            EnderecoDTO enderecoDTO = enderecoRepository.obterEnderecoPeloId(12341234)

        then:
            enderecoDTO.cidade == null
            enderecoDTO.cep == null
    }

    def "obterEnderecoPeloId retorna enderecoDTO com informações quando id é localizado"() {
        given:
            Integer enderecoId = sql.firstRow("SELECT id FROM enderecos WHERE cep = '88063-074'").id as Integer

        when:
            EnderecoDTO enderecoDTO = enderecoRepository.obterEnderecoPeloId(enderecoId)

        then:
            enderecoDTO.cidade == 'Florianópolis'
            enderecoDTO.cep == '88063-074'
            enderecoDTO.estado == 'Santa Catarina'
            enderecoDTO.pais == 'Brasil'
    }

    def "obterEnderecoDoUsuarioPeloId retorna enderecoDTO com informações quando id é localizado"() {
        given:
            Integer usuarioId = sql.firstRow("SELECT id FROM usuarios WHERE email = 'empresa@empresa.com'").id as Integer

        when:
            EnderecoDTO enderecoDTO = enderecoRepository.obterEnderecoDoUsuarioPeloId(usuarioId)

        then:
            enderecoDTO.cidade == 'Florianópolis'
            enderecoDTO.cep == '88063-074'
            enderecoDTO.estado == 'Santa Catarina'
            enderecoDTO.pais == 'Brasil'
    }

    def "obterEnderecoDoUsuarioPeloId retorna enderecoDTO vazio quando id não é localizado"() {
        when:
        EnderecoDTO enderecoDTO = enderecoRepository.obterEnderecoDoUsuarioPeloId(123455)

        then:
        enderecoDTO.cidade == null
        enderecoDTO.cep == null
        enderecoDTO.estado == null
        enderecoDTO.pais == null
    }

    def "obterIdDeEnderecoPeloCep retorna -1 quando cep não é localizado"() {
        when:
            Integer enderecoId = enderecoRepository.obterIdDeEnderecoPeloCep('123123123')

        then:
            enderecoId == -1
    }

    def "obterIdDeEnderecoPeloCep retorna id quando cep é localizado"() {
        given:
            Integer expectedEnderecoId = sql.firstRow("SELECT id FROM enderecos WHERE cep = '88063-074'").id as Integer

        when:
            Integer enderecoId = enderecoRepository.obterIdDeEnderecoPeloCep('88063-074')

        then:
            enderecoId == expectedEnderecoId
    }

    def "adicionarNovoEndereco retorna Id de novo endereco"() {
        given:
            EnderecoDTO novoEndereco = new EnderecoDTO(
                    cep: '91920-010',
                    cidade: 'Porto Alegre',
                    estado: 'Rio Grande do Sul',
                    pais: 'Brasil'
            )

        when:
            Integer novoEnderecoId = enderecoRepository.adicionarNovoEndereco(novoEndereco)

        then:
            novoEnderecoId != null
    }

    def "adicionarEnderecoParaUsuario"() {
        given:
            sql.execute("""
                INSERT INTO enderecos (cep, cidade, estado, pais)
                VALUES ('91920-010', 'Porto Alegre', 'Rio Grande do Sul', 'Brasil')
            """)
            Integer usuarioId = sql.firstRow("SELECT id FROM usuarios").id as Integer

            Integer enderecoId = sql.firstRow("SELECT id FROM enderecos WHERE cep = '91920-010'").id as Integer

        when:
            enderecoRepository.adicionarEnderecoParaUsuario(usuarioId, enderecoId)

        then:
            2 == sql.firstRow("SELECT COUNT(*) AS total FROM enderecos_usuario").total as Integer
    }

    def "adicionarEnderecoParaUsuario lança exceção quando não encontra o usuario com o id informado"() {
        given:
        sql.execute("""
                INSERT INTO enderecos (cep, cidade, estado, pais)
                VALUES ('91920-010', 'Porto Alegre', 'Rio Grande do Sul', 'Brasil')
            """)

        Integer enderecoId = sql.firstRow("SELECT id FROM enderecos WHERE cep = '91920-010'").id as Integer

        when:
        enderecoRepository.adicionarEnderecoParaUsuario(12345454, enderecoId)

        then:
        thrown(SQLException)
    }

    def "adicionarEnderecoParaUsuario lança exceção quando não encontra o endereco com o id informado"() {
        given:
        Integer usuarioId = sql.firstRow("SELECT id FROM usuarios").id as Integer

        when:
        enderecoRepository.adicionarEnderecoParaUsuario(usuarioId, 123458484)

        then:
        thrown(SQLException)
    }

    def "updateEnderecoUsuario atualiza o id do endereco"() {
        given:
            sql.execute("""
                    INSERT INTO enderecos (cep, cidade, estado, pais)
                    VALUES ('91920-010', 'Porto Alegre', 'Rio Grande do Sul', 'Brasil')
                """)

            Integer usuarioId = sql.firstRow("SELECT id FROM usuarios").id as Integer
            Integer novoEnderecoId = sql.firstRow("SELECT id FROM enderecos WHERE cep = '91920-010'").id as Integer

        when:
            enderecoRepository.updateEnderecoUsuario(usuarioId, novoEnderecoId)

        then:
            Integer enderecoId = sql.firstRow("SELECT endereco_id as id FROM enderecos_usuario WHERE usuario_id = ?", [usuarioId]).id as Integer
            enderecoId == novoEnderecoId
    }

    def "updateEnderecoUsuario lança exceção quando o id do endereco é inválido"() {
        given:
        Integer usuarioId = sql.firstRow("SELECT id FROM usuarios").id as Integer

        when:
        enderecoRepository.updateEnderecoUsuario(usuarioId, 123545)

        then:
        thrown(SQLException)
    }

}
