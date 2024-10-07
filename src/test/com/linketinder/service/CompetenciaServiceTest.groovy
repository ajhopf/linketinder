package com.linketinder.service

import com.linketinder.exceptions.CompetenciaNotFoundException
import com.linketinder.exceptions.RepositoryAccessException
import com.linketinder.model.Competencia
import com.linketinder.model.dtos.CompetenciaDTO
import com.linketinder.model.enums.Afinidade
import com.linketinder.repository.CompetenciaRepository
import spock.lang.Specification

import static org.mockito.Mockito.*;

class CompetenciaServiceTest extends Specification {
    CompetenciaRepository repository = mock(CompetenciaRepository.class);
    CompetenciaService competenciaService = new CompetenciaService(repository)


    void "listarCompetenciasDeUsuario() retorna lista vazia"() {
        given:
        List<CompetenciaDTO> competenciaDTOS = []

        when:
        when(repository.listarCompetenciasDeCandidatoOuVaga(any(Integer))).thenReturn(competenciaDTOS);
        List<Competencia> listaResultado = competenciaService.listarCompetenciasDeUsuarioOuVaga(1);

        then:
        listaResultado.size() == 0
    }

    void "listarCompetenciasDeUsuario() retorna lista com competencias"() {
        given:
        List<CompetenciaDTO> competenciaDTOS = [new CompetenciaDTO(), new CompetenciaDTO()]

        when:
        when(repository.listarCompetenciasDeCandidatoOuVaga(any(Integer))).thenReturn(competenciaDTOS)
        List<Competencia> listaResultado = competenciaService.listarCompetenciasDeUsuarioOuVaga(1);

        then:
        listaResultado.size() == 2
    }

    void "listarCompetenciasDeUsuario() lança RepositoryAccessException quando há erro de acesso no repository"() {
        given:
        when(repository.listarCompetenciasDeCandidatoOuVaga(any(Integer))).thenThrow(RepositoryAccessException.class);

        when:
        competenciaService.listarCompetenciasDeUsuarioOuVaga(1);

        then:
        thrown(RepositoryAccessException)
    }

    void "verificarSeCompetenciaExiste() lança CompetenciaNotFoundException quando não encontra a competencia no BD"() {
        given:
        when(repository.obterIdDeCompetencia(any(String))).thenThrow(CompetenciaNotFoundException.class)

        when:
        competenciaService.verificarSeCompetenciaExiste('jeva')

        then:
        thrown(CompetenciaNotFoundException)
    }

    void "adicionarCompetencia() lança CompetenciaNotFoundException quando não encontra a competencia no BD"() {
        given:
        when(repository.obterIdDeCompetencia(any(String))).thenThrow(CompetenciaNotFoundException.class)

        when:
        competenciaService.adicionarCompetenciaDeUsuario(new Competencia('jeva', 1, Afinidade.ALTA), 1)

        then:
        thrown(CompetenciaNotFoundException)
    }

    void "adicionarCompetencia() invoca adicionarCompetenciaDeUsuario uma vez"() {
        given:
        when(repository.obterIdDeCompetencia(any(String))).thenReturn(1)

        when:
        competenciaService.adicionarCompetenciaDeUsuario(new Competencia('jeva', 1, Afinidade.ALTA), 1)

        then:
        verify(repository, times(1))
                .adicionarCompetenciaUsuario(any(CompetenciaDTO), eq(1))
    }

    void "listarCompetencias retorna lista de competencias"() {
        given:
            List<CompetenciaDTO> competenciaDTOS = [
                    new CompetenciaDTO(competencia: 'Java', id: 10),
                    new CompetenciaDTO(competencia: 'JavaScript', id: 11),
                    new CompetenciaDTO(competencia: 'PosgreSQL', id: 12),
            ]
            when(repository.listarCompetencias()).thenReturn(competenciaDTOS)
        when:
            List<Competencia> resultado = competenciaService.listarCompetencias()
        then:
            resultado.size() == 3

    }

    void "obterCompetenciaPeloId lança CompetenciaNotFoundException quando não encontra competencia"() {
        given:
        when(repository.obterCompetenciaPeloId(1)).thenThrow(CompetenciaNotFoundException.class)

        when:
        competenciaService.obterCompetenciaPeloId(1)

        then:
        thrown(CompetenciaNotFoundException)
    }

    void "deletarCompetencia lança CompetenciaNotFound quando não encontra competencia"() {
        given:
        when(repository.deleteCompetencia(1)).thenThrow(CompetenciaNotFoundException.class)

        when:
        competenciaService.deletarCompetencia(1)

        then:
        thrown(CompetenciaNotFoundException)
    }

}
