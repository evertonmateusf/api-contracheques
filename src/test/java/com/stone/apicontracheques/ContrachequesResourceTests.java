package com.stone.apicontracheques;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stone.apicontracheques.dto.credenciais.CredenciaisDTO;
import com.stone.apicontracheques.repositories.FuncionarioRepository;
import com.stone.apicontracheques.resources.FuncionarioResource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class ContrachequesResourceTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private FuncionarioResource funcionarioResource;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(funcionarioResource);
	}

	private String getToken() throws JsonProcessingException, Exception {
		CredenciaisDTO credenciais = new CredenciaisDTO();
		credenciais.setEmail("everton.mfernandes@gmail.com");
		credenciais.setSenha("1234");
		ResultActions result = mockMvc.perform(
				post("/login")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(credenciais)))
				.andExpect(status().isOk());
		return result.andReturn().getResponse().getHeader("Authorization");
	}

	@Test
	void getContraChequeSemAutenticarERecebe401() throws Exception {
	}
	@Test
	void getContraChequeNaoExistenteRecebe404() throws Exception {
	}
	@Test
	void getContraCheque07_5PorCentoINSS() throws Exception {
	}
	@Test
	void getContraCheque9PorCentoINSS() throws Exception {
	}
	@Test
	void getContraCheque12PorCentoINSS() throws Exception {
	}
	@Test
	void getContraCheque14PorCentoINSS() throws Exception {
	}
	@Test
	void getContraCheque07_5PorCentoIR() throws Exception {
	}
	@Test
	void getContraCheque15PorCentoIR() throws Exception {
	}
	@Test
	void getContraCheque22_5PorCentoIR() throws Exception {
	}
	@Test
	void getContraCheque27_5PorCentoIR() throws Exception {
	}
	@Test
	void getContraChequeComDescontoPlanoSaudeEDentalETransporte() throws Exception {
	}
	@Test
	void getContraChequeComDescontoDentalETransporte() throws Exception {
	}
	@Test
	void getContraChequeComDescontoPlanoSaudeETransporte() throws Exception {
	}
	@Test
	void getContraChequeComDescontoPlanoSaudeEDental() throws Exception {
	}
	@Test
	void getContraChequeComDescontoPlanoSaude() throws Exception {
	}
	@Test
	void getContraChequeComDescontoDental() throws Exception {
	}
	@Test
	void getContraChequeComDescontoTransporte() throws Exception {
	}
	@Test
	void getContraChequeCom8PorCentoFGTS() throws Exception {
	}

}
