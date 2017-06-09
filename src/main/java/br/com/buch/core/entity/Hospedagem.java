package br.com.buch.core.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    private Integer codigo;
	
	
	@Column(name = "DATA_ENTRADA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dataEntrada;
    
	
    @Column(name = "DATA_SAIDA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dataSaida;
    
    
    @Column(name="DIARIAS", insertable=false, updatable=false)
    private Integer diarias;
    
    
    @Column(name = "OBS" , length = 255)
    private String obs;
      
    
    @Column(name = "MOTIVO_VIAGEM" , length = 60)
    private String motivoViagem;
    
    
    @Column(name = "MEIO_TRANSPORTE" , length = 20)
    private String meioTransporte;
    
    
    @Column(name = "PROXIMO_DESTINO" , length = 60)
    private String proximoDestino;
    
    
    @Column(name = "DESTINO_ANTERIOR" , length = 60)
    private String destinoAnterior;
    
    
    
    @OneToOne
    @JoinColumn(name ="COD_CADAPARTAMENTO")
    private Apartamento apartamento;
       
    
    @OneToOne
    @JoinColumn(name ="COD_CADHOSPEDE")
    private Hospede hospede;

    
    @OneToMany(mappedBy = "hospedagem", targetEntity = HospedeAdicional.class,
    			fetch = FetchType.EAGER, cascade = CascadeType.ALL)    
    private List<HospedeAdicional> hospedesAdicionais;
    
    
    @OneToMany(mappedBy = "hospedagem", targetEntity = HospedagemLancamento.class, 
    			fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<HospedagemLancamento> lancamentos;


    
   //--------------------------------	GETs and SETs------------------------------//
    
	public Integer getIdHospedagem() {
		return idHospedagem;
	}

	public void setIdHospedagem(Integer idHospedagem) {
		this.idHospedagem = idHospedagem;
	}


	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}


	public Calendar getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Calendar dataEntrada) {
		this.dataEntrada = dataEntrada;
	}


	public Calendar getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Calendar dataSaida) {
		this.dataSaida = dataSaida;
	}


	public Integer getDiarias() {
		return diarias;
	}

	public void setDiarias(Integer diarias) {
		this.diarias = diarias;
	}


	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}


	public String getMotivoViagem() {
		return motivoViagem;
	}

	public void setMotivoViagem(String motivoViagem) {
		this.motivoViagem = motivoViagem;
	}


	public String getMeioTransporte() {
		return meioTransporte;
	}

	public void setMeioTransporte(String meioTransporte) {
		this.meioTransporte = meioTransporte;
	}


	public String getProximoDestino() {
		return proximoDestino;
	}

	public void setProximoDestino(String proximoDestino) {
		this.proximoDestino = proximoDestino;
	}


	public String getDestinoAnterior() {
		return destinoAnterior;
	}

	public void setDestinoAnterior(String destinoAnterior) {
		this.destinoAnterior = destinoAnterior;
	}


	public Apartamento getApartamento() {
		return apartamento;
	}

	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}


	public Hospede getHospede() {
		return hospede;
	}

	public void setHospede(Hospede hospede) {
		this.hospede = hospede;
	}


	public List<HospedeAdicional> getHospedesAdicionais() {
		return hospedesAdicionais;
	}

	public void setHospedesAdicionais(List<HospedeAdicional> hospedesAdicionais) {
		this.hospedesAdicionais = hospedesAdicionais;
	}


	public List<HospedagemLancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<HospedagemLancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	
	
	
	@Transient
    public String getData_EntradaFormatada(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.dataEntrada.getTime());                
    }
    
    @Transient
    public String getData_SaidaFormatada(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.dataSaida.getTime());                
    }
    
    
	//--------------------------------	Métodos Auxiliares------------------------------//	

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
