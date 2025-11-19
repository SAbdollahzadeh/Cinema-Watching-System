package ir.maktabsharif.cinemawatchingsystem.model;

import ir.maktabsharif.cinemawatchingsystem.enums.UserLevel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
@DiscriminatorValue("regular_user")
@EqualsAndHashCode(callSuper=true, exclude = {"movies", "rates", "comments"})
@SuperBuilder
public class RegularUser extends User{

    @Enumerated(EnumType.STRING)
    private UserLevel userLevel;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_movie",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseForeignKey = @ForeignKey(name = "fk_movie_id", value = ConstraintMode.CONSTRAINT,
                    foreignKeyDefinition = "foreign key (movie_id) references movies(id) on  delete cascade")
    )
    private Set<Movie> movies = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Rate> rates = new HashSet<>();
}
