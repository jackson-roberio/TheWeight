package br.com.theweight.entidade;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Mudanca {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mudanca_sequence")
//	@SequenceGenerator(name="mudanca_sequence", sequenceName = "mudanca_sequence", allocationSize=1)
	private Long id;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalDate data;
	
	private int peso;

	@Deprecated
	public Mudanca() {}
	
	
	
	public Mudanca(int peso, LocalDate data) {
		this.peso = peso;
		this.data = data;
	}
	
	public Long getId() {
		return id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	
	
	
}
