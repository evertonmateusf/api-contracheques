package com.stone.apicontracheques;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
	void getFuncionarioSemAutenticarERecebe403() throws Exception {
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
	void postFuncionarioSemAutenticarERecebe403() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
		funcionarioDTO.setNome("Carolina Heloisa Emilly");
		funcionarioDTO.setSobrenome("Aparício");
		funcionarioDTO.setDocumento("22222166306");
		funcionarioDTO.setSetor("Marketing");
		funcionarioDTO.setSalarioBruto(9865);
		funcionarioDTO.setDataDeAdmissao(sdf.parse("2019-01-02 13:55"));
		funcionarioDTO.setDescontaPlanoDeSaude("true");
		funcionarioDTO.setDescontaPlanoDental("false");
		funcionarioDTO.setDescontaValeTransporte("false");
		mockMvc.perform(
			post("/funcionarios")
			.contentType("application/json")
			.content(objectMapper.writeValueAsString(funcionarioDTO)))
			.andExpect(status().isForbidden());
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
		funcionarioDTO.setDescontaPlanoDeSaude("true");
		funcionarioDTO.setDescontaPlanoDental("false");
		funcionarioDTO.setDescontaValeTransporte("false");
		ResultActions result = mockMvc.perform(
				post("/funcionarios")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(funcionarioDTO))
				.header("Authorization", getToken()))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				// .andDo(print())
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
		funcionarioDTO.setDescontaPlanoDeSaude("true");
		funcionarioDTO.setDescontaPlanoDental("false");
		funcionarioDTO.setDescontaValeTransporte("false");
		mockMvc.perform(
				post("/funcionarios")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(funcionarioDTO))
				.header("Authorization", getToken()))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				// .andDo(print())
				.andExpect(status().is4xxClientError())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].fieldName").value("documento"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message").value("CPF inválido"));
	}
	@Test
	void postFuncionarioJaExistenteRecebe400JaExiste() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
		funcionarioDTO.setNome("Daiane Sarah");
		funcionarioDTO.setSobrenome("Porto");
		funcionarioDTO.setDocumento("00099458810");
		funcionarioDTO.setSetor("RH");
		funcionarioDTO.setSalarioBruto(1000);
		funcionarioDTO.setDataDeAdmissao(sdf.parse("2019-01-02"));
		funcionarioDTO.setDescontaPlanoDeSaude("true");
		funcionarioDTO.setDescontaPlanoDental("true");
		funcionarioDTO.setDescontaValeTransporte("true");
		mockMvc.perform(
				post("/funcionarios")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(funcionarioDTO))
				.header("Authorization", getToken()))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				// .andDo(print())
				.andExpect(status().is4xxClientError())
				.andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Integridade de dados"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("CPF 00099458810 já existente."));
	}
	@Test
	void putFuncionarioSemAutenticarERecebe403() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
		funcionarioDTO.setNome("Daiane Sarah Alterado");
		funcionarioDTO.setSobrenome("Porto Seguro");
		funcionarioDTO.setDocumento("00099458810");
		funcionarioDTO.setSetor("Vendas");
		funcionarioDTO.setSalarioBruto(7000);
		funcionarioDTO.setDataDeAdmissao(sdf.parse("2019-01-03"));
		funcionarioDTO.setDescontaPlanoDeSaude("true");
		funcionarioDTO.setDescontaPlanoDental("false");
		funcionarioDTO.setDescontaValeTransporte("true");
		mockMvc.perform(
			put("/funcionarios/1")
			.contentType("application/json")
			.content(objectMapper.writeValueAsString(funcionarioDTO)))
			.andExpect(status().isForbidden());
	}
	@Test
	void putFuncionarioJaExistenteRecebe204() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
		funcionarioDTO.setNome("Daiane Sarah Alterado");
		funcionarioDTO.setSobrenome("Porto Seguro");
		funcionarioDTO.setDocumento("00099458810");
		funcionarioDTO.setSetor("Vendas");
		funcionarioDTO.setSalarioBruto(7000);
		funcionarioDTO.setDataDeAdmissao(sdf.parse("2019-01-03"));
		funcionarioDTO.setDescontaPlanoDeSaude("true");
		funcionarioDTO.setDescontaPlanoDental("false");
		funcionarioDTO.setDescontaValeTransporte("true");
		mockMvc.perform(
			put("/funcionarios/1")
			.contentType("application/json")
			.content(objectMapper.writeValueAsString(funcionarioDTO))
			.header("Authorization", getToken()))
			// .andDo(print())
			.andExpect(status().isNoContent());
		Funcionario funcionario = funcionarioRepository.findById(1).get();

		Assertions.assertEquals(funcionario.getId(), 1);
		Assertions.assertEquals(funcionario.getNome(), funcionarioDTO.getNome());
		Assertions.assertEquals(funcionario.getSobrenome(), funcionarioDTO.getSobrenome());
		Assertions.assertEquals(funcionario.getDocumento(), funcionarioDTO.getDocumento());
		Assertions.assertEquals(funcionario.getSetor(), funcionarioDTO.getSetor());
		Assertions.assertEquals(funcionario.getSalarioBruto(), funcionarioDTO.getSalarioBruto());
		Assertions.assertEquals(sdf.format(funcionario.getDataDeAdmissao()), sdf.format(funcionarioDTO.getDataDeAdmissao()));
		Assertions.assertEquals(funcionario.isDescontaPlanoDeSaude(), funcionarioDTO.isDescontaPlanoDeSaude());
		Assertions.assertEquals(funcionario.isDescontaPlanoDental(), funcionarioDTO.isDescontaPlanoDental());
		Assertions.assertEquals(funcionario.isDescontaValeTransporte(), funcionarioDTO.isDescontaValeTransporte());
	}
	@Test
	void putFuncionarioNaoExistenteRecebe404() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
		funcionarioDTO.setNome("Daiane Sarah Alterado");
		funcionarioDTO.setSobrenome("Porto Seguro");
		funcionarioDTO.setDocumento("00099458810");
		funcionarioDTO.setSetor("Vendas");
		funcionarioDTO.setSalarioBruto(7000);
		funcionarioDTO.setDataDeAdmissao(sdf.parse("2019-01-03"));
		funcionarioDTO.setDescontaPlanoDeSaude("true");
		funcionarioDTO.setDescontaPlanoDental("false");
		funcionarioDTO.setDescontaValeTransporte("true");
		mockMvc.perform(
			put("/funcionarios/9999")
			.contentType("application/json")
			.content(objectMapper.writeValueAsString(funcionarioDTO))
			.header("Authorization", getToken()))
			// .andDo(print())
			.andExpect(status().isNotFound());
	}
	
	@Test
	void deleteFuncionarioSemAutenticarERecebe403() throws Exception {
		mockMvc.perform(
			delete("/funcionarios/9")
			.contentType("application/json"))
			.andExpect(status().isForbidden());
	}
	@Test
	void deleteFuncionarioJaExistenteRecebe204() throws Exception {
		mockMvc.perform(
			delete("/funcionarios/9")
			.contentType("application/json")
			.header("Authorization", getToken()))
			// .andDo(print())
			.andExpect(status().isNoContent());
	}
	@Test
	void deleteFuncionarioNaoExistenteRecebe404() throws Exception {
		mockMvc.perform(
			delete("/funcionarios/9999")
			.contentType("application/json")
			.header("Authorization", getToken()))
			// .andDo(print())
			.andExpect(status().isNotFound());
	}
	
}
