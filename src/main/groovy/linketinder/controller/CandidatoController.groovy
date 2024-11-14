package linketinder.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import linketinder.exceptions.CandidatoNotFoundException
import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.exceptions.RepositoryAccessException
import linketinder.model.Candidato
import linketinder.model.Competencia
import linketinder.model.dtos.CandidatoControllerResponseDTO
import linketinder.model.mappers.CandidatoMapper
import linketinder.service.CandidatoService

import linketinder.util.HttpHelper

import javax.servlet.ServletContext
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/candidatos/*")
class CandidatoController extends HttpServlet {
    private CandidatoService candidatoService
    private CompetenciaController competenciaController

    CandidatoController(){}

    CandidatoController(CandidatoService candidatoService, CompetenciaController competenciaController) {
        this.candidatoService = candidatoService
        this.competenciaController = competenciaController
    }

    @Override
    void init() {
        ServletContext context = getServletContext()
        candidatoService = (CandidatoService) context.getAttribute("candidatoService")
        competenciaController = (CompetenciaController) context.getAttribute("competenciaController")
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getPathInfo()

        if (path == null || path == "/") {
            this.listarCandidatos(response)
        } else {
            this.obterCandidatoPeloId(path, response)
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper()
        mapper.registerModule(new JavaTimeModule())

        try {
            Candidato candidato = mapper.readValue(request.getReader(), Candidato.class)

            Integer id = this.adicionarCandidato(candidato)
            response.setStatus(HttpServletResponse.SC_CREATED)
            response.getWriter().write("""
            |{
            |  "message": "Candidato criado com sucesso",
            |  "uri": "localhost:8080/linketinder/candidatos/$id
            |}
            """.stripMargin())
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            response.getWriter().write("{\"message\": \"Formato JSON inválido\"}")
        } catch (CompetenciaNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            response.getWriter().write("""
                |{        
                |   "message": "Competencia nao encontrada",
                |   "competenciasHref": "localhost:8080/linketinder-1.0-SNAPSHOT/competencias" 
                |}
            """)
        } catch (RepositoryAccessException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            response.getWriter().write("""
                |{        
                |    "message": "Erro ao cadastrar o candidato",
                |    "cause": ${e.getMessage()}
                |}
            """.stripMargin())
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper()
        mapper.registerModule(new JavaTimeModule())
        try {
            Candidato candidatoAtualizado = mapper.readValue(request.getReader(), Candidato.class)

            this.editarCandidato(candidatoAtualizado)
            response.setStatus(HttpServletResponse.SC_CREATED)
            response.getWriter().write("""
            |{
            |  "message": "Candidato atualizado com sucesso",
            |  "uri": "localhost:8080/linketinder/candidatos/$candidatoAtualizado.id
            |}
            """.stripMargin())
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            response.getWriter().write("{\"message\": \"Formato JSON inválido\"}")
        } catch (CompetenciaNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            response.getWriter().write("""
                |{        
                |   "message": "Competencia nao encontrada",
                |   "competenciasHref": "localhost:8080/linketinder-1.0-SNAPSHOT/competencias" 
                |}
            """.stripMargin())
        } catch (CandidatoNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            response.getWriter().write("""
                |{        
                |   "message": "Candidato nao encontrado para fazer a atualizacao",
                |   "competenciasHref": "localhost:8080/linketinder-1.0-SNAPSHOT/candidatos" 
                |}
            """.stripMargin())
        } catch (RepositoryAccessException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            response.getWriter().write("""
                |{        
                |    "message": "Erro ao editar o candidato"
                |    "cause": ${e.getMessage()}
                |}
            """.stripMargin())
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getPathInfo()

        if (path == null || path == "/") {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            response.getWriter().write("""
                |{        
                |    "message": "Erro ao deletar o candidato, informe o id na requisicao"
                |}
            """.stripMargin())
        } else {
            try {
                int id = Integer.parseInt(path.split("/")[1])
                this.deletarCandidato(id)

                response.setStatus(HttpServletResponse.SC_NO_CONTENT)
            } catch (CandidatoNotFoundException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND)
                response.getWriter().write("""
                |{        
                |    "message": "Nao foi possivel encontrar um candidato com o id informado"
                |}
                """.stripMargin())
            }


        }
    }

    void listarCandidatos(HttpServletResponse response) {
        List<Candidato> candidatos = this.candidatoService.listarCandidatos()
        List<CandidatoControllerResponseDTO> candidatoControllerResponseDTOS = CandidatoMapper.toControllerResponseDTOList(candidatos)

        HttpHelper.writeResponse(
                response,
                HttpServletResponse.SC_OK,
                candidatoControllerResponseDTOS
        )
    }

    void obterCandidatoPeloId(String pathInfo, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(pathInfo.split("/")[1])
            Candidato candidato = this.candidatoService.obterCandidatoPeloId(id)

            CandidatoControllerResponseDTO candidatoControllerResponseDTO = CandidatoMapper.toControllerResponseDTO(candidato)

            HttpHelper.writeResponse(
                    response,
                    HttpServletResponse.SC_OK,
                    candidatoControllerResponseDTO
            )
        } catch (NumberFormatException e) {
            HttpHelper.writeResponse(
                    response,
                    HttpServletResponse.SC_BAD_REQUEST,
                    null,
                    "Id inválido"
            )
        } catch (CandidatoNotFoundException e) {
            HttpHelper.writeResponse(
                    response,
                    HttpServletResponse.SC_NOT_FOUND,
                    null,
                    "Candidato não encontrado com o id informado"
            )
        }
    }

    void verificarSeCompetenciasExistem(List<Competencia> competencias) throws CompetenciaNotFoundException {
        competencias.each { competencia ->
            competenciaController.obterIdDeCompetencia(competencia.competencia)
        }
    }


    Candidato obterCandidatoPeloId(Integer id) throws CandidatoNotFoundException, RepositoryAccessException {
        return candidatoService.obterCandidatoPeloId(id)
    }


    List<Candidato> listarCandidatos() {
        return candidatoService.listarCandidatos()
    }


    Integer adicionarCandidato(Candidato candidato) throws CompetenciaNotFoundException, RepositoryAccessException {
        this.verificarSeCompetenciasExistem(candidato.competencias)

        return candidatoService.adicionarCandidato(candidato)
    }


    void editarCandidato(Candidato candidatoAtualizado) throws CandidatoNotFoundException, CompetenciaNotFoundException, RepositoryAccessException {
        this.verificarSeCompetenciasExistem(candidatoAtualizado.competencias)
        this.obterCandidatoPeloId(candidatoAtualizado.id)

        candidatoService.updateCandidato(candidatoAtualizado)
    }


    void deletarCandidato(Integer idDoCandidato) throws CompetenciaNotFoundException, RepositoryAccessException  {
        candidatoService.deletarCandidatoPeloId(idDoCandidato)
    }
}
