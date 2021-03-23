package com.reyson.java.kubernetes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.reyson.java.kubernetes.domain.Glicemia;
import com.reyson.java.kubernetes.domain.Periodo;
import com.reyson.java.kubernetes.domain.Refeicao;
import com.reyson.java.kubernetes.persistence.GlicemiaRepository;
import com.reyson.java.kubernetes.service.GlicemiaService;

@SpringBootApplication
public class MonitoramentoGlicemiaDmgApplication {

    @Autowired
    private GlicemiaService service;
    
    @Autowired
    private GlicemiaRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(MonitoramentoGlicemiaDmgApplication.class, args);
    }

    @PostConstruct
    public void checkIfWorks() {
    	
    	repository.deleteAll();
    	
    	// Perfil diário de 4 pontos: jejum, pós-café, pós-almoço, pós-jantar
    	
        service.create(new Glicemia("Paciente 1",LocalDate.of(2021,03,22),Periodo.JEJUM.name(),null,BigDecimal.valueOf(82)));
        
        service.create(new Glicemia("Paciente 1",LocalDate.of(2021,03,22),Periodo.POS_PRANDIAL_1H.name(),Refeicao.LANCHE.getDescricao(),BigDecimal.valueOf(152.21)));
        
        service.create(new Glicemia("Paciente 1",LocalDate.of(2021,03,22),Periodo.POS_PRANDIAL_1H.name(),Refeicao.ALMOCO.getDescricao(),BigDecimal.valueOf(87)));
        
        service.create(new Glicemia("Paciente 1",LocalDate.of(2021,03,22),Periodo.POS_PRANDIAL_1H.name(),Refeicao.JANTAR.getDescricao(),BigDecimal.valueOf(97)));
        
        
        service.create(new Glicemia("Paciente 2",LocalDate.of(2021,03,22),Periodo.JEJUM.name(),null,BigDecimal.valueOf(95)));
        
        service.create(new Glicemia("Paciente 2",LocalDate.of(2021,03,22),Periodo.POS_PRANDIAL_1H.name(),Refeicao.LANCHE.getDescricao(),BigDecimal.valueOf(140)));
        
        service.create(new Glicemia("Paciente 2",LocalDate.of(2021,03,22),Periodo.POS_PRANDIAL_1H.name(),Refeicao.ALMOCO.getDescricao(),BigDecimal.valueOf(139.9)));
        
        service.create(new Glicemia("Paciente 2",LocalDate.of(2021,03,22),Periodo.POS_PRANDIAL_1H.name(),Refeicao.JANTAR.getDescricao(),BigDecimal.valueOf(120)));

        List<Glicemia> findAll = service.findAll();
        for (Glicemia glicemia : findAll) {
            System.out.println(glicemia.getId() + ":" + glicemia.getNome());
        }

    }

}
