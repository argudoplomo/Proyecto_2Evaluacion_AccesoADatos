/*
 * Clase Personaje
 * Representa la entidad "personaje" en la base de datos
 * usando JPA (Jakarta Persistence)
 */
package modelo;

import Modelo.Pelicula;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diurno
 */

/*
 * @Entity indica que esta clase es una entidad JPA
 * y se mapeará a una tabla de la base de datos
 */
@Entity

/*
 * @Table define el nombre de la tabla en la BD
 */
@Table(name = "personaje")
public class Personaje {

    /*
     * Clave primaria del personaje
     * Se genera automáticamente con autoincrement (IDENTITY)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /*
     * Nombre del personaje
     */
    @Column(name = "nombre")
    private String nombre;

    /*
     * Género del personaje
     */
    @Column(name = "genero")
    private String genero;

    /*
     * Lugar de nacimiento del personaje
     */
    @Column(name = "lugar_nacimiento")
    private String lugarNacimiento;

    /*
     * Año de nacimiento del personaje
     */
    @Column(name = "anio_nacimiento")
    private String anioNacimiento;

    /*
     * Altura del personaje (en cm)
     */
    @Column(name = "altura")
    private int altura;

    /*
     * Peso del personaje
     */
    @Column(name = "peso")
    private double peso;

    /*
     * Relación ManyToMany con Pelicula
     *
     * @JoinTable define la tabla intermedia "pelicula_personaje"
     * joinColumns -> columna que referencia a Personaje
     * inverseJoinColumns -> columna que referencia a Pelicula
     *
     * Esta es la entidad propietaria de la relación
     */
    @ManyToMany
    @JoinTable(
        name = "pelicula_personaje",
        joinColumns = @JoinColumn(
            name = "personaje_id",
            referencedColumnName = "id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "pelicula_id",
            referencedColumnName = "episode_id"
        )
    )
    private List<Pelicula> apariciones;

    /*
     * Constructor vacío obligatorio para JPA
     */
    public Personaje() {}

    /*
     * Constructor con solo el id
     */
    public Personaje(int id) {
        this.id = id;
    }

    /*
     * Constructor completo con lista de películas
     */
    public Personaje(int id, String nombre, String genero,
                     String lugarNacimiento, String anioNacimiento,
                     int altura, double peso,
                     ArrayList<Pelicula> apariciones) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.lugarNacimiento = lugarNacimiento;
        this.anioNacimiento = anioNacimiento;
        this.altura = altura;
        this.peso = peso;
        this.apariciones = apariciones;
    }

    /*
     * Constructor sin películas
     */
    public Personaje(int id, String nombre, String genero,
                     String lugarNacimiento, String anioNacimiento,
                     int altura, double peso) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.lugarNacimiento = lugarNacimiento;
        this.anioNacimiento = anioNacimiento;
        this.altura = altura;
        this.peso = peso;
    }

    /*
     * Constructor sin id (útil para insertar nuevos registros)
     */
    public Personaje(String nombre, String genero,
                     String lugarNacimiento, String anioNacimiento,
                     int altura, double peso,
                     List<Pelicula> apariciones) {
        this.nombre = nombre;
        this.genero = genero;
        this.lugarNacimiento = lugarNacimiento;
        this.anioNacimiento = anioNacimiento;
        this.altura = altura;
        this.peso = peso;
        this.apariciones = apariciones;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    public String getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(String anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public List<Pelicula> getApariciones() {
        return apariciones;
    }

    public void setApariciones(ArrayList<Pelicula> apariciones) {
        this.apariciones = apariciones;
    }

    /*
     * Método toString para mostrar los datos del personaje
     */
    @Override
    public String toString() {
        return "Personaje{" +
                "id=" + id +
                ", nombre=" + nombre +
                ", genero=" + genero +
                ", lugarNacimiento=" + lugarNacimiento +
                ", anioNacimiento=" + anioNacimiento +
                ", altura=" + altura +
                ", peso=" + peso +
                '}';
    }
}
