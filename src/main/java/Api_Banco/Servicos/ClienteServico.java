package Api_Banco.Servicos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Api_Banco.DTOS.EnderecoCpfDTO;
import Api_Banco.DTOS.EnderecoNomeDTO;
import Api_Banco.DTOS.ListaDTOCliente;
import Api_Banco.Entidades.Cliente;
import Api_Banco.Exceptions.ContaInexistente;
import Api_Banco.Repositorio.ClienteRepositorio;

@Service
public class ClienteServico {
	
	@Autowired
	private ClienteRepositorio clienteBD;
	
	
	
	
	public List<ListaDTOCliente> ListaDeClienteEmOrdemAlfabetica(){
		List<Cliente> lista = clienteBD.findByOrderByNomeAsc();
		List<ListaDTOCliente> listaDto = lista.stream().map(x -> new ListaDTOCliente(x)).collect(Collectors.toList());
		return listaDto;
	}
	
	public EnderecoCpfDTO encontraEnderecoPorCpf(String cpf) {
		Optional<Cliente> cli = clienteBD.findByCpf(cpf);
		
		if(cli.isEmpty()) {
			throw new ContaInexistente();
		}
		return new EnderecoCpfDTO(cli);
		
		
	}
	public List<EnderecoNomeDTO> encontraEnderecoPorNome(String nome) {
	
		//Optional<Cliente> cli = clienteBD.findByNome(nome.toLowerCase());
		List<Cliente> lista = new ArrayList<>();
		for(Cliente cliente:clienteBD.findAll()){
			if(cliente.getNome().toLowerCase().startsWith(nome.toLowerCase())) {
				lista.add(cliente);
			}
		}
		
		List<EnderecoNomeDTO> listaDTO = lista.stream().map(x-> new EnderecoNomeDTO(x)).collect(Collectors.toList());
		return listaDTO;
		
		
	}

}
