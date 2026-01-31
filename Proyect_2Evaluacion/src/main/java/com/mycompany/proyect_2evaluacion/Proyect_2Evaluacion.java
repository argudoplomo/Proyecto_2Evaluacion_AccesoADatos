/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyect_2evaluacion;

import Controlador.APIConnector;
import Controlador.DBConnector;
import Controlador.HibernateConnector;
import Controlador.MongoConnector;
import Modelo.Pelicula;
import com.mongodb.client.MongoDatabase;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import modelo.Personaje;

/**
 *
 * @author Diurno
 */
public class Proyect_2Evaluacion {

    public static void main(String[] args) {
        
        
            //APIConnector api = new APIConnector();
            MongoConnector mongo = new MongoConnector("mongodb://localhost:27017/");
            DBConnector sql = new DBConnector("BD_SW");
            HibernateConnector hibernate = new HibernateConnector();
            
            //ArrayList<Personaje> pers = api.cargarPersonajes();
            //ArrayList<Pelicula> pels = api.cargarPeliculas();
            
            String bd = "SW_API";
            MongoDatabase db = mongo.createDB(bd);
            //mongo.createCollectionPersonajes(db);
            //for (int i = 0; i < pels.size(); i++) {
            //    System.out.println(pels.get(i).toString());
            //}
            System.out.println("");
												
            //mongo.createCollectionPeliculas(db);
            //for (int i = 0; i < pers.size(); i++) {
            //    System.out.println(pers.get(i).toString());
            //}
            
            //mongo.insertarPersonajes(db, pers);
            //mongo.insertarPeliculas(db, pels);
            
            ArrayList <Personaje> pers2 = mongo.cogerPersonajes(db);
            ArrayList <Pelicula> pels2 = mongo.cogerPeliculas(db);

            sql.insertarPersonajes(pers2);
            sql.insertarPeliculas(pels2);
            sql.insertarRelacionATravesDePeliculas(pels2);
            
            //Pelicula pel1 = new Pelicula(7, "El despertar de la fuerza", "JJAbrams", "JJAbrams", LocalDate.of(2015, 12, 18));
            //hibernate.insertarPelicula(pel1);
            hibernate.insertarSecuelas();
        
    }
}
