package linketinder.controller

import linketinder.exceptions.CandidatoNotFoundException
import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.exceptions.RepositoryAccessException
import linketinder.model.Candidato
import linketinder.model.Competencia
import linketinder.service.CandidatoService

class CandidatoController {
    private final CandidatoService candidatoService
    private final CompetenciaController competenciaController

    CandidatoController(CandidatoService candidatoService, CompetenciaController competenciaController) {
        this.candidatoService = candidatoService
        this.competenciaController = competenciaController
    }

    private verificarSeCompetenciasExistem(List<Competencia> competencias) throws CompetenciaNotFoundException {
        competencias.each { competencia ->
            competenciaController.obterIdDeCompetencia(competencia.competencia)
        }
    }


    Candidato obterCandidatoPeloId(Integer id) throws CandidatoNotFoundException, RepositoryAccessException {
        return candidatoService.obterCandidatoPeloId(id)
    }


    List<Candidato> listarCandidatos() {
        return candidatoService.listarCandidatos()
    }


    Integer adicionarCandidato(Candidato candidato) throws CompetenciaNotFoundException, RepositoryAccessException {
        this.verificarSeCompetenciasExistem(candidato.competencias)

        return candidatoService.adicionarCandidato(candidato)
    }


    void editarCandidato(Candidato candidatoAtualizado) throws CandidatoNotFoundException, CompetenciaNotFoundException, RepositoryAccessException {
        this.verificarSeCompetenciasExistem(candidatoAtualizado.competencias)
        this.obterCandidatoPeloId(candidatoAtualizado.id)

        candidatoService.updateCandidato(candidatoAtualizado)
    }


    void deletarCandidato(Integer idDoCandidato) throws CompetenciaNotFoundException, RepositoryAccessException  {
        candidatoService.deletarCandidatoPeloId(idDoCandidato)
    }
}
