var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));

$(document).ready(function(){


	  
});



//onclick del boton buscar
$("#bus_cliente_buscar").click(function(){
    var error = 0;
    error = error + inputRequired($("#bus_cliente_documento"))
    error = error + inputRequired($("#bus_cliente_nombre"))
    if(error  < 2){
       $("#bus_cliente_documento").css({'border':'1px solid #d2d6de'});	
       $("#bus_cliente_nombre").css({'border':'1px solid #d2d6de'});	
       removeRegistro()
       getClienteByParam();
   }

 });
            	
//obtener clienres por parametros
function getClienteByParam(){
      var nrodoc = $("#bus_cliente_documento").val();
      if(nrodoc == "") nrodoc = "null";
      var nombre = $("#bus_cliente_nombre").val();
      if(nombre == "") nombre = "null";
            	blockScreen();
            	$.ajax({
            		type : "GET",
            		url : context + "/api/v1/clientes/byParam/"+$("#bus_cliente_tipoDoc").val()+"/"+nrodoc+"/"+nombre,	
            		success : function(data) {
            			console.log(data)
            			data = JSON.stringify(data);
            			var jsonobject = JSON.parse(data);
            			if (jsonobject.estado == 'ERROR') {
            				alertify.errorAlert(jsonobject.error);
            			} else if (jsonobject.estado == 'OK') {	
            				var table = document.getElementById("tablaClientes");
            				if(jsonobject.datos.length == 0){
            					 swal("","No se encontraron coincidencias", "warning")
            				}else{
            					var l = 0;
            					$.each(jsonobject.datos, function(index, element) {	
            						l++;
            						habilitar($("#bus_cliente_aceptar"))
            						var row = table.insertRow(-1);
            						var cell0 = row.insertCell(0);
            						var cell1 = row.insertCell(1);
            						var cell2 = row.insertCell(2);
            						var cell3 = row.insertCell(3);
            						var cell4 = row.insertCell(4);
            						var element0 = document.createElement("input");
            						element0.type = "checkbox";
            						//$(element0).addClass("i-checks")
            						element0.onclick = function() {
            							compCHK(this, index, l, table);

            						}
            						if(jsonobject.datos.length == 1){
            							element0.checked = true;
            						}
            						cell0.appendChild(element0);
            						cell1.innerHTML = element.idcliente;
            						cell2.innerHTML = element.nrodoc;
            						cell3.innerHTML = element.nombreapellido;
            						cell4.innerHTML = JSON.stringify(element);
            						
            					});		
            					//desmarca los demas registros
            					function compCHK(c, index, l, t){
        							if (c.checked==true){ 
        								for (var i = l-1; i >= 0; i--) {	
        									var chks = t.rows[i+1].cells[0].childNodes[0];
        									if(i != index){
        										chks.checked = false;
        									}
        								}
        							}
        						}
            					$('.i-checks').iCheck({
            						checkboxClass: 'icheckbox_square-green',
            						radioClass: 'iradio_square-green',
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


//obtener clienres por parametros
function getClienteByNrodoc(tipodoc, nrodoc){
		var cliente = {}
            	blockScreen();
            	$.ajax({
            		type : "GET",
            		url : context + "/api/v1/clientes/byParam/"+tipodoc+"/"+nrodoc+"/null",	
            		success : function(data) {
            			console.log(data)
            			data = JSON.stringify(data);
            			var jsonobject = JSON.parse(data);
            			if (jsonobject.estado == 'ERROR') {
            				alertify.errorAlert(jsonobject.error);
            			} else if (jsonobject.estado == 'OK') {	
            				var table = document.getElementById("tablaClientes");
            				if(jsonobject.datos.length == 0){
            					// swal("","No se encontraron coincidencias", "warning")
            					 cliente = null;
            					 $('#buscador_cliente').modal()
            				}else{
            					if(jsonobject.datos.length > 1){
            						cliente = null;
            						$('#buscador_cliente').modal()
            					}else{            						
            						$.each(jsonobject.datos, function(index, element) {
            							
            							setCliente(element)

            						});		
            					}
            					
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
            	
       return cliente;
 }

//onclick del boton aceptar del buscador
$("#bus_cliente_aceptar").click(function(){
	insertClienteFromBuscador();
	

 });

//onclick del boton buscar
$("#bus_cliente_limpiar").click(function(){
   
	limpiarBuscador()
 });

//onclick del boton cerrar
$("#bus_cliente_cerrar").click(function(){
   
	limpiarBuscador()
 });

function limpiarBuscador(){
	deshabilitar($("#bus_cliente_aceptar"))
	$("#bus_cliente_documento").val("");
	$("#bus_cliente_nombre").val("");
	$("#bus_cliente_tipoDoc").val("CI")
	
	removeRegistro()

}

//remueve los registros de la tabla
function removeRegistro(){
	var trs = $("#tablaClientes tr").length;
	for (var i = 1; trs > i; i++) {
		// Eliminamos la ultima columna
		$("#tablaClientes  tr:last").remove();
	}
}

