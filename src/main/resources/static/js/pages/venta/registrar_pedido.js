var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var login;
var n = 0;

$(document).ready(function(){
    login = JSON.parse(sjcl.decrypt(encrypt, localStorage.usuario));
    
    

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
		habilitar($(".campoHabilitable"))
	}
}

//onclick del input documento
$('#documento').keydown(function(event){ 
    var keyCode = (event.keyCode ? event.keyCode : event.which);   
    if (keyCode == 13) {
    	$("#idcliente").val(0);
		$("#cliente").val("");
        //buscar cliente por numero de cedula
    	getClienteByNrodoc($("#selectTipoDoc").val(), $("#documento").val())

    }
});

/*
 * Articulos
 * 
 */
function buscarArticulo(){
	//clear()
	if($("#articulo").val() == ""){		
		$('#buscador_articulo_venta').modal();
	}else{
		getArticuloByParam2($("#idprecioventa").val(), $("#articulo").val())
		
	}
}

//onclick del input documento
$('#articulo').keydown(function(event){ 
    var keyCode = (event.keyCode ? event.keyCode : event.which);   
    if (keyCode == 13) {
        //buscar cliente por numero de cedula
    	getArticuloByParam2($("#idprecioventa").val(), $("#articulo").val())

    }
});

function setArticulo(articulo){
	console.log(articulo)
	if(articulo != null){	
		desenmascarar()
		$("#idarticulo").val(articulo.articulo.idarticulo);
		$("#articulo").val("");
		$("#descripcion").val(articulo.articulo.descripcion)
		$("#cantidad").attr({"max" : articulo.cantidad, "min" : 1});
		$("#cantidad").val(1)
		$("#precio").val(articulo.precioventa)
		if(articulo.articulo.tipoimpuesto.idtipoimpuesto != 100){			
			$("#iva").val(articulo.articulo.tipoimpuesto.idtipoimpuesto)
		}
		calcularSubtotalItem();

		
		
		enmascarar();
		
	}
}


function insertRegistroFromBuscador(){
	var ban = false;
	var table = document.getElementById('tablaArticulosVenta');
	var rowLength = table.rows.length;

	for (var i = 1; i < rowLength; i += 1) {
		var row = table.rows[i];
		var chkbox = row.cells[0].childNodes[0];
		if (true == chkbox.checked) {
			ban = true;
			$('#buscador_articulo_venta').modal('toggle');
			var articulo = JSON.parse(row.cells[5].innerHTML);
			console.log(articulo);
			setArticulo(articulo)
			/*$("#idarticulo").val(articulo.articulo.idarticulo);
			$("#descripcion").val(articulo.articulo.descripcion);
			$("#cantidad").val(articulo.cantidad)
			 $("#cantidad").attr({"max" : articulo.cantidad, "min" : 1});*/
			
		}
	}

	if (!ban) {
		 swal("","Debes seleccionar un registro", "warning");
	}else{
		limpiarBuscadorArticulo();
    }
}


function agregarFila(){
	habilitar($("#grabar"))
    n += 1;
    var $id = $("#idarticulo");
    var $articulo = $("#articulo");
    var $cantidad = $("#cantidad");
    var $punit = $("#precio");
    var $tipoImpuesto = $("#iva");
    var $importeiva = $("#importeiva");
    var $importe = $("#importe");
    var $exenta = $("#exenta");
    var $gravada = $("#gravada");
  

    var error = 0;
    error = inputMayor0($id) + inputRequired($cantidad) + inputRequired($punit);

    if(error > 0){
        return;
    }

    /*if($cantidad.val()==="0"  || $punit.val()==="0"){
        swal("","Cantidad o Precio no pueden ser cero", "warning");
        return;
    }*/

    $('#tabla > tbody:last-child').append('<tr><td id="cel_1" style="vertical-align: middle;">'+$id.val()+'</td>'
        +'<td id="cel_2" style="vertical-align: middle;">'+$("#descripcion").val()+'</td>'
        +'<td id="cel_3" style="vertical-align: middle;">'+$cantidad.val()+'</td>'
        +'<td id="cel_4" style="vertical-align: middle;">'+$punit.val()+'</td>'
        +'<td id="cel_5" style="vertical-align: middle;">'+$exenta.val()+'</td>'
        +'<td id="cel_6" style="vertical-align: middle;">'+$gravada.val()+'</td>'
        +'<td id="cel_7" style="vertical-align: middle;">'+$tipoImpuesto.val()+'</td>'
        +'<td id="cel_8" style="vertical-align: middle;">'+$importeiva.val()+'</td>'
        +'<td id="cel_9" style="vertical-align: middle;">'+$importe.val()+'</td>'
        +'<td style="vertical-align: middle;"><button type="button" class="btn btn-outline btn-danger" id="del_'+n+'"> <i class="fa fa-trash"></i> </button></td>'
        +'<td id="cel_10" style="display: none;">'+$tipoImpuesto.val()+'</td></tr>');

    $("#del_"+n).click(function(){
        $(this).parent().parent().remove();
        calcularTotales();
    });

    calcularTotales();

    $id.val("0")
    $tipoImpuesto.val("0")
    $articulo.val("");
    $cantidad.val("0");
    $punit.val("0")
    $("#importeiva").val("0");
    $("#importe").val("0");
    $("#exenta").val("0");
    $("#gravada").val("0");
    $("#descripcion").val("");
}



////////////////////////////////

function enmascarar(){
    $("#total").mask("000.000.000.000",{reverse: true});
    $("#subtotal").mask("000.000.000.000",{reverse: true});
    $("#totiva").mask("000.000.000.000",{reverse: true});
    
    $("#precio").mask("000.000.000.000",{reverse: true});
    $("#gravada").mask("000.000.000.000",{reverse: true});
    $("#importeiva").mask("000.000.000.000",{reverse: true});
    $("#importe").mask("000.000.000.000",{reverse: true});

}

function desenmascarar(){
    $("#total").unmask();
    $("#subtotal").unmask();
    $("#totiva").unmask();
    //
    $("#precio").unmask();
    $("#importeiva").unmask();
    $("#gravada").unmask();
    $("#importe").unmask();

}

function calcularSubtotalItem(){
    desenmascarar();
    var precio = $("#precio").val().replace(/\./g,'');
    precio = precio === "" ? "0" : precio;
    var cantidad = $("#cantidad").val().replace(/\./g,'');
    cantidad = cantidad === "" ? "0" : cantidad;
    $("#importe").val(parseInt(precio)*parseInt(cantidad))
   calcularImpuestoItem()
}

function calcularImpuestoItem(){
    desenmascarar();
    var tipoImpuesto = $("#iva").val() 
	var subtotal = $("#importe").val()
    var monto = 0;
    subtotal = subtotal === "" ? "0" : subtotal;
    if(tipoImpuesto==="5"){
        monto = parseInt(subtotal)*0.05;
       
        $("#importeiva").val(parseInt(monto));
        $("#gravada").val(parseInt( $("#importe").val()) - parseInt(monto));
    }
    else if(tipoImpuesto==="10"){
        monto = parseInt(subtotal)*0.1;
        $("#importeiva").val(parseInt(monto));
        $("#gravada").val(parseInt( $("#importe").val()) - parseInt(monto));
    }
    else if(tipoImpuesto==="100"){
        monto = 0;
        $("#exenta").val($("#importe").val());
     
    }
    
    enmascarar();
}



function calcularTotales(){
    desenmascarar();
    var totalImporte = 0;
    var exenta = 0;
    var iva = 0;
    var gravada = 0;

    $("#tabla_body tr").each(function () {
 
        //exenta
        var monto_exenta = $(this).find("#cel_5").text().replace(/\./g,'');
        monto_exenta = monto_exenta === "" ? "0" : monto_exenta;
        monto_exenta = parseInt(monto_exenta);
        exenta = exenta + monto_exenta;
        
        
        //gravada
        var monto_gravada = $(this).find("#cel_6").text().replace(/\./g,'');
        monto_gravada = monto_gravada === "" ? "0" : monto_gravada;
        monto_gravada = parseInt(monto_gravada);
        gravada = gravada + monto_gravada;

        //importe iva
        var monto_iva5 = $(this).find("#cel_8").text().replace(/\./g,'');
        monto_iva5 = monto_iva5 === "" ? "0" : monto_iva5;
        monto_iva5 = parseInt(monto_iva5);
        iva = iva + monto_iva5;
        

  
        //total
        var monto_fila = $(this).find("#cel_9").text().replace(/\./g,'');
        monto_fila = monto_fila === "" ? "0" : monto_fila;
        monto_fila = parseInt(monto_fila);
        totalImporte = totalImporte + monto_fila;

        
/*        var tipoImpuesto = $(this).find("#cel_10").text();
        if (tipoImpuesto==="10"){
            gravada = gravada10 + monto_fila;
        }
        else if (tipoImpuesto==="5"){
            gravada = gravada5 + monto_fila;
        }*/

    });

    $("#subtotal").val(parseInt(exenta)+parseInt(gravada));
    $("#totiva").val(parseInt(iva));
    $("#total").val(parseInt(totalImporte));
    enmascarar();
}

function grabar(){
	desenmascarar();
	

    var error = 0;
    //validaciones
    
    if(error>0){
        return;
    }

    var ventacab = {};
    var cliente = {};
    var usuario = {};
    var formapago = {};
    var listVentadet = []
    usuario.idusuario = login.idusuario;
    cliente.idcliente = $("#idcliente").val()

    ventacab.usuario = usuario;
    ventacab.cliente = cliente;
    ventacab.importe = $("#total").val()
    ventacab.impuesto = $("#totiva").val()
    
    //poner el front end un radio 
    /* 1 contado
     * 2 credito
     */
    formapago.idformapago = 1
    ventacab.formapago = formapago;
    
    	
    var cantidad = 0;	
    $("#tabla_body tr").each(function () {

    	var ventadet = {};
    	var articulo = {};
    	articulo.idarticulo = $(this).find("#cel_1").text().replace(/\./g,'');
    	ventadet.articulo = articulo;
    	ventadet.cantidad =  $(this).find("#cel_3").text().replace(/\./g,'');
    	ventadet.precioventa =  $(this).find("#cel_4").text().replace(/\./g,'');
    	var tipoimpuesto = {};
    	tipoimpuesto.idtipoimpuesto =  $(this).find("#cel_10").text();
    	ventadet.tipoimpuesto = tipoimpuesto;
    	//ventadet.preciocosto = (this).find("td").eq(0).html();
    	ventadet.impuesto =  $(this).find("#cel_8").text().replace(/\./g,'');
    	ventadet.total =  $(this).find("#cel_9").text().replace(/\./g,'');
    	listVentadet.push(ventadet)	;
    	cantidad += parseFloat($(this).find("#cel_3").text().replace(/\./g,''));
    });
    
    ventacab.cantidadtotal = cantidad;
    
  
    ventacab.detalleVenta = listVentadet;

    console.log(JSON.stringify(ventacab))
    $.ajax({
        type: "POST",
        url: context + "/api/v1//venta/registrar-pedido/",
        data: JSON.stringify(ventacab),
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
        },
        error: function(e) {
            console.log("ERROR", e);
        },
        done: function(e) {
            console.log("DONE");
        }
    });
}