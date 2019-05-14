package br.com.theweight.recurso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	//TODO: Preciso implementar os dois Post's, para inclusão de novo usuário e para inclusão de novo peso.
}
