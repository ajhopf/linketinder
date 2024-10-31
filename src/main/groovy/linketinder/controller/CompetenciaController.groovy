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
}
