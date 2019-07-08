package py.com.perseo.venta.reports.datasource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import py.com.perseo.venta.entities.Facturadet;

import java.util.Collection;
import java.util.Iterator;


public class JRDataSourceFactura implements JRDataSource {
	Iterator it = null;

	Facturadet facturadet_data;

	public JRDataSourceFactura(Collection c) {
		it = c.iterator();
	}

	public boolean next() throws JRException {
		boolean ret = false;
		ret = it.hasNext();
		if (ret) {
			facturadet_data = (Facturadet) it.next();
		}
		return ret;
	}

	public Object getFieldValue(JRField field) throws JRException {
		Object value = null;
		if ("sArticulo".equals(field.getName())) {
			value = facturadet_data.getConcepto();
		}
		if ("tasa".equals(field.getName())) {
			if (facturadet_data.getArticulo().getTipoimpuesto().getIdtipoimpuesto() > 0) {
				if (facturadet_data.getArticulo().getTipoimpuesto().getIdtipoimpuesto() == 10) {
					value = "10%";
				}else if (facturadet_data.getArticulo().getTipoimpuesto().getIdtipoimpuesto() == 5){
					value = "5%";
				}
			}else{
				value = "Ex";
			}
		}
		if ("nCantidad".equals(field.getName())) {
			value = facturadet_data.getCantidad();
		}
		if ("nExentas".equals(field.getName())) {
			value = facturadet_data.getExenta();
		}
		if ("nGravada5".equals(field.getName())) {
			value = facturadet_data.getGravada5().doubleValue()+facturadet_data.getIva5().doubleValue();
		}
		if ("nGravada10".equals(field.getName())) {
			value = facturadet_data.getGravada10().doubleValue()+facturadet_data.getIva10().doubleValue();
		}
		if ("nPrecio".equals(field.getName())) {
			value = facturadet_data.getPrecioventa();
		}
		if ("totallinea".equals(field.getName())) {
			value = facturadet_data.getGravada5() + facturadet_data.getIva5() + facturadet_data.getGravada10() + facturadet_data.getIva10() + facturadet_data.getExenta();
		}
		return value;
	}
}