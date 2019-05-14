package br.com.theweight.entidade.enums;

public enum StatusDoMes {

	MANTEVE(0, "Manteve"), ENGORDOU(1, "Engordou"), EMAGRECEU(2, "Emagreceu"); 
	
	private int codigo;
	private String descricao;

	private StatusDoMes(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	
}
