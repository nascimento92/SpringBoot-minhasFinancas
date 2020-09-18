package br.com.gsn.minhasFinancas.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.gsn.minhasFinancas.exception.RegraNegocioExpection;
import br.com.gsn.minhasFinancas.model.entity.Usuario;
import br.com.gsn.minhasFinancas.model.repository.UsuarioRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	@Autowired
	UsuarioService service;
	
	@Autowired
	UsuarioRepository repository;
	
	@Test
	public void deveValidarEmail() {
		//cenario
		repository.deleteAll();
		
		//ação
		Assertions.assertDoesNotThrow(()->service.validarEmail("gabriel_maker@hotmail.com"));
	}
	
	@Test
	public void deveLancarErroQuandoExisteEmailCadastrado() {
		//cenario
		Usuario usuario = Usuario.builder().nome("gabriel").email("gabriel_maker@hotmail.com").build();
		repository.save(usuario);
		
		//ação
		Assertions.assertThrows(RegraNegocioExpection.class,()->{
			service.validarEmail("gabriel_maker@hotmail.com");
		});
		
	}
}
