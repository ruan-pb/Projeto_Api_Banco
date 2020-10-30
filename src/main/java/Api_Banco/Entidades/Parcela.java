package Api_Banco.Entidades;

import java.sql.Date;
//import java.text.ParseException;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Parcela {
	
	@Id
	private Integer id;
	private int QuantidadeDeParcelas;
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
		DataDeVencimento = dataDeVencimento;
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
	/*
	public Date adicionarMes(Date data,int mes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.MONTH, mes);
		return (Date) cal.getTime();
	}
	
	
	public void processamentoDeCompra(CartaoDeCredito cartao) throws ParseException {
		double valorUmaParcela = this.getValor()/this.getQuantidadeDeParcelas();
		double valorPorParcela =0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		

		String agora = "25/03/2020";
		
		java.util.Date aqui = sdf.parse(agora);
		

		for(int k=1;k<this.QuantidadeDeParcelas;k++) {
			java.sql.Date now = new java.sql.Date(this.getDataDeVencimento().getTime());
			Date data = adicionarMes(now, k);
			valorPorParcela += valorUmaParcela;
			
			
			this.parcela.add(new Parcela(k, data, valorPorParcela));
			
			
			
		}
		
		*/
		
	

	
	

}
