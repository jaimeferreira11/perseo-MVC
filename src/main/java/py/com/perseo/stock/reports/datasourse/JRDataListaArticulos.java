package py.com.perseo.stock.reports.datasourse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import py.com.perseo.stock.entities.Articulodeposito;

import java.util.Iterator;
import java.util.List;

public class JRDataListaArticulos implements JRDataSource {
		Iterator it = null;

		Articulodeposito articulo;

		public JRDataListaArticulos(List<Articulodeposito> c) {
			it = c.iterator();
		}

		public boolean next() throws JRException {
			boolean ret = false;
			ret = it.hasNext();
			if (ret) {
				articulo = (Articulodeposito) it.next();
			}
			return ret;
		}

		public Object getFieldValue(JRField field) throws JRException {
			Object value = null;

			if ("idarticulo".equals(field.getName())) {
				value = articulo.getArticulo().getIdarticulo();
			}

			if ("descripcion".equals(field.getName())) {
				value = articulo.getArticulo().getDescripcion();
			}

			if ("codigobarra".equals(field.getName())) {
				value = articulo.getArticulo().getCodigobarra();
			}

			if ("preciocosto".equals(field.getName())) {
				value = articulo.getPreciocosto();
			}

			if ("precioventa".equals(field.getName())) {
				value = articulo.getPrecioventa();
			}

			if ("disponible".equals(field.getName())) {
				value = articulo.getCantidad();
			}
			/*if ("moneda".equals(field.getName())) {
				value = articulo.getArticulo().getMoneda().getCodigo();
			}

			if ("familia".equals(field.getName())) {
				if (articulo.getArticulo().getFamilia() != null) {
					value = articulo.getArticulo().getFamilia().getDescripcion();
				}
			}
			if ("idfamilia".equals(field.getName())) {
				if (articulo.getArticulo().getFamilia() != null) {
					value = articulo.getArticulo().getFamilia().getIdfamilia();
				}
			}*/
			return value;
		}
	}