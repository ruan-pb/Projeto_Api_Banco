package Api_Banco.Repositorio;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Api_Banco.Entidades.Poupanca;

@Repository
public interface PoupancaRepositorio extends JpaRepository<Poupanca, Integer> {

	

}
