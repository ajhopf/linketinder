package com.linketinder.model

import groovy.transform.ToString

//@ToString(includeSuperFields = true, includeSuper = true, includePackage = false)
class Candidato extends Pessoa {
    String cpf
    Integer idade

    @Override
    String toString() {
        "Candidato: $super.nome, $super.email, $idade, $cpf, $super.competencias"
    }
}
