package com.linketinder.model.mappers

import com.linketinder.model.Competencia
import com.linketinder.model.Endereco
import com.linketinder.model.dtos.CompetenciaDTO
import com.linketinder.model.dtos.EnderecoDTO

class CompetenciaMapper {
    static CompetenciaDTO toDTO(Competencia competencia) {
        return new CompetenciaDTO(
                competencia: competencia.competencia,
                afinidade: competencia.afinidade,
                anosExperiencia: competencia.anosExperiencia
        )
    }

    static Competencia toEntity(CompetenciaDTO competenciaDTO) {
        return new Competencia(competenciaDTO.competencia, competenciaDTO.anosExperiencia, competenciaDTO.afinidade)
    }
}