package com.linketinder.service

import com.linketinder.exceptions.CandidatoNotFoundException
import com.linketinder.exceptions.CompetenciaNotFoundException
import com.linketinder.exceptions.RepositoryAccessException
import com.linketinder.model.Candidato
import com.linketinder.model.Competencia
import com.linketinder.model.Endereco
import com.linketinder.model.dtos.CandidatoDTO
import com.linketinder.model.enums.Afinidade
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
        when(competenciaService.listarCompetenciasDeUsuarioOuVaga(any(Integer))).thenReturn(competencias)
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


    void "adicionarCandidato() lança CompetenciaNotFoundException quando candidato tem competencia invalida"() {
        given:
            Candidato candidato = new Candidato()
            candidato.competencias = [new Competencia('Java', 1, Afinidade.ALTA)]
            when(competenciaService.verificarSeCompetenciaExiste(any(String))).thenThrow(CompetenciaNotFoundException.class)

        when:
            candidatoService.adicionarCandidato(candidato)

        then:
            thrown(CompetenciaNotFoundException)
    }

    void "adicionarCandidato() cria novo candidato com suas competencias e endereco"() {
        given:
        Candidato candidato = new Candidato()
        candidato.endereco = new Endereco()
        candidato.competencias = [new Competencia('Java', 1, Afinidade.ALTA)]
        doNothing().when(competenciaService).verificarSeCompetenciaExiste(any(String))

        when(repository.adicionarCandidato(any(CandidatoDTO))).thenReturn(1)

        when:
        candidatoService.adicionarCandidato(candidato)

        then:
        verify(competenciaService, times(1)).adicionarCompetenciaDeUsuario(any(Competencia), eq(1))
        verify(enderecoService, times(1)).adicionarEnderecoParaUsuario(any(Endereco), eq(1))
    }

    void "obterCandidatoPeloId lança CandidatoNotFoundException quando não é encontrado um candidato"() {
        given:
        when(repository.obterCandidatoPeloId(any(Integer))).thenThrow(CandidatoNotFoundException.class)

        when:
        candidatoService.obterCandidatoPeloId(1)

        then:
        thrown(CandidatoNotFoundException)
    }

    void "deletarCandidatoPeloId invoca o metodo deletarCandidatoPeloId do Repository"() {
        when:
        doNothing().when(repository).deletarCandidatoPeloId(1)
        candidatoService.deletarCandidatoPeloId(1)

        then:
        verify(repository, times(1)).deletarCandidatoPeloId(1)
    }

    void "deletarCandidatoPeloId lança CandidatoNotFoundException quando não é encontrado um candidato"() {
        given:
        when(repository.obterCandidatoPeloId(1)).thenThrow(CandidatoNotFoundException.class)

        when:
        candidatoService.deletarCandidatoPeloId(1)

        then:
        thrown(CandidatoNotFoundException)
    }

}
