package com.linketinder.view

import com.linketinder.model.Candidato
import com.linketinder.model.Competencia
import com.linketinder.model.Empresa
import com.linketinder.model.Endereco
import com.linketinder.model.enums.Afinidade
import com.linketinder.service.CandidatoService
import com.linketinder.service.EmpresaService
import com.linketinder.util.MyUtil

class CadastroView {

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

        boolean senhaInvalida = true
        String senha = null
        while (senhaInvalida) {
            senha = MyUtil.obterString('Digite a senha - deve ter no minimo 6 caracteres', sc)

            if (senha.size() > 6) {
                senhaInvalida = false
            } else {
                println 'Senha deve ter no minimo 6 caracteres'
            }
        }

        String descricao = MyUtil.obterString("Digite a descrição ${isEmpresa ? "da empresa" : "do candidato"}:", sc)

        [
                nome: nome,
                email: email,
                descricao: descricao,
                senha: senha
        ]
    }

    static Endereco obterEndereco(Scanner sc, isEmpresa = true) {
        String finalDaString = isEmpresa ? "da empresa" : "do candidato"

        String pais = MyUtil.obterString("Digite o país $finalDaString:", sc)
        String estado = MyUtil.obterString("Digite o estado $finalDaString:", sc)
        String cidade = MyUtil.obterString("Digite a cidade $finalDaString", sc)

        String cep = null
        boolean cepInvalido = true

        while(cepInvalido) {
            cep = MyUtil.obterString("Digite o cep $finalDaString no formato 99999-999:", sc)

            if (cep ==~ /^\d{5}-\d{3}$/) {
                cepInvalido = false
            } else {
                println 'Cep informado está em um formato inválido'
            }
        }


        new Endereco(pais: pais, estado: estado, cep: cep, cidade: cidade)
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
            Double anosExperiencia = MyUtil.getIntInput(0, 50, 'Anos de experiencia', sc)
            Integer afinidade = MyUtil.getIntInput(1, 5, 'Digite o nível de afiidade entre 1 e 5', sc)
            competencias << new Competencia(competencia, anosExperiencia, Afinidade.fromValor(afinidade))
        }

        competencias
    }
}
