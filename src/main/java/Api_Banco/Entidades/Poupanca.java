package Api_Banco.Entidades;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Poupanca {
	@Id
	private Integer Id; 
	private double Saldo;
	private double Juros;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date DataDeAbertura;
	
	public Poupanca() {}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public double getSaldo() {
		return Saldo;
	}

	public void setSaldo(double saldo) {
		Saldo = saldo;
	}

	public double getJuros() {
		return Juros;
	}

	public void setJuros(double juros) {
		Juros = juros;
	}

	public Date getDataDeAbertura() {
		return DataDeAbertura;
	}

	public void setDataDeAbertura(Date dataDeAbertura) {
		DataDeAbertura = dataDeAbertura;
	}
	
	public double ganhosComJuros() {
		return this.Saldo*this.Juros;
	}

}
