package linketinder.view

import linketinder.controller.EmpresaController

import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.exceptions.EmpresaNotFoundException
import linketinder.exceptions.RepositoryAccessException

import linketinder.model.Empresa
import linketinder.model.Endereco

import linketinder.util.ViewHelpers

class EmpresaView {
    static String obterCnpj(Scanner sc) {
        String cnpj = ''

        boolean cnpjInvalido = true

        while(cnpjInvalido) {
            cnpj = ViewHelpers.obterString("Digite o cnpj no formato 00.000.000/0000-00", sc)
            if (cnpj ==~ /^\d{2}\.\d{3}\.\d{3}\/\d{4}-\d{2}$/) {
                cnpjInvalido = false
            } else {
                println 'Cnpj informado está em um formato inválido'
            }
        }

        return cnpj
    }

    static Empresa obterInformacoes(Scanner sc) {
        Map infosBasicas = ViewHelpers.obterInfosBasicas(sc)
        String cnpj = obterCnpj(sc)
        Endereco endereco = ViewHelpers.obterEndereco(sc)

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

    static void listarEmpresas(EmpresaController empresaController) {
        List<Empresa> empresas = empresaController.listarEmpresas()
        println "Empresas: "
        empresas.each {empresa -> ViewHelpers.printInfosDePessoa(empresa)}
    }

    static void adicionarEmpresa(EmpresaController empresaController, Scanner sc) {
        try {
            println "Criar nova Empresa"
            ViewHelpers.printInfosIniciais()

            Empresa empresa = obterInformacoes(sc)

            Integer empresaId = empresaController.adicionarEmpresa(empresa)

            println "Empresa cadastrada com sucesso. Id: $empresaId"
        } catch (RepositoryAccessException e) {
            println 'Falha ao adicionar empresa'
            println e.getMessage()
        }

    }

    static void editarEmpresa(EmpresaController empresaController, Scanner sc) {
        println "Editar Empresa"

        try {
            Integer empresaId = ViewHelpers.getIntInput(0, 1000, "Digite o id da empresa para editar", sc)
            Empresa empresa = empresaController.obterEmpresaPeloId(empresaId)

            println "Empresa Selecionada: $empresa.nome"
            println "Digite as novas informações para a empresa"

            Empresa empresaAtualizada = obterInformacoes(sc)

            empresaController.editarEmpresa(empresaAtualizada)

            println "Empresa atualizada com sucesso"
        } catch (CompetenciaNotFoundException e) {
            println e.getMessage()
        }

    }

    static void deletarEmpresa(EmpresaController empresaController, Scanner sc) {
        println "Deletar Competencia"

        try {
            Integer empresaId = ViewHelpers.getIntInput(0, 1000, "Digite o id da empresa para deletar", sc)

            empresaController.deletarEmpresa(empresaId)

            println "Empresa deletada com sucesso"
        } catch (EmpresaNotFoundException e) {
            println e.getMessage()
        }
    }

}
