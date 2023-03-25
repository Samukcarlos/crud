package com.scm.crud.dto;

import com.scm.crud.entities.Client;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDate;
public class ProductDTO {

    private Long id;
    private  String name;
    private  String cpf;
    private  Double income;
    private   LocalDate birthDate;
    private  Integer children;
    public ProductDTO(Long id, String name, String dpf, Double income, LocalDate birthDate, Integer children) {
        this.id = id;
        this.name = name;
        this.cpf = dpf;
        this.income = income;
        this.birthDate = birthDate;
        this.children = children;
    }

    public ProductDTO(Client entity) {
        id = entity.getId();
        name = entity.getName();
        cpf = entity.getCpf();
        income = entity.getIncome();
        birthDate = entity.getBirthDate();
        children = entity.getChildren();

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDpf() {
        return cpf;
    }

    public Double getIncome() {
        return income;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Integer getChildren() {
        return children;
    }
}
