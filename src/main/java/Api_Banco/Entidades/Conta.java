package Api_Banco.Entidades;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;






@Entity
public class Conta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String conta;
	private String Agencia;
	private String senha;
	private double saldo;
	
	
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "conta_cliente")
	private Cliente cliente;
	
	
	
	
	
	public Conta() {}
	
	@OneToOne
	@JoinColumn(name="conta_emprestimo")
	private Emprestimo emprestimo;
	
	@OneToOne
	@JoinColumn(name="conta_poupanca")
	private Poupanca poupanca;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="conta_credito")
	private CartaoDeCredito credito;
	
	

	
	public String getAgencia() {
		return this.Agencia;
	}

	public void setAgencia(String numeroDaAgencia) {
		this.Agencia = numeroDaAgencia;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Agencia == null) ? 0 : Agencia.hashCode());
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
		if (Agencia == null) {
			if (other.Agencia != null)
				return false;
		} else if (!Agencia.equals(other.Agencia))
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
		return !this.Agencia.isBlank()&&!this.conta.isBlank()&&!this.cliente.getCpf().isBlank();
	}

	@Override
	public String toString() {
		return "Conta [conta=" + conta + ", Agencia=" + Agencia + ", senha=" + senha +", saldo=" +  String.format("%.2f", this.saldo)+ ", cliente="
				+ cliente + ", emprestimo=" + emprestimo + ", poupanca=" + poupanca + ", credito=" + credito + "]";
	}
	
	
	
	
	

}
