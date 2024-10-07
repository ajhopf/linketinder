package com.linketinder.model.dtos

import com.linketinder.model.enums.Afinidade
import groovy.transform.ToString

@ToString
class CompetenciaDTO {
    Integer id
    String competencia
    Double anosExperiencia
    Afinidade afinidade
}
