package com.linketinder.model

import com.linketinder.model.enums.Afinidade
import com.linketinder.service.CompetenciaService

class Competencia extends Identificavel{
    String competencia
    Double anosExperiencia
    Afinidade afinidade

    Competencia(Integer id, String competencia, Double anosExperiencia, Afinidade afinidade) {
        this.competencia = competencia
        this.anosExperiencia = anosExperiencia
        this.afinidade = afinidade
        super.id = id
    }


    Competencia(String competencia, Double anosExperiencia, Afinidade afinidade) {
        this.competencia = competencia
        this.anosExperiencia = anosExperiencia
        this.afinidade = afinidade
    }

    @Override
    String toString() {
        "$competencia"
    }
}
