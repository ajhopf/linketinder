package com.linketinder.model

import com.linketinder.model.enums.Afinidade

class Competencia extends Identificavel{
    String competencia
    Double anosExperiencia
    Afinidade afinidade

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
