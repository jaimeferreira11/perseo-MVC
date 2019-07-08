var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var login;
var table;
var estado = true;
var all = true;
$(document).ready(function(){

	// Inicializar la tabla con el pluguin datatable.js
	table = $('#tabla').DataTable({
   	 "language": { "url": context + "/js/plugins/dataTables/Spanish.json"},
   	 pageLength: 10,
     responsive: true,
     dom: '<"html5buttons"B>lTfgitp',
     buttons: [
         { extend: 'copy', title: 'Articulo-Deposito', exportOptions: { columns: ':visible'}},
         {extend: 'csv' , title: 'Articulo-Deposito',exportOptions: { columns: ':visible'}},
         {extend: 'excel', title: 'Articulo-Deposito',exportOptions: { columns: ':visible'}},
         {extend: 'pdf', title: 'Articulo-Deposito',exportOptions: { columns: ':visible'}},

         {extend: 'print', title: 'Articulo-Deposito',exportOptions: { columns: ':visible'},
          customize: function (win){
                 $(win.document.body).addClass('white-bg');
                 $(win.document.body).css('font-size', '10px');

                 $(win.document.body).find('table')
                         .addClass('compact')
                         .css('font-size', 'inherit');
         }
         }
     ]

	});
	// click en un registro de la tabla
	$('#tabla tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
            deshabilitar($("#editar"))
            deshabilitar($("#eliminar"))
            habilitar($("#buscar"))
            clearForm();
        } else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
            habilitar($("#editar"))
            habilitar($("#eliminar"))
            deshabilitar($("#buscar"))
            insertForm(this);
            
        }
    } );
    //inicializar el select con el pluguins choosen.js
    $('.chosen-select').chosen({
       	width: "100%",
       	inherit_select_classes: true
    });
    
    //cambia el valor del check estado
    var $estado = $('#estado');
    $estado.on('ifUnchecked', function(event){
        estado = false;
    });
	$estado.on('ifChecked', function(event){
        estado = true;
	});


	// obtener el usuario logueado
    login = JSON.parse(sjcl.decrypt(encrypt, localStorage.usuario));
    console.log(login);

    getDepositos($('#selectSucursal').val());
   // getArticulos($('#selectSucursal').val(), $('#selectDeposito').val());
    		
    $('#selectSucursal').chosen().change(function () {
        getDepositos($(this).val());
    	reloadTable();
    	getArticulos($('#selectSucursal').val(), $('#selectDeposito').val());
    });
    $('#selectDeposito').chosen().change(function () {
    	reloadTable();
    	getArticulos($('#selectSucursal').val(), $('#selectDeposito').val());
    	habilitar($("#buscar"))
    });
    
});

//completa el form para editar
function insertForm(fila){
	var obj = JSON.parse(fila.cells[6].innerHTML);
  $("#idarticulodep").val(obj.idarticulodeposito)
  $("#codigo").val(obj.articulo.codigobarra)
   $("#descripcion").val(obj.articulo.descripcion)
  $("#idarticulo").val(obj.articulo.idarticulo)
  $("#cantidads").val(obj.cantidad)
  $("#cantidadm").val(obj.cantidadminima)
  $("#cantidadb").val(obj.cantidadbloqueo)
  $("#fecha").val(obj.fechavencimiento)
  $("#lote").val(obj.nroolote)
  $("#precioc").val(obj.preciocosto)
 // $("#preciov").val(obj.precioventa)
	
}
//limpiar el formulario
function clearForm(){
	 $("#idarticulodep").val("0")
	  $("#idarticulo").val("0")
	  $("#cantidads").val("0")
	  $("#cantidadm").val("0")
	  $("#cantidadb").val("0")
	  $("#fecha").val("")
	  $("#lote").val("")
	  $("#precioc").val("0")
	 // $("#preciov").val("0")
}
//onclick boton editar
function editar(){
	$('#agregar_articulo_deposito').modal();
}
//Vuelve a poblar la tabla
function reloadTable(){
	table.clear().draw();
}

//obtener depositos por empresa
function getArticulos(idsucursal, iddeposito){
	blockScreen();
	$.ajax({
		type : "GET",
		url : context + "/api/v1/articulosdeposito/" + idsucursal+"/"+iddeposito+"/"+all,		
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
							"0": element.idarticulodeposito,
							"1": element.articulo.codigobarra,
							"2": element.articulo.descripcion,	
							"3": element.cantidad,
							"4": element.preciocosto,
							"5": estado,
							"6": JSON.stringify(element),
				
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



//onclick boton buscar
function buscar(){
	$('#buscador_articulo').modal();
}


function grabarArticulo(){
	var $id = $("#idarticulodep");
    var $idarticulo = $("#idarticulo");
    var $cantidads = $("#cantidads");
    var $cantidadm = $("#cantidadm");
    var $cantidadb = $("#cantidadb");
    var $fecha = $("#fecha");
    var $lote = $("#lote");
    var $precioc = $("#precioc");
   // var $preciov = $("#preciov");
    var $sucursal = $("#selectSucursal");
    var $deposito = $("#selectDeposito");
    var error= 0;

    var articulodeposito = {};
    var articulo = {};
    var sucursal = {};
    var deposito = {};
    var usuario = {};

    if (error === 0){
    	articulodeposito.estado = estado;
    	articulodeposito.cantidad = $cantidads.val().replace(/\./g, '');
    	articulodeposito.cantidadminima = $cantidadm.val().replace(/\./g, '');
    	articulodeposito.cantidadbloqueada = $cantidadb.val().replace(/\./g, '');
        sucursal.idsucursal = $sucursal.find(":selected").val();
        articulodeposito.sucursal = sucursal
        deposito.iddeposito = $deposito.find(":selected").val();
        articulodeposito.deposito = deposito;
        if(validarVacio($fecha.val())){        	
        	articulodeposito.fechavencimiento = moment(formatServerFecha($fecha.val()), "YYYY-MM-DD");
        }
        articulodeposito.preciocosto = $precioc.val().replace(/\./g, '');
       // articulodeposito.precioventa = $preciov.val().replace(/\./g, '');
        usuario.idusuario = login.idusuario;
        articulodeposito.usuario = usuario;
        articulo.idarticulo = $idarticulo.val();
        articulodeposito.articulo = articulo;
        articulodeposito.nrolote = $lote.val().replace(/\./g, '');


        //insert
        if($id.val() == 0){
            type = "POST";
        }else{
            //update
        	articulodeposito.idarticulodeposito = $id.val()
            type = "PUT";
        }

        console.log(JSON.stringify(articulodeposito))
        $.ajax({
            type: type,
            url: context + "/api/v1/articulosdeposito/",
            data: JSON.stringify(articulodeposito),
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
                    swal("Proceso correcto", "Artículo " + jsonobject.datos.articulo.idarticulo + " guardado.", "success")
                    $('#agregar_articulo_deposito').modal('toggle');
                    reloadTable()
                    getArticulos($('#selectSucursal').val(), $('#selectDeposito').val())
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

// inserta el registro desde el buscador
function insertRegistroFromBuscador(){
	
	$('#agregar_articulo_deposito').modal();
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

			$("#idarticulo").val(articulo.idarticulo);
			if(articulo.estado == true){
				$('#estado').iCheck('check');
			}else{
				$('#estado').iCheck('uncheck');
			}
			$("#descripcion").val(articulo.descripcion);
			$("#codigo").val(articulo.codigobarra);

			$('#agregar_articulo_deposito').modal();
		}

	}

	if (!ban) {
		 swal("","Debes seleccionar un registro", "warning");
	}else{
		limpiarBuscador();
    }
}

//obtener depositos por sucursal
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
    		//		habilitar($("#buscar"))
				}else{
					deshabilitar($("#selectDeposito"));
					deshabilitar($("#buscar"))
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