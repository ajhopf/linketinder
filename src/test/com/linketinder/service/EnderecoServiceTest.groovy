package com.linketinder.service


import com.linketinder.model.Endereco
import com.linketinder.model.dtos.CompetenciaDTO
import com.linketinder.model.dtos.EnderecoDTO
import com.linketinder.repository.EnderecoRepository
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.eq
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.times
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when

class EnderecoServiceTest extends Specification {
    EnderecoRepository repository = mock(EnderecoRepository.class);
    EnderecoService enderecoService = new EnderecoService(repository)

    void "obterEnderecoDoUsuario() retorna Endereco"() {
        given:
        EnderecoDTO endereco = new EnderecoDTO(cep: '88063-074', cidade: 'Florianópolis', estado: 'Santa Catarina', pais: 'Brasil')

        when:
        when(repository.obterEnderecoDoUsuarioPeloId(any(Integer))).thenReturn(endereco);
        Endereco resultado = enderecoService.obterEnderecoDoUsuario(1);

        then:
        resultado.estado == 'Santa Catarina'
        resultado.cep == '88063-074'
        resultado.cidade == 'Florianópolis'
    }

    void "obterEnderecoDoUsuario() retorna Endereco vazio quando não encontra endereço pelo id"() {
        given:
        EnderecoDTO endereco = new EnderecoDTO()

        when:
        when(repository.obterEnderecoDoUsuarioPeloId(any(Integer))).thenReturn(endereco);
        Endereco resultado = enderecoService.obterEnderecoDoUsuario(1);

        then:
        resultado.estado == null
        resultado.cep == null
    }

    void "adicionarEndereco() cria novo endereço quando este não existir no DB"() {
        given:
        Endereco endereco = new Endereco()
        endereco.cep = '88063-074'
        when(repository.obterIdDeEnderecoPeloCep(endereco.cep)).thenReturn(-1)
        when(repository.adicionarNovoEndereco(any(EnderecoDTO))).thenReturn(1)

        when:
        enderecoService.adicionarEndereco(endereco, 1);

        then:
        verify(repository, times(1))
                .adicionarEnderecoParaUsuario(1, 1)
    }

    void "adicionarEndereco() não cria novo endereço quando este existir no DB e associa o id ao usuario"() {
        given:
        Endereco endereco = new Endereco()
        endereco.cep = '88063-074'
        when(repository.obterIdDeEnderecoPeloCep(endereco.cep)).thenReturn(1)

        when:
        enderecoService.adicionarEndereco(endereco, 1);

        then:
        verify(repository, times(1))
                .adicionarEnderecoParaUsuario(1, 1)
    }

}
