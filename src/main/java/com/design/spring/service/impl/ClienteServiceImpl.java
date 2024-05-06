package com.design.spring.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.design.spring.http.ViaCepService;
import com.design.spring.model.Cliente;
import com.design.spring.model.Endereco;
import com.design.spring.repository.ClienteRepository;
import com.design.spring.repository.EnderecoRepository;
import com.design.spring.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.get();
    }

    private void salvarClienteComCep(Cliente cliente) {

		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			Endereco novoEndereco = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		cliente.setEndereco(endereco);
		clienteRepository.save(cliente);
	}

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
		if (clienteBd.isPresent()) {
			salvarClienteComCep(cliente);
		}
    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }
    
}
