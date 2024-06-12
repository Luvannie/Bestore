package com.luvannie.springbootbookecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "complains")
public class Complain extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "complain")
    private String complain;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "is_finish")
    private boolean isFinish;
}
