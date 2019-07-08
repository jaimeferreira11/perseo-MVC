var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var login;
var all = false;
var columna = "I"
var arrayIngresos = []
var arrayEgresos = []

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

    $("#buscar").click(function(){
        buscar();
    });

    // obtener el usuario logueado
    login = JSON.parse(sjcl.decrypt(encrypt, localStorage.usuario));
    
    $('#selectSucursal').chosen().change(function () {
        getDepositos($(this).val());
    });
   
    $('#selectDeposito').chosen().change(function () {
    	 clear();
       
    })
    
  
    //obtiene los conceptos			
    getConceptoMov();
    
    
    
    deshabilitar($(".campoHabilitable"))
    
});	


function radio(){
	$('input:radio[name=columna]').on('ifChecked', function(event){
		   columna = $(this).val(); 
		   
		  $('#selectConcepto').empty().trigger("chosen:updated");
		   var o = $("<option/>", {
			   text : "Seleccione"
		   });
		   o.attr('disabled', 'disabled');
		   o.attr('selected', 'selected');
		   $("#selectConcepto").append(o);
		   if(columna === 'E'){
			   $.each(arrayEgresos, function(index, element) {	

					var o = $("<option/>", {
							value : element.idconceptomov,
							text : element.descripcion
						});
					$("#selectConcepto").append(o);
		
				});	
			   
			   $("#selectConcepto").chosen().trigger("chosen:updated");
		   }else if(columna === 'I'){
			   $.each(arrayIngresos, function(index, element) {	

					var o = $("<option/>", {
							value : element.idconceptomov,
							text : element.descripcion
						});
					$("#selectConcepto").append(o);
		
				});	
			   
			   $("#selectConcepto").chosen().trigger("chosen:updated");
			   
		   }
		});
}

//obtener concepto movimiento tipo
function getConceptoMov(){
	//$('#selectConcepto').chosen("destroy");
	$.ajax({
		type : "GET",
		url : context + "/api/v1/conceptomov/",		
		success : function(data) {
			console.log(data)
			data = JSON.stringify(data);
			var jsonobject = JSON.parse(data);
			if (jsonobject.estado == 'ERROR') {
				 swal("ERROR", jsonobject.error, "error");
			} else if (jsonobject.estado == 'OK') {	
				var o = $("<option/>", {
					   text : "Seleccione"
				   });
				   o.attr('disabled', 'disabled');
				   o.attr('selected', 'selected');
				   $("#selectConcepto").append(o);
				$.each(jsonobject.datos, function(index, element) {	
					
					if(element.tipo == 'I'){
						arrayIngresos.push(element);
						var o = $("<option/>", {
							value : element.idconceptomov,
							text : element.descripcion
						});
						$("#selectConcepto").append(o);
					}else if (element.tipo == 'E'){
						arrayEgresos.push(element);
					}
					
						
				});			
			}
			$("#selectConcepto").chosen().trigger("chosen:updated");
			radio()
			
			

		},
		error : function(e) {
			console.log("ERROR: ", e);	
		},
		done : function(e) {
			console.log("DONE");
		}
	});
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
					 habilitar($("#buscar"))
					 clear();
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

//onclick boton buscar
function buscar(){
	clear();
	// se envia la buscador los parametros
	 busca_por_deposito = $("#selectDeposito").val();
	 buscar_por_sucursal = $("#selectSucursal").val();
	$('#buscador_articulo_deposito').modal();
	$("#bus_descripcion").focus();
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
			$('#buscador_articulo_deposito	').modal('toggle');
			var articulo = JSON.parse(row.cells[5].innerHTML);
			console.log(articulo);

			$("#idarticulo").val(articulo.articulo.idarticulo);
			$("#descripcion").val(articulo.articulo.descripcion);
			$("#existencia").val(articulo.cantidad)
			
			habilitar($(".campoHabilitable"))
			habilitar($("#grabar"))
			$("#selectConcepto").chosen().trigger("chosen:updated");
			
			radio()
		}

	}

	if (!ban) {
		 swal("","Debes seleccionar un registro", "warning");
	}else{
		limpiarBuscador();
    }
}

function clear(){
    deshabilitar($(".campoHabilitable"));
    $("#idarticulo").val("0")
    $('#descripcion').val("")
    $('#existencia').val("")
    $(".campoHabilitable").val("");
    $('input[name=columna][value=I]').iCheck('check'); 
}


function grabar(){

    var $existencia= $("#existencia");
    var $cantidad= $("#cantidad");
    var $idarticulo= $("#idarticulo");
    var $concepto= $("#selectConcepto");

    
    var error = 0;

    error += inputRequired($cantidad) + inputMayor0($idarticulo) + selectRequired($concepto) + inputMayor0($cantidad)

    if($cantidad.val()!= ""){

    	if(columna === "E" && parseInt($existencia.val()) < parseInt($cantidad.val()) ){
    		swal("","La cantidad a dar de baja es mayor a la existencia actual", "warning");
    		return;
    	}
    }
    console.log(error)
    var articulomovimiento = {};
    var deposito = {};
    var sucursal = {};
    var conceptomov = {};
    var articulo = {};
    if (error === 0){
    	
        articulo.idarticulo = $idarticulo.val();
        articulomovimiento.articulo = articulo;
        deposito.iddeposito = $("#selectDeposito").val()
        articulomovimiento.deposito = deposito;
        sucursal.idsucursal= $("#selectSucursal").val()
        articulomovimiento.sucursal = sucursal;
        conceptomov.idconceptomov = $concepto.val()
        articulomovimiento.conceptomov = conceptomov;
        articulomovimiento.cantidad = $cantidad.val();
        articulomovimiento.columna = columna;
        
        if($("#observaciones").val() != ""){
        	articulomovimiento.obs = $("#observaciones").val(); 
        }
        
        console.log(JSON.stringify(articulomovimiento))

        $.ajax({
            type: "POST",
            url: context + "/api/v1/stock/ajuste/",
            data: JSON.stringify(articulomovimiento),
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
                    clear();
                    deshabilitar($("#grabar"))
        			$("#selectConcepto").val("").chosen().trigger("chosen:updated");
     
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