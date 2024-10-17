package linketinder.view

import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.exceptions.EmpresaNotFoundException
import linketinder.exceptions.RepositoryAccessException
import linketinder.model.Empresa
import linketinder.model.Endereco
import linketinder.service.EmpresaService
import linketinder.util.InputHelpers

class EmpresaView {
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
        try {
            println "Criar nova Empresa"
            InputHelpers.printInfosIniciais()

            Empresa empresa = obterInformacoes(sc)

            Integer empresaId = service.adicionarEmpresa(empresa)

            println "Empresa cadastrada com sucesso. Id: $empresaId"
        } catch (RepositoryAccessException e) {
            println 'Falha ao adicionar empresa'
            println e.getMessage()
        }

    }

    static void editarEmpresa(EmpresaService empresaService, Scanner sc) {
        println "Editar Empresa"

        try {
            Integer empresaId = InputHelpers.getIntInput(0, 1000, "Digite o id da empresa para editar", sc)
            Empresa empresa = empresaService.obterEmpresaPeloId(empresaId)

            println "Empresa Selecionada: $empresa.nome"
            println "Digite as novas informações para a empresa"

            Empresa empresaAtualizada = obterInformacoes(sc)
            empresaAtualizada.id = empresaId

            empresaService.updateEmpresa(empresaAtualizada)

            println "Empresa atualizada com sucesso"
        } catch (CompetenciaNotFoundException e) {
            println e.getMessage()
        }

    }

    static void deletarEmpresa(EmpresaService empresaService, Scanner sc) {
        println "Deletar Competencia"

        try {
            Integer competenciaId = InputHelpers.getIntInput(0, 1000, "Digite o id da empresa para deletar", sc)

            empresaService.deleteEmpresa(competenciaId)

            println "Empresa deletada com sucesso"
        } catch (EmpresaNotFoundException e) {
            println e.getMessage()
        }
    }

}
