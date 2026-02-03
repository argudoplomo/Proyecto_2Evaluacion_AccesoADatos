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
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import modelo.Personaje;

/**
 * Clase encargada de gestionar la conexión con Hibernate.
 * 
 * Crear el EntityManager a partir de la unidad de persistencia
 * Instanciar los repositorios (DAO)
 * Servir como puente entre la aplicación y la base de datos usando Hibernate
 */
public class HibernateConnector {
    
     // EntityManager: objeto principal de gestion
    private EntityManager em;
    
    PersonajeRepository perr;
    PeliculaRepository pelr;
    
     /**
     * Constructor: inicializa Hibernate.
     */
    public HibernateConnector () {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadDePersistencia");
        this.em = emf.createEntityManager();
        perr = new PersonajeRepository(em);
        pelr = new PeliculaRepository(em);
    }
    
    /**
     * Inserta una película usando el repositorio PeliculaRepository.
     * 
     * @param p película a insertar
     */
    public void insertarPelicula (Pelicula p) {
        pelr.insertarHib(p);
    }
    
    /**
     * Inserta un personaje usando el repositorio PersonajeRepository.
     * 
     * @param p personaje a insertar
     */
    public void insertarPersonaje (Personaje p) {
        perr.insertarHib(p);
    }
    
    public void insertarSecuelas() {
        pelr.insertarSecuelasHib();
    }

    /**
     * Busca una película por su ID (episode_id).
     * 
     * @param id ID de la película
     * @return película encontrada o null 
     */
    public Pelicula buscarPeliPorIDHib(int id) {
        return pelr.buscarPorIDHib(id);
    }

    /**
     * Devuelve un array con los títulos de todas las películas.
     * 
     * @return array de títulos
     */
    public String[] sabarTitulosDePeliculas() {
        return pelr.sacarTitulos();
    }

    /**
     * Devuelve los nombres de los personajes que aparecen en una película concreta.
     * 
     * @param episode_id ID de la película
     * @return array de nombres de personajes
     */
    public String[] sacarNombresDePersonajesDePelicula(int episode_id) {
        return perr.sacarTodosDeUnaPelicula(episode_id);
    }

    /**
     * Busca un personaje por su nombre.
     * 
     * @param nombre nombre del personaje
     * @return Personaje encontrado
     */
    public Personaje buscarPersonajePorNombre (String nombre) throws NoResultException {
        return perr.buscarPorNombreHib(nombre);
    }
}