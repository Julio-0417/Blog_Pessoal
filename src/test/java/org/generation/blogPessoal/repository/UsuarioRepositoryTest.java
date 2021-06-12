package org.generation.blogPessoal.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.generation.blogPessoal.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository repositoryU;
	
	/*
	 * Antes de tudo, cria três usuários para teste, caso seja diferente de nulo, salvar no repositório
	 */
	
	@BeforeAll
	public void start() {
		Usuario usuario = new Usuario("Julio Santos","Julio123","1234567");
		if(repositoryU.findByUsuario(usuario.getUsuario())!=null)
			repositoryU.save(usuario);
		
		usuario = new Usuario("Vitor Santos","Vitor123","7654321");
		if(repositoryU.findByUsuario(usuario.getUsuario())!=null)
			repositoryU.save(usuario);
		
		usuario = new Usuario("Julio Silva","Julio987","3456789");
		if(repositoryU.findByUsuario(usuario.getUsuario())!=null)
			repositoryU.save(usuario);
	}
	
	/**
	 * Teste para retornar usuário em específico
	 * @throws Exception
	 */

	@Test
	public void findByUsuarioRetornaUsuario() throws Exception {
		Usuario usuario = repositoryU.findByUsuario("Julio123").get();
		assertTrue(usuario.getUsuario().equals("Julio123"));
	}
	
	@Test
	public void findAllByUsuarioContainingIgnoreCaseRetornaTresUsuarios() {

		List<Usuario> listaDeUsuarios = repositoryU.findAllByUsuarioContainingIgnoreCase("Julio");
		assertEquals(2, listaDeUsuarios.size());
	}
	
	/**
	 * Ao final, apaga os usuários criados no teste
	 */
	
	@AfterAll
	public void end() {
		repositoryU.deleteAll();
	}

}
