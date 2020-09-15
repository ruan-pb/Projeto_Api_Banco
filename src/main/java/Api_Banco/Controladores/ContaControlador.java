package Api_Banco.Controladores;

import java.util.List;
import java.util.NoSuchElementException;

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

import Api_Banco.DTOS.ContaDepositoDTO;
import Api_Banco.DTOS.ContaSaqueDTO;
import Api_Banco.DTOS.InputCriarConta;
import Api_Banco.Entidades.Conta;
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
	public ResponseEntity<ContaDepositoDTO> depositar(@RequestBody Conta conta) {
		try {
			return new ResponseEntity<ContaDepositoDTO>(contaServico.depositar(conta), HttpStatus.OK);
		} catch (ContaInvalida e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	@PutMapping("/sacar")
	public ResponseEntity<ContaSaqueDTO> sacar(@RequestBody Conta conta, @RequestHeader ("Authorization")String token) {
		try {
			return new ResponseEntity<ContaSaqueDTO>(contaServico.saque(conta,token), HttpStatus.OK);
		} catch (ContaInvalida e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		catch(SaldoInsuficiente e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}
	
	@GetMapping("/buscarConta/{id}")
	public ResponseEntity<Conta> buscarConta(@PathVariable String id){
		try {
			return new ResponseEntity<Conta>(contaServico.buscarConta(id),HttpStatus.OK);
		}
		catch(ContaInexistente e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/lista")
	public ResponseEntity<List<Conta>> lista(){
		return new ResponseEntity<List<Conta>> (contaServico.listaDeContas(),HttpStatus.OK);
		
		
		

}
}
