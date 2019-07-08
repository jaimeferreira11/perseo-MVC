var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var login;
var table;
var all = true;
$(document).ready(function(){
	
	var table = $('#tabla').DataTable({
   	 "language": { "url": context + "/js/plugins/dataTables/Spanish.json"},
   	pageLength: 10,
       responsive: true,
       dom: '<"html5buttons"B>lTfgitp',
       buttons: [
           { extend: 'copy', title: 'Perfiles',exportOptions: { columns: ':visible'}},
           {extend: 'csv', title: 'Perfiles',exportOptions: { columns: ':visible'}},
           {extend: 'excel', title: 'Perfiles',exportOptions: { columns: ':visible'}},
           {extend: 'pdf', title: 'Perfiles',exportOptions: { columns: ':visible'}},

           {extend: 'print', title: 'Perfiles',exportOptions: { columns: ':visible'},
            customize: function (win){
                   $(win.document.body).addClass('white-bg');
                   $(win.document.body).css('font-size', '10px');

                   $(win.document.body).find('table')
                           .addClass('compact')
                           .css('font-size', 'inherit');
           }
           }
       ],
       //formato numerico
       "createdRow": function ( row, data, index ) {
               $('td', row).eq(4).addClass('price');
               $('td', row).eq(3).addClass('price');
       }

   });
	$('#tabla tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
            clearForm()
        } else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
            insertForm(this);
            
        }
    } );
	
    //inicializar el select con el pluguins choosen.js
    $('.chosen-select').chosen({
        width: "100%",
        inherit_select_classes: true
    });

    $("#precioventa").mask("000.000.000.000",{reverse: true});
    
    $(".price").mask("000.000.000.000",{reverse: true});

    $('#selectListaPrecios').chosen().change(function () {
        obtenerArticulos($(this).val());
        habilitar($("#buscar"))
    });

    login = JSON.parse(sjcl.decrypt(encrypt, localStorage.usuario));
    console.log(login);
});

//onclick boton buscar
function buscar(){
	$('#buscador_articulo').modal();
}

function insertRegistroFromBuscador(){
	var table = document.getElementById('tablaArticulos');
	var rowLength = table.rows.length;

	for (var i = 1; i < rowLength; i += 1) {
		var row = table.rows[i];
		var chkbox = row.cells[0].childNodes[0];
		if (true == chkbox.checked) {
			ban = true;
			$('#buscador_articulo').modal('toggle');
			var articulo = JSON.parse(row.cells[4].innerHTML);
			console.log(articulo);

			$("#idarticulo").val(articulo.idarticulo);
			$("#descripcion").val(articulo.descripcion);
			//deshabilitar($("#buscar"))
			habilitar($("#grabar"))
		}
	}
}

function obtenerArticulos(id){
	reloadTable();
$.ajax({
    type : "GET",
    url: context + "/api/v1/articuloprecioventadet/cab/"+id,
    success: function(data) {
        console.log(data);
        var datos = data.datos;

        var table = $('#tabla').DataTable();

		$.each(datos, function(index, element) {

		
			table.rows.add( [ {
					"0": element.articulo.idarticulo,
					"1": element.articulo.codigobarra,
					"2": element.articulo.descripcion,
					"3": element.articulo.preciocosto,
					"4": element.precio,
					"5": element.idarticuloprecioventadet,
		
				}
				] ).draw();
			//formato numerico
			$(".price").mask("000.000.000.000",{reverse: true});
			});	
    },
    error: function(e) {
        console.log("ERROR", e);
    },
    done: function(e) {
        console.log("DONE");
    }
});
}

function reloadTable(){
	var table = $('#tabla').DataTable();
	table.clear().draw();

	
}

function clearForm() {
	//table.$('tr.selected').removeClass('selected');
	$('#idarticulo').val("0");
	$('#descripcion').val("");
	$('#precioventa').val("0");
	$('#idprecioventa').val("0");
	deshabilitar($("#grabar"))
	habilitar($("#buscar"))
}

function insertForm(fila) {
	$('#idarticulo').val(fila.cells[0].innerHTML);
	$('#descripcion').val(fila.cells[2].innerHTML);
	$('#precioventa').val(fila.cells[4].innerHTML);
	$('#idprecioventa').val(fila.cells[5].innerHTML);
	//deshabilitar($("#buscar"))
	habilitar($("#grabar"))
}


function grabar(){
    var $idarticuloprecioventadet= $("#idprecioventa");
    var $idarticuloprecioventacab = $("#selectListaPrecios");
    var $precio= $("#precioventa");
    var $idarticulo= $("#idarticulo");


    var error = 0;
    error = inputRequired($precio) + inputMayor0($idarticulo)
    
    var articuloprecioventadet = {};
    var articuloprecioventacab = {};
    var articulo = {};
    if (error === 0){
    	
        articulo.idarticulo = $idarticulo.val();
        articuloprecioventacab.idarticuloprecioventacab = $idarticuloprecioventacab.val();
        articuloprecioventadet.articuloprecioventacab= articuloprecioventacab;
        articuloprecioventadet.articulo = articulo;
        articuloprecioventadet.precio = $precio.val().replace(/\./g, '');
        

        //insert
        if($idarticuloprecioventadet.val() == 0){
            type = "POST";
        }else{
            //update
        	articuloprecioventadet.idarticuloprecioventadet = $idarticuloprecioventadet.val()
            type = "PUT";
        }
        console.log(JSON.stringify(articuloprecioventadet))

        $.ajax({
            type: type,
            url: context + "/api/v1/articuloprecioventadet/",
            data: JSON.stringify(articuloprecioventadet),
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
                    swal("Proceso correcto", jsonobject.datos.idarticuloprecioventadet + " guardado correctamente"  , "success")
                    clearForm();
                    reloadTable();
                    obtenerArticulos($('#selectListaPrecios').val());
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