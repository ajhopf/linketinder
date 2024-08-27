package com.linketinder.model

import groovy.transform.ToString

@ToString(includePackage = false)
class Competencia {
    String competencia

    Competencia(String competencia) {
        this.competencia = competencia
    }
}
