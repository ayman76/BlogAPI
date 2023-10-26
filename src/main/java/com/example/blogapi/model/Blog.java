package com.example.blogapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "blogs")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String slug;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private AppUser user;
    @CreationTimestamp
    private Date createdDate;

    @OneToMany(mappedBy = "blog", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tags_blogs", joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private List<Tag> tags;
}
