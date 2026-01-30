/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Modelo.Pelicula;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import modelo.Personaje;

/**
 *
 * @author Argudo_Plomo
 */
public class PeliculaRepository {
    MongoClient instance;
    private String nomColeccion = "peliculas";

    public PeliculaRepository (MongoClient instance) {
        this.instance = instance;
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
}
