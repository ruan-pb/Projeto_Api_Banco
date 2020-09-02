package Api_Banco.Entidades;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;




@Entity
public class Conta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String conta;
	private String NumeroDaAgencia;
	private String senha;
	private double saldo;
	
	@OneToOne
	@JoinColumn(name = "conta_cliente")
	private Cliente cliente;
	
	
	
	//private Conta extrato;
	
	@OneToOne
	@JoinColumn(name="conta_emprestimo")
	private Emprestimo emprestimo;
	
	@OneToOne
	@JoinColumn(name="conta_poupanca")
	private Poupanca poupanca;
	
	@OneToOne
	@JoinColumn(name="conta_credito")
	private CartaoDeCredito credito;
	
	public Conta() {}

	
	public String getNumeroDaAgencia() {
		return NumeroDaAgencia;
	}

	public void setNumeroDaAgencia(String numeroDaAgencia) {
		NumeroDaAgencia = numeroDaAgencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String numero_Da_Conta) {
		conta = numero_Da_Conta;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Emprestimo getEmmprestimo() {
		return emprestimo;
	}

	public void setEmmprestimo(Emprestimo emmprestimo) {
		this.emprestimo = emmprestimo;
	}

	public Poupanca getPoupanca() {
		return poupanca;
	}

	public void setPoupanca(Poupanca poupanca) {
		this.poupanca = poupanca;
	}

	
	public CartaoDeCredito getCredito() {
		return credito;
	}

	public void setCredito(CartaoDeCredito credito) {
		this.credito = credito;
	}
/*
	public void adicionarElementoNoExtrato(Conta conta) {
		this.extrato.add(conta);
		
	}
	public void RemoverElementoNoExtrato(Conta conta) {
		this.extrato.remove(conta);
		
	}
	*/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((NumeroDaAgencia == null) ? 0 : NumeroDaAgencia.hashCode());
		result = prime * result + ((conta == null) ? 0 : conta.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conta other = (Conta) obj;
		if (NumeroDaAgencia == null) {
			if (other.NumeroDaAgencia != null)
				return false;
		} else if (!NumeroDaAgencia.equals(other.NumeroDaAgencia))
			return false;
		if (conta == null) {
			if (other.conta != null)
				return false;
		} else if (!conta.equals(other.conta))
			return false;
		return true;
	}
	public void debitar(double valor) {
		this.saldo -=valor;
	}
	public void creditar(double valor) {
		this.saldo += valor;
	}
	public boolean isValid() {
		return !this.NumeroDaAgencia.isBlank()&&!this.conta.isBlank()&&!this.cliente.getCpf().isBlank();
	}
	
	
	
	

}
