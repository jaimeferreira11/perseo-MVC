var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
$(document).ready(function(){
	
	var table = $('#tablaDetalleVenta').DataTable({
	   	 "language": { "url": context + "/js/plugins/dataTables/Spanish.json"},
	   	pageLength: 10,
	       responsive: true,
	       dom: '<"html5buttons"B>lTfgitp',
	       buttons: [
	           {extend: 'csv', title: 'Detalle Venta',exportOptions: { columns: ':visible'}},
	           {extend: 'excel', title: 'Detalle Venta',exportOptions: { columns: ':visible'}},
	           {extend: 'pdf', title: 'Detalle Venta',exportOptions: { columns: ':visible'}}
	       ],
	       //formato numerico
	       "createdRow": function ( row, data, index ) {
	               $('td', row).eq(2).addClass('price');
	               $('td', row).eq(3).addClass('price');
	               $('td', row).eq(5).addClass('price');
	               $('td', row).eq(6).addClass('price');
	               
	       }

	   });
	
	
	$(".price").mask("000.000.000.000",{reverse: true});



});



function setDetalleVenta(indice){
	var datos = JSON.parse(array_detalle_venta[indice])
	var table = $('#tablaDetalleVenta').DataTable();
	table.clear().draw();
	var cont = 1;
	$.each(datos, function(index, element) {
		
		table.rows.add( [ {
			"0": cont,
			"1": element.articulo.descripcion,
			"2": element.precioventa,
			"3": element.cantidad,
			"4": element.tipoimpuesto.descripcion,
			"5": element.impuesto,
			"6": element.total,
			}]).draw();
			//formato numerico
			$(".price").mask("000.000.000.000",{reverse: true});
			cont++;
	});	
    
}
