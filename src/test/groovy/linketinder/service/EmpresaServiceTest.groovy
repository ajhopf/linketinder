package linketinder.service

import linketinder.exceptions.EmpresaNotFoundException
import linketinder.model.Empresa
import linketinder.model.Endereco
import linketinder.model.dtos.EmpresaDTO
import linketinder.repository.EmpresaRepository
import spock.lang.Specification

import static org.mockito.Mockito.*

class EmpresaServiceTest extends Specification {
    EmpresaRepository repository = mock(EmpresaRepository.class)
    EnderecoService enderecoService = mock(EnderecoService.class)
    EmpresaService empresaService = new EmpresaService(repository, enderecoService)

    void "obterEmpresaPeloId lanca EmpresaNotFoundException quando não encontra empresa"() {
        given:
        when(repository.obterEmpresaPeloId(any(Integer))).thenThrow(EmpresaNotFoundException.class)

        when:
        empresaService.obterEmpresaPeloId(1)

        then:
        thrown(EmpresaNotFoundException)
    }

    void "listarEmpresas() retorna uma lista empresas"() {
        given:
            EmpresaDTO empresa1 = new EmpresaDTO()
            empresa1.id = 1

            EmpresaDTO empresa2 = new EmpresaDTO()
            empresa2.id = 2
            List<EmpresaDTO> empresaDTOList = [empresa1, empresa2]
            when(repository.listarEmpresas()).thenReturn(empresaDTOList)
            when(enderecoService.obterEnderecoDoUsuario(any(Integer))).thenReturn(new Endereco())
        when:
            List<Empresa> resultado = empresaService.listarEmpresas()
        then:
            resultado.size() == 2
            resultado[0].id == 1
    }

    void "adicionarEmpresa() cria uma empresa"(){
        given:
        Empresa emoresa = new Empresa()
        emoresa.endereco = new Endereco()

        when(repository.adicionarEmpresa(any(EmpresaDTO))).thenReturn(1)

        when:
        empresaService.adicionarEmpresa(emoresa)

        then:
        verify(enderecoService, times(1)).adicionarEnderecoParaUsuario(any(Endereco), eq(1))
    }

    void 'editarEmpresa() lanca EmpresaNotFoundException quando empresa não é encontrada'() {
        given:
        when(repository.updateEmpresa(any(EmpresaDTO))).thenThrow(EmpresaNotFoundException.class)

        when:
        empresaService.editarEmpresa(new Empresa())

        then:
        thrown(EmpresaNotFoundException)
    }

    void "deleteEmpresa() lança EmpresaNotFoundException quando empresa não é encontrada"() {
        given:
        when(repository.deletarEmpresaPeloId(any(Integer))).thenThrow(EmpresaNotFoundException.class)

        when:
        empresaService.deleteEmpresa(1)

        then:
        thrown(EmpresaNotFoundException)
    }
}
