var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
var login;

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

    $("#fecha").mask("99/99/9999");
    $("#primervencimiento").mask("99/99/9999");
    $("#montosolicitado").mask("000.000.000.000",{reverse: true});
});

function grabar(){
    console.log("Boton Grabar presionado");
}

function imprimir(){
    console.log("Boton Imprimir presionado");
}

function addCliente(){
    console.log("Boton Agregar Cliente presionado");
}