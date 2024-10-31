package linketinder.view

import linketinder.controller.CandidatoController
import linketinder.controller.CompetenciaController
import linketinder.controller.EmpresaController
import linketinder.controller.VagaController

import linketinder.util.ViewHelpers

class MenuInicial {
    static void iniciar(
            CompetenciaController competenciaController,
            EmpresaController empresaController,
            VagaController vagaController,
            CandidatoController candidatoController) {
        Scanner sc = new Scanner(System.in)

        while (true) {
            gerarMenuInicial()
            try {
                int opcaoSelecionada = ViewHelpers.getIntInput(0, 16, "Selecione a opção desejada: ", sc)

                switch (opcaoSelecionada) {
                    case 0:
                        println "Até logo!"
                        return
                    case 1:
                        CompetenciaView.listarCompetencias(competenciaController)
                        break
                    case 2:
                        CompetenciaView.adicionarCompetencia(competenciaController, sc)
                        break
                    case 3:
                        CompetenciaView.editarCompetencia(competenciaController, sc)
                        break
                    case 4:
                        CompetenciaView.deletarCompetencia(competenciaController, sc)
                        break
                    case 5:
                        EmpresaView.listarEmpresas(empresaController)
                        break
                    case 6:
                        EmpresaView.adicionarEmpresa(empresaController, sc)
                        break
                    case 7:
                        EmpresaView.editarEmpresa(empresaController, sc)
                        break
                    case 8:
                        EmpresaView.deletarEmpresa(empresaController, sc)
                        break
                    case 9:
                        VagaView.listarVagas(vagaController)
                        break
                    case 10:
                        VagaView.adicionarVaga(vagaController, empresaController, sc)
                        break
                    case 11:
                        VagaView.editarVaga(vagaController, sc)
                        break
                    case 12:
                        VagaView.deletarVaga(vagaController, sc)
                        break
                    case 13:
                        CandidatoView.listarCandidatos(candidatoController)
                        break
                    case 14:
                        CandidatoView.adicionarCandidato(candidatoController, sc)
                        break
                    case 15:
                        CandidatoView.editarCandidato(candidatoController, sc)
                        break
                    case 16:
                        CandidatoView.deletarCandidato(candidatoController, sc)
                        break

                    default: println "Você escolheu a opção " + opcaoSelecionada
                }

            } catch (Exception e) {
                e.getMessage()
                e.printStackTrace()
                return
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
