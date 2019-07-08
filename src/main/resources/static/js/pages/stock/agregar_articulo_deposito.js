var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
$(document).ready(function(){
	
//	$(".numerico").mask("000.000.000.000",{reverse: true});
	$('.numerico').priceFormat({
		prefix : '',
		centsSeparator : ',',
		thousandsSeparator : '.',
		centsLimit : 0,
	});
	
	//inicializar los check y radiobutton con el plugins Ichecks.js
	$('.i-checks').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
    });

});


//onclick del boton cerrar
$("#add_cancelar").click(function(){
	limpiarAdd();
 });

 function limpiarAdd(){

	$("#idarticulodep").val("0");
 	$("#idarticulo").val("0");
 	$("#codigo").val("");
 	$("#descripcion").val("");
 	$("#cantidads").val("0");
 	$("#cantidadm").val("0");
 	$("#cantidadb").val("0");
 	$("#lote").val("0");
 	$("#precioc").val("0");
 	//$("#preciov").val("0");
 	$('#estado').iCheck('check');

 }




//onclick del boton aceptar del buscador
$("#add_aceptar").click(function(){
	 var error = 0;
	// error = inputMayor0($("#precioc")) + inputMayor0($("#preciov"))
	 if(error === 0){		 
		 grabarArticulo()
	 }
});
