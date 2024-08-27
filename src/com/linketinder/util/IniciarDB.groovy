package com.linketinder.util

import com.linketinder.model.Candidato
import com.linketinder.model.Competencia
import com.linketinder.model.Empresa
import com.linketinder.model.Endereco
import com.linketinder.service.CandidatoService
import com.linketinder.service.EmpresaService

class IniciarDB {
    static void iniciar(CandidatoService candidatoService, EmpresaService empresaService) {
        criarCandidatos(candidatoService)
        criarEmpresas(empresaService)
    }

    static List<Empresa> criarEmpresas(EmpresaService empresaService) {
        Empresa empresa1 = new Empresa(
                nome: "Arroz-Gostoso",
                email: "arroz.gostoso@arroz.com.br",
                descricao: "Tecnologia aliada a tradição",
                cnpj: "123.123.123-33",
                endereco: new Endereco(
                        pais: "Brasil",
                        estado: "Rio Grande do Sul",
                        cep: "91929-322"
                ),
                competencias: [new Competencia("Java"), new Competencia("Engenheiro de Produção")]
        )

        Empresa empresa2 = new Empresa(
                nome: "Império do Boliche",
                email: "rei.boliche@imperio.com.br",
                descricao: "Alegria e boliche",
                cnpj: "323.123.123-33",
                endereco: new Endereco(
                        pais: "Brasil",
                        estado: "Paraná",
                        cep: "74420-322"
                ),
                competencias: [new Competencia("TypeScript"), new Competencia("React")]
        )

        Empresa empresa3 = new Empresa(
                nome: "Azeites do Magrão",
                email: "magrao@azeites.com.br",
                descricao: "Maior produtor de azeite da América do Sul",
                cnpj: "123.123.123-53",
                endereco: new Endereco(
                        pais: "Brasil",
                        estado: "Goiás",
                        cep: "74420-322"
                ),
                competencias: [new Competencia("Groovy")]
        )

        Empresa empresa4 = new Empresa(
                nome: "Azeites do Magrão",
                email: "magrao@azeites.com.br",
                descricao: "Maior produtor de azeite da América do Sul",
                cnpj: "123.353.123-33",
                endereco: new Endereco(
                        pais: "Brasil",
                        estado: "Goiás",
                        cep: "74420-322"
                ),
                competencias: [new Competencia("Groovy")]
        )

        Empresa empresa5 = new Empresa(
                nome: "Soluções Empresarias Azurbal",
                email: "azurbal@soluções.com.br",
                descricao: "Seu problema, nossa solução",
                cnpj: "123.123.786-33",
                endereco: new Endereco(
                        pais: "Brasil",
                        estado: "Rio de Janeiro",
                        cep: "97820-322"
                ),
                competencias: [new Competencia("Groovy"), new Competencia("Angular"), new Competencia("SQL")]
        )

        List<Empresa> empresas = [empresa1, empresa2, empresa3, empresa4, empresa5]
        empresas.each {empresaService.adicionarEmpresa(it)}

        empresas
    }

    static List<Candidato> criarCandidatos(CandidatoService candidatoService) {
        Candidato candidato1 = new Candidato(
                nome: "André Hopf",
                idade: 32,
                email: "andre.hopf@hotmail.com",
                cpf: "022.567.470-00",
                descricao: "Full-stack developer",
                endereco: new Endereco(
                        pais: "Brasil",
                        estado: "Santa Catarina",
                        cep: "88063-074"
                ),
                competencias: [new Competencia("Java"), new Competencia("Typescript"), new Competencia("Angular")]
        )

        Candidato candidato2 = new Candidato(
                nome: "Rachel Lunardi",
                idade: 31,
                email: "rachel@hotmail.com",
                cpf: "123.567.370-00",
                descricao: "Engenheira de Produção",
                endereco: new Endereco(
                        pais: "Brasil",
                        estado: "Santa Catarina",
                        cep: "88063-074"
                ),
                competencias: [new Competencia("QI Builder")]
        )

        Candidato candidato3 = new Candidato(
                nome: "Luiz Fernandes",
                idade: 53,
                email: "fernandes@gmail.com",
                cpf: "033.543.330-10",
                descricao: "Back-end developer",
                endereco: new Endereco(
                        pais: "Brasil",
                        estado: "Rio Grande do Sul",
                        cep: "91920-074"
                ),
                competencias: [new Competencia("Groovy"), new Competencia("SQL"), new Competencia("Python")]
        )

        Candidato candidato4 = new Candidato(
                nome: "Luiz Fernandes",
                idade: 53,
                email: "fernandes@gmail.com",
                cpf: "033.543.330-10",
                descricao: "Back-end developer",
                endereco: new Endereco(
                        pais: "Brasil",
                        estado: "Bahia",
                        cep: "91920-074"
                ),
                competencias: [new Competencia("Groovy"), new Competencia("Python")]
        )

        Candidato candidato5 = new Candidato(
                nome: "Ana Oliveira",
                idade: 23,
                email: "ana.oliva@gmail.com",
                cpf: "133.555.670-10",
                descricao: "Front-end developer",
                endereco: new Endereco(
                        pais: "Brasil",
                        estado: "Goiás",
                        cep: "91920-074"
                ),
                competencias: [new Competencia("Groovy"), new Competencia("Python")]
        )

        List<Candidato> candidatos = [candidato1, candidato2, candidato3, candidato4, candidato5]
        candidatos.each {candidatoService.adicionarCandidato(it)}

        candidatos
    }
}
