package com.linketinder.view

import com.linketinder.exceptions.CompetenciaNotFoundException
import com.linketinder.model.Competencia
import com.linketinder.service.CompetenciaService
import com.linketinder.util.InputHelpers

class CompetenciaView {
    static void adicionarCompetencia(CompetenciaService competenciaService, Scanner sc ) {
        println "Criar nova Competencia"

        String competencia = InputHelpers.obterString('Digite a competência', sc)

        competenciaService.adicionarCompetencia(competencia)
    }

    static void listarCompetencias(CompetenciaService competenciaService) {
        println "Competencias"

        List<Competencia> competencias = competenciaService.listarCompetencias()

        competencias.each {competencia ->
            println "Id: $competencia.id | $competencia.competencia"
        }
    }

    static void editarCompetencia(CompetenciaService competenciaService, Scanner sc) {
        println "Editar Competencia"

        boolean idInvalido = true

        while (idInvalido) {
            try {
                Integer competenciaId = InputHelpers.getIntInput(0, 1000, "Digite o id da competencia para editar", sc)
                Competencia competencia = competenciaService.obterCompetenciaPeloId(competenciaId)
                idInvalido = false

                println "Competencia Selecionada: $competencia.competencia"

                String competenciaNova = InputHelpers.obterString('Digite o novo nome da competência', sc)
                competencia.competencia = competenciaNova

                competenciaService.updateCompetencia(competenciaId, competencia)
            } catch (CompetenciaNotFoundException e) {
                println e.getMessage()
            }
        }
    }

    static void deletarCompetencia(CompetenciaService competenciaService, Scanner sc) {
        println "Deletar Competencia"

        try {
            Integer competenciaId = InputHelpers.getIntInput(0, 1000, "Digite o id da competencia para deletar", sc)

            competenciaService.deletarCompetencia(competenciaId)
        } catch (CompetenciaNotFoundException e) {
            println e.getMessage()
        }

    }
}
