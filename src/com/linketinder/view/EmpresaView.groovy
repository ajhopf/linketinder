package com.linketinder.view

import com.linketinder.model.Competencia
import com.linketinder.model.Empresa
import com.linketinder.model.Endereco
import com.linketinder.service.EmpresaService
import com.linketinder.util.MyUtil

class EmpresaView {
    static void listarEmpresas(EmpresaService service) {
        List<Empresa> empresas = service.listarEmpresas()
        println "Empresas: "
        empresas.each {empresa -> println """
            |-----------------------------------------------
            |ID: $empresa.id
            |Nome: $empresa.nome
            |E-mail corporativo: $empresa.email
            |CNPJ: $empresa.cnpj
            |País: $empresa.endereco.pais
            |Estado: $empresa.endereco.estado
            |Descrição: $empresa.descricao
            |Tem interesse nas competências: $empresa.competencias
            """.stripMargin()
        }
    }

    static void adicionarEmpresa(EmpresaService service, Scanner sc) {
        println "Criar nova Empresa"
        println "---------------------------"
        println "ATENÇÃO: esta é uma versão inicial do programa em que o cadastro de novas empresas está livremente habilitado"
        println "Na versão final a adição de empresas só será possível através de um formulário ou feito por um administrador do sistema"
        println "---------------------------"
        String nome = MyUtil.obterString("Digite o nome da empresa", sc)
        String email = MyUtil.obterString("Digite o email corporativo da empresa:", sc)
        String descricao = MyUtil.obterString("Digite a descrição da empresa", sc)
        String cnpj = MyUtil.obterString("Digite o cnpj da empresa", sc)
        String pais = MyUtil.obterString("Digite o país da empresa", sc)
        String estado = MyUtil.obterString("Digite o estado da empresa", sc)
        String cep = MyUtil.obterString("Digite o cep da empresa", sc)

        List<Competencia> competencias = []
        println "Digite as competências desejadas pela empresa, uma de cada vez"

        while (true) {
            println "Para parar de adicionar competências digite 'sair'"
            String competencia = MyUtil.obterString("Digite uma competência", sc)
            if (competencia == "sair") {
                break
            }
            competencias << new Competencia(competencia)
        }

        Empresa empresa = new Empresa(
                nome: nome,
                email: email,
                descricao: descricao,
                cnpj: cnpj,
                endereco: new Endereco(
                        pais: pais,
                        estado: estado,
                        cep: cep
                ),
                competencias: competencias
        )

        service.adicionarEmpresa(empresa);
    }
}
