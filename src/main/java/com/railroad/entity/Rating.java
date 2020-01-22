package com.railroad.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "S_RATING")
public class Rating extends AbstractEntity {
    @NotNull(message = "rating cannot be null")
    private double rating;

    @Size(min = 1, max = 280, message = "Character count should be between 1 and 280")
    @NotEmpty(message = "please leave a comment")
    private String comment;

    @ManyToMany(mappedBy = "ratings")
    private Collection<User> users;

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
