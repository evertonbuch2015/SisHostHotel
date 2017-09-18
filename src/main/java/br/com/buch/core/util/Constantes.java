package br.com.buch.core.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import br.com.buch.core.dao.ApartamentoDao;
import br.com.buch.core.dao.BancoDao;
import br.com.buch.core.dao.CategoriaDao;
import br.com.buch.core.dao.FormaPagamentoDao;
import br.com.buch.core.dao.HotelDao;
import br.com.buch.core.dao.TipoTarifaDao;
import br.com.buch.core.entity.Apartamento;
import br.com.buch.core.entity.Banco;
import br.com.buch.core.entity.Categoria;
import br.com.buch.core.entity.FormaPagamento;
import br.com.buch.core.entity.Hospedagem;
import br.com.buch.core.entity.Hotel;
import br.com.buch.core.entity.Reserva;
import br.com.buch.core.entity.TipoTarifa;
import br.com.buch.core.entity.Usuario;
import br.com.buch.core.service.ServiceHospedagem;
import br.com.buch.core.service.ServiceReserva;


public class Constantes {

	public enum ConstantesLista{
		APARTAMENTOS,
		CATEGORIAS,
		BANCOS,
		TIPOS_TARIFA,
		FORMAS_PAGAMENTO,
		HOSPEDAGENS_ATIVAS,
		RESERVAS_VENCIDAS;
	}
	
	
	private static Constantes instance;
	
	private List<Usuario> usuariosLogados; 
	private List<Apartamento> listaApartamentos;
	private List<Categoria> listaCategorias;
	private List<Banco> listaBancos;
	private List<TipoTarifa> listaTiposTarifa;
	private List<FormaPagamento> listaFormasPagamento;
	private List<Hospedagem> listaHospedagensAtivas;
	private List<Reserva> listaReservasVencidas;
	private List<Hotel> listaHoteis;
	
	private Constantes() {
		usuariosLogados = new ArrayList<Usuario>();
	}
	
	
	public static synchronized Constantes getInstance(){
		if(instance == null)
			instance = new Constantes();
		return instance;		
	}

	
	public void refreshAll(){
		try {			
			listaApartamentos 	   = new ApartamentoDao().findAll();
			listaCategorias 	   = new CategoriaDao().findAll();
			listaBancos 		   = new BancoDao().findAll();
			listaTiposTarifa       = new TipoTarifaDao().findAll();
			listaFormasPagamento   = new FormaPagamentoDao().findAll();
			listaHospedagensAtivas = new ServiceHospedagem().getHospedagensAtivas(); 
			listaReservasVencidas  = new ServiceReserva().buscarReservasVencidas();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void refresh(ConstantesLista tipo){
		try {
			switch (tipo) {
			case APARTAMENTOS:
				listaApartamentos 	   = new ApartamentoDao().findAll();
				break;
			case CATEGORIAS:
				listaCategorias 	   = new CategoriaDao().findAll();	
				break;
			case BANCOS:
				listaBancos 		   = new BancoDao().findAll();			
				break;
			case FORMAS_PAGAMENTO:
				listaFormasPagamento   = new FormaPagamentoDao().findAll();	
				break;
			case HOSPEDAGENS_ATIVAS:
				listaHospedagensAtivas = new ServiceHospedagem().getHospedagensAtivas();	
				break;
			case TIPOS_TARIFA:
				listaTiposTarifa       = new TipoTarifaDao().findAll();	
				break;
			case RESERVAS_VENCIDAS:
				listaReservasVencidas  = new ServiceReserva().buscarReservasVencidas();	
				break;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
		
	public List<Apartamento> getListaApartamentos() throws Exception {
		if (listaApartamentos == null) {
			listaApartamentos = new ApartamentoDao().findAll();
		}
		return listaApartamentos;
	}
	
	public List<Categoria> getListaCategorias() throws Exception {
		if (listaCategorias == null) {
			listaCategorias = new CategoriaDao().findAll();
		}
		return listaCategorias;
	}
	
	public List<Banco> getListaBancos() throws Exception {
		if (listaBancos == null) {
			listaBancos = new BancoDao().findAll();
		}
		return listaBancos; 
	}
	
	public List<TipoTarifa> getListaTiposTarifa()throws Exception{
		if (listaTiposTarifa == null) {
			listaTiposTarifa = new TipoTarifaDao().findAll();
		}
		return listaTiposTarifa;
	}
	
	public List<FormaPagamento> getListaFormasPagamento()throws Exception{
		if (listaFormasPagamento == null) {
			listaFormasPagamento = new FormaPagamentoDao().findAll();
		}
		return listaFormasPagamento;
	}
	
	
	public List<Hospedagem> getHospedagensAtivas() throws PersistenciaException {
		if(listaHospedagensAtivas == null){
			listaHospedagensAtivas = new ServiceHospedagem().getHospedagensAtivas(); 
		}
		return listaHospedagensAtivas;
	}
		
	
	public List<Hospedagem> getHospedagensParaCheckOut() throws PersistenciaException{
		if(listaHospedagensAtivas == null){
			listaHospedagensAtivas = new ServiceHospedagem().getHospedagensAtivas(); 
		}
		Date d1 = new Date(); 
		List<Hospedagem> hospedagens = listaHospedagensAtivas.stream()
			    .filter(p -> p.getDataSaida() == d1).collect(Collectors.toList());
		return hospedagens;
	}
	
	
	public List<Reserva> getListaReservasVencidas() throws Exception {
		if(listaReservasVencidas == null){
			listaReservasVencidas = new ServiceReserva().buscarReservasVencidas();
		}
		return listaReservasVencidas;
	}
	
	public List<Hotel> getListaHoteis() throws Exception {
		if(listaHoteis == null){
			listaHoteis = new HotelDao().findAll();
		}
		return listaHoteis;
	}
	

	//Métodos referente aos usuários que estão logados no sistema.	
	public List<Usuario> getUsuariosLogados() {return usuariosLogados;}
	
	public void addUsuarioLogado(Usuario usuario){
		if(!this.usuariosLogados.contains(usuario))
			this.usuariosLogados.add(usuario);
	}	
	
	public void removeUsuarioLogado(Usuario usuario){
		if(this.usuariosLogados.contains(usuario))
			this.usuariosLogados.remove(usuario);
	}
}
