package be.howest.sooa.o4.domain;

import java.util.Locale;
import java.util.Objects;

/**
 *
 * @author hayk
 */
public class Movie {

    private final long id;
    private String title;
    private int year;
    private Integer stars;
    private Genre genre;

    public Movie(long id, String title, int year, Integer stars) {
        this.title = title;
        this.year = year;
        this.stars = stars;
        this.id = id;
    }

    public Movie(String title, int year, Integer stars) {
        this(0L, title, year, stars);
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

    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }

    public Integer getStars() {
        return stars;
    }
    
    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.title.toLowerCase(Locale.ENGLISH));
        hash = 71 * hash + this.year;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Movie other = (Movie) obj;
        if (this.year != other.year) {
            return false;
        }
        return title.equalsIgnoreCase(other.title);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("(")
                .append(year).append(") ")
                .append(title).append(" ");
        int _stars = (stars == null) ? 0 : stars;
        for (int i = 0; i < _stars; i++) {
            sb.append("●");
        }
        for (int i = _stars; i < 5; i++) {
            sb.append("○");
        }
        return sb.toString();
    }

}
