package br.com.theweight.recurso;

import java.net.URI;
import java.util.List;

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

import br.com.theweight.entidade.Alerta;
import br.com.theweight.repositorio.AlertaRepositorio;

@RestController
@RequestMapping("/api/alerta")
public class AlertaRecurso {

	@Autowired
	private AlertaRepositorio repositorio;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Alerta> inserirAlerta(@RequestBody Alerta alerta, UriComponentsBuilder uriBuilder){
		Alerta a = repositorio.save(alerta);
		URI path = uriBuilder.path("/api/alerta/{id}").buildAndExpand(a.getId()).toUri();

		return ResponseEntity.created(path).body(a);
	}
	
	
	@GetMapping(value = "/{id}")
	public Alerta alertaById(@PathVariable Long id) {
		return repositorio.findById(id);
	}
	
	@GetMapping(value = "/todos")
	public List<Alerta> todos(){
		return repositorio.findAllByOrderByHorario();
	}
	
}
