package Api_Banco.Servicos;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Api_Banco.DTOS.ContaDepositoDTO;
import Api_Banco.DTOS.ContaSaqueDTO;
import Api_Banco.DTOS.InputCriarConta;
import Api_Banco.Entidades.Conta;
import Api_Banco.Exceptions.ContaInexistente;
import Api_Banco.Exceptions.ContaInvalida;
import Api_Banco.Exceptions.ContaJaExisti;
import Api_Banco.Exceptions.SaldoInsuficiente;
import Api_Banco.Repositorio.ContaRepositorio;

@Service
public class ContaServico {

	@Autowired
	private ContaRepositorio contaBD;

	@Autowired
	private JWTServico jwtServico;

	public ContaServico() {
	}

	@PostConstruct
	public void initContas() {
		try {
			if (contaBD.count() == 5) {
				System.out.println("Já existe 5 contas");
			} else {
				ObjectMapper mapper = new ObjectMapper();
				TypeReference<List<Conta>> tipoDeDados = new TypeReference<List<Conta>>() {
				};
				InputStream inputStream = ObjectMapper.class.getResourceAsStream("/json/contas.json");
				List<Conta> conta = mapper.readValue(inputStream, tipoDeDados);
				System.out.println("Contas salvas no Banco De Dados");
				LocalTime now = LocalTime.now();
				System.out.println(now);
				contaBD.saveAll(conta);

			}

		} catch (IOException e) {
			System.out.println("não foi possivel salvar " + e.getMessage());
		}

	}

	public InputCriarConta criarConta(Conta conta) {

		boolean existiConta = existiConta(conta.getCliente().getCpf());
		if (existiConta == true) {
			throw new ContaJaExisti();
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

	public boolean existiConta(String cpf) {
		boolean existi = false;
		for (Conta conta : this.contaBD.findAll()) {

			if (conta.getCliente().getCpf().equals(cpf)) {
				
				existi = true;
				System.out.println("CPF do Banco"+conta.getCliente().getCpf());
				System.out.println("CPF do parametro "+cpf);
			} else {
				existi =  false;
			}
		}
		return existi;

	}

	public ContaDepositoDTO depositar(Conta conta) {
		Optional<Conta> conta01 = contaBD.findByConta(conta.getConta());
		Conta conta02 = conta01.get();

		if (conta.getAgencia().equals(conta02.getAgencia()) && conta.getConta().equals(conta02.getConta())) {
			conta02.creditar(conta.getSaldo());

		} else {
			throw new ContaInvalida();
		}

		contaBD.save(conta02);
		return new ContaDepositoDTO(conta02);

	}

	public ContaSaqueDTO saque(Conta conta, String cabecalhoAutorizacao) {

		Optional<String> optRecupera = jwtServico.recuperaConta(cabecalhoAutorizacao);
		Conta conta03 = validarConta(optRecupera);

		if (conta03.getSaldo() >= conta.getSaldo()) {// conta03.getSaldo() >= conta.getSaldo()
			
			conta03.debitar(conta.getSaldo());
		} else {
			throw new SaldoInsuficiente();
		}

		contaBD.save(conta03);
		return new ContaSaqueDTO(conta03);

	}

	@GetMapping("/buscarConta")
	public Conta buscarConta(String id) {
		Optional<Conta> optConta = contaBD.findByConta(id);
		if (optConta.isEmpty()) {
			throw new ContaInexistente();

		}
		return optConta.get();

	}

	public Conta validarConta(Optional<String> id) {
		if (id.isEmpty()) {
			throw new ContaInexistente();
		}
		Optional<Conta> conta = contaBD.findByConta(id.get());
		if (conta.isEmpty()) {
			throw new ContaInexistente();
		}
		return conta.get();

	}

	public List<Conta> listaDeContas() {
		return contaBD.findAll();
	}
	
	public List<Conta> listaDeContaDeSaldoMenor(double valor){
		return contaBD.findAll().stream().filter(x -> x.getSaldo() <valor).collect(Collectors.toList());
		/*
		List<Conta> conta = new ArrayList<Conta>();
		for(Conta c:contaBD.findAll()) {
			if(c.getSaldo()<valor) {
				conta.add(c);
			}
			
		}
		*/
	}

}
