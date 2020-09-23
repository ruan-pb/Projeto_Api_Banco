package Api_Banco.Repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Api_Banco.Entidades.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, String>{
	List<Cliente> findByOrderByNomeAsc();
	Optional<Cliente> findByEndereco(String cpf);
	Optional<Cliente> findByCpf(String cpf);
	Optional<Cliente> findByNome(String nome);
	

}
