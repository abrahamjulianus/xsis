package com.xsis.movies.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.xsis.movies.model.Movie;

@Repository
public interface MovieRepo extends CrudRepository<Movie, Long>{

	public List<Movie> findByNameContaining(String title);
	
}
