package Api_Banco.Controladores;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException.BadGateway;

import Api_Banco.DTOS.EnderecoCpfDTO;
import Api_Banco.DTOS.EnderecoNomeDTO;
import Api_Banco.DTOS.ListaDTOCliente;
import Api_Banco.Entidades.Cliente;
import Api_Banco.Exceptions.ContaNaoExiste;
import Api_Banco.Servicos.ClienteServico;

@RestController
@RequestMapping("/cliente")
public class ClienteControlador {
	
	@Autowired
	private ClienteServico clienteServico;
	
	
	@GetMapping("/listaAlfabetica")
	public ResponseEntity<List<ListaDTOCliente>> listaEmOrdemAlfabetica(){
		try {
			return new ResponseEntity<List<ListaDTOCliente>>(clienteServico.ListaDeClienteEmOrdemAlfabetica(),HttpStatus.OK);
		}
		catch(BadGateway e) {
			return new ResponseEntity<List<ListaDTOCliente>>(HttpStatus.BAD_GATEWAY);
		}
	}
	@GetMapping("/buscarEnderecoCpf")
	public ResponseEntity<EnderecoCpfDTO> buscarEnderecoDoClienteCpf( @RequestBody Cliente cpf){
		System.out.println(cpf.getNome());
		try {
			return new ResponseEntity<EnderecoCpfDTO>(clienteServico.encontraEnderecoPorCpf(cpf),HttpStatus.OK);
		}
		catch(ContaNaoExiste e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/buscarEnderecoNome")
	public ResponseEntity<List<EnderecoNomeDTO>> buscarEnderecoDoClienteNome( @RequestBody Cliente nome){
		try {
			return new ResponseEntity<List<EnderecoNomeDTO>>(clienteServico.encontraEnderecoPorNome(nome),HttpStatus.OK);
		}
		catch(ContaNaoExiste e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
