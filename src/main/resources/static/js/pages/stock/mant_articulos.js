var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var login;
var table;
var estado = true;
var all = true;

$(document).ready(function(){
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

    //cambia el valor del check estado
    var $estado = $('#estado');
    $estado.on('ifUnchecked', function(event){
        estado = false;
        console.log(estado)
    });
	$estado.on('ifChecked', function(event){
        estado = true;
        console.log(estado)
	});

	$("#stock").mask("000.000.000.000",{reverse: true});
	//$("#preciocosto").mask("000.000.000.000",{reverse: true});

	// obtener el usuario logueado
    login = JSON.parse(sjcl.decrypt(encrypt, localStorage.usuario));
    console.log(login);
    
  //linea de articulos por familia
  /*  var emp = $("#selectFamilia");  
    emp.chosen().change({
    	
   });*/
    

    $('#selectFamilia').chosen().change(function () {
    	getLineaByFamilia($('#selectFamilia').val());
    });
    
});

// onclick del boton nuevo
function nuevo(){
    var $selectImpuesto = $("#selectImpuesto");
    habilitar($selectImpuesto);
    $selectImpuesto.trigger('chosen:updated');
    var $selectMarca = $("#selectMarca");
    habilitar($selectMarca);
    $selectMarca.trigger('chosen:updated');
    var $selectFamilia = $("#selectFamilia");
    habilitar($selectFamilia);
    $selectFamilia.trigger('chosen:updated');
    var $selectUnidad = $("#selectUnidad");
    habilitar($selectUnidad);
    $selectUnidad.trigger('chosen:updated');
    $("#estado").parent().removeClass('disabled');
	habilitar($(".campoHabilitable"));
	habilitar($("#grabar"));
    deshabilitar($("#nuevo"));
    
    //cambia el valor del check estado
    var $estado = $('#estado');
    $estado.on('ifUnchecked', function(event){
        estado = false;
        console.log(estado)
    });
	$estado.on('ifChecked', function(event){
        estado = true;
        console.log(estado)
	});
}

//onclick boton buscar
function buscar(){
	clear();
	$('#buscador_articulo').modal();
	$("#bus_descripcion").focus();
}

function clear(){
    deshabilitar($(".campoHabilitable"));
    $("#idarticulo").val("0")
    $(".campoHabilitable").val("");
    $('#estado').iCheck('check');
    $("#selectFamilia").val("").trigger('chosen:updated');
    $("#selectLinea").val("").trigger('chosen:updated');
    $("#selectUnidad").val("").trigger('chosen:updated');
    $("#selectMarca").val("").trigger('chosen:updated');
    $("#selectImpuesto").val("10").trigger('chosen:updated');
    estado = true;
    habilitar($("#nuevo"));
    deshabilitar($("#grabar"));
    
}

function grabar(){
    var $idarticulo = $("#idarticulo");
    var $descripcion = $("#descripcion");
    var $codigobarra = $("#codigobarra");
   // var $preciocosto = $("#preciocosto");
    var $stock = $("#stock");
    var $selectImpuesto = $("#selectImpuesto");
    var $selectFamilia = $("#selectFamilia");
    var $selectMarca = $("#selectMarca");
    var $selectUnidad = $("#selectUnidad");
    var $selectLinea = $("#selectLinea");

    var error = 0;
    error = inputRequired($descripcion)

    var articulo = {};
    var tipoimpuesto = {};
    var usuario = {};
    if (error === 0){
    	console.log(estado)
        articulo.estado = estado;
        articulo.descripcion = $descripcion.val();
        articulo.codigobarra = $codigobarra.val();
      //  articulo.preciocosto = $preciocosto.val().replace(".","");
        articulo.stock = $stock.val();
        tipoimpuesto.idtipoimpuesto = $selectImpuesto.find(":selected").val();
        articulo.idfamilia = $selectFamilia.find(":selected").val();
        articulo.idmarca = $selectMarca.find(":selected").val();
        articulo.idlineaarticulo = $selectLinea.find(":selected").val();
        articulo.idunidadmedida = $selectUnidad.find(":selected").val();
        articulo.tipoimpuesto = tipoimpuesto;
        usuario.idusuario = login.idusuario;
        articulo.usuario = usuario;
        articulo.codmoneda = 1;

        //insert
        if($idarticulo.val() == 0){
            type = "POST";
        }else{
            //update
            articulo.idarticulo = $idarticulo.val()
            type = "PUT";
        }
        console.log(JSON.stringify(articulo))

        $.ajax({
            type: type,
            url: context + "/api/v1/articulos/",
            data: JSON.stringify(articulo),
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
                    swal("Proceso correcto", "Artículo " + jsonobject.datos.idarticulo + " guardado.", "success")
                    clear();
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
			console.log(articulo);

			$("#idarticulo").val(articulo.idarticulo);
	
			if(row.cells[3].innerText == "Activo"){
				$('#estado').iCheck('check'); 
				 estado = true;
			}else{
				$('#estado').iCheck('uncheck'); 
				 estado = false;
			}
			$("#descripcion").val(articulo.descripcion);
			$("#codigobarra").val(articulo.codigobarra);
			//$("#preciocosto").val(articulo.preciocosto);
			$("#stock").val(articulo.stock);
			$("#selectImpuesto").val(articulo.tipoimpuesto.idtipoimpuesto);
			$("#selectMarca").val(articulo.idmarca);
			$("#selectFamilia").val(articulo.idfamilia);
			$("#selectUnidad").val(articulo.idunidadmedida);

			getLineaByFamilia(articulo.idfamilia)
			nuevo();
		}

	}

	if (!ban) {
		 swal("","Debes seleccionar un registro", "warning");
	}else{
		limpiarBuscador();
    }
}

//obtener depositos por empresa
function getLineaByFamilia(idfamilia){
	blockScreen();
	$('#selectLinea').chosen("destroy");
	$.ajax({
		type : "GET",
		url : context + "/api/v1/lineaarticulos/familia/" + idfamilia,		
		success : function(data) {
			console.log(data)
			data = JSON.stringify(data);
			var jsonobject = JSON.parse(data);
			if (jsonobject.estado == 'ERROR') {
				 swal("ERROR", jsonobject.error, "error");
			} else if (jsonobject.estado == 'OK') {	
				$.each(jsonobject.datos, function(index, element) {	
					var o = $("<option/>", {
						value : element.idlineaarticulo,
						text : element.descripcion
					});
					$("#selectLinea").append(o);
						
				});			
			}
			habilitar($("#selectLinea"));
			$("#selectLinea").chosen().trigger("chosen:updated");
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
