package com.linketinder.view

import com.linketinder.exceptions.CompetenciaNotFoundException
import com.linketinder.exceptions.EmpresaNotFoundException
import com.linketinder.model.Competencia
import com.linketinder.model.Empresa
import com.linketinder.model.Endereco
import com.linketinder.model.Vaga
import com.linketinder.service.EmpresaService
import com.linketinder.service.VagaService
import com.linketinder.util.InputHelpers

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

    static void adicionarVaga(VagaService vagaService, EmpresaService empresaService, Scanner sc) {
        println "Adicionar Vaga"

        boolean idInvalido = true

        while (idInvalido) {
            try {
                Integer empresaId = InputHelpers.getIntInput(0, 1000, "Digite o id da empresa para adicionar uma vaga", sc)
                Empresa empresa = empresaService.obterEmpresaPeloId(empresaId)

                idInvalido = false

                println "Empresa Selecionada: $empresa.nome"

                String tituloVaga = InputHelpers.obterString('Digite o titulo da vaga', sc)
                String descricaoVaga = InputHelpers.obterString('Digite a descricao da vaga', sc)
                Endereco enderecoVaga = InputHelpers.obterEndereco(sc, true)
                List<Competencia> competencias = InputHelpers.obterCompetencias(sc)

                Vaga vaga = new Vaga(nome: tituloVaga, descricao: descricaoVaga, endereco: enderecoVaga, empresa: empresa, competencias: competencias)
                Integer vagaId = vagaService.adicionarVaga(vaga)

                println "Vaga adicionada com sucesso. Id: $vagaId"
            } catch (EmpresaNotFoundException e) {
                println e.getMessage()
            }
        }
    }
}
