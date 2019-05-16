package br.com.theweight.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import br.com.theweight.entidade.Pessoa;


public interface PessoaRepositorio extends Repository<Pessoa, Long>  {

	@Query("select p from Pessoa p")
	public Pessoa buscarUnicaPessoa();
	
	public Pessoa save(Pessoa pessoa);

	public Pessoa findById(Long id);
	
}
