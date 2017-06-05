package br.com.buch.core.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name="HOSPEDAGEM")
public class Hospedagem implements Serializable {

	
	@Id
    @SequenceGenerator(name="G_HOSPEDAGEM", sequenceName="\"G_HOSPEDAGEM\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_HOSPEDAGEM")
    @Column(name = "COD_HOSPEDAGEM")
    private Integer idHospedagem;
	
	
	@Column(name="CODIGO")
    private String codigo;
	
	
	
	
	@Column(name = "DT_ENTRADA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dtEntrada;
    
    @Column(name = "DT_SAIDA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dtSaida;
    
    @Column(name="DIARIAS")
    private Integer diarias;
    
    
    
    
    @Column(name = "OBS" , length = 150)
    private String obs;
      
    
    @Column(name = "MOTIVO_VIAGEM" , length = 150)
    private String motivoViagem;
    
    
    @Column(name = "MEIO_TRANSPORTE" , length = 20)
    private String meioTransporte;
    
    
    @Column(name = "PROXIMO_DESTINO" , length = 150)
    private String proximoDestino;
    
    
    @Column(name = "DESTINO_ANTERIOR" , length = 20)
    private String destinoAnterior;
    
    
    
    @OneToOne
    @JoinColumn(name ="COD_CADAPARTAMENTO")
    private Apartamento apartamento;
       
    
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name ="hospede_id")
    private Hospede hospede;
}
