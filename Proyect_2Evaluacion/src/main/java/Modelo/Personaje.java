/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import Modelo.Pelicula;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diurno
 */
@Entity
@Table(name = "personaje")
public class Personaje {
	
    @Id
    @Column(name = "id")
    private int id;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "genero")
    private String genero;
    
    @Column(name = "lugar_nacimiento")
    private String lugarNacimiento;
    
    @Column(name = "anio_nacimiento")
    private String anioNacimiento;
    
    @Column(name = "altura")
    private int altura;
    
    @Column(name = "peso")
    private double peso;
			
    @ManyToMany(mappedBy = "personajes")
    private List<Pelicula> apariciones;


    public Personaje () {}
				
    public Personaje (int id) {
        this.id = id;
    }

    public Personaje(int id, String nombre, String genero, String lugarNacimiento, String anioNacimiento, int altura, double peso, ArrayList<Pelicula> apariciones) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.lugarNacimiento = lugarNacimiento;
        this.anioNacimiento = anioNacimiento;
        this.altura = altura;
        this.peso = peso;
        this.apariciones = apariciones;
    }

    public Personaje(int id, String nombre, String genero, String lugarNacimiento, String anioNacimiento, int altura, double peso) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.lugarNacimiento = lugarNacimiento;
        this.anioNacimiento = anioNacimiento;
        this.altura = altura;
        this.peso = peso;
    }

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

    @Override
    public String toString() {
        return "Personaje{" + "id=" + id + ", nombre=" + nombre + ", genero=" + genero + ", lugarNacimiento=" + lugarNacimiento + ", anioNacimiento=" + anioNacimiento + ", altura=" + altura + ", peso=" + peso + '}';
    }

}
