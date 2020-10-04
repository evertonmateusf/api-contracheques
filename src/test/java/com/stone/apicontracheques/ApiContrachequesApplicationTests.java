package com.stone.apicontracheques;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stone.apicontracheques.domain.Funcionario;
import com.stone.apicontracheques.dto.credenciais.CredenciaisDTO;
import com.stone.apicontracheques.repositories.FuncionarioRepository;
import com.stone.apicontracheques.resources.FuncionarioResource;
import com.stone.apicontracheques.services.FuncionarioService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class ApiContrachequesApplicationTests {

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
	void getFuncionarioSemAutenticarERecebe401() throws Exception {
		String codigo = "1";
		mockMvc.perform(
				get("/funcionarios/" + codigo).contentType("application/json"))
				.andExpect(status().isForbidden());
	}

	@Test
	void getFuncionarioByCodeRecebe200() throws Exception {
		String codigo = "1";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		ResultActions result = mockMvc.perform(
				get("/funcionarios/" + codigo).contentType("application/json")
				.header("Authorization", getToken()))
				.andDo(print())
				.andExpect(status()
				.isOk());

		String resultString = result.andReturn().getResponse().getContentAsString();
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		Map<String, Object> objReturned = jsonParser.parseMap(resultString);
		Funcionario funcionario = funcionarioRepository.findById(1).get();

		Assertions.assertEquals(funcionario.getId(), objReturned.get("id"));
		Assertions.assertEquals(funcionario.getNome(), objReturned.get("nome"));
		Assertions.assertEquals(funcionario.getSobrenome(), objReturned.get("sobrenome"));
		Assertions.assertEquals(funcionario.getDocumento(), objReturned.get("documento"));
		Assertions.assertEquals(funcionario.getSetor(), objReturned.get("setor"));
		Assertions.assertEquals(funcionario.getSalarioBruto(), objReturned.get("salarioBruto"));
		Assertions.assertEquals(sdf.format(funcionario.getDataDeAdmissao()), objReturned.get("dataDeAdmissao"));
		Assertions.assertEquals(funcionario.isDescontaPlanoDeSaude(), objReturned.get("descontaPlanoDeSaude"));
		Assertions.assertEquals(funcionario.isDescontaPlanoDental(), objReturned.get("descontaPlanoDental"));
		Assertions.assertEquals(funcionario.isDescontaValeTransporte(), objReturned.get("descontaValeTransporte"));
	}
	@Test
	void getFuncionarioByCodeNaoExistenteRecebe404() throws Exception {
	}
	@Test
	void postFuncionarioSemAutenticarERecebe401() throws Exception {
	}
	@Test
	void postFuncionarioNovoRecebe201() throws Exception {
	}
	@Test
	void postFuncionarioJaExistenteRecebe400JaExiste() throws Exception {
	}
	@Test
	void putFuncionarioSemAutenticarERecebe401() throws Exception {
	}
	@Test
	void putFuncionarioJaExistenteRecebe200() throws Exception {
	}
	@Test
	void putFuncionarioNaoExistenteRecebe404() throws Exception {
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
