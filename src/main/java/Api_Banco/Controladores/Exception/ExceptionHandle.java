package Api_Banco.Controladores.Exception;

import javax.servlet.http.HttpServletRequest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import Api_Banco.Exceptions.ContaNaoExiste;
import Api_Banco.Exceptions.ContaInvalida;
import Api_Banco.Exceptions.ContaJaExisti;
import Api_Banco.Exceptions.LimiteDeEmprestimo;
import Api_Banco.Exceptions.LimiteInsuficiente;
import Api_Banco.Exceptions.SaldoInsuficiente;


@ControllerAdvice
public class ExceptionHandle {
	
	@ExceptionHandler(ContaInvalida.class)
	public ResponseEntity<StandardError> ContaInvalidaException(ContaInvalida e,HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Conta Invalida", "Algum dado informado, está incorreto :(", request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	@ExceptionHandler(ContaNaoExiste.class)
	public ResponseEntity<StandardError> ContaInexistenteException(ContaNaoExiste e,HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Conta não existe", "Não conseguimos localizar a conta informada", request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	@ExceptionHandler(ContaJaExisti.class)
	public ResponseEntity<StandardError> ContaJaExistiException(ContaJaExisti e,HttpServletRequest request){
		HttpStatus status = HttpStatus.CONFLICT;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Conflito", "Impossivel cadastrar novo Usuario, pois essa conta já existi", request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	@ExceptionHandler(SaldoInsuficiente.class)
	public ResponseEntity<StandardError> SaldoInsuficienteException(SaldoInsuficiente e,HttpServletRequest request){
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Saldo Insuficiente", "Você não tem saldo suficiente, para executar essa operação :(", request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	@ExceptionHandler(LimiteInsuficiente.class)
	public ResponseEntity<StandardError> SaldoInsuficienteException(LimiteInsuficiente e,HttpServletRequest request){
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Você não tem limite suficiente, para executar Empréstimo/cartaoDeCredito :(", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	@ExceptionHandler(LimiteDeEmprestimo.class)
	public ResponseEntity<StandardError> LimiteDeEmprestimoException(LimiteInsuficiente e,HttpServletRequest request){
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "No momento, o banco só está disponibilizando somente 1'um' empréstimo por pessoa :(", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	

}
