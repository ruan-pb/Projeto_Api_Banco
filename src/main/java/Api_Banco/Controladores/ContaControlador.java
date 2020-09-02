package Api_Banco.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Api_Banco.DTOS.InputCriarConta;
import Api_Banco.Entidades.Conta;
import Api_Banco.Exceptions.ContaInvalida;
import Api_Banco.Exceptions.ContaJaExisti;
import Api_Banco.Servicos.ContaServico;

@RestController
@RequestMapping("/conta")
public class ContaControlador {
	
	@Autowired
	private ContaServico contaServico;
	
	
	
	@PostMapping("/criarConta")
	public ResponseEntity<InputCriarConta> abrirConta(@RequestBody Conta conta){
		try {
			return new ResponseEntity<InputCriarConta>(contaServico.criarConta(conta),HttpStatus.CREATED);
			
		}
		catch(ContaJaExisti e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		catch(ContaInvalida e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
	}

}
