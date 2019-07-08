var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var login;
$(document).ready(function(){
	login = JSON.parse(sjcl.decrypt(encrypt, localStorage.usuario));
	$(".select2").select2({ placeholder: "Seleccione un usuario"});
	
	//if($("#idperfil").val() == 1){		
		getUsuarios()
	//}else{
		
//		getUsuariosByEmpresa(login.empresa.idempresa)
	//}
    
 
});

function resetpass(){
	if($("#pwd").val() != '' && $("#selectUsuario").val() != null ){
		$("#pwd").css({'border':'1px solid #d2d6de'});

		$('#ibox1').children('.ibox-content').toggleClass('sk-loading');
		$.ajax({
			type : "POST",
			url : context + "/api/v1/usuarios/reset/" + $("#selectUsuario").val()+"/"+$("#pwd").val(),	
		
			success : function(data) {
				$('#ibox1').children('.ibox-content').toggleClass('sk-loading');
				console.log(data)
		
				if (data.estado == "ERROR") {
					 swal("ERROR", jsonobject.error, "error");
				} else if (data.estado == 'OK') {	
					$("#selectUsuario").val("").trigger('change');
		
					swal("Proceso correcto", "Password modificado correctamente", "success")
					$("#pwd").val("")
					
				}
			},
			error : function(e) {
				console.log("ERROR: ", e);			
			},
			done : function(e) {
				console.log("DONE");
			}
		});
	}else{
		$("#pwd").css({'border':'1px solid red'});
	}
}


function getUsuarios(){
	blockScreen();
	$.ajax({
		type : "GET",
		url : context + "/api/v1/usuarios/empresa/" + login.empresa.idempresa,		
		success : function(data) {
			console.log(data)
			data = JSON.stringify(data);
			var jsonobject = JSON.parse(data);
			if (jsonobject.estado == 'ERROR') {
				 swal("ERROR", jsonobject.error, "error");
			} else if (jsonobject.estado == 'OK') {	
	
				$.each(jsonobject.datos, function(index, element) {
					
					var o = $("<option/>", {
						value : element.idusuario,
						text : element.usuario
					});
					$("#selectUsuario").append(o);
	
					$("#selectUsuario").select2().trigger('change');
						
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

