package linketinder.config

import groovy.sql.Sql
import linketinder.controller.CandidatoController
import linketinder.controller.CompetenciaController
import linketinder.model.Competencia
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
import linketinder.util.sql.PostgreSqlConnection
import linketinder.util.sql.SqlFactory

import javax.servlet.ServletContext
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet

@WebServlet(name = "AppInitializer", loadOnStartup = 1) // loadOnStartup = 1 garante que ele seja carregado no início
class AppInitializer extends HttpServlet {
    @Override
    public void init() {
        println "AppInitializer carregado com sucesso!"
        PostgreSqlConnection postgreSqlConnection = new PostgreSqlConnection()
        Sql sql = SqlFactory.newInstance(postgreSqlConnection)

        EmpresaRepository empresaRepository = EmpresaRepository.getInstance(sql)
        CandidatoRepository candidatoRepository = CandidatoRepository.getInstance(sql)
        EnderecoRepository enderecoRepository = EnderecoRepository.getInstance(sql)
        CompetenciaRepository competenciaRepository = CompetenciaRepository.getInstance(sql)
        VagaRepository vagaRepository = VagaRepository.getInstance(sql)

        EnderecoService enderecoService = new EnderecoService(enderecoRepository)
        CompetenciaService competenciaService = new CompetenciaService(competenciaRepository)
        EmpresaService empresaService = new EmpresaService(empresaRepository, enderecoService)
        CandidatoService candidatoService = new CandidatoService(candidatoRepository, enderecoService, competenciaService)
        VagaService vagaService = new VagaService(vagaRepository, competenciaService, enderecoService)

        CompetenciaController competenciaController = new CompetenciaController(competenciaService)

        ServletContext context = getServletContext()
        context.setAttribute("candidatoService", candidatoService)
        context.setAttribute("competenciaController", competenciaController)
    }
}
