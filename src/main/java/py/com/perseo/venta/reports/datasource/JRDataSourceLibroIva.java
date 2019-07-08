package py.com.perseo.venta.reports.datasource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import py.com.perseo.venta.beans.FacturacabBean;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;

public class JRDataSourceLibroIva implements JRDataSource {
		Iterator it = null;

		FacturacabBean ivaBean;

		public JRDataSourceLibroIva(Collection c) {
			it = c.iterator();
		}

		public boolean next() throws JRException {
			boolean ret = false;
			ret = it.hasNext();
			if (ret) {
				ivaBean = (FacturacabBean) it.next();
			}
			return ret;
		}

		protected String getCompletarComprobante(String comp, int tipo) {
			String text = "";
			if (tipo == 1 || tipo == 2) {
				DecimalFormat df = new DecimalFormat("000");
				Integer valor = new Integer(comp);
				text = df.format(valor);
			} else if (tipo == 3) {
				DecimalFormat df = new DecimalFormat("0000000");
				Integer valor = new Integer(comp);
				text = df.format(valor);
			}

			return text;
		}

		public Object getFieldValue(JRField field) throws JRException {
			Object value = null;

			if ("sNrofactura".equals(field.getName())) {
				value = getCompletarComprobante(ivaBean.getEstablecimiento().toString(), 1) + "-" + getCompletarComprobante(ivaBean.getPuntoexpedicion().toString(), 2) + "-" + getCompletarComprobante(ivaBean.getNrofactura().toString(), 3);
			}
			
			if ("tipo".equals(field.getName())) {
				if (ivaBean.getTipofactura().equals("CONTADO") || ivaBean.getTipofactura().equals("CREDITO")) {
					value = "FACTURA";
				}else if (ivaBean.getTipofactura().equals("NOTACREDITO")) {
					value = "NOTA DE CREDITO";
				}
			}

			if ("sRuc".equals(field.getName())) {
				if (ivaBean.getEstado()) {
					value = ivaBean.getNrodocumento();
				} else {
					value = "";
				}
			}

			if ("dFechafactura".equals(field.getName())) {
				value = ivaBean.getFecha();
			}

			if ("sRazonsocial".equals(field.getName())) {
				if (ivaBean.getEstado()) {
					value = ivaBean.getPersona();
				} else {
					value = "ANULADO";
				}
			}
			if ("nGrav10".equals(field.getName())) {
				if (ivaBean.getEstado()) {
					value = ivaBean.getGravada10();
				} else {
					value = new Double(0);
				}
			}
			if ("nGrav5".equals(field.getName())) {
				if (ivaBean.getEstado()) {
					value = ivaBean.getGravada5();
				} else {
					value = new Double(0);
				}
			}
			if ("nIva10".equals(field.getName())) {
				if (ivaBean.getEstado()) {
					value = ivaBean.getIva10();
				} else {
					value = new Double(0);
				}
			}
			if ("nIva5".equals(field.getName())) {
				if (ivaBean.getEstado()) {
					value = ivaBean.getIva5();
				} else {
					value = new Double(0);
				}
			}
			if ("nExenta".equals(field.getName())) {
				if (ivaBean.getEstado()) {
					value = ivaBean.getExenta();
				} else {
					value = new Double(0);
				}
			}
			if ("nTotal".equals(field.getName())) {
				if (ivaBean.getEstado()) {
					value = ivaBean.getImporte();
				} else {
					value = new Double(0);
				}
			}
			
			if ("idsucursal".equals(field.getName())) {
				value = ivaBean.getSucursal().getIdsucursal();
			}
			if ("sucursal_descripcion".equals(field.getName())) {
				value = ivaBean.getSucursal().getNombre();
			}
			
			return value;
		}
	}