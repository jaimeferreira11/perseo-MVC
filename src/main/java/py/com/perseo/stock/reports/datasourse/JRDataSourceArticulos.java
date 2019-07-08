package py.com.perseo.stock.reports.datasourse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import py.com.perseo.stock.entities.Historicoarticulo;

import java.util.Iterator;
import java.util.List;


public class JRDataSourceArticulos implements JRDataSource {
		Iterator it;
		private Historicoarticulo mov;

		public JRDataSourceArticulos(List<Historicoarticulo> list) {
			it = list.iterator();
		}

		public boolean next() throws JRException {
			boolean ret = false;
			ret = it.hasNext();
			if (ret) {
				mov = (Historicoarticulo) it.next();
				ret = true;
			}
			return ret;
		}

		public Object getFieldValue(JRField field) throws JRException {
			Object value = null;
			if ("importe".equals(field.getName())) {
				value = mov.getPrecioventa() * mov.getVenta();
			}
			if ("producto".equals(field.getName())) {
				value = mov.getArticulodeposito().getArticulo().getDescripcion();
			}
			if ("precio".equals(field.getName())) {
				value = mov.getPrecioventa();;
			}
			if ("idfamilia".equals(field.getName())) {
				value = mov.getArticulodeposito().getArticulo().getIdfamilia();				
			}
			/*if ("familia".equals(field.getName())) {
				value = mov.getArticulodeposito().getArticulo().getFamilia().getDescripcion();				
			}*/
			if ("anterior".equals(field.getName())) {
				value = mov.getAnterior();				
			}
			if ("compras".equals(field.getName())) {
				value = mov.getEntrada();
			}
			if ("cantidad".equals(field.getName())) {
				value = mov.getVenta();
			}
			if ("actual".equals(field.getName())) {
				value = mov.getAnterior().doubleValue() + mov.getEntrada().doubleValue() - mov.getVenta().doubleValue(); 				
			}

			return value;
		}
	}