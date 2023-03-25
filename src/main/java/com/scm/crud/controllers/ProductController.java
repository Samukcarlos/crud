package com.scm.crud.controllers;

import com.scm.crud.dto.ProductDTO;
import com.scm.crud.services.ProductServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController // Configura para que esta classe responda na WEB
@RequestMapping(value = "/client") // rota
public class ProductController {
    @Autowired
    private ProductServices services;

    @GetMapping (value = "/{id}") // Customizando retorno de código de nenhum erro no Postman
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
        ProductDTO dto = services.findByID(id);
        return ResponseEntity.ok(dto);
}
    @GetMapping //
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable){
        Page<ProductDTO> dto = services.findALL(pageable);
        return ResponseEntity.ok(dto);
    }
    @PostMapping
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO dto){ // @Valid -> Faz passar pelas verificações em ProductDTO (Campo vazio...)
        dto =  services.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);// Devolvendo como resposta no Postman 201 ("created") e no cabessálho da resposta terá o link do recurso criado a URI
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) { // No Put tambem tenho o corpo Bary
        dto = services.update(id, dto);
        return ResponseEntity.ok(dto);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        services.delete(id);
        return ResponseEntity.noContent().build(); // resposta sem corpo 204
    }

}





