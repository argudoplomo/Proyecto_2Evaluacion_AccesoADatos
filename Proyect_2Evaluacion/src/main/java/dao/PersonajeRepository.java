/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import modelo.Personaje;

/**
 *
 * @author Diurno
 */
public class PersonajeRepository {
    
    private EntityManager em;
    
    private String nomColeccion = "personajes";

    public PersonajeRepository() {}
    
    public PersonajeRepository (EntityManager instance) {
        this.em = instance;
    }

    public void crearColeccion (MongoDatabase db) {
        db.createCollection(nomColeccion);
    }

    public void insertarMuchosMongo (MongoDatabase db, ArrayList<Personaje> personajes) {
        db.getCollection(nomColeccion, Personaje.class).insertMany(personajes);
    }
				
    public ArrayList<Personaje> cogerPersonajesMongo (MongoDatabase db) {
        FindIterable<Personaje> persMongo = db.getCollection(nomColeccion, Personaje.class).find();
        ArrayList<Personaje> personajes = new ArrayList<>();

        for (Personaje p : persMongo) {
            personajes.add(p);
        }


        return personajes;
    }
    
    public void insertarHib (Personaje p) {
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }
    
    public String[] sacarTodosDeUnaPelicula (int episode_id) {
        List<Personaje> pers = em.createQuery("select per from Pelicula pel join pel.personajes per where pel.episode_id = :id", Personaje.class)
                .setParameter("id", episode_id).getResultList();
        String[] nombres = new String[pers.size()];
        
        for (int i = 0; i < nombres.length; i++) {
            nombres[i] = pers.get(i).getNombre();
        }
        
        return nombres;
    }
}