package test.xsis.movie.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import test.xsis.movie.model.Movie;
import test.xsis.movie.repository.MovieRepo;

@RestController
@RequestMapping("/Movies")
public class MovieController {
 
    @Autowired
    private MovieRepo movieRepo;
    
    @Autowired
    public void setMovieRepo(MovieRepo movieRepo) {
        this.movieRepo = movieRepo;
    }
     
    @RequestMapping(value = "/", method= RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Movie>> getAllMovies(@RequestParam(required = false) String title) {
        try {
          List<Movie> movies = new ArrayList<Movie>();

          if (title == null)
        	  movieRepo.findAll().forEach(movies::add);
          else
        	  movieRepo.findByTitleContaining(title).forEach(movies::add);

          if (movies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }

          return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }    
               
    @RequestMapping(value = "/{id}", method= RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") long id) {
      Optional<Movie> movieData = movieRepo.findById(id);

      if (movieData.isPresent()) {
        return new ResponseEntity<>(movieData.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
     
    // @PostMapping("/")
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
      try {
    	  Movie _movie = movieRepo.save(new Movie(movie.getTitle(), movie.getDescription(), movie.getRating(), movie.getCreated_at(), movie.getUpdated_at()));
        return new ResponseEntity<>(_movie, HttpStatus.CREATED);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
        
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable("id") long id, @RequestBody Movie movie) {
      Optional<Movie> movieData = movieRepo.findById(id);

      if (movieData.isPresent()) {
    	  Movie _movie = movieData.get();
	        _movie.setTitle(movie.getTitle());
	        _movie.setDescription(movie.getDescription());
	        _movie.setRating(movie.getRating());
	        _movie.setImage(movie.getImage());
	        _movie.setCreated_at(movie.getCreated_at());
	        _movie.setUpdated_at(movie.getUpdated_at());
	        return new ResponseEntity<>(movieRepo.save(_movie), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }

    @DeleteMapping("/{id}")    
    public ResponseEntity<HttpStatus> deleteMovie(@PathVariable("id") long id) {
      try {
    	  movieRepo.deleteById(id);
    	  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } catch (Exception e) {
    	  return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @DeleteMapping("/")
    public ResponseEntity<HttpStatus> deleteAllMovies() {
        try {
        	movieRepo.deleteAll();
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
        	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

      }
}