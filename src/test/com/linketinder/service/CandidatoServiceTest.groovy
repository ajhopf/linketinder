package com.linketinder.service

import com.linketinder.exceptions.RepositoryAccessException
import com.linketinder.model.Candidato
import com.linketinder.model.Competencia
import com.linketinder.model.Endereco
import com.linketinder.model.dtos.CandidatoDTO
import com.linketinder.repository.CandidatoRepository

import spock.lang.Specification

import static org.mockito.Mockito.*;

class CandidatoServiceTest extends Specification {
    CandidatoRepository repository = mock(CandidatoRepository.class);
    EnderecoService enderecoService = mock(EnderecoService.class);
    CompetenciaService competenciaService = mock(CompetenciaService.class);
    CandidatoService candidatoService = new CandidatoService(repository, enderecoService, competenciaService);

    void "listarCandidatos() retorna lista vazia com tabela sem candidatos"() {
        given:
        List<Candidato> resultadoEsperado = []
        List<CandidatoDTO> candidatoDTOS = []

        when:
        when(repository.listarCandidatos()).thenReturn(candidatoDTOS);
        List<Candidato> listaResultado = candidatoService.listarCandidatos();

        then:
        listaResultado == resultadoEsperado
    }

    void "listarCandidatos() retorna lista com candidatos"() {
        given:
        List<CandidatoDTO> candidatoDTOS = [new CandidatoDTO(id: 1), new CandidatoDTO(id: 2)]
        List<Competencia> competencias = []

        when:
        when(repository.listarCandidatos()).thenReturn(candidatoDTOS);
        when(enderecoService.obterEnderecoDoUsuario(any(Integer))).thenReturn(new Endereco())
        when(competenciaService.listarCompetenciasDeUsuario(any(Integer))).thenReturn(competencias)
        List<Candidato> listaResultado = candidatoService.listarCandidatos();

        then:
        listaResultado.size() == 2
    }

    void "listarCandidatos() lança RepositoryAccessException quando há erro de acesso no repository"() {
        given:
        when(repository.listarCandidatos()).thenThrow(RepositoryAccessException.class);

        when:
        candidatoService.listarCandidatos();

        then:
        thrown(RepositoryAccessException)
    }

}
