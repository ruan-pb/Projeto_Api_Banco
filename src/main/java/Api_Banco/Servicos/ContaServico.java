package Api_Banco.Servicos;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
import Api_Banco.DTOS.InputCartaoDeCredito;
import Api_Banco.DTOS.InputCriarConta;
import Api_Banco.DTOS.InputDeposito;
import Api_Banco.DTOS.InputTranferencia;
import Api_Banco.DTOS.ListaDTO;
import Api_Banco.DTOS.TranferenciaDTO;
import Api_Banco.Entidades.CartaoDeCredito;
import Api_Banco.Entidades.Conta;
import Api_Banco.Entidades.Parcela;
import Api_Banco.Exceptions.ContaInexistente;
import Api_Banco.Exceptions.ContaInvalida;
import Api_Banco.Exceptions.ContaJaExisti;
import Api_Banco.Exceptions.SaldoInsuficiente;
import Api_Banco.Repositorio.CartaoRepositorio;
import Api_Banco.Repositorio.ContaRepositorio;
import Api_Banco.Repositorio.ParcelaRepositorio;

@Service
public class ContaServico {

	@Autowired
	private ContaRepositorio contaBD;

	@Autowired
	private JWTServico jwtServico;

	private ModelMapper modelMapper;

	@Autowired
	private ParcelaRepositorio parcelaBD;

	@Autowired
	private CartaoRepositorio cartaoBD;

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
		// conta.getCliente().setNome(conta.getCliente().getNome().toLowerCase());
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
				System.out.println("CPF do Banco" + conta.getCliente().getCpf());
				System.out.println("CPF do parametro " + cpf);
			} else {
				existi = false;
			}
		}
		return existi;

	}

	public ContaDepositoDTO depositar(InputDeposito conta) {
		Optional<Conta> conta01 = contaBD.findByConta(conta.getConta());
		Conta conta02 = conta01.get();

		if (conta.getAgencia().equals(conta02.getAgencia()) && conta.getConta().equals(conta02.getConta())) {
			conta02.creditar(conta.getDeposito());

		} else {
			throw new ContaInvalida();
		}

		contaBD.save(conta02);
		return new ContaDepositoDTO(conta02);

	}

	public ContaSaqueDTO saque(ContaSaqueDTO valor, String cabecalhoAutorizacao) {

		Optional<String> optRecupera = jwtServico.recuperaConta(cabecalhoAutorizacao);

		Conta conta03 = validarConta(optRecupera);

		System.out.println("conta03" + conta03.getSaldo());

		if (conta03.getSaldo() >= valor.getValor()) {// conta03.getSaldo() >= conta.getSaldo()

			conta03.debitar(valor.getValor());
			System.out.println(conta03.getSaldo());
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

	public List<Conta> listaDeContaDeSaldoMenor(double valor) {
		return contaBD.findAll().stream().filter(x -> x.getSaldo() < valor).collect(Collectors.toList());

	}

	public List<ListaDTO> HankindDeSaldos() {
		List<Conta> lista = contaBD.findByOrderBySaldoDesc();

		List<ListaDTO> listaDto = lista.stream().map(x -> new ListaDTO(x)).collect(Collectors.toList());
		return listaDto;
	}

	public TranferenciaDTO tranferir(InputTranferencia transferencia) {

		Optional<Conta> ContaOrigem = contaBD.findByConta(transferencia.getContaOrigem());
		Optional<Conta> ContaDestino = contaBD.findByConta(transferencia.getContaDestino());

		System.out.println("conta origem " + ContaOrigem.get().getConta());

		System.out.println("conta destino " + ContaDestino.get().getConta());

		if (ContaOrigem.isPresent() && ContaDestino.isPresent()) {
			if (ContaOrigem.get().getSenha().equals(transferencia.getSenha())) {
				if (ContaOrigem.get().getSaldo() >= transferencia.getValor()) {

					double valorDeTranferencia = transferencia.getValor();
					System.out.println(valorDeTranferencia);
					ContaOrigem.get().debitar(transferencia.getValor());
					System.out.println("conta origem " + ContaOrigem.get().getSaldo());
					ContaDestino.get().creditar(valorDeTranferencia);
					System.out.println("conta destino " + ContaDestino.get().getSaldo());

					contaBD.save(ContaOrigem.get());
					contaBD.save(ContaDestino.get());

				} else {
					throw new SaldoInsuficiente();
				}

			} else {
				throw new ContaInvalida();
			}

		} else {
			throw new ContaInexistente();

		}
		return new TranferenciaDTO(transferencia);

	}

	public Conta toEntity(InputTranferencia tranferencia) {
		return modelMapper.map(tranferencia, Conta.class);

	}

	public Conta comprarCartaoDeCredito(InputCartaoDeCredito cartao) {
		Optional<Conta> conta = contaBD.findByConta(cartao.getConta());
		//List<Parcela> proximasFaturas = new ArrayList<>();
		
		Parcela parcela = new Parcela();
		Date parcelas = Date.valueOf(LocalDate.now().plusMonths(cartao.getParcelas()));
		
		Date DataDaCompra = Date.valueOf(LocalDate.now());
		if (conta.isPresent()) {
			if (conta.get().getConta().equals(cartao.getConta())) {
				CartaoDeCredito cartaoDeCredito = new CartaoDeCredito();
			
				if(conta.get().getCredito()==null) {
				

					cartaoDeCredito.setConta(conta.get());
					cartaoDeCredito.setValor(cartao.getValor());
					cartaoDeCredito.setFaturaAtual(cartao.getValor());
					cartaoDeCredito.setLimiteDisponivel(cartao.getValor());
					cartaoDeCredito.setNumeroDoCartao(cartao.getNumeroDoCartao());
					cartaoDeCredito.setDataDaCompra(DataDaCompra);
					cartaoBD.save(cartaoDeCredito);
					conta.get().setCredito(cartaoDeCredito);
					
					
				}
				else {
					cartaoDeCredito.setId(Integer.parseInt(cartao.getConta()));
					cartaoDeCredito.setValor(cartao.getValor());
					System.out.println("Fatura Atual "+conta.get().getCredito().getFaturaAtual());
					System.out.println("limite disponivel "+conta.get().getCredito().getLimiteDisponivel());
					System.out.println("resultado da fatural atual"+(cartao.getValor()-conta.get().getCredito().getFaturaAtual()));
					System.out.println("resultado do limite disponivel atual"+(conta.get().getCredito().getLimiteDisponivel()-cartao.getValor()));
					cartaoDeCredito.setFaturaAtual(conta.get().getCredito().getFaturaAtual()+cartao.getValor());
					cartaoDeCredito.setLimiteDisponivel(conta.get().getCredito().getLimiteDisponivel()-cartao.getValor());
					cartaoDeCredito.setNumeroDoCartao(conta.get().getCredito().getNumeroDoCartao());
					cartaoDeCredito.setDataDaCompra(DataDaCompra);
					cartaoBD.save(cartaoDeCredito);
					conta.get().setCredito(cartaoDeCredito);
					
				}
				

				

				parcela.setCredito(cartaoDeCredito);
				parcela.setQuantidadeDeParcelas(cartao.getParcelas());
				
				parcela.setValor(cartaoDeCredito.getValor());
				
				parcela.setDataDeVencimento(parcelas);
				
				//proximasFaturas.add(parcela);
				//cartaoDeCredito.setProximasFaturas(proximasFaturas);
				
				
				conta.get().setCredito(cartaoDeCredito);
				conta.get().getCredito().setFaturaAtual(cartaoDeCredito.getFaturaAtual() / cartao.getParcelas());
				conta.get().getCredito().setConta(cartaoDeCredito.getConta());
				conta.get().getCredito().setNumeroDoCartao(cartaoDeCredito.getNumeroDoCartao());
				
				
				contaBD.save(conta.get());
				parcelaBD.save(parcela);

			} else {
				throw new ContaInvalida();
			}

		} else {
			throw new ContaInexistente();
		}
		return conta.get();

	}
	
	public Conta getOne(String contaId) {
		Optional<Conta> conta = contaBD.findByConta(contaId);
		if(conta != null) {
			return conta.get();
		}
		throw new ContaInvalida("Conta invalida");
		
	}

	public CartaoDeCredito toEntity(InputCartaoDeCredito cartao) {
		return modelMapper.map(cartao, CartaoDeCredito.class);

	}

	public List<Parcela> adicionandoParcela(List<Parcela> parcelas) {
		List<Parcela> parce = new ArrayList<>();
		for (Parcela pp : parcelas) {
			parce.add(pp);
			parcelaBD.save(pp);
		}
		return parce;
	}

	public void adicionandoParcela(String id, Integer k) {
		Optional<Conta> conta = contaBD.findByConta(id);
		if (conta.isPresent()) {
			Date parcelas = Date.valueOf(LocalDate.now().plusMonths(k));
			System.out.println("data " + parcelas);
			conta.get().getCredito().setDataDaCompra(parcelas); // o erro é aqui
			System.out.println(conta.get().getCredito().getDataDaCompra());
		} else {
			System.out.println("Não existe conta");
		}
	}

}
