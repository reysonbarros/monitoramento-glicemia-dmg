package com.reyson.java.kubernetes.domain;

public enum Refeicao {
		
	LANCHE("Lanche"),
	ALMOCO("Almoço"),
	JANTAR("Jantar");

	private final String descricao;

	Refeicao(String param){
		descricao = param;
	}
	
	public String getDescricao(){
		return descricao;
	}	

}
