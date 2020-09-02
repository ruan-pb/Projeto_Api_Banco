package Api_Banco.Entidades;

import java.sql.Date;
//import java.text.ParseException;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class CartaoDeCredito {
	@Id
	private Integer Id;
	private double LimiteDisponivel;
	private double FaturaAtual;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date DataDaCompra;
	
	@OneToMany(mappedBy = "credito")
	private List<Parcela> ProximasFaturas;
	
	
	@OneToOne(mappedBy = "credito")
	private Conta conta;
	
	public CartaoDeCredito() {}
	
	

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
		LimiteDisponivel = limiteDisponivel;
	}

	public double getFaturaAtual() {
		return FaturaAtual;
	}

	public void setFaturaAtual(double faturaAtual) {
		FaturaAtual = faturaAtual;
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
	public void adicionarParcela(CartaoDeCredito c){
		this.cartaoDeCredito.add(c);
	}
*/
	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public void passandoCartao(Parcela parcela) {
		double disponivel = this.getFaturaAtual()+parcela.getValor();
		if(this.LimiteDisponivel > disponivel) {
			this.FaturaAtual += parcela.getValor();
			ProximasFaturas.add(parcela);
		
		}
		else {
			//exceção
		}
		
	}
	/*
	public void processamentoDoEmpréstimo() throws ParseException {
		double valorBasicoDaParcela =this.getValor()/this.getQuantidadeDeParcelas();
		double valorTotal = 0;
		
		/*
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		

		String agora = "25/03/2020";
		
		java.util.Date aqui = sdf.parse(agora);

		
		java.sql.Date now = new java.sql.Date(aqui.getTime());
		
		*/
		
		
				
		
		
	/*	
		for(int k=1;k<=this.getQuantidadeDeParcelas();k++) {
			Date data = adicionarMes(this.DataDeVencimento,k);
			valorTotal += valorBasicoDaParcela * this.getJuros();
			this.parcela.add(new Parcela(k, data, valorTotal));
			System.out.println("parcela "+k+" data "+data+" valorTotal "+valorTotal);
			
	}
*/
	

}
