package Modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jdk.jfr.Timestamp;
import modelo.Personaje;

/**
 *
 * @author Diurno
 */

/*
 * @Entity indica que esta clase es una entidad 
 * y se mapeará a una tabla en la base de datos
 */
@Entity

/*
 * @Table define el nombre de la tabla en la BD
 */
@Table(name = "pelicula")
public class Pelicula {

    /*
     * @Id indica que este atributo es la clave primaria
     * de la entidad
     */
    @Id
    @Column(name = "episode_id")
    private int episode_id;

    /*
     * Título de la película
     */
    @Column(name = "titulo")
    private String titulo;

    /*
     * Director de la película
     */
    @Column(name = "director")
    private String director;

    /*
     * Productor de la película
     */
    @Column(name = "productor")
    private String productor;

    /*
     * Fecha de lanzamiento de la película
     * Se almacena como LocalDate
     */
    @Timestamp
    @Column(name = "fecha_lanzamiento")
    private LocalDate fecha_lanzamiento;

    /*
     * Relación ManyToMany con la entidad Personaje
     * mappedBy indica que la relación se gestiona desde la clase Personaje, en el atributo "apariciones"
     */
    @ManyToMany(mappedBy = "apariciones")
    private List<Personaje> personajes;

    /*
     * Constructor vacío
     */
    public Pelicula() {}

    /*
     * Constructor que recibe solo el id
     */
    public Pelicula(int id) {
        this.episode_id = id;
    }

    /*
     * Constructor completo con lista de personajes
     */
    public Pelicula(int episode_id, String titulo, String director, String productor, LocalDate fecha_lanzamiento, ArrayList<Personaje> personajes) {
        this.episode_id = episode_id;
        this.titulo = titulo;
        this.director = director;
        this.productor = productor;
        this.fecha_lanzamiento = fecha_lanzamiento;
        this.personajes = personajes;
    }

    /*
     * Constructor sin personajes
     */
    public Pelicula(int episode_id, String titulo, String director, String productor, LocalDate fecha_lanzamiento) {
        this.episode_id = episode_id;
        this.titulo = titulo;
        this.director = director;
        this.productor = productor;
        this.fecha_lanzamiento = fecha_lanzamiento;
    }

    // Getters y Setters

    public int getEpisode_id() {
        return episode_id;
    }

    public void setEpisode_id(int episode_id) {
        this.episode_id = episode_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public LocalDate getFecha_lanzamiento() {
        return fecha_lanzamiento;
    }

    public void setFecha_lanzamiento(LocalDate fecha_lanzamiento) {
        this.fecha_lanzamiento = fecha_lanzamiento;
    }

    public List<Personaje> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(ArrayList<Personaje> personajes) {
        this.personajes = personajes;
    }

    /*
     * Método toString para mostrar la información
     * de la película en formato texto
     */
    @Override
    public String toString() {
        return "Pelicula{" + "episode_id=" + episode_id + ", titulo=" + titulo + ", director=" + director + ", productor=" + productor + ", fecha_lanzamiento=" + fecha_lanzamiento + '}';
    }
}
