package com.linketinder.view

import com.linketinder.service.CandidatoService
import com.linketinder.service.CompetenciaService
import com.linketinder.service.EmpresaService
import com.linketinder.util.InputHelpers
import com.linketinder.util.MyUtil

class MenuInicial {
    static void iniciar(EmpresaService empresaService, CandidatoService candidatoService, CompetenciaService competenciaService) {
        Scanner sc = new Scanner(System.in)

        while (true) {
            gerarMenuInicial()

            int opcaoSelecionada = InputHelpers.getIntInput(0, 16, "Selecione a opção desejada: ", sc)

            switch (opcaoSelecionada) {
                case 0:
                    println "Até logo!"
                    return
                case 1:
                    CompetenciaView.listarCompetencias(competenciaService)
                    break
                case 2:
                    CompetenciaView.adicionarCompetencia(competenciaService, sc)
                    break
                case 3:
                    CompetenciaView.editarCompetencia(competenciaService, sc)
                    break
                case 5:
                    ListagemView.listarEmpresas(empresaService)
                    break
                case 6:
                    InputHelpers.adicionarEmpresa(empresaService, sc)
                    break
                case 13:
                    ListagemView.listarCandidatos(candidatoService)
                    break
                case 14:
                    CandidatoView.adicionarCandidato(candidatoService, sc)
                    break
                case 16:
                    DeletarView.deletarCandidato(candidatoService, sc)
                    break

                default: println "Você escolheu a opção " + opcaoSelecionada
            }
        }

    }

    static void gerarMenuInicial(){
        println '''---------------------------------------------
        |Bem vindo ao Linketinder
               
        |Selecione uma das opções abaixo:
        |----- Competências -----
        |1. Listar Competências do Sistema
        |2. Adicionar Competência
        |3. Editar Competência
        |4. Deletar Competência
        |-----  Empresas   -----
        |5. Listar Empresas
        |6. Adicionar Empresa
        |7. Editar Empresa
        |8. Deletar Empresa
        |-----    Vagas    -----
        |9. Listar Vagas
        |10. Adicionar Vaga
        |11. Editar Vaga
        |12. Deletar Vaga        
        |----- Candidatos  -----
        |13. Listar Candidatos
        |14. Adicionar Candidato
        |15. Editar Candidato
        |16. Deletar Candidato
        -----              -----
        |0. Sair do Sistema
        |---------------------------------------------
        '''.stripMargin()
    }

}
