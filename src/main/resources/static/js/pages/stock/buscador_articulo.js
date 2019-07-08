var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
$(document).ready(function(){
	
});

//onclick del boton limpiar
$("#bus_limpiar").click(function(){
	limpiarBuscador();
 });

//onclick del boton cerrar
$("#bus_cerrar").click(function(){
	limpiarBuscador();
 });

 function limpiarBuscador(){
 	deshabilitar($("#bus_aceptar"));
 	$("#bus_descripcion").val("");
 	//$("#bus_codigobarra").val("");
 	removeRegistro();

 }

 //remueve los registros de la tabla
 function removeRegistro(){
 	var trs = $("#tablaArticulos tr").length;
 	for (var i = 1; trs > i; i++) {
 		// Eliminamos la ultima columna
 		$("#tablaArticulos  tr:last").remove();
 	}
 }

$("#bus_buscar").click(function(){
    var error = 0;
  //  error = error + inputRequired($("#bus_codigobarra"))
    error = error + inputRequired($("#bus_descripcion"))
    if(error  == 0){
       $("#bus_descripcion").css({'border':'1px solid #d2d6de'});
    //   $("#bus_codigobarra").css({'border':'1px solid #d2d6de'});
       removeRegistro();
       getArticuloByParam($("#bus_descripcion").val());
    }
});

//obtener articulos por parametros
function getArticuloByParam(descripcion){

    blockScreen();
    $.ajax({
        type : "GET",
        url : context + "/api/v1/articulos/byParam/"+descripcion+"/"+ all,
        success : function(data) {
            console.log(data)
            data = JSON.stringify(data);
            var jsonobject = JSON.parse(data);
            if (jsonobject.estado == 'ERROR') {
                alertify.errorAlert(jsonobject.error);
            } else if (jsonobject.estado == 'OK') {
                var table = document.getElementById("tablaArticulos");
            	if(jsonobject.datos.length == 0){
                    swal("","No se encontraron coincidencias", "warning")
                }else{
                    var l = 0;
                    $.each(jsonobject.datos, function(index, element) {
                        l++;
                        habilitar($("#bus_aceptar"));
            			var row = table.insertRow(1);
                        var cell0 = row.insertCell(0);
            			var cell1 = row.insertCell(1);
            			var cell2 = row.insertCell(2);
            			var cell3 = row.insertCell(3);
            			var cell4 = row.insertCell(4);
            			var element0 = document.createElement("input");
            			element0.type = "checkbox";
                        element0.onclick = function() {
                            compCHK(this, index, l, table);
            			}
                        if(jsonobject.datos.length == 1){
            				element0.checked = true;
                        }
                        var estado = "";
    					if(element.estado == true){
    						estado = '<span class="label label-primary pull-center">Activo</span>'
    					}else{
    						estado ='<span class="label label-danger pull-center">Inactivo</span>'
    					}
                        cell0.appendChild(element0);
                        cell1.innerHTML = element.codigobarra;
                        cell2.innerHTML = element.descripcion;
                        cell3.innerHTML = estado;
                        cell4.innerHTML = JSON.stringify(element);
            		});
                    //desmarca los demas registros
            		function compCHK(c, index, l, t){
        			    if (c.checked==true){
        				    for (var i = l-1; i >= 0; i--) {
                                console.log( i + "!="+index)
        						if(i != index){
        							var chks = t.rows[l-i].cells[0].childNodes[0];
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


function getArticuloByParam2(descripcion, all){

    blockScreen();
    $.ajax({
        type : "GET",
        url : context + "/api/v1/articulos/byParam/"+descripcion+"/"+ all,
        success : function(data) {
            console.log(data)
            data = JSON.stringify(data);
            var jsonobject = JSON.parse(data);
            if (jsonobject.estado == 'ERROR') {
                alertify.errorAlert(jsonobject.error);
            } else if (jsonobject.estado == 'OK') {
                var table = document.getElementById("tablaArticulos");
            	if(jsonobject.datos.length == 0){
                   swal("","No se encontraron coincidencias", "warning")
                   $('#buscador_articulo').modal();
                }else{
                	if(jsonobject.datos.length > 1){
                		$('#buscador_articulo').modal();
                		getArticuloByParam(descripcion, all)
                	}else{                		
                		$.each(jsonobject.datos, function(index, element) {
                			setArticulo(element)
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
}

//onclick del boton aceptar del buscador
$("#bus_aceptar").click(function(){
    insertRegistroFromBuscador();
});
