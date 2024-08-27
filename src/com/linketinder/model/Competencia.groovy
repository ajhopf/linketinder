package com.linketinder.model

import groovy.transform.ToString


class Competencia {
    String competencia

    Competencia(String competencia) {
        this.competencia = competencia
    }

    @Override
    String toString() {
        "$competencia"
    }
}
