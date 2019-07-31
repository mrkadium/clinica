const Campo = function(input, tipo, msg, limiteSuperior, limiteInferior, soloNumeros, soloLetras){
    this.input = tipo == 'radio' ? document.querySelectorAll('input[name=campo6]') : document.querySelector("#"+input);
    this.i = document.querySelector('label[for=' + input + '] + i');
    this.errorMsg = (msg == undefined || msg == '') ? 'Este campo debe llenarse' : msg;
    this.form = document.querySelector('#formulario');
    this.tipo = (tipo == undefined || tipo == '') ? '' : tipo;
    this.limiteSuperior = (limiteSuperior == undefined || limiteSuperior == '') ? 256 : limiteSuperior;
    this.limiteInferior = (limiteInferior == undefined || limiteInferior == '') ? 1 : limiteInferior;
    this.soloNumeros = (soloNumeros == undefined || soloNumeros == '') ? false : soloNumeros;
    this.soloLetras = (soloLetras == undefined || soloLetras == '') ? false : soloLetras;
    
    let iE = document.querySelector('label[for=' + input + '] + i');
    let foo = "";

    // this.Campo = function(input, msg){
    //     this.input = document.querySelector("#"+input);
    //     this.i = document.querySelector('label[for=' + input + '] + i');
    //     this.errorMsg = msg;
    //     this.form = this.input.parentElement.parentElement;
    // }
    // this.Campo2 = function(input){
    //     this.input = document.querySelector("#"+input);
    //     this.i = document.querySelector('label[for=' + input + '] + i');
    //     this.errorMsg = 'Este campo no debe quedar vacío';
    //     this.form = this.input.parentElement.parentElement;
    // };

    this.alertar = function(){
        let eval = true;
        if(this.tipo != 'radio'){
            let valor = this.input.value;
            if(valor.length == 0 || valor == 0){
                foo = this.errorMsg;
                eval = false;
            }
            if(this.tipo == 'texto' && (valor.length < this.limiteInferior || valor.length > this.limiteSuperior)){
                foo = `Este campo debe tener entre ${this.limiteInferior} y ${this.limiteSuperior} caracteres`;
                eval = false;
            }
            if(this.tipo == 'lupa' && (valor.length == 0 || valor == 0)){
                foo = 'Debe elegir un elemento dando clic en la lupa';
                eval = false;
            }
            if(this.tipo == 'select' && valor == 0){
                foo = 'Debe seleccionar un elemento de la lista';
                eval = false;
            }
        }
        if(this.tipo == 'radio'){
            eval = false;
            for(e of this.input){
                if(e.checked){
                    eval = true;
                }
            }
            foo = 'Debe escoger un elemento';
        }

        if(eval){
            foo = '';
        }
        location.href = '#body';
        this.i.textContent = foo;
        return eval;
    }
    // this.input.addEventListener('input', function(){
    //     iE.textContent = '';
    // });
}