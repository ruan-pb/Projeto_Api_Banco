package Api_Banco.Servicos;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Api_Banco.DTOS.ContaLoginDTO;
import Api_Banco.Entidades.Conta;
import Api_Banco.Repositorio.ContaRepositorio;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Service
public class JWTServico {

	public static final String TOKEN_KEY = "ja pode sair?";
	
	
	
	@Autowired
	private ContaRepositorio contaBD;
	
	public LoginResponse autentica(ContaLoginDTO conta) {
		String mensagemDeErro = "Usuario/Senha Inválidos";
		Optional<Conta> optConta = contaBD.findByConta(conta.getNumeroConta());
		if(optConta.isPresent() && conta.getSenha().equals(optConta.get().getSenha())) {
			return new LoginResponse(gerarToken(conta));
		}
		return new LoginResponse(mensagemDeErro);
	}
	
	private String gerarToken(ContaLoginDTO conta) {
		String token = Jwts.builder().setSubject(conta.getNumeroConta()).signWith(SignatureAlgorithm.HS512, TOKEN_KEY).setExpiration(new Date(System.currentTimeMillis()+5*60*1000)).compact();
		return token;
	}
	
	public Optional<String> recuperaConta(String cabecalhoAutorizacao){
		if(cabecalhoAutorizacao == null || !cabecalhoAutorizacao.startsWith("Bearer")) {
			throw new SecurityException();
		}
		String token = cabecalhoAutorizacao.substring(Api_Banco.Filtros.filtroToken.TOKEN_INDEX);
		String subject = null;
		
		try {
			subject = Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token).getBody().getSubject();
		}
		catch(SignatureException e) {
			throw new SecurityException("Token inválido ou inspirado");
		}
		return Optional.of(subject);
		
	}
	public String getConta(String autorizacao) {
		Optional<String> NumeroConta = recuperaConta(autorizacao);
		if(NumeroConta.isEmpty()) {
			throw new SecurityException();
		}
		return NumeroConta.get();
	}
	
	

}
