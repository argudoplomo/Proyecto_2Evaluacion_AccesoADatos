/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Pelicula;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Personaje;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Clase encargada de conectarse a la API pública de Star Wars (SWAPI)
 * para obtener información de personajes y películas.
 * 
 * Descarga datos en formato JSON y los transforma en objetos Java
 * (Personaje y Pelicula).
 */
public class APIConnector {
    // URL base para acceder a los personajes en la API
    private String urlBasicaPersonajes = "https://swapi.info/api/people/";
    // URL base para acceder a las películas en la API
    private String urlBasicaPeliculas = "https://swapi.info/api/films/";
    
    // Scanner utilizado para leer el contenido que devuelve la API
    private Scanner sc;
    
    /**
     * Carga todos los personajes desde la API.
     * 
     * @return ArrayList con todos los personajes obtenidos de la API
     */
    public ArrayList<Personaje> cargarPersonajes () {
        ArrayList<Personaje> personajes = new ArrayList<>();

        try {

            URL url = URI.create(urlBasicaPersonajes).toURL();
            sc = new Scanner(url.openStream());

            StringBuilder strPersonajes = new StringBuilder();
            while (sc.hasNext()) {
                strPersonajes.append(sc.nextLine());
            }

            JSONArray JSONPersonajes = new JSONArray(strPersonajes.toString());

            for (int i = 0; i < JSONPersonajes.length(); i++) {

                JSONObject personaje = JSONPersonajes.getJSONObject(i);

                int id = (i+1);
                if (id >= 17) {
                    id++;
                }
                String nombre = personaje.getString("name");
                String genero = personaje.getString("gender");
                String urlPlaneta = personaje.getString("homeworld");
                String lugarNacimiento = cargarLugarNacimiento(urlPlaneta);
                String fechaNacimiento = personaje.getString("birth_year");
                int altura = transformarAltura(personaje.getString("height"));
                double peso = transformarPeso(personaje.getString("mass"));
																
                JSONArray JSONPeliculas = personaje.getJSONArray("films");
                ArrayList<Pelicula> peliculas = new ArrayList<Pelicula>(1);
                for (int j = 0 ; j < JSONPeliculas.length(); j++) {
                    String strPelicula = JSONPeliculas.getString(j);
                    int longitudPelicula = strPelicula.length() - urlBasicaPeliculas.length();

                    String strId = strPelicula.substring(strPelicula.length() - longitudPelicula);
                    int idPeli = Integer.parseInt(strId);

                    peliculas.add(new Pelicula (idPeli));
                }

                personajes.add(new Personaje(id, nombre, genero, lugarNacimiento, fechaNacimiento, altura, peso, peliculas));

            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(APIConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(APIConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        sc.close();
        
        return personajes;
    }

     /**
     * Carga todas las películas desde la API.
     * 
     * @return ArrayList con todas las películas obtenidas de la API
     */
    public ArrayList<Pelicula> cargarPeliculas () {
        ArrayList<Pelicula> peliculas = new ArrayList<>();

        try {

            URL url = URI.create(urlBasicaPeliculas).toURL();
            sc = new Scanner(url.openStream());

            StringBuilder strPeliculas = new StringBuilder();
            while (sc.hasNext()) {
                strPeliculas.append(sc.nextLine());
            }

            JSONArray JSONPeliculas = new JSONArray(strPeliculas.toString());

            for (int i = 0; i < JSONPeliculas.length(); i++) {

                JSONObject pelicula = JSONPeliculas.getJSONObject(i);

                int id = pelicula.getInt("episode_id");
                String titulo = pelicula.getString("title");
                String director = pelicula.getString("director");
                String productor = pelicula.getString("producer");

                String strFecha = pelicula.getString("release_date");
                LocalDate fechaLanzamiento = transformarFecha(strFecha);
																
                JSONArray JSONPersonajes = pelicula.getJSONArray("characters");
                ArrayList<Personaje> personajes = new ArrayList<Personaje>(1);
                for (int j = 0 ; j < JSONPersonajes.length(); j++) {
                    String strPersonaje = JSONPersonajes.getString(j);
                    int longitudPersonaje = strPersonaje.length() - urlBasicaPersonajes.length();

                    String strId = strPersonaje.substring(strPersonaje.length() - longitudPersonaje);
                    int idPers = Integer.parseInt(strId);

                    personajes.add(new Personaje (idPers));
                }

                peliculas.add(new Pelicula(id, titulo, director, productor, fechaLanzamiento, personajes));
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(APIConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(APIConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        sc.close();
        return peliculas;
    }
    
    /**
     * Dada una URL de un planeta, consulta la API y devuelve el nombre del planeta.
     * 
     * @param urlStr URL del planeta en formato String
     * @return nombre del planeta
     */
    private String cargarLugarNacimiento (String urlStr) {

        String planeta = "";

        try {

            URL url = URI.create(urlStr).toURL();
            sc = new Scanner(url.openStream());

            StringBuilder strPlaneta = new StringBuilder();
            while (sc.hasNext()) {
                strPlaneta.append(sc.nextLine());
            }

            JSONObject JSONPlaneta = new JSONObject(strPlaneta.toString());
            planeta = JSONPlaneta.getString("name");

        } catch (MalformedURLException ex) {
            Logger.getLogger(APIConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(APIConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        sc.close();

        return planeta;

    }
 
    /**
     * Convierte una fecha en formato String "yyyy-MM-dd" a LocalDate.
     * 
     * @param strFecha fecha en String
     * @return LocalDate con la fecha convertida
     */
    private LocalDate transformarFecha (String strFecha) {
        String[] arrayFecha = strFecha.split("-");

        int year = Integer.parseInt(arrayFecha[0]);
        int mes = Integer.parseInt(arrayFecha[1]);
        int dia = Integer.parseInt(arrayFecha[2]);

        return LocalDate.of(year, mes, dia);
    }
    
    /**
     * Convierte el peso recibido de la API en double.
     * Si el peso es "unknown", devuelve -1.
     * 
     * @param strPeso peso en String
     * @return peso en double o -1 si es desconocido
     */
    private double transformarPeso (String strPeso) {
        strPeso = strPeso.replace(',', '.');
        if (strPeso.equals("unknown")) {
            return -1;
        }
        return Double.parseDouble(strPeso);
    }
    
    /**
     * Convierte la altura recibida de la API en int.
     * Si la altura es "unknown", devuelve -1.
     * 
     * @param strAltura altura en String
     * @return altura en int o -1 si es desconocida
     */
    private int transformarAltura (String strAltura) {
        if (strAltura.equals("unknown")) {
            return -1;
        }
        return Integer.parseInt(strAltura);
    }
}
