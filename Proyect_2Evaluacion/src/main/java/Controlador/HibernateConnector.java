/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Pelicula;
import dao.PeliculaRepository;
import dao.PersonajeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.Personaje;

/**
 *
 * @author Argudo_Plomo
 */
public class HibernateConnector {
    
    private EntityManager em;
    
    PersonajeRepository perr;
    PeliculaRepository pelr;
    
    public HibernateConnector () {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadDePersistencia");
        this.em = emf.createEntityManager();
        perr = new PersonajeRepository(em);
        pelr = new PeliculaRepository(em);
    }
    
    public void insertarPelicula (Pelicula p) {
        pelr.insertarHib(p);
    }
    
    public void insertarPersonaje (Personaje p) {
        perr.insertarHib(p);
    }
    
    public void insertarSecuelas () {
        pelr.insertarSecuelasHib();
    }
    
    public Pelicula buscarPeliPorIDHib (int id) {
        return pelr.buscarPorIDHib(id);
    }
}
