package linketinder.repository


import linketinder.exceptions.EmpresaNotFoundException
import linketinder.model.dtos.EmpresaDTO
import spock.lang.Shared

import java.sql.SQLException

class EmpresaRepositoryTest extends SetupRepositoryTest {
    EmpresaDTO empresaDTO

    @Shared
    EmpresaRepository empresaRepository

    def setupSpec() {
        empresaRepository = new EmpresaRepository(sql)
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

        this.empresaDTO = new EmpresaDTO(
                nome: 'Nova Empresa',
                email: 'empresa2@empresa.com',
                senha: 'password',
                descricao: 'Empresa top demais',
                cnpj: '99.144.904/0001-73'
        )
    }

    def cleanup() {
        sql.execute("DELETE FROM usuarios CASCADE")
    }

    def "listarEmpresas retorna todas empresas"() {
        when:
            def result = empresaRepository.listarEmpresas()

        then:
            result.size() == 1
            result[0].nome == 'Empresa'
            result[0].cnpj == '00.623.904/0001-73'
    }

    def "obterEmpresaPeloId lança EmpresaNotFoundException quando id é inexistente"() {
        when:
            empresaRepository.obterEmpresaPeloId(12342141)

        then:
            thrown(EmpresaNotFoundException)
    }

    def "obterEmpresaPeloId retorna Empresa"() {
        given:
            Integer empresaId =  sql.firstRow("SELECT id FROM usuarios WHERE email = 'empresa@empresa.com'").id as Integer

        when:
            EmpresaDTO empresa = empresaRepository.obterEmpresaPeloId(empresaId)

        then:
            empresa.nome == 'Empresa'
            empresa.cnpj == '00.623.904/0001-73'
    }

    def "adicionarEmpresa insere empresa e retorna o ID do usuário"() {
        when:
            def userId = empresaRepository.adicionarEmpresa(empresaDTO)

        then:
            userId != null

        and:
            def usuario = sql.firstRow("SELECT * FROM usuarios WHERE id = ?", [userId])
            usuario.nome == 'Nova Empresa'
            usuario.email == 'empresa2@empresa.com'

        and:
            def empresa = sql.firstRow("SELECT * FROM empresas WHERE usuario_id = ?", [userId])
            empresa.cnpj == '99.144.904/0001-73'
    }

    def "adicionarEmpresa não adiciona usuário se informação de empresa é inválida"() {
        given: "CandidatoDTO inválido -> cpf null"
            empresaDTO.cnpj = null

        when: "adicionarCandidato é executado com o candidatoDTO"
            empresaRepository.adicionarEmpresa(empresaDTO)

        then: "Lança uma SQLException"
            thrown(SQLException)

        and: "Contém apenas o usuário inserido antes do início do teste"
        def count = sql.firstRow("SELECT COUNT(*) AS total FROM usuarios").total
        count == 1
    }

    def "updateEmpresa lança EmpresaNotFoundException quando invocado com id inexistente"() {
        given:
        empresaDTO.id = 1234555

        when:
        empresaRepository.updateEmpresa(empresaDTO)

        then:
        thrown(EmpresaNotFoundException)
    }

    def "updateEmpresa  faz atualização do Usuário e da Empresa"() {
        given: "Busca o candidato existente e altera o nome e o cpf"
        Integer empresaId = sql.firstRow("SELECT id FROM usuarios WHERE email = 'empresa@empresa.com'").id as Integer
        empresaDTO.id = empresaId
        empresaDTO.nome = 'Um novo nome'

        when:
        empresaRepository.updateEmpresa(empresaDTO)

        then:
        empresaDTO.nome == sql.firstRow("SELECT * FROM usuarios").nome
        empresaDTO.cnpj == sql.firstRow("SELECT * FROM empresas").cnpj
    }

    def "deletarEmpresa lança EmpresaNotFoundException quando invocado com id inexistente"(){
        when:
        empresaRepository.deletarEmpresaPeloId(1234455)

        then:
        thrown(EmpresaNotFoundException)
    }

    def "deletarEmpresa remove registro de usuario e empresa"() {
        given:
            Integer empresaId = sql.firstRow("SELECT id FROM usuarios WHERE email = 'empresa@empresa.com'").id as Integer

        when:
            empresaRepository.deletarEmpresaPeloId(empresaId)

        then:
            null == sql.firstRow("SELECT id FROM usuarios WHERE email = 'empresa@empresa.com'")
            null == sql.firstRow("SELECT * FROM empresas WHERE cnpj = '00.623.904/0001-73'")
    }
}
