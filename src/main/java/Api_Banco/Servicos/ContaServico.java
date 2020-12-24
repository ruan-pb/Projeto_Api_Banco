package Api_Banco.Servicos;

import java.io.IOException;

import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Api_Banco.DTOS.CartaoDTO;
import Api_Banco.DTOS.ContaDepositoDTO;
import Api_Banco.DTOS.ContaSaqueDTO;
import Api_Banco.DTOS.InputCartaoDeCredito;
import Api_Banco.DTOS.InputCriarConta;
import Api_Banco.DTOS.InputDeposito;
import Api_Banco.DTOS.InputEmprestimo;
import Api_Banco.DTOS.InputPoupancaDTO;
import Api_Banco.DTOS.InputTranferencia;
import Api_Banco.DTOS.ListaDTO;
import Api_Banco.DTOS.PoupancaDTO;
import Api_Banco.DTOS.TranferenciaDTO;
import Api_Banco.Entidades.CartaoDeCredito;
import Api_Banco.Entidades.Conta;
import Api_Banco.Entidades.Emprestimo;
import Api_Banco.Entidades.Parcela;
import Api_Banco.Entidades.Poupanca;
import Api_Banco.Exceptions.ContaNaoExiste;
import Api_Banco.Exceptions.ContaInvalida;
import Api_Banco.Exceptions.ContaJaExisti;
import Api_Banco.Exceptions.LimiteInsuficiente;
import Api_Banco.Exceptions.SaldoInsuficiente;
import Api_Banco.Repositorio.CartaoRepositorio;
import Api_Banco.Repositorio.ContaRepositorio;
import Api_Banco.Repositorio.EmprestimoRepositorio;
import Api_Banco.Repositorio.ParcelaRepositorio;
import Api_Banco.Repositorio.PoupancaRepositorio;

@Service
public class ContaServico {

	@Autowired
	private ContaRepositorio contaBD;

	@Autowired
	private JWTServico jwtServico;


	@Autowired
	private ParcelaRepositorio parcelaBD;

	@Autowired
	private CartaoRepositorio cartaoBD;

	@Autowired
	private EmprestimoRepositorio emprestimoBD;

	@Autowired
	private PoupancaRepositorio poupancaBD;

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
			throw new ContaNaoExiste();
		}
		return optConta.get();
	}

	public boolean existiConta(String cpf) {
		boolean existi = false;
		for (Conta conta : this.contaBD.findAll()) {

			if (conta.getCliente().getCpf().equals(cpf)) {

				existi = true;
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



		if (conta03.getSaldo() >= valor.getValor()) {

			conta03.debitar(valor.getValor());
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
			throw new ContaNaoExiste();

		}
		return optConta.get();

	}

	public Conta validarConta(Optional<String> id) {
		if (id.isEmpty()) {
			throw new ContaNaoExiste();
		}
		Optional<Conta> conta = contaBD.findByConta(id.get());
		if (conta.isEmpty()) {
			throw new ContaNaoExiste();
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

		if (ContaOrigem.isPresent() && ContaDestino.isPresent()) {
			if (ContaOrigem.get().getSenha().equals(transferencia.getSenha())) {
				if (ContaOrigem.get().getSaldo() >= transferencia.getValor()) {

					double valorDeTranferencia = transferencia.getValor();
					ContaOrigem.get().debitar(transferencia.getValor());
					ContaDestino.get().creditar(valorDeTranferencia);

					contaBD.save(ContaOrigem.get());
					contaBD.save(ContaDestino.get());

				} else {
					throw new SaldoInsuficiente();
				}

			} else {
				throw new ContaInvalida();
			}

		} else {
			throw new ContaNaoExiste();

		}
		return new TranferenciaDTO(transferencia);

	}

	

	public CartaoDTO comprarCartaoDeCredito(InputCartaoDeCredito cartao) {
		Optional<Conta> conta = contaBD.findByConta(cartao.getConta());

		Parcela parcela = new Parcela();
		Date faturas = Date.valueOf(LocalDate.now().plusMonths(cartao.getParcelas()));
		CartaoDeCredito cartaoDeCredito = new CartaoDeCredito();
		Date DataDaCompra = Date.valueOf(LocalDate.now());
		if (conta.isPresent()) {
			if (conta.get().getConta().equals(cartao.getConta())) {
				

				if (conta.get().getCredito() == null) {

					if (cartao.getValor() <= 1000) {
						cartaoDeCredito.setConta(conta.get());
						cartaoDeCredito.setValor(cartao.getValor());
						cartaoDeCredito.setFaturaAtual(cartao.getValor() / cartao.getParcelas());
						cartaoDeCredito.setLimiteDisponivel(cartaoDeCredito.getLimiteDisponivel() - cartao.getValor());
						cartaoDeCredito.setNumeroDoCartao(cartao.getNumeroDoCartao());
						cartaoDeCredito.setDataDaCompra(DataDaCompra);
						cartaoBD.save(cartaoDeCredito);
						conta.get().setCredito(cartaoDeCredito);

					} else {
						throw new SaldoInsuficiente();
					}
				} else {

					if (conta.get().getCredito().getLimiteDisponivel() >= cartao.getValor()) {

						cartaoDeCredito.setId(conta.get().getCredito().getId());
						cartaoDeCredito.setValor(cartao.getValor());
						cartaoDeCredito.setFaturaAtual(cartao.getValor());
						cartaoDeCredito.alterarFaturalAtual(conta.get().getCredito().getFaturaAtual() + (cartao.getValor() / cartao.getParcelas()));
						cartaoDeCredito.setLimiteDisponivel(conta.get().getCredito().getLimiteDisponivel() - cartao.getValor());
						cartaoDeCredito.setNumeroDoCartao(conta.get().getCredito().getNumeroDoCartao());
						cartaoDeCredito.setDataDaCompra(DataDaCompra);
						cartaoBD.save(cartaoDeCredito);
						conta.get().setCredito(cartaoDeCredito);

					} else {
						throw new LimiteInsuficiente();
					}

				}

				parcela.setCredito(cartaoDeCredito);
				parcela.setQuantidadeDeParcelas(cartao.getParcelas());
				parcela.setValor(cartaoDeCredito.getValor());
				parcela.setDataDeVencimento(faturas);
				
				//cartaoDeCredito.passandoCartao(parcela);

				conta.get().setCredito(cartaoDeCredito);
				conta.get().getCredito().setConta(cartaoDeCredito.getConta());
				conta.get().getCredito().setNumeroDoCartao(cartaoDeCredito.getNumeroDoCartao());

				contaBD.save(conta.get());
				parcelaBD.save(parcela);

			} else {
				throw new ContaInvalida();
			}

		} else {
			throw new ContaNaoExiste();
		}
		return new CartaoDTO(cartaoDeCredito);

	}

	public Emprestimo emprestimo(InputEmprestimo emprestimo) throws ParseException {
		Date dataEmprestimo = Date.valueOf(LocalDate.now().plusMonths(2));
		Date terminaPagarEmprestimo = Date.valueOf(LocalDate.now().plusMonths(emprestimo.getQuantidadeDeParcelas()+2));
		
		Optional<Conta> conta = contaBD.findByConta(emprestimo.getId());
		Emprestimo emprestimo01 = new Emprestimo();

		if(conta.isPresent()) {
			if(conta.get().getEmmprestimo()==null) {
				
				
				emprestimo01.setConta(conta.get());
				double valorTotal = emprestimo.getValor()*emprestimo01.getJuros();
				emprestimo01.setValor(valorTotal);
				emprestimo01.setValorDeCadaParcela(valorTotal/emprestimo.getQuantidadeDeParcelas());
				emprestimo01.setDataDoEmprestimo(dataEmprestimo);
				emprestimo01.setUltimaParcela(terminaPagarEmprestimo);
				emprestimo01.setLimite(emprestimo01.getLimite()-emprestimo.getValor());
				emprestimo01.setQuantidadeDeParcelas(emprestimo.getQuantidadeDeParcelas());
	
				emprestimoBD.save(emprestimo01);
	
				conta.get().setEmmprestimo(emprestimo01);
			}
			
			else {
				throw new LimiteInsuficiente();
			}
			}



		contaBD.save(conta.get());
		return emprestimo01;

	}

	public PoupancaDTO poupanca(InputPoupancaDTO poupanca) {
		Optional<Conta> conta = contaBD.findByConta(poupanca.getId());
		Date dataEmprestimo = Date.valueOf(LocalDate.now());
		Poupanca poupancaP = new Poupanca();

		if (conta.isPresent()) {
			if(conta.get().getPoupanca()==null) {
				poupancaP.setConta(conta.get());
				poupancaP.setDataDeAbertura(dataEmprestimo);
				poupancaP.setSaldo(poupanca.getDeposito() * poupancaP.getJuros());
				poupancaBD.save(poupancaP);
	
				conta.get().setPoupanca(poupancaP);
			}
			else {
				poupancaP.setId(conta.get().getPoupanca().getId());
				poupancaP.setDataDeAbertura(conta.get().getPoupanca().getDataDeAbertura());
				poupancaP.setSaldo(poupancaP.getSaldo() +(poupanca.getDeposito()*poupancaP.getJuros()));
				poupancaBD.save(poupancaP);
	
				conta.get().setPoupanca(poupancaP);
			}
		} 
		else {
			throw new ContaInvalida("conta não encontrada");
		}
		contaBD.save(conta.get());
		return new PoupancaDTO(poupancaP);
	}

	public Conta getOne(String contaId) {
		Optional<Conta> conta = contaBD.findByConta(contaId);
		if (conta != null) {
			return conta.get();
		}
		throw new ContaInvalida("Conta invalida");

	}


}
