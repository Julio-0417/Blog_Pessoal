package org.generation.blogPessoal.controller;

/*Parte 1 criação da tabela Tema.

1. Camada de model com o nome de Tema com os atributos apresentados na
video-aula:
2. Uma camada de repository com o nome TemaRepository (com a
capacidade de se comunicar com o banco de dados mysql).
3. Uma camada de Controller com o nome de TemaController Com 5
endpoints:
● findAllTema = um endPoint com a capacidade de trazer todas os temas.
● findByIDTema = um endPoint com a função de trazer uma unico tema por id.
● findByDescricaoPostagem = um endPoint com a função de trazer um unico
tema por Descricao.
● postTema = um endPoint com a função de gravar um novo tema no banco de
dados.
● putTema = um endPoint com a função de atualizar dados de um tema.
● deleteTema = um endPoint com a função de apagar um tema do banco de
dados).*/

import java.util.List;

import org.generation.blogPessoal.model.Tema;
import org.generation.blogPessoal.service.TemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")		//habilita ver dados independente da origem e parametros
@RequestMapping("/tema")
public class TemaController {
	
	@Autowired
	private TemaService serviceT;
	
	@GetMapping("/todos")
	public ResponseEntity<List<Tema>> todosTemas(){
		return serviceT.todosTemas();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tema> buscaPorId(@PathVariable Long id){
		return serviceT.buscarPorId(id);
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Tema>> buscaPorNome(@PathVariable String nome){
		return serviceT.buscaPorNome(nome);
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<Tema> salvarTema (@RequestBody Tema novoTema){
		return serviceT.salvarTema(novoTema);
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<Tema> atualizarTema (@RequestBody Tema novoTema){
		return serviceT.atualizarTema(novoTema);
	}	
	
	@DeleteMapping(params = "id")
	public ResponseEntity<Tema> deletarTema(@RequestParam Long id) {
		return serviceT.deletarTema(id);
	}

}
