/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Pelicula;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Personaje;

/**
 *
 * @author Diurno
 */
public class DBConnector {
    private String strConection = "jdbc:mysql://localhost/";
    private String user = "root";
    private String password = "";

    Connection con;

    public DBConnector(String nameDB) {
        try {
            this.con = DriverManager.getConnection(strConection, user, password);
            createBaseDatos(nameDB);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarPersonajes (ArrayList <Personaje> personajes) {
        String insert = "INSERT INTO personaje (id, nombre, genero, lugar_nacimiento, anio_nacimiento, altura, peso) "
                                                                        + "VALUES (?, ?, ?, ?, ?, ?, ?);";

        try {
            PreparedStatement pstmt = con.prepareStatement(insert);

            for (int i = 0; i < personajes.size(); i++) {
                pstmt.setInt(1, personajes.get(i).getId());
                pstmt.setString(2, personajes.get(i).getNombre());
                pstmt.setString(3, personajes.get(i).getGenero());
                pstmt.setString(4, personajes.get(i).getLugarNacimiento());
                pstmt.setString(5, personajes.get(i).getAnioNacimiento());
                pstmt.setInt(6, personajes.get(i).getAltura());
                pstmt.setDouble(7, personajes.get(i).getPeso());

                pstmt.execute();
            }


        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarPeliculas (ArrayList <Pelicula> peliculas) {
        String insert = "INSERT INTO pelicula(episode_id, titulo, director, productor, fecha_lanzamiento) "
                                                                        + "VALUES (?, ?, ?, ?, ?);";

        try {
            PreparedStatement pstmt = con.prepareStatement(insert);

            for (int i = 0; i < peliculas.size(); i++) {
                pstmt.setInt(1, peliculas.get(i).getEpisode_id());
                pstmt.setString(2, peliculas.get(i).getTitulo());
                pstmt.setString(3, peliculas.get(i).getDirector());
                pstmt.setString(4, peliculas.get(i).getProductor());
                Timestamp date = Timestamp.valueOf(peliculas.get(i).getFecha_lanzamiento().atStartOfDay());
                pstmt.setTimestamp(5, date);

                pstmt.execute();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
				
				public void insertarRelacionATravesDePeliculas (ArrayList<Pelicula> peliculas) {
								String insert = "INSERT INTO personaje_pelicula(personaje_id, pelicula_id) "
																												+ "VALUES (?, ?);";

        try {
            PreparedStatement pstmt = con.prepareStatement(insert);

            for (int i = 0; i < peliculas.size(); i++) {
																Pelicula pel = peliculas.get(i);
																
																for (int j = 0; j < peliculas.get(i).getPersonajes().size(); j++) {
																				Personaje per = peliculas.get(i).getPersonajes().get(j);
																				
																				pstmt.setInt(1, per.getId());
																				pstmt.setInt(2, pel.getEpisode_id());
																				pstmt.execute();
																}
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
				}
				

    private void createBaseDatos (String nameDB) {
        try {
            String creacion = "CREATE DATABASE IF NOT EXISTS " + nameDB + ";";
            String uso = "USE " +  nameDB + ";";

            Statement stmt = con.prepareStatement(creacion);
            stmt.addBatch(creacion);
            stmt.addBatch(uso);
            stmt.executeBatch();
            stmt.close();

            crearTablas();

        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void crearTablas () {
        String tablaPersonaje = "CREATE TABLE IF NOT EXISTS personaje (\n" +
                                        "id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                                        "nombre VARCHAR(100) NOT NULL,\n" +
                                        "genero VARCHAR(50),\n" +
                                        "lugar_nacimiento VARCHAR(100),\n" +
                                        "anio_nacimiento VARCHAR(10),\n" +
                                        "altura INT,\n" +
                                        "peso DOUBLE\n" +
        ");";
        String tablaPelicula = "CREATE TABLE IF NOT EXISTS pelicula (\n" +
            "episode_id INT PRIMARY KEY,\n" +
            "titulo VARCHAR(150) NOT NULL,\n" +
            "director VARCHAR(100),\n" +
            "productor VARCHAR(150),\n" +
            "fecha_lanzamiento DATE\n" +
        ");";

        String tablaPersoPeli = "CREATE TABLE IF NOT EXISTS personaje_pelicula (\n" +
        "    personaje_id INT,\n" +
        "    pelicula_id INT,\n" +
        "    PRIMARY KEY (personaje_id, pelicula_id)," +
        "    FOREIGN KEY (personaje_id) REFERENCES personaje(id), \n" +
        "    FOREIGN KEY (pelicula_id) REFERENCES pelicula(episode_id) \n" +
        ");";

        try {
            Statement stmt = con.createStatement();

            stmt.addBatch(tablaPelicula);
            stmt.addBatch(tablaPersonaje);
            stmt.addBatch(tablaPersoPeli);

            stmt.executeBatch();

            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
				
}
