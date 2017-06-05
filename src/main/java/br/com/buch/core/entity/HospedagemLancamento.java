package br.com.buch.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

import br.com.buch.core.enumerated.TipoLancamento;


/**
*
* @author Everton de Souza
*/

@Entity
@Table(name="HOSPEDAGEM_LANCAMENTOS")
public class HospedagemLancamento implements Serializable {

	
	
	@Id
    @SequenceGenerator(name="G_HOSPEDAGEM_LANCAMENTOS", sequenceName="\"G_HOSPEDAGEM_LANCAMENTOS\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_HOSPEDAGEM_LANCAMENTOS")
    @Column(name = "COD_HOSPEDAGEM_LANCAMENTOS")
    private Integer idHospedagemLancamento;
	
	
	
	@Column(name="DESCRICAO", length = 250)
	private String descricao;
	
	
	@Column(name="QUANTIDADE")
	private Integer quantidade;
	
	
	@Column(name="VL_UNITARIO")
	private BigDecimal vlUnitario;
	
	
	@Column(name = "VL_TOTAL", insertable=false, updatable=false)
    private BigDecimal vlTotal;
	
	
	@Column(name = "DATA_CADASTRO")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar dataCadastro;
	
	
	@Column(name="TIPO_LANCAMENTO")
	@Enumerated(EnumType.STRING)
	private TipoLancamento tipoLancamento;
	
	
	//DEBITO = D      CREDITO = C
	@Column(name="OPERACAO")
	private Character operacao;
	
	
	@ManyToOne
    @JoinColumn(name ="COD_HOSPEDAGEM")
    private Hospedagem hospedagem;
}
