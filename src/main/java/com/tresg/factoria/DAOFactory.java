
package com.tresg.factoria;

import com.tresg.almacen.interfaz.MovimientoDAO;
import com.tresg.almacen.interfaz.DetalleAlmacenDAO;
import com.tresg.almacen.interfaz.DetalleMovimientoDAO;
import com.tresg.incluido.interfaz.ClienteDAO;
import com.tresg.incluido.interfaz.ComboDAO;
import com.tresg.incluido.interfaz.ProductoDAO;
import com.tresg.incluido.interfaz.ProveedorDAO;
import com.tresg.incluido.interfaz.TipoProductoDAO;
import com.tresg.incluido.interfaz.UnidadMedidaDAO;
import com.tresg.seguridad.interfaz.UsuarioDAO;
import com.tresg.ventas.interfaz.CobranzaDAO;
import com.tresg.ventas.interfaz.DetalleVentaDAO;
import com.tresg.ventas.interfaz.GuiaRemisionDAO;
import com.tresg.ventas.interfaz.VentaDAO;

public abstract class DAOFactory{

	public static final int MYSQL = 1;
	
    public static DAOFactory getDAOFactory(int whichFactory){
        switch(whichFactory){
       	case MYSQL:
        	    return new MySqlDAOFactory();
        	default:
        	    return null;
        }
     }
    
    // Existira un metodo por cada DAO que pueda ser creado.

	//MODULO GESTION Y/O INCLUIDO
    public abstract TipoProductoDAO getTipoProductoDAO();
    public abstract UnidadMedidaDAO getUnidadMedidaDAO();
    public abstract ProductoDAO getProductoDAO();
    public abstract ComboDAO getComboDAO();
    
    public abstract ClienteDAO getClienteDAO();
    public abstract ProveedorDAO getProveedorDAO();
    
    public abstract VentaDAO getVentaDAO();
    public abstract DetalleVentaDAO getDetalleVentaDAO();
    public abstract CobranzaDAO getCobranzaDAO();
    public abstract GuiaRemisionDAO getGuiaRemisionDAO();
    
    public abstract MovimientoDAO getMovimientoDAO();
    public abstract DetalleAlmacenDAO getDetalleAlmacenDAO();
    public abstract DetalleMovimientoDAO getDetalleMovimientoDAO();
    
    public abstract UsuarioDAO getUsuarioDAO();
   
    
    
}
