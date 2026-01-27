/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import modelo.Personaje;

/**
 *
 * @author Diurno
 */
public class PersonajeRepository {
    MongoClient instance;
    private String nomColeccion = "personajes";

    public PersonajeRepository (MongoClient instance) {
        this.instance = instance;
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
}