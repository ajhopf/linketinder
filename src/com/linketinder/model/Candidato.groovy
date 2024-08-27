package com.linketinder.model

class Candidato extends Pessoa {
    String cpf
    Integer idade

    @Override
    String toString() {
        "Candidato: $super.nome, $super.email, idade: $idade anos, cpf: $cpf, Competencias: $super.competencias"
    }
}
