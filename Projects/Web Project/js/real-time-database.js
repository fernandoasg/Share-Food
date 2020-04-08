//var nome = document.getElementById('');

/*preCadastrarBotao.addEventListener('click', function (){

});*/


function salvarPreCadastro(){
    console.log("asdasdasdds")
    var nome = document.getElementById('nomePre').value
    var eDoador = document.getElementById('radioDoador').checked
    var email = document.getElementById('emailPre').value

    var data = new Date()
    
    db.collection("PreCadastro").add({
        Doador: eDoador,
        Email: email,
        Nome: nome
    })
    .then(function(docRef) {
        console.log("Document written with ID: ", docRef.id);
        $('#preCadastro').modal('hide');
    })
    .catch(function(error) {
        console.error("Error adding document: ", error);
    });
}