var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var login;
var table;
var $sexo = "M"
var estado= true
$(document).ready(function(){
	//inicializar los check y radiobutton con el plugins Ichecks.js
	$('.i-checks').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
    });
	//inicializar el select con el pluguins choosen.js
	$('.chosen-select').chosen({
    	width: "100%",
    	inherit_select_classes: true
    });
	// obtener el usuario logueado
	login = JSON.parse(sjcl.decrypt(encrypt, localStorage.usuario));
	console.log(login)
	
	 //cambia el valor del check estado
	 $('#estado').on('ifUnchecked', function(event){
	  	  estado = false;
	  	});
	  $('#estado').on('ifChecked', function(event){
	    	  estado = true;
	   });
	  
	  $('input:radio[name=sexo]').on('ifChecked', function(event){
		  $sexo = $(this).val(); 
		});  
	  
	  
});



// onclick del boton nuevo
function nuevo(){

	habilitar($("#selectTipoDoc"))
	habilitar($("#nombres"))
	habilitar($("#documento"))
	habilitar($("#selectEstadoCivil"))
	habilitar($("#selectSucursal"))
	$("#selectSucursal").trigger('chosen:updated');
	habilitar($("#fechanac"))
	habilitar($("#lugarnac"))
	habilitar($("#direccion"))
	habilitar($("#telefono"))
	habilitar($("#telefono1"))
	habilitar($("#telefono2"))
	habilitar($("#selectDpto"))
	$("#selectDpto").trigger('chosen:updated');
	habilitar($("#selectCiudad"))
	$("#selectCiudad").trigger('chosen:updated');
	habilitar($("#selectBarrio"))
	$("#selectBarrio").trigger('chosen:updated');
	habilitar($("#selectListaPrecio"))
	$("#selectListaPrecio").trigger('chosen:updated');
	habilitar($("#correo"))
	habilitar($("#web"))
	//botones
	habilitar($("#grabar"))
	deshabilitar($("#nuevo"))
}

//onclick boton buscar
function buscar(){
	clear()
	$('#buscador_cliente').modal()
}

//limpiar el form
function clear(){
	deshabilitar($("#selectTipoDoc"))
	deshabilitar($("#nombres"))
	deshabilitar($("#documento"))
	deshabilitar($("#selectEstadoCivil"))
	deshabilitar($("#selectSucursal"))
	deshabilitar($("#fechanac"))
	deshabilitar($("#lugarnac"))
	deshabilitar($("#direccion"))
	deshabilitar($("#telefono"))
	deshabilitar($("#telefono1"))
	deshabilitar($("#telefono2"))
	deshabilitar($("#selectDpto"))
	deshabilitar($("#selectCiudad"))
	deshabilitar($("#selectBarrio"))
	deshabilitar($("#correo"))
	deshabilitar($("#web"))
	
	
	deshabilitar($("#grabar"))
	habilitar($("#nuevo"))
	//restaurar valores predefinidos
	$("#selectSucursal").val("").trigger('chosen:updated');
	$("#selectListaPrecio").val("").trigger('chosen:updated');
	$("#selectDpto").val("").trigger('chosen:updated');
	$("#selectCiudad").val("").trigger('chosen:updated');
	$("#selectBarrio").val("").trigger('chosen:updated');
	$("#id").val("0");
	$('#estado').iCheck('check'); 
	$('input[name=sexo][value=M]').iCheck('check'); 
	$("#selectTipoDoc").val("CI")
	$("#selectEstadoCivil").val("SO")
	
	$("#nombres").val("");
	$("#documento").val("");
	$("#fechanac").val("");
	$("#lugarnac").val("");
	$("#direccion").val("");
	$("#telefono").val("");
	$("#telefono1").val("");
	$("#telefono2").val("");
	$("#correo").val("");
	$("#web").val("");
}



// grabar un nuevo registro
function grabar(){
	var type;
    var error = 0;
    var cliente = {};
    var empresa = {};
    var sucursal = {};
    var departamento = {};
    var barrio = {};
    var distrito = {};
    var tipodocumento = {};

    var $idcliente = $("#id");
	var $tipodoc = $("#selectTipoDoc");
	var $nrodoc = $("#documento");
	var $nombreapellido= $("#nombres");
	var $estadocivil = $("#selectEstadoCivil");
	var $idsucursal = $("#selectSucursal");
	var $fechanac= $("#fechanac");
	var $lugarnac = $("#lugarnac");
	var $direccion = $("#direccion");
	
	var $telefono = $("#telefono");
	var $telefono1= $("#telefono1");
	var $telefono2= $("#telefono2");
	var $dpto= $("#selectDpto");
	
	var $idciudad= $("#selectCiudad");
	var $idlistaprecio= $("#selectListaPrecio");
	var $idbarrio= $("#selectBarrio");
	var $correo= $("#correo");
	var $web= $("#web");
	
	error =  inputRequired($nombreapellido)+inputRequired($nombreapellido)+inputRequired($nrodoc)+inputRequired($fechanac)+
			inputRequired($direccion)+inputRequired($telefono)
	error = error + selectRequired($idsucursal) + selectRequired($idciudad) + selectRequired($dpto) + selectRequired($idlistaprecio)
	console.log(error)
	if(error == 0){		

		empresa.idempresa = login.empresa.idempresa;
		cliente.empresa= empresa;
		cliente.estado = estado;
        cliente.nombreapellido = $nombreapellido.val();
        tipodocumento.codtipodoc = $tipodoc.find(":selected").text();
        cliente.tipodocumento = tipodocumento
        cliente.nrodoc = $nrodoc.val();
        cliente.sexo  = $sexo
        cliente.estadocivil = $estadocivil.val();
        cliente.fecnacimiento = moment(formatServerFecha($fechanac.val(), "YYYY-MM-DD"));
        cliente.lugarnacimiento = $lugarnac.val();
        cliente.direccion = $direccion.val();
        cliente.telefono = $telefono.val();
        cliente.telefono1 = $telefono1.val();
        cliente.telefono2 = $telefono2.val();
        departamento.iddepartamento = $dpto.val();
        cliente.departamento = departamento
        distrito.iddistrito = $idciudad.val();
        cliente.distrito = distrito;
        barrio.idbarrio = $idbarrio.val();
        cliente.barrio = barrio;
        cliente.correo = $correo.val();
        cliente.web = $web.val();
        sucursal.idsucursal = $idsucursal.val();
        cliente.sucursal = sucursal;
        var articuloprecioventacab = {}
        articuloprecioventacab.idarticuloprecioventacab = $idlistaprecio.val();
        cliente.articuloprecioventacab = articuloprecioventacab
		
		//insert
		if($("#id").val() == 0){
			type = "POST";
		}else{
		//update
			cliente.idcliente = $idcliente.val()
			type = "PUT";
		}
		console.log(JSON.stringify(cliente))

		$.ajax({
			type : type,
			url : context + "/api/v1/clientes/",
			data :JSON.stringify(cliente),
			contentType: "application/json; charset=utf-8",
	        dataType   : "json",
			success : function(data) {
				console.log(data)
				data = JSON.stringify(data);
				var jsonobject = JSON.parse(data);
				
				if (jsonobject.estado == "ERROR") {
					$('#modal').modal("toggle")
					 swal("ERROR", jsonobject.error, "error");
				} else if (jsonobject.estado == 'OK') {	
					swal("Proceso correcto", "Cliente " + jsonobject.datos.idcliente + " guardado.", "success")
					clear()
					
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
}
// inserta el registro desde el buscador
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
			console.log(cliente)
	
			if(cliente.sucursal != null){				
				$("#selectSucursal").val(siNuloVacio(cliente.sucursal.idsucursal)).trigger('chosen:updated');
			}
			if(cliente.departamento != null){				
				$("#selectDpto").val(cliente.departamento.iddepartamento).trigger('chosen:updated');
			}
			if(cliente.distrito != null){				
				$("#selectCiudad").val(cliente.distrito.iddistrito).trigger('chosen:updated');
			}
			if(cliente.barrio != null){				
				$("#selectBarrio").val(cliente.barrio.idbarrio).trigger('chosen:updated');
			}
			if(cliente.articuloprecioventacab != null){
                $("#selectListaPrecio").val(cliente.articuloprecioventacab.idarticuloprecioventacab).trigger('chosen:updated');
            }
			$("#id").val(cliente.idcliente);
			$("#selectTipoDoc").val(cliente.tipodocumento.codtipodoc)
			
			if(cliente.sexo == "M"){
				 $('input[name=sexo][value=M]').iCheck('check'); 
			}else{
				 $('input[name=sexo][value=F]').iCheck('check'); 
			}
			
			if(cliente.estado == true){				
				$('#estado').iCheck('check'); 
			}else{
				$('#estado').iCheck('uncheck'); 
			}
			$("#nombres").val(cliente.nombreapellido);
			$("#documento").val(cliente.nrodoc);
			$("#fechanac").val(formatFecha(cliente.fecnacimiento));
			$("#lugarnac").val(cliente.lugarnacimiento);
			$("#direccion").val(cliente.direccion);
			$("#telefono").val(cliente.telefono);
			$("#telefono1").val(cliente.telefono1);
			$("#telefono2").val(cliente.telefono2);
			$("#correo").val(cliente.correo);
			$("#web").val(cliente.web);
				
			nuevo();
		}
		
	}
	
	if (!ban) {
		 swal("","Debes seleccionar un registro", "warning")
	}else{
		limpiarBuscador()
	}
}
