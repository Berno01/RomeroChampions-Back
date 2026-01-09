package com.sistemasTarija.romeroChampions.catalogo.application.port.in.options;

import com.sistemasTarija.romeroChampions.catalogo.application.dto.options.ColorDTO;
import com.sistemasTarija.romeroChampions.catalogo.application.dto.options.OptionDTO;

import java.util.List;


public interface ManageOptionsUseCase {
    
    // === MARCA ===
    OptionDTO createMarca(OptionDTO dto);
    OptionDTO updateMarca(Integer id, OptionDTO dto);
    void deleteMarca(Integer id);
    OptionDTO findMarcaById(Integer id);
    List<OptionDTO> findAllMarcas();
    
    // === CATEGORIA ===
    OptionDTO createCategoria(OptionDTO dto);
    OptionDTO updateCategoria(Integer id, OptionDTO dto);
    void deleteCategoria(Integer id);
    OptionDTO findCategoriaById(Integer id);
    List<OptionDTO> findAllCategorias();
    
    // === ESTILO ===
    OptionDTO createEstilo(OptionDTO dto);
    OptionDTO updateEstilo(Integer id, OptionDTO dto);
    void deleteEstilo(Integer id);
    OptionDTO findEstiloById(Integer id);
    List<OptionDTO> findAllEstilos();
    
    // === GENERO ===
    OptionDTO createGenero(OptionDTO dto);
    OptionDTO updateGenero(Integer id, OptionDTO dto);
    void deleteGenero(Integer id);
    OptionDTO findGeneroById(Integer id);
    List<OptionDTO> findAllGeneros();
    
    // === TALLA ===
    OptionDTO createTalla(OptionDTO dto);
    OptionDTO updateTalla(Integer id, OptionDTO dto);
    void deleteTalla(Integer id);
    OptionDTO findTallaById(Integer id);
    List<OptionDTO> findAllTallas();
    
    // === COLOR ===
    ColorDTO createColor(ColorDTO dto);
    ColorDTO updateColor(Integer id, ColorDTO dto);
    void deleteColor(Integer id);
    ColorDTO findColorById(Integer id);
    List<ColorDTO> findAllColores();
}
