package io.github.Erissonteixeira.api_pedidos.mapper;

import io.github.Erissonteixeira.api_pedidos.dto.cliente.ClienteRequestDto;
import io.github.Erissonteixeira.api_pedidos.dto.cliente.ClienteResponseDto;
import io.github.Erissonteixeira.api_pedidos.entity.Cliente;

public class ClienteMapper {

    public static Cliente toEntity(ClienteRequestDto dto) {

        return Cliente.builder()
                .nome(dto.nome())
                .email(dto.email())
                .cpf(dto.cpf())
                .telefone(dto.telefone())
                .build();
    }

    public static ClienteResponseDto toDto(Cliente cliente) {

        return new ClienteResponseDto(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getDataCadastro()
        );
    }
}
