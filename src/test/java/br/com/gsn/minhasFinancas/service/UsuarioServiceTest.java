package br.com.gsn.minhasFinancas.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.gsn.minhasFinancas.exception.RegraNegocioExpection;
import br.com.gsn.minhasFinancas.model.repository.UsuarioRepository;
import br.com.gsn.minhasFinancas.service.impl.UsuarioServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	UsuarioService service;
	
	@MockBean
	UsuarioRepository repository;
	
	@BeforeEach
	public void setUp() {
		service = new UsuarioServiceImpl(repository);
	}
	
	@Test
	public void deveValidarEmail() {
		//cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);	
		
		//ação
		Assertions.assertDoesNotThrow(()->service.validarEmail("gabriel_maker@hotmail.com"));
	}
	
	@Test
	public void deveLancarErroQuandoExisteEmailCadastrado() {
		//cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		//ação
		Assertions.assertThrows(RegraNegocioExpection.class,()->{
			service.validarEmail("gabriel_maker@hotmail.com");
		});
		
	}
}
