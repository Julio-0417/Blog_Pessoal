package org.generation.blogPessoal.service;

import java.util.List;
import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.blogPessoal.model.UserLogin;
import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	public ResponseEntity<List<Usuario>> pegarTodos(){
		List<Usuario> listaDeUsuario = repository.findAll();
		if (!listaDeUsuario.isEmpty()) {
			return ResponseEntity.status(200).body(listaDeUsuario);
		} else {			
			return ResponseEntity.status(204).build();
		}
	}
	
	public Usuario CadastrarUsuario(Usuario usuario) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);
		
		return repository.save(usuario);
	}
	
	public ResponseEntity<Usuario> atualizarUsuario(Long id, Usuario novoUsuario) {
		Optional<Usuario> idUsuarioExiste = repository.findById(id);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if(idUsuarioExiste.isPresent()) {
			
			if(novoUsuario.getNome() == null) {
			String senhaEncoder = encoder.encode(novoUsuario.getSenha());
			idUsuarioExiste.get().setSenha(senhaEncoder);
			return ResponseEntity.status(202).body(repository.save(idUsuarioExiste.get()));

		} else if (novoUsuario.getSenha() == null) {
			idUsuarioExiste.get().setNome(novoUsuario.getNome());
			return ResponseEntity.status(202).body(repository.save(idUsuarioExiste.get()));

		} else {
			String senhaEncoder = encoder.encode(novoUsuario.getSenha());
			idUsuarioExiste.get().setNome(novoUsuario.getNome());
			idUsuarioExiste.get().setSenha(senhaEncoder);
			return ResponseEntity.status(202).body(repository.save(idUsuarioExiste.get()));
		}
	} else {
		return ResponseEntity.status(304).build();
		}
}
	
	public Optional<UserLogin> Logar(Optional<UserLogin> user){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = repository.findByUsuario(user.get().getUsuario());
		
		if(usuario.isPresent()) {
			if(encoder.matches(user.get().getSenha(), usuario.get().getSenha())); {
				
				String auth = user.get().getUsuario() + ":" + user.get().getSenha();
				byte[] encodeAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodeAuth);
				
				user.get().setToken(authHeader);
				user.get().setNome(usuario.get().getNome());
				
				return user;
			}
		}
		
		return null;
	}

}
