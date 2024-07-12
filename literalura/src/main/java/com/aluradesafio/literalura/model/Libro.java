package com.aluradesafio.literalura.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "libros")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @Column
    private Double descargas;
    @Column(length = 2)
    private String idioma;
    @ManyToOne
    @JoinColumn(name = "autorId")
    private Autor autor;

    //Generamos el ToString
    @Override
    public String toString() {
        return "Libro{" +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor.getNombre() + '\'' +
                ", idioma='" + idioma + '\'' +
                ", descargas=" + descargas +
                '}';
    }
}
