package io.github.Erissonteixeira.api_pedidos.controller;

import io.github.Erissonteixeira.api_pedidos.entity.Cliente;
import io.github.Erissonteixeira.api_pedidos.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
    }

    @Test
    void deveCriarClienteComSucesso() throws Exception {
        String json = """
                {
                  "nome": "Cliente Teste",
                  "email": "cliente@teste.com",
                  "cpf": "12345678901",
                  "telefone": "51999999999"
                }
                """;

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Cliente Teste"))
                .andExpect(jsonPath("$.email").value("cliente@teste.com"))
                .andExpect(jsonPath("$.cpf").value("12345678901"))
                .andExpect(jsonPath("$.telefone").value("51999999999"));
    }

    @Test
    void deveListarClientesComSucesso() throws Exception {
        salvarCliente();

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome").value("Cliente Teste"));
    }

    @Test
    void deveBuscarClientePorIdComSucesso() throws Exception {
        Cliente cliente = salvarCliente();

        mockMvc.perform(get("/clientes/{id}", cliente.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cliente.getId()))
                .andExpect(jsonPath("$.nome").value("Cliente Teste"));
    }

    @Test
    void deveRetornar404AoBuscarClienteInexistente() throws Exception {
        mockMvc.perform(get("/clientes/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.erro").value("Recurso não encontrado"))
                .andExpect(jsonPath("$.mensagem").value("Cliente não encontrado"));
    }

    @Test
    void deveRetornar400AoCriarClienteInvalido() throws Exception {
        String json = """
                {
                  "nome": "",
                  "email": "email-invalido",
                  "cpf": "123",
                  "telefone": ""
                }
                """;

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.erro").value("Dados inválidos"))
                .andExpect(jsonPath("$.campos.nome").exists())
                .andExpect(jsonPath("$.campos.email").exists())
                .andExpect(jsonPath("$.campos.cpf").exists())
                .andExpect(jsonPath("$.campos.telefone").exists());
    }

    @Test
    void deveAtualizarClienteComSucesso() throws Exception {
        Cliente cliente = salvarCliente();

        String json = """
                {
                  "nome": "Cliente Atualizado",
                  "email": "cliente.atualizado@teste.com",
                  "cpf": "12345678901",
                  "telefone": "51977777777"
                }
                """;

        mockMvc.perform(put("/clientes/{id}", cliente.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Cliente Atualizado"))
                .andExpect(jsonPath("$.email").value("cliente.atualizado@teste.com"))
                .andExpect(jsonPath("$.telefone").value("51977777777"));
    }

    @Test
    void deveDeletarClienteComSucesso() throws Exception {
        Cliente cliente = salvarCliente();

        mockMvc.perform(delete("/clientes/{id}", cliente.getId()))
                .andExpect(status().isNoContent());
    }

    private Cliente salvarCliente() {
        Cliente cliente = Cliente.builder()
                .nome("Cliente Teste")
                .email("cliente@teste.com")
                .cpf("12345678901")
                .telefone("51999999999")
                .dataCadastro(LocalDateTime.now())
                .build();

        return clienteRepository.save(cliente);
    }
}