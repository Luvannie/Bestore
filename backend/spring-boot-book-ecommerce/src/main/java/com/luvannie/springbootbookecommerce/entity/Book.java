package com.luvannie.springbootbookecommerce.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@Builder
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "authors")
    private String author;
    @Column(name = "description")
    private String description;
    @Column(name = "publisher")
    private String publisher;
//    @Column(name = "categories")
//    private String categories;
    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private BookCategory category;
    @Column(name = "language")
    private String language;
    @Column(name = "thumbnail")
    private String thumbnail;
    @Column(name = "unit_price")
    private float unitPrice;
    @Column(name = "active")
    private boolean active;
    @Column(name = "units_in_stock")
    private int unitsInStock;
    @Column(name = "publishedDate")
    private Date publisedDate;
    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;
    @Column(name = "last_updated")
    @UpdateTimestamp
    private Date lastUpdated;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "book",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Favorite> favorites = new ArrayList<>();

}
