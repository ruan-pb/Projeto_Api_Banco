package Api_Banco.Servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Api_Banco.DTOS.InputCriarConta;
import Api_Banco.Entidades.Conta;
import Api_Banco.Exceptions.ContaInexistente;
import Api_Banco.Exceptions.ContaInvalida;
import Api_Banco.Repositorio.ContaRepositorio;

@Service
public class ContaServico {

	@Autowired
	private ContaRepositorio contaBD;

	public InputCriarConta criarConta(Conta conta) {
		/*
		if (!conta.isValid()) {
			throw new ContaInvalida();
		}
*/
		boolean existiConta = existiConta(conta.getConta());
		if (existiConta == true) {
			throw new ContaInvalida();
		}
		contaBD.save(conta);
		return new InputCriarConta(conta);

	}

	public Conta getConta(String numeroConta) {
		Optional<Conta> optConta = contaBD.findByConta(numeroConta);
		if (optConta.isEmpty()) {
			throw new ContaInexistente();
		}
		return optConta.get();
	}

	public boolean existiConta(String numeroConta) {
		boolean existi = false;
		for (Conta conta : this.contaBD.findAll()) {

			if (conta.getConta().equals(numeroConta)) {
				return true;
			} else {
				return false;
			}
		}
		return existi;

	}

}
