package com.linketinder

import com.linketinder.model.Candidato
import com.linketinder.model.dtos.CandidatoDTO
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

