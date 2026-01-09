package com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.out.persistence;

import com.sistemasTarija.romeroChampions.catalogo.application.port.out.CatalogoPersistencePort;
import com.sistemasTarija.romeroChampions.catalogo.domain.model.options.*;
import com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.out.persistence.entity.options.*;
import com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.out.persistence.mapper.CatalogoPersistenceMapper;
import com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.out.persistence.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador de Persistencia: implementa el puerto de salida usando repositorios JPA
 */
@Component
@RequiredArgsConstructor
public class CatalogoRepositoryAdapter implements CatalogoPersistencePort {

    private final MarcaRepository marcaRepository;
    private final CategoriaRepository categoriaRepository;
    private final EstiloRepository estiloRepository;
    private final GeneroRepository generoRepository;
    private final TallaRepository tallaRepository;
    private final ColorRepository colorRepository;
    private final CatalogoPersistenceMapper mapper;

    // ==================== MARCA ====================
    @Override
    public Marca saveMarca(Marca marca) {
        MarcaCatalogoEntity entity = mapper.toMarcaEntity(marca);
        MarcaCatalogoEntity savedEntity = marcaRepository.save(entity);
        return mapper.toMarcaDomain(savedEntity);
    }

    @Override
    public Optional<Marca> findMarcaById(Integer id) {
        return marcaRepository.findById(id)
                .map(mapper::toMarcaDomain);
    }

    @Override
    public Optional<Marca> findMarcaByNombre(String nombre) {
        return marcaRepository.findByNombre(nombre)
                .map(mapper::toMarcaDomain);
    }

    @Override
    public List<Marca> findAllMarcas() {
        return mapper.toMarcaDomainList(marcaRepository.findByEstadoTrue());
    }

    // ==================== CATEGORIA ====================
    @Override
    public Categoria saveCategoria(Categoria categoria) {
        CategoriaCatalogoEntity entity = mapper.toCategoriaEntity(categoria);
        CategoriaCatalogoEntity savedEntity = categoriaRepository.save(entity);
        return mapper.toCategoriaDomain(savedEntity);
    }

    @Override
    public Optional<Categoria> findCategoriaById(Integer id) {
        return categoriaRepository.findById(id)
                .map(mapper::toCategoriaDomain);
    }

    @Override
    public Optional<Categoria> findCategoriaByNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre)
                .map(mapper::toCategoriaDomain);
    }

    @Override
    public List<Categoria> findAllCategorias() {
        return mapper.toCategoriaDomainList(categoriaRepository.findByEstadoTrue());
    }

    // ==================== ESTILO ====================
    @Override
    public Estilo saveEstilo(Estilo estilo) {
        EstiloCatalogoEntity entity = mapper.toEstiloEntity(estilo);
        EstiloCatalogoEntity savedEntity = estiloRepository.save(entity);
        return mapper.toEstiloDomain(savedEntity);
    }

    @Override
    public Optional<Estilo> findEstiloById(Integer id) {
        return estiloRepository.findById(id)
                .map(mapper::toEstiloDomain);
    }

    @Override
    public Optional<Estilo> findEstiloByNombre(String nombre) {
        return estiloRepository.findByNombre(nombre)
                .map(mapper::toEstiloDomain);
    }

    @Override
    public List<Estilo> findAllEstilos() {
        return mapper.toEstiloDomainList(estiloRepository.findByEstadoTrue());
    }

    // ==================== GENERO ====================
    @Override
    public Genero saveGenero(Genero genero) {
        GeneroCatalogoEntity entity = mapper.toGeneroEntity(genero);
        GeneroCatalogoEntity savedEntity = generoRepository.save(entity);
        return mapper.toGeneroDomain(savedEntity);
    }

    @Override
    public Optional<Genero> findGeneroById(Integer id) {
        return generoRepository.findById(id)
                .map(mapper::toGeneroDomain);
    }

    @Override
    public Optional<Genero> findGeneroByNombre(String nombre) {
        return generoRepository.findByNombre(nombre)
                .map(mapper::toGeneroDomain);
    }

    @Override
    public List<Genero> findAllGeneros() {
        return mapper.toGeneroDomainList(generoRepository.findByEstadoTrue());
    }

    // ==================== TALLA ====================
    @Override
    public Talla saveTalla(Talla talla) {
        TallaCatalogoEntity entity = mapper.toTallaEntity(talla);
        TallaCatalogoEntity savedEntity = tallaRepository.save(entity);
        return mapper.toTallaDomain(savedEntity);
    }

    @Override
    public Optional<Talla> findTallaById(Integer id) {
        return tallaRepository.findById(id)
                .map(mapper::toTallaDomain);
    }

    @Override
    public Optional<Talla> findTallaByNombre(String nombre) {
        return tallaRepository.findByNombre(nombre)
                .map(mapper::toTallaDomain);
    }

    @Override
    public List<Talla> findAllTallas() {
        return mapper.toTallaDomainList(tallaRepository.findByEstadoTrue());
    }

    // ==================== COLOR ====================
    @Override
    public Color saveColor(Color color) {
        ColorCatalogoEntity entity = mapper.toColorEntity(color);
        ColorCatalogoEntity savedEntity = colorRepository.save(entity);
        return mapper.toColorDomain(savedEntity);
    }

    @Override
    public Optional<Color> findColorById(Integer id) {
        return colorRepository.findById(id)
                .map(mapper::toColorDomain);
    }

    @Override
    public Optional<Color> findColorByNombre(String nombre) {
        return colorRepository.findByNombre(nombre)
                .map(mapper::toColorDomain);
    }

    @Override
    public List<Color> findAllColores() {
        return mapper.toColorDomainList(colorRepository.findByEstadoTrue());
    }
}
