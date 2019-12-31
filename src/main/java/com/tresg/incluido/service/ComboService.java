package com.tresg.incluido.service;

import java.io.Serializable;
import java.util.List;

import com.tresg.almacen.jpa.AlmacenJPA;
import com.tresg.almacen.jpa.TipoMovimientoJPA;
import com.tresg.factoria.DAOFactory;
import com.tresg.incluido.interfaz.ComboDAO;
import com.tresg.incluido.jpa.ComprobanteJPA;
import com.tresg.incluido.jpa.DocumentoIdentidadJPA;
import com.tresg.incluido.jpa.MediosPagoJPA;
import com.tresg.incluido.jpa.TipoProductoJPA;
import com.tresg.incluido.jpa.UnidadMedidaJPA;
import com.tresg.seguridad.jpa.RolJPA;
import com.tresg.ventas.jpa.NotaCreditoJPA;
import com.tresg.ventas.jpa.NotaDebitoJPA;
import com.tresg.ventas.jpa.OperacionJPA;


public class ComboService implements ComboService_I,Serializable {


	private static final long serialVersionUID = 1L;
	
	DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	ComboDAO iComboDAO = fabrica.getComboDAO();
	
	@Override
	public List<TipoProductoJPA> comboTipoProducto() {
		return iComboDAO.comboTipoProducto();
	}

	@Override
	public List<UnidadMedidaJPA> comboUnidadMedida() {
		return iComboDAO.comboUnidadMedida();
	}


	@Override
	public List<AlmacenJPA> comboAlamcen() {
		return iComboDAO.comboAlamcen();
	}
	
	@Override
	public List<TipoMovimientoJPA> comboTipoMovimiento() {
		return iComboDAO.comboTipoMovimiento();
	}
	
	@Override
	public List<DocumentoIdentidadJPA> comboIdentidad() {
		return iComboDAO.comboIdentidad();
	}
	
	@Override
	public List<ComprobanteJPA> comboComprobante() {
		return iComboDAO.comboComprobante();
	}

	@Override
	public List<MediosPagoJPA> comboPago() {
		return iComboDAO.comboPago();
	}

	@Override
	public List<RolJPA> comboRol() {
		return iComboDAO.comboRol();
	}

	@Override
	public List<OperacionJPA> comboOperacion() {
		return iComboDAO.comboOperacion();
	}

	@Override
	public List<NotaCreditoJPA> comboCredito() {
		return iComboDAO.comboCredito();
	}

	@Override
	public List<NotaDebitoJPA> comboDebito() {
		return iComboDAO.comboDebito();
	}

	
}
