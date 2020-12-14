package Api_Banco.Entidades;

import java.sql.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Columns;



@Entity
public class Parcela {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private int QuantidadeDeParcelas;
	
	@Column(name = "ultima_parcela")
	private Date DataDeVencimento;
	private double valor;
	
	@ManyToOne
	@JoinColumn(name="parcela_emprestimo")
	private Emprestimo emprestimo;
	
	@ManyToOne
	@JoinColumn(name="parcela_credito")
	private CartaoDeCredito credito;
	
	

	
	public Parcela() {}
	
	

	public Parcela(int quantidadeDeParcelas, Date dataDeVencimento, double valordaParcela) {
		QuantidadeDeParcelas = quantidadeDeParcelas;
		valor = valordaParcela;
	}
	public Parcela(int quantidadeDeParcelas, double valordaParcela) {
		QuantidadeDeParcelas = quantidadeDeParcelas;
		valor = valordaParcela;
	}



	public int getQuantidadeDeParcelas() {
		return QuantidadeDeParcelas;
	}

	public void setQuantidadeDeParcelas(int quantidadeDeParcelas) {
		QuantidadeDeParcelas = quantidadeDeParcelas;
	}

	public Date getDataDeVencimento() {
		return DataDeVencimento;
	}

	public void setDataDeVencimento(Date dataDeVencimento) {
		DataDeVencimento = dataDeVencimento;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valorr) {
		valor = valorr;
	}



	@Override
	public String toString() {
		return "Parcela [id=" + id + ", QuantidadeDeParcelas=" + QuantidadeDeParcelas + ", DataDeVencimento="
				+ DataDeVencimento + ", valor=" + valor + ", credito=" + credito + "]";
	}
	
	
	
	
	

	
	

}
