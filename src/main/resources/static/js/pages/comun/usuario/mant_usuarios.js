var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var matriz = true;
var login;
var table;
$(document).ready(function(){
    //inicializar la tabla con el pluguin datatable.js
	table = $('#tabla').DataTable({
   	 "language": { "url": context + "/js/plugins/dataTables/Spanish.json"},
   	 pageLength: 10,
     responsive: true,
	});
	 
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

    //cambia el valor del check matriz
    var $matriz = $('#matriz');
    $matriz.on('ifUnchecked', function(event){
        matriz = false;
    });
	$matriz.on('ifChecked', function(event){
        matriz = true;
	});

	$('#selectSucursal').chosen().change(function () {
            getDepositos($(this).val());
    });

	// obtener el usuario logueado
    login = JSON.parse(sjcl.decrypt(encrypt, localStorage.usuario));
    console.log(login);

    getAll();

    //seleccionar un registro de la tabla
    $('#tabla tbody').on( 'click', 'tr', function () {
        if($(this).hasClass('selected') ) {
    	    $(this).removeClass('selected');
    	    clear();
    	} else {
            table.$('tr.selected').removeClass('selected');
    	    $(this).addClass('selected');
    	    nuevo();
    	    insertForm(this);
    	 }
    });
});

// onclick del boton nuevo
function nuevo(){
    var $selectPerfil= $("#selectPerfil");
    var $selectSucursal= $("#selectSucursal");
    var $selectDeposito = $("#selectDeposito");
    var $selectClase= $("#selectClase");
    $("#matriz").parent().removeClass('disabled');
	habilitar($(".campoHabilitable"));
	habilitar($("#grabar"));
    deshabilitar($("#nuevo"));
    
    habilitar($selectPerfil);
    $selectPerfil.trigger('chosen:updated');
    habilitar($selectSucursal);
    $selectSucursal.trigger('chosen:updated');
    habilitar($selectDeposito);
    $selectDeposito.trigger('chosen:updated');
    habilitar($selectClase);
    $selectClase.trigger('chosen:updated');
}

function grabar(){
    var type;
    var error = 0;
    var usuario = {};
    var sucursal = {};
    var deposito = {};
    var claseempleado = {};
    var empresa = {};
    var caja = {};
    caja.idcaja = 1;

    var $idusuario = $("#idusuario");
	var $nombre = $("#nombre");
	var $usuario = $("#usuario");
	var $cargo = $("#cargo");
	var $clase= $("#selectClase");
	var $sucursal = $("#selectSucursal");
	var $deposito = $("#selectDeposito");
	var $perfil= $("#selectPerfil");
	
    error = selectRequired($cargo) + inputRequired($nombre) + inputRequired($nombre)
    error = error + selectRequired($sucursal)+ selectRequired($clase)+ selectRequired($perfil)

    if(error == 0){
    	
    	empresa.idempresa = login.empresa.idempresa;
    	usuario.empresa= empresa;
    	usuario.usuario= $usuario.val();
    	usuario.nombreapellido = $nombre.val();
    	usuario.cargo = $cargo.val();
    	sucursal.idsucursal = $sucursal.val();
    	usuario.sucursal= sucursal;
    	deposito.iddeposito = $deposito.val();
    	usuario.deposito = deposito;
    	usuario.estado = matriz;
    	claseempleado.idclaseempleado = $clase.val();
    	usuario.claseempleado = claseempleado;
    	usuario.perfilusuario = obtenerPerfiles($perfil.val());
    	usuario.caja = caja;

        //insert
        if($idusuario.val() == 0){
            type = "POST";
        }else{
            //update
        	usuario.idusuario = $idusuario.val();
            type = "PUT";
        }
    	console.log(JSON.stringify(usuario))
    	console.log($idusuario.val())
        $.ajax({
            type: type,
            url: context + "/api/v1/usuarios/",
            data: JSON.stringify(usuario),
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
                        swal("Proceso correcto", "Usuario " + jsonobject.datos.usuario + " guardado.", "success")
                		clear();
                		reloadTable();
                    }
                },
            error: function(e) {
                console.log("ERROR", e);
            },
            done: function(e) {
                console.log("DONE");
            }
        });
    }else{
    	  alertify.warning('Complete los campos');
    }
}

//limpiar el form
function clear(){
	deshabilitar($(".campoHabilitable"));
	deshabilitar($("#grabar"));
	habilitar($("#nuevo"));
	//restaurar valores predefinidos
	$("#selectSucursal").val("").trigger('chosen:updated');
	$("#selectDeposito").val("").trigger('chosen:updated');
	$('#matriz').iCheck('check');
	$("#idusuario").val("");
    $("#nombre").val("");
    $("#usuaro").val("");;
    $("#cargo").val("");
    $("#selectClase").val("").trigger('chosen:updated');
    $("#selectPerfil").val("").trigger('chosen:updated');
 
}

//obtener sucursales de la empresa
function getAll(){
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
					var estado = "";
					
					if(element.estado == true){
						estado = '<span class="label label-primary pull-center">Activo</span>'
					}else{
						estado ='<span class="label label-danger pull-center">Inactivo</span>'
					}
					var perfil = "";
						if(element.perfilusuario != null){							
							for (var i = 0; i < element.perfilusuario.length; i++) {
								if(i == (element.perfilusuario.length - 1)){							
									perfil = perfil + element.perfilusuario[i].perfil.idperfil
								}else{
									perfil = perfil + element.perfilusuario[i].perfil.idperfil + ","
								}
							}
						}
					
					table.rows.add([{
					    "0": element.idusuario,
						"1": element.usuario,
						"2": element.nombreapellido,
						"3": element.cargo,
						"4": element.sucursal.nombre,
						"5": estado,
						"6": element.claseempleado.idclaseempleado,
						"7": element.sucursal.idsucursal,
						"8": perfil,
						"9": element.deposito.iddeposito
					}]).draw();
				});
			}
			$(document).ajaxStop($.unblockUI);
		},
		error: function(e) {
		    console.log("ERROR", e);
			$(document).ajaxStop($.unblockUI);
		},
		done: function(e) {
			console.log("DONE");
		}
	});
}

//insertar la fila seleccionada en el form
function insertForm(fila) {
	$("#idusuario").val(fila.cells[0].innerHTML);
	$("#usuario").val(fila.cells[1].innerHTML);
	$("#nombre").val(fila.cells[2].innerHTML);
	$("#cargo").val(fila.cells[3].innerHTML);
	
	if(fila.cells[5].innerText == "Activo"){
    	$('#matriz').iCheck('check');
    } else {
    	$('#matriz').iCheck('uncheck');
    }
	$('#selectClase').val(fila.cells[6].innerHTML).trigger('chosen:updated');
	$('#selectSucursal').val(fila.cells[7].innerHTML).trigger('chosen:updated');
	getDepositos($('#selectSucursal').val());

    var iddeposito = fila.cells[9].innerHTML;
	setTimeout(function(){ $("#selectDeposito").val(iddeposito).trigger("chosen:updated"); }, 300);
	
	var idperfil = fila.cells[8].innerHTML.split(",") 
	$('#selectPerfil').val(idperfil).trigger('chosen:updated');
	
}

//Vuelve a poblar la tabla
function reloadTable(){
	table.clear().draw();
	getAll();
}


function obtenerPerfiles(idperfil){
	var perfiles = "";
	for (var i = 0; i < idperfil.length; i++) {
		
		
		if(i == (idperfil.length - 1)){
			perfiles+= '{"perfil":' + pojoPerfil(idperfil[i] , "")+ ","
			perfiles+= '"usuario":{"idusuario": '+ login.idusuario+'}}' 
		}else{
			perfiles+= '{"perfil":' + pojoPerfil(idperfil[i] , "")+ ","
			perfiles+= '"usuario":{"idusuario": '+ login.idusuario+'}}' + ","
	
		}
	}
	return JSON.parse('['+perfiles + ']')
}

function pojoPerfil(idperfil, descripcion){
	
	var pojo = "{";
	pojo += '"idperfil":' + idperfil
	pojo += ',"descripcion" :"'+ descripcion +'"'
		
	pojo += "}";
	
	return pojo;
}

function getDepositos(id){
	blockScreen();
	$('#selectDeposito').empty().trigger("chosen:updated");
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
					$("#selectDeposito").append(o);
					$.each(jsonobject.datos, function(index, element) {
						var o = $("<option/>", {
							value : element.iddeposito,
							text : element.descripcion
						});
						$("#selectDeposito").append(o);

					});
					habilitar($("#selectDeposito"));
				}else{
					deshabilitar($("#selectDeposito"));
				}
			}
			$("#selectDeposito").chosen().trigger("chosen:updated");
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