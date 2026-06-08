package io.github.Erissonteixeira.api_pedidos.service;

import io.github.Erissonteixeira.api_pedidos.dto.cliente.ClienteRequestDto;
import io.github.Erissonteixeira.api_pedidos.dto.cliente.ClienteResponseDto;
import io.github.Erissonteixeira.api_pedidos.entity.Cliente;
import io.github.Erissonteixeira.api_pedidos.exception.BusinessException;
import io.github.Erissonteixeira.api_pedidos.exception.ResourceNotFoundException;
import io.github.Erissonteixeira.api_pedidos.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;
    private ClienteRequestDto requestDto;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder()
                .id(1L)
                .nome("Cliente Teste")
                .email("cliente@teste.com")
                .cpf("12345678901")
                .telefone("51999999999")
                .dataCadastro(LocalDateTime.now())
                .build();

        requestDto = new ClienteRequestDto(
                "Cliente Teste",
                "cliente@teste.com",
                "12345678901",
                "51999999999"
        );
    }

    @Test
    void deveCriarClienteComSucesso() {
        when(clienteRepository.existsByEmail(requestDto.email())).thenReturn(false);
        when(clienteRepository.existsByCpf(requestDto.cpf())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteResponseDto response = clienteService.criar(requestDto);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.nome()).isEqualTo("Cliente Teste");
        assertThat(response.email()).isEqualTo("cliente@teste.com");

        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void deveLancarErroAoCriarClienteComEmailDuplicado() {
        when(clienteRepository.existsByEmail(requestDto.email())).thenReturn(true);

        assertThatThrownBy(() -> clienteService.criar(requestDto))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Email já cadastrado");

        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void deveLancarErroAoCriarClienteComCpfDuplicado() {
        when(clienteRepository.existsByEmail(requestDto.email())).thenReturn(false);
        when(clienteRepository.existsByCpf(requestDto.cpf())).thenReturn(true);

        assertThatThrownBy(() -> clienteService.criar(requestDto))
                .isInstanceOf(BusinessException.class)
                .hasMessage("CPF já cadastrado");

        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void deveListarClientesComSucesso() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<ClienteResponseDto> clientes = clienteService.listar();

        assertThat(clientes).hasSize(1);
        assertThat(clientes.get(0).nome()).isEqualTo("Cliente Teste");
    }

    @Test
    void deveBuscarClientePorIdComSucesso() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        ClienteResponseDto response = clienteService.buscarPorId(1L);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.nome()).isEqualTo("Cliente Teste");
    }

    @Test
    void deveLancarErroAoBuscarClienteInexistente() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clienteService.buscarPorId(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Cliente não encontrado");
    }

    @Test
    void deveAtualizarClienteComSucesso() {
        ClienteRequestDto updateDto = new ClienteRequestDto(
                "Cliente Atualizado",
                "cliente.atualizado@teste.com",
                "12345678901",
                "51977777777"
        );

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteResponseDto response = clienteService.atualizar(1L, updateDto);

        assertThat(response.nome()).isEqualTo("Cliente Atualizado");
        assertThat(response.email()).isEqualTo("cliente.atualizado@teste.com");
        assertThat(response.telefone()).isEqualTo("51977777777");

        verify(clienteRepository).save(cliente);
    }

    @Test
    void deveDeletarClienteComSucesso() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        clienteService.deletar(1L);

        verify(clienteRepository).delete(cliente);
    }
}