package py.com.perseo.tesoreria.datasources;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import py.com.perseo.tesoreria.entities.Ordenpagocab;
import py.com.perseo.tesoreria.entities.Ordenpagofp;

import java.util.Iterator;
import java.util.List;


public class JRDataSourceFormaPago implements JRDataSource {
	Iterator it = null;

	Ordenpagofp forma;

	public JRDataSourceFormaPago(List<Ordenpagofp> c, Ordenpagocab ordenpagcab) {
		it = c.iterator();
	}

	public boolean next() throws JRException {
		boolean ret = false;
		ret = it.hasNext();
		if (ret) {
			forma = (Ordenpagofp) it.next();
		}
		return ret;
	}

	public Object getFieldValue(JRField field) throws JRException {
		Object value = null;
		if ("sFormaPago".equals(field.getName())) {
			if (forma.getCajacuenta() != null) {
				if (forma.getCajacuenta().getTipo().equals("C")) {
					value = "CHEQUE";
				} else if (forma.getCajacuenta().getTipo().equals("E")) {
					value = "EFECTIVO";
				} else if (forma.getCajacuenta().getTipo().equals("A")) {
					value = "CAJA DE AHORRO";
				} else if (forma.getCajacuenta().getTipo().equals("B")) {
					value = "BANCO";
				} else if (forma.getCajacuenta().getTipo().equals("Z")) {
					value = "ANTICIPO";
				} else if (forma.getCajacuenta().getTipo().equals("F")) {
					value = "FONDO FIJO";
				} else if (forma.getCajacuenta().getTipo().equals("N")) {
					value = "NOTA DE CREDITO";
				}
			}
		}
		if ("sDescripcion".equals(field.getName())) {
			if (forma.getCajacuenta() != null) {
				if (forma.getCajacuenta().getTipo().equals("C")) {
					value = forma.getCajacuenta().getDescripcion();
				} else if (forma.getCajacuenta().getTipo().equals("E")) {
					value = forma.getCajacuenta().getDescripcion();
				} else if (forma.getCajacuenta().getTipo().equals("A")) {
					value = forma.getCajacuenta().getDescripcion();
				} else if (forma.getCajacuenta().getTipo().equals("B")) {
					value = forma.getCajacuenta().getDescripcion();
				} else if (forma.getCajacuenta().getTipo().equals("F")) {
					value = forma.getCajacuenta().getDescripcion();
				} else if (forma.getCajacuenta().getTipo().equals("Z")) {
					value = forma.getCajacuenta().getDescripcion();
				} else if (forma.getCajacuenta().getTipo().equals("N")) {
					value = forma.getCajacuenta().getDescripcion();
				}
			}
		}
		if ("sTransaccion".equals(field.getName())) {
			if (forma.getTransaccion() != null) {
				value = forma.getTransaccion();
			} else {
				value = "";
			}
		}
		if ("nImporte".equals(field.getName())) {
			value = forma.getImporte();
		}
		

		return value;
	}
}