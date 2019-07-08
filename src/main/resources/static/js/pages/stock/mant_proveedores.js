var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var login;
var table;
var estado = true;

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
    });
	$estado.on('ifChecked', function(event){
        estado = true;
	});

	// obtener el usuario logueado
    login = JSON.parse(sjcl.decrypt(encrypt, localStorage.usuario));
    console.log(login);
});

// onclick del boton nuevo
function nuevo(){
    var $selectDoc = $("#selectDoc");
    habilitar($selectDoc);
    $selectDoc.trigger('chosen:updated');
    $("#estado").parent().removeClass('disabled');
	habilitar($(".campoHabilitable"));
	habilitar($("#grabar"));
    deshabilitar($("#nuevo"));
}

//onclick boton buscar
function buscar(){
	clear();
	$('#buscador_proveedor').modal();
}

function clear(){
    deshabilitar($(".campoHabilitable"));
    $(".campoHabilitable").val("");
    habilitar($("#nuevo"));
    deshabilitar($("#grabar"));
}

function grabar(){
    var $idproveedor = $("#idproveedor");
    var $descripcion = $("#descripcion");
    var $selectDoc = $("#selectDoc");
    var $nrodoc = $("#nrodoc");
    var $telefono1 = $("#telefono1");
    var $telefono2 = $("#telefono2");
    var $sitioweb = $("#sitioweb");
    var $direccion = $("#direccion");
    var $correo = $("#correo");

    var error = 0;
    error = inputRequired($descripcion) + inputRequired($nrodoc) + inputRequired($telefono1)

    var proveedor = {};
    var empresa = {};
    var tipodoc = {};
    var usuario = {};
    if (error === 0){
        empresa.idempresa = login.empresa.idempresa;
        proveedor.empresa = empresa;
        proveedor.estado = estado;
        proveedor.descripcion = $descripcion.val();
        proveedor.nrodoc = $nrodoc.val();
        proveedor.telefono1 = $telefono1.val();
        proveedor.telefono2 = $telefono2.val();
        proveedor.direccion = $direccion.val();
        proveedor.email = $correo.val();
        proveedor.sitioweb = $sitioweb.val();
        proveedor.codtipodoc = $selectDoc.find(":selected").val();
        proveedor.tipodoc = tipodoc;
        usuario.idusuario = login.idusuario;
        proveedor.usuario = usuario;

        //insert
        if($idproveedor.val() == 0){
            type = "POST";
        }else{
            //update
            proveedor.idproveedor = $idproveedor.val()
            type = "PUT";
        }

        $.ajax({
            type: type,
            url: context + "/api/v1/proveedores/",
            data: JSON.stringify(proveedor),
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
                    swal("Proceso correcto", "Proveedor " + jsonobject.datos.idproveedor + " guardado.", "success")
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
	var table = document.getElementById('tablaProveedores');
	var rowLength = table.rows.length;

	for (var i = 1; i < rowLength; i += 1) {
		var row = table.rows[i];
		var chkbox = row.cells[0].childNodes[0];
		if (true == chkbox.checked) {
			ban = true;
			$('#buscador_proveedor').modal('toggle');
			var proveedor = JSON.parse(row.cells[4].innerHTML);
			console.log(proveedor);

			$("#idproveedor").val(proveedor.idproveedor);
			if(proveedor.estado == true){
				$('#estado').iCheck('check');
			}else{
				$('#estado').iCheck('uncheck');
			}
			$("#descripcion").val(proveedor.descripcion);
			$("#telefono1").val(proveedor.telefono1);
			$("#telefono2").val(proveedor.telefono2);
			$("#direccion").val(proveedor.direccion);
			$("#sitioweb").val(proveedor.sitioweb);
			$("#correo").val(proveedor.email);
			$("#nrodoc").val(proveedor.nrodoc);
			$("#selectDoc").val(proveedor.codtipodoc);

			nuevo();
		}

	}

	if (!ban) {
		 swal("","Debes seleccionar un registro", "warning");
	}else{
		limpiarBuscador();
    }
}
