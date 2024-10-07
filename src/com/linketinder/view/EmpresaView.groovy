package com.linketinder.view

import com.linketinder.model.Empresa
import com.linketinder.model.Endereco
import com.linketinder.service.EmpresaService
import com.linketinder.util.InputHelpers

class EmpresaView {
    static void adicionarEmpresa(EmpresaService service, Scanner sc) {
        println "Criar nova Empresa"
        InputHelpers.printInfosIniciais()

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

        service.adicionarEmpresa(empresa)
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
