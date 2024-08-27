package com.linketinder.view

import com.linketinder.model.Candidato
import com.linketinder.service.CandidatoService

class CandidatoView {
    static void listarCandidatos(CandidatoService service) {
        List<Candidato> candidatos = service.listarCandidatos()
        println "Candidatos: "
        candidatos.each {candidato -> println """
            |-----------------------------------------------
            |ID: $candidato.id
            |Nome: $candidato.nome
            |E-mail: $candidato.email
            |Idade: $candidato.idade
            |CPF: $candidato.cpf
            |País: $candidato.endereco.pais
            |Estado: $candidato.endereco.estado
            |Descrição: $candidato.descricao
            |Competências: $candidato.competencias
            """.stripMargin()
        }
    }
}
