package Api_Banco.Entidades;

import java.sql.Date;
import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Emprestimo {
	@Id
	private Integer Id; 
	private double Limite; 
	private double Valor; 
	private int QuantidadeDeParcelas;
	
	@OneToMany(mappedBy = "emprestimo")
	private List<Parcela> parcela;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date DataDeVencimento;
	
	private static final double Juros = 1.6;
	
	@OneToOne(mappedBy = "emprestimo")
	private Conta conta;
	
	
	
	private Emprestimo() {}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}
	

	public double getLimite() {
		return Limite;
	}

	public void setLimite(double limite) {
		Limite = limite;
	}

	public double getValor() {
		return Valor;
	}

	public void setValor(double valor) {
		Valor = valor;
	}

	public double getJuros() {
		return Juros;
	}

	/*
	public void setJuros(double juros) {
		Juros = juros;
	}
   */
	public Conta getConta() {
		return conta;
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

	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
	public void adicionarParcelar(Parcela parcela) {
		this.parcela.add(parcela);
	}
	public void removerParcela(Parcela parcela) {
		this.parcela.remove(parcela);
	}
	
	public Date adicionarMes(Date data,int mes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.MONTH, mes);
		return (Date) cal.getTime();
	}

	public void processamentoDoEmpréstimo() throws ParseException {
		double valorBasicoDaParcela =this.getValor()/this.getQuantidadeDeParcelas();
		double valorTotal = 0;
		
	
		for(int k=1;k<=this.getQuantidadeDeParcelas();k++) {
			Date data = adicionarMes(this.DataDeVencimento,k);
			valorTotal += valorBasicoDaParcela * this.getJuros();
			this.parcela.add(new Parcela(k, data, valorTotal));
			System.out.println("parcela "+k+" data "+data+" valorTotal "+valorTotal);
			
	}
		
		
		
	}

}
