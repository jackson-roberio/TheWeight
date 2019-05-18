package br.com.theweight.recurso;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.theweight.entidade.Dieta;
import br.com.theweight.repositorio.DietaRespositorio;

@RestController
@RequestMapping("/api/dieta")
public class DietaRecurso {

	@Autowired
	private DietaRespositorio repositorio;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Dieta> inserirDieta(@RequestBody Dieta dieta, UriComponentsBuilder uriBuilder){
		Dieta d = repositorio.save(dieta);
		
		URI path = uriBuilder.path("/api/dieta/{id}").buildAndExpand(d.getId()).toUri();

		return ResponseEntity.created(path).body(d);
	}
	
	
	@GetMapping("/{id}")
	public Dieta buscarPorId(@RequestBody Long id) {
		return repositorio.findById(id);
	}
	
	
	@PostMapping(value = "delete/{id}")
	public void deletarDieta(@RequestBody Long id) {
		repositorio.deleteById(id);
	}
	
	
	
	
	
}
