package py.com.perseo.venta.reports.datasource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import py.com.perseo.venta.entities.Facturacab;

import java.util.Iterator;
import java.util.List;

public class JRDataSourceFacturas implements JRDataSource {
		Iterator it;
		private Facturacab facturacab;

		public JRDataSourceFacturas(List<Facturacab> list) {
			it = list.iterator();
		}

		public boolean next() throws JRException {
			boolean ret = false;
			ret = it.hasNext();
			if (ret) {
				facturacab = (Facturacab) it.next();
				ret = true;
			}
			return ret;
		}

		public Object getFieldValue(JRField field) throws JRException {
			Object value = null;
			if ("fecha".equals(field.getName())) {
				value = facturacab.getFecha();
			}
			if ("tipo".equals(field.getName())) {
				if (facturacab.getClasefactura().equals("F")) {
					value = facturacab.getTipofactura().getDescripcion();
				} else if (facturacab.getClasefactura().equals("R")) {
					value = "REMISION";
				}
			}
			if ("comprobante".equals(field.getName())) {
				value = facturacab.getEstablecimiento() + "-" + facturacab.getPuntoexpedicion() + "-" + facturacab.getNrofactura();
			}
			if ("cliente".equals(field.getName())) {
				value = facturacab.getCliente().getNrodoc() + " - " + facturacab.getCliente().getNombreapellido();
			}
			if ("importe".equals(field.getName())) {
				value = facturacab.getImporte();
			}
			/*if ("litros".equals(field.getName())) {
				value = facturacab.getCantLitros();
			}*/

			return value;
		}
	}