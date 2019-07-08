var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var login;
var table;
var estado= true
var update= false
$(document).ready(function(){
	// Inicializar la tabla con el pluguin datatable.js
	table = $('#tabla').DataTable({
   	 "language": { "url": context + "/js/plugins/dataTables/Spanish.json"},
   	 pageLength: 10,
     responsive: true,

	});
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

	//poblar la tabla
	 getAll()

	//seleccionar un registro de la tabla
	 $('#tabla tbody').on( 'click', 'tr', function () {
	     if($(this).hasClass('selected') ) {
	         $(this).removeClass('selected');
	         clear()
	      }else{
	         table.$('tr.selected').removeClass('selected');
	         $(this).addClass('selected');
	         nuevo()
	         insertForm(this);
	       }
	   });

	 //cambia el valor del check estado
	 $('#estado').on('ifUnchecked', function(event){
	  	  estado = false;
	  	});
	  $('#estado').on('ifChecked', function(event){
	    	  estado = true;
	   });
	  //cambia el valor del check update
		$('#update').on('ifUnchecked', function(event){
			 update = false;
			  deshabilitar($("#selectListaPrecios"))
			  $("#selectListaPrecios").trigger('chosen:updated');
		 });
		$('#update').on('ifChecked', function(event){
			  update = true;
			  habilitar($("#selectListaPrecios"))
			  $("#selectListaPrecios").trigger('chosen:updated');
		 });


});

//obtener depositos por empresa
function getAll(){
	blockScreen();
	$.ajax({
		type : "GET",
		url : context + "/api/v1/familias/",
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
						estado ='<span class="label label-danger pull-center">Activo</span>'
					}
					table.rows.add( [ {
							"0": element.idfamilia,
							"1": element.descripcion,
							"2": element.procentajeganancia,
							"3": estado,
						}
						] ).draw();

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

// onclick del boton nuevo
function nuevo(){
	habilitar($("#descripcion"))
	habilitar($("#porcentaje"))
	habilitar($("#grabar"))
	deshabilitar($("#nuevo"))
}

//limpiar el form
function clear(){
	deshabilitar($("#descripcion"))
	deshabilitar($("#grabar"))
	habilitar($("#nuevo"))
	//restaurar valores predefinidos
	$("#id").val("");
	$("#descripcion").val("");
	$('#estado').iCheck('check');
	$("#porcentaje").val("");
	$("#divUpdate").hide();
	$('#update').iCheck('uncheck');
	update = false;
	$("#selectListaPrecios").val("").trigger('chosen:updated');
}
//Vuelve a poblar la tabla
function reloadTable(){
	table.clear().draw();
	getAll();
}

//insertar la fila seleccionada en el form
function insertForm(fila) {
	$("#id").val(fila.cells[0].innerHTML);
	$("#descripcion").val(fila.cells[1].innerHTML);
	$("#porcentaje").val(fila.cells[2].innerHTML);
	if(fila.cells[3].innerText == "Activo"){
		$('#estado').iCheck('check');
	}else{
		$('#estado').iCheck('uncheck');
	}
	$("#divUpdate").show();
}

// grabar un nuevo registro
function grabar(){
	var deposito = {}
	var type;
	var error = 0;

	error = error + inputRequired($("#descripcion"))
	var array = $("#selectListaPrecios").val();
	deposito.listaPrecio = "[]"
	if(update){
		if(array.length === 0){
			alertify.warning('Seleccione una o varias lista de precio');
			error = error + 1
		}else{
			deposito.listaPrecio = JSON.stringify(array);
		}
	}
	if(error == 0){
		deposito.descripcion = $("#descripcion").val();
		deposito.estado = estado;
		deposito.procentajeganancia = $("#porcentaje").val();

		//insert
		if($("#id").val() == 0){
			type = "POST";
		}else{
		//update
			deposito.idfamilia = $("#id").val()
			type = "PUT";
		}
		console.log(JSON.stringify(deposito))
		$.ajax({
			type : type,
			url : context + "/api/v1/familias/",
			data :JSON.stringify(deposito),
			contentType: "application/json; charset=utf-8",
	        dataType   : "json",
			success : function(data) {
				data = JSON.stringify(data);
				var jsonobject = JSON.parse(data);

				if (jsonobject.estado == "ERROR") {
					$('#modal').modal("toggle")
					 swal("ERROR", jsonobject.error, "error");
				} else if (jsonobject.estado == 'OK') {

					swal("Proceso correcto", "Familia " + jsonobject.datos.idfamilia + " guardada.", "success")
					clear()
					reloadTable()
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

