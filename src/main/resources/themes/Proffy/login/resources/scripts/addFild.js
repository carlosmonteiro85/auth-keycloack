
//procurar o botão
document.querySelector("#add-time")

//quando clicar no botão
//adicionar um ouvidor de evento do tipo clique de clonar  
.addEventListener('click', cloneField)
//criar um metodo
function cloneField(){
    //schedule-item é o que ira ser clonado
   const newFildContainer =  document.querySelector('.schedule-item').cloneNode(true)
    //obtem os campos
   const filds = newFildContainer.querySelectorAll('input')
    //limpando os campos
    filds.forEach(function(i){
        i.value = ""
    })
    //duplicar o campo em schedule-items, e criando um filho
    document.querySelector('#schedule-items').appendChild(newFildContainer)
}



