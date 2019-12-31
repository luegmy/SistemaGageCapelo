
package com.tresg.ventas.interfaz;

import java.util.List;

import com.tresg.ventas.jpa.DetalleVentaJPA;
import com.tresg.ventas.jpa.VentaJPA;

public interface VentaDAO {

	
	// CU consultar ventas
	List<VentaJPA> listarVenta();
	
	// mostrar el detalle de la VENTA
	VentaJPA obtenerVenta(int comprobante);
	
	int comprobarExistenciaProducto(int producto);

	// CU registrar venta
	String registrarVenta(VentaJPA venta);

	// CU actualizar venta
	String actualizarVenta(VentaJPA venta);

	// Actualiza stock en almacen cuando item de venta es retirada de la lista
	void actualizarItemVentaEliminada(DetalleVentaJPA dv);

	// CU anular venta
	String anularVenta(int venta);

}
