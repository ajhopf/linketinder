package linketinder.repository.interfaces

import linketinder.model.dtos.VagaRequestDTO
import linketinder.model.dtos.VagaResponseDTO

interface VagaDAO {
    List<VagaResponseDTO> listarVagas()
    Integer adicionarVaga(VagaRequestDTO vaga)
    VagaResponseDTO obterVagaPeloId(Integer vagaId)
    void updateVaga(Integer vagaId, VagaRequestDTO vaga)
    void deletarVaga(Integer id)
}