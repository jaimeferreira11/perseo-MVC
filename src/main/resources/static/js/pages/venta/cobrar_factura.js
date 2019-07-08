var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var login;
var n = 0;
var table;
var array_detalle_venta = [];
$(document).ready(function(){
    login = JSON.parse(sjcl.decrypt(encrypt, localStorage.usuario));
    
    table = $('#tabla').DataTable({
      	 "language": { "url": context + "/js/plugins/dataTables/Spanish.json"},
      	 pageLength: 10,
        responsive: true,
      //formato numerico
        "createdRow": function ( row, data, index ) {
        	$('td', row).eq(4).addClass('price');
            $('td', row).eq(5).addClass('price');
        }

   	});
    
   // $('#forma_pago').modal()
  //seleccionar un registro de la tabla
	 $('#tabla tbody').on( 'click', 'tr', function () {
	     if($(this).hasClass('selected') ) {
	         $(this).removeClass('selected');
	         clear()
	      }else{
	         table.$('tr.selected').removeClass('selected');
	         $(this).addClass('selected');
	         insertForm(this);
	         
	       }
	   });
    

    //inicializar los check y radiobutton con el plugins Ichecks.js
	$('.i-checks').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green'
    });

    //inicializar el select con el pluguins choosen.js
    $('.chosen-select').chosen({
       	width: "100%",
       	inherit_select_classes: true
    });
    
 // obtener el usuario logueado
	login = JSON.parse(sjcl.decrypt(encrypt, localStorage.usuario));
	console.log(login)
	

    enmascarar();

    $("#precio").blur(function() {
        calcularSubtotalItem();

    });
    $("#cantidad").blur(function() {
        calcularSubtotalItem();

    });

   // getAll()
 

});

/////////////////////////////////
/*
 * Cliente
 */

function buscarCliente(){
	//clear()
	if($("#documento").val() == ""){		
		$('#buscador_cliente').modal()
	}else{
		getClienteByNrodoc($("#selectTipoDoc").val(), $("#documento").val())
	}
	
}

//inserta el registro desde el buscador
function insertClienteFromBuscador(){
	var ban = false;
	var table = document.getElementById('tablaClientes');
	var rowLength = table.rows.length;
	
	for (var i = 1; i < rowLength; i += 1) {
		var row = table.rows[i];
		var chkbox = row.cells[0].childNodes[0];
		
		if (true == chkbox.checked) {
			ban = true;
			$('#buscador_cliente').modal('toggle');	
			var cliente = JSON.parse(row.cells[4].innerHTML)
			setCliente(cliente)
			/*$("#idcliente").val(cliente.idcliente);
			$("#selectTipoDoc").val(cliente.tipodocumento.codtipodoc)
			
			$("#cliente").val(cliente.nombreapellido);
			$("#documento").val(cliente.nrodoc);*/
		}
	}
	
	if (!ban) {
		 swal("","Debes seleccionar un registro", "warning")
	}else{
		limpiarBuscador()
	}
}

function setCliente(cliente){
	console.log(cliente)
	if(cliente != null){		
		$("#idcliente").val(cliente.idcliente);
		$("#selectTipoDoc").val(cliente.tipodocumento.codtipodoc)
		$("#cliente").val(cliente.nombreapellido);
		$("#documento").val(cliente.nrodoc);
		$("#idprecioventa").val(cliente.articuloprecioventacab.idarticuloprecioventacab);
		deshabilitar($("#documento"))
		getByCliente(cliente.idcliente)
	}
}

//onclick del input documento
$('#documento').keydown(function(event){ 
    var keyCode = (event.keyCode ? event.keyCode : event.which);   
    if (keyCode == 13) {
        //buscar cliente por numero de cedula
    	getClienteByNrodoc($("#selectTipoDoc").val(), $("#documento").val())

    }
});


//insertar la fila seleccionada en el form
function insertForm(fila) {
	desenmascarar()
	var obj = JSON.parse(fila.cells[7].innerHTML)
	console.log(obj)
	$("#idfacturacab").val(obj.idfacturacab);
	$("#idcliente").val(obj.cliente.idcliente);
	$("#selectTipoDoc").val(obj.cliente.tipodocumento.codtipodoc)
	$("#documento").val(obj.cliente.nrodoc);
	$("#cliente").val(obj.cliente.nombreapellido);
	//totales
	//$("#subtotal").val((parseFloat(obj.importe) - parseFloat(obj.impuesto)))
	//$("#totiva").val(obj.impuesto);
	$("#total").val(obj.importe);
	habilitar($("#grabar"))
	enmascarar();
	
}

function clear(){
	$("#idventacab").val(0);
	$("#idcliente").val(0);
	$("#selectTipoDoc").val("CI")
	$("#documento").val("");
	$("#cliente").val("");
	deshabilitar($("#grabar"))
	$("#total").val("");
}



//obtener depositos por empresa
function getByCliente(idcliente){
	blockScreen();
	$.ajax({
		type : "GET",
		url : context + "/api/v1/facturacab/cliente/"+idcliente+"/false/false",
		success : function(data) {
			console.log(data)
			data = JSON.stringify(data);
			var jsonobject = JSON.parse(data);
			if (jsonobject.estado == 'ERROR') {
				 swal("ERROR", jsonobject.error, "error");
			} else if (jsonobject.estado == 'OK') {
				if(jsonobject.datos.length == 0){
					 swal("Atencion", "No hay Facturas pendientes", "info");
					 clear();
				}else{
					$.each(jsonobject.datos, function(index, element) {
						
						var cont = 0;
						var detalleVenta = JSON.stringify(element.detalleFactura);
						array_detalle_venta.push(detalleVenta)
						table.rows.add( [ {
							"0": element.idfacturacab,
							"1": formatFecha(element.fecha),
							"2": element.cliente.nrodoc + " "+element.cliente.nombreapellido,
							"3": element.sucursal.descripcion,
							"4": element.importe,
							"5": element.saldo,
							"6": '<a data-toggle="modal" href="#detalle_factura" onclick="setDetalleVenta('+cont+')">Ver Detalles</a>',
							"7": JSON.stringify(element),
							
						}
						] ).draw();
						enmascarar();
						cont++;
						
					});
					
				}
			}
			$(document).ajaxStop($.unblockUI);
		},
		error : function(e) {
			console.log("ERROR: ", e);
			$(document).ajaxStop($.unblockUI);
		},
		done : function(e) {
			console.log("DONE");
		}
	});
}







////////////////////////////////

function enmascarar(){

	$(".price").mask("000.000.000.000",{reverse: true});

}

function desenmascarar(){
    $(".price").unmask();


}



function grabar(){
    //Datos de la factura
    var idFacturaCab = $("#idfacturacab").val();
   
    console.log(idFacturaCab)
    // Validaciones para grabar
    var error = 0;

    if(idFacturaCab == 0){
        return;
    }
    blockScreen();
    $.ajax({
        type: "POST",
        url: context + "/api/v1/factura/cobrar-factura/"+idFacturaCab,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data) {
            console.log(data);
            data = JSON.stringify(data);
            var jsonobject = JSON.parse(data);
            if (jsonobject.estado == "ERROR") {
                $('#modal').modal("toggle")
                swal("ERROR", jsonobject.error, "error");
            } else if (jsonobject.estado == 'OK') {
                swal("Proceso correcto", jsonobject.datos  , "success")
                
                setTimeout(function(){
                  location.reload(true);
                }, 2000);
            }
            $(document).ajaxStop($.unblockUI);
        },
        error: function(e) {
            console.log("ERROR", e);
            $(document).ajaxStop($.unblockUI);
        },
        done: function(e) {
            console.log("DONE");
        }
    });
    
}