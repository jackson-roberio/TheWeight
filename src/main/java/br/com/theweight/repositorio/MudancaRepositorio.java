package br.com.theweight.repositorio;

import java.util.List;
import org.springframework.data.repository.Repository;
import br.com.theweight.entidade.Mudanca;
import br.com.theweight.entidade.Pessoa;

public interface MudancaRepositorio extends Repository<Mudanca, Long>  {

	public List<Mudanca> findAllOrderByData();

	public Mudanca save(Mudanca mudanca);

	public Pessoa findById(Long id);
}
