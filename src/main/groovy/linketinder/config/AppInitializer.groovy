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

        Sql sql = LinketinderConfig.getPostgreConnection()
        Map services = LinketinderConfig.getServices(sql)

        CompetenciaController competenciaController = new CompetenciaController(services.competenciaService)

        ServletContext context = getServletContext()
        context.setAttribute("candidatoService", services.candidatoService)
        context.setAttribute("empresaService", services.empresaService)
        context.setAttribute("vagaService", services.vagaService)
        context.setAttribute("competenciaService", services.competenciaService)
        context.setAttribute("competenciaController", competenciaController)
    }
}
