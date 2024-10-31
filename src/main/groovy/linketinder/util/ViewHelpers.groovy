package linketinder.util

import linketinder.exceptions.OpcaoInvalidaException
import linketinder.model.Candidato
import linketinder.model.Competencia
import linketinder.model.Empresa
import linketinder.model.Endereco
import linketinder.model.Usuario
import linketinder.model.enums.Afinidade

class ViewHelpers {
    static void printInfosIniciais() {
        println "---------------------------"
        println "ATENÇÃO: esta é uma versão inicial do programa em que o cadastro de novas empresas e candidatos está livremente habilitado"
        println "Na versão final a adição de empresas e candidatos só será possível através de um formulário ou feito por um administrador do sistema"
        println "---------------------------"
    }

    static <T extends Usuario> void printInfosDePessoa(T pessoa) {
        boolean isEmpresa = pessoa.getClass() == Empresa
        boolean isCandidato = pessoa.getClass() == Candidato
        String infosEspecificas = "Não foi possível obter o cpf / cnpj deste cadastro"

        if (isEmpresa) {
            Empresa empresa = pessoa as Empresa
            infosEspecificas = "CNPJ: $empresa.cnpj"
        } else if (isCandidato) {
            Candidato candidato = pessoa as Candidato
            infosEspecificas = "CPF: $candidato.cpf\n|Data de Nascimento: $candidato.dataNascimento\n|Telefone: $candidato.telefone|Tem interesse nas competências: $candidato.competencias"
        }

        println """
            |-----------------------------------------------
            |ID: $pessoa.id
            |Nome: $pessoa.nome
            |E-mail corporativo: $pessoa.email
            |$infosEspecificas
            |País: $pessoa.endereco.pais
            |Estado: $pessoa.endereco.estado
            |Descrição: $pessoa.descricao
            
            """.stripMargin()
    }

    static String obterString(String title, Scanner sc) {
        println title
        return sc.nextLine()
    }

    static int getIntInput(int min, int max, String title, Scanner sc) {
        println title

        while (true) {
            try {
                String stringValue = sc.nextLine()

                int value = Integer.parseInt(stringValue)

                if (value < min || value > max) {
                    throw new OpcaoInvalidaException("Escolha um número entre $min e $max")
                }
                return value
            } catch (OpcaoInvalidaException e) {
                println e.getMessage()
            } catch (InputMismatchException | NumberFormatException e) {
                println "Você deve escolher utilizando um número de $min a $max"
            }
        }
    }

    static Map obterInfosBasicas(Scanner sc, isEmpresa = true) {
        String nome = obterString("Digite o nome ${isEmpresa ? "da empresa" : "do candidato"}:", sc)
        String email = obterString("Digite o email ${isEmpresa ? "corporativo da empresa" : "do candidato"}:", sc)

        boolean senhaInvalida = true
        String senha = null
        while (senhaInvalida) {
            senha = obterString('Digite a senha - deve ter no minimo 6 caracteres', sc)

            if (senha.size() >= 6) {
                senhaInvalida = false
            } else {
                println 'Senha deve ter no minimo 6 caracteres'
            }
        }

        String descricao = obterString("Digite a descrição ${isEmpresa ? "da empresa" : "do candidato"}:", sc)

        [
                nome: nome,
                email: email,
                descricao: descricao,
                senha: senha
        ]
    }

    static Endereco obterEndereco(Scanner sc, isVaga = true) {
        String finalDaString = isVaga ? "da vaga" : "do candidato"

        String pais = obterString("Digite o país $finalDaString:", sc)
        String estado = obterString("Digite o estado $finalDaString:", sc)
        String cidade = obterString("Digite a cidade $finalDaString", sc)

        String cep = null
        boolean cepInvalido = true

        while(cepInvalido) {
            cep = obterString("Digite o cep $finalDaString no formato 99999-999:", sc)

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
            String competencia = obterString("Digite uma competência", sc)
            if (competencia == "sair") {
                break
            }
            Double anosExperiencia = getIntInput(0, 50, 'Anos de experiencia', sc)
            Integer afinidade = getIntInput(1, 5, 'Digite o nível de afiidade entre 1 e 5', sc)
            competencias << new Competencia(competencia, anosExperiencia, Afinidade.fromValor(afinidade))
        }

        competencias
    }
}
