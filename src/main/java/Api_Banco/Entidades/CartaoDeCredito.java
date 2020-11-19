package Api_Banco.Entidades;

import java.sql.Date;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
//import java.text.ParseException;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;

import Api_Banco.Exceptions.SaldoInsuficiente;

@Entity
public class CartaoDeCredito {
	@Id
	private Integer Id;
	private String numeroDoCartao;
	private double LimiteDisponivel;
	private double FaturaAtual;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date DataDaCompra;

	@OneToMany(mappedBy = "credito")
	private List<Parcela> ProximasFaturas  =  new ArrayList<>();

	@OneToOne(mappedBy = "credito", cascade = CascadeType.ALL)
	private Conta conta;

	public CartaoDeCredito() {
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public double getLimiteDisponivel() {
		return LimiteDisponivel;
	}

	public void setLimiteDisponivel(double limiteDisponivel) {
		LimiteDisponivel -= limiteDisponivel;
	}

	public String getNumeroDoCartao() {
		return numeroDoCartao;
	}

	public void setNumeroDoCartao(String numeroDoCartao) {
		this.numeroDoCartao = numeroDoCartao;
	}

	public double getFaturaAtual() {
		return FaturaAtual;
	}

	public void setFaturaAtual(double faturaAtual) {
		this.FaturaAtual = faturaAtual;
	}

	public Date getDataDaCompra() {
		return DataDaCompra;
	}

	public void setDataDaCompra(Date dataDaCompra) {
		DataDaCompra = dataDaCompra;
	}

	public void adicionarParcela(Parcela parcela) {
		this.ProximasFaturas.add(parcela);

	}

	public void removerParcela(Parcela parcela) {
		this.ProximasFaturas.remove(parcela);
	}

	public Conta getConta() {
		return conta;
	}

	/*
	 * public void adicionarParcela(CartaoDeCredito c){ this.cartaoDeCredito.add(c);
	 * }
	 */
	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public void passandoCartao(Parcela parcela) {
		Parcela p = new Parcela();

		double parcelaBasica = parcela.getValor() / parcela.getQuantidadeDeParcelas();
		double disponivel = this.getFaturaAtual() + parcela.getValor();
		double limite = 1000;
		if (disponivel < limite) {

			
			for (int k = 1; k < parcela.getQuantidadeDeParcelas(); k++) {
				
	          //Date dataDaCompra = Date.valueOf(LocalDate.now());
				
				//Date dataDaCompta = Date.valueOf()
				
				
				Date parcelas = Date.valueOf(LocalDate.now().plusMonths(k));
				p.setQuantidadeDeParcelas(k);
				p.setDataDeVencimento(parcelas);
				p.setValor(parcelaBasica);
				
				System.out.println(p.getQuantidadeDeParcelas());
				System.out.println(p.getDataDeVencimento());
				System.out.println(p.getValor());
				
				ProximasFaturas.add(p);
				
				/*
				java.util.Date data = adicionarMes(k);
				p.setQuantidadeDeParcelas(k);
			
				p.setDataDeVencimento(data.getTime());
				p.setValor(parcelaBasica);
*/
			}
			

			

		} else {
			throw new SaldoInsuficiente();
		}

	}

	public java.util.Date adicionarMes(int mes) {
		Instant inst = Instant.now();
		Timestamp data =  java.sql.Timestamp.from(inst);
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.MONTH, mes);
		return cal.getTime();

	}

	/*
	 * public void processamentoDoEmpréstimo() throws ParseException { double
	 * valorBasicoDaParcela =this.getValor()/this.getQuantidadeDeParcelas(); double
	 * valorTotal = 0;
	 * 
	 * /* SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	 * 
	 * 
	 * String agora = "25/03/2020";
	 * 
	 * java.util.Date aqui = sdf.parse(agora);
	 * 
	 * 
	 * java.sql.Date now = new java.sql.Date(aqui.getTime());
	 * 
	 */

	/*
	 * for(int k=1;k<=this.getQuantidadeDeParcelas();k++) { Date data =
	 * adicionarMes(this.DataDeVencimento,k); valorTotal += valorBasicoDaParcela *
	 * this.getJuros(); this.parcela.add(new Parcela(k, data, valorTotal));
	 * System.out.println("parcela "+k+" data "+data+" valorTotal "+valorTotal);
	 * 
	 * }
	 */

}
