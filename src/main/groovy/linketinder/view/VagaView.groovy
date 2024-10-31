package linketinder.view

import linketinder.controller.EmpresaController
import linketinder.controller.VagaController

import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.exceptions.EmpresaNotFoundException
import linketinder.exceptions.VagaNotFoundException

import linketinder.model.Competencia
import linketinder.model.Empresa
import linketinder.model.Endereco
import linketinder.model.Vaga

import linketinder.util.ViewHelpers

class VagaView {
    static void listarVagas(VagaController vagaController) {
        println "Vagas"

        List<Vaga> vagas = vagaController.listarVagas()

        for (vaga in vagas) {
            println """
            |-----------------------------------------------
            |ID: $vaga.id
            |Nome: $vaga.nome
            |Descrição: $vaga.descricao
            |Estado: $vaga.endereco.estado
            |Cidade: $vaga.endereco.cidade
            |Competencias: $vaga.competencias
            """.stripMargin()
        }
    }

    static Vaga obterInfosDeVaga (Scanner sc, Empresa empresa = new Empresa()) {
        String tituloVaga = ViewHelpers.obterString('Digite o titulo da vaga', sc)
        String descricaoVaga = ViewHelpers.obterString('Digite a descricao da vaga', sc)
        Endereco enderecoVaga = ViewHelpers.obterEndereco(sc, true)
        List<Competencia> competencias = ViewHelpers.obterCompetencias(sc)

        Vaga vaga = new Vaga(nome: tituloVaga, descricao: descricaoVaga, endereco: enderecoVaga, empresa: empresa, competencias: competencias)

        return vaga
    }


    static void adicionarVaga(VagaController vagaController, EmpresaController empresaController, Scanner sc) {
        println "Adicionar Vaga"

        try {
            Integer empresaId = ViewHelpers.getIntInput(0, 1000, "Digite o id da empresa para adicionar uma vaga", sc)
            Empresa empresa = empresaController.obterEmpresaPeloId(empresaId)

            println "Empresa Selecionada: $empresa.nome"

            Vaga vaga = obterInfosDeVaga(sc, empresa)

            Integer vagaId = vagaController.adicionarVaga(vaga)

            println "Vaga adicionada com sucesso. Id: $vagaId"
        } catch (EmpresaNotFoundException e) {
            println e.getMessage()
        } catch (CompetenciaNotFoundException e) {
            println e.getMessage()
        }

    }

    static void editarVaga(VagaController vagaController, Scanner sc) {
        println "Editar Vaga"
        try {
            Integer vagaId = ViewHelpers.getIntInput(0, 1000, "Digite o id da vaga para editar", sc)
            Vaga vaga = vagaController.obterVagaPeloId(vagaId)

            Vaga vagaAtualizada = obterInfosDeVaga(sc)
            vagaAtualizada.id = vaga.id

            vagaController.editarVaga(vagaAtualizada)

            println "Vaga atualizada com sucesso"
        } catch (CompetenciaNotFoundException e) {
            println e.getMessage()
        }
    }

    static void deletarVaga(VagaController vagaController, Scanner sc) {
        println "Deletar Vaga"

        Integer idDaVaga = ViewHelpers.getIntInput(0, 5000, 'Digite o id da vaga', sc)
        try {
            vagaController.deletarVaga(idDaVaga)
            println 'Vaga deletada com sucesso'

        } catch (VagaNotFoundException e) {
            println e.getMessage()
        }

    }
}
