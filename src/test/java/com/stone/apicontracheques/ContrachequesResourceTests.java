package com.stone.apicontracheques;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stone.apicontracheques.domain.Contracheque;
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
				post("/login").contentType("application/json").content(objectMapper.writeValueAsString(credenciais)))
				.andExpect(status().isOk());
		return result.andReturn().getResponse().getHeader("Authorization");
	}

	@Test
	void getContraChequeSemAutenticarERecebe403() throws Exception {
		String codigo = "1";
		mockMvc.perform(get("/funcionarios/" + codigo + "/contracheque").contentType("application/json"))
				.andExpect(status().isForbidden());
	}

	@Test
	void getContraChequeNaoExistenteRecebe404() throws Exception {
		mockMvc.perform(get("/funcionarios/9999/contracheque").contentType("application/json").header("Authorization",
				getToken())).andExpect(status().isNotFound());
	}

	@Test
	void getContraCheque07_5PorCentoINSS() throws Exception {
		//salario bruto 1000
		//IR isento (< 1903,98)
		//desconta plano de saude (-10), 
		//descpmta dental(-5) 
		//isento de desconto de VT, pois ganha menos de 1500 (VT seria 6% sobre o bruto)
		//FGTS 8% sobre o bruto -80
		//INSS 7,5% sobre o bruto -75
		//liquido 830
		String codigo = "1";
		Calendar rightNow = Calendar.getInstance();
		Contracheque contracheque = new Contracheque();
		contracheque.setSalarioBruto(1000);
		contracheque.setTotalDescontos(-170);
		contracheque.setSalarioLiquido(830);
		contracheque.setMesReferencia(rightNow.get(Calendar.MONTH+1));

		mockMvc.perform(
				get("/funcionarios/" + codigo + "/contracheque").contentType("application/json")
				.header("Authorization", getToken()))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.mesReferencia", is(contracheque.getMesReferencia())))
				.andExpect(jsonPath("$.totalDescontos", is(contracheque.getTotalDescontos())))
				.andExpect(jsonPath("$.salarioBruto", is(contracheque.getSalarioBruto())))
				.andExpect(jsonPath("$.salarioLiquido", is(contracheque.getSalarioLiquido())))
				.andExpect(jsonPath("$.lancamentos", hasSize(5) ) )
                .andExpect(jsonPath("$.lancamentos[1].tipoLancamento", is("DESCONTO")))
                .andExpect(jsonPath("$.lancamentos[1].valor", is(75.0)))
                .andExpect(jsonPath("$.lancamentos[1].descricao", is("INSS")));

		// JacksonJsonParser jsonParser = new JacksonJsonParser();
		// Map<String, Object> objReturned = jsonParser.parseMap(result.andReturn().getResponse().getContentAsString());
		
		// Assertions.assertEquals(contracheque.getMesReferencia(), objReturned.get("mesReferencia"));
		// Assertions.assertEquals(contracheque.getTotalDescontos(), objReturned.get("totalDescontos"));
		// Assertions.assertEquals(contracheque.getSalarioBruto(), objReturned.get("salarioBruto"));
		// Assertions.assertEquals(contracheque.getSalarioLiquido(), objReturned.get("salarioLiquido"));
	}
	@Test
	void getContraCheque9PorCentoINSS() throws Exception {
		Assertions.assertEquals(1, 2);
	}
	@Test
	void getContraCheque12PorCentoINSS() throws Exception {
		Assertions.assertEquals(1, 2);
	}
	@Test
	void getContraCheque14PorCentoINSS() throws Exception {
		Assertions.assertEquals(1, 2);
	}
	@Test
	void getContraCheque07_5PorCentoIR() throws Exception {
		Assertions.assertEquals(1, 2);
	}
	@Test
	void getContraCheque15PorCentoIR() throws Exception {
		Assertions.assertEquals(1, 2);
	}
	@Test
	void getContraCheque22_5PorCentoIR() throws Exception {
		Assertions.assertEquals(1, 2);
	}
	@Test
	void getContraCheque27_5PorCentoIR() throws Exception {
		Assertions.assertEquals(1, 2);
	}
	@Test
	void getContraChequeComDescontoPlanoSaudeEDentalETransporte() throws Exception {
		Assertions.assertEquals(1, 2);
	}
	@Test
	void getContraChequeComDescontoDentalETransporte() throws Exception {
		Assertions.assertEquals(1, 2);
	}
	@Test
	void getContraChequeComDescontoPlanoSaudeETransporte() throws Exception {
		Assertions.assertEquals(1, 2);
	}
	@Test
	void getContraChequeComDescontoPlanoSaudeEDental() throws Exception {
		Assertions.assertEquals(1, 2);
	}
	@Test
	void getContraChequeComDescontoPlanoSaude() throws Exception {
		Assertions.assertEquals(1, 2);
	}
	@Test
	void getContraChequeComDescontoDental() throws Exception {
		Assertions.assertEquals(1, 2);
	}
	@Test
	void getContraChequeComDescontoTransporte() throws Exception {
		Assertions.assertEquals(1, 2);
	}
	@Test
	void getContraChequeCom8PorCentoFGTS() throws Exception {
		Assertions.assertEquals(1, 2);
	}

}
