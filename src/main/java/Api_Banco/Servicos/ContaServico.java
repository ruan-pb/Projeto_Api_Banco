package Api_Banco.Servicos;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Api_Banco.DTOS.ContaDepositoDTO;
import Api_Banco.DTOS.ContaSaqueDTO;
import Api_Banco.DTOS.InputCriarConta;
import Api_Banco.DTOS.InputTranferencia;
import Api_Banco.DTOS.ListaDTO;
import Api_Banco.DTOS.TranferenciaDTO;
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
	
	private ModelMapper modelMapper;

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
		//conta.getCliente().setNome(conta.getCliente().getNome().toLowerCase());
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

	public List<ListaDTO> listaDeContas() {
		List<Conta> lista = contaBD.findAll();
		List<ListaDTO> listaDto = lista.stream().map(x -> new ListaDTO(x)).collect(Collectors.toList());
		return listaDto;
	}
	
	public List<Conta> listaDeContaDeSaldoMenor(double valor){
		return contaBD.findAll().stream().filter(x -> x.getSaldo() <valor).collect(Collectors.toList());
	
	}
	public List<ListaDTO> HankindDeSaldos(){
		List<Conta> lista = contaBD.findByOrderBySaldoDesc();
		List<ListaDTO> listaDto = lista.stream().map(x -> new ListaDTO(x)).collect(Collectors.toList());
		return listaDto;
	}

	public TranferenciaDTO tranferir(InputTranferencia tranferencia) {
		
		Optional<Conta> ContaOrigem = contaBD.findByConta(tranferencia.getContaOrigem());
		Optional<Conta> ContaDestino = contaBD.findByConta(tranferencia.getContaDestino());
		
		System.out.println("conta origem "+ContaOrigem.get().getConta());
		
		System.out.println("conta destino "+ContaDestino.get().getConta());
		
	
		
		
		if(ContaOrigem.isPresent() && ContaDestino.isPresent()) {
			if(ContaOrigem.get().getSenha().equals(tranferencia.getSenha())) {
				
				double valorDeTranferencia = tranferencia.getValor();
				System.out.println(valorDeTranferencia);
				ContaOrigem.get().debitar(tranferencia.getValor());
				System.out.println("conta origem "+ContaOrigem.get().getSaldo());
				ContaDestino.get().creditar(valorDeTranferencia);
				System.out.println("conta destino "+ContaDestino.get().getSaldo());

				
				contaBD.save(ContaOrigem.get());
				contaBD.save(ContaDestino.get());
				
			}
			else {
				throw new ContaInvalida();
			}
				
		}
		else{
			throw new ContaInexistente();
			
		}
		return new TranferenciaDTO(tranferencia);
		
	}
	public Conta toEntity(InputTranferencia tranferencia) {
		return modelMapper.map(tranferencia, Conta.class);
	}
	

}
