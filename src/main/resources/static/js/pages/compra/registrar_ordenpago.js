var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var login;
var porcentajeRt = 0.3;
var table;
var tableOP;
var listFormaPago = [];
var idordenpagocabAconfirmar;

//variable para modal forma_pago
var fp_total_a_pagar = 0;

$(document).ready(function(){
    table = $('#tablaDetalleCompra').DataTable({
        "language": { "url": context + "/js/plugins/dataTables/Spanish.json"},
        pageLength: 10,
        responsive: true,
        dom: '<"html5buttons"B>lTfgitp',
        buttons: [
            {extend: 'csv', title: 'Detalle Compra',exportOptions: { columns: ':visible'}},
            {extend: 'excel', title: 'Detalle Compra',exportOptions: { columns: ':visible'}},
            {extend: 'pdf', title: 'Detalle Compra',exportOptions: { columns: ':visible'}}
        ],
        //formato numerico
        "createdRow": function ( row, data, index ) {
            $('td', row).eq(2).addClass('price');
            $('td', row).eq(3).addClass('price');
            $('td', row).eq(4).addClass('price');
            $('td', row).eq(5).addClass('price');
            $('td', row).eq(6).addClass('price');
    	}
    });

    tableOP = $('#tablaDetalleOrden').DataTable({
            "language": { "url": context + "/js/plugins/dataTables/Spanish.json"},
            pageLength: 10,
            responsive: true,
            dom: '<"html5buttons"B>lTfgitp',
            buttons: [
                {extend: 'csv', title: 'Detalle Orden',exportOptions: { columns: ':visible'}},
                {extend: 'excel', title: 'Detalle Orden',exportOptions: { columns: ':visible'}},
                {extend: 'pdf', title: 'Detalle Orden',exportOptions: { columns: ':visible'}}
            ],
            //formato numerico
            "createdRow": function ( row, data, index ) {
                $('td', row).eq(3).addClass('price');
                $('td', row).eq(4).addClass('price');
        	}
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

    $("#buscar").click(function(){
        buscar();
    });

    $("#buscarOP").click(function(){
        buscarOP();
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
   // $("#fechaInicio").mask("99/99/9999");
   // $("#fechaFin").mask("99/99/9999");
    $('input[name=rt_iva]:checkbox').on('ifChanged', function () {
            var $importe_rt = $("#importe_rt");
            var $importe_total = $("#importe_total");
            var calculado = parseInt(parseInt($importe_total.val().replace(/\./g,'')) * porcentajeRt);
            if ($(this)[0].checked) {
                $importe_rt.val(aplicarSeparadorMiles(calculado));
            }else {
                $importe_rt.val("0");
            }
    });

    $('input[name=master_check]:checkbox').click(function(){
        var checkboxes = document.getElementsByName('slave_check');
        for(var i=0; i<checkboxes.length;i++) {
            checkboxes[i].checked = $(this)[0].checked;
            calcularImporte();
        }
    });

    /*$('input[name=master_check_op]:checkbox').click(function(){
            var checkboxes = document.getElementsByName('slave_check_op');
            for(var i=0; i<checkboxes.length;i++) {
                checkboxes[i].checked = $(this)[0].checked;
                calcularImporteOP();
            }
    });*/

    $(".price").mask("000.000.000.000",{reverse: true});
});

function buscar(){
    $('#tabla > tbody').empty();
    var fechaInicio = $('#fecha').data('daterangepicker').startDate.format('DD/MM/YYYY');
   // fechaInicio = fechaInicio === undefined ? "" : fechaInicio;
    var fechaFin = $('#fecha').data('daterangepicker').endDate.format("DD/MM/YYYY");
    //fechaFin = fechaFin === undefined ? "" : fechaFin;
    var soloPendientes = $('input[name="soloPendientes"]:checked').val();
    soloPendientes = soloPendientes === undefined ? "" : soloPendientes;
    var proveedor = $("#selectProveedor").val();
   /* if(fechaInicio!=="" || fechaFin !== ""){
        if(!(fechaInicio!=="" && fechaFin !== "")){
            inputRequired($("#fechaInicio"));
            inputRequired($("#fechaFin"));
            return;
        }
    }*/
    var error = 0;
    error = error + inputRequired($("#fecha"))
    if(error>0){
        return;
    }
    var parametros = "?ini="+fechaInicio+"&fin="+fechaFin+"&estado="+soloPendientes+"&proveedor="+proveedor;
    $.ajax({
    		type : "GET",
    		url : context + "/api/v1/compra/obtener/" + parametros,
    		success : function(data) {
    			console.log(data)
    			data = JSON.stringify(data);
    			var jsonobject = JSON.parse(data);
    			if (jsonobject.estado == 'ERROR') {
    				swal("ERROR", jsonobject.error, "error");
    			} else if (jsonobject.estado == 'OK') {
    				$.each(jsonobject.datos, function(index, element) {
    				    var id = element.idcompracab;
    				    var tipo = element.tipo;
    				    var tipodoc;
    				    if (tipo==="FA"){
    				        tipodoc= "Factura";
    				    }else if(tipo==="RE"){
    				        tipodoc= "Recibo";
    				    }

                        var pagado = parseInt(element.pagado);
                        var saldo = parseInt(element.importe) - pagado;
                        var disabled = (saldo===0?'disabled="disabled"':'');
                        var cel1 = '<td id="cel_1" style="vertical-align: middle;"><input type="checkbox" name="slave_check" value="'
                        +id+'"'+disabled+'/></td>';
                        var cel2 = '<td id="cel_2" style="vertical-align: middle;">'+element.proveedor.descripcion+'</td>';
                        var cel3 = '<td id="cel_3" style="vertical-align: middle;">'+formatFecha(element.fecha)+'</td>';
                        var cel8 = '<td id="cel_8" style="vertical-align: middle;">'+tipodoc+'</td>';
                        var cel11 = '<td id="cel_11" style="vertical-align: middle;">'+element.comprobante+'</td>';
                        var cel4 = '<td id="cel_4" style="vertical-align: middle;">'+aplicarSeparadorMiles(element.importe)+'</td>';
                        var cel5 = '<td id="cel_5" style="vertical-align: middle;"><input id="a_pagar_'+id+'" value="'
                        +aplicarSeparadorMiles(saldo)+'" class="form-control campoHabilitable"'+disabled+'/></td>';
                        var cel6 = '<td id="cel_6" style="vertical-align: middle;">'+aplicarSeparadorMiles(pagado)+'</td>';
                        var cel7 = '<td id="cel_7" style="vertical-align: middle;">'+aplicarSeparadorMiles(saldo)+'</td>';
                        var cel9 = '<td id="cel_9" style="vertical-align: middle;">'+element.usuario.usuario+'</td>';
                        var cel10 = '<td id="cel_10" style="vertical-align: middle;">'+element.estadocompra.descripcion+'</td>';
                        var cel12 = '<td id="cel_12" style="vertical-align: middle;">'+'<a data-toggle="modal" href="#detalle_compra" onclick="verDetalleCompra('+id+')">Ver Detalles</a></td>';

                        $('#tabla > tbody:last-child').append('<tr>'+cel1+cel2+cel3+cel8+cel11+cel4+cel5+cel6+cel7+cel9+cel10+cel12+'</tr>');
                        $("#a_pagar_"+id).mask("000.000.000.000",{reverse: true});
                        $("#a_pagar_"+id).blur(function() {
                            calcularImporte();
                        });
    				});
    				$('input[name=slave_check]:checkbox').change(function() {
                        calcularImporte();
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

function calcularImporte(){
    var importe_total = 0;
    $('input[name=slave_check]:checked').each(function() {
            var id = $(this).val();
            var a_pagar = $("#a_pagar_"+id).val().replace(/\./g,'');
            importe_total = importe_total + parseInt(a_pagar);
    });

    if ($('input[name=rt_iva]:checkbox')[0].checked) {
        $("#importe_rt").val(aplicarSeparadorMiles(parseInt(importe_total*porcentajeRt)));
    }
    $("#importe_total").val(aplicarSeparadorMiles(importe_total));
}

function grabar(){
    var compras = []
    $('input[name=slave_check]:checked').each(function() {
                var id = $(this).val();
                var a_pagar = $("#a_pagar_"+id).val().replace(/\./g,'');
                compra = {};
                compra.id = id;
                compra.monto = a_pagar;
                compras.push(compra);
    });
    if (compras.length===0){
        return;
    }

    var ordenpago = {};
    ordenpago.compras = compras;
    ordenpago.retencion = $('input[name=rt_iva]:checkbox')[0].checked;
    ordenpago.importeretencion = $("#importe_rt").val();
    ordenpago.concepto = $("#concepto").val();
    ordenpago.total = $("#importe_total").val().replace(/\./g,'');

    $.ajax({
            type: "POST",
            url: context + "/api/v1/compra/nuevaordenpago/",
            data: JSON.stringify(ordenpago),
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
                    setTimeout(function(){
                      location.reload(true);
                    }, 2000);
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

function confirmacionFP(){
    var orden = {};
    $('input[name=slave_check_op]:checked').each(function() {
        var id = $(this).val();
        orden.idordepagocab = id;
    });

    if (listFormaPago.length===0){
       return;
    }

    orden.listFormaPago = listFormaPago;

    $.ajax({
        type: "POST",
        url: context + "/api/v1/compra/confirmarordenpago/",
        data: JSON.stringify(orden),
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
        },
        error: function(e) {
            console.log("ERROR", e);
        },
        done: function(e) {
            console.log("DONE");
        }
    });
}

function buscarOP(){
    $('#tabla > tbody').empty();
    var fechaInicio = $('#fecha').data('daterangepicker').startDate.format('DD/MM/YYYY');
    // fechaInicio = fechaInicio === undefined ? "" : fechaInicio;
     var fechaFin = $('#fecha').data('daterangepicker').endDate.format("DD/MM/YYYY");

    /*var soloPendientes = $('input[name="soloPendientes"]:checked').val();
    soloPendientes = soloPendientes === undefined ? "" : soloPendientes;
    var proveedor = $("#selectProveedor").val();*/
    /*if(fechaInicio!=="" || fechaFin !== ""){
        if(!(fechaInicio!=="" && fechaFin !== "")){
            inputRequired($("#fechaInicio"));
            inputRequired($("#fechaFin"));
            return;
        }
    }*/
     var error = 0;
     error = error + inputRequired($("#fecha"))
     if(error>0){
         return;
     }

    var parametros = "?ini="+fechaInicio+"&fin="+fechaFin;//+"&proveedor="+proveedor;
    $.ajax({
        type : "GET",
        url : context + "/api/v1/compra/obtenerordenpago/" + parametros,
        success : function(data) {
                console.log(data)
                data = JSON.stringify(data);
                var jsonobject = JSON.parse(data);
                if (jsonobject.estado == 'ERROR') {
                    swal("ERROR", jsonobject.error, "error");
                } else if (jsonobject.estado == 'OK') {
                	if(jsonobject.datos.length == 0){
                		swal("Atencion", "No existen datos", "info");
                	}else{
                		$.each(jsonobject.datos, function(index, element) {
                			var id = element.idordenpagocab;
                			
                			var cel1 = '<td id="cel_1" style="vertical-align: middle; text-align: center;"><input type="radio" name="slave_check_op" value="'+id+'"/></td>';
                			var cel2 = '<td id="cel_2" style="vertical-align: middle;">'+id+'</td>';
                			var cel3 = '<td id="cel_3" style="vertical-align: middle;">'+formatFecha(element.fecha)+'</td>';
                			var cel4 = '<td id="cel_4" style="vertical-align: middle;">'+aplicarSeparadorMiles(element.importe)+'</td>';
                			var cel5 = '<td id="cel_5" style="vertical-align: middle;">'+aplicarSeparadorMiles(element.importeretenido)+'</td>';
                			var cel6 = '<td id="cel_6" style="vertical-align: middle;">'+element.usuario.nombreapellido+'</td>';
                			var cel7 = '<td id="cel_7" style="vertical-align: middle;">'+'<a data-toggle="modal" href="#detalle_orden" onclick="verDetalleOrden('+id+')">Ver Detalles</a></td>';
                			$('#tabla > tbody:last-child').append('<tr>'+cel1+cel2+cel3+cel4+cel5+cel6+cel7+'</tr>');
                		});
                		
                		$('input:radio[name=slave_check_op]').change(function() {
                			calcularImporteOP();
                		});
                		
                	}
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

function verDetalleCompra(id){

    table.clear();

    $.ajax({
            type : "GET",
            url : context + "/api/v1/compra/obtener/?id=" + id,
            success : function(data) {
                    console.log(data)
                    var datos = data.datos.compradet;
                    data = JSON.stringify(data);
                    var jsonobject = JSON.parse(data);
                    if (jsonobject.estado == 'ERROR') {
                        swal("ERROR", jsonobject.error, "error");
                    } else if (jsonobject.estado == 'OK') {
                        $.each(datos, function(index, element) {
                                table.rows.add( [ {
                                    "0": element.concepto,
                                    "1": element.precio,
                                    "2": element.cantidad,
                                    "3": element.exenta,
                                    "4": element.ivaimporte,
                                    "5": element.ivaporcentaje,
                                    "6": element.gravada
                                }]).draw();

                                //formato numerico
                                $(".price").mask("000.000.000.000",{reverse: true});
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

function verDetalleOrden(id){

    tableOP.clear();

    $.ajax({
            type : "GET",
            url : context + "/api/v1/compra/obtenerordenpago/?id=" + id,
            success : function(data) {
                    console.log(data)
                    var datos = data.datos;
                    data = JSON.stringify(data);
                    var jsonobject = JSON.parse(data);
                    if (jsonobject.estado == 'ERROR') {
                        swal("ERROR", jsonobject.error, "error");
                    } else if (jsonobject.estado == 'OK') {
                        $.each(datos, function(index, element) {
                                tableOP.rows.add( [ {
                                    "0": formatFecha(element.fecha),
                                    "1": element.comprobante,
                                    "2": element.tipo === "RE" ? "Recibo" : "Factura",
                                    "3": element.importeCompra,
                                    "4": element.importeOrden
                                }]).draw();

                                //formato numerico
                                $(".price").mask("000.000.000.000",{reverse: true});
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

function calcularImporteOP(){
    var total = 0;
    $('input[name=slave_check_op]:checked').each(function() {
            var monto = $(this).parent().parent().find("#cel_4").text().replace(/\./g,'');
            total = total + parseInt(monto);
            var id = $(this).val();
            idordenpagocabAconfirmar = id;
    });
    fp_total_a_pagar = total;
    inicializarModalFormaPago();
}