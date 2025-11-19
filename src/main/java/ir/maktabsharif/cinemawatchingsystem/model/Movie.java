package ir.maktabsharif.cinemawatchingsystem.model;

import ir.maktabsharif.cinemawatchingsystem.model.base.BaseModel;
import ir.maktabsharif.cinemawatchingsystem.enums.Genre;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"rates", "comments"})
@SuperBuilder
public class Movie extends BaseModel<Long> {

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private Genre genre;

    @Column(name = "duration", nullable = false)
    private double duration;

    @Column(name="realease_date")
    private LocalDate releaseDate;

    @Column(name = "description")
    private String description;

//    @Lob
    @Column(name = "picture", columnDefinition = "TEXT")
    private String picture;

    @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER)
    private Set<Rate> rates = new HashSet<>();

    public double getRating(){
        return rates.stream()
                .mapToDouble(r -> r.getRate())
                .average()
                .orElse(Double.NaN);
    }
}
