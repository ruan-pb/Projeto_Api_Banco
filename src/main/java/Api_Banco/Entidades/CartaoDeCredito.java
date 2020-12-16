package Api_Banco.Entidades;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
//import java.text.ParseException;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import Api_Banco.Exceptions.SaldoInsuficiente;

@Entity
public class CartaoDeCredito {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Integer Id;
	private String numeroDoCartao;
	private double LimiteDisponivel = 1000.0;
	private double valor;
	private double FaturaAtual;
	

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date DataDaCompra;

	@OneToMany(mappedBy = "credito")
	private List<Parcela> ProximasFaturas  =  new ArrayList<>();

	@OneToOne(mappedBy = "credito", cascade = CascadeType.ALL)
	private Conta conta;

	public CartaoDeCredito() {
	}
	
	

	public CartaoDeCredito(Integer id, String numeroDoCartao, double limiteDisponivel) {
		super();
		Id = id;
		this.numeroDoCartao = numeroDoCartao;
		LimiteDisponivel = limiteDisponivel;
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

	@JsonIgnore
	public Conta getConta() {
		return conta;
	}
	
	@JsonIgnore
	public List<Parcela> getProximasFaturas() {
		return ProximasFaturas;
	}



	public void setProximasFaturas(List<Parcela> proximasFaturas) {
		ProximasFaturas = proximasFaturas;
	}


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
				
	         
				
				
				Date parcelas = Date.valueOf(LocalDate.now().plusMonths(k));
				this.setDataDaCompra(parcelas);
				p.setQuantidadeDeParcelas(k);
				p.setValor(parcelaBasica);
				p.setDataDeVencimento(this.getDataDaCompra());
				
				
				
				System.out.println("quantidade de parcelas"+p.getQuantidadeDeParcelas());
				System.out.println("Data de vencimento"+p.getDataDeVencimento());
				System.out.println("Valor de cada parcela"+p.getValor());
				
				ProximasFaturas.add(p);
				
				System.out.println("---------------Começo---------------------------");
				System.out.println("lista de todas as faturas ");

				ProximasFaturas.forEach(System.out::println);
				
				System.out.println("-------------------FIM-----------------------");

				
			}
			

			

		} else {
			throw new SaldoInsuficiente();
		}

	}
	public void ultimaParcela(int quantidadeDeparcela) {
		Parcela p = new Parcela();
		Date parcelas = Date.valueOf(LocalDate.now().plusMonths(quantidadeDeparcela));
		p.setDataDeVencimento(parcelas);
	}



	public double getValor() {
		return valor;
	}



	public void setValor(double valor) {
		this.valor = valor;
	}

	

}
