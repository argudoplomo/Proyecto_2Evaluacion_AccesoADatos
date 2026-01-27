/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Pelicula;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import dao.PeliculaRepository;
import dao.PersonajeRepository;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Personaje;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

/**
 *
 * @author Diurno
 */
public class MongoConnector {
				
    private static MongoClient instance = null;
				
    private PersonajeRepository pr;
    private PeliculaRepository pelr;
				
    //PojoCodecProvider
    PojoCodecProvider pojo = PojoCodecProvider.builder().automatic(true).build();  
    //CodecRegistry
    CodecRegistry codec = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(pojo));
				
    public MongoConnector(String dbUri) throws InstantiationException {
        if (instance == null) {
            instance = MongoClients.create(dbUri);
            pr = new PersonajeRepository(getInstance());
            pelr = new PeliculaRepository(getInstance());
        } else {
            throw new InstantiationException("Connector Instance Already Exists");
        }
    }

    public MongoClient getInstance() {
        return instance;
    }

    public void listDatabases() {
        try {
            List<Document> databases = instance.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.get("name")));
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }

    public MongoDatabase createDB(String dbName) {
        return instance.getDatabase(dbName).withCodecRegistry(codec);
    }

    public void createCollectionPersonajes(MongoDatabase db) {
        pr.crearColeccion(db);
    }
				
    public void createCollectionPeliculas(MongoDatabase db) {
        pelr.crearColeccion(db);
    }

    public void printCollectionNames(MongoDatabase db) {
        List<String> collections = db.listCollectionNames().into(new ArrayList<>());
        collections.forEach(name -> System.out.println(name));
    }
				
    public void insertarPersonajes (MongoDatabase db, ArrayList<Personaje> personajes) {
        pr.insertarMuchosMongo(db, personajes);
    }
    
    public void insertarPeliculas (MongoDatabase db, ArrayList<Pelicula> peliculas) {
        pelr.insertarMuchosMongo(db, peliculas);
    }
				
				public ArrayList<Personaje> cogerPersonajes (MongoDatabase db) {
								return pr.cogerPersonajesMongo(db);
				}
				
				public ArrayList<Pelicula> cogerPeliculas (MongoDatabase db) {
								return pelr.cogerPeliculasMongo(db);
				}
				
}
