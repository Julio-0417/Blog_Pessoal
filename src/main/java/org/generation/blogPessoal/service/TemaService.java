package org.generation.blogPessoal.service;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.model.Tema;
import org.generation.blogPessoal.repository.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TemaService {
	
	private @Autowired TemaRepository repositoryT;
	
	public ResponseEntity<List<Tema>> todosTemas(){
		List<Tema> listaTemas = repositoryT.findAll();
		if (listaTemas.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(listaTemas);
		}
	}
	
	public ResponseEntity<Tema> buscarPorId(Long id) {
		return repositoryT.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	public ResponseEntity<List<Tema>> buscaPorNome(String descricao){
		return ResponseEntity.ok(repositoryT.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	
	public ResponseEntity<Tema> salvarTema (Tema novoTema) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(repositoryT.save(novoTema));
	}
	
	public ResponseEntity<Tema> atualizarTema(Tema novoTema) {
		Optional<Tema> temaExistente = repositoryT.findByDescricaoIgnoreCase(novoTema.getDescricao());
		if (temaExistente.isPresent()) {
			return ResponseEntity.status(400).build();
		} else {
			return ResponseEntity.status(200).body(repositoryT.save(novoTema));
		}
				
	}
	
	public ResponseEntity<Tema> deletarTema (Long id) {
		if (repositoryT.existsById(id)) {
			repositoryT.deleteById(id);
		} else {
			return ResponseEntity.status(404).build();
		}
		return null;
	}

}
