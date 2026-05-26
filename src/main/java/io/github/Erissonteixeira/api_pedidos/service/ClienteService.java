package io.github.Erissonteixeira.api_pedidos.service;

import io.github.Erissonteixeira.api_pedidos.dto.cliente.ClienteRequestDto;
import io.github.Erissonteixeira.api_pedidos.dto.cliente.ClienteResponseDto;
import io.github.Erissonteixeira.api_pedidos.entity.Cliente;
import io.github.Erissonteixeira.api_pedidos.mapper.ClienteMapper;
import io.github.Erissonteixeira.api_pedidos.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public ClienteResponseDto criar(ClienteRequestDto dto) {

        if (clienteRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        if (clienteRepository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        Cliente cliente = ClienteMapper.toEntity(dto);

        Cliente clienteSalvo = clienteRepository.save(cliente);

        return ClienteMapper.toDto(clienteSalvo);
    }
}
