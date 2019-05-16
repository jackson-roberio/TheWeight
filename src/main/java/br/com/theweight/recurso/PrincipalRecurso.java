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
import org.springframework.web.util.UriComponentsBuilder;

import br.com.theweight.entidade.Mudanca;
import br.com.theweight.entidade.Pessoa;
import br.com.theweight.entidade.enums.StatusDoMes;
import br.com.theweight.repositorio.MudancaRepositorio;
import br.com.theweight.repositorio.PessoaRepositorio;

@RequestMapping("/api")
public class PrincipalRecurso {

	@Autowired
	private PessoaRepositorio pessoaRepositorio;
	
	@Autowired
	private MudancaRepositorio mudancaRepositorio;
	
	
	
	@GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public Pessoa temCadastro(){
		 return pessoaRepositorio.buscarUnicaPessoa();
	}
	
	/***
	 * Compara o rendimento do mês anterior com o mês atual e retorna um {@link StatusDoMes}
	 * 
	 **/
	@GetMapping(value = "/situacao_mes", produces = MediaType.APPLICATION_JSON_VALUE)
	public StatusDoMes situacaoAtual(){
		List<Mudanca> pessoas = mudancaRepositorio.findAllOrderByData();
		int total = pessoas.size();
		StatusDoMes retorno = StatusDoMes.MANTEVE;
		
		if(pessoas.size() > 2) {
			int pesoOld = pessoas.get(total -1).getPeso();
			int pesoNow = pessoas.get(total).getPeso();
			 
			if(pesoOld > pesoNow)
				retorno = StatusDoMes.EMAGRECEU;
			else if(pesoNow > pesoOld)
				retorno = StatusDoMes.ENGORDOU;
			
		}
		return retorno;
	}
	
	//TODO: Trocar por ResponseEntity
	@GetMapping(value = "/historico", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Mudanca> historico(){
		return mudancaRepositorio.findAllOrderByData();
	}
	
	@GetMapping(value = "/pessoa/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Pessoa buscarPessoaPorId(@PathVariable Long id) {
		return pessoaRepositorio.findById(id);
	}
	
	@GetMapping(value = "/mudanca/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Pessoa buscarMudancaPorId(@PathVariable Long id) {
		return mudancaRepositorio.findById(id);
	}
	
	
	@Transactional
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pessoa> inserirPessoa(@RequestBody Pessoa pessoa, UriComponentsBuilder uriBuilder){
		Pessoa pessoaRetorno = pessoaRepositorio.save(pessoa);
		
		URI path = uriBuilder.path("/api/pessoa/{id}").buildAndExpand(pessoaRetorno.getId()).toUri();

		return ResponseEntity.created(path).body(pessoaRetorno);
	}
	
	
	//TODO: Creio que ele vai da conflito com o post de inserirPessoa, se caso acontecer, tem que colocar o value = ".."
	@Transactional
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Mudanca> inserirMudanca(@RequestBody Mudanca mudanca, UriComponentsBuilder uriBuilder){
		Mudanca mudancaRetorno = mudancaRepositorio.save(mudanca);
		
		URI path = uriBuilder.path("/api/mudanca/{id}").buildAndExpand(mudancaRetorno.getId()).toUri();

		return ResponseEntity.created(path).body(mudancaRetorno);
	}
	
}
