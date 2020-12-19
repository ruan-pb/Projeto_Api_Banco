package Api_Banco.Controladores;

import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException.BadGateway;

import Api_Banco.DTOS.ContaDepositoDTO;
import Api_Banco.DTOS.ContaSaqueDTO;
import Api_Banco.DTOS.InputCartaoDeCredito;
import Api_Banco.DTOS.InputCriarConta;
import Api_Banco.DTOS.InputDeposito;
import Api_Banco.DTOS.InputEmprestimo;
import Api_Banco.DTOS.InputTranferencia;
import Api_Banco.DTOS.ListaDTO;
import Api_Banco.DTOS.TranferenciaDTO;
import Api_Banco.Entidades.Conta;
import Api_Banco.Entidades.Emprestimo;
import Api_Banco.Exceptions.ContaInexistente;
import Api_Banco.Exceptions.ContaInvalida;
import Api_Banco.Exceptions.ContaJaExisti;
import Api_Banco.Exceptions.SaldoInsuficiente;
import Api_Banco.Servicos.ContaServico;

@RestController
@RequestMapping("/conta")
public class ContaControlador {

	@Autowired
	private ContaServico contaServico;
	
	private ModelMapper modelMapper;

	@PostMapping("/criarConta")
	public ResponseEntity<InputCriarConta> abrirConta(@RequestBody Conta conta) {

		try {
			return new ResponseEntity<InputCriarConta>(contaServico.criarConta(conta), HttpStatus.CREATED);

		} catch (ContaJaExisti e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (ContaInvalida e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

	}

	@PutMapping("/deposito")
	public ResponseEntity<ContaDepositoDTO> depositar(@RequestBody InputDeposito conta) {
		try {
		
			//Conta conta1 = toConta(conta);
			return new ResponseEntity<ContaDepositoDTO>(contaServico.depositar(conta), HttpStatus.OK);
		} catch (ContaInvalida e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping("/sacar")
	public ResponseEntity<ContaSaqueDTO> sacar(@RequestBody ContaSaqueDTO valor, @RequestHeader("Authorization") String token) {
		try {
			return new ResponseEntity<ContaSaqueDTO>(contaServico.saque(valor, token), HttpStatus.OK);
		} catch (ContaInvalida e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (SaldoInsuficiente e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping("/buscarConta/{id}")
	public ResponseEntity<Conta> buscarConta(@PathVariable String id) {
		try {
			return new ResponseEntity<Conta>(contaServico.buscarConta(id), HttpStatus.OK);
		} catch (ContaInexistente e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/lista")
	public ResponseEntity<List<ListaDTO>> lista() {
		return new ResponseEntity<List<ListaDTO>>(contaServico.listaDeContas(), HttpStatus.OK);

	}
	@GetMapping("/listacontasmenores/{valor}")
	public ResponseEntity<List<Conta>> listaDeContaComSaldoMenor(@PathVariable double valor){
		try {
			return new ResponseEntity<>(contaServico.listaDeContaDeSaldoMenor(valor),HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
		
	}
	@GetMapping("hankindDeSaldo")
	
	public ResponseEntity<List<ListaDTO>> hankingDeSaldo(){
		try {
			return new ResponseEntity<List<ListaDTO>>(contaServico.HankindDeSaldos(),HttpStatus.OK);
		}
		catch(BadGateway e) {
			return new ResponseEntity<List<ListaDTO>>(HttpStatus.BAD_GATEWAY);
		}
	}
	
	public Conta toEntity(InputTranferencia tranferencia) {
		return modelMapper.map(tranferencia, Conta.class);
	}
	public Conta toConta(InputDeposito deposito) {
		return modelMapper.map(deposito, Conta.class);
	}
	public ContaDepositoDTO toContaOur(Conta conta) {
		return modelMapper.map(conta, ContaDepositoDTO.class);
	}
	
	@PutMapping("/transferencia")
	public ResponseEntity<TranferenciaDTO> tranferencia(@RequestBody InputTranferencia tranferencia){

	
		try {
			return new ResponseEntity<TranferenciaDTO>(contaServico.tranferir(tranferencia),HttpStatus.OK);
		}
		catch(ContaInexistente e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		catch(ContaInvalida e) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		catch(SaldoInsuficiente e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	@PutMapping("/cartaoDeCredito")
	public ResponseEntity<Conta> cartaoDeCredito(@RequestBody InputCartaoDeCredito cartao){
		try {
			return new ResponseEntity<Conta>(contaServico.comprarCartaoDeCredito(cartao),HttpStatus.OK);
		}
		catch(SaldoInsuficiente e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			
		}
		catch(ContaInvalida e) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			
		}
		catch(ContaInexistente e){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	@PutMapping("/emprestimo")
	public ResponseEntity<Emprestimo> emprestimo (@RequestBody InputEmprestimo emprestimo) throws ParseException{
		try {
			return new ResponseEntity<Emprestimo>(contaServico.emprestimo(emprestimo),HttpStatus.OK);
		}
		catch(ContaInvalida e) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			
		}
		catch(ContaInexistente e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		
	}
	
}
