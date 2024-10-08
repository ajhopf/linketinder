package com.linketinder.model

import java.time.LocalDate

class Candidato extends Usuario {
    String sobrenome
    String cpf
    LocalDate dataNascimento
    String telefone
    List<Competencia> competencias

    @Override
    String toString() {
        "Candidato: " +
                "$super.nome, " +
                "$sobrenome, " +
                "$super.email, " +
                "data de nascimento: $dataNascimento, " +
                "telefone: $telefone" +
                "cpf: $cpf, " +
                "Competencias: $competencias" +
                "Endereço: $super.endereco"
    }
}
