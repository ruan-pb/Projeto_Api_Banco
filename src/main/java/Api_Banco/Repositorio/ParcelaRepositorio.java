package Api_Banco.Repositorio;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import Api_Banco.Entidades.Parcela;

@Repository
public interface ParcelaRepositorio extends JpaRepository<Parcela, Integer> {

	

}
