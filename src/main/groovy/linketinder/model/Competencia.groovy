package linketinder.model

import linketinder.model.enums.Afinidade

class Competencia extends Identificavel{
    String competencia
    Double anosExperiencia
    Afinidade afinidade

    Competencia() {}

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
        "Nome: $competencia; Anos Experiência: $anosExperiencia; Afinidade: $afinidade"
    }
}
