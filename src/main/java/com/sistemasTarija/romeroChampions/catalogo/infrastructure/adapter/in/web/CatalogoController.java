package com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.in.web;

import com.sistemasTarija.romeroChampions.catalogo.application.dto.CatalogoOptionsResponse;
import com.sistemasTarija.romeroChampions.catalogo.application.dto.options.ColorDTO;
import com.sistemasTarija.romeroChampions.catalogo.application.dto.options.OptionDTO;
import com.sistemasTarija.romeroChampions.catalogo.application.port.in.options.ManageOptionsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/catalogo")
@RequiredArgsConstructor
public class CatalogoController {

    private final ManageOptionsUseCase manageOptionsUseCase;

    // ==================== OPCIONES AGREGADAS ====================
    @GetMapping("/opciones")
    public ResponseEntity<CatalogoOptionsResponse> getAllOptions() {
        CatalogoOptionsResponse response = CatalogoOptionsResponse.builder()
                .marcas(manageOptionsUseCase.findAllMarcas())
                .categorias(manageOptionsUseCase.findAllCategorias())
                .estilos(manageOptionsUseCase.findAllEstilos())
                .generos(manageOptionsUseCase.findAllGeneros())
                .tallas(manageOptionsUseCase.findAllTallas())
                .colores(manageOptionsUseCase.findAllColores())
                .build();
        return ResponseEntity.ok(response);
    }

    // ==================== MARCA ====================
    @PostMapping("/marcas")
    public ResponseEntity<OptionDTO> createMarca(@RequestBody OptionDTO dto) {
        return new ResponseEntity<>(manageOptionsUseCase.createMarca(dto), HttpStatus.CREATED);
    }

    @PutMapping("/marcas/{id}")
    public ResponseEntity<OptionDTO> updateMarca(@PathVariable Integer id, @RequestBody OptionDTO dto) {
        return ResponseEntity.ok(manageOptionsUseCase.updateMarca(id, dto));
    }

    @DeleteMapping("/marcas/{id}")
    public ResponseEntity<Void> deleteMarca(@PathVariable Integer id) {
        manageOptionsUseCase.deleteMarca(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== CATEGORIA ====================
    @PostMapping("/categorias")
    public ResponseEntity<OptionDTO> createCategoria(@RequestBody OptionDTO dto) {
        return new ResponseEntity<>(manageOptionsUseCase.createCategoria(dto), HttpStatus.CREATED);
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<OptionDTO> updateCategoria(@PathVariable Integer id, @RequestBody OptionDTO dto) {
        return ResponseEntity.ok(manageOptionsUseCase.updateCategoria(id, dto));
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Integer id) {
        manageOptionsUseCase.deleteCategoria(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== estilo ====================
    @PostMapping("/estilos")
    public ResponseEntity<OptionDTO> createestilo(@RequestBody OptionDTO dto) {
        return new ResponseEntity<>(manageOptionsUseCase.createEstilo(dto), HttpStatus.CREATED);
    }

    @PutMapping("/estilos/{id}")
    public ResponseEntity<OptionDTO> updateestilo(@PathVariable Integer id, @RequestBody OptionDTO dto) {
        return ResponseEntity.ok(manageOptionsUseCase.updateEstilo(id, dto));
    }

    @DeleteMapping("/estilos/{id}")
    public ResponseEntity<Void> deleteestilo(@PathVariable Integer id) {
        manageOptionsUseCase.deleteEstilo(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== GENERO ====================
    @PostMapping("/generos")
    public ResponseEntity<OptionDTO> createGenero(@RequestBody OptionDTO dto) {
        return new ResponseEntity<>(manageOptionsUseCase.createGenero(dto), HttpStatus.CREATED);
    }

    @PutMapping("/generos/{id}")
    public ResponseEntity<OptionDTO> updateGenero(@PathVariable Integer id, @RequestBody OptionDTO dto) {
        return ResponseEntity.ok(manageOptionsUseCase.updateGenero(id, dto));
    }

    @DeleteMapping("/generos/{id}")
    public ResponseEntity<Void> deleteGenero(@PathVariable Integer id) {
        manageOptionsUseCase.deleteGenero(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== TALLA ====================
    @PostMapping("/tallas")
    public ResponseEntity<OptionDTO> createTalla(@RequestBody OptionDTO dto) {
        return new ResponseEntity<>(manageOptionsUseCase.createTalla(dto), HttpStatus.CREATED);
    }

    @PutMapping("/tallas/{id}")
    public ResponseEntity<OptionDTO> updateTalla(@PathVariable Integer id, @RequestBody OptionDTO dto) {
        return ResponseEntity.ok(manageOptionsUseCase.updateTalla(id, dto));
    }

    @DeleteMapping("/tallas/{id}")
    public ResponseEntity<Void> deleteTalla(@PathVariable Integer id) {
        manageOptionsUseCase.deleteTalla(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== COLOR ====================
    @PostMapping("/colores")
    public ResponseEntity<ColorDTO> createColor(@RequestBody ColorDTO dto) {
        return new ResponseEntity<>(manageOptionsUseCase.createColor(dto), HttpStatus.CREATED);
    }

    @PutMapping("/colores/{id}")
    public ResponseEntity<ColorDTO> updateColor(@PathVariable Integer id, @RequestBody ColorDTO dto) {
        return ResponseEntity.ok(manageOptionsUseCase.updateColor(id, dto));
    }

    @DeleteMapping("/colores/{id}")
    public ResponseEntity<Void> deleteColor(@PathVariable Integer id) {
        manageOptionsUseCase.deleteColor(id);
        return ResponseEntity.noContent().build();
    }
}
