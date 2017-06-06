package br.com.buch.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
*
* @author Everton de Souza
*/

@Entity
@Table(name="HOSPEDE_ADICIONAL")
public class HospedeAdicional implements Serializable {

	private static final long serialVersionUID = -2040362566253714061L;

	
	@Id
    @SequenceGenerator(name="G_HOSPEDE_ADICIONAL", sequenceName="\"G_HOSPEDE_ADICIONAL\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_HOSPEDE_ADICIONAL")
    @Column(name = "COD_HOSPEDEADICIONAL")
    private Integer idHospedeAdicional;

    
    @Column(name = "NOME")
    private String nome;
    
    
    @Column(name = "CPF")
    private String cpf;
    
    
    @Column(name = "RG")
    private String rg;
    
    
    @Column(name = "IDADE")
    private Integer idade;
       
    
    @ManyToOne
    @JoinColumn(name ="COD_HOSPEDAGEM")
    private Hospedagem hospedagem;
}
