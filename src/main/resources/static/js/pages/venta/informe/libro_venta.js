var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var login;
var n = 0;
var all = true;
var $informe = "CO"
var $ck_sucursal= true;
var $ck_cliente= false;
var $ck_expedicion= true;
var $factura = true;
var	$remision = false;

$(document).ready(function(){
    login = JSON.parse(sjcl.decrypt(encrypt, localStorage.usuario));
    

    /*swal({
  	  title: "Are you sure?",
  	  text: "Your will not be able to recover this imaginary file!",
  	  type: "warning",
  	  showCancelButton: true,
  	  confirmButtonClass: "btn-danger",
  	  confirmButtonText: "Yes, delete it!",
  	  closeOnConfirm: false
  	},
  	function(){
  	  swal("Deleted!", "Your imaginary file has been deleted.", "success");
  	});*/

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

    $('#fecha').daterangepicker({
    	format: 'DD/MM/YYYY',
        startDate: moment().subtract(29, 'days'),
        endDate: moment(),
        dateLimit: { days: 60 },
        showDropdowns: true,
        opens: 'right',
        drops: 'down',
        buttonClasses: ['btn', 'btn-sm'],
        applyClass: 'btn-primary',
        cancelClass: 'btn-default',
        separator: ' - ',
        locale: {
            applyLabel: 'Aceptar',
            cancelLabel: 'Cancelar',
            fromLabel: 'Desde',
            toLabel: 'Hasta',
            customRangeLabel: 'Custom',
            daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr','Sa'],
            monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
            firstDay: 1
        }
    	
    });

    $("#expedicion").mask("999");


   
    $('input[name=condicion]:radio').on('ifChanged', function () {
        if ($(this).val() == 'CO') {
        	$informe = "CO"
        }else if ($(this).val() == 'AD') {
        	$informe = "AD"
        }
    });
    
    $('input[name=tipo]:radio').on('ifChanged', function () {
        if ($(this).val() == 'FA') {
        	$factura = true;
        	$remision = false;
        }else if ($(this).val() == 'RE') {
        	$factura = false;
        	$remision = true;
        }
    });
    
    //cambia el valor del check matriz
    var $matriz = $('#ck_sucursal');
    $matriz.on('ifUnchecked', function(event){
    	habilitar($('#selectSucursal'));
    	$('#selectSucursal').trigger('chosen:updated');
    	$ck_sucursal = false;
    	
    });
	$matriz.on('ifChecked', function(event){
		$ck_sucursal = true;
		deshabilitar($('#selectSucursal'));
    	$('#selectSucursal').trigger('chosen:updated');
	});
	
	 //cambia el valor del check cliente
    var $cliente = $('#ck_cliente');
    $cliente.on('ifUnchecked', function(event){
    	$ck_cliente = false;
		deshabilitar($('#selectTipoDoc'));
		$('#selectTipoDoc').trigger('chosen:updated');
    	deshabilitar($('#documento'));
    	
    });
    $cliente.on('ifChecked', function(event){
    	habilitar($('#selectTipoDoc'));
    	$('#selectTipoDoc').trigger('chosen:updated');
    	habilitar($('#documento'));
    	$ck_cliente= true;
    	
		
	});
    
    //cambia el valor del check cliente
    var $expedicion = $('#ck_expedicion');
    $expedicion.on('ifUnchecked', function(event){
    	$ck_expedicion = false;
    	habilitar($('#expedicion'));
    	
    });
    $expedicion.on('ifChecked', function(event){
    	
    	deshabilitar($('#expedicion'));
    	$ck_expedicion= true;
    	
		
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


////////////////


function grabar(tipo){
	

    var error = 0;
    error = error + inputRequired($("#fecha"))
    
    if($ck_cliente){
    	error = error + inputMayor0($("#idcliente")) +  inputRequired($("#cliente"))  +  inputRequired($("#documento"))
    }
    if($ck_expedicion === false){
    	error = error + inputMayor0($("#expedicion"))
    }
    
    if($ck_sucursal === false){
    	error = error + selectRequired($("#selectSucursal"))
    }
    
    var fechadesde = $('#fecha').data('daterangepicker').startDate.format('DD/MM/YYYY');
    var fechahasta = $('#fecha').data('daterangepicker').endDate.format("DD/MM/YYYY");

    if(error>0){
        return;
    }

    	
    var datos = {};
    var cliente = {};
    cliente.idcliente = $("#idcliente").val();
    datos.cliente = cliente;
    datos.iscliente = $ck_cliente;
    datos.puntoexpedicion = $("#expedicion").val();
    datos.fechadesde = fechadesde;
    datos.fechahasta = fechahasta;
    datos.suc_todas = $ck_sucursal;
    datos.tipo = tipo
    if($("#selectSucursal").val() == null){
    	datos.idsucursal = 0;
    }else{
    	datos.idsucursal = $("#selectSucursal").val();
    }
    datos.factura = $factura;
    datos.remision = $remision;

    console.log(JSON.stringify(datos))

    $.ajax({
        type: "POST",
        url: context + "/api/v1/venta/informe/libro-venta/",
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
            	
            	if(jsonobject.datos == ""){
            		swal("Atencion", "No existen datos", "info");
            	}else{
            		
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