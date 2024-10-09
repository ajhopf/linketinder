package linketinder

import linketinder.repository.CandidatoRepository
import linketinder.repository.CompetenciaRepository
import linketinder.repository.EmpresaRepository
import linketinder.repository.EnderecoRepository
import linketinder.repository.VagaRepository
import linketinder.service.CandidatoService
import linketinder.service.CompetenciaService
import linketinder.service.EmpresaService
import linketinder.service.EnderecoService
import linketinder.service.VagaService
import linketinder.util.SqlFactory
import linketinder.view.MenuInicial
import groovy.sql.Sql

static void main(String[] args) {
    try {
        Sql sql = SqlFactory.newInstance()

        EmpresaRepository empresaRepository = new EmpresaRepository(sql)
        CandidatoRepository candidatoRepository = new CandidatoRepository(sql)
        EnderecoRepository enderecoRepository = new EnderecoRepository(sql)
        CompetenciaRepository competenciaRepository = new CompetenciaRepository(sql)
        VagaRepository vagaRepository = new VagaRepository(sql)

        EnderecoService enderecoService = new EnderecoService(enderecoRepository)
        CompetenciaService competenciaService = new CompetenciaService(competenciaRepository)
        EmpresaService empresaService = new EmpresaService(empresaRepository, enderecoService)
        CandidatoService candidatoService = new CandidatoService(candidatoRepository, enderecoService, competenciaService)
        VagaService vagaService = new VagaService(vagaRepository, competenciaService, enderecoService)

        MenuInicial.iniciar(empresaService, candidatoService, competenciaService, vagaService)
    } catch (Exception e) {
        println e.getStackTrace()
        println e
    }

}
