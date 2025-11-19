package ir.maktabsharif.cinemawatchingsystem.model;

import ir.maktabsharif.cinemawatchingsystem.model.base.BaseModel;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(exclude = {})
@Table(name = "comments",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_comments_table",
                        columnNames = {"user_id", "movie_id", "comment"}
                )
        })
public class Comment extends BaseModel<Long> {

    @Column(name = "comment", nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(name = "fk_user_id")
    )
    private RegularUser user;

    @ManyToOne
    @JoinColumn(
            name = "movie_id",
            foreignKey = @ForeignKey(name = "fk_movie_id", value = ConstraintMode.CONSTRAINT,
                    foreignKeyDefinition = "foreign key (movie_id) references movies(id) on  delete cascade")
    )
    private Movie movie;
}
