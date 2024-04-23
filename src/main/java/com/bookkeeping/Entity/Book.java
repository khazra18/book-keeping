package com.bookkeeping.Entity;


import com.bookkeeping.validationDTO.ValidClassification;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Book")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Name must not be empty")
    @Size(min = 5)
    private String name;

    @Size(max = 250)
    private String description;
    private String author;

    @NotNull(message = "Price must not be null")
    private Double price;

    @NotBlank(message = "ISBN must not be empty")
    @Pattern(regexp = "^[A-Za-z]{3}-\\d{4}$",
            message = "Pattern mismatch (First 3 letters should be alphabet then followed by - and then followed by 4 numbers)")
    private String isbn;

    @ValidClassification(enumClass = Classification.class)
    @Enumerated(EnumType.STRING)
    private Classification classification;

}
