package linketinder.model.mappers

import linketinder.model.Competencia
import linketinder.model.dtos.CompetenciaDTO

class CompetenciaMapper {
    static CompetenciaDTO toDTO(Competencia competencia) {
        return new CompetenciaDTO(
                competencia: competencia.competencia,
                afinidade: competencia.afinidade,
                anosExperiencia: competencia.anosExperiencia
        )
    }

    static Competencia toEntity(CompetenciaDTO competenciaDTO) {
        return new Competencia(competenciaDTO.id, competenciaDTO.competencia, competenciaDTO.anosExperiencia, competenciaDTO.afinidade)
    }

    static List<Competencia> dtoListToEntityList(List<CompetenciaDTO> competenciasDTO) {
        List<Competencia> competencias = []

        for (competencia in competenciasDTO) {
            competencias << toEntity(competencia)
        }

        return competencias
    }
}
