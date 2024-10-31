package linketinder.controller

import linketinder.exceptions.CandidatoNotFoundException
import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.exceptions.RepositoryAccessException
import linketinder.model.Candidato
import linketinder.service.CandidatoService

class CandidatoController {
    private final CandidatoService candidatoService
    private final CompetenciaController competenciaController

    CandidatoController(CandidatoService candidatoService, CompetenciaController competenciaController) {
        this.candidatoService = candidatoService
        this.competenciaController = competenciaController
    }


    Candidato obterCandidatoPeloId(Integer id) throws CandidatoNotFoundException, RepositoryAccessException {
        return candidatoService.obterCandidatoPeloId(id)
    }


    List<Candidato> listarCandidatos() {
        return candidatoService.listarCandidatos()
    }


    Integer adicionarCandidato(Candidato candidato) throws CompetenciaNotFoundException, RepositoryAccessException {
        candidato.competencias.each { competencia ->
            competenciaController.obterIdDeCompetencia(competencia.competencia)
        }

        return candidatoService.adicionarCandidato(candidato)
    }


    void editarCandidato(Candidato candidatoAtualizado) throws CompetenciaNotFoundException, RepositoryAccessException {
        candidatoAtualizado.competencias.each { competencia ->
            competenciaController.obterIdDeCompetencia(competencia.competencia)
        }

        this.obterCandidatoPeloId(candidatoAtualizado.id)

        candidatoService.updateCandidato(candidatoAtualizado)
    }


    void deletarCandidato(Integer idDoCandidato) throws CompetenciaNotFoundException, RepositoryAccessException  {
        candidatoService.deletarCandidatoPeloId(idDoCandidato)
    }
}
