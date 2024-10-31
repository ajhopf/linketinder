package linketinder.controller

import linketinder.exceptions.CandidatoNotFoundException
import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.model.Candidato
import linketinder.model.Competencia
import linketinder.model.enums.Afinidade
import linketinder.service.CandidatoService
import spock.lang.Specification

import static org.mockito.Mockito.*

class CandidatoControllerTest extends Specification {
    CandidatoService candidatoService = mock(CandidatoService.class)
    CompetenciaController competenciaController = mock(CompetenciaController.class)
    CandidatoController candidatoController = new CandidatoController(candidatoService, competenciaController)
    Candidato candidato

    def setup() {
        Candidato candidato = new Candidato()
        List<Competencia> competencias = [new Competencia('Java', 5, Afinidade.ALTA)]
        candidato.nome = 'André'
        candidato.competencias = competencias

        this.candidato = candidato
    }

    def "verificarSeCompetenciasExistem lança CompetenciaNotFoundException"() {
        given:
            when(competenciaController.obterIdDeCompetencia(anyString())).thenThrow(CompetenciaNotFoundException)
        when:
            candidatoController.verificarSeCompetenciasExistem([new Competencia('java', 1, Afinidade.ALTA)])
        then:
            thrown(CompetenciaNotFoundException)
    }

    def "obterCandidatoPeloId retorna candidato"() {
        given:
            when(candidatoService.obterCandidatoPeloId(anyInt())).thenReturn(this.candidato)
        when:
            Candidato result = candidatoController.obterCandidatoPeloId(1)
        then:
            result.nome == "André"
    }

    def "obterCandidatoPeloId lança CandidatoNotFoundException"() {
        given:
            when(candidatoService.obterCandidatoPeloId(anyInt())).thenThrow(CandidatoNotFoundException)
        when:
            candidatoController.obterCandidatoPeloId(1)
        then:
            thrown(CandidatoNotFoundException)
    }

    def "listarCandidatos retorna lista"() {
        given:
            List<Candidato> candidatos = [this.candidato, new Candidato()]

            when(candidatoService.listarCandidatos()).thenReturn(candidatos)
        when:
            List<Candidato> result = candidatoController.listarCandidatos()
        then:
            result.size() == 2
    }

    def "adicionarCandidato com competencia inválida lança CompetenciaNotFoundException"() {
        given:
            when(competenciaController.obterIdDeCompetencia(anyString())).thenThrow(CompetenciaNotFoundException)
        when:
            candidatoController.adicionarCandidato(this.candidato)
        then:
            thrown(CompetenciaNotFoundException)
    }

    def "adicionarCandidato retorna id do candidato"() {
        given:
            when(candidatoService.adicionarCandidato(this.candidato)).thenReturn(1)
        when:
            Integer result = candidatoController.adicionarCandidato(this.candidato)
        then:
            result == 1
    }

    def "editarCandidato com competencia inválida lança CompetenciaNotFoundException"() {
        given:
            when(competenciaController.obterIdDeCompetencia(anyString())).thenThrow(CompetenciaNotFoundException)
        when:
            candidatoController.editarCandidato(this.candidato)
        then:
            thrown(CompetenciaNotFoundException)
    }

    def "editarCandidato com id inválido lança CandidatoNotFoundException"() {
        given:
            when(candidatoService.obterCandidatoPeloId(anyInt())).thenThrow(CandidatoNotFoundException)
        when:
            candidatoController.editarCandidato(this.candidato)
        then:
            thrown(CandidatoNotFoundException)
    }

    def "deletarCandidato com id inválido lança CandidatoNotFoundException"() {
        given:
            when(candidatoService.deletarCandidatoPeloId(anyInt())).thenThrow(CandidatoNotFoundException)
        when:
            candidatoController.deletarCandidato(1)
        then:
            thrown(CandidatoNotFoundException)
    }
}


