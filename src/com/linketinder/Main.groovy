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

        EmpresaRepository empresaRepository = new EmpresaRepository(sql)
        CandidatoRepository candidatoRepository = new CandidatoRepository(sql)
        EnderecoRepository enderecoRepository = new EnderecoRepository(sql)
        CompetenciaRepository competenciaRepository = new CompetenciaRepository(sql)

        EnderecoService enderecoService = new EnderecoService(enderecoRepository)
        CompetenciaService competenciaService = new CompetenciaService(competenciaRepository)
        EmpresaService empresaService = new EmpresaService(empresaRepository, enderecoService)
        CandidatoService candidatoService = new CandidatoService(candidatoRepository, enderecoService, competenciaService)

        MenuInicial.iniciar(empresaService, candidatoService, competenciaService)
    } catch (Exception e) {
        println e.getStackTrace()
        println e
    }

}
