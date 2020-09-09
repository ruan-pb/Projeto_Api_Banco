package Api_Banco.Entidades;

import java.sql.Date;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Size(max = 11)
	private String cpf;
	
	@Size(max = 20)
	@NotBlank
	private String nome;
	
	@Size(max = 12)
	private String telefone;
	
	@OneToOne(cascade= CascadeType.ALL)
	private Endereco endereco;
	
	/*
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataDeAniversário;
	*/
	@JsonIgnore
	@OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
	private Conta conta;
	
	public Cliente() {}
	
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	/*
	public Date getDataDeAniversário() {
		return dataDeAniversário;
	}
	public void setDataDeAniversário(Date dataDeAniversário) {
		this.dataDeAniversário = dataDeAniversário;
	}
	*/
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
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
		Cliente other = (Cliente) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		return true;
	}
	
	

}
