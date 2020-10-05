package com.stone.apicontracheques.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.stone.apicontracheques.domain.Funcionario;
import com.stone.apicontracheques.domain.Usuario;
import com.stone.apicontracheques.domain.enums.PerfilAcesso;
import com.stone.apicontracheques.domain.enums.StatusUsuario;
import com.stone.apicontracheques.repositories.FuncionarioRepository;
import com.stone.apicontracheques.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DbService {
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	FuncionarioRepository funcionarioRepository;
	@Autowired
	private BCryptPasswordEncoder passEncoder;

	public void instantiateTestDatabase() throws ParseException {

		// Estado estadoSP = new Estado(null, "SP", "São Paulo");

		// Cidade cidade1 = new Cidade(null, "Guarulhos", estadoSP);
		// Cidade cidade1 = new Cidade(null, "São Paulo", estadoSP);
		

		// estadoSP.getCidades().addAll(Arrays.asList(cidade1, cidade2));

		// estadoRepository.saveAll(Arrays.asList(estadoSP));
		// cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Usuario usr = new Usuario(null, "Administrador", "everton.mfernandes@gmail.com", passEncoder.encode("1234"),
				null, new Date(System.currentTimeMillis()), StatusUsuario.ATIVO);
		usr.addPerfil(PerfilAcesso.ADMINISTRADOR);
		usr.setUsuarioInclusao(usr);
		usr.setUsuarioUltimaAlteracao(usr);
		usuarioRepository.saveAll(Arrays.asList(usr));

		Funcionario fun1 = new Funcionario();
		fun1.setNome("Daiane Sarah");
		fun1.setSobrenome("Porto");
		fun1.setDocumento("00099458810");
		fun1.setSetor("RH");
		fun1.setSalarioBruto(1000);
		fun1.setDataDeAdmissao(sdf.parse("02/01/2019 15:33"));
		fun1.setDescontaPlanoDeSaude(true);
		fun1.setDescontaPlanoDental(true);
		fun1.setDescontaValeTransporte(true);

		Funcionario fun2 = new Funcionario();
		fun2.setNome("Kevin Oliver");
		fun2.setSobrenome("Silva");
		fun2.setDocumento("08899345171");
		fun2.setSetor("Marketing");
		fun2.setSalarioBruto(2000);
		fun2.setDataDeAdmissao(sdf.parse("02/01/2018 15:33"));
		fun2.setDescontaPlanoDeSaude(true);
		fun2.setDescontaPlanoDental(true);
		fun2.setDescontaValeTransporte(false);

		Funcionario fun3 = new Funcionario();
		fun3.setNome("Luzia Marina");
		fun3.setSobrenome("Farias");
		fun3.setDocumento("10272679712");
		fun3.setSetor("Vendas");
		fun3.setSalarioBruto(3000);
		fun3.setDataDeAdmissao(sdf.parse("02/01/2002 15:33"));
		fun3.setDescontaPlanoDeSaude(true);
		fun3.setDescontaPlanoDental(false);
		fun3.setDescontaValeTransporte(true);

		Funcionario fun4 = new Funcionario();
		fun4.setNome("Ester Mirella");
		fun4.setSobrenome("de Paula");
		fun4.setDocumento("35407397854");
		fun4.setSetor("Compras");
		fun4.setSalarioBruto(4000);
		fun4.setDataDeAdmissao(sdf.parse("02/01/2001 15:33"));
		fun4.setDescontaPlanoDeSaude(false);
		fun4.setDescontaPlanoDental(true);
		fun4.setDescontaValeTransporte(true);

		Funcionario fun5 = new Funcionario();
		fun5.setNome("Tatiane Betina Laís");
		fun5.setSobrenome("Galvão");
		fun5.setDocumento("69670036747");
		fun5.setSetor("RH");
		fun5.setSalarioBruto(5000);
		fun5.setDataDeAdmissao(sdf.parse("02/01/2015 15:33"));
		fun5.setDescontaPlanoDeSaude(true);
		fun5.setDescontaPlanoDental(false);
		fun5.setDescontaValeTransporte(false);

		Funcionario fun6 = new Funcionario();
		fun6.setNome("Thomas Sebastião");
		fun6.setSobrenome("Bernardes");
		fun6.setDocumento("04456466243");
		fun6.setSetor("Marketing");
		fun6.setSalarioBruto(6000);
		fun6.setDataDeAdmissao(sdf.parse("02/01/2020 15:33"));
		fun6.setDescontaPlanoDeSaude(false);
		fun6.setDescontaPlanoDental(true);
		fun6.setDescontaValeTransporte(false);
		
		Funcionario fun7 = new Funcionario();
		fun7.setNome("Melissa Gabriela");
		fun7.setSobrenome("Nascimento");
		fun7.setDocumento("70926876082");
		fun7.setSetor("Vendas");
		fun7.setSalarioBruto(7000);
		fun7.setDataDeAdmissao(sdf.parse("02/01/2017 15:33"));
		fun7.setDescontaPlanoDeSaude(false);
		fun7.setDescontaPlanoDental(false);
		fun7.setDescontaValeTransporte(true);

		Funcionario fun8 = new Funcionario();
		fun8.setNome("Gabriel Augusto");
		fun8.setSobrenome("Mendes");
		fun8.setDocumento("37835550199");
		fun8.setSetor("Compras");
		fun8.setSalarioBruto(8000);
		fun8.setDataDeAdmissao(sdf.parse("02/01/2008 15:33"));
		fun8.setDescontaPlanoDeSaude(false);
		fun8.setDescontaPlanoDental(false);
		fun8.setDescontaValeTransporte(false);

		Funcionario fun9 = new Funcionario();
		fun9.setNome("Funcionario Delete");
		fun9.setSobrenome("Porto");
		fun9.setDocumento("25910888021");
		fun9.setSetor("RH");
		fun9.setSalarioBruto(1000);
		fun9.setDataDeAdmissao(sdf.parse("02/01/2019 15:33"));
		fun9.setDescontaPlanoDeSaude(true);
		fun9.setDescontaPlanoDental(true);
		fun9.setDescontaValeTransporte(true);

		funcionarioRepository.saveAll(Arrays.asList(fun1,fun2,fun3,fun4,fun5,fun6,fun7,fun8,fun9));
		
	}
}
