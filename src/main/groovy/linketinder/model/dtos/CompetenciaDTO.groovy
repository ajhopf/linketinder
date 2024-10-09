package linketinder.model.dtos

import linketinder.model.enums.Afinidade
import groovy.transform.ToString

@ToString
class CompetenciaDTO {
    Integer id
    String competencia
    Double anosExperiencia
    Afinidade afinidade
}
