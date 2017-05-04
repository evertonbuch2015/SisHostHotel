package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.EmpresaDao;
import br.com.buch.core.entity.Empresa;
import br.com.buch.view.managedBean.EmpresaBean.TipoFiltro;
import br.com.buch.view.util.UtilMensagens;

public class EmpresaService implements GenericService<Empresa> {

	private EmpresaDao empresaDao;
	
	
	public EmpresaService() {
		empresaDao = new EmpresaDao();
	}
	
	
	@Override
	public boolean salvar(Empresa entidate) {
		if (entidate.getIdEmpresa() == null) {
			if (empresaDao.save(entidate)) {
				UtilMensagens.mensagemInformacao("Cadastro de Empresa Realizado com Sucesso");
				return true;
			}
		} else {
			if (empresaDao.update(entidate)) {
				UtilMensagens.mensagemInformacao("Cadastro de Empresa Alterado com Sucesso");
				return true;
			}
		}
		return false;
	}

	
	@Override
	public void excluir(Empresa entidade) {
		empresaDao.delete(entidade);
	}

	
	@Override
	public Empresa carregarEntidade(Integer id) {
		return empresaDao.findAllAttributesEntity(id);
	}

	
	@Override
	public List<Empresa> buscarTodos() {
		return empresaDao.findAll();
	}

	
	public List<Empresa> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro){
		List<Empresa> lista = null;
		
		if(tipoFiltro.equals(TipoFiltro.CODIGO)){
			String jpql = "Select e From Empresa e where e.codigo in (" + valorFiltro + ")";
			lista = empresaDao.find(jpql);
		}
		else if(tipoFiltro.equals(TipoFiltro.NOME)){
			lista = empresaDao.find("Select e From Empresa e where e.nomeRazao like ?",valorFiltro);
		}
		
		return lista;	
	}
}
