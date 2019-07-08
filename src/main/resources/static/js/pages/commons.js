var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var encrypt= "e15f6113-dba3-4f0c-bd35-1e0952c97347"
$(document).ready(function() {
	//modal error
	erroralertify();
	alertify.defaults.transition = "slide";
	alertify.defaults.theme.ok = "btn btn-w-m btn-success";
	alertify.defaults.theme.cancel = "btn btn-w-m btn-danger";
	alertify.defaults.theme.input = "form-control";
});

function siNuloVacio(elemento){
	if(elemento== null){
		elemento = ""		
	}
		return elemento;	
}

function siNuloSinInfo(elemento){
	if(elemento== null){
		elemento = "Sin Informacion"		
	}
		return elemento;	
}
function siNuloLink(elemento){
	if(elemento== null){
		elemento = "#"		
	}
		return elemento;	
}

function siNuloCero(elemento){
	if(elemento== null){
		elemento = 0		
	}
		return elemento;	
}
//validar campo numerico
function validarNumero(numero){
    if (!/^([0-9])*$/.test(numero) || isNaN(numero)){
    	return false;
    }
    return true;
      
  }

function validarSelect(elemento){
	if( elemento == null || elemento == 0 ) {
		  return false;
		}
	 return true;
}

//validar campo no vacio
function validarVacio(valor){
	if( valor == null || valor.length == 0 || /^\s+$/.test(valor) ) {
		  return false;
		}
	return true;
}

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

function cancelar(){
	location.reload(true)
}

function habilitar(element){
	element.attr("disabled", false)	
}
function deshabilitar(element){
	element.attr("disabled", true)	
}
/*
 * Cambia del formato del servidor(yyyy-mm-dd) a (dd/mm/yyyy)
 * @param fecha(yyyy-mm-dd)
 * @returns (dd/mm/yyyy)
 */
function formatFecha(fecha){
	if(fecha != null){
		fecha = fecha.split("-");
		var f = fecha[2] +"/"+fecha[1] +"/"+fecha[0];
	}
	
	return f;
}

/*
 * Cambia del formato (dd/mm/yyyy) al formato del servidor (yyyy-mm-dd)
 * @param fecha(yyyy-mm-dd)
 * @returns (dd/mm/yyyy)
 */
function formatServerFecha(fecha){
	if(fecha != null){
		fecha = fecha.split("/");
		var f = fecha[2] +"-"+fecha[1] +"-"+fecha[0];
	}
	
	return f;
}

/**
 * Valida si se completo un inout requerido
 * @param element
 * @returns 0 si el campo esta completo, 1 si falta completar
 */
function inputRequired(element){
	var error = 0;
	if (!validarVacio($(element).val())) {			
		$(element).css({'border':'1px solid red'});
		error++;
	}else{
		  $(element).css({'border':'1px solid #d2d6de'});	 
	 }
	return error;
}

/**
 * Valida si se completo un inout requerido
 * @param element
 * @returns 0 si el campo esta completo, 1 si falta completar
 */
function inputMayor0(element){
	var error = 0;
	if (!validarVacio($(element).val())) {			
		$(element).css({'border':'1px solid red'});
		error++;
	}else{
		if(element.val() > 0){			
			$(element).css({'border':'1px solid #d2d6de'});	 
		}else{
			$(element).css({'border':'1px solid red'});
			error++;
		}
	 }
	return error;
}

/**
 * Valida si se completo un inout requerido
 * @param element
 * @returns 0 si el campo esta completo, 1 si falta completar
 */
function selectRequired(element){
	var error = 0;
	if (!validarSelect($(element).val())) {			
		$(element).addClass('error');
		alertify.warning('Seleccione una opcion');
		error++;
	}else{
		$(element).css({'border':'1px solid #d2d6de'});
		
	}
	return error;
}


function erroralertify(){
	if(!alertify.errorAlert){
		  //define a new errorAlert base on alert
		  alertify.dialog('errorAlert',function factory(){
		    return{
		            build:function(){
		                var errorHeader = '<span class="fa fa-times-circle fa-2x" '
		                +    'style="vertical-align:middle;color:#e10000;">'
		                + '</span> Error';
		                this.setHeader(errorHeader);
		            }
		        };
		    },true,'alert');
		}
}


function blockScreen(){
	 $.blockUI({ css: { 
	        border: 'none', 
	        padding: '15px', 
	        backgroundColor: '#000', 
	        '-webkit-border-radius': '10px', 
	        '-moz-border-radius': '10px', 
	        opacity: .5, 
	        color: '#fff' 
	    }, message: '<img src="'+context+'/img/loading.gif" /> ' }); 
}

function cambiarPerfil(id){
	$.ajax({
		type : "POST",
		url : context + "/private/perfil/change",
		data : {
			"idperfil_" : id,
		},
		success : function(data) {
			location.reload();
			location.href= context + "/home";
		
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});	
}

function modalPwd() {	
	$('#modalPwd').modal()
}


function cambiarPwd(){
	
	var pa = $("#pwd_pa").val()
	var p1 = $("#pwd_p1").val()
	var p2 = $("#pwd_p2").val()
	var error = 0;
	if($("#pwd_p1").val() != $("#pwd_p2").val()){
		error++;
		$("#pwd_p1").css({'border':'1px solid red'});
		$("#pwd_p2").css({'border':'1px solid red'});
	}
	
	if(error == 0){
		var obj = JSON.parse(sjcl.decrypt(encrypt, localStorage.usuario));
		console.log(pa + " " + p1 + " " + p2 + " error " + obj.idusuario)
		changepass(obj.idusuario, $("#pwd_p1").val())
	}else{
		alert("las contrasenas no coinciden")
	}
	
}

function cerrarPwd(){
	$("#pwd_pa").val("")
	$("#pwd_p1").val("")
	$("#pwd_p2").val("")
	$("#pwd_pa").css({'border':'1px solid #d2d6de'});
	$("#pwd_p1").css({'border':'1px solid #d2d6de'});
	$("#pwd_p2").css({'border':'1px solid #d2d6de'});
	
}

function clearStorage(){
	delete localStorage.usuario;
}

function cerrarSesion(){
	clearStorage()
}

function changepass(idusuario, password){
	$.ajax({
		type : "POST",
		url : context + "/api/v1/usuarios/change-pwd/" + idusuario+"/"+password,	
	
		success : function(data) {

			console.log(data)
	
			if (data.estado == "ERROR") {
				$('#modalPwd').modal("toggle")
			
				 swal("ERROR", data.error, "error");
				//alertify.error('Error message');
			} else if (data.estado == 'OK') {	
				$('#modalPwd').modal("toggle")
				swal("Proceso correcto", "Password modificado correctamente", "success")

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

function checkpass( password){
	$.ajax({
		type : "POST",
		url : context + "/api/v1/usuarios/check-pwd/" + $("#pwd_idusuario").val()+"/"+password,	
	
		success : function(data) {

			console.log(data)
	
			if (data.estado == "ERROR") {

				alertify.errorAlert(jsonobject.error);
				//alertify.error('Error message');
			} else if (data.estado == 'OK') {	

				if(data.datos == true){
					habilitar($("#pwd_aceptar"))
					$("#pwd_pa").css({'border':'1px solid #d2d6de'});
				}else{
					$("#pwd_pa").css({'border':'1px solid red'});
					deshabilitar($("#pwd_aceptar"))
				}

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

function aplicarSeparadorMiles(n){
    return n.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}