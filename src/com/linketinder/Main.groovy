package com.linketinder

import com.linketinder.model.Candidato
import com.linketinder.model.Competencia
import com.linketinder.model.Endereco
import com.linketinder.repository.CandidatoRepository
import com.linketinder.repository.EmpresaRepository
import com.linketinder.service.CandidatoService
import com.linketinder.service.EmpresaService

static void main(String[] args) {
    Map services = criarBeans()
    CandidatoService candidatoService = services.candidatoService
    EmpresaService empresaService = services.empresaService

    popular(candidatoService, empresaService)

    List<Candidato> candidatos = candidatoService.listarCandidatos()
    println candidatos
}

static Map criarBeans() {
    CandidatoRepository candidatoRepository = new CandidatoRepository()
    CandidatoService candidatoService = new CandidatoService(candidatoRepository)
    EmpresaRepository empresaRepository = new EmpresaRepository()
    EmpresaService empresaService = new EmpresaService(empresaRepository)

    [
            candidatoService: candidatoService,
            empresaService: empresaService
    ]

}

static void popular(CandidatoService candidatoService, EmpresaService empresaService) {
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
            competencias: [new Competencia("Java"), new Competencia("Typescript")]
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
            competencias: [new Competencia("Groovy"), new Competencia("Python")]
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

}