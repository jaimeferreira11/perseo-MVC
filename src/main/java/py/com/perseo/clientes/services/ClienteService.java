package py.com.perseo.clientes.services;

import py.com.perseo.clientes.entities.Cliente;

import java.util.List;

public interface ClienteService {

	public List<Cliente> getAllClientes() throws Exception;

	public Cliente getClienteById(Integer idCliente) throws Exception;

	public Cliente updateCliente(Cliente cliente) throws Exception;

	public String deleteCliente(Integer idCliente) throws Exception;

	public Cliente addCliente(Cliente cliente) throws Exception;
	
	public List<Cliente> getClientesByParams(String tipodoc, String nrodoc, String nombres) throws Exception;

	
}
