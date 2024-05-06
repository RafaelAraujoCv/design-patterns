package com.design.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.design.spring.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, String> {
    
}
