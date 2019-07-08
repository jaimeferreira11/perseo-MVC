var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var login;
var n = 0;
var table;
var array_detalle_venta = [];
//variable para modal forma_pago
var fp_total_a_pagar = 0;
$(document).ready(function(){
    login = JSON.parse(sjcl.decrypt(encrypt, localStorage.usuario));
    
    table = $('#tabla').DataTable({
      	 "language": { "url": context + "/js/plugins/dataTables/Spanish.json"},
      	 pageLength: 10,
        responsive: true,
        "order": [[ 1, "desc" ]],
      //formato numerico
        "createdRow": function ( row, data, index ) {
                $('td', row).eq(5).addClass('price');
        }

   	});
    
   
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

    getAll();
    getNumeracion();
 
   

});

function grabar(){
	 fp_total_a_pagar = $("#total").val().replace(/\./g,'');;

	    inicializarModalFormaPago();
	    
	    $('#forma_pago').modal()
	     
	    
}

//insertar la fila seleccionada en el form
function insertForm(fila) {
	desenmascarar()
	var obj = JSON.parse(fila.cells[7].innerHTML)
	console.log(obj)
	$("#idventacab").val(obj.idventacab);
	$("#idcliente").val(obj.cliente.idcliente);
	$("#selectTipoDoc").val(obj.cliente.tipodocumento.codtipodoc)
	$("#documento").val(obj.cliente.nrodoc);
	$("#cliente").val(obj.cliente.nombreapellido);
	//totales
	$("#subtotal").val((parseFloat(obj.importe) - parseFloat(obj.impuesto)))
	$("#totiva").val(obj.impuesto);
	$("#total").val(obj.importe);
	
	enmascarar();
	
	habilitar($("#grabar"))
	

}

function clear(){
	$("#idventacab").val(0);
	$("#idcliente").val(0);
	$("#selectTipoDoc").val("CI")
	$("#documento").val("");
	$("#cliente").val("");
	//totales
	$("#subtotal").val("")
	$("#totiva").val("");
	$("#total").val("");
}




//obtener depositos por empresa
function getAll(){
	blockScreen();
	$.ajax({
		type : "GET",
		url : context + "/api/v1/ventacab/estado/1",
		success : function(data) {
			console.log(data)
			data = JSON.stringify(data);
			var jsonobject = JSON.parse(data);
			if (jsonobject.estado == 'ERROR') {
				 swal("ERROR", jsonobject.error, "error");
			} else if (jsonobject.estado == 'OK') {
				$.each(jsonobject.datos, function(index, element) {
					/*var estado = "";
					if(element.estado == true){
						estado = '<span class="label label-primary pull-center">Activo</span>'
					}else{
						estado ='<span class="label label-danger pull-center">Activo</span>'
					}*/
					var cont = 0;
					var detalleVenta = JSON.stringify(element.detalleVenta);
					array_detalle_venta.push(detalleVenta)
					table.rows.add( [ {
							"0": element.idventacab,
							"1": element.cliente.nrodoc + " "+element.cliente.nombreapellido,
							"2": element.sucursal.descripcion,
							"3": element.usuario.usuario,
							"4": formatFecha(element.fecha),
							"5": element.importe,
							"6": '<a data-toggle="modal" href="#detalle_venta" onclick="setDetalleVenta('+cont+')">Ver Detalles</a>',
							"7": JSON.stringify(element),

						}
						] ).draw();
					enmascarar();
					cont++;
					

					});
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



//obtener el numero de factura por caja
function getNumeracion(){
	$.ajax({
		type : "GET",
		url : context + "/api/v1/factura/numeracion-factura/",
		success : function(data) {
			console.log(data)
			data = JSON.stringify(data);
			var jsonobject = JSON.parse(data);
			if (jsonobject.estado == 'ERROR') {
				 swal("ERROR", jsonobject.error, "error");
			} else if (jsonobject.estado == 'OK') {
				$.each(jsonobject.datos, function(index, element) {
			
					setNumeracion(jsonobject.datos)

				});
			}

		},
		error : function(e) {
			console.log("ERROR: ", e);

		},
		done : function(e) {
			console.log("DONE");
		}
	});
}


function setNumeracion(obj){
	$("#establecimiento").val(obj.establecimiento)
	$("#expedicion").val(obj.puntoexpedicion)
	$("#nrocomprobante").val(obj.nrofactura)
	$("#nrotimbrado").val(obj.timbrado)
	
	
}


////////////////////////////////

function enmascarar(){

	$(".price").mask("000.000.000.000",{reverse: true});

}

function desenmascarar(){
    $(".price").unmask();


}



function confirmacionFP(){
	
	
	
    //Datos de la factura
    var idventacab = $("#idventacab").val();
    var condicion = 1;
    if($('input[name="condicion"]:checked').val() === 'CO'){
    	condicion = 1;
    }else if($('input[name="condicion"]:checked').val() === 'CR'){
    	condicion = 2;
    }

    // Validaciones para grabar
    var error = 0;

    if(error>0){
        return;
    }
    
    //factura forma pago
    var datos = {};
    var listFormaPago = [];
    var row = 0;
    $("#tablaFormaPago tr").each(function () {
        var item = {};
        if(row> 0){
        	
        	item.importe = $(this).find("td").eq(2).text().replace(/\./g,'');
        	item.estado = true;
        	item.idcajacuenta =  $(this).find("td").eq(0).text();
        	item.referencia  =  $(this).find("td").eq(3).text();
        	
        	
        	
        	listFormaPago.push(item);
        }
        
         row++;
    });
    datos.listFormaPago = listFormaPago;
    datos.idventacab = idventacab;
    datos.clasefactura = 'F'
    datos.condicion = condicion;
    
    console.log(JSON.stringify(datos))
    
    $('#forma_pago').modal("toggle")
    
    blockScreen();
    $.ajax({
        type: "POST",
        url: context + "/api/v1/venta/procesar-venta/",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(datos),
        dataType: "json",
        success: function(data) {
            console.log(data);
            data = JSON.stringify(data);
            var jsonobject = JSON.parse(data);
            if (jsonobject.estado == "ERROR") {
            	$(document).ajaxStop($.unblockUI);
                $('#modal').modal("toggle")
                swal("ERROR", jsonobject.error, "error");
            } else if (jsonobject.estado == 'OK') {
            	$(document).ajaxStop($.unblockUI);
            	swal({
        			title: "Proceso correcto",
        			text: "",
        			type: "success",
        			// showCancelButton: true,
        			//  confirmButtonClass: "btn-danger",
        			confirmButtonText: "Descargar",
        			closeOnConfirm: false
        		},
        		function(){
        			var filename = jsonobject.datos;
        			var url = context + "/downloadFile?filename="+filename;
        			location.href=url;
        			setTimeout(function(){
        				location.reload(true);
        			}, 1000);
        		});
            }
            
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