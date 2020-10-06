package br.com.gsn.minhasFinancas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import br.com.gsn.minhasFinancas.exception.ErroAutenticacao;
import br.com.gsn.minhasFinancas.exception.RegraNegocioExpection;
import br.com.gsn.minhasFinancas.model.entity.Usuario;
import br.com.gsn.minhasFinancas.model.repository.UsuarioRepository;
import br.com.gsn.minhasFinancas.service.impl.UsuarioServiceImpl;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	@SpyBean
	UsuarioServiceImpl service;
	
	@MockBean
	UsuarioRepository repository;
	
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
	
	@Test
	public void deveAltenticarUmUsuarioComSucesso() {
		//cenario
		String email="gabriel_maker@hotmail.com";
		String senha="senha";
		
		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
		
		//acao
		Assertions.assertDoesNotThrow(()->service.autenticar(email, senha));
	}
	
	@Test
	public void deveRetornarErroPorNaoEncontrarUsuarioCadastrado() {
		//cenario
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		//ação
		ErroAutenticacao assertThrows = Assertions.assertThrows(ErroAutenticacao.class,()->{
			service.autenticar("email@email", "123");
		});
		
		assertEquals("Usuario não encontrado.", assertThrows.getMessage());
	}
	
	@Test
	public void deveRetornarErroDeSenhaInvalida() {
		//cenario
		String senha="senha";
		
		Usuario usuario = Usuario.builder().email("gabriel_maker@hotmail.com").senha(senha).build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
		
		//acao
		ErroAutenticacao assertThrows = Assertions.assertThrows(ErroAutenticacao.class,()->{
			service.autenticar("gabriel_maker@hotmail.com", "123");
		});
		
		assertEquals("Senha inválida !", assertThrows.getMessage());
	}
	
	@Test
	public void deveSalvarUmUsuario() {
		//cenário
		Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
		Usuario usuario = Usuario.builder()
				.id(1)
				.nome("nome")
				.email("email@email.com")
				.senha("senha")
				.build();
		
		Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);		
		
		//acao
		Usuario usuarioSalvo = service.salvarUsuario(new Usuario());
		
		//verificacao
		Assertions.assertNotNull(usuarioSalvo);
		Assertions.assertEquals(usuarioSalvo.getId(), 1);
		Assertions.assertEquals(usuarioSalvo.getNome(), "nome");
		Assertions.assertEquals(usuarioSalvo.getEmail(), "email@email.com");
		Assertions.assertEquals(usuarioSalvo.getSenha(), "senha");
	}
	
	@Test
	public void naoDeveDeixarCadastrarUsuarioComEmailJaCadastrado() {
		//cenário
		String email = "email@email.com";
		Usuario usuario = Usuario.builder().email(email).build();
		Mockito.doThrow(RegraNegocioExpection.class).when(service).validarEmail(email);
		
		//ação
		Assertions.assertThrows(RegraNegocioExpection.class,()->{
			service.salvarUsuario(usuario);
		});
		
		//verificação
		Mockito.verify(repository, Mockito.never()).save(usuario);
	}
}
