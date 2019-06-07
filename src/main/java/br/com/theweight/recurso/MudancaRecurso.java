package br.com.theweight.recurso;

import java.net.URI;
import java.time.LocalDate;
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
import br.com.theweight.entidade.dto.MudancaPesoInputDTO;
import br.com.theweight.entidade.enums.StatusDoMes;
import br.com.theweight.repositorio.MudancaRepositorio;
import br.com.theweight.repositorio.PessoaRepositorio;

@RestController
@RequestMapping("/api/m")
public class MudancaRecurso {

	@Autowired
	private MudancaRepositorio mudancaRepositorio;
	
	@Autowired
	private PessoaRepositorio pessoaRepositorio;
	
	/***
	 * Compara o rendimento do mês anterior com o mês atual e retorna um {@link StatusDoMes}
	 * 
	 **/
	@GetMapping(value = "/situacao_mes", produces = MediaType.APPLICATION_JSON_VALUE)
	public StatusDoMes situacaoAtual(){
		List<Mudanca> pessoas = mudancaRepositorio.findAllByOrderByData();
		int total = pessoas.size();
		
		int pesoOld = 0;
		int pesoNow = 0;
		
		//Avalia os últimos dois resultados, avaliando se há muitos registros ou não.
		if(total > 1) {
			if(total == 2) { //se só exister duas opções, então a lógica deve ser get(0) e get(1)
				pesoOld = pessoas.get(0).getPeso();
				pesoNow = pessoas.get(1).getPeso();
			} else { //Se não pega os últimos valores, como a contagem inicia no zero, é preciso inserir o -1 pra pegar o valor correto. 
				pesoOld = pessoas.get(total -2).getPeso();
				pesoNow = pessoas.get(total -1).getPeso();
			}
		} else if (total == 1) {
			pesoOld = pessoaRepositorio.findFirstByOrderById().getPeso();
			pesoNow = pessoas.get(0).getPeso();
		}
		return isEngordado(pesoOld, pesoNow);
	}
	
	
	private StatusDoMes isEngordado(int pesoOld, int pesoNow) {
		StatusDoMes retorno = StatusDoMes.MANTEVE;
		if(pesoOld > pesoNow)
			retorno = StatusDoMes.EMAGRECEU;
		else if(pesoNow > pesoOld)
			retorno = StatusDoMes.ENGORDOU;
		
		return retorno;
	}
	
	@GetMapping(value = "/historico", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Mudanca> historico(){
		return mudancaRepositorio.findAllByOrderByData();
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Pessoa buscarMudancaPorId(@PathVariable Long id) {
		return mudancaRepositorio.findById(id);
	}
	
	//TODO: Creio que ele vai da conflito com o post de inserirPessoa, se caso acontecer, tem que colocar o value = ".."
	@Transactional
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Mudanca> inserirMudanca(@RequestBody Mudanca mudanca, UriComponentsBuilder uriBuilder){
		Mudanca mudancaRetorno = mudancaRepositorio.save(mudanca);
		
		URI path = uriBuilder.path("/api/m/{id}").buildAndExpand(mudancaRetorno.getId()).toUri();

		return ResponseEntity.created(path).body(mudancaRetorno);
	}
	
	@Transactional
	@PostMapping(value = "/hoje" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Mudanca> inserirMudancaHoje(@RequestBody MudancaPesoInputDTO mudanca, UriComponentsBuilder uriBuilder){
		Mudanca m = new Mudanca(mudanca.getPeso(), LocalDate.now());
		Mudanca mudancaRetorno = mudancaRepositorio.save(m);
		
		URI path = uriBuilder.path("/api/m/{id}").buildAndExpand(mudancaRetorno.getId()).toUri();

		return ResponseEntity.created(path).body(mudancaRetorno);
	}
	
	@Transactional
	@PostMapping(value = "excluir/{id}")
	public ResponseEntity<Void> excluirMudanca(@PathVariable Long id) {
		mudancaRepositorio.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@Transactional
	@PostMapping(value = "excluir/tudo")
	public ResponseEntity<Void> excluirTudo() {
		mudancaRepositorio.delete();
		return ResponseEntity.noContent().build();
	}

}
