var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var login;
var n = 0;
var all = true;
var $ck_sucursal= true;

$(document).ready(function(){
    login = JSON.parse(sjcl.decrypt(encrypt, localStorage.usuario));

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

    $('#fecha').daterangepicker({
    	format: 'DD/MM/YYYY',
        startDate: moment().subtract(29, 'days'),
        endDate: moment(),
        dateLimit: { days: 60 },
        showDropdowns: true,
        opens: 'right',
        drops: 'down',
        buttonClasses: ['btn', 'btn-sm'],
        applyClass: 'btn-primary',
        cancelClass: 'btn-default',
        separator: ' - ',
        locale: {
            applyLabel: 'Aceptar',
            cancelLabel: 'Cancelar',
            fromLabel: 'Desde',
            toLabel: 'Hasta',
            customRangeLabel: 'Custom',
            daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr','Sa'],
            monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
            firstDay: 1
        }
    	
    });

    //cambia el valor del check matriz
    var $matriz = $('#ck_sucursal');
    $matriz.on('ifUnchecked', function(event){
    	habilitar($('#selectSucursal'));
    	$('#selectSucursal').trigger('chosen:updated');
    	$ck_sucursal = false;
    	
    });
	$matriz.on('ifChecked', function(event){
		$ck_sucursal = true;
		deshabilitar($('#selectSucursal'));
    	$('#selectSucursal').trigger('chosen:updated');
	});
	
	
    
});




function grabar(tipo){
	

    var error = 0;
    error = error + inputRequired($("#fecha"))
    
    if($ck_sucursal === false){
    	error = error + selectRequired($("#selectSucursal"))
    }
    
    var fechadesde = $('#fecha').data('daterangepicker').startDate.format('DD/MM/YYYY');
    var fechahasta = $('#fecha').data('daterangepicker').endDate.format("DD/MM/YYYY");

    if(error>0){
        return;
    }

    	
    var datos = {};

    datos.fechadesde = fechadesde;
    datos.fechahasta = fechahasta;
    datos.suc_todas = $ck_sucursal;
    if($("#selectSucursal").val() == null){
    	datos.idsucursal = 0;
    }else{
    	datos.idsucursal = $("#selectSucursal").val();
    }


    console.log(JSON.stringify(datos))

    $.ajax({
        type: "POST",
        url: context + "/api/v1/compra/informe/libro-compra/",
        data: JSON.stringify(datos),
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
            	
            	if(jsonobject.datos == ""){
            		swal("Atencion", "No existen datos", "info");
            	}else{
            		
            		swal({
            			title: "Proceso correcto",
            			text: "",
            			type: "success",
            			// showCancelButton: true,
            			//  confirmButtonClass: "btn-danger",
            			confirmButtonText: "Descargar",
            			closeOnConfirm: false
            		},
            		function(){
            			var filename = jsonobject.datos;
            			var url = context + "/downloadFile?filename="+filename;
            			location.href=url;
            			setTimeout(function(){
            				location.reload(true);
            			}, 1000);
            		});
            	}
  

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