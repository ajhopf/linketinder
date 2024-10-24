package linketinder.service

import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.exceptions.RepositoryAccessException
import linketinder.model.Competencia
import linketinder.model.dtos.CompetenciaDTO
import linketinder.model.enums.Afinidade
import linketinder.repository.CompetenciaRepository
import spock.lang.Specification

import static org.mockito.Mockito.*

class CompetenciaServiceTest extends Specification {
    CompetenciaRepository repository = mock(CompetenciaRepository.class)
    CompetenciaService competenciaService = new CompetenciaService(repository)


    void "listarCompetenciasDeVaga() retorna lista vazia"() {
        given:
        List<CompetenciaDTO> competenciaDTOS = []

        when:
        when(repository.listarCompetenciasDeVaga(any(Integer))).thenReturn(competenciaDTOS)
        List<Competencia> listaResultado = competenciaService.listarCompetenciasDeVaga(1)

        then:
        listaResultado.size() == 0
    }


    void "listarCompetenciasDeVaga() retorna lista com competencias"() {
        given:
        List<CompetenciaDTO> competenciaDTOS = [new CompetenciaDTO(id: 1, afinidade: Afinidade.ALTA, anosExperiencia: 3, competencia: 'Java')]

        when:
        when(repository.listarCompetenciasDeVaga(any(Integer))).thenReturn(competenciaDTOS)
        List<Competencia> listaResultado = competenciaService.listarCompetenciasDeVaga(1)

        then:
        listaResultado.size() == 1
    }

    void "listarCompetenciasDeVaga() lança RepositoryAccessException quando há erro de acesso no repository"() {
        given:
        when(repository.listarCompetenciasDeVaga(any(Integer))).thenThrow(RepositoryAccessException.class)

        when:
        competenciaService.listarCompetenciasDeVaga(1)

        then:
        thrown(RepositoryAccessException)
    }

    void "listarCompetenciasDeCandidato() retorna lista vazia"() {
        given:
        List<CompetenciaDTO> competenciaDTOS = []

        when:
        when(repository.listarCompetenciasDeCandidato(any(Integer))).thenReturn(competenciaDTOS)
        List<Competencia> listaResultado = competenciaService.listarCompetenciasDeCandidato(1)

        then:
        listaResultado.size() == 0
    }

    void "listarCompetenciasDeCandidato() retorna lista com competencias"() {
        given:
        List<CompetenciaDTO> competenciaDTOS = [new CompetenciaDTO(id: 1, afinidade: Afinidade.ALTA, anosExperiencia: 3, competencia: 'Java')]

        when:
        when(repository.listarCompetenciasDeCandidato(any(Integer))).thenReturn(competenciaDTOS)
        List<Competencia> listaResultado = competenciaService.listarCompetenciasDeCandidato(1)

        then:
        listaResultado.size() == 1
    }


    void "listarCompetenciasDeCandidato() lança RepositoryAccessException quando há erro de acesso no repository"() {
        given:
        when(repository.listarCompetenciasDeCandidato(any(Integer))).thenThrow(RepositoryAccessException.class)

        when:
        competenciaService.listarCompetenciasDeCandidato(1)

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

    void "adicionarCompetenciaVaga() lança CompetenciaNotFoundException quando não encontra a competencia no BD"() {
        given:
        when(repository.obterIdDeCompetencia(any(String))).thenThrow(CompetenciaNotFoundException.class)

        when:
        competenciaService.adicionarCompetenciaVaga(new Competencia('jeva', 1, Afinidade.ALTA), 1)

        then:
        thrown(CompetenciaNotFoundException)
    }

    void "adicionarCompetenciaVaga() invoca repository.adicionarCompetenciaVaga uma vez"() {
        given:
        when(repository.obterIdDeCompetencia(any(String))).thenReturn(1)
        Competencia competencia = new Competencia('Java', 1, Afinidade.ALTA)

        when:
        competenciaService.adicionarCompetenciaVaga(competencia, 1)

        then:
        verify(repository, times(1))
                .adicionarCompetenciaVaga(any(CompetenciaDTO), eq(1))
    }

    void "adicionarCompetenciaCandidato() lança CompetenciaNotFoundException quando não encontra a competencia no BD"() {
        given:
        when(repository.obterIdDeCompetencia(any(String))).thenThrow(CompetenciaNotFoundException.class)

        when:
        competenciaService.adicionarCompetenciaCandidato(new Competencia('jeva', 1, Afinidade.ALTA), 1)

        then:
        thrown(CompetenciaNotFoundException)
    }

    void "adicionarCompetenciaCandidato() invoca adicionarCompetenciaDeUsuario uma vez"() {
        given:
        when(repository.obterIdDeCompetencia(any(String))).thenReturn(1)
        Competencia competencia = new Competencia('Java', 1, Afinidade.ALTA)

        when:
        competenciaService.adicionarCompetenciaCandidato(competencia, 1)

        then:
        verify(repository, times(1))
                .adicionarCompetenciaCandidato(any(CompetenciaDTO), eq(1))
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

    void "deletarCompetenciasDeCandidato invoca repository.deletarCompetenciasCandidato uma vez"() {
        when:
        competenciaService.deletarCompetenciasDeCandidato(1)

        then:
        verify(repository, times(1))
                .deletarCompetenciasCandidato(1)
    }

    void "deletarCompetenciasDeVaga invoca repository.deletarComptenciasVaga uma vez"() {
        when:
        competenciaService.deletarCompetenciasDeVaga(1)

        then:
        verify(repository, times(1))
                .deletarCompetenciasVaga(1)
    }

}
