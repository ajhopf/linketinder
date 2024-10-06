package com.linketinder.view

import com.linketinder.model.Candidato
import com.linketinder.model.Competencia
import com.linketinder.model.Endereco
import com.linketinder.service.CandidatoService
import com.linketinder.util.InputHelpers

import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CandidatoView {
    static void adicionarCandidato(CandidatoService service, Scanner sc) {
        println "Criar novo Cadastro de Candidato"
        InputHelpers.printInfosIniciais()
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

        service.adicionarCandidato(candidato)
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

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            simpleDateFormat.setLenient(false);
            try {
                simpleDateFormat.parse( dataString )
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                dataDeNascimento = LocalDate.parse(dataString, formatter);
                dataInvalida = false;
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
}
