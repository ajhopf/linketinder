package linketinder.controller

import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.exceptions.VagaNotFoundException
import linketinder.model.Competencia
import linketinder.model.Vaga
import linketinder.model.enums.Afinidade
import linketinder.service.VagaService
import spock.lang.Specification

import static org.mockito.Mockito.*

class VagaControllerTest extends Specification {
    VagaService vagaService = mock(VagaService.class)
    CompetenciaController competenciaController = mock(CompetenciaController.class)
    VagaController vagaController = new VagaController(vagaService, competenciaController)
    Vaga vaga


    def setup() {
        Vaga vaga = new Vaga()
        vaga.id = 1
        vaga.nome = 'Dev Fullstack'
        vaga.competencias = [new Competencia('Java', 10, Afinidade.ALTA)]
        this.vaga = vaga
    }

    def "listarVagas retorna lista de vagas"() {
        given:
            List<Vaga> vagas = [this.vaga, this.vaga]
            when(vagaService.listarVagas()).thenReturn(vagas)

        when:
            List<Vaga> result = vagaController.listarVagas()

        then:
            result.size() == 2
            result[0].nome == 'Dev Fullstack'
    }

    def "obterVagaPeloId lança VagaNotFoundException"() {
        given:
            when(vagaService.obterVagaPeloId(anyInt())).thenThrow(VagaNotFoundException)

        when:
            vagaController.obterVagaPeloId(1)

        then:
            thrown(VagaNotFoundException)
    }

    def "obterVagaPeloId retorna vaga"() {
        given:
            when(vagaService.obterVagaPeloId(anyInt())).thenReturn(this.vaga)

        when:
            Vaga result = vagaController.obterVagaPeloId(1)

        then:
            result.nome == 'Dev Fullstack'
    }

    def "adicionarVaga lança CompetenciaNotFoundException"() {
        given:
            when(competenciaController.obterIdDeCompetencia(anyString())).thenThrow(CompetenciaNotFoundException)

        when:
            vagaController.adicionarVaga(this.vaga)

        then:
            thrown(CompetenciaNotFoundException)
    }

    def "editarVaga lança CompetenciaNotFoundException"() {
        given:
            when(competenciaController.obterIdDeCompetencia(anyString())).thenThrow(CompetenciaNotFoundException)

        when:
            vagaController.editarVaga(this.vaga)

        then:
            thrown(CompetenciaNotFoundException)
    }

    def "editarVaga lança VagaNotFoundException"() {
        given:
            when(vagaService.obterVagaPeloId(anyInt())).thenThrow(VagaNotFoundException)

        when:
            vagaController.editarVaga(this.vaga)

        then:
            thrown(VagaNotFoundException)
    }

    def "deletarVaga lança VagaNotFoundException"() {
        given:
            when(vagaService.deletarVaga(anyInt())).thenThrow(VagaNotFoundException)

        when:
            vagaController.deletarVaga(1)

        then:
            thrown(VagaNotFoundException)
    }
}
