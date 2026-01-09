package com.sistemasTarija.romeroChampions.catalogo.application.service;

import com.sistemasTarija.romeroChampions.catalogo.application.dto.options.ColorDTO;
import com.sistemasTarija.romeroChampions.catalogo.application.dto.options.OptionDTO;
import com.sistemasTarija.romeroChampions.catalogo.application.mapper.CatalogoMapper;
import com.sistemasTarija.romeroChampions.catalogo.application.port.in.options.ManageOptionsUseCase;
import com.sistemasTarija.romeroChampions.catalogo.application.port.out.CatalogoPersistencePort;
import com.sistemasTarija.romeroChampions.catalogo.domain.exception.options.*;
import com.sistemasTarija.romeroChampions.catalogo.domain.model.options.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CatalogoOptionsService implements ManageOptionsUseCase {

    private final CatalogoPersistencePort persistencePort;
    private final CatalogoMapper mapper;

    // ==================== MARCA ====================
    @Override
    public OptionDTO createMarca(OptionDTO dto) {
        // Validar que no exista una marca con el mismo nombre
        persistencePort.findMarcaByNombre(dto.getNombre())
                .ifPresent(marca -> {
                    throw new MarcaFailedException("Ya existe una marca con el nombre: " + dto.getNombre());
                });
        
        Marca domain = mapper.toMarcaDomain(dto);
        domain.setEstado(true); // Nuevo registro siempre activo
        Marca saved = persistencePort.saveMarca(domain);
        return mapper.toMarcaDTO(saved);
    }

    @Override
    public OptionDTO updateMarca(Integer id, OptionDTO dto) {
        Marca existing = persistencePort.findMarcaById(id)
                .orElseThrow(() -> new MarcaFailedException("Marca no encontrada con id: " + id));
        
        // Validar nombre duplicado (excepto si es el mismo registro)
        persistencePort.findMarcaByNombre(dto.getNombre())
                .ifPresent(marca -> {
                    if (!marca.getId().equals(id)) {
                        throw new MarcaFailedException("Ya existe una marca con el nombre: " + dto.getNombre());
                    }
                });
        
        existing.setNombre(dto.getNombre());
        Marca updated = persistencePort.saveMarca(existing);
        return mapper.toMarcaDTO(updated);
    }

    @Override
    public void deleteMarca(Integer id) {
        Marca marca = persistencePort.findMarcaById(id)
                .orElseThrow(() -> new MarcaFailedException("Marca no encontrada con id: " + id));
        
        marca.setEstado(false); // Eliminación lógica
        persistencePort.saveMarca(marca);
    }

    @Override
    public OptionDTO findMarcaById(Integer id) {
        return persistencePort.findMarcaById(id)
                .map(mapper::toMarcaDTO)
                .orElseThrow(() -> new MarcaFailedException("Marca no encontrada con id: " + id));
    }

    @Override
    public List<OptionDTO> findAllMarcas() {
        List<Marca> marcas = persistencePort.findAllMarcas();
        return mapper.toMarcaDTOList(marcas);
    }

    // ==================== CATEGORIA ====================
    @Override
    public OptionDTO createCategoria(OptionDTO dto) {
        persistencePort.findCategoriaByNombre(dto.getNombre())
                .ifPresent(categoria -> {
                    throw new CategoriaFailedException("Ya existe una categoría con el nombre: " + dto.getNombre());
                });
        
        Categoria domain = mapper.toCategoriaDomain(dto);
        domain.setEstado(true);
        Categoria saved = persistencePort.saveCategoria(domain);
        return mapper.toCategoriaDTO(saved);
    }

    @Override
    public OptionDTO updateCategoria(Integer id, OptionDTO dto) {
        Categoria existing = persistencePort.findCategoriaById(id)
                .orElseThrow(() -> new CategoriaFailedException("Categoría no encontrada con id: " + id));
        
        persistencePort.findCategoriaByNombre(dto.getNombre())
                .ifPresent(categoria -> {
                    if (!categoria.getId().equals(id)) {
                        throw new CategoriaFailedException("Ya existe una categoría con el nombre: " + dto.getNombre());
                    }
                });
        
        existing.setNombre(dto.getNombre());
        Categoria updated = persistencePort.saveCategoria(existing);
        return mapper.toCategoriaDTO(updated);
    }

    @Override
    public void deleteCategoria(Integer id) {
        Categoria categoria = persistencePort.findCategoriaById(id)
                .orElseThrow(() -> new CategoriaFailedException("Categoría no encontrada con id: " + id));
        
        categoria.setEstado(false);
        persistencePort.saveCategoria(categoria);
    }

    @Override
    public OptionDTO findCategoriaById(Integer id) {
        return persistencePort.findCategoriaById(id)
                .map(mapper::toCategoriaDTO)
                .orElseThrow(() -> new CategoriaFailedException("Categoría no encontrada con id: " + id));
    }

    @Override
    public List<OptionDTO> findAllCategorias() {
        List<Categoria> categorias = persistencePort.findAllCategorias();
        return mapper.toCategoriaDTOList(categorias);
    }

    // ==================== ESTILO ====================
    @Override
    public OptionDTO createEstilo(OptionDTO dto) {
        persistencePort.findEstiloByNombre(dto.getNombre())
                .ifPresent(estilo -> {
                    throw new EstiloFailedException("Ya existe un estilo con el nombre: " + dto.getNombre());
                });
        
        Estilo domain = mapper.toEstiloDomain(dto);
        domain.setEstado(true);
        Estilo saved = persistencePort.saveEstilo(domain);
        return mapper.toEstiloDTO(saved);
    }

    @Override
    public OptionDTO updateEstilo(Integer id, OptionDTO dto) {
        Estilo existing = persistencePort.findEstiloById(id)
                .orElseThrow(() -> new EstiloFailedException("Estilo no encontrado con id: " + id));
        
        persistencePort.findEstiloByNombre(dto.getNombre())
                .ifPresent(estilo -> {
                    if (!estilo.getId().equals(id)) {
                        throw new EstiloFailedException("Ya existe un estilo con el nombre: " + dto.getNombre());
                    }
                });
        
        existing.setNombre(dto.getNombre());
        Estilo updated = persistencePort.saveEstilo(existing);
        return mapper.toEstiloDTO(updated);
    }

    @Override
    public void deleteEstilo(Integer id) {
        Estilo estilo = persistencePort.findEstiloById(id)
                .orElseThrow(() -> new EstiloFailedException("Estilo no encontrado con id: " + id));
        
        estilo.setEstado(false);
        persistencePort.saveEstilo(estilo);
    }

    @Override
    public OptionDTO findEstiloById(Integer id) {
        return persistencePort.findEstiloById(id)
                .map(mapper::toEstiloDTO)
                .orElseThrow(() -> new EstiloFailedException("Estilo no encontrado con id: " + id));
    }

    @Override
    public List<OptionDTO> findAllEstilos() {
        List<Estilo> estilos = persistencePort.findAllEstilos();
        return mapper.toEstiloDTOList(estilos);
    }

    // ==================== GENERO ====================
    @Override
    public OptionDTO createGenero(OptionDTO dto) {
        persistencePort.findGeneroByNombre(dto.getNombre())
                .ifPresent(genero -> {
                    throw new GeneroFailedException("Ya existe un género con el nombre: " + dto.getNombre());
                });
        
        Genero domain = mapper.toGeneroDomain(dto);
        domain.setEstado(true);
        Genero saved = persistencePort.saveGenero(domain);
        return mapper.toGeneroDTO(saved);
    }

    @Override
    public OptionDTO updateGenero(Integer id, OptionDTO dto) {
        Genero existing = persistencePort.findGeneroById(id)
                .orElseThrow(() -> new GeneroFailedException("Género no encontrado con id: " + id));
        
        persistencePort.findGeneroByNombre(dto.getNombre())
                .ifPresent(genero -> {
                    if (!genero.getId().equals(id)) {
                        throw new GeneroFailedException("Ya existe un género con el nombre: " + dto.getNombre());
                    }
                });
        
        existing.setNombre(dto.getNombre());
        Genero updated = persistencePort.saveGenero(existing);
        return mapper.toGeneroDTO(updated);
    }

    @Override
    public void deleteGenero(Integer id) {
        Genero genero = persistencePort.findGeneroById(id)
                .orElseThrow(() -> new GeneroFailedException("Género no encontrado con id: " + id));
        
        genero.setEstado(false);
        persistencePort.saveGenero(genero);
    }

    @Override
    public OptionDTO findGeneroById(Integer id) {
        return persistencePort.findGeneroById(id)
                .map(mapper::toGeneroDTO)
                .orElseThrow(() -> new GeneroFailedException("Género no encontrado con id: " + id));
    }

    @Override
    public List<OptionDTO> findAllGeneros() {
        List<Genero> generos = persistencePort.findAllGeneros();
        return mapper.toGeneroDTOList(generos);
    }

    // ==================== TALLA ====================
    @Override
    public OptionDTO createTalla(OptionDTO dto) {
        persistencePort.findTallaByNombre(dto.getNombre())
                .ifPresent(talla -> {
                    throw new TallaFailedException("Ya existe una talla con el nombre: " + dto.getNombre());
                });
        
        Talla domain = mapper.toTallaDomain(dto);
        domain.setEstado(true);
        Talla saved = persistencePort.saveTalla(domain);
        return mapper.toTallaDTO(saved);
    }

    @Override
    public OptionDTO updateTalla(Integer id, OptionDTO dto) {
        Talla existing = persistencePort.findTallaById(id)
                .orElseThrow(() -> new TallaFailedException("Talla no encontrada con id: " + id));
        
        persistencePort.findTallaByNombre(dto.getNombre())
                .ifPresent(talla -> {
                    if (!talla.getId().equals(id)) {
                        throw new TallaFailedException("Ya existe una talla con el nombre: " + dto.getNombre());
                    }
                });
        
        existing.setNombre(dto.getNombre());
        Talla updated = persistencePort.saveTalla(existing);
        return mapper.toTallaDTO(updated);
    }

    @Override
    public void deleteTalla(Integer id) {
        Talla talla = persistencePort.findTallaById(id)
                .orElseThrow(() -> new TallaFailedException("Talla no encontrada con id: " + id));
        
        talla.setEstado(false);
        persistencePort.saveTalla(talla);
    }

    @Override
    public OptionDTO findTallaById(Integer id) {
        return persistencePort.findTallaById(id)
                .map(mapper::toTallaDTO)
                .orElseThrow(() -> new TallaFailedException("Talla no encontrada con id: " + id));
    }

    @Override
    public List<OptionDTO> findAllTallas() {
        List<Talla> tallas = persistencePort.findAllTallas();
        return mapper.toTallaDTOList(tallas);
    }

    // ==================== COLOR ====================
    @Override
    public ColorDTO createColor(ColorDTO dto) {
        persistencePort.findColorByNombre(dto.getNombre())
                .ifPresent(color -> {
                    throw new ColorFailedException("Ya existe un color con el nombre: " + dto.getNombre());
                });
        
        Color domain = mapper.toColorDomain(dto);
        domain.setEstado(true);
        Color saved = persistencePort.saveColor(domain);
        return mapper.toColorDTO(saved);
    }

    @Override
    public ColorDTO updateColor(Integer id, ColorDTO dto) {
        Color existing = persistencePort.findColorById(id)
                .orElseThrow(() -> new ColorFailedException("Color no encontrado con id: " + id));
        
        persistencePort.findColorByNombre(dto.getNombre())
                .ifPresent(color -> {
                    if (!color.getId().equals(id)) {
                        throw new ColorFailedException("Ya existe un color con el nombre: " + dto.getNombre());
                    }
                });
        
        existing.setNombre(dto.getNombre());
        existing.setCodigoHex(dto.getCodigoHex());
        Color updated = persistencePort.saveColor(existing);
        return mapper.toColorDTO(updated);
    }

    @Override
    public void deleteColor(Integer id) {
        Color color = persistencePort.findColorById(id)
                .orElseThrow(() -> new ColorFailedException("Color no encontrado con id: " + id));
        
        color.setEstado(false);
        persistencePort.saveColor(color);
    }

    @Override
    public ColorDTO findColorById(Integer id) {
        return persistencePort.findColorById(id)
                .map(mapper::toColorDTO)
                .orElseThrow(() -> new ColorFailedException("Color no encontrado con id: " + id));
    }

    @Override
    public List<ColorDTO> findAllColores() {
        List<Color> colores = persistencePort.findAllColores();
        return mapper.toColorDTOList(colores);
    }
}
