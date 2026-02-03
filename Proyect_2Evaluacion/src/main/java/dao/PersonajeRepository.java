/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import modelo.Personaje;

/**
 *
 * @author Diurno
 */
public class PersonajeRepository {
    
    // EntityManager usado para trabajar con Hibernate
    EntityManager em;
    
    // Nombre de la colección en MongoDB donde se guardan los personajes
    private String nomColeccion = "personajes";

    /**
     * Constructor vacío.
     */
    public PersonajeRepository() {}
    
    /**
     * Constructor que recibe un EntityManager.
     * 
     * @param instance EntityManager ya creado en el conector Hibernate
     */
    public PersonajeRepository (EntityManager instance) {
        this.em = instance;
    }

    /**
     * Crea la colección "personajes" en MongoDB.
     * 
     * @param db base de datos Mongo
     */
    public void crearColeccion(MongoDatabase db) {
        db.createCollection(nomColeccion);
    }

    /**
     * Inserta muchos personajes en MongoDB.
     * 
     * @param db base de datos Mongo
     * @param personajes lista de personajes a insertar
     */
    public void insertarMuchosMongo(MongoDatabase db, ArrayList<Personaje> personajes) {
        db.getCollection(nomColeccion, Personaje.class).insertMany(personajes);
    }

    /**
     * Obtiene todos los personajes guardados en MongoDB.
     * 
     * @param db base de datos Mongo
     * @return lista de personajes
     */
    public ArrayList<Personaje> cogerPersonajesMongo(MongoDatabase db) {
        FindIterable<Personaje> persMongo = db.getCollection(nomColeccion, Personaje.class).find();
        ArrayList<Personaje> personajes = new ArrayList<>();

        for (Personaje p : persMongo) {
            personajes.add(p);
        }

        return personajes;
    }

    /**
     * Inserta o actualiza un personaje en la BD relacional usando Hibernate.
     * 
     * @param p personaje a insertar/actualizar
     */
    public void insertarHib(Personaje p) {
        em.getTransaction().begin();
        em.merge(p);
        em.getTransaction().commit();
    }

    /**
     * Devuelve los nombres de todos los personajes que aparecen en una película.
     * 
     * La consulta hace JOIN entre Pelicula y sus personajes.
     * 
     * @param episode_id id de la película
     * @return array de nombres de personajes
     */
    public String[] sacarTodosDeUnaPelicula(int episode_id) {
        List<Personaje> pers = em.createQuery(
                "select per from Pelicula pel join pel.personajes per where pel.episode_id = :id",
                Personaje.class
        ).setParameter("id", episode_id).getResultList();

        String[] nombres = new String[pers.size()];

        for (int i = 0; i < nombres.length; i++) {
            nombres[i] = pers.get(i).getNombre();
        }

        return nombres;
    }

    /**
    * Busca un personaje por su nombre en la base de datos usando Hibernate/JPA.
    * 
    * IMPORTANTE:
    * Si no existe ningún personaje con ese nombre, lanzará NoResultException.
    * 
    * @param nomPersonaje nombre del personaje a buscar
    * @return Personaje encontrado en la BD
    * @throws NoResultException si no hay ningún personaje con ese nombre
    */
    public Personaje buscarPorNombreHib(String nomPersonaje) throws NoResultException{
        Personaje p = em.createQuery(
                "select p from Personaje p where p.nombre = :name",
                Personaje.class
        ).setParameter("name", nomPersonaje).getSingleResult();

        return p;
    }
}