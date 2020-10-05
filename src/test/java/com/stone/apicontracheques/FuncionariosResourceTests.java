package com.stone.apicontracheques;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stone.apicontracheques.domain.Funcionario;
import com.stone.apicontracheques.dto.FuncionarioDTO;
import com.stone.apicontracheques.dto.credenciais.CredenciaisDTO;
import com.stone.apicontracheques.repositories.FuncionarioRepository;
import com.stone.apicontracheques.resources.FuncionarioResource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class FuncionariosResourceTests {

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
				// .andDo(print())
				.andExpect(status()
				.isOk());

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		Map<String, Object> objReturned = jsonParser.parseMap(result.andReturn().getResponse().getContentAsString());
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
		String codigo = "9999";
		
		mockMvc.perform(
				get("/funcionarios/" + codigo).contentType("application/json")
				.header("Authorization", getToken()))
				.andExpect(status().isNotFound());
	}
	@Test
	void postFuncionarioSemAutenticarERecebe401() throws Exception {
		Assertions.assertEquals(1, 2);
	}
	@Test
	void postFuncionarioNovoRecebe201() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
		funcionarioDTO.setNome("Carolina Heloisa Emilly");
		funcionarioDTO.setSobrenome("Aparício");
		funcionarioDTO.setDocumento("22222166306");
		funcionarioDTO.setSetor("Marketing");
		funcionarioDTO.setSalarioBruto(9865);
		funcionarioDTO.setDataDeAdmissao(sdf.parse("2019-01-02 13:55"));
		funcionarioDTO.setDescontaPlanoDeSaude(true);
		funcionarioDTO.setDescontaPlanoDental(false);
		funcionarioDTO.setDescontaValeTransporte(false);
		ResultActions result = mockMvc.perform(
				post("/funcionarios")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(funcionarioDTO))
				.header("Authorization", getToken()))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		Map<String, Object> objReturned = jsonParser.parseMap(result.andReturn().getResponse().getContentAsString());
		Funcionario funcionario = funcionarioRepository.findById((Integer) objReturned.get("id")).get();

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
	void postFuncionarioCPFInvalidoRecebe400CPFInvalido() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
		funcionarioDTO.setNome("Carolina Heloisa Emilly");
		funcionarioDTO.setSobrenome("Aparício");
		funcionarioDTO.setDocumento("12345678901");
		funcionarioDTO.setSetor("Marketing");
		funcionarioDTO.setSalarioBruto(9865);
		funcionarioDTO.setDataDeAdmissao(sdf.parse("2019-01-02 13:55"));
		funcionarioDTO.setDescontaPlanoDeSaude(true);
		funcionarioDTO.setDescontaPlanoDental(false);
		funcionarioDTO.setDescontaValeTransporte(false);
		mockMvc.perform(
				post("/funcionarios")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(funcionarioDTO))
				.header("Authorization", getToken()))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().is4xxClientError());
	}
	@Test
	void postFuncionarioJaExistenteRecebe400JaExiste() throws Exception {
		Assertions.assertEquals(1, 2);
	}
	@Test
	void putFuncionarioSemAutenticarERecebe401() throws Exception {
		Assertions.assertEquals(1, 2);
	}
	@Test
	void putFuncionarioJaExistenteRecebe200() throws Exception {
		Assertions.assertEquals(1, 2);
	}
	@Test
	void putFuncionarioNaoExistenteRecebe404() throws Exception {
		Assertions.assertEquals(1, 2);
	}
	
}
