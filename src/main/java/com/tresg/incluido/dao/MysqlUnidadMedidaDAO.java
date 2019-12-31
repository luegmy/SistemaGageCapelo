package com.tresg.incluido.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.tresg.incluido.interfaz.UnidadMedidaDAO;
import com.tresg.incluido.jpa.UnidadMedidaJPA;
import com.tresg.util.jpa.JpaUtil;

public class MysqlUnidadMedidaDAO implements UnidadMedidaDAO {

	EntityManager em = null;
	private void open() {
		 em = JpaUtil.getEntityManager();
	}
	
	private void close() {
		 em.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UnidadMedidaJPA> buscarMedidaPorDescripcion2(String descripcion) {
		open();
		Query q = em.createNamedQuery("medida.buscarMedida").setParameter("p1", "%" + descripcion + "%");

		return q.getResultList();
	}

	@Override
	public UnidadMedidaJPA buscarMedidaPorCodigo(int codigo) {
		open();

		return em.find(UnidadMedidaJPA.class, codigo);
	}

	@Override
	public String actualizarMedida(UnidadMedidaJPA medida) {
		open();
		String mensaje;
		try {
			em.getTransaction().begin();
			if (medida.getCodMedida() == 0) {
				em.persist(medida);
				mensaje = "Unidad de Medida registrada";
			} else {
				em.merge(medida);
				mensaje = "Unidad de Medida actualizada";
			}
			em.getTransaction().commit();
		} catch (RuntimeException e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			close();
		}

		return mensaje;
	}

	@Override
	public String obtenerDescripcionMedida(int medida) {
		open();

		UnidadMedidaJPA objMedida = em.find(UnidadMedidaJPA.class, medida);

		return objMedida.getDescripcion();
	}

}
