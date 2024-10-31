package linketinder.view

import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.exceptions.EmpresaNotFoundException
import linketinder.exceptions.VagaNotFoundException
import linketinder.model.Competencia
import linketinder.model.Empresa
import linketinder.model.Endereco
import linketinder.model.Vaga
import linketinder.service.CompetenciaService
import linketinder.service.EmpresaService
import linketinder.service.VagaService
import linketinder.util.ViewHelpers

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


    static void adicionarVaga(VagaService vagaService, EmpresaService empresaService, CompetenciaService competenciaService, Scanner sc) {
        println "Adicionar Vaga"

        try {
            Integer empresaId = ViewHelpers.getIntInput(0, 1000, "Digite o id da empresa para adicionar uma vaga", sc)
            Empresa empresa = empresaService.obterEmpresaPeloId(empresaId)

            println "Empresa Selecionada: $empresa.nome"

            Vaga vaga = obterInfosDeVaga(sc, empresa)

            vaga.competencias.each {competencia ->
                competenciaService.obterIdDeCompetencia(competencia.competencia)
            }

            Integer vagaId = vagaService.adicionarVaga(vaga)

            println "Vaga adicionada com sucesso. Id: $vagaId"
        } catch (EmpresaNotFoundException e) {
            println e.getMessage()
        }

    }

    static void editarVaga(VagaService vagaService, CompetenciaService competenciaService, Scanner sc) {
        println "Editar Vaga"
        try {
            Integer vagaId = ViewHelpers.getIntInput(0, 1000, "Digite o id da vaga para editar", sc)
            Vaga vaga = vagaService.obterVagaPeloId(vagaId)

            Vaga vagaAtualizada = obterInfosDeVaga(sc)
            vagaAtualizada.id = vaga.id

            vaga.competencias.each {competencia ->
                competenciaService.obterIdDeCompetencia(competencia.competencia)
            }

            vagaService.updateVaga(vagaAtualizada)

            println "Vaga atualizada com sucesso"
        } catch (CompetenciaNotFoundException e) {
            println e.getMessage()
        }
    }

    static void deletarVaga(VagaService service, Scanner sc) {
        println "Deletar Vaga"

        Integer idDaVaga = ViewHelpers.getIntInput(0, 5000, 'Digite o id da vaga', sc)
        try {
            service.deletarVaga(idDaVaga)
            println 'Vaga deletada com sucesso'

        } catch (VagaNotFoundException e) {
            println e.getMessage()
        }

    }
}
