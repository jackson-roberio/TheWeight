package br.com.theweight.repositorio;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;

import br.com.theweight.entidade.Mudanca;
import br.com.theweight.entidade.Pessoa;

public interface MudancaRepositorio extends Repository<Mudanca, Long>  {

	public List<Mudanca> findAllByOrderByData();

	public List<Mudanca> findAll(Sort sort);
	
	public Mudanca save(Mudanca mudanca);
	
	public void deleteById(Long id);
	
	public void delete();

	public Pessoa findById(Long id);
}
