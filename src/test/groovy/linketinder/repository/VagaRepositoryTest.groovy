package linketinder.repository

import linketinder.exceptions.VagaNotFoundException
import linketinder.model.dtos.VagaRequestDTO
import linketinder.model.dtos.VagaResponseDTO
import spock.lang.Shared

import java.sql.SQLException

class VagaRepositoryTest extends SetupRepositoryTest {
    @Shared
    VagaRepository vagaRepository

    def setupSpec() {
        vagaRepository = new VagaRepository(sql)
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
            INSERT INTO vagas (nome, descricao, empresa_id, endereco_id)
            VALUES ('Desenvolvedor', 'Dev fullstack', ?, ?)
        """, [empresaId, enderecoId])

    }

    def cleanup() {
        sql.execute("DELETE FROM usuarios CASCADE")
        sql.execute("DELETE FROM vagas CASCADE")
        sql.execute("DELETE FROM enderecos CASCADE")
    }

    def "listarVagar lista todas as vagas existentes"() {
        when:
            List<VagaResponseDTO> vagas = vagaRepository.listarVagas()

        then:
            vagas.size() == 1
            vagas[0].nome == 'Desenvolvedor'
    }

    def "obterVagaPeloId lança VagaNotFoundException quando o id é inexistente"() {
        when:
            vagaRepository.obterVagaPeloId(1232322)

        then:
            thrown(VagaNotFoundException)
    }

    def "obterVagaPeloId retorna informações da vaga"() {
        given:
            Integer vagaId = sql.firstRow("SELECT id FROM vagas").id as Integer

        when:
            VagaResponseDTO vaga = vagaRepository.obterVagaPeloId(vagaId)

        then:
            vaga.nome == 'Desenvolvedor'
            vaga.cidade == 'Florianópolis'
    }

    def "adicionarVaga retorna id da nova vaga"() {
        given:
            Integer empresaId = sql.firstRow("SELECT id FROM usuarios").id as Integer
            Integer enderecoId = sql.firstRow("SELECT id FROM enderecos WHERE cep = '88063-074'").id as Integer

            VagaRequestDTO vagaRequestDTO = new VagaRequestDTO(
                    nome: 'Vaga nova',
                    empresaId: empresaId,
                    enderecoId: enderecoId,
                    descricao: 'Descricao da nova vaga',
            )

        when:
            Integer novaVagaId = vagaRepository.adicionarVaga(vagaRequestDTO)

        then:
            novaVagaId != null
    }

    def "adicionarVaga lança SQLException quando Empresa não é localizada"() {
        given:
        Integer enderecoId = sql.firstRow("SELECT id FROM enderecos WHERE cep = '88063-074'").id as Integer

        VagaRequestDTO vagaRequestDTO = new VagaRequestDTO(
                nome: 'Vaga nova',
                empresaId: 123441,
                enderecoId: enderecoId,
                descricao: 'Descricao da nova vaga',
        )

        when:
            vagaRepository.adicionarVaga(vagaRequestDTO)

        then:
            thrown(SQLException)
    }


    def "adicionarVaga lança SQLException quando Endereco não é localizada"() {
        given:
            Integer empresaId = sql.firstRow("SELECT id FROM usuarios").id as Integer

            VagaRequestDTO vagaRequestDTO = new VagaRequestDTO(
                    nome: 'Vaga nova',
                    empresaId: empresaId,
                    enderecoId: 12312312312,
                    descricao: 'Descricao da nova vaga',
                    competenciaDTOList: []
            )

        when:
            vagaRepository.adicionarVaga(vagaRequestDTO)

        then:
            thrown(SQLException)
    }

    def "updateVaga lança SQLException quando Endereco não é localizado"() {
        given:
        Integer vagaId = sql.firstRow("SELECT id FROM vagas").id as Integer
        Integer enderecoId = sql.firstRow("SELECT id FROM enderecos WHERE cep = '88063-074'").id as Integer

        VagaRequestDTO vagaRequestDTO = new VagaRequestDTO(
                nome: 'Vaga nova',
                enderecoId: 123232,
                descricao: 'Descricao da nova vaga',
                competenciaDTOList: []
        )

        when:
            vagaRepository.updateVaga(vagaId, vagaRequestDTO)

        then:
            thrown(SQLException)
    }

    def "updateVaga lança VagaNotFoundException quando Vaga não é localizada"() {
        given:
        Integer enderecoId = sql.firstRow("SELECT id FROM enderecos WHERE cep = '88063-074'").id as Integer

        VagaRequestDTO vagaRequestDTO = new VagaRequestDTO(
                nome: 'Vaga nova',
                enderecoId: enderecoId,
                descricao: 'Descricao da nova vaga',
                competenciaDTOList: []
        )

        when:
        vagaRepository.updateVaga(1215454, vagaRequestDTO)

        then:
        thrown(VagaNotFoundException)
    }

    def "deleteVaga remove a vaga"() {
        given:
            Integer vagaId = sql.firstRow("SELECT id FROM vagas").id as Integer

        when:
            vagaRepository.deleteVaga(vagaId)

        then:
            null == sql.firstRow("SELECT * FROM vagas")
    }

}
