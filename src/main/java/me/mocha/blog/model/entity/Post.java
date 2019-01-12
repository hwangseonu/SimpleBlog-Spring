package me.mocha.blog.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "posts")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @Column(nullable = false)
    @Getter
    private long id;

    @Column(nullable = false)
    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    @Getter
    private long views;

    @Getter
    @Column(nullable = false)
    @JoinColumn(name = "username")
    @ManyToOne
    private User user;

    public Post addViews(long i) {
        this.views += i;
        return this;
    }

}
