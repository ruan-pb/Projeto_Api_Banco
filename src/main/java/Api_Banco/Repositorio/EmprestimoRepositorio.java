package Api_Banco.Repositorio;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Api_Banco.Entidades.Emprestimo;

@Repository
public interface EmprestimoRepositorio extends JpaRepository<Emprestimo, Integer> {

	

}
