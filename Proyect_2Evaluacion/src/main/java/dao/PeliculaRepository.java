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
    
    // EntityManager usado para trabajar con Hibernate
    EntityManager em;
    
    // Nombre de la colección en MongoDB donde se guardan las películas
    private String nomColeccion = "peliculas";

    /**
     * Constructor vacío.
     */
    public PeliculaRepository() {}
    
    /**
     * Constructor que recibe un EntityManager.
     * 
     * @param instance EntityManager ya creado en el conector Hibernate
     */
    public PeliculaRepository (EntityManager instance) {
        this.em = instance;
    }

    /**
     * Crea la colección "peliculas" dentro de la base de datos MongoDB.
     * 
     * @param db base de datos Mongo donde se crea la colección
     */
    public void crearColeccion (MongoDatabase db) {
        db.createCollection(nomColeccion);
    }

    /**
     * Inserta muchas películas de golpe en MongoDB.
     * 
     * Utiliza insertMany() para hacerlo en bloque
     * 
     * @param db base de datos Mongo
     * @param peliculas lista de películas a insertar
     */
    public void insertarMuchosMongo (MongoDatabase db, ArrayList<Pelicula> peliculas) {
        db.getCollection(nomColeccion, Pelicula.class).insertMany(peliculas);
    }
	
    /**
     * Obtiene todas las películas almacenadas en MongoDB.
     * 
     * @param db base de datos Mongo
     * @return ArrayList con todas las películas encontradas
     */
    public ArrayList <Pelicula> cogerPeliculasMongo (MongoDatabase db) {
        FindIterable<Pelicula> pelsMongo = db.getCollection(nomColeccion, Pelicula.class).find();
        ArrayList<Pelicula> peliculas = new ArrayList<>();

        for (Pelicula p : pelsMongo) {
            peliculas.add(p);
        }

        return peliculas;
    }
    
    /**
     * Inserta una película en la base de datos usando Hibernate
     * 
     * @param p película a insertar
     */
    public void insertarHib (Pelicula p) {
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }
    
    /**
     * Inserta en BD 3 películas nuevas correspondientes a los episodios 7, 8 y 9.
     */
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
    
    /**
     * Busca una película por su ID usando Hibernate.
     * 
     * @param id clave primaria de Pelicula (episode_id)
     * @return Pelicula encontrada o null si no existe
     */
    public Pelicula buscarPorIDHib(int id) {
        Pelicula p = em.find(Pelicula.class, id);
        
        return p;
    }
    
    /**
     * Obtiene los títulos de todas las películas almacenadas en BD,
     * ordenadas por episode_id.
     * 
     * @return array de Strings con los títulos de las películas
     */
    public String[] sacarTitulos () {
        List<Pelicula> pelis = em.createQuery("select p from Pelicula p order by p.episode_id", Pelicula.class).getResultList();
        String[] titulos = new String[pelis.size()];
        
        for (int i = 0; i < pelis.size(); i++) {
            titulos[i] = pelis.get(i).getTitulo();
        }
        return titulos;
    }
}
