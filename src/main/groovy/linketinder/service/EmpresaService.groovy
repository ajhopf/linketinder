package linketinder.service


import linketinder.exceptions.EmpresaNotFoundException
import linketinder.exceptions.RepositoryAccessException
import linketinder.model.Empresa
import linketinder.model.Endereco
import linketinder.model.dtos.EmpresaDTO
import linketinder.model.mappers.EmpresaMapper
import linketinder.repository.EmpresaRepository


import java.sql.SQLException

class EmpresaService {
    EmpresaRepository repository
    EnderecoService enderecoService

    EmpresaService (EmpresaRepository repository, EnderecoService enderecoService) {
        this.repository = repository
        this.enderecoService = enderecoService
    }

    Empresa obterEmpresaPeloId(Integer id) throws RepositoryAccessException, EmpresaNotFoundException {
        try {
            EmpresaDTO empresaDTO = repository.obterEmpresaPeloId(id)
            Endereco endereco = enderecoService.obterEnderecoDoUsuario(id)

            return EmpresaMapper.toEntity(empresaDTO, endereco)
        } catch (SQLException e){
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        }
    }

    List<Empresa> listarEmpresas() throws RepositoryAccessException{
        List<Empresa> empresas = []

        try {
            List<EmpresaDTO> empresaDTOList = repository.listarEmpresas()

            for (empresaDTO in empresaDTOList) {
                Endereco endereco = enderecoService.obterEnderecoDoUsuario(empresaDTO.id)
                empresas << EmpresaMapper.toEntity(empresaDTO, endereco)
            }
            return empresas

        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        }

    }

    Integer adicionarEmpresa(Empresa empresa) throws RepositoryAccessException {
        try {
            EmpresaDTO empresaDTO = EmpresaMapper.toDTO(empresa)

            Integer empresaId = repository.adicionarEmpresa(empresaDTO)

            enderecoService.adicionarEnderecoParaUsuario(empresa.endereco, empresaId)

            return empresaId
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        }
    }


    void editarEmpresa(Empresa empresa) throws RepositoryAccessException, EmpresaNotFoundException {
        try {
            EmpresaDTO empresaDTO = EmpresaMapper.toDTO(empresa)

            repository.updateEmpresa(empresaDTO)
            enderecoService.updateEnderecoDoUsuario(empresa.endereco, empresaDTO.id)
        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        }
    }

    void deleteEmpresa(Integer id) throws RepositoryAccessException, EmpresaNotFoundException {
        try {
            repository.deletarEmpresaPeloId(id)
        } catch (SQLException e) {
            throw new RepositoryAccessException(e.getMessage(), e.getCause())
        }
    }

}
