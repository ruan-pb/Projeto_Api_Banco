package Api_Banco.Servicos;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Api_Banco.DTOS.InputCriarConta;
import Api_Banco.Entidades.Conta;
import Api_Banco.Exceptions.ContaInexistente;
import Api_Banco.Exceptions.ContaInvalida;
import Api_Banco.Repositorio.ContaRepositorio;

@Service
public class ContaServico {

	@Autowired
	private ContaRepositorio contaBD;
	
	public ContaServico() {}
	
	@PostConstruct
	public void initContas() {
		try {
			if(contaBD.count() == 5) {
				System.out.println("Já existe 5 contas");
			}
			else {
				ObjectMapper mapper = new ObjectMapper();
				TypeReference<List<Conta>> tipoDeDados = new TypeReference<List<Conta>>() {};
				InputStream inputStream = ObjectMapper.class.getResourceAsStream("/json/contas.json");
				List<Conta> conta = mapper.readValue(inputStream, tipoDeDados);
				System.out.println("Contas salvas no Banco De Dados");
				LocalTime now = LocalTime.now();
				System.out.println(now);
				contaBD.saveAll(conta);
				
			}
			
		}
		catch(IOException e) {
			System.out.println("não foi possivel salvar " + e.getMessage());
		}
		
	}
	
	
	
	
	
	
	

	public InputCriarConta criarConta(Conta conta) {
		/*
		if (conta.isValid()) {
			throw new ContaInvalida();
		}
*/
		boolean existiConta = existiConta(conta.getConta());
		if (existiConta == true) {
			throw new ContaInvalida();
		}
		contaBD.save(conta);
		return new InputCriarConta(conta);

	}

	public Conta getConta(String numeroConta) {
		Optional<Conta> optConta = contaBD.findByConta(numeroConta);
		if (optConta.isEmpty()) {
			throw new ContaInexistente();
		}
		return optConta.get();
	}

	public boolean existiConta(String numeroConta) {
		boolean existi = false;
		for (Conta conta : this.contaBD.findAll()) {

			if (conta.getConta().equals(numeroConta)) {
				return true;
			} else {
				return false;
			}
		}
		return existi;

	}

}
