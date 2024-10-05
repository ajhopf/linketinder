package com.linketinder.service

import com.linketinder.exceptions.RepositoryAccessException
import com.linketinder.model.Candidato
import com.linketinder.model.Competencia
import com.linketinder.model.Endereco
import com.linketinder.model.dtos.CandidatoDTO
import com.linketinder.model.dtos.CompetenciaDTO
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
        when(repository.listarCompetenciasDeUsuario(any(Integer))).thenReturn(competenciaDTOS);
        List<Competencia> listaResultado = competenciaService.listarCompetenciasDeUsuario(1);

        then:
        listaResultado.size() == 0
    }

    void "listarCompetenciasDeUsuario() retorna lista com competencias"() {
        given:
        List<CompetenciaDTO> competenciaDTOS = [new CompetenciaDTO(), new CompetenciaDTO()]

        when:
        when(repository.listarCompetenciasDeUsuario(any(Integer))).thenReturn(competenciaDTOS)
        List<Competencia> listaResultado = competenciaService.listarCompetenciasDeUsuario(1);

        then:
        listaResultado.size() == 2
    }

    void "listarCompetenciasDeUsuario() lança RepositoryAccessException quando há erro de acesso no repository"() {
        given:
        when(repository.listarCompetenciasDeUsuario(any(Integer))).thenThrow(RepositoryAccessException.class);

        when:
        competenciaService.listarCompetenciasDeUsuario(1);

        then:
        thrown(RepositoryAccessException)
    }
}
