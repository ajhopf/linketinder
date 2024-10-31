package linketinder.controller

import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.exceptions.RepositoryAccessException
import linketinder.exceptions.VagaNotFoundException
import linketinder.model.Vaga
import linketinder.service.VagaService

class VagaController {
    private final VagaService vagaService
    private final CompetenciaController competenciaController

    VagaController(VagaService vagaService, CompetenciaController competenciaController) {
        this.vagaService = vagaService
        this.competenciaController = competenciaController
    }

    List<Vaga> listarVagas() throws RepositoryAccessException {
        return vagaService.listarVagas()
    }

    Vaga obterVagaPeloId(Integer id) throws RepositoryAccessException, VagaNotFoundException {
        return vagaService.obterVagaPeloId(id)
    }

    Integer adicionarVaga(Vaga vaga) throws RepositoryAccessException, CompetenciaNotFoundException {
        vaga.competencias.each {competencia ->
            competenciaController.obterIdDeCompetencia(competencia.competencia)
        }

        vagaService.adicionarVaga(vaga)
    }

    void editarVaga(Vaga vagaAtualizada) throws VagaNotFoundException, RepositoryAccessException, CompetenciaNotFoundException {
        vagaAtualizada.competencias.each {competencia ->
            competenciaController.obterIdDeCompetencia(competencia.competencia)
        }
        this.obterVagaPeloId(vagaAtualizada.id)

        vagaService.updateVaga(vagaAtualizada)
    }

    void deletarVaga(Integer id) throws RepositoryAccessException, VagaNotFoundException {
        vagaService.deletarVaga(id)
    }
}
