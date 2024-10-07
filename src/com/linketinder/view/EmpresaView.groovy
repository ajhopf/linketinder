package com.linketinder.view

import com.linketinder.exceptions.CompetenciaNotFoundException
import com.linketinder.model.Empresa
import com.linketinder.model.Endereco
import com.linketinder.service.EmpresaService
import com.linketinder.util.InputHelpers

class EmpresaView {
    static Empresa obterInformacoes(sc) {
        Map infosBasicas = InputHelpers.obterInfosBasicas(sc)
        String cnpj = obterCnpj(sc)
        Endereco endereco = InputHelpers.obterEndereco(sc)

        Empresa empresa = new Empresa(
                nome: infosBasicas.nome,
                email: infosBasicas.email,
                descricao: infosBasicas.descricao,
                senha: infosBasicas.senha,
                cnpj: cnpj,
                endereco: endereco
        )

        return empresa
    }

    static void adicionarEmpresa(EmpresaService service, Scanner sc) {
        println "Criar nova Empresa"
        InputHelpers.printInfosIniciais()

        Empresa empresa = obterInformacoes(sc)

        service.adicionarEmpresa(empresa)
    }

    static void editarEmpresa(EmpresaService empresaService, Scanner sc) {
        println "Editar Empresa"

        boolean idInvalido = true

        while (idInvalido) {
            try {
                Integer empresaId = InputHelpers.getIntInput(0, 1000, "Digite o id da empresa para editar", sc)
                Empresa empresa = empresaService.obterEmpresaPeloId(empresaId)
                idInvalido = false

                println "Empresa Selecionada: $empresa.nome"
                println "Digite as novas informações para a empresa"

                Empresa empresaAtualizada = obterInformacoes(sc)
                empresaAtualizada.id = empresaId

                empresaService.updateEmpresa(empresaAtualizada)
            } catch (CompetenciaNotFoundException e) {
                println e.getMessage()
            }
        }
    }

    static String obterCnpj(Scanner sc) {
        String cnpj = ''

        boolean cnpjInvalido = true

        while(cnpjInvalido) {
            cnpj = InputHelpers.obterString("Digite o cnpj no formato 00.000.000/0000-00", sc)
            if (cnpj ==~ /^\d{2}\.\d{3}\.\d{3}\/\d{4}-\d{2}$/) {
                cnpjInvalido = false
            } else {
                println 'Cnpj informado está em um formato inválido'
            }
        }

        return cnpj
    }
}
