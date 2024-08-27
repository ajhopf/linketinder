package com.linketinder.view

import com.linketinder.model.Candidato
import com.linketinder.service.CandidatoService
import com.linketinder.service.EmpresaService
import com.linketinder.util.MyUtil

class MenuInicial {
    static void iniciar(EmpresaService empresaService, CandidatoService candidatoService) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            gerarMenuInicial();

            int opcaoSelecionada = MyUtil.getIntInput(0, 2, "Selecione a opção desejada: ", sc);

            switch (opcaoSelecionada) {
                case 0:
                    println "Até logo!"
                    break
                case 1:
                    EmpresaView.listarEmpresas(empresaService)
                    break
                case 2:
                    CandidatoView.listarCandidatos(candidatoService)
                    break
                default: System.out.println("Você escolheu a opção " + opcaoSelecionada);
            }
        }

    }

    static void gerarMenuInicial(){
        println '''---------------------------------------------
        |Bem vindo ao Linketinder
               
        |Selecione uma das opções abaixo:
        |1. Listar Empresas
        |2. Listar Candidatos
        |0. Sair do Sistema
        |---------------------------------------------
        '''.stripMargin()
    }

}
