package ir.maktabsharif.cinemawatchingsystem.model;

import ir.maktabsharif.cinemawatchingsystem.model.base.BaseModel;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "rates",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_rate_table",
                        columnNames = {"rate", "user_id", "movie_id"}
                )
        })
@EqualsAndHashCode(exclude = {"user", "movie"})
public class Rate extends BaseModel<Long> {
    @Column(name = "rate")
    private double rate;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(name = "fk_user_id")
    )
    private User user;

    @ManyToOne
    @JoinColumn(
            name = "movie_id",
            foreignKey = @ForeignKey(name = "fk_movie_id", value = ConstraintMode.CONSTRAINT,
                    foreignKeyDefinition = "foreign key (movie_id) references movies(id) on  delete cascade")
    )
    private Movie movie;

}
