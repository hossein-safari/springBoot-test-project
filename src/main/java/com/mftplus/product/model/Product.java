package com.mftplus.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity(name = "productEntity")
@Table(name = "products")
@SQLDelete(sql = "UPDATE products SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$", message = "Invalid Name (only letters and spaces, 3-20 chars)")
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9\\s.,!?-]{3,255}$", message = "Invalid description (letters, numbers, punctuation, 3-255 chars)")
    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @Positive(message = "Price must be positive")
    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$", message = "Invalid user (only letters and spaces, 3-20 chars)")
    @Column(name = "username", length = 30, nullable = false)  // renamed to username to avoid SQL keyword conflict
    private String username;
}