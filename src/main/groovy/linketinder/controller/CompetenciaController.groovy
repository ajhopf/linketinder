package linketinder.controller

import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.exceptions.RepositoryAccessException
import linketinder.model.Competencia
import linketinder.service.CompetenciaService

class CompetenciaController {
    private final CompetenciaService competenciaService

    CompetenciaController(CompetenciaService competenciaService) {
        this.competenciaService = competenciaService
    }

    Integer obterIdDeCompetencia(String competencia) throws CompetenciaNotFoundException, RepositoryAccessException {
        return competenciaService.obterIdDeCompetencia(competencia)
    }

    Competencia obterCompetenciaPeloId(Integer id) throws CompetenciaNotFoundException, RepositoryAccessException {
        return competenciaService.obterCompetenciaPeloId(id)
    }

    Integer adicionarCompetencia(String competencia) throws RepositoryAccessException {
        return competenciaService.adicionarCompetencia(competencia)
    }

    List<Competencia> listarCompetencias() throws RepositoryAccessException {
        List<Competencia> competencias = competenciaService.listarCompetencias()

        return competencias
    }

    void editarCompetencia(Competencia competencia) throws CompetenciaNotFoundException, RepositoryAccessException {
        competenciaService.editarCompetencia(competencia)
    }

    void deletarCompetencia(Integer id) throws CompetenciaNotFoundException, RepositoryAccessException {
        competenciaService.deletarCompetencia(id)
    }
}
