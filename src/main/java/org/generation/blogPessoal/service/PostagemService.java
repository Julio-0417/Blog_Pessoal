package org.generation.blogPessoal.service;

import java.util.List;

import org.generation.blogPessoal.model.Postagem;
import org.generation.blogPessoal.repository.PostagemRepository;
import org.generation.blogPessoal.repository.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class PostagemService {
	
	private @Autowired PostagemRepository repositoryP;
	private @Autowired TemaRepository repositoryT;
	
	/*
	 * Buscar todas Postagens
	 */
	
	public ResponseEntity<List<Postagem>> todasPostagens() {
		List<Postagem> listaPostagem = repositoryP.findAll();
		if (listaPostagem.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(listaPostagem);
		}
		
	}
	
	public ResponseEntity<Postagem> buscaPorId(Long id) {
		return repositoryP.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	public ResponseEntity<List<Postagem>> buscaPorTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(repositoryP.findAllByTituloContainingIgnoreCase(titulo));
		
	}
	
	public ResponseEntity<Postagem> salvarPostagem (Postagem novaPostagem) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(repositoryP.save(novaPostagem));
	}
	
	public ResponseEntity<Postagem> atualizarPostagem (Postagem novaPostagem){
		if (repositoryP.existsById(novaPostagem.getId())
			&& repositoryT.existsById(novaPostagem.getTema().getId())) {
			return ResponseEntity.status(200).body(repositoryP.save(novaPostagem));
		} else {
			return ResponseEntity.status(404).build();
		}
	} 
	
	public ResponseEntity<Postagem> deletarPostagem (Long id) {
		if (repositoryP.existsById(id)) {
			repositoryP.deleteById(id);
		} else {
			return ResponseEntity.status(404).build();
		}
		return null;
	}
	

	
	
	

}
