package io.github.Erissonteixeira.api_pedidos.service;

import io.github.Erissonteixeira.api_pedidos.dto.cliente.ClienteRequestDto;
import io.github.Erissonteixeira.api_pedidos.dto.cliente.ClienteResponseDto;
import io.github.Erissonteixeira.api_pedidos.entity.Cliente;
import io.github.Erissonteixeira.api_pedidos.exception.BusinessException;
import io.github.Erissonteixeira.api_pedidos.exception.ResourceNotFoundException;
import io.github.Erissonteixeira.api_pedidos.mapper.ClienteMapper;
import io.github.Erissonteixeira.api_pedidos.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public ClienteResponseDto criar(ClienteRequestDto dto) {

        if (clienteRepository.existsByEmail(dto.email())) {
            throw new BusinessException("Email já cadastrado");
        }

        if (clienteRepository.existsByCpf(dto.cpf())) {
            throw new BusinessException("CPF já cadastrado");
        }

        Cliente cliente = ClienteMapper.toEntity(dto);

        Cliente clienteSalvo = clienteRepository.save(cliente);

        return ClienteMapper.toDto(clienteSalvo);
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDto> listar() {

        return clienteRepository.findAll()
                .stream()
                .map(ClienteMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponseDto buscarPorId(Long id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        return ClienteMapper.toDto(cliente);
    }

    @Transactional
    public ClienteResponseDto atualizar(Long id, ClienteRequestDto dto) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setCpf(dto.cpf());
        cliente.setTelefone(dto.telefone());

        Cliente clienteAtualizado = clienteRepository.save(cliente);

        return ClienteMapper.toDto(clienteAtualizado);
    }

    @Transactional
    public void deletar(Long id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        clienteRepository.delete(cliente);
    }
}