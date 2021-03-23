package com.reyson.java.kubernetes.domain;

import java.math.BigDecimal;

// Metas para o Controle Glicêmico(pag 33 da publicação)
public enum Periodo {	
	
	JEJUM(new BigDecimal(95)),	
	POS_PRANDIAL_1H(new BigDecimal(140)),
	POS_PRANDIAL_2H(new BigDecimal(120));	

	private final BigDecimal valorReferencia;

	Periodo(BigDecimal value){
		valorReferencia = value;
	}
	
	public BigDecimal getValorReferencia(){
		return valorReferencia;
	}	

}
