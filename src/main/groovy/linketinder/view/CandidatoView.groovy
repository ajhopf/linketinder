package linketinder.view

import linketinder.exceptions.CandidatoNotFoundException
import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.model.Candidato
import linketinder.model.Competencia
import linketinder.model.Endereco
import linketinder.service.CandidatoService
import linketinder.service.CompetenciaService
import linketinder.util.InputHelpers

import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CandidatoView {
    static Candidato obterInformacoesDeCandidato(Scanner sc) {
        String sobrenome = InputHelpers.obterString('Digite o sobrenome do candidato', sc)
        Map infosBasicas = InputHelpers.obterInfosBasicas(sc, false)
        String telefone = obterTelefone(sc)
        String cpf = obterCpf(sc)
        LocalDate dataDeNascimento = obterDataDeNascimento(sc)
        Endereco endereco = InputHelpers.obterEndereco(sc, false)
        List<Competencia> competencias = InputHelpers.obterCompetencias(sc, false)

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
            cpf = InputHelpers.obterString("Digite o cpf no formato 000.000.000-00", sc)
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
            String dataString = InputHelpers.obterString("Digite a data de nascimento no formato dd/MM/yyyy", sc)

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
            telefone = InputHelpers.obterString("Digite o telefone no formato (DD) 99999-9999", sc)
            if (telefone ==~ /^\(\d{2}\)\s\d{5}-\d{4}$/) {
                telefoneInvalido = false
            } else {
                println 'Telefone informado está em um formato inválido'
            }
        }

        return telefone
    }

    static void adicionarCandidato(CandidatoService service, CompetenciaService competenciaService, Scanner sc) {
        println "Criar novo Cadastro de Candidato"
        InputHelpers.printInfosIniciais()
        Candidato candidato = obterInformacoesDeCandidato(sc)

        try {
            candidato.competencias.each {competencia ->
                competenciaService.obterIdDeCompetencia(competencia.competencia)
            }

            Integer id = service.adicionarCandidato(candidato)
            println "Candidato adicionado com sucesso. Id: $id"
        } catch(CompetenciaNotFoundException e) {
            println 'Não foi possível adicionar competencias ao candidato. Ao menos uma das competências relacionadas ao candidato não existem no sistema.'
        } catch(Exception e) {
            println 'Candidato não adicionado'
            println e.getMessage()
        }

    }

    static void editarCandidato(CandidatoService candidatoService, CompetenciaService competenciaService, Scanner sc) {
        println "Editar Candidato"

        try {
            Integer candidatoId = InputHelpers.getIntInput(0, 1000, "Digite o id do candidato para editar", sc)
            Candidato candidato = candidatoService.obterCandidatoPeloId(candidatoId)

            println "Candidato selecionado: $candidato.nome"
            println "Digite as novas informações para o candidato"

            Candidato candidatoAtualizado = obterInformacoesDeCandidato(sc)
            candidatoAtualizado.id = candidatoId

            candidato.competencias.each {competencia ->
                competenciaService.obterIdDeCompetencia(competencia.competencia)
            }

            candidatoService.updateCandidato(candidatoAtualizado)
        } catch (CandidatoNotFoundException e) {
            println e.getMessage()
        } catch(CompetenciaNotFoundException e) {
            println 'Não foi possível adicionar o candidato. Ao menos uma das competências relacionadas ao candidato não existem no sistema.'
        }
    }

    static void deletarCandidato(CandidatoService service, Scanner sc) {
        println "Deletar Candidato"

        Integer idDoCandidato = InputHelpers.getIntInput(0, 5000, 'Digite o id do candidato', sc)
        try {
            service.deletarCandidatoPeloId(idDoCandidato)
            println 'Candidato deletado com sucesso'

        } catch (CandidatoNotFoundException e) {
            println e.getMessage()
        }
    }
}
