package com.luvannie.springbootbookecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "unit_price")
    private double unitPrice;

    @Column(name = "quantity")
    private int quantity;

//    @OneToOne
//    @JoinColumn(name = "book_id")
    @Column(name = "book_id")
    private Long bookId;

//    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


}


