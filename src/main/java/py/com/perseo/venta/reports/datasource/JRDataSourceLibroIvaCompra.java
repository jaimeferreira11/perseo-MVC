package py.com.perseo.venta.reports.datasource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import py.com.perseo.tesoreria.beans.CompracabBean;

import java.util.Collection;
import java.util.Iterator;

public class JRDataSourceLibroIvaCompra implements JRDataSource {
		Iterator it = null;

		CompracabBean compra_data;

		public JRDataSourceLibroIvaCompra(Collection c) {
			it = c.iterator();
		}

		public boolean next() throws JRException {
			boolean ret = false;
			ret = it.hasNext();
			if (ret) {
				compra_data = (CompracabBean) it.next();
			}
			return ret;
		}

		public Object getFieldValue(JRField field) throws JRException {
			Object value = null;

			if ("sNrofactura".equals(field.getName())) {
				value = compra_data.getComprobante();
			}

			if ("tipo".equals(field.getName())) {
				if (compra_data.getTipocompra().equals("FA")) {
					value = "FACTURA COMPRA";
				} else if (compra_data.getTipocompra().equals("NC")) {
					value = "NOTA DE CREDITO";
				}
			}

			if ("sRuc".equals(field.getName())) {
				value = compra_data.getProveedordata().getNrodoc();
			}

			if ("dFechafactura".equals(field.getName())) {
				value = compra_data.getFecha();
			}

			if ("sRazonsocial".equals(field.getName())) {

				value = compra_data.getProveedordata().getDescripcion();

			}
			if ("nGrav10".equals(field.getName())) {
				value = compra_data.getGravada10();
			}
			if ("nGrav5".equals(field.getName())) {
				value = compra_data.getGravada5();
			}
			if ("nIva10".equals(field.getName())) {
				value = compra_data.getIva10();
			}
			if ("nIva5".equals(field.getName())) {
				value = compra_data.getIva5();
			}
			if ("nExenta".equals(field.getName())) {
				value = compra_data.getExenta();
			}
			if ("nTotal".equals(field.getName())) {
				value = compra_data.getImporte();
			}
			if ("idsucursal".equals(field.getName())) {
				value = compra_data.getSucursal().getIdsucursal();
			}
			if ("sucursal_descripcion".equals(field.getName())) {
				value = compra_data.getSucursal().getNombre();
			}
			
			return value;
		}
	}