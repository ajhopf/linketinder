package linketinder.view

import linketinder.controller.CompetenciaController
import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.model.Competencia
import linketinder.util.ViewHelpers

class CompetenciaView {
    static void adicionarCompetencia(CompetenciaController competenciaController, Scanner sc ) {
        println "Criar nova Competencia"

        String competencia = ViewHelpers.obterString('Digite a competência', sc)

        Integer competenciaId = competenciaController.adicionarCompetencia(competencia)

        println "Competencia adicionada com sucesso. Id gerado: $competenciaId"
    }

    static void listarCompetencias(CompetenciaController competenciaController) {
        println "Competencias"

        List<Competencia> competencias = competenciaController.listarCompetencias()

        for (competencia in competencias) {
            println "Id: $competencia.id | $competencia.competencia"
        }
    }

    static void editarCompetencia(CompetenciaController competenciaController, Scanner sc) {
        println "Editar Competencia"

        try {
            Integer competenciaId = ViewHelpers.getIntInput(0, 1000, "Digite o id da competencia para editar", sc)
            Competencia competencia = competenciaController.obterCompetenciaPeloId(competenciaId)

            println "Competencia Selecionada: $competencia.competencia"

            String competenciaNova = ViewHelpers.obterString('Digite o novo nome da competência', sc)
            competencia.competencia = competenciaNova

            competenciaController.editarCompetencia(competencia)

            println "Competencia Atualizada"
        } catch (CompetenciaNotFoundException e) {
            println e.getMessage()
        }

    }

    static void deletarCompetencia(CompetenciaController competenciaController, Scanner sc) {
        println "Deletar Competencia"

        try {
            Integer competenciaId = ViewHelpers.getIntInput(0, 1000, "Digite o id da competencia para deletar", sc)

            competenciaController.deletarCompetencia(competenciaId)

            println "Competencia Deletada"
        } catch (CompetenciaNotFoundException e) {
            println e.getMessage()
        }

    }
}
