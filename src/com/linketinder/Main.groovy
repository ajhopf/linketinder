package com.linketinder

import com.linketinder.repository.CandidatoRepository
import com.linketinder.repository.CompetenciaRepository
import com.linketinder.repository.EmpresaRepository
import com.linketinder.repository.EnderecoRepository
import com.linketinder.service.CandidatoService
import com.linketinder.service.CompetenciaService
import com.linketinder.service.EmpresaService
import com.linketinder.service.EnderecoService
import com.linketinder.util.SqlFactory
import com.linketinder.view.MenuInicial
import groovy.sql.Sql

static void main(String[] args) {
    try {
        Sql sql = SqlFactory.newInstance()

        EmpresaRepository empresaRepository = new EmpresaRepository()
        CandidatoRepository candidatoRepository = new CandidatoRepository(sql)
        EnderecoRepository enderecoRepository = new EnderecoRepository(sql)
        CompetenciaRepository competenciaRepository = new CompetenciaRepository(sql)

        EnderecoService enderecoService = new EnderecoService(enderecoRepository)
        CompetenciaService competenciaService = new CompetenciaService(competenciaRepository)
        EmpresaService empresaService = new EmpresaService(empresaRepository)
        CandidatoService candidatoService = new CandidatoService(candidatoRepository, enderecoService, competenciaService)

        MenuInicial.iniciar(empresaService, candidatoService, competenciaService)
    } catch (Exception e) {
        println e.getStackTrace()
        println e
    }

}

//static Candidato criarCandidato() {
//    Candidato candidato = new Candidato()
//
//    candidato.nome = 'André'
//    candidato.sobrenome = 'Hopf'
//    candidato.descricao = 'Developer'
//    candidato.senha = 'umaSenhaTops'
//    candidato.email = 'andre.hopf@hotmail.com'
//    candidato.cpf = '022.567.470-00'
//    candidato.dataNascimento = LocalDate.parse("14/03/1992", DateTimeFormatter.ofPattern("dd/MM/yyyy"))
//    candidato.competencias = [new Competencia('Java', 1, Afinidade.fromValor(3))]
//    candidato.endereco = new Endereco(cep: '88064-074', pais: "Brasil", cidade: "Florianópolis", estado: "Santa Catarina")
//    candidato.telefone = "(48) 99903-0959"
//
//    return candidato
//}

