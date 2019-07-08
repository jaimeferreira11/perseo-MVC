package py.com.perseo.tesoreria.datasources;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import py.com.perseo.tesoreria.entities.Ordenpagocab;
import py.com.perseo.tesoreria.entities.Ordenpagodet;

import java.util.Iterator;
import java.util.List;


public class JRDataSourceOPDetalle implements JRDataSource {
	Iterator it = null;

	Ordenpagodet ordendetalle_data;
	Ordenpagocab ordendata;

	public JRDataSourceOPDetalle(List<Ordenpagodet> c, Ordenpagocab opdata) {
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

		if ("importe".equals(field.getName())) {
			value = ordendetalle_data.getImporte();
		}
		if ("cuenta".equals(field.getName())) {
			value = ordendata.getBeneficiario();
		}
		if ("concepto".equals(field.getName())) {
			value = ordendetalle_data.getConcepto();
		}
		return value;
	}
}