package test.xsis.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import test.xsis.movie.model.Movie;


@Repository
public interface MovieRepo extends JpaRepository<Movie, Long>{

	public List<Movie> findByTitleContaining(String title);
	
}
