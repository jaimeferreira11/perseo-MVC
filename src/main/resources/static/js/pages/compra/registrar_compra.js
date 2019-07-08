var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var login;
var n = 0;
var all = true;

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

    $("#nrotimbrado").mask("99999999");
    $("#nrocomprobante").mask("9999999");
    $("#venctimbrado").mask("99/99/9999");
    $("#fecha").mask("99/99/9999");
    $("#establecimiento").mask("999");
    $("#expedicion").mask("999");
    enmascarar();

    $("#punit").blur(function() {
        calcularSubtotalItem();
        calcularImpuestoItem();
    });
    $("#cantidad").blur(function() {
        calcularSubtotalItem();
        calcularImpuestoItem();
    });
    $('#selectImpuesto').chosen().change(function () {
        calcularImpuestoItem();
    });
    $("#agregar").click(function(){
        agregarFila();
    });
    $('input[name=columna]:radio').on('ifChanged', function () {
        if ($(this).val() == 'FA') {
            $("#div_timbrado").show();
            $("#venctimbrado").val("");
            $("#div_establecimiento").show();
            $("#div_expedicion").show();
        }else if ($(this).val() == 'RE') {
            $("#div_timbrado").hide();
            $("#venctimbrado").val("12/31/2010");
            $("#div_establecimiento").hide();
            $("#div_expedicion").hide();
        }
    });
    
    // servicio o articulo
    $('input[name=tipoprovision]:radio').on('ifChanged', function () {

        if ($(this).val() == 1) {
           $("#divArticulo").show();
           $("#divServicio").hide();
        }else if ($(this).val() == 2) {
        	$("#divServicio").show();
        	$("#divArticulo").hide();
        }
    });
});

/*
 * Articulos
 * 
 */
function buscarArticulo(){
	//clear()
	if($("#articulo").val() == ""){		
		$('#buscador_articulo').modal();
	}else{
		// ver en buscador articulo venta y copiar el api
		getArticuloByParam2( $("#articulo").val(), all)
		
	}
}

//onclick del input documento
$('#articulo').keydown(function(event){ 
    var keyCode = (event.keyCode ? event.keyCode : event.which);   
    if (keyCode == 13) {
        //buscar cliente por numero de cedula
    	getArticuloByParam2( $("#articulo").val(), all)

    }
});

function setArticulo(articulo){
	console.log(articulo)
	if(articulo != null){	
		desenmascarar();
		//setear los valores
		$("#idarticulo").val(articulo.idarticulo);
		$("#articulo").val("");
		$("#descripcion").val(articulo.descripcion);
		$("#cantidad").val(1);
		$("#cantidad").attr({ "min" : 1});
		//$("#precio").val(articulo.precioventa)
		if(articulo.tipoimpuesto.idtipoimpuesto != 100){			
			$("#iva").val(articulo.tipoimpuesto.idtipoimpuesto);
		}
		enmascarar();
	}
	
	// servicio o articulo
    $('input[name=tipoprovision]:radio').on('ifChanged', function () {

        if ($(this).val() == 1) {
           $("#divArticulo").show();
           $("#divServicio").hide();
        }else if ($(this).val() == 2) {
        	$("#divServicio").show();
        	$("#divArticulo").hide();
        }
    });
}

function insertRegistroFromBuscador(){
	var ban = false;
	var table = document.getElementById('tablaArticulos');
	var rowLength = table.rows.length;
	
	for (var i = 1; i < rowLength; i += 1) {
		var row = table.rows[i];
		var chkbox = row.cells[0].childNodes[0];
		if (true == chkbox.checked) {
			ban = true;
			$('#buscador_articulo').modal('toggle');
			var articulo = JSON.parse(row.cells[4].innerHTML);
			setArticulo(articulo)
			
		}

	}

	if (!ban) {
		 swal("","Debes seleccionar un registro", "warning");
	}else{
		limpiarBuscador();
    }
}

/****
 * 
 * @returns
 */

function enmascarar(){
    $("#total").mask("000.000.000.000",{reverse: true});
    $("#exenta").mask("000.000.000.000",{reverse: true});
    $("#gravada5").mask("000.000.000.000",{reverse: true});
    $("#gravada10").mask("000.000.000.000",{reverse: true});
    $("#importe5").mask("000.000.000.000",{reverse: true});
    $("#importe10").mask("000.000.000.000",{reverse: true});
    $("#punit").mask("000.000.000.000",{reverse: true});
    $("#subtotal").mask("000.000.000.000",{reverse: true});
    $("#cantidad").mask("000.000.000.000",{reverse: true});
    $("#impuesto").mask("000.000.000.000",{reverse: true});

}

function desenmascarar(){
    $("#total").unmask();
    $("#exenta").unmask();
    $("#gravada5").unmask();
    $("#gravada10").unmask();
    $("#importe5").unmask();
    $("#importe10").unmask();
    $("#punit").unmask();
    $("#subtotal").unmask();
    $("#cantidad").unmask();
    $("#impuesto").unmask();
}

function calcularSubtotalItem(){
    desenmascarar();
    var precio = $("#punit").val().replace(/\./g,'');
    precio = precio === "" ? "0" : precio;
    var cantidad = $("#cantidad").val().replace(/\./g,'');
    cantidad = cantidad === "" ? "0" : cantidad;
    $("#subtotal").val(parseInt(precio)*parseInt(cantidad))
    enmascarar();
}

function calcularImpuestoItem(){
    desenmascarar();
    var tipoImpuesto = $('#selectImpuesto').val();
    var monto = 0;
    var subtotal = $("#subtotal").val().replace(/\./g,'');
    subtotal = subtotal === "" ? "0" : subtotal;
    if(tipoImpuesto==="5"){
        monto = parseInt(subtotal)*0.05;
    }
    else if(tipoImpuesto==="10"){
        monto = parseInt(subtotal)*0.1;
    }
    else if(tipoImpuesto==="100"){
        monto = 0;
    }
    $("#impuesto").val(parseInt(monto));
    enmascarar();
}

function agregarFila(){
    n += 1;
    var $concepto = $("#concepto");
    var $cantidad = $("#cantidad");
    var $punit = $("#punit");
    var $selectImpuesto = $("#selectImpuesto");
    var tipoProvision = $('input[name="tipoprovision"]:checked').val();
    var tipoProvisionText = tipoProvision === "1" ? "Artículo" : "Servicio";
    var concepto = $concepto.val();
    var cantidad = $cantidad.val();
    var punit = $punit.val();
    var subtotal = $("#subtotal").val();
    var impuesto = $("#impuesto").val();
    var tipoImpuesto = $selectImpuesto.find(":selected").val();
    var descripcion = $("#descripcion").val();
    var idarticulo = $("#idarticulo").val();

    var error = 0;
    error = inputRequired($cantidad) + inputRequired($punit);
    if (tipoProvision==="2"){
        error = error + inputRequired($concepto);
        idarticulo = 0
    }else {
        concepto = descripcion;
    }

    if(error > 0){
        return;
    }

    if($cantidad.val()==="0"  || $punit.val()==="0"){
        swal("","Cantidad o Precio no pueden ser cero", "warning");
        return;
    }

    $('#tabla > tbody:last-child').append('<tr><td id="cel_1" style="vertical-align: middle;">'+tipoProvisionText+'</td>'
        +'<td id="cel_2" style="vertical-align: middle;">'+concepto+'</td>'
        +'<td id="cel_3" style="vertical-align: middle;">'+cantidad+'</td>'
        +'<td id="cel_4" style="vertical-align: middle;">'+punit+'</td>'
        +'<td id="cel_5" style="vertical-align: middle;">'+(tipoImpuesto==="100" ? subtotal : "0")+'</td>'
        +'<td id="cel_6" style="vertical-align: middle;">'+(tipoImpuesto==="5" ? impuesto : "0")+'</td>'
        +'<td id="cel_7" style="vertical-align: middle;">'+(tipoImpuesto==="10" ? impuesto : "0")+'</td>'
        +'<td id="cel_8" style="vertical-align: middle;">'+subtotal+'</td>'
        +'<td style="vertical-align: middle;"><button type="button" class="btn btn-w-m btn-outline btn-danger" id="del_'+n+'" style="font-size: 13px;">Eliminar</button></td>'
        +'<td id="cel_9" style="display: none;">'+tipoProvision+'</td>'
        +'<td id="cel_10" style="display: none;">'+tipoImpuesto+'</td>'
        +'<td id="cel_11" style="display: none;">'+idarticulo+'</td></tr>');

    $("#del_"+n).click(function(){
        $(this).parent().parent().remove();
        calcularTotales();
    });

    calcularTotales();

    $concepto.val("");
    $cantidad.val("0");
    $punit.val("0")
    $("#subtotal").val("0");
    $("#impuesto").val("0");
}

function calcularTotales(){
    desenmascarar();
    var totalImporte = 0;
    var exenta = 0;
    var gravada5 = 0;
    var gravada10 = 0;
    var importe10 = 0;
    var importe5 = 0;

    $("#tabla_body tr").each(function () {
        var monto_exenta = $(this).find("#cel_5").text().replace(/\./g,'');
        monto_exenta = monto_exenta === "" ? "0" : monto_exenta;
        monto_exenta = parseInt(monto_exenta);
        exenta = exenta + monto_exenta;

        var monto_iva5 = $(this).find("#cel_6").text().replace(/\./g,'');
        monto_iva5 = monto_iva5 === "" ? "0" : monto_iva5;
        monto_iva5 = parseInt(monto_iva5);
        importe5 = importe5 + monto_iva5;

        var monto_iva10 = $(this).find("#cel_7").text().replace(/\./g,'');
        monto_iva10 = monto_iva10 === "" ? "0" : monto_iva10;
        monto_iva10 = parseInt(monto_iva10);
        importe10 = importe10 + monto_iva10;

        var monto_fila = $(this).find("#cel_8").text().replace(/\./g,'');
        monto_fila = monto_fila === "" ? "0" : monto_fila;
        monto_fila = parseInt(monto_fila);
        totalImporte = totalImporte + monto_fila;

        var tipoImpuesto = $(this).find("#cel_10").text();
        if (tipoImpuesto==="10"){
            gravada10 = gravada10 + monto_fila;
        }
        else if (tipoImpuesto==="5"){
            gravada5 = gravada5 + monto_fila;
        }
    });

    $("#exenta").val(parseInt(exenta));
    $("#importe5").val(parseInt(importe5));
    $("#importe10").val(parseInt(importe10));
    $("#total").val(parseInt(totalImporte));
    $("#gravada5").val(parseInt(gravada5));
    $("#gravada10").val(parseInt(gravada10));
    enmascarar();
}

function grabar(){
    //Datos de la factura
    var proveedor = $("#selectProveedor").find(":selected").val();
    var condicioncompra = $('input[name="condicion"]:checked').val();
    var documento = $('input[name="columna"]:checked').val();
    var nrotimbrado = $("#nrotimbrado").val();
    var venctimbrado = $("#venctimbrado").val();
    var fecha = $("#fecha").val();
    var establecimiento = $("#establecimiento").val();
    var expedicion = $("#expedicion").val();
    var nrocomprobante = $("#nrocomprobante").val();
    var total = $("#total").val().replace(/\./g,'');;
    var importe10 = $("#importe10").val().replace(/\./g,'');;
    var importe5 = $("#importe5").val().replace(/\./g,'');;
    var gravada10 = $("#gravada10").val().replace(/\./g,'');;
    var gravada5 = $("#gravada5").val().replace(/\./g,'');;
    var exenta = $("#exenta").val().replace(/\./g,'');;
    var observaciones = $("#observaciones").val();
    //Detalles de la factura

    // Validaciones para grabar
    if(proveedor==="Seleccione"){
        swal("","Seleccione un proveedor", "warning");
        return;
    }
    var error = 0;
    var comprobante;
    if(documento==="FA"){
        error = inputRequired($("#nrotimbrado"))+inputRequired($("#venctimbrado"))+inputRequired($("#fecha"))
            +inputRequired($("#establecimiento"))+inputRequired($("#expedicion"))+inputRequired($("#nrocomprobante"));
        comprobante = establecimiento+"-"+expedicion+"-"+nrocomprobante;
    }
    else if(documento==="RE"){
        error = inputRequired($("#fecha"))+inputRequired($("#nrocomprobante"));
        comprobante = nrocomprobante;
    }

    if(error>0){
        return;
    }

    var compracab = {};
    var proveedorObj = {};
    var usuario = {};
    usuario.idusuario = login.idusuario;
    proveedorObj.idproveedor = proveedor;
    compracab.usuario = usuario;
    compracab.proveedor = proveedorObj;
    compracab.fecha = fecha;
    compracab.condicion = condicioncompra;
    compracab.tipo = documento;
    compracab.timbrado = nrotimbrado
    compracab.fecvenctimbrado = venctimbrado;
    compracab.comprobante = comprobante;
    compracab.gravada10 = gravada10;
    compracab.gravada5 = gravada5;
    compracab.iva10 = importe10;
    compracab.iva5 = importe5;
    compracab.exenta = exenta;
    compracab.importe = total;
    compracab.observaciones = observaciones;

    var compradet = [];
    $("#tabla_body tr").each(function () {
        var item = {};
        var concepto = $(this).find("#cel_2").text();
        var cantidad = $(this).find("#cel_3").text().replace(/\./g,'');;
        var precio = $(this).find("#cel_4").text().replace(/\./g,'');;
        var exenta = $(this).find("#cel_5").text().replace(/\./g,'');;
        var gravada = $(this).find("#cel_8").text().replace(/\./g,'');;
        var tipoProvision = $(this).find("#cel_9").text();
        var tipoImpuesto = $(this).find("#cel_10").text();
        var ivaImporte = $(this).find("#cel_7").text().replace(/\./g,'');;
        if(tipoImpuesto==="5"){
            ivaImporte =  $(this).find("#cel_6").text().replace(/\./g,'');;
        }
        var idarticulo = $(this).find("#cel_11").text();

        item.concepto = concepto;
        item.cantidad = cantidad;
        item.precio = precio;
        item.exenta = exenta;
        item.gravada = gravada;
        item.ivaporcentaje = tipoImpuesto;
        item.ivaimporte = ivaImporte;
        item.tipoprovision = tipoProvision;
        var articulodeposito = {};
        var articulo = {};
        articulo.idarticulo = idarticulo;
        articulodeposito.articulo = articulo;
        item.articulodeposito = articulodeposito;
        compradet.push(item);
    });

    var datos = {};
    datos.compracab = compracab;
    datos.compradet = compradet;

    $.ajax({
        type: "POST",
        url: context + "/api/v1/compra/agregar/",
        data: JSON.stringify(datos),
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