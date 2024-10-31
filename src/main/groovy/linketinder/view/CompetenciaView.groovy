package linketinder.view

import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.model.Competencia
import linketinder.service.CompetenciaService
import linketinder.util.ViewHelpers

class CompetenciaView {
    static void adicionarCompetencia(CompetenciaService competenciaService, Scanner sc ) {
        println "Criar nova Competencia"

        String competencia = ViewHelpers.obterString('Digite a competência', sc)

        Integer competenciaId = competenciaService.adicionarCompetencia(competencia)

        println "Competencia adicionada com sucesso. Id gerado: $competenciaId"
    }

    static void listarCompetencias(CompetenciaService competenciaService) {
        println "Competencias"

        List<Competencia> competencias = competenciaService.listarCompetencias()

        for (competencia in competencias) {
            println "Id: $competencia.id | $competencia.competencia"
        }
    }

    static void editarCompetencia(CompetenciaService competenciaService, Scanner sc) {
        println "Editar Competencia"

        boolean idInvalido = true

        while (idInvalido) {
            try {
                Integer competenciaId = ViewHelpers.getIntInput(0, 1000, "Digite o id da competencia para editar", sc)
                Competencia competencia = competenciaService.obterCompetenciaPeloId(competenciaId)
                idInvalido = false

                println "Competencia Selecionada: $competencia.competencia"

                String competenciaNova = ViewHelpers.obterString('Digite o novo nome da competência', sc)
                competencia.competencia = competenciaNova

                competenciaService.updateCompetencia(competenciaId, competencia)

                println "Competencia Atualizada"
            } catch (CompetenciaNotFoundException e) {
                println e.getMessage()
            }
        }
    }

    static void deletarCompetencia(CompetenciaService competenciaService, Scanner sc) {
        println "Deletar Competencia"

        try {
            Integer competenciaId = ViewHelpers.getIntInput(0, 1000, "Digite o id da competencia para deletar", sc)

            competenciaService.deletarCompetencia(competenciaId)

            println "Competencia Deletada"
        } catch (CompetenciaNotFoundException e) {
            println e.getMessage()
        }

    }
}
