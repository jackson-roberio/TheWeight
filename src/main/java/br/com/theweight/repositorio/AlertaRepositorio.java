package br.com.theweight.repositorio;

import java.util.List;

import org.springframework.data.repository.Repository;

import br.com.theweight.entidade.Alerta;

public interface AlertaRepositorio extends Repository<Alerta, Long> {

	public Alerta save(Alerta alerta);
	
	public List<Alerta> findAllByOrderByHorario();

	public Alerta findById(Long id);
	
}
