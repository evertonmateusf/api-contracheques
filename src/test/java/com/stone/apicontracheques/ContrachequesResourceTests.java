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
import com.stone.apicontracheques.dto.ContrachequeDTO;
import com.stone.apicontracheques.dto.credenciais.CredenciaisDTO;
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
	void getContraCheque07_5PorCentoINSSIsentoIR() throws Exception {
		String codigo = "1";
		Calendar rightNow = Calendar.getInstance();
		ContrachequeDTO contracheque = new ContrachequeDTO();
		contracheque.setSalarioBruto(1000);
		contracheque.setTotalDescontos(-170);
		contracheque.setSalarioLiquido(830);
		contracheque.setMesReferencia(rightNow.get(Calendar.MONTH)+1);

		mockMvc.perform(get("/funcionarios/" + codigo + "/contracheque").contentType("application/json")
				.header("Authorization", getToken()))
				// .andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.mesReferencia", is(contracheque.getMesReferencia())))
				.andExpect(jsonPath("$.totalDescontos", is(contracheque.getTotalDescontos())))
				.andExpect(jsonPath("$.salarioBruto", is(contracheque.getSalarioBruto())))
				.andExpect(jsonPath("$.salarioLiquido", is(contracheque.getSalarioLiquido())))
				.andExpect(jsonPath("$.lancamentos", hasSize(5)))
				.andExpect(jsonPath("$.lancamentos[0].tipoLancamento", is("REMUNERACAO")))
				.andExpect(jsonPath("$.lancamentos[0].valor", is(1000.0)))
				.andExpect(jsonPath("$.lancamentos[0].descricao", is("Salário")))
				.andExpect(jsonPath("$.lancamentos[1].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[1].valor", is(75.0)))
				.andExpect(jsonPath("$.lancamentos[1].descricao", is("INSS")))
				.andExpect(jsonPath("$.lancamentos[2].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[2].valor", is(10.0)))
				.andExpect(jsonPath("$.lancamentos[2].descricao", is("Plano De Saude")))
				.andExpect(jsonPath("$.lancamentos[3].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[3].valor", is(5.0)))
				.andExpect(jsonPath("$.lancamentos[3].descricao", is("Plano Dental")))
				.andExpect(jsonPath("$.lancamentos[4].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[4].valor", is(80.0)))
				.andExpect(jsonPath("$.lancamentos[4].descricao", is("FGTS")));
	}

	@Test
	void getContraCheque9PorCentoINSS7_5PorCentoIR() throws Exception {
		String codigo = "2";
		Calendar rightNow = Calendar.getInstance();
		ContrachequeDTO contracheque = new ContrachequeDTO();
		contracheque.setSalarioBruto(2000);
		contracheque.setTotalDescontos(-497.8);
		contracheque.setSalarioLiquido(1502.2);
		contracheque.setMesReferencia(rightNow.get(Calendar.MONTH)+1);

		mockMvc.perform(get("/funcionarios/" + codigo + "/contracheque").contentType("application/json")
				.header("Authorization", getToken()))
				// .andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.mesReferencia", is(contracheque.getMesReferencia())))
				.andExpect(jsonPath("$.totalDescontos", is(contracheque.getTotalDescontos())))
				.andExpect(jsonPath("$.salarioBruto", is(contracheque.getSalarioBruto())))
				.andExpect(jsonPath("$.salarioLiquido", is(contracheque.getSalarioLiquido())))
				.andExpect(jsonPath("$.lancamentos", hasSize(6)))
				.andExpect(jsonPath("$.lancamentos[0].tipoLancamento", is("REMUNERACAO")))
				.andExpect(jsonPath("$.lancamentos[0].valor", is(2000.0)))
				.andExpect(jsonPath("$.lancamentos[0].descricao", is("Salário")))
				.andExpect(jsonPath("$.lancamentos[1].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[1].valor", is(180.0)))
				.andExpect(jsonPath("$.lancamentos[1].descricao", is("INSS")))
				.andExpect(jsonPath("$.lancamentos[2].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[2].valor", is(142.8)))
				.andExpect(jsonPath("$.lancamentos[2].descricao", is("Imposto De Renda")))
				.andExpect(jsonPath("$.lancamentos[3].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[3].valor", is(10.0)))
				.andExpect(jsonPath("$.lancamentos[3].descricao", is("Plano De Saude")))
				.andExpect(jsonPath("$.lancamentos[4].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[4].valor", is(5.0)))
				.andExpect(jsonPath("$.lancamentos[4].descricao", is("Plano Dental")))
				.andExpect(jsonPath("$.lancamentos[5].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[5].valor", is(160.0)))
				.andExpect(jsonPath("$.lancamentos[5].descricao", is("FGTS")));
	}

	@Test
	void getContraCheque12PorCentoINSS15PorCentoIR() throws Exception {
		String codigo = "3";
		Calendar rightNow = Calendar.getInstance();
		ContrachequeDTO contracheque = new ContrachequeDTO();
		contracheque.setSalarioBruto(3000);
		contracheque.setTotalDescontos(-1144.8);
		contracheque.setSalarioLiquido(1855.2);
		contracheque.setMesReferencia(rightNow.get(Calendar.MONTH)+1);

		mockMvc.perform(get("/funcionarios/" + codigo + "/contracheque").contentType("application/json")
				.header("Authorization", getToken()))
				// .andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.mesReferencia", is(contracheque.getMesReferencia())))
				.andExpect(jsonPath("$.totalDescontos", is(contracheque.getTotalDescontos())))
				.andExpect(jsonPath("$.salarioBruto", is(contracheque.getSalarioBruto())))
				.andExpect(jsonPath("$.salarioLiquido", is(contracheque.getSalarioLiquido())))
				.andExpect(jsonPath("$.lancamentos", hasSize(6)))
				.andExpect(jsonPath("$.lancamentos[0].tipoLancamento", is("REMUNERACAO")))
				.andExpect(jsonPath("$.lancamentos[0].valor", is(3000.0)))
				.andExpect(jsonPath("$.lancamentos[0].descricao", is("Salário")))
				.andExpect(jsonPath("$.lancamentos[1].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[1].valor", is(360.0)))
				.andExpect(jsonPath("$.lancamentos[1].descricao", is("INSS")))
				.andExpect(jsonPath("$.lancamentos[2].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[2].valor", is(354.8)))
				.andExpect(jsonPath("$.lancamentos[2].descricao", is("Imposto De Renda")))
				.andExpect(jsonPath("$.lancamentos[3].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[3].valor", is(10.0)))
				.andExpect(jsonPath("$.lancamentos[3].descricao", is("Plano De Saude")))
				.andExpect(jsonPath("$.lancamentos[4].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[4].valor", is(180.0)))
				.andExpect(jsonPath("$.lancamentos[4].descricao", is("Vale Transporte")))
				.andExpect(jsonPath("$.lancamentos[5].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[5].valor", is(240.0)))
				.andExpect(jsonPath("$.lancamentos[5].descricao", is("FGTS")));
	}

	@Test
	void getContraCheque14PorCentoINSS22_5PorCentoIR() throws Exception {
		String codigo = "4";
		Calendar rightNow = Calendar.getInstance();
		ContrachequeDTO contracheque = new ContrachequeDTO();
		contracheque.setSalarioBruto(4000);
		contracheque.setTotalDescontos(-1761.13);
		contracheque.setSalarioLiquido(2238.87);
		contracheque.setMesReferencia(rightNow.get(Calendar.MONTH)+1);

		mockMvc.perform(get("/funcionarios/" + codigo + "/contracheque").contentType("application/json")
				.header("Authorization", getToken()))
				// .andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.mesReferencia", is(contracheque.getMesReferencia())))
				.andExpect(jsonPath("$.totalDescontos", is(contracheque.getTotalDescontos())))
				.andExpect(jsonPath("$.salarioBruto", is(contracheque.getSalarioBruto())))
				.andExpect(jsonPath("$.salarioLiquido", is(contracheque.getSalarioLiquido())))
				.andExpect(jsonPath("$.lancamentos", hasSize(6)))
				.andExpect(jsonPath("$.lancamentos[0].tipoLancamento", is("REMUNERACAO")))
				.andExpect(jsonPath("$.lancamentos[0].valor", is(4000.0)))
				.andExpect(jsonPath("$.lancamentos[0].descricao", is("Salário")))
				.andExpect(jsonPath("$.lancamentos[1].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[1].valor", is(560.0)))
				.andExpect(jsonPath("$.lancamentos[1].descricao", is("INSS")))
				.andExpect(jsonPath("$.lancamentos[2].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[2].valor", is(636.13)))
				.andExpect(jsonPath("$.lancamentos[2].descricao", is("Imposto De Renda")))
				.andExpect(jsonPath("$.lancamentos[3].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[3].valor", is(5.0)))
				.andExpect(jsonPath("$.lancamentos[3].descricao", is("Plano Dental")))
				.andExpect(jsonPath("$.lancamentos[4].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[4].valor", is(240.0)))
				.andExpect(jsonPath("$.lancamentos[4].descricao", is("Vale Transporte")))
				.andExpect(jsonPath("$.lancamentos[5].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[5].valor", is(320.0)))
				.andExpect(jsonPath("$.lancamentos[5].descricao", is("FGTS")));
	}

	@Test
	void getContraCheque27_5PorCentoIR() throws Exception {
		String codigo = "5";
		Calendar rightNow = Calendar.getInstance();
		ContrachequeDTO contracheque = new ContrachequeDTO();
		contracheque.setSalarioBruto(5000);
		contracheque.setTotalDescontos(-1979.36);
		contracheque.setSalarioLiquido(3020.64);
		contracheque.setMesReferencia(rightNow.get(Calendar.MONTH)+1);

		mockMvc.perform(get("/funcionarios/" + codigo + "/contracheque").contentType("application/json")
				.header("Authorization", getToken()))
				// .andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.mesReferencia", is(contracheque.getMesReferencia())))
				.andExpect(jsonPath("$.totalDescontos", is(contracheque.getTotalDescontos())))
				.andExpect(jsonPath("$.salarioBruto", is(contracheque.getSalarioBruto())))
				.andExpect(jsonPath("$.salarioLiquido", is(contracheque.getSalarioLiquido())))
				.andExpect(jsonPath("$.lancamentos", hasSize(5)))
				.andExpect(jsonPath("$.lancamentos[0].tipoLancamento", is("REMUNERACAO")))
				.andExpect(jsonPath("$.lancamentos[0].valor", is(5000.0)))
				.andExpect(jsonPath("$.lancamentos[0].descricao", is("Salário")))
				.andExpect(jsonPath("$.lancamentos[1].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[1].valor", is(700.0)))
				.andExpect(jsonPath("$.lancamentos[1].descricao", is("INSS")))
				.andExpect(jsonPath("$.lancamentos[2].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[2].valor", is(869.36)))
				.andExpect(jsonPath("$.lancamentos[2].descricao", is("Imposto De Renda")))
				.andExpect(jsonPath("$.lancamentos[3].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[3].valor", is(10.0)))
				.andExpect(jsonPath("$.lancamentos[3].descricao", is("Plano De Saude")))
				.andExpect(jsonPath("$.lancamentos[4].tipoLancamento", is("DESCONTO")))
				.andExpect(jsonPath("$.lancamentos[4].valor", is(400.0)))
				.andExpect(jsonPath("$.lancamentos[4].descricao", is("FGTS")));
	}

}
