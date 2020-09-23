package Api_Banco.Repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Api_Banco.Entidades.Conta;

@Repository
public interface ContaRepositorio extends JpaRepository<Conta, String> {
	Optional<Conta> findByConta(String Numero_Da_Conta);
	List<Conta> findByOrderBySaldoDesc();
	

}
