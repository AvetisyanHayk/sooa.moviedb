package be.howest.sooa.o4.domain;

import java.util.Locale;
import java.util.Objects;

/**
 *
 * @author hayk
 */
public class Genre {
    private final long id;
    private String name;

    public Genre(long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Genre(String name) {
        this(0, name);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.name.toLowerCase(Locale.ENGLISH));
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
        final Genre other = (Genre) obj;
        return name.equalsIgnoreCase(other.name);
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    
}
