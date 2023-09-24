package com.xsis.movies.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xsis.movies.model.Movie;
import com.xsis.movies.repository.MovieRepo;

@RestController
@RequestMapping("/Movies")
public class MovieController {
 
    @Autowired
    private MovieRepo movieRepo;
        
    @GetMapping   
    public ResponseEntity<List<Movie>> findAll(
            @RequestParam(required = false, 
                    defaultValue = "") String title) {
        try {
            List<Movie> movies;
            if (StringUtils.hasText(title)) {
            	movies = movieRepo.findByNameContaining(title);
            } else {
            	movies = (List<Movie>) movieRepo.findAll();
            }

            if (movies.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Movie> findMovieById(@PathVariable(value = "id") long id) {
    	Optional<Movie> movie = movieRepo.findById(id);
    	 
        if(movie.isPresent()) {
            return ResponseEntity.ok().body(movie.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
     
    @PostMapping
    public ResponseEntity<Movie> create(@Validated @RequestBody Movie movie) {
    	try {
    		return new ResponseEntity<>(movieRepo.save(movie), HttpStatus.CREATED);
    	} catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable("id") long id, @RequestBody Movie movie) {

        Optional<Movie> movieData = movieRepo.findById(id);

        if (movieData.isPresent()) {
            Movie updatedMovie = movieData.get();
            updatedMovie.setTitle(movie.getTitle());
            updatedMovie.setDescription(movie.getDescription());
            updatedMovie.setImage(movie.getImage());
            updatedMovie.setRating(movie.getRating());
            updatedMovie.setUpdated_at(movie.getUpdated_at());
            return new ResponseEntity<>(movieRepo.save(updatedMovie), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
        try {
            movieRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<HttpStatus> deleteAll() {
        try {
        	movieRepo.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}