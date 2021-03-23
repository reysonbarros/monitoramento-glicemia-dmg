package com.reyson.java.kubernetes.util;

import java.math.BigDecimal;

import com.reyson.java.kubernetes.domain.NivelGlicose;
import com.reyson.java.kubernetes.domain.Periodo;

public class ControladorGlicose {
	 
	 public String nivelGlicose(String periodo, BigDecimal valor) {
			String nivel = NivelGlicose.NORMAL.name();
			
	 		if(periodo.equals(Periodo.JEJUM.name())) {
	 			if(valor.compareTo(Periodo.JEJUM.getValorReferencia()) != -1) {	 				
	 				nivel = NivelGlicose.ALTERADO.name();
	 			}
	 		}
	 		
	 		if(periodo.contains(Periodo.POS_PRANDIAL_1H.name())) {
	 			if(valor.compareTo(Periodo.POS_PRANDIAL_1H.getValorReferencia()) != -1) {	 				
	 				nivel = NivelGlicose.ALTERADO.name();
	 			}
	 		}
	 		
	 		if(periodo.contains(Periodo.POS_PRANDIAL_2H.name())) {
	 			if(valor.compareTo(Periodo.POS_PRANDIAL_2H.getValorReferencia()) != -1) {	 				
	 				nivel = NivelGlicose.ALTERADO.name();
	 			}
	 		}
	 		return nivel;
	 }
	
}
