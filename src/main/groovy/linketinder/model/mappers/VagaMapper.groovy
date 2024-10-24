package linketinder.model.mappers

import linketinder.model.Competencia
import linketinder.model.Endereco
import linketinder.model.Vaga
import linketinder.model.dtos.CompetenciaDTO
import linketinder.model.dtos.VagaRequestDTO
import linketinder.model.dtos.VagaResponseDTO

class VagaMapper {
    static VagaRequestDTO toRequestDTO(Vaga vaga, Integer enderecoId) {
        return new VagaRequestDTO(
                id: vaga.id,
                nome: vaga.nome,
                descricao: vaga.descricao,
                empresaId: vaga.empresa.id,
                enderecoId: enderecoId
        )
    }

    static Vaga toEntity(VagaRequestDTO vagaDTO, Endereco endereco) {
        return new Vaga(
                id: vagaDTO.id,
                nome: vagaDTO.nome,
                descricao: vagaDTO.descricao,
                endereco: endereco,
        )
    }

    static Vaga toEntity(VagaResponseDTO vagaResponseDTO) {
        Endereco enderecoSimples = new Endereco()
        enderecoSimples.estado = vagaResponseDTO.estado
        enderecoSimples.cidade = vagaResponseDTO.cidade

        return new Vaga(
                id: vagaResponseDTO.id,
                nome: vagaResponseDTO.nome,
                descricao: vagaResponseDTO.descricao,
                endereco: enderecoSimples,
        )
    }

//    static List<CompetenciaDTO> entityListToDTOList(List<Competencia> competencias) {
//        List<CompetenciaDTO> competenciaDTOList = []
//
//        for (competencia in competencias) {
//            Integer competenciaId = competenciaService.verificarSeCompetenciaExiste(competencia.competencia)
//            CompetenciaDTO competenciaDTO = CompetenciaMapper.toDTO(competencia)
//            competenciaDTO.id = competenciaId
//            competenciaDTOList << competenciaDTO
//        }
//
//        return competenciaDTOList
//    }
}
