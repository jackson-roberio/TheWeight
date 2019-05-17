package br.com.theweight.recurso;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.theweight.entidade.Mudanca;
import br.com.theweight.entidade.Pessoa;
import br.com.theweight.entidade.enums.StatusDoMes;
import br.com.theweight.repositorio.MudancaRepositorio;
import br.com.theweight.repositorio.PessoaRepositorio;

@RestController
@RequestMapping("/api/p")
public class PrincipalRecurso {

	@Autowired
	private PessoaRepositorio pessoaRepositorio;
	
	
	
	
	
	@GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public Pessoa temCadastro(){
		 return pessoaRepositorio.findFirstByOrderById();
	}
	
	@GetMapping(value = "/pessoa/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Pessoa buscarPessoaPorId(@PathVariable Long id) {
		return pessoaRepositorio.findById(id);
	}
	
	@Transactional
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pessoa> inserirPessoa(@RequestBody Pessoa pessoa, UriComponentsBuilder uriBuilder){
		Pessoa pessoaRetorno = pessoaRepositorio.save(pessoa);
		
		URI path = uriBuilder.path("/api/pessoa/{id}").buildAndExpand(pessoaRetorno.getId()).toUri();

		return ResponseEntity.created(path).body(pessoaRetorno);
	}
	
	
	
	
}
