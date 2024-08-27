package com.linketinder.view

import com.linketinder.model.Candidato
import com.linketinder.model.Competencia
import com.linketinder.model.Empresa
import com.linketinder.model.Endereco
import com.linketinder.service.CandidatoService
import com.linketinder.service.EmpresaService
import com.linketinder.util.MyUtil

class CadastroView {
    static void adicionarCandidato(CandidatoService service, Scanner sc) {
        println "Criar novo Cadastro de Candidato"
        printInfosIniciais()
        Map infosBasicas = obterInfosBasicas(sc, false)
        String cpf = MyUtil.obterString("Digite o cpf do candidato", sc)
        Integer idade = MyUtil.getIntInput(0, 100, "Digite a idade do candidato", sc)
        Endereco endereco = obterEndereco(sc, false)
        List<Competencia> competencias = obterCompetencias(sc, false)

        Candidato candidato = new Candidato(
                nome: infosBasicas.nome,
                email: infosBasicas.email,
                descricao: infosBasicas.descricao,
                idade: idade,
                cpf: cpf,
                endereco: endereco,
                competencias: competencias
        )

        service.adicionarCandidato(candidato)
    }


    static void adicionarEmpresa(EmpresaService service, Scanner sc) {
        println "Criar nova Empresa"
        printInfosIniciais()

        Map infosBasicas = obterInfosBasicas(sc)
        String cnpj = MyUtil.obterString("Digite o cnpj da empresa", sc)
        Endereco endereco = obterEndereco(sc)
        List<Competencia> competencias = obterCompetencias(sc)

        Empresa empresa = new Empresa(
                nome: infosBasicas.nome,
                email: infosBasicas.email,
                descricao: infosBasicas.descricao,
                cnpj: cnpj,
                endereco: endereco,
                competencias: competencias
        )

        service.adicionarEmpresa(empresa);
    }

    static void printInfosIniciais() {
        println "---------------------------"
        println "ATENÇÃO: esta é uma versão inicial do programa em que o cadastro de novas empresas e candidatos está livremente habilitado"
        println "Na versão final a adição de empresas e candidatos só será possível através de um formulário ou feito por um administrador do sistema"
        println "---------------------------"
    }

    static Map obterInfosBasicas(Scanner sc, isEmpresa = true) {
        String nome = MyUtil.obterString("Digite o nome ${isEmpresa ? "da empresa" : "do candidato"}:", sc)
        String email = MyUtil.obterString("Digite o email ${isEmpresa ? "corporativo da empresa" : "do candidato"}:", sc)
        String descricao = MyUtil.obterString("Digite a descrição ${isEmpresa ? "da empresa" : "do candidato"}:", sc)

        [
                nome: nome,
                email: email,
                descricao: descricao,
        ]
    }

    static Endereco obterEndereco(Scanner sc, isEmpresa = true) {
        String finalDaString = isEmpresa ? "da empresa" : "do candidato"

        String pais = MyUtil.obterString("Digite o país $finalDaString:", sc)
        String estado = MyUtil.obterString("Digite o estado $finalDaString:", sc)
        String cep = MyUtil.obterString("Digite o cep da $finalDaString:", sc)

        new Endereco(pais: pais, estado: estado, cep: cep)
    }

    static List<Competencia> obterCompetencias(Scanner sc, isEmpresa = true) {
        List<Competencia> competencias = []

        println "Digite as competências ${isEmpresa ? "desejadas pela empresa" : "do candidato"}, uma de cada vez"

        while (true) {
            println "Para parar de adicionar competências digite 'sair'"
            String competencia = MyUtil.obterString("Digite uma competência", sc)
            if (competencia == "sair") {
                break
            }
            competencias << new Competencia(competencia)
        }

        competencias
    }
}
