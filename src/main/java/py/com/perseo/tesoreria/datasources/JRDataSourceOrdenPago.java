package py.com.perseo.tesoreria.datasources;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import py.com.perseo.tesoreria.entities.Ordenpagocab;
import py.com.perseo.tesoreria.entities.Ordenpagodet;

import java.util.Iterator;
import java.util.List;


public class JRDataSourceOrdenPago implements JRDataSource {
	Iterator it = null;

	Ordenpagodet ordendetalle_data;
	Ordenpagocab ordendata;

	public JRDataSourceOrdenPago(List<Ordenpagodet> c, Ordenpagocab opdata) {
		it = c.iterator();
		ordendata = opdata;
	}

	public boolean next() throws JRException {
		boolean ret = false;
		ret = it.hasNext();
		if (ret) {
			ordendetalle_data = (Ordenpagodet) it.next();
		}
		return ret;
	}

	public Object getFieldValue(JRField field) throws JRException {
		Object value = null;
		// if ("dFechaFactura".equals(field.getName())) {
		// if (ordendetalle_data.getCompracab() != null) {
		// value = ordendetalle_data.getCompracab().getFechafactura();
		// }
		// }
		// if ("nPagado".equals(field.getName())) {
		// value = ordendetalle_data.getImporte();
		// }
		// if ("sComprobante".equals(field.getName())) {
		// if (ordendetalle_data.getCompracab() != null) {
		// value = ordendetalle_data.getCompracab().getComprobante();
		// }
		// }
		// if ("rubro".equals(field.getName())) {
		// if (ordendetalle_data.getCompracab() != null) {
		// String gasto = "";
		// for (Iterator iterator =
		// ordendetalle_data.getCompracab().getCompradet().iterator();
		// iterator.hasNext();) {
		// CompraDetEJBData det = (CompraDetEJBData) iterator.next();
		// gasto = gasto + det.getConcepto() + " ";
		// }
		// value = gasto;
		// } else {
		// value = ordendata.getTipotransaccion().getDescripcion();
		// }
		// }

		if ("concepto".equals(field.getName())) {
			value = ordendetalle_data.getConcepto();
		}

		return value;
	}
}