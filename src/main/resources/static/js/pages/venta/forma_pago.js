$(document).ready(function(){
});


function fp_enmascarar(){
    $("#fp_monto").mask("000.000.000.000",{reverse: true});
    $("#fp_saldo").mask("000.000.000.000",{reverse: true});
    $("#fp_importe").mask("000.000.000.000",{reverse: true});
    $("#fp_diferencia").mask("000.000.000.000",{reverse: true});
}

function fp_desenmascarar(){
    $("#fp_monto").unmask();
    $("#fp_saldo").unmask();
    $("#fp_importe").unmask();
    $("#fp_diferencia").unmask();
}


function fp_add() {
	fp_desenmascarar();
	//calcular monto
	var importe = parseFloat($("#fp_importe").val());
	var saldo = parseFloat($("#fp_saldo").val());
	var totalCobrar = parseFloat($("#fp_monto").val());
	var fp_cheque = $("#fp_cheque").val();

	saldo = saldo + importe;

    var cuentasOk = false;
    var idcuenta = $("#fp_selectCajacuenta").val();
    if(idcuenta === null || idcuenta === undefined || idcuenta === "0" || idcuenta === "Seleccione"){
        swal("", "No se puede grabar, elija una cuenta", "warning");
        return;
    }

    var option = $("#fp_selectCajacuenta").find(":selected").text();
    var esCheque = option.indexOf("EFECTIVO") === -1 && option !== "Seleccione";
    if (esCheque){
        var idbanco = $("#fp_selectBanco").val();
        var bancoOk = idbanco !== null && idbanco !== undefined && idbanco !== "0";
        if (bancoOk && fp_cheque){
            cuentasOk = true;
        } else {
            swal("", "No se puede grabar, elija un banco y complete el número de cheque", "warning");
            return;
        }
    }else{
        cuentasOk = true;
    }

    if (saldo <= totalCobrar && cuentasOk && importe > 0) {
	    $("#fp_saldo").val(saldo);
		$("#fp_monto").val(totalCobrar);
		$("#fp_diferencia").val((totalCobrar - saldo));
		$("#fp_importe").val(0);
	}
	else {
	    fp_enmascarar();
	    return;
	}

	fp_enmascarar();
	
    // llenar la tabla plan caja
    var table =  document.getElementById("tablaFormaPago");
    var row = table.insertRow(1);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(2);
    var cell4 = row.insertCell(3);
    var cell5 = row.insertCell(4);
    var cell6 = row.insertCell(5);
    var cell7 = row.insertCell(6);
    var cell8 = row.insertCell(7);

    var element0 = document.createElement("button");
    element0.type = "button";
    element0.className = "btn btn-danger";
    element0.innerHTML = 'x';
    element0.onclick = function() {
        delete_fila(this, row);
    }
    
 // separar la cadena
	var index = idcuenta.indexOf("|");
	var efectivo = idcuenta.substring(0, 1)
	var id = idcuenta.substring((index+1));

	console.log( idcuenta + " efectivo: " + efectivo + " id: " + id)
    cell1.innerHTML = id;
    cell2.innerHTML = option;
    cell3.innerHTML = aplicarSeparadorMiles(importe);
    cell5.appendChild(element0);
    cell4.innerHTML = fp_cheque;
    cell6.innerHTML = id;
    cell7.innerHTML = siNuloCero(idbanco);
    cell8.innerHTML = efectivo;

    cell6.style.display = "none";
    cell7.style.display = "none";
    cell8.style.display = "none";

    var fp = {}
    var cc = {};
    var opc = {};
    opc.idordenpagocab = idordenpagocabAconfirmar;
    cc.idcajacuenta = id;
    fp.cajacuenta = cc;
    fp.ordenpagocab = opc;
    fp.importe = importe;
    listFormaPago.push(fp)

    $("#fp_cheque").val("0");
    $('#fp_selectCajacuenta').val("0").trigger('chosen:updated');
    $('#fp_selectBanco').val("0").trigger('chosen:updated');

    if(saldo==totalCobrar){
        $("#fp_grabar").attr('disabled', false);
    }else{
        $("#fp_grabar").attr('disabled', true);
    }
}

//eliminar filas de la tabla planCuentas
function delete_fila(row, fila) {
	fp_desenmascarar();
	var monto = parseFloat(fila.cells[2].innerHTML.replace(/\./g, ''));
    var saldo = parseFloat($("#fp_saldo").val());
    var totalCobrar = parseFloat($("#fp_monto").val());
    saldo = saldo - monto;

    $("#fp_saldo").val(saldo);
    $("#fp_diferencia").val((totalCobrar - saldo));

	row.closest('tr').remove();
	fp_enmascarar();
}


//hablitar campos para cheques
function habilitarCheques(habilitar) {
	$('#fp_cheque').val("");

	if (habilitar) {
		$('#fp_selectBanco').attr('disabled', false);
        $('#fp_cheque').attr('disabled', false);
        $('#fp_selectBanco').trigger('chosen:updated');
	} else {
		$('#fp_selectBanco').attr('disabled', true);
        $('#fp_cheque').attr('disabled', true);
        $('#fp_selectBanco').val("").trigger('chosen:updated');
	}

}

function inicializarModalFormaPago(){
    $("#fp_monto").val(fp_total_a_pagar);
    $("#fp_importe").val(fp_total_a_pagar);
    $("#fp_diferencia").val(fp_total_a_pagar);

   /* $.ajax({
            type : "GET",
            url : context + "/api/v1/cajacuenta/",
            success : function(data) {
                console.log(data)
                var datos = data.datos;
                data = JSON.stringify(data);
                var jsonobject = JSON.parse(data);
                if (jsonobject.estado == 'ERROR') {
                    swal("ERROR", jsonobject.error, "error");
                } else if (jsonobject.estado == 'OK') {
                    if(jsonobject.datos.length > 0){
                        $.each(jsonobject.datos, function(index, element) {
                            var o = $("<option/>", {
                                value : element.idcajacuenta,
                                text : element.descripcion
                            });
                            $("#fp_selectCajacuenta").append(o);
                        });
                    }
                }
                $(document).ajaxStop($.unblockUI);
                $('#fp_selectCajacuenta').trigger('chosen:updated');
            },
            error: function(e) {
                console.log("ERROR", e);
                $(document).ajaxStop($.unblockUI);
            },
            done: function(e) {
                console.log("DONE");
            }
    });

    $.ajax({
                type : "GET",
                url : context + "/api/v1/banco/",
                success : function(data) {
                    console.log(data)
                    var datos = data.datos;
                    data = JSON.stringify(data);
                    var jsonobject = JSON.parse(data);
                    if (jsonobject.estado == 'ERROR') {
                        swal("ERROR", jsonobject.error, "error");
                    } else if (jsonobject.estado == 'OK') {
                        if(jsonobject.datos.length > 0){
                            $.each(jsonobject.datos, function(index, element) {
                                var o = $("<option/>", {
                                    value : element.idbanco,
                                    text : element.descripcion
                                });
                                $("#fp_selectBanco").append(o);
                            });
                        }
                    }
                    $(document).ajaxStop($.unblockUI);
                    $('#fp_selectBanco').trigger('chosen:updated');
                },
                error: function(e) {
                    console.log("ERROR", e);
                    $(document).ajaxStop($.unblockUI);
                },
                done: function(e) {
                    console.log("DONE");
                }
    });*/

    $('#fp_selectCajacuenta').chosen().change(function () {
        var option = this[this.selectedIndex].text;
        var esCheque = option.indexOf("EFECTIVO") === -1;
    	habilitarCheques(esCheque);
    });

    fp_enmascarar();

    $("#fp_grabar").click(function(){
            confirmacionFP();
    });
}