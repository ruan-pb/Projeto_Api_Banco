package Api_Banco.Controladores.Exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import Api_Banco.Exceptions.ContaInexistente;
import Api_Banco.Exceptions.ContaInvalida;
import Api_Banco.Exceptions.ContaJaExisti;
import Api_Banco.Exceptions.LimiteInsuficiente;
import Api_Banco.Exceptions.SaldoInsuficiente;


@ControllerAdvice
public class ExceptionHandle {
	
	@ExceptionHandler(ContaInvalida.class)
	public ResponseEntity<StandardError> ContaInvalidaException(ContaInvalida e,HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Conta Invalida", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	@ExceptionHandler(ContaInexistente.class)
	public ResponseEntity<StandardError> ContaInexistenteException(ContaInexistente e,HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Conta não existe", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	@ExceptionHandler(ContaJaExisti.class)
	public ResponseEntity<StandardError> ContaJaExistiException(ContaJaExisti e,HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Essa Conta já existi", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	@ExceptionHandler(SaldoInsuficiente.class)
	public ResponseEntity<StandardError> SaldoInsuficienteException(SaldoInsuficiente e,HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Você não tem saldo suficiente, para executar essa operação :(", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	@ExceptionHandler(LimiteInsuficiente.class)
	public ResponseEntity<StandardError> SaldoInsuficienteException(LimiteInsuficiente e,HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Você não tem limite suficiente, para executar Empréstimo/cartaoDeCredito :(", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	

}
