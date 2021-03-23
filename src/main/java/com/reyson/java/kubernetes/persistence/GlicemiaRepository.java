package com.reyson.java.kubernetes.persistence;

import org.springframework.data.repository.CrudRepository;

import com.reyson.java.kubernetes.domain.Glicemia;

public interface GlicemiaRepository extends CrudRepository<Glicemia, Long> {	

}
