package Api_Banco.Entidades;

import java.sql.Date;
import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Emprestimo {
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id; 
	private double Limite = 1000; 
	@Column(name = "Valor_total_do_Emprestimo")
	private double Valor;
	
	private double ValorDeCadaParcela;
	private int parcelas;
	/*
	@OneToMany(mappedBy = "emprestimo")
	private List<Parcela> parcela;
	*/
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "inicio_do_Emprestimo")
	private Date DataDoEmprestimo;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date UltimaParcela;
	
	
	
	private static final double Juros = 1.6;
	
	@OneToOne(mappedBy = "emprestimo",cascade = CascadeType.ALL)
	private Conta conta;
	
	
	
	public Emprestimo() {}

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

	
	@JsonIgnore
	public Conta getConta() {
		return conta;
	}
	

	public int getQuantidadeDeParcelas() {
		return parcelas;
	}

	public void setQuantidadeDeParcelas(int quantidadeDeParcelas) {
		parcelas = quantidadeDeParcelas;
	}

	

	public double getValorDeCadaParcela() {
		return ValorDeCadaParcela;
	}

	public void setValorDeCadaParcela(double valorDeCadaParcela) {
		ValorDeCadaParcela = valorDeCadaParcela;
	}

	public Date getDataDoEmprestimo() {
		return DataDoEmprestimo;
	}

	public void setDataDoEmprestimo(Date dataDoEmprestimo) {
		DataDoEmprestimo = dataDoEmprestimo;
	}

	public Date getUltimaParcela() {
		return UltimaParcela;
	}

	public void setUltimaParcela(Date ultimaParcela) {
		UltimaParcela = ultimaParcela;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
	/*
	public void adicionarParcelar(Parcela parcela) {
		this.parcela.add(parcela);
	}
	public void removerParcela(Parcela parcela) {
		this.parcela.remove(parcela);
	}
	*/
	
		
		
	
	}


