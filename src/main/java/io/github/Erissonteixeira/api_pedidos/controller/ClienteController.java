package io.github.Erissonteixeira.api_pedidos.controller;

import io.github.Erissonteixeira.api_pedidos.dto.cliente.ClienteRequestDto;
import io.github.Erissonteixeira.api_pedidos.dto.cliente.ClienteResponseDto;
import io.github.Erissonteixeira.api_pedidos.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> listar() {

        List<ClienteResponseDto> clientes = clienteService.listar();

        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> buscarPorId(
            @PathVariable Long id
    ) {

        ClienteResponseDto cliente = clienteService.buscarPorId(id);

        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ClienteRequestDto dto
    ) {

        ClienteResponseDto cliente = clienteService.atualizar(id, dto);

        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id
    ) {

        clienteService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}