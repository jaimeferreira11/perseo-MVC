
function pojoLogin(idusuario, username, idempresa, idsucursal, iddeposito){
	
	var pojo = "{";
	pojo += '"idusuario":' + idusuario 
	pojo += ',"username": "' +username + '"'
	pojo += ',"empresa":' + pojoEmpresa(idempresa, "")
	pojo += ',"sucursal":' + pojoSucursal(idsucursal, "")
	pojo += ',"deposito":' + pojoDeposito(iddeposito, "")
	pojo += "}";	

	return pojo;
	
}

function pojoUsuario(idusuario, username, activated, idempresa, idversion, idperfil){
	
	var perfiles = "";
	for (var i = 0; i < idperfil.length; i++) {
		
		
		if(i == (idperfil.length - 1)){
			perfiles+= '{"perfil":' + pojoPerfil(idperfil[i] , "")+ ","
			perfiles+= '"usuario":{"idusuario": '+ idusuario+'}}' 
		}else{
			perfiles+= '{"perfil":' + pojoPerfil(idperfil[i] , "")+ ","
			perfiles+= '"usuario":{"idusuario": '+ idusuario+'}}' + ","
	
		}
	}
	
	var pojo = "{";
	pojo += '"idusuario":' + idusuario 
	pojo += ',"username": "' +username + '"'
	pojo += ',"activated":' + activated 
	pojo += ',"empresa":' + pojoEmpresa(idempresa, "")
	pojo += ',"versionencuesta" :' + pojoVersionEncuesta2(idversion , "")
	pojo += ',"perfilusuario" : ['+perfiles + ']'
		
	pojo += "}";	
	
	//console.log(pojo)
	
	return pojo;

}

function pojoUsuario2(idusuario, username, idempresa, idversion){
	
	var pojo = "{";
	pojo += '"idusuario":' + idusuario 
	pojo += ',"username": "' +username + '"'
	pojo += ',"empresa":' + pojoEmpresa(idempresa, "")
	pojo += ',"versionencuesta" :' + pojoVersionEncuesta2(idversion , "")	
	pojo += "}";	
	
	//console.log(pojo)
	
	return pojo;

}

function pojoEmpresa(idempresa, descripcion){
	
	var pojo = "{";
	pojo += '"idempresa":' + idempresa
	pojo += ',"descripcion" : "' + descripcion +'"'
		
	pojo += "}";
	
	return pojo;
}

function pojoDeposito(iddeposito, descripcion){
	
	var pojo = "{";
	pojo += '"iddeposito":' + iddeposito
	pojo += ',"descripcion" : "' + descripcion +'"'
		
	pojo += "}";
	
	return pojo;
}

function pojoSucursal(idsucursal, descripcion){
	
	var pojo = "{";
	pojo += '"idsucursal":' + idsucursal
	pojo += ',"descripcion" : "' + descripcion +'"'
		
	pojo += "}";
	
	return pojo;
}

function pojoPerfil(idperfil, descripcion, idempresa){
	
	var pojo = "{";
	pojo += '"idperfil":' + idperfil
	pojo += ',"descripcion" :"'+ descripcion +'"'
	pojo += ',"empresa":' + pojoEmpresa(idempresa, "")	
	pojo += "}";
	
	return pojo;
}

function pojoPerfil(idperfil, descripcion, estado, idempresa){
	
	var pojo = "{";
	pojo += '"idperfil":' + idperfil
	pojo += ',"descripcion" :"'+ descripcion.toUpperCase() +'"'
	pojo += ',"estado":' + estado 	
	pojo += ',"empresa":' + pojoEmpresa(idempresa, "")	
	pojo += "}";
	
	return pojo;
}



function pojoClase(idclase, descripcion , url, estado, idempresa){
	
	var pojo = "{";
	pojo += '"idclase":' + idclase
	pojo += ',"descripcion":"' + descripcion +'"'
	pojo += ',"url":"' + url+'"'
	pojo += ',"estado":' + estado 	
	pojo += ',"empresa":' +pojoEmpresa(idempresa, "")
	pojo += "}";
	
	return pojo;
}

function pojoMenu(idmenu, descripcion , tipo, orden, idclase, menuanterior, idperfil, estado){
	
	var pojo = "{";
	pojo += '"idmenu":' + idmenu
	pojo += ',"descripcion":"' + descripcion +'"'
	pojo += ',"tipo":"' + tipo +'"'
	pojo += ',"orden":' + orden
	pojo += ',"idclase":' + idclase
	pojo += ',"menuanterior":' + menuanterior
	pojo += ',"idperfil":' + idperfil
	pojo += ',"estado":' + estado 	
	pojo += "}";
	
	return pojo;
}


