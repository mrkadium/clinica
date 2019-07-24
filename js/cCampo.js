const Campo = function(input, msg){
    this.input = document.querySelector("#"+input);
    this.i = document.querySelector('label[for=' + input + '] + i');
    this.errorMsg = (msg == undefined || msg == '') ? 'Este campo debe llenarse' : msg;
    this.form = this.input.parentElement.parentElement;
    let foo = 'Error';
    let iE = document.querySelector('label[for=' + input + '] + i');

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
        if(this.input.value.length == 0 || this.input.value == 0){
            this.i.textContent = this.errorMsg;
            location.href = '#body';
            return false;
        }else{
            this.i.textContent = '';
            return true;
        }
    }
    this.input.addEventListener('input', function(){
        iE.textContent = '';
    });
}