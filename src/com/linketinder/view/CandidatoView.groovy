package com.linketinder.view

import com.linketinder.model.Candidato
import com.linketinder.model.Empresa
import com.linketinder.service.CandidatoService
import com.linketinder.service.EmpresaService

class CandidatoView {
    static void listarCandidatos(CandidatoService service) {
        List<Candidato> candidatos = service.listarCandidatos()
        println "Candidatos: "
        candidatos.each {candidato -> println """
            |-----------------------------------------------
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
