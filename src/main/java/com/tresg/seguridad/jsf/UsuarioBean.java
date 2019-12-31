package com.tresg.seguridad.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import com.tresg.incluido.jpa.EmpleadoJPA;
import com.tresg.incluido.service.ComboService_I;
import com.tresg.incluido.service.IncluidoBusinessDelegate;
import com.tresg.seguridad.jpa.RolJPA;
import com.tresg.seguridad.jpa.UsuarioJPA;
import com.tresg.seguridad.service.AutenticarUsuarioBusinessService;
import com.tresg.seguridad.service.SeguridadBusinessDelegate;

@ManagedBean(name = "usuario")
@SessionScoped
public class UsuarioBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private MenuModel modelo=new DefaultMenuModel();
	private int dni;
	private String nombreEmpleado;
	private String direccion;
	private int telefono;
	private int codigoUsuario;
	private String usuario;
	private String clave;
	private List<SelectItem> roles;
	private int codigoRol;

	private List<UsuarioJPA> usuarios;
	private String busqueda = "";
	private int sesionCodigoUsuario;
	private String sesionUsuario;

	String login = "login.xhtml?faces-redirect=true";
	String icono = "pi pi-users";

	AutenticarUsuarioBusinessService sSeguridad = SeguridadBusinessDelegate.getAutenticarUsuarioService();
	ComboService_I sCombo = IncluidoBusinessDelegate.getComboService();

	public String login() {

		FacesContext context = FacesContext.getCurrentInstance();
		UsuarioJPA objUsuario = sSeguridad.validaUsuario(usuario);
		if (!objUsuario.getUsuario().equals(usuario)) {
			context.addMessage("mensajeLogin",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario incorrecto", null));
			return login;
		} else if (!objUsuario.getClave().equals(clave)) {
			context.addMessage("mensajeLogin", new FacesMessage(FacesMessage.SEVERITY_INFO, "Clave incorrecta", null));
			return login;
		} else {
			if (objUsuario.getRol().getCodRol() == 1 || objUsuario.getRol().getCodRol()==2) {
				menuVenta();
				menuAlmacen();
				menuProducto();
				menuContacto();
				menuReporte();

			}
			if (objUsuario.getRol().getCodRol() == 3) {
				menuAlmacen();
				menuProducto();
			}

			setSesionUsuario(objUsuario.getEmpleado().getNombre());
			setSesionCodigoUsuario(objUsuario.getCodUsuario());
			return  "home.xhtml?faces-redirect=true";
		}

	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return login;
	}

	public void editarUsuario(ActionEvent e) {
		int codigo = (Integer) e.getComponent().getAttributes().get("codigo");
		UsuarioJPA objUsuario = sSeguridad.buscaUsuarioPorCodigo(codigo);
		setDni(objUsuario.getEmpleado().getDni());
		setNombreEmpleado(objUsuario.getEmpleado().getNombre());
		setDireccion(objUsuario.getEmpleado().getDireccion());
		setTelefono(objUsuario.getEmpleado().getTelefono());
		setCodigoUsuario(objUsuario.getCodUsuario());
		setUsuario(objUsuario.getUsuario());
		setClave(objUsuario.getClave());
		setCodigoRol(objUsuario.getRol().getCodRol());

	}

	public void actualizarUsuario() {
		FacesContext context = FacesContext.getCurrentInstance();
		EmpleadoJPA objE = new EmpleadoJPA();
		objE.setDni(dni);
		objE.setNombre(nombreEmpleado);
		objE.setDireccion(direccion);
		objE.setTelefono(telefono);

		RolJPA objRol = new RolJPA();
		objRol.setCodRol(codigoRol);

		UsuarioJPA objU = new UsuarioJPA();
		objU.setCodUsuario(codigoUsuario);
		objU.setUsuario(usuario);
		objU.setClave(clave);
		objU.setRol(objRol);
		objU.setEmpleado(objE);

		context.addMessage("mensajeRegistroUsuario",
				new FacesMessage(FacesMessage.SEVERITY_INFO, sSeguridad.registraUsuario(objE, objU), null));

		limpiar();
	}

	public void limpiar() {
		dni = 0;
		codigoUsuario = 0;
		nombreEmpleado = "";
		direccion = "";
		telefono = 0;
		usuario = "";
		clave = "";
		codigoRol = 0;

	}

	public void menuVenta() {
		
		DefaultSubMenu ventaSubmenu = new DefaultSubMenu("Venta");

		DefaultMenuItem item = new DefaultMenuItem("Registrar venta");
		item.setCommand("registroVenta.xhtml");
		item.setIcon("pi pi-shopping-cart");
		ventaSubmenu.addElement(item);

		item = new DefaultMenuItem("Modificar venta");
		item.setCommand("consultaVentaModificada.xhtml");
		item.setIcon("pi pi-calendar-times");
		ventaSubmenu.addElement(item);

		item = new DefaultMenuItem("Venta por cobrar");
		item.setCommand("consultaFacturaPendiente.xhtml");
		item.setIcon("pi pi-money-bill");
		ventaSubmenu.addElement(item);

		modelo.addElement(ventaSubmenu);

	}

	public void menuAlmacen() {
		
		DefaultSubMenu almacenSubmenu = new DefaultSubMenu("Almacen");

		DefaultMenuItem item = new DefaultMenuItem("Movimientos");
		item.setCommand("registroAlmacen.xhtml");
		item.setIcon("pi pi-bookmark");
		almacenSubmenu.addElement(item);

		item = new DefaultMenuItem("Consulta movimiento");
		item.setCommand("consultaMovimiento.xhtml");
		item.setIcon("pi pi-paperclip");
		almacenSubmenu.addElement(item);
		
		modelo.addElement(almacenSubmenu);

	}


	public void menuContacto() {
		
		DefaultSubMenu contactoSubmenu = new DefaultSubMenu("Contacto");

		DefaultMenuItem item = new DefaultMenuItem("Cliente");
		item.setCommand("actualizaCliente.xhtml");
		item.setIcon(icono);
		contactoSubmenu.addElement(item);
		
		item = new DefaultMenuItem("Proveedor");
		item.setCommand("actualizaProveedor.xhtml");
		item.setIcon(icono);
		contactoSubmenu.addElement(item);

		modelo.addElement(contactoSubmenu);

	}

	public void menuProducto() {
		
		DefaultSubMenu productoSubmenu = new DefaultSubMenu("Producto");

		DefaultMenuItem item = new DefaultMenuItem("Producto");
		item.setCommand("actualizaProducto.xhtml");
		item.setIcon("pi pi-tags");
		productoSubmenu.addElement(item);

		item = new DefaultMenuItem("Tipo Producto");
		item.setCommand("actualizaTipoProducto.xhtml");
		item.setIcon("pi pi-eject");
		productoSubmenu.addElement(item);

		item = new DefaultMenuItem("Unidad Medida");
		item.setCommand("actualizaMedida.xhtml");
		item.setIcon("pi pi-clone");
		productoSubmenu.addElement(item);

		modelo.addElement(productoSubmenu);

	}

	public void menuReporte() {

		DefaultSubMenu reporteSubmenu = new DefaultSubMenu("Reporte");

		DefaultMenuItem item = new DefaultMenuItem("Consulta venta");
		item.setCommand("consultaVenta.xhtml");
		item.setIcon("pi pi-info-circle");
		reporteSubmenu.addElement(item);
		
		item = new DefaultMenuItem("Productos cliente");
		item.setCommand("consultaProductoXCliente.xhtml");
		item.setIcon("pi pi-info");
		reporteSubmenu.addElement(item);
		
		item = new DefaultMenuItem("Productos venta");
		item.setCommand("consultaProductoXVenta.xhtml");
		item.setIcon("pi pi-filter");
		reporteSubmenu.addElement(item);
		
		modelo.addElement(reporteSubmenu);

	}



	public List<UsuarioJPA> getUsuarios() {
		usuarios = new ArrayList<>();
		for (UsuarioJPA c : sSeguridad.buscaUsuarioPorNombre(busqueda)) {
			usuarios.add(c);
		}
		return usuarios;
	}

	public void setUsuarios(List<UsuarioJPA> usuarios) {
		this.usuarios = usuarios;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getNombreEmpleado() {
		return nombreEmpleado;
	}

	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public int getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(int codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public List<SelectItem> getRoles() {
		roles = new ArrayList<>();
		for (RolJPA a : sCombo.comboRol()) {
			roles.add(new SelectItem(a.getCodRol(), a.getDescripcion()));
		}
		return roles;
	}

	public void setRoles(List<SelectItem> roles) {
		this.roles = roles;
	}

	public int getCodigoRol() {
		return codigoRol;
	}

	public void setCodigoRol(int codigoRol) {
		this.codigoRol = codigoRol;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	public String getSesionUsuario() {
		return sesionUsuario;
	}

	public void setSesionUsuario(String sesionUsuario) {
		this.sesionUsuario = sesionUsuario;
	}

	public int getSesionCodigoUsuario() {
		return sesionCodigoUsuario;
	}

	public void setSesionCodigoUsuario(int sesionCodigoUsuario) {
		this.sesionCodigoUsuario = sesionCodigoUsuario;
	}

	public MenuModel getModelo() {
		return modelo;
	}

	public void setModelo(MenuModel modelo) {
		this.modelo = modelo;
	}

}
