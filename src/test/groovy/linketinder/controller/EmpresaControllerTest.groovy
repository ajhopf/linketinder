package linketinder.controller

import linketinder.exceptions.EmpresaNotFoundException
import linketinder.model.Empresa
import linketinder.service.EmpresaService
import spock.lang.Specification

import static org.mockito.Mockito.*

class EmpresaControllerTest extends Specification {
    EmpresaService empresaService = mock(EmpresaService.class)
    EmpresaController empresaController = new EmpresaController(empresaService)
    Empresa empresa

    def setup() {
        Empresa empresa = new Empresa()
        empresa.nome = 'EmpresaTop'
        empresa.id = 1

        this.empresa = empresa
    }

    def "listarEmpresas retorna lista de empresas"() {
        given:
            List<Empresa> empresas = [this.empresa, this.empresa]
            when(empresaService.listarEmpresas()).thenReturn(empresas)
        when:
            List<Empresa> result = empresaController.listarEmpresas()
        then:
            result.size() == 2
            result[0].nome == 'EmpresaTop'
    }

    def "obterEmpresaPeloId lança EmpresaNotFoundException"() {
        given:
            when(empresaService.obterEmpresaPeloId(anyInt())).thenThrow(EmpresaNotFoundException)

        when:
            empresaController.obterEmpresaPeloId(1)

        then:
            thrown(EmpresaNotFoundException)
    }

    def "obterEmpresaPeloId retorna empresa"() {
        given:
            when(empresaService.obterEmpresaPeloId(anyInt())).thenReturn(this.empresa)

        when:
            Empresa result = empresaController.obterEmpresaPeloId(1)

        then:
            result.nome == 'EmpresaTop'
    }

    def "adicionarEmpresa retorna id da empresa"() {
        given:
            when(empresaService.adicionarEmpresa(this.empresa)).thenReturn(1)

        when:
            Integer result = empresaController.adicionarEmpresa(this.empresa)

        then:
            result == 1
    }

    def "editarEmpresa lança EmpresaNotFoundException"() {
        given:
            when(empresaService.editarEmpresa(this.empresa)).thenThrow(EmpresaNotFoundException)

        when:
            empresaController.editarEmpresa(this.empresa)

        then:
            thrown(EmpresaNotFoundException)
    }

    def "deletarEmpresa lança EmpresaNotFoundException"() {
        given:
            when(empresaService.deleteEmpresa(anyInt())).thenThrow(EmpresaNotFoundException)

        when:
            empresaController.deletarEmpresa(1)

        then:
            thrown(EmpresaNotFoundException)
    }
}
