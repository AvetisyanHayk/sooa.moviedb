package be.howest.sooa.o4.domain;

import java.util.Locale;
import java.util.Objects;

/**
 *
 * @author hayk
 */
public class Movie {

    private final long id;
    private final String title;
    private final int year;
    private final int stars;

    public Movie(long id, String title, int year, int stars) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.stars = stars;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public int getStars() {
        return stars;
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
        for (int i = 0; i < stars; i++) {
            sb.append("●");
        }
        for (int i = stars; i < 5; i++) {
            sb.append("○");
        }
        return sb.toString();
    }

}
