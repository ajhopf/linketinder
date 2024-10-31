package linketinder.view

import linketinder.controller.CandidatoController

import linketinder.exceptions.CandidatoNotFoundException
import linketinder.exceptions.CompetenciaNotFoundException

import linketinder.model.Candidato
import linketinder.model.Competencia
import linketinder.model.Endereco

import linketinder.util.ViewHelpers

import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CandidatoView {
    static Candidato obterInformacoesDeCandidato(Scanner sc) {
        String sobrenome = ViewHelpers.obterString('Digite o sobrenome do candidato', sc)
        Map infosBasicas = ViewHelpers.obterInfosBasicas(sc, false)
        String telefone = obterTelefone(sc)
        String cpf = obterCpf(sc)
        LocalDate dataDeNascimento = obterDataDeNascimento(sc)
        Endereco endereco = ViewHelpers.obterEndereco(sc, false)
        List<Competencia> competencias = ViewHelpers.obterCompetencias(sc, false)

        Candidato candidato = new Candidato(
                nome: infosBasicas.nome,
                sobrenome: sobrenome,
                email: infosBasicas.email,
                senha: infosBasicas.senha,
                telefone: telefone,
                descricao: infosBasicas.descricao,
                dataNascimento: dataDeNascimento,
                cpf: cpf,
                endereco: endereco,
                competencias: competencias
        )

        return candidato
    }

    static String obterCpf(Scanner sc) {
        String cpf = ''

        boolean cpfInvalido = true

        while(cpfInvalido) {
            cpf = ViewHelpers.obterString("Digite o cpf no formato 000.000.000-00", sc)
            if (cpf ==~ /\d{3}\.\d{3}\.\d{3}-\d{2}/) {
                cpfInvalido = false
            } else {
                println 'Cpf informado está em um formato inválido'
            }
        }

        return cpf
    }

    static LocalDate obterDataDeNascimento(Scanner sc) {
        LocalDate dataDeNascimento = null
        boolean dataInvalida = true

        while (dataInvalida) {
            String dataString = ViewHelpers.obterString("Digite a data de nascimento no formato dd/MM/yyyy", sc)

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy")
            simpleDateFormat.setLenient(false)
            try {
                simpleDateFormat.parse( dataString )
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                dataDeNascimento = LocalDate.parse(dataString, formatter)
                dataInvalida = false
            } catch (ParseException e) {
                println 'Formato da data não é válido'
            }
        }

        return dataDeNascimento
    }

    static String obterTelefone(Scanner sc) {
        String telefone = ''

        boolean telefoneInvalido = true

        while(telefoneInvalido) {
            telefone = ViewHelpers.obterString("Digite o telefone no formato (DD) 99999-9999", sc)
            if (telefone ==~ /^\(\d{2}\)\s\d{5}-\d{4}$/) {
                telefoneInvalido = false
            } else {
                println 'Telefone informado está em um formato inválido'
            }
        }

        return telefone
    }

    static void listarCandidatos(CandidatoController candidatoController) {
        List<Candidato> candidatos = candidatoController.listarCandidatos()
        println "Candidatos: "
        candidatos.each {candidato -> ViewHelpers.printInfosDePessoa(candidato)}
    }

    static void adicionarCandidato(CandidatoController candidatoController, Scanner sc) {
        println "Criar novo Cadastro de Candidato"
        ViewHelpers.printInfosIniciais()
        Candidato candidato = obterInformacoesDeCandidato(sc)

        try {
            Integer id = candidatoController.adicionarCandidato(candidato)
            println "Candidato adicionado com sucesso. Id: $id"
        } catch(CompetenciaNotFoundException e) {
            println 'Não foi possível adicionar o candidato. Ao menos uma das competências relacionadas ao candidato não existem no sistema.'
        } catch(Exception e) {
            println 'Candidato não adicionado'
            println e.getMessage()
        }

    }

    static void editarCandidato(CandidatoController candidatoController, Scanner sc) {
        println "Editar Candidato"

        try {
            Integer candidatoId = ViewHelpers.getIntInput(0, 1000, "Digite o id do candidato para editar", sc)
            Candidato candidato = candidatoController.obterCandidatoPeloId(candidatoId)

            println "Candidato selecionado: $candidato.nome"
            println "Digite as novas informações para o candidato"

            Candidato candidatoAtualizado = obterInformacoesDeCandidato(sc)
            candidatoAtualizado.id = candidatoId

            candidatoController.editarCandidato(candidatoAtualizado)
        } catch (CandidatoNotFoundException e) {
            println e.getMessage()
        } catch(CompetenciaNotFoundException e) {
            println 'Não foi possível adicionar o candidato. Ao menos uma das competências relacionadas ao candidato não existem no sistema.'
        }
    }

    static void deletarCandidato(CandidatoController candidatoController, Scanner sc) {
        println "Deletar Candidato"

        Integer idDoCandidato = ViewHelpers.getIntInput(0, 5000, 'Digite o id do candidato', sc)
        try {
            candidatoController.deletarCandidato(idDoCandidato)
            println 'Candidato deletado com sucesso'

        } catch (CandidatoNotFoundException e) {
            println e.getMessage()
        }
    }
}
