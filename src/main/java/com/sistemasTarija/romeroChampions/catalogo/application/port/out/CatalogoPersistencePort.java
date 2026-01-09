package com.sistemasTarija.romeroChampions.catalogo.application.port.out;

import com.sistemasTarija.romeroChampions.catalogo.domain.model.options.*;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida para la persistencia del catálogo
 */
public interface CatalogoPersistencePort {
    
    // === MARCA ===
    Marca saveMarca(Marca marca);
    Optional<Marca> findMarcaById(Integer id);
    Optional<Marca> findMarcaByNombre(String nombre);
    List<Marca> findAllMarcas();
    
    // === CATEGORIA ===
    Categoria saveCategoria(Categoria categoria);
    Optional<Categoria> findCategoriaById(Integer id);
    Optional<Categoria> findCategoriaByNombre(String nombre);
    List<Categoria> findAllCategorias();
    
    // === ESTILO ===
    Estilo saveEstilo(Estilo estilo);
    Optional<Estilo> findEstiloById(Integer id);
    Optional<Estilo> findEstiloByNombre(String nombre);
    List<Estilo> findAllEstilos();
    
    // === GENERO ===
    Genero saveGenero(Genero genero);
    Optional<Genero> findGeneroById(Integer id);
    Optional<Genero> findGeneroByNombre(String nombre);
    List<Genero> findAllGeneros();
    
    // === TALLA ===
    Talla saveTalla(Talla talla);
    Optional<Talla> findTallaById(Integer id);
    Optional<Talla> findTallaByNombre(String nombre);
    List<Talla> findAllTallas();
    
    // === COLOR ===
    Color saveColor(Color color);
    Optional<Color> findColorById(Integer id);
    Optional<Color> findColorByNombre(String nombre);
    List<Color> findAllColores();
}
