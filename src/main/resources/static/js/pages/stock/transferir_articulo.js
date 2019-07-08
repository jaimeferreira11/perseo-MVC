var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));

$(document).ready(function(){
    //inicializar el select con el pluguins choosen.js
    $('.chosen-select').chosen({
       	width: "100%",
       	inherit_select_classes: true
    });

    $('#selectSucursal1').chosen().change(function () {
        getDepositos($(this).val(), "1");
        clearByChangeFilter();
        habilitar($("#selectSucursal2"))
        
        $("#selectSucursal2").chosen().trigger("chosen:updated");
    });

    $('#selectDeposito1').chosen().change(function () {
            clearByChangeFilter();
            
            //habilita todos los options
            $("#selectDeposito2").find('option').each(function () {
           	 $(this).prop('disabled', false);
   		  });
           //deshabilita el seleccionado
           $("#selectDeposito2").find("option[value='"+$(this).val()+"']").prop("disabled",true);
           $("#selectDeposito2").val("0").chosen().trigger("chosen:updated");
    });

    $('#selectSucursal2').chosen().change(function () {
            getDepositos($(this).val(), "2");
          

    });

    $("#buscar").click(function(){
            buscar();
    });

    $("#agregar").click(function(){
            agregarFila();
    });
});

function clearByChangeFilter(){
    var $idarticulo = $("#idarticulo");
    var $cantidad = $("#cantidad");
    var $descripcion = $("#descripcion");
    $('#tabla > tbody').empty();
    $idarticulo.val("0");
    $cantidad.val("0");
    $descripcion.val("");
}

function agregarFila(){
    var $tabla = $("#tabla");
    var $idarticulo = $("#idarticulo");
    var $cantidad = $("#cantidad");
    var $descripcion = $("#descripcion");
    var ok = true;

    $("#transfer_body tr").each(function () {
        var idarticulo = $(this).find("#my_id").text();
        if(idarticulo===$idarticulo.val()){
            swal("","Este artículo ya se encuentra en la lista de artículos a transferir", "warning");
            ok = false;
        }
    });

    if($idarticulo.val()!=="0" && ok){
        $('#tabla > tbody:last-child').append('<tr><td id="my_id" style="vertical-align: middle;">'+$idarticulo.val()
        +'</td><td style="vertical-align: middle;">'+$descripcion.val()+'</td><td id="my_cant" style="vertical-align: middle;">'
            +$cantidad.val()+'</td><td style="vertical-align: middle;">'
            +'<button type="button" class="btn btn-w-m btn-outline btn-danger" id="del_'
            +$idarticulo.val()+'" style="font-size: 13px;">Eliminar</button>'+'</td></tr>');

        $("#del_"+$idarticulo.val()).click(function(){
            $(this).parent().parent().remove();
        });

        $idarticulo.val("0");
        $cantidad.val("0");
        $descripcion.val("");
        habilitar($("#grabar"))
    }
}

//obtener depositos por sucursal
function getDepositos(id,inputNumber){
	blockScreen();
	var $selectDeposito = $('#selectDeposito'+inputNumber);
	$selectDeposito.empty().trigger("chosen:updated");
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
                    $selectDeposito.append(o);
                    $.each(jsonobject.datos, function(index, element) {
                        var o = $("<option/>", {
                            value : element.iddeposito,
                            text : element.descripcion
                        });
                        $selectDeposito.append(o);

                    });
                    habilitar($selectDeposito);
                }else{
                	deshabilitar($selectDeposito);
                }
            }
            $selectDeposito.chosen().trigger("chosen:updated");
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

	if(inputNumber==="2"){
	    setTimeout(function(){
            $("#selectDeposito2").find('option').each(function () {
                var deposito_1_selected = $("#selectDeposito1").val();
                if(deposito_1_selected===$(this).val()){
                    $(this).prop('disabled', true);
                }else{
                    $(this).prop('disabled', false);
                }
            });
            $("#selectDeposito2").chosen().trigger("chosen:updated");
        }, 1000);
    }else{
        setTimeout(function(){
            $("#selectDeposito1").find('option').each(function () {
                var deposito_2_selected = $("#selectDeposito2").val();
                if(deposito_2_selected===$(this).val()){
                    $(this).prop('disabled', true);
                }else{
                    $(this).prop('disabled', false);
                }
            });
            $("#selectDeposito1").chosen().trigger("chosen:updated");
        }, 1000);
    }
}

function buscar(){
	 var  deposito_origen = $("#selectDeposito1").val();
	 var  deposito_destino = $("#selectDeposito2").val();
     if(deposito_origen === "0" || deposito_destino ==="0" || deposito_origen===null || deposito_destino===null){
        swal("","Seleccione el origen y destino antes de buscar el artículo", "warning");
        return;
     }

    busca_por_deposito = $("#selectDeposito1").val();
	buscar_por_sucursal = $("#selectSucursal1").val();
	$('#buscador_articulo_deposito').modal();
}

function insertRegistroFromBuscador(){
	var ban = false;
	var table = document.getElementById('tablaArticulos');
	var rowLength = table.rows.length;

	for (var i = 1; i < rowLength; i += 1) {
		var row = table.rows[i];
		var chkbox = row.cells[0].childNodes[0];
		if (true == chkbox.checked) {
			ban = true;
			$('#buscador_articulo_deposito').modal('toggle');
			var articulo = JSON.parse(row.cells[5].innerHTML);
			console.log(articulo);

			$("#idarticulo").val(articulo.articulo.idarticulo);
			$("#descripcion").val(articulo.articulo.descripcion);
			$("#cantidad").val(articulo.cantidad)
			 $("#cantidad").attr({"max" : articulo.cantidad, "min" : 1});
			
		}
	}

	if (!ban) {
		 swal("","Debes seleccionar un registro", "warning");
	}else{
		limpiarBuscador();
    }
}

function grabar(){
    if($('#selectDeposito1').val()===$('#selectDeposito2').val()){
        swal("","Los depósitos deben ser diferentes", "warning");
        return;
    }

    if($("#transfer_body tr").length < 1){
        swal("","Agregue al menos un artículo", "warning");
        return;
    }

    var articuloTransferenciaCab = {};
    var articuloTransferenciaDet = [];
    var cantidadTotal = 0;
    var depositoOrigen = {};
    var depositoDestino = {};

    depositoOrigen.iddeposito = $("#selectDeposito1").val();
    depositoDestino.iddeposito = $("#selectDeposito2").val();

    $("#transfer_body tr").each(function () {
        var itemDet = {};
        var articulo = {};
        var idarticulo = $(this).find("#my_id").text();
        var cantidad = $(this).find("#my_cant").text();
        cantidadTotal += parseInt(cantidad);
        articulo.idarticulo = idarticulo;
        itemDet.articulo = articulo;
        itemDet.cantidadrecibido = cantidad;
        articuloTransferenciaDet.push(itemDet);
    });

    articuloTransferenciaCab.cantidadtotal = cantidadTotal;
    articuloTransferenciaCab.depositoorigen = depositoOrigen;
    articuloTransferenciaCab.depositodestino = depositoDestino;

    var datos = {};
    datos.articulotransferenciacab = articuloTransferenciaCab;
    datos.detalles = articuloTransferenciaDet;
    datos = JSON.stringify(datos);
    console.log(datos);

    $.ajax({
        type: "POST",
        url: context + "/api/v1/stock/transferir/",
        data: datos,
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
                swal("Proceso correcto", jsonobject.datos  , "success")
                clearByChangeFilter();
                deshabilitar($("#grabar"))
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