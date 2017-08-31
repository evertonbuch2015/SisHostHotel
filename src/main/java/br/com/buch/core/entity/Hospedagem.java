package br.com.buch.core.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.com.buch.core.entity.HospedagemLancamento.OrigemLancamento;
import br.com.buch.core.enumerated.SituacaoHospedagem;
import br.com.buch.core.util.CodeUtils;

@Entity
@Table(name="HOSPEDAGEM")
public class Hospedagem implements Serializable {

	private static final long serialVersionUID = -4252605366733523688L;


	@Id
    @SequenceGenerator(name="G_HOSPEDAGEM", sequenceName="\"G_HOSPEDAGEM\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_HOSPEDAGEM")
    @Column(name = "COD_HOSPEDAGEM")
    private Integer idHospedagem;
	
	
	@Column(name="CODIGO")
    private String codigo;
	
	
	@Column(name = "DATA_ENTRADA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataEntrada;
    
	
    @Column(name = "DATA_SAIDA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataSaida;
    
    
    @Column(name="DIARIAS", insertable=false, updatable=false)
    private Integer diarias;
    
    
    @Column(name = "SITUACAO" , length = 20)
    @Enumerated(EnumType.STRING)
    private SituacaoHospedagem situacao;
    
    
    
    @Column(name = "VALOR_DIARIA")
    private Double valorDiaria;
       
    @Column(name = "VALOR_DESCONTO")
    private Double valorDesconto;
        
    @Column(name = "VALOR_TAXA_TURISMO")
    private Double valorTaxaTurismo;
        
    @Column(name = "VALOR_TAXA_SERVICO")
    private Double valorTaxaServico;
        
    @Transient
    private Double valorTotal;
                
    
    @Column(name = "OBS" , length = 255)
    private String obs;  
    
    
    @OneToOne
    @NotNull
    @JoinColumn(name ="COD_CADAPARTAMENTO")
    private Apartamento apartamento;
       
    
    @OneToOne
    @NotNull
    @JoinColumn(name ="COD_CADHOSPEDE")
    private Hospede hospede;

    
    @OneToOne
    @JoinColumn(name ="COD_RESERVA")
    private Reserva reserva;
    
    
    @OneToMany(mappedBy = "hospedagem", targetEntity = HospedeAdicional.class,
    			fetch = FetchType.LAZY, cascade = CascadeType.ALL)    
    private List<HospedeAdicional> hospedesAdicionais;
    
    
    @OneToMany(mappedBy = "hospedagem", targetEntity = HospedagemLancamento.class, 
    			fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<HospedagemLancamento> lancamentos;
    
        
   //--------------------------------	GETs and SETs------------------------------//
    
	public Integer getIdHospedagem() {return idHospedagem;}

	public void setIdHospedagem(Integer idHospedagem) {this.idHospedagem = idHospedagem;}


	public String getCodigo() {return codigo;}

	public void setCodigo(String codigo) {this.codigo = codigo;}


	public Date getDataEntrada() {return dataEntrada;}

	public void setDataEntrada(Date dataEntrada) {this.dataEntrada = dataEntrada;}


	public Date getDataSaida() {return dataSaida;}

	public void setDataSaida(Date dataSaida) {this.dataSaida = dataSaida;}


	public Integer getDiarias() {
		try {
			return diarias == null ? CodeUtils.diasDiferenca(dataEntrada, dataSaida): diarias;
		} catch (Exception e) {
			return 0;
		}
	}

	public void setDiarias(Integer diarias) {this.diarias = diarias;}


	public SituacaoHospedagem getSituacao() {return situacao;}
	
	public void setSituacao(SituacaoHospedagem situacao) {this.situacao = situacao;}
	

	public Double getValorDiaria() {return valorDiaria;}

	public void setValorDiaria(Double valorDiaria) {this.valorDiaria = valorDiaria;}


	public Double getValorDesconto() {return valorDesconto;}

	public void setValorDesconto(Double valorDesconto) {this.valorDesconto = valorDesconto;}


	public Double getValorTaxaTurismo() {return valorTaxaTurismo;}

	public void setValorTaxaTurismo(Double valorTaxaTurismo) {this.valorTaxaTurismo = valorTaxaTurismo;}


	public Double getValorTaxaServico() {return valorTaxaServico;}

	public void setValorTaxaServico(Double valorTaxaServico) {this.valorTaxaServico = valorTaxaServico;}


	public Double getValorTotal() {		
		try {			
			Double valor = 0.00;
			
			valor += (this.valorDiaria != null) ? valorDiaria * getDiarias() : 0.00;		
			valor += (this.valorTaxaServico != null) ? valorTaxaServico : 0.00;
			valor += (this.valorTaxaTurismo != null) ? valorTaxaTurismo : 0.00;
			
			valor -= (this.valorDesconto != null) ? valorDesconto : 0.00;
			
			this.valorTotal = valor;
		} catch (Exception e) {
			valorTotal = 0.00;
		}			
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {this.valorTotal = valorTotal;}
	
	
	public String getObs() {return obs;}

	public void setObs(String obs) {this.obs = obs;}

	
	public Apartamento getApartamento() {return apartamento;}

	public void setApartamento(Apartamento apartamento) {this.apartamento = apartamento;}


	public Hospede getHospede() {return hospede;}

	public void setHospede(Hospede hospede) {this.hospede = hospede;}

	
	public Reserva getReserva() {
		return reserva;
	}
	
	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
	

	public List<HospedeAdicional> getHospedesAdicionais() {
		if(hospedesAdicionais == null){
			hospedesAdicionais = new ArrayList<>();
		}
		return hospedesAdicionais;
	}

	public void setHospedesAdicionais(List<HospedeAdicional> hospedesAdicionais) {
		this.hospedesAdicionais = hospedesAdicionais;
	}


	public List<HospedagemLancamento> getLancamentos() {
		if(this.lancamentos == null){
			this.lancamentos = new ArrayList<HospedagemLancamento>();
		}	
		return lancamentos;
	}

	public void setLancamentos(List<HospedagemLancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
		
	
	@Transient
    public String getDataEntradaFormatada(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.dataEntrada.getTime());                
    }
    
    @Transient
    public String getDataSaidaFormatada(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.dataSaida.getTime());                
    }
    
    
    @Transient
    public Double getTotalLancamentos(){
    	Double valor = 0.00;
    	for (HospedagemLancamento hospedagemLancamento : lancamentos) {
			if(OrigemLancamento.isCredito(hospedagemLancamento.getOrigemLancamento())){
				valor -= hospedagemLancamento.getVlTotal().doubleValue();
			}else{
				valor += hospedagemLancamento.getVlTotal().doubleValue();
			}
		}
    	return valor;
    }
    
	//--------------------------------	Métodos Auxiliares------------------------------//	

    
    public void addLancamento(HospedagemLancamento lancamento){
    	lancamento.setHospedagem(this);
    	getLancamentos().add(lancamento);    	
    }
    
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idHospedagem == null) ? 0 : idHospedagem.hashCode());
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
		Hospedagem other = (Hospedagem) obj;
		if (idHospedagem == null) {
			if (other.idHospedagem != null)
				return false;
		} else if (!idHospedagem.equals(other.idHospedagem))
			return false;
		return true;
	}    
}
