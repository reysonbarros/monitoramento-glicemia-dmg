package com.reyson.java.kubernetes.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.reyson.java.kubernetes.util.ControladorGlicose;

@Entity(name="glicemia")
public class Glicemia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Long id;
	
	@NotNull
	private String nome;
	
	@NotNull
	private LocalDate data;
	
	@NotNull
	private String periodo;
	
	private String refeicao;
	
	@NotNull
	private BigDecimal valor;	
	
	@Transient
	private String status;	
	
	public Glicemia() {
		
	}
	
	public Glicemia(@NotNull String nome, @NotNull LocalDate data, @NotNull String periodo, String refeicao, @NotNull BigDecimal valor) {
		this.nome = nome;
		this.data = data;
		this.periodo = periodo;
		this.refeicao = refeicao;
		this.valor = valor;		
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public LocalDate getData() {
		return data;
	}
	
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	public String getPeriodo() {
		return periodo;
	}
	
	public void setRefeicao(String refeicao) {
		this.refeicao = refeicao;
	}
	
	public String getRefeicao() {
		return refeicao;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public BigDecimal getValor() {
		return valor;
	}	
	
	public String getStatus() {
		if (status == null) {
			ControladorGlicose cg = new ControladorGlicose();	    	
			status = cg.nivelGlicose(periodo, valor);
		}
		return status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Glicemia other = (Glicemia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
