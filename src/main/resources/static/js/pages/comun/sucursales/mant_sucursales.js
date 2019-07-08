var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var matriz = false;
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
    var $selectCiudad = $("#selectCiudad");
    $("#matriz").parent().removeClass('disabled');
	habilitar($(".campoHabilitable"));
	habilitar($("#grabar"));
    deshabilitar($("#nuevo"));
    habilitar($selectCiudad);
    $selectCiudad.trigger('chosen:updated');
}

function grabar(){
    var type;
    var error = 0;
    var sucursal = {};
    var empresa = {};

    var $idsucursal = $("#idsucursal");
	var $nombre = $("#nombre");
	var $codigo = $("#codigo");
	var $telefono1 = $("#telefono1");
	var $telefono2 = $("#telefono2");
	var $descripcion = $("#descripcion");
	var $direccion = $("#direccion");
	var $ciudad = $("#selectCiudad");
	var $pais = $("#pais");

    error = selectRequired($ciudad) + inputRequired($direccion) + inputRequired($codigo)
    + inputRequired($codigo)

    if(error == 0){
        sucursal.matriz = matriz;
        empresa.idempresa = login.empresa.idempresa;
        sucursal.empresa= empresa;
        sucursal.nombre = $nombre.val();
        sucursal.codigo = $codigo.val();
        sucursal.telefono1 = $telefono1.val();
        sucursal.telefono2 = $telefono2.val();
        sucursal.descripcion = $descripcion.val();
        sucursal.direccion = $direccion.val();
        sucursal.ciudad = $ciudad.find(":selected").text();
        sucursal.pais = $pais.val();

        //insert
        if($idsucursal.val() == 0){
            type = "POST";
        }else{
            //update
            sucursal.idsucursal = $idsucursal.val()
            type = "PUT";
        }

        $.ajax({
            type: type,
            url: context + "/api/v1/sucursales/",
            data: JSON.stringify(sucursal),
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
                        swal("Proceso correcto", "Sucursal " + jsonobject.datos.idsucursal + " guardada.", "success")
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
    }
}

//limpiar el form
function clear(){
	deshabilitar($(".campoHabilitable"));
	deshabilitar($("#grabar"));
	habilitar($("#nuevo"));
	//restaurar valores predefinidos
	$("#selectCiudad").val("").trigger('chosen:updated');
	$('#matriz').iCheck('check');
	$("#idsucursal").val("");
    $("#nombre").val("");
    $("#codigo").val("");;
    $("#telefono1").val("");
    $("#telefono2").val("");
    $("#descripcion").val("");
    $("#direccion").val("");
    $("#pais").val("");
}

//obtener sucursales de la empresa
function getAll(){
	blockScreen();
	$.ajax({
		type : "GET",
		url : context + "/api/v1/sucursales/",//empresa/" + login.empresa.idempresa,
		success : function(data) {
			console.log(data)
			data = JSON.stringify(data);
			var jsonobject = JSON.parse(data);
			if (jsonobject.estado == 'ERROR') {
				 swal("ERROR", jsonobject.error, "error");
			} else if (jsonobject.estado == 'OK') {
				$.each(jsonobject.datos, function(index, element) {
					table.rows.add([{
					    "0": element.codigo,
						"1": element.direccion,
						"2": element.ciudad,
						"3": element.idsucursal,
						"4": element.nombre,
						"5": element.telefono1,
						"6": element.matriz,
						"7": element.pais,
						"8": element.empresa.idempresa,
						"9": element.telefono2,
						"10": element.descripcion
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
	$("#codigo").val(fila.cells[0].innerHTML);
	$("#direccion").val(fila.cells[1].innerHTML);
	$('#selectCiudad').val(fila.cells[2].innerHTML).trigger('chosen:updated');
	$("#idsucursal").val(fila.cells[3].innerHTML);
	$("#nombre").val(fila.cells[4].innerHTML);
	$("#telefono1").val(fila.cells[5].innerHTML);
	if(fila.cells[6].innerText == "true"){
    	$('#matriz').iCheck('check');
    } else {
    	$('#matriz').iCheck('uncheck');
    }
	$("#pais").val(fila.cells[7].innerHTML);
	$("#telefono2").val(fila.cells[9].innerHTML);
	$("#descripcion").val(fila.cells[10].innerHTML);
}

//Vuelve a poblar la tabla
function reloadTable(){
	table.clear().draw();
	getAll();
}