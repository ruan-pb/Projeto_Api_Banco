package Api_Banco.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Api_Banco.DTOS.ContaLoginDTO;
import Api_Banco.Exceptions.ContaInvalida;
import Api_Banco.Servicos.JWTServico;
import Api_Banco.Servicos.LoginResponse;


@RestController
public class LoginControlador {
	
	
	@Autowired
	private JWTServico jwtServico;

	@PostMapping("/autorizacao/login")
	public ResponseEntity<LoginResponse> autenticarUsuario(@RequestBody ContaLoginDTO usuario) {
		try {
			return new ResponseEntity<LoginResponse>(jwtServico.autentica(usuario), HttpStatus.OK);
		} catch (ContaInvalida e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

}
