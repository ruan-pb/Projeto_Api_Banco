package Api_Banco.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import Api_Banco.Entidades.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, String>{

}
