var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var login;
var n = 0;
var all = true;
var $informe = "CO"
var $ck_familia= true;
var $ck_cliente= false;
var $ck_expedicion= true;
var $factura = true;
var	$remision = false;

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

   /* $('#fecha').daterangepicker({
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
    	
    });*/


    $('#selectSucursal1').chosen().change(function () {
        getDepositos($(this).val());
    
    });
    
  
    
    //cambia el valor del check matriz
    var $matriz = $('#ck_familia');
    $matriz.on('ifUnchecked', function(event){
    	habilitar($('#selectFamilia'));
    	$('#selectFamilia').trigger('chosen:updated');
    	$ck_familia = false;
    	
    });
	$matriz.on('ifChecked', function(event){
		$ck_familia = true;
		deshabilitar($('#selectFamilia'));
    	$('#selectFamilia').trigger('chosen:updated');
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

// depositos
//obtener depositos por sucursal
function getDepositos(id){
	blockScreen();
	var $selectDeposito = $('#selectDeposito1');
	$selectDeposito.empty().trigger("chosen:updated");
	$.ajax({
		type : "GET",
		url : context + "/api/v1/depositos/sucursal/" + id,		
		success : function(data) {
			console.log(data)
			data = JSON.stringify(data);
            var jsonobject = JSON.parse(data);
            if (jsonobject.estado == 'ERROR') {
                swal("ERROR", jsonobject.error, "error");
            } else if (jsonobject.estado == 'OK') {
                if(jsonobject.datos.length > 0){
                    var o = $("<option/>", {
                        value :"0",
                        text : "Seleccione"
                    });
                    $selectDeposito.append(o);
                    $.each(jsonobject.datos, function(index, element) {
                        var o = $("<option/>", {
                            value : element.iddeposito,
                            text : element.descripcion
                        });
                        $selectDeposito.append(o);

                    });
                    habilitar($selectDeposito);
                }else{
                	deshabilitar($selectDeposito);
                }
            }
            $selectDeposito.chosen().trigger("chosen:updated");
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


/*
 * Articulos
 * 
 */
function buscarArticulo(){
	//clear()
	if($("#documento").val() == ""){		
		$('#buscador_articulo').modal();
	}else{
		// ver en buscador articulo venta y copiar el api
		getArticuloByParam2( $("#articulo").val(), all)
		
	}
}

//onclick del input documento
$('#documento').keydown(function(event){ 
    var keyCode = (event.keyCode ? event.keyCode : event.which);   
    if (keyCode == 13) {
        //buscar cliente por numero de cedula
    	getArticuloByParam2( $("#articulo").val(), all)

    }
});

function setArticulo(articulo){
	console.log(articulo)
	if(articulo != null){	
		//setear los valores
		$("#idcliente").val(articulo.idarticulo);
		$("#documento").val("");
		$("#cliente").val(articulo.descripcion);
	}
	
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

/////////////////////////////////


function grabar(tipo){
	

    var error = 0;
  //  error = error + inputRequired($("#fecha"))
    error = error + selectRequired($("#selectSucursal1"))
    error = error + selectRequired($("#selectDeposito1"))
    
    if($ck_cliente){
    	error = error + inputMayor0($("#idcliente")) +  inputRequired($("#cliente"))  
    }
   
    
    if($ck_familia === false){
    	error = error + selectRequired($("#selectFamilia"))
    }
    
    //var fechadesde = $('#fecha').data('daterangepicker').startDate.format('DD/MM/YYYY');
    //var fechahasta = $('#fecha').data('daterangepicker').endDate.format("DD/MM/YYYY");

    if(error>0){
        return;
    }

    	
    var datos = {};
    datos.idarticulo = $("#idcliente").val();
    datos.articulo_todas = $ck_cliente;

    datos.familia_todas = $ck_familia;
    if($("#selectFamilia").val() == null){
    	datos.idfamilia = 0;
    }else{
    	datos.idfamilia = $("#selectFamilia").val();
    }
    datos.idsucursal = $("#selectSucursal1").val();
    datos.iddeposito = $("#selectDeposito1").val();

    console.log(JSON.stringify(datos))

    $.ajax({
        type: "POST",
        url: context + "/api/v1/articulosdeposito/informe/lista-articulo/",
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