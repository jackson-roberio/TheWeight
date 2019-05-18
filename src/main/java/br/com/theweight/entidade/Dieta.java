package br.com.theweight.entidade;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Dieta {

	@Id
	@GeneratedValue
	private Long id;
	
	private String refeicao;
	
	private List<String> recomendacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRefeicao() {
		return refeicao;
	}

	public void setRefeicao(String refeicao) {
		this.refeicao = refeicao;
	}

	public List<String> getRecomendacao() {
		return recomendacao;
	}

	public void setRecomendacao(List<String> recomendacao) {
		this.recomendacao = recomendacao;
	}
	
	
	
}
