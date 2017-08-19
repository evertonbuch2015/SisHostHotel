package br.com.buch.core.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.buch.core.enumerated.SituacaoHospedagem;


/**
*
* @author Everton de Souza
*/

@Entity
@Table(name="RESERVA")
public class Reserva implements Serializable {

	private static final long serialVersionUID = 9007298697122901447L;

	
	@Id
    @SequenceGenerator(name="G_RESERVA", sequenceName="\"G_RESERVA\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_RESERVA")
    @Column(name = "COD_RESERVA")
    private Integer idReserva;
    
    @Column(name="CODIGO")
    private String codigo;
    
    
    @Column(name = "DATA_ENTRADA")
    @Temporal(TemporalType.DATE)
    private Date dataEntrada;
    
    
    @Column(name = "DATA_SAIDA")
    @Temporal(TemporalType.DATE)
    private Date dataSaida;
    
    
    @Column(name = "DATA_CONFIRMACAO")
    @Temporal(TemporalType.DATE)
    private Calendar dataConfirmacao;
    
    
    @Transient
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
        
    
    
    @Column(name = "DATA_CANCELAMENTO")
    @Temporal(TemporalType.DATE)
    private Calendar dataCancelamento;
    
    @Column(name="MOTIVO_CANCELAMENTO",length=100)
    private String motivoCancelamento;
    
    
    @Column(name = "OBS" , length = 255)
    private String obs;
    
    
    @OneToOne
    @JoinColumn(name ="COD_CADHOSPEDE")
    private Hospede hospede;
    
    
    @OneToOne
    @JoinColumn(name ="COD_CADAPARTAMENTO")
    private Apartamento apartamento;

    
    
    //--------------------------------	GETs and SETs------------------------------//
	
    public Integer getIdReserva() {return idReserva;}

	public void setIdReserva(Integer idReserva) {this.idReserva = idReserva;}

	
	public String getCodigo() {return codigo;}

	public void setCodigo(String codigo) {this.codigo = codigo;}


	public String getDataEntradaFormatada(){return new SimpleDateFormat("dd/MM/yyyy").format(this.dataEntrada);}
	
	public Date getDataEntrada() {return dataEntrada;}

	public void setDataEntrada(Date dataEntrada) {this.dataEntrada = dataEntrada;}


	public String getDataSaidaFormatada(){return new SimpleDateFormat("dd/MM/yyyy").format(this.dataSaida);}
	
	public Date getDataSaida() {return dataSaida;}

	public void setDataSaida(Date dataSaida) {this.dataSaida = dataSaida;}


	public Calendar getDataConfirmacao() {return dataConfirmacao;}

	public void setDataConfirmacao(Calendar dataConfirmacao) {this.dataConfirmacao = dataConfirmacao;}


	public Integer getDiarias() {
		if(dataEntrada != null && dataSaida != null){
			int MILLIS_IN_DAY = 86400000;
			
			Calendar c1 = Calendar.getInstance(), c2 = Calendar.getInstance(); 
			
			c1.setTime(dataEntrada);
			c2.setTime(dataSaida);

			return ((Long)((c2.getTimeInMillis() - c1.getTimeInMillis())/ MILLIS_IN_DAY)).intValue();
		}else{
			return 0;
		}
		
	}
	
	public void setDiarias(Integer diarias) {this.diarias = diarias;}


	public String getObs() {return obs;}

	public void setObs(String obs) {this.obs = obs;}


	public SituacaoHospedagem getSituacao() {return situacao;}

	public void setSituacao(SituacaoHospedagem situacao) {this.situacao = situacao;}


	public Calendar getDataCancelamento() {return dataCancelamento;}

	public void setDataCancelamento(Calendar dataCancelamento) {this.dataCancelamento = dataCancelamento;}


	public String getMotivoCancelamento() {return motivoCancelamento;}

	public void setMotivoCancelamento(String motivoCancelamento) {this.motivoCancelamento = motivoCancelamento;}


	public Double getValorDiaria() {return valorDiaria;}

	public void setValorDiaria(Double valorDiaria) {this.valorDiaria = valorDiaria;}


	public Double getValorDesconto() {return valorDesconto;}

	public void setValorDesconto(Double valorDesconto) {this.valorDesconto = valorDesconto;}


	public Double getValorTaxaTurismo() {return valorTaxaTurismo;}

	public void setValorTaxaTurismo(Double valorTaxaTurismo) {this.valorTaxaTurismo = valorTaxaTurismo;}


	public Double getValorTaxaServico() {return valorTaxaServico;}

	public void setValorTaxaServico(Double valorTaxaServico) {this.valorTaxaServico = valorTaxaServico;}


	public Double getValorTotal() {
		Double valor = 0.0;
		
		valor += (this.valorDiaria != null) ? valorDiaria * getDiarias() : 0.0;		
		valor += (this.valorTaxaServico != null) ? valorTaxaServico : 0.0;
		valor += (this.valorTaxaTurismo != null) ? valorTaxaTurismo : 0.0;
		
		valor -= (this.valorDesconto != null) ? valorDesconto : 0.0;
		
		this.valorTotal = valor;
		
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {this.valorTotal = valorTotal;}


	public Hospede getHospede() {return hospede;}

	public void setHospede(Hospede hospede) {this.hospede = hospede;}


	public Apartamento getApartamento() {return apartamento;}

	public void setApartamento(Apartamento apartamento) {this.apartamento = apartamento;}

	
	
	//--------------------------------	Métodos Auxiliares------------------------------//	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idReserva == null) ? 0 : idReserva.hashCode());
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
		Reserva other = (Reserva) obj;
		if (idReserva == null) {
			if (other.idReserva != null)
				return false;
		} else if (!idReserva.equals(other.idReserva))
			return false;
		return true;
	}
}
