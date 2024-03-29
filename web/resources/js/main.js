window.onload = function(){
    // BURGER
    const burger = document.querySelector('.burger');
    const bars = document.querySelector('.burger .icon-menu');
    const ex = document.querySelector('.burger .icon-cross');
    const linksMobile = document.querySelector("nav .linksMobile");

    if(burger){
        burger.addEventListener('click', function(){
            if(ex.style.display == '' || ex.style.display == 'none'){
                ex.style.display = 'inline';
                bars.style.display = 'none';
                linksMobile.style.display = 'flex';
            }else{
                ex.style.display = 'none';
                bars.style.display = 'inline';
                linksMobile.style.display = 'none';
            }
        });
    }


    //BUSCAR
    const buscar = document.querySelector('#buscar');
    const category = document.querySelector('#category');
    const reset = document.querySelector('#reset');
    const tableHeadRow = document.querySelector('#table01 thead tr');

    function filter(value){
        let texto = value;
        texto = texto.normalize('NFD').replace(/([aeio])\u0301|(u)[\u0301\u0308]/gi,"$1$2").normalize();
        let contenido = RegExp(texto, 'i');
        let filas = document.querySelectorAll("#table01 tbody tr");

        for(i = 0; i < filas.length; i++){
            let f = filas[i];
            let filtro = f.textContent.normalize('NFD').replace(/([aeio])\u0301|(u)[\u0301\u0308]/gi,"$1$2").normalize();
            filtro.search(contenido) == '-1' ? f.style = 'display: none;' : f.style = '';
        }
    }
    function filterSelect(value, index){
        let texto = value;
        texto = texto.normalize('NFD').replace(/([aeio])\u0301|(u)[\u0301\u0308]/gi,"$1$2").normalize();
        let contenido = RegExp(texto, 'i');
        let filas = document.querySelectorAll("#table01 tbody tr");

        for(i = 0; i < filas.length; i++){
            let f = filas[i].cells[index];
            let filtro = f.textContent.normalize('NFD').replace(/([aeio])\u0301|(u)[\u0301\u0308]/gi,"$1$2").normalize();
            filtro.search(contenido) == '-1' ? filas[i].style = 'display: none;' : filas[i].style = '';
        }
    }

    if(buscar){
        buscar.addEventListener('input', function(){
            filter(buscar.value);
        });

        buscar.parentElement.addEventListener('submit', function(e){
            e.preventDefault();
        });
    }
    if(category){
        let index = -1;
        for(i = 0; i < tableHeadRow.cells.length; i++){ //identifica columna del select
            let aux = tableHeadRow.cells[i];
            if(aux.textContent === category.getAttribute('title')){
                if(index == -1){
                    index = i;
                }
            }
        }
        category.addEventListener('change', function(){
            if(index != -1 && category.value != 0){
                filterSelect(category.value, index);
            }else{
                filter('');
            }
        });
    }
    if(reset){
        reset.parentElement.addEventListener('reset', function(){
            filter('');
        });
    }


    // SLIDER
    let images = ['img1.jpg','img2.jpg','img3.jpg','img4.jpg'];
    const next = document.querySelector(".slider .next");
    const prev = document.querySelector(".slider .prev");
    let cont = -1;
    let time = 5000;

    const slider = document.querySelector(".slider");
    const sliderimg = document.querySelector(".slider .slider-img");
    const sliderTitle = document.querySelector(".slider-info h3");
    const sliderContent = document.querySelector(".slider-info p");
    const sliderLink = document.querySelector(".slider-info a");

    function Noticia(){
        let img;
        let titulo;
        let contenido;
        let link;
        this.Noticia = function(i, t, c, l){
            img = i;
            titulo = t;
            contenido = c;
            link = l;
        }
        Object.defineProperty(this, 'img', {
            get : function(){return img;},
            set : function(value){img = value;}
        });
        Object.defineProperty(this, 'titulo', {
            get : function(){return titulo;},
            set : function(value){titulo = value;}
        });
        Object.defineProperty(this, 'contenido', {
            get : function(){return contenido;},
            set : function(value){contenido = value;}
        });
        Object.defineProperty(this, 'link', {
            get : function(){return link;},
            set : function(value){link = value;}
        });
    }

    let noticia1 = new Noticia();
    noticia1.Noticia("img1.jpg", "Sólo hoy: ¡Consultas gratis!", "Ven hoy a nuestra clínica, entre 8 y 12 de la mañana, y podrás tener una consulta gratis.", "nothing.com");
    let noticia2 = new Noticia();
    noticia2.Noticia("img2.jpg", "Otra noticia", "Pues esta es otra noticia", "nothing.com");
    let noticia3 = new Noticia();
    noticia3.Noticia("img3.jpg", "Sólo hoy: ¡Consultas gratis!", "Ven hoy a nuestra clínica, entre 8 y 12 de la mañana, y podrás tener una consulta gratis.", "nothing.com");
    let noticia4 = new Noticia();
    noticia4.Noticia("img4.jpg", "Otra noticia", "Pues esta es otra noticia", "nothing.com");

    let noticiasArray = [noticia1, noticia2, noticia3, noticia4];

    if(next && prev){
        function changeNew(contador){
            slider.style = `background-image: url('resources/img/${noticiasArray[contador].img}');`;
            sliderimg.setAttribute("src", "resources/img/" +  noticiasArray[contador].img);
            sliderTitle.textContent = noticiasArray[contador].titulo;
            sliderContent.textContent = noticiasArray[contador].contenido;
            sliderLink.setAttribute("href", noticiasArray[contador].link);
        }

        function changeImage(){
            if(cont < noticiasArray.length - 1){
                cont++;
            }else{
                cont = 0;
            }
            changeNew(cont);
        }
    
    
        next.addEventListener('click', nextImage);
        function nextImage(){
            if(cont < images.length - 1){
                cont++;
            }else{
                cont = 0;
            }
            changeNew(cont);
        }
        
        prev.addEventListener('click', prevImage);
        function prevImage(){
            if(cont > 0){
                cont--;
            }else{
                cont = images.length - 1;
            }
            changeNew(cont);
        }
    
        function timeout(){
            changeImage();
            setTimeout(timeout, time);
        }
        timeout();
    }

    // const padres = document.querySelectorAll(".padre");
    // const hijos = document.querySelectorAll("nav .linksMobile p.padre + div");
    // for(i=0; i<padres.length; i++){
    //     var hijo = hijos[i];        
    //     padres[i].addEventListener('click', function(){
    //         console.log(padres[i].nextElementSibling);
    //     });
    // }
}