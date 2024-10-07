package com.linketinder.view

import com.linketinder.model.Vaga
import com.linketinder.service.VagaService

class VagaView {
    static void listarVagas(VagaService vagaService) {
        println "Vagas"

        List<Vaga> vagas = vagaService.listarVagas()

        for (vaga in vagas) {
            println """
            |-----------------------------------------------
            |ID: $vaga.id
            |Nome: $vaga.nome
            |Descrição: $vaga.descricao
            |Estado: $vaga.endereco.estado
            |Cidade: $vaga.endereco.cidade
            """.stripMargin()
        }
    }
}
