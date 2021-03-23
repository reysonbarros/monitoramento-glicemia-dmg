package com.reyson.java.kubernetes.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.reyson.java.kubernetes.domain.Glicemia;
import com.reyson.java.kubernetes.persistence.GlicemiaRepository;

@Service
public class GlicemiaService {
	
	 private GlicemiaRepository glicemiaRepository;
	 	

	    public GlicemiaService(GlicemiaRepository glicemiaRepository) {
	        this.glicemiaRepository = glicemiaRepository;
	    }
	    
	    @Transactional(propagation = Propagation.REQUIRED)
	    public Glicemia create(Glicemia glicemia) {	    		    	
	    	return glicemiaRepository.save(glicemia);
	    }

	    @Transactional(propagation = Propagation.SUPPORTS)
	    public Glicemia findById(Long id) {
	        return glicemiaRepository.findById(id).orElseThrow(() ->
	                new EntityNotFoundException("Glicemia não encontrada com id:" + id));
	    }

	    @Transactional(propagation = Propagation.SUPPORTS)
	    public List<Glicemia> findAll() {
	        List<Glicemia> pessoas = new ArrayList<>();
	        Iterator<Glicemia> iterator = glicemiaRepository.findAll().iterator();
	        iterator.forEachRemaining(pessoas::add);
	        return pessoas;
	    }

	    @Transactional(propagation = Propagation.REQUIRED)
	    public void delete(Glicemia glicemia) {
	        glicemiaRepository.delete(glicemia);
	    }

}
