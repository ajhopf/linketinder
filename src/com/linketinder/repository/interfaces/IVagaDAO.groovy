package com.linketinder.repository.interfaces

import com.linketinder.model.dtos.VagaRequestDTO
import com.linketinder.model.dtos.VagaResponseDTO

interface IVagaDAO {
    List<VagaResponseDTO> listarVagas()
    Integer adicionarVaga(VagaRequestDTO vaga)
    VagaResponseDTO obterVagaPeloId(Integer vagaId)
    void updateVaga(Integer vagaId, VagaRequestDTO vaga)
    void deleteVaga(Integer id)
}