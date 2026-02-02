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
 *
 * @author Diurno
 */
public class APIConnector {
				
    private String urlBasicaPersonajes = "https://swapi.info/api/people/";
    private String urlBasicaPeliculas = "https://swapi.info/api/films/";
    private Scanner sc;

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
 
    private LocalDate transformarFecha (String strFecha) {
        String[] arrayFecha = strFecha.split("-");

        int year = Integer.parseInt(arrayFecha[0]);
        int mes = Integer.parseInt(arrayFecha[1]);
        int dia = Integer.parseInt(arrayFecha[2]);

        return LocalDate.of(year, mes, dia);
    }
    
    private double transformarPeso (String strPeso) {
        strPeso = strPeso.replace(',', '.');
        if (strPeso.equals("unknown")) {
            return -1;
        }
        return Double.parseDouble(strPeso);
    }
    
    private int transformarAltura (String strAltura) {
        if (strAltura.equals("unknown")) {
            return -1;
        }
        return Integer.parseInt(strAltura);
    }
}
