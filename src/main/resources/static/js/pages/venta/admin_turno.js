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
    
    $('.datetimepicker').datetimepicker({
    	
    	 format: 'dd/mm/yyyy hh:ii', 
    	 autoclose: true,
    	 language: 'es',
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
    
    getCurrentTurno()
    
});

/////////
//obtener el numero de factura por caja
function getCurrentTurno(){
	$.ajax({
		type : "GET",
		url : context + "/api/v1/turnos/usuario/",
		success : function(data) {
			console.log(data)
			data = JSON.stringify(data);
			var jsonobject = JSON.parse(data);
			if (jsonobject.estado == 'ERROR') {
				 swal("ERROR", jsonobject.error, "error");
			} else if (jsonobject.estado == 'OK') {
				if(jsonobject.datos.idturno == null ){
					habilitar($("#abrir"))
					 $("#fechaapertura").datetimepicker('update', new Date());
					habilitar($("#fechaapertura"))
				}else{			
					habilitar($("#cerrar"))
					habilitar($("#imprimir"))
					habilitar($("#fechacierre"))
					setTurno(jsonobject.datos)
					/*$.each(jsonobject.datos, function(index, element) {
						
						
						
					});*/
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

function setTurno(obj){
	$("#nroturno").val(obj.idturno)
	 $("#fechaapertura").datetimepicker('update', new Date(obj.fechaapertura));
	 $("#fechacierre").datetimepicker('update', new Date());
	
	$("#estado").html('<small class="label label-primary">TURNO HABILITADO</small>')
	
	
}

////////////////


function grabar(metodo){
	
	var error = 0;
	 var turno = {};
	 var type = context + "/api/v1/turnos/"
	var msg = "Turno habilitado Correctamente"
	if(metodo == 'abrir'){
		 error = error + inputRequired($("#fechaapertura"))
		 var fechaapertura = $('#fechaapertura').data("datetimepicker").getDate();
		 turno.fechaapertura = fechaapertura;
	}else{
		error = error + inputRequired($("#fechacierre"))
		 var fechacierre = $('#fechacierre').data("datetimepicker").getDate();
		 turno.fechacierre = fechacierre;
		 turno.idturno = $('#nroturno').val();
		 turno.estado = false;
		 type =  context + "/api/v1/turnos/cerrar/"
		 msg = "Turno Cerrado Correctamente"
	}

   
    if(error>0){
        return;
    }

  
    console.log(JSON.stringify(turno))
    blockScreen();
    $.ajax({
        type: "POST",
        url: type,
        data: JSON.stringify(turno),
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
            		if(metodo == 'abrir'){
            			swal({
                			title: "Proceso correcto",
                			text: msg,
                			type: "success",
                			// showCancelButton: true,
                			//  confirmButtonClass: "btn-danger",
                			confirmButtonText: "Aceptar",
                			closeOnConfirm: false
                		},
                		function(){
                			setTimeout(function(){
                				location.reload(true);
                			}, 500);
                		});
            		}else{
            			swal({
                			title: "Proceso correcto",
                			text: "",
                			type: "success",
                			 showCancelButton: true,
                			 cancelButtonClass: "btn-danger",
                			 cancelButtonText: "No",
                			confirmButtonText: "Descargar Reporte",
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
  

            }
            
            $(document).ajaxStop($.unblockUI);
        },
        error: function(e) {
            console.log("ERROR", e);
            $(document).ajaxStop($.unblockUI);
        },
        done: function(e) {
            console.log("DONE");
            $(document).ajaxStop($.unblockUI);
        }
    });
}


function imprimir(){
	
    var error = 0;
    if(error>0){
        return;
    }
    $.ajax({
        type: "POST",
        url: context + "/api/v1/turnos/movimientos/",
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