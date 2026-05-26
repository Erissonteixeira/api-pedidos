package io.github.Erissonteixeira.api_pedidos.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequestDto(

        @NotBlank
        @Size(min = 3, max = 30)
        String nome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 11, max = 14)
        String cpf,

        @NotBlank
        String telefone

) {
}
