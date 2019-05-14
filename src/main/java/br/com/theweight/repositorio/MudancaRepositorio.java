package br.com.theweight.repositorio;

import java.util.List;
import org.springframework.data.repository.Repository;
import br.com.theweight.entidade.Mudanca;

public interface MudancaRepositorio extends Repository<Mudanca, Long>  {

	public List<Mudanca> findAllOrderByData();
}
