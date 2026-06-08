package io.github.Erissonteixeira.api_pedidos.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiError(

        LocalDateTime timestamp,

        Integer status,

        String erro,

        String mensagem,

        String path,

        Map<String, String> campos

) {
}