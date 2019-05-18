package br.com.theweight.repositorio;

import org.springframework.data.repository.Repository;

import br.com.theweight.entidade.Dieta;

public interface DietaRespositorio extends Repository<Dieta, Long> {

	public Dieta save(Dieta dieta);
	

	public Dieta findById(Long id);


	public void deleteById(Long id);
	
}
