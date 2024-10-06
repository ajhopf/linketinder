package com.linketinder

import com.linketinder.model.Candidato
import com.linketinder.model.Competencia
import com.linketinder.model.Endereco
import com.linketinder.model.dtos.CandidatoDTO
import com.linketinder.model.enums.Afinidade
import com.linketinder.repository.CandidatoRepository
import com.linketinder.repository.CompetenciaRepository
import com.linketinder.repository.EmpresaRepository
import com.linketinder.repository.EnderecoRepository
import com.linketinder.service.CandidatoService
import com.linketinder.service.CompetenciaService
import com.linketinder.service.EmpresaService
import com.linketinder.service.EnderecoService
import com.linketinder.util.IniciarDB
import com.linketinder.util.SqlFactory
import com.linketinder.view.MenuInicial
import groovy.sql.Sql

import java.time.LocalDate
import java.time.format.DateTimeFormatter

static void main(String[] args) {
//    Map services = criarBeans()
//    IniciarDB.iniciar(services.candidatoService, services.empresaService)
//
//    MenuInicial.iniciar(services.empresaService, services.candidatoService)

    try {
        Sql sql = SqlFactory.newInstance()

        CandidatoRepository candidatoRepository = new CandidatoRepository(sql)
        EnderecoRepository enderecoRepository = new EnderecoRepository(sql)
        CompetenciaRepository competenciaRepository = new CompetenciaRepository(sql)


        EnderecoService enderecoService = new EnderecoService(enderecoRepository)
        CompetenciaService competenciaService = new CompetenciaService(competenciaRepository)
        CandidatoService candidatoService = new CandidatoService(candidatoRepository, enderecoService, competenciaService)

        Candidato candidato = new Candidato()

        candidato.nome = 'André'
        candidato.sobrenome = 'Hopf'
        candidato.descricao = 'Developer'
        candidato.senha = 'umaSenhaTops'
        candidato.email = 'andre.hopf@hotmail.com'
        candidato.cpf = '022.567.470-00'
        candidato.dataNascimento = LocalDate.parse("14/03/1992", DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        candidato.competencias = [new Competencia('Java', 1, Afinidade.fromValor(3))]
        candidato.endereco = new Endereco(cep: '88064-074', pais: "Brasil", cidade: "Florianópolis", estado: "Santa Catarina")
        candidato.telefone = "(48) 99903-0959"

        candidatoService.adicionarCandidato(candidato)

        List<Candidato> candidatos = candidatoService.listarCandidatos()

        println candidatos

    } catch (Exception e) {
        println e.getStackTrace()
        println e
    }

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

