package linketinder.view

import linketinder.service.CandidatoService
import linketinder.service.CompetenciaService
import linketinder.service.EmpresaService
import linketinder.service.VagaService
import linketinder.util.InputHelpers

class MenuInicial {
    static void iniciar(EmpresaService empresaService, CandidatoService candidatoService, CompetenciaService competenciaService, VagaService vagaService) {
        Scanner sc = new Scanner(System.in)

        while (true) {
            gerarMenuInicial()
            try {
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
                    case 4:
                        CompetenciaView.deletarCompetencia(competenciaService, sc)
                        break
                    case 5:
                        ListagemView.listarEmpresas(empresaService)
                        break
                    case 6:
                        EmpresaView.adicionarEmpresa(empresaService, sc)
                        break
                    case 7:
                        EmpresaView.editarEmpresa(empresaService, sc)
                        break
                    case 8:
                        EmpresaView.deletarEmpresa(empresaService, sc)
                        break
                    case 9:
                        VagaView.listarVagas(vagaService)
                        break
                    case 10:
                        VagaView.adicionarVaga(vagaService, empresaService, competenciaService, sc)
                        break
                    case 11:
                        VagaView.editarVaga(vagaService, competenciaService, sc)
                        break
                    case 12:
                        VagaView.deletarVaga(vagaService, sc)
                        break
                    case 13:
                        ListagemView.listarCandidatos(candidatoService)
                        break
                    case 14:
                        CandidatoView.adicionarCandidato(candidatoService, competenciaService, sc)
                        break
                    case 15:
                        CandidatoView.editarCandidato(candidatoService, competenciaService, sc)
                        break
                    case 16:
                        CandidatoView.deletarCandidato(candidatoService, sc)
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