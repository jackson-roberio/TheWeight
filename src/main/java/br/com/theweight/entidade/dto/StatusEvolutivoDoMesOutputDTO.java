package br.com.theweight.entidade.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.theweight.entidade.enums.StatusDoMes;

public class StatusEvolutivoDoMesOutputDTO {

	@Enumerated(EnumType.STRING)
	private StatusDoMes status;

	public StatusDoMes getStatus() {
		return status;
	}
	
}
