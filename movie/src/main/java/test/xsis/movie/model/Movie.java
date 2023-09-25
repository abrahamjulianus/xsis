package test.xsis.movie.model;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "rating")
	private float rating;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "created_at")
	private String created_at;
	
	@Column(name = "updated_at")
	private String updated_at;
	
	public Movie() {

	}

	public Movie(String title, String description, float rating, String created_at, String updated_at) {
		this.title = title;
		this.description = description;
		this.rating = rating;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	public long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	
	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", desc=" + description + ", rating=" + rating + ", image=" + image +", created_at=" + created_at +", updated_at=" + updated_at +"]";
	}
}
