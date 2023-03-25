package com.scm.crud.services;
import com.scm.crud.dto.ProductDTO;
import com.scm.crud.entities.Client;
import com.scm.crud.repositories.ProductRepository;
import com.scm.crud.services.exceptions.DatabaseException;
import com.scm.crud.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServices {
    @Autowired

    private ProductRepository repository;
    @Transactional(readOnly = true)
    public ProductDTO findByID(Long id) {
        Client client = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Recurso não encontrado"));
        //findById(id) -> pegando produto no DB pelo id e guardando no "Optional"
        // pegando o produto de (Optional e salvando em product )// orElseThrow() -> tratando exceção
        return new ProductDTO(client); //convertendo o produto para DTO

    }
    @Transactional(readOnly = true) // operação de consulta
    public Page<ProductDTO> findALL(Pageable pageable ){  // Pageable pageable  -> paginação
        Page<Client> result = repository.findAll(pageable);
        return result.map(x -> new ProductDTO(x));
}
    @Transactional() // salvando no Banco de dados
    public ProductDTO insert(ProductDTO dto){  // recebendo o json e instanciando DTO
        Client entity = new Client(); // instanciando Product
        copyDtoEntity(dto, entity);
        entity = repository.save(entity);// savando no banco de dados e na mesma variável entity
        return new ProductDTO(entity); // reconverter para DTO e retornar no meu método
    }
    @Transactional() // Atualizando dados
    public ProductDTO update(Long id, ProductDTO dto){ //put recebendo id e corpo bory
        try {
            Client entity = repository.getReferenceById(id); // instanciando pela referenci pelo banco de dados
            // repository.getReferenceById(id) -> não vai no DB apenas é monitorado pelo JPA
            copyDtoEntity(dto, entity);
            entity = repository.save(entity);
            return new ProductDTO(entity);
        }
        catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }
    @Transactional(propagation = Propagation.SUPPORTS) // suporte no tratamento de Exceptions para bancos de dados que não são h2
    public void delete(Long id){
        try {
            repository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Falha de integridade referencial");
        }

    }
    private void copyDtoEntity(ProductDTO dto, Client entity) { // metodo criado para copia de valores
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setBirthDate(dto.getBirthDate());
        entity.setCpf(dto.getDpf());
        entity.setChildren(dto.getChildren());
    }

}




