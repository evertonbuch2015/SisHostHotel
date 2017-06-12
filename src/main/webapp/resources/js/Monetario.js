function mascara(o,f){ 
    v_obj=o 
    v_fun=f 
    setTimeout("execmascara()",1) 
} 

function execmascara(){ 
    v_obj.value=v_fun(v_obj.value) 
} 

function moeda(v){ 
     
    v=v.replace(/(\d{1})(\d{15})$/,"$1.$2") // coloca ponto antes dos ultimos digitos 
    v=v.replace(/(\d{1})(\d{11})$/,"$1.$2") // coloca ponto antes dos ultimos 11 digitos 
    v=v.replace(/(\d{1})(\d{8})$/,"$1.$2") // coloca ponto antes dos ultimos 8 digitos 
    v=v.replace(/(\d{1})(\d{5})$/,"$1.$2") // coloca ponto antes dos ultimos 5 digitos 
    
    if (v.indexOf(",") == -1) {//teste para saber se colocou virgula,se não colocou insere dois digitos para a seguir colocar a virgula.
    	v= v + "00"
	}
    
    v=v.replace(/\D/g,""); // permite digitar apenas numero
    
    v=v.replace(/(\d{1})(\d{1,2})$/,"$1,$2") // coloca virgula antes dos ultimos 2 digitos 
    return v; 
}