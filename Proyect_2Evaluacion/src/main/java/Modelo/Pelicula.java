/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import modelo.Personaje;

/**
 *
 * @author Diurno
 */
public class Pelicula {
				
    private int episode_id;
    private String titulo;
    private String director;
    private String productor;
    private LocalDate fecha_lanzamiento;
				
				private ArrayList<Personaje> personajes;

				public Pelicula () {}
				
				public Pelicula (int id) {
								this.episode_id = id;
				}
				
    public Pelicula(int episode_id, String titulo, String director, String productor, LocalDate fecha_lanzamiento, ArrayList<Personaje> personajes) {
        this.episode_id = episode_id;
        this.titulo = titulo;
        this.director = director;
        this.productor = productor;
        this.fecha_lanzamiento = fecha_lanzamiento;
								this.personajes = personajes;
    }

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

				public ArrayList<Personaje> getPersonajes() {
								return personajes;
				}

				public void setPersonajes(ArrayList<Personaje> personajes) {
								this.personajes = personajes;
				}

    @Override
    public String toString() {
        return "Pelicula{" + "episode_id=" + episode_id + ", titulo=" + titulo + ", director=" + director + ", productor=" + productor + ", fecha_lanzamiento=" + fecha_lanzamiento + '}';
    }
    
}
