package linketinder.controller

import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.model.Competencia
import linketinder.model.enums.Afinidade
import linketinder.service.CompetenciaService
import spock.lang.Specification

import static org.mockito.Mockito.*

class CompetenciaControllerTest extends Specification {
    CompetenciaService competenciaService = mock(CompetenciaService.class)
    CompetenciaController competenciaController = new CompetenciaController(competenciaService)
    Competencia competencia

    def setup() {
        Competencia competencia = new Competencia(1, 'Java', 5, Afinidade.ALTA)
        this.competencia = competencia
    }

    def "obterIdDeCompetencia lança CompetenciaNotFoundException"(){
        given:
            when(competenciaService.obterIdDeCompetencia(anyString())).thenThrow(CompetenciaNotFoundException)
        when:
            competenciaController.obterIdDeCompetencia('Java')
        then:
            thrown(CompetenciaNotFoundException)

    }

    def "obterIdDeCompetencia retorna id de competencia"(){
        given:
            when(competenciaService.obterIdDeCompetencia(anyString())).thenReturn(1)
        when:
            Integer result = competenciaController.obterIdDeCompetencia('Java')
        then:
            result == 1
    }

    def "obterCompetenciaPeloId lança CompetenciaNotFoundException"(){
        given:
            when(competenciaService.obterCompetenciaPeloId(anyInt())).thenThrow(CompetenciaNotFoundException)
        when:
            competenciaController.obterCompetenciaPeloId(1)
        then:
            thrown(CompetenciaNotFoundException)
    }

    def "obterCompetenciaPeloId retorna Competencia"(){
        given:
            when(competenciaService.obterCompetenciaPeloId(anyInt())).thenReturn(this.competencia)
        when:
            Competencia result = competenciaController.obterCompetenciaPeloId(1)
        then:
            result.competencia == 'Java'
            result.id == 1
    }

    def "adicionarCompetencia retorna Id"() {
        given:
            when(competenciaService.adicionarCompetencia(anyString())).thenReturn(1)
        when:
            Integer result = competenciaController.adicionarCompetencia('Java')
        then:
            result == 1
    }

    def "listarCompetencias retorna lista de competencias"() {
        given:
            List<Competencia> competencias = [this.competencia, this.competencia]
            when(competenciaService.listarCompetencias()).thenReturn(competencias)

        when:
            List<Competencia> result = competenciaController.listarCompetencias()

        then:
            result.size() == 2
            result[0].competencia == 'Java'
    }

    def "editarCompetencia lança CompetenciaNotFoundException"() {
        given:
            when(competenciaService.editarCompetencia(this.competencia)).thenThrow(CompetenciaNotFoundException)

        when:
            competenciaController.editarCompetencia(this.competencia)

        then:
            thrown(CompetenciaNotFoundException)
    }

    def "deletarCompetencia lança CompetenciaNotFoundException"() {
        given:
        when(competenciaService.deletarCompetencia(anyInt())).thenThrow(CompetenciaNotFoundException)

        when:
        competenciaController.deletarCompetencia(1)

        then:
        thrown(CompetenciaNotFoundException)
    }
}
