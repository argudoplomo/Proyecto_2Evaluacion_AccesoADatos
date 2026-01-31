/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Modelo.Pelicula;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.Personaje;

/**
 *
 * @author Argudo_Plomo
 */
public class PeliculaRepository {
    EntityManager em;
    private String nomColeccion = "peliculas";

    public PeliculaRepository() {}
    
    public PeliculaRepository (EntityManager instance) {
        this.em = instance;
    }

    public void crearColeccion (MongoDatabase db) {
        db.createCollection(nomColeccion);
    }

    public void insertarMuchosMongo (MongoDatabase db, ArrayList<Pelicula> peliculas) {
        db.getCollection(nomColeccion, Pelicula.class).insertMany(peliculas);
    }
				
    public ArrayList <Pelicula> cogerPeliculasMongo (MongoDatabase db) {
        FindIterable<Pelicula> pelsMongo = db.getCollection(nomColeccion, Pelicula.class).find();
        ArrayList<Pelicula> peliculas = new ArrayList<>();

        for (Pelicula p : pelsMongo) {
            peliculas.add(p);
        }

        return peliculas;
    }
    
    public void insertarHib (Pelicula p) {
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }
    
    public void insertarSecuelasHib () {
        em.getTransaction().begin();
        
        Pelicula episode7 = new Pelicula(7, "The Force Awakens", "J. J. Abrams", "Kathleen Kennedy", LocalDate.of(2015, 12, 18));
        Pelicula episode8 = new Pelicula(8, "The Last Jedi", "Rian Johnson", "Kathleen Kennedy", LocalDate.of(2017, 12, 15));
        Pelicula episode9 = new Pelicula(9, "The Rise of Skywalker", "J. J. Abrams", "Kathleen Kennedy", LocalDate.of(2019, 12, 20));
        
        em.persist(episode7);
        em.persist(episode8);
        em.persist(episode9);
        
        em.getTransaction().commit();
    }
    
    public Pelicula buscarPorIDHib(int id) {
        Pelicula p = em.find(Pelicula.class, id);
        
        return p;
    }
    
    public String[] sacarTitulos () {
        List<Pelicula> pelis = em.createQuery("select p from Pelicula p order by p.episode_id", Pelicula.class).getResultList();
        String[] titulos = new String[pelis.size()];
        
        for (int i = 0; i < pelis.size(); i++) {
            titulos[i] = pelis.get(i).getTitulo();
        }
        return titulos;
    }
}
