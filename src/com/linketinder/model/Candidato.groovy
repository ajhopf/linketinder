package com.linketinder.model

class Candidato extends Usuario {
    String sobrenome
    String cpf
    Date dataNascimento

    @Override
    String toString() {
        "Candidato: " +
                "$super.nome, " +
                "$sobrenome, " +
                "$super.email, " +
                "data de nascimento: $dataNascimento, " +
                "cpf: $cpf, " +
                "Competencias: $super.competencias" +
                "Endereço: $super.endereco"
    }
}
