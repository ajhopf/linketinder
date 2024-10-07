package com.linketinder.service

import com.linketinder.model.Empresa
import com.linketinder.model.Endereco
import com.linketinder.model.dtos.EmpresaDTO
import com.linketinder.repository.EmpresaRepository
import spock.lang.Specification

import static org.mockito.Mockito.*;

class EmpresaServiceTest extends Specification {
    EmpresaRepository repository = mock(EmpresaRepository.class);
    EnderecoService enderecoService = mock(EnderecoService.class);
    EmpresaService empresaService = new EmpresaService(repository, enderecoService);

    def "listarEmpresas() retorna uma lista empresas"() {
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

}
