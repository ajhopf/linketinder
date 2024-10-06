package com.linketinder.view

import com.linketinder.service.CandidatoService
import com.linketinder.service.EmpresaService
import com.linketinder.util.MyUtil

class MenuInicial {
    static void iniciar(EmpresaService empresaService, CandidatoService candidatoService) {
        Scanner sc = new Scanner(System.in)

        while (true) {
            gerarMenuInicial()

            int opcaoSelecionada = MyUtil.getIntInput(0, 5, "Selecione a opção desejada: ", sc)

            switch (opcaoSelecionada) {
                case 0:
                    println "Até logo!"
                    return
                case 1:
                    ListagemView.listarEmpresas(empresaService)
                    break
                case 2:
                    CadastroView.adicionarEmpresa(empresaService, sc)
                    break
                case 3:
                    ListagemView.listarCandidatos(candidatoService)
                    break
                case 4:
                    CadastroCandidatoView.adicionarCandidato(candidatoService, sc)
                    break
                case 5:
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
        |1. Listar Empresas
        |2. Adicionar Empresa
        |3. Listar Candidatos
        |4. Adicionar Candidato
        |5. Deletar Candidato
        |0. Sair do Sistema
        |---------------------------------------------
        '''.stripMargin()
    }

}
