package Api_Banco.Repositorio;





import org.springframework.data.jpa.repository.JpaRepository;

import Api_Banco.Entidades.CartaoDeCredito;


public interface CartaoRepositorio extends JpaRepository<CartaoDeCredito, Integer>{
	
	

}
