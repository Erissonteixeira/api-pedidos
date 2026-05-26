package io.github.Erissonteixeira.api_pedidos.controller;

import io.github.Erissonteixeira.api_pedidos.dto.cliente.ClienteRequestDto;
import io.github.Erissonteixeira.api_pedidos.dto.cliente.ClienteResponseDto;
import io.github.Erissonteixeira.api_pedidos.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponseDto> criar(
            @RequestBody @Valid ClienteRequestDto dto
    ) {

        ClienteResponseDto cliente = clienteService.criar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }
}