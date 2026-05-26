package io.github.Erissonteixeira.api_pedidos.dto.cliente;

import java.time.LocalDateTime;

public record ClienteResponseDto(

        Long id,

        String nome,

        String email,

        String cpf,

        String telefone,

        LocalDateTime dataCadastro

) {
}
