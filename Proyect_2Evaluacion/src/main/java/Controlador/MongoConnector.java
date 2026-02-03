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
 * Clase que actúa como conector entre la aplicación y MongoDB.
 * 
 * Usa un patrón Singleton para mantener una única instancia de MongoClient.
 * Además, utiliza PojoCodecProvider para poder guardar y recuperar objetos
 * Java (POJOs) directamente en MongoDB sin tener que convertirlos manualmente.
 */
public class MongoConnector {
	
    // Instancia única del cliente MongoDB 
    private static MongoClient instance = null;
				
    private PersonajeRepository pr;
    private PeliculaRepository pelr;
				
    //PojoCodecProvider
    PojoCodecProvider pojo = PojoCodecProvider.builder().automatic(true).build();  
    //CodecRegistry
    CodecRegistry codec = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(pojo));

    /**
     * Constructor:
     * Crea la conexión con MongoDB.
     * 
     * @param dbUri URI de conexión a MongoDB (ej: mongodb://localhost:27017)
     */
    public MongoConnector(String dbUri) {
        if (instance == null) {
            instance = MongoClients.create(dbUri);
            pr = new PersonajeRepository();
            pelr = new PeliculaRepository();
        }
    }

    /**
     * Devuelve la instancia única del cliente MongoDB.
     * 
     * @return MongoClient (instancia Singleton)
     */
    public MongoClient getInstance() {
        return instance;
    }

    /**
     * Lista todas las bases de datos disponibles en el servidor MongoDB.
     * Imprime sus nombres por consola.
     */
    public void listDatabases() {
        try {
            List<Document> databases = instance.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.get("name")));
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }

    /**
     * Crea o accede a una base de datos en MongoDB y le aplica el codecRegistry.
     * 
     * @param dbName nombre de la base de datos
     * @return MongoDatabase con el codecRegistry aplicado
     */
    public MongoDatabase createDB(String dbName) {
        return instance.getDatabase(dbName).withCodecRegistry(codec);
    }

    /**
     * Crea la colección de personajes en la base de datos.
     * Delegando la creación al repositorio PersonajeRepository.
     * 
     * @param db base de datos donde se creará la colección
     */
    public void createCollectionPersonajes(MongoDatabase db) {
        pr.crearColeccion(db);
    }
	
     /**
     * Crea la colección de películas en la base de datos.
     * Delegando la creación al repositorio PeliculaRepository.
     * 
     * @param db base de datos donde se creará la colección
     */
    public void createCollectionPeliculas(MongoDatabase db) {
        pelr.crearColeccion(db);
    }

    /**
     * Muestra por consola el nombre de todas las colecciones existentes en una BD.
     * 
     * @param db base de datos de la cual se quieren listar las colecciones
     */
    public void printCollectionNames(MongoDatabase db) {
        List<String> collections = db.listCollectionNames().into(new ArrayList<>());
        collections.forEach(name -> System.out.println(name));
    }
	
     /**
     * Inserta una lista de personajes en MongoDB.
     * Delegando el insert al repositorio de personajes.
     * 
     * @param db base de datos donde se insertarán los personajes
     * @param personajes lista de personajes a insertar
     */
    public void insertarPersonajes (MongoDatabase db, ArrayList<Personaje> personajes) {
        pr.insertarMuchosMongo(db, personajes);
    }
    
     /**
     * Inserta una lista de películas en MongoDB.
     * Delegando el insert al repositorio de películas.
     * 
     * @param db base de datos donde se insertarán las películas
     * @param peliculas lista de películas a insertar
     */
    public void insertarPeliculas (MongoDatabase db, ArrayList<Pelicula> peliculas) {
        pelr.insertarMuchosMongo(db, peliculas);
    }
				
    /**
     * Recupera todos los personajes almacenados en MongoDB.
     * 
     * @param db base de datos desde donde se obtendrán los personajes
     * @return ArrayList con todos los personajes
     */
    public ArrayList<Personaje> cogerPersonajes(MongoDatabase db) {
        return pr.cogerPersonajesMongo(db);
    }

    /**
     * Recupera todas las películas almacenadas en MongoDB.
     * 
     * @param db base de datos desde donde se obtendrán las películas
     * @return ArrayList con todas las películas
     */
    public ArrayList<Pelicula> cogerPeliculas(MongoDatabase db) {
        return pelr.cogerPeliculasMongo(db);
    }
}