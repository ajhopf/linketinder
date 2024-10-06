package com.linketinder.view

import com.linketinder.exceptions.CandidatoNotFoundException
import com.linketinder.service.CandidatoService
import com.linketinder.util.InputHelpers

class DeletarView {
    static void deletarCandidato(CandidatoService service, Scanner sc) {
        println "Deletar Candidato"

        boolean idInvalido = true

        while(idInvalido) {
            Integer idDoCandidato = InputHelpers.getIntInput(0, 5000, 'Digite o id do candidato', sc)
            try {
                service.deletarCandidatoPeloId(idDoCandidato)
                println 'Candidato deletado com sucesso'
                idInvalido = false
            } catch (CandidatoNotFoundException e) {
                println e.getMessage()
            }
        }

    }

}
