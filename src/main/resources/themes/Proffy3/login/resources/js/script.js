function login(a,b) {
    let passwordField = document.getElementById('password')
    let usernameField = document.getElementById('username')
    let buttonSubmit = document.getElementById('kc-login')

    usernameField.setAttribute('value',a)
    passwordField.setAttribute('value', b)
	buttonSubmit.click();
}

function validaChave(urlUsuarioAppContexPath) {
	let chave = obterChaveUnica();
	if(chave != null && chave.length > 0){
		document.getElementById("loading").style = 'display:block';
		
		fetch(urlUsuarioAppContexPath + "/acesso/" + obterChaveUnica())
			.then(data => { return data.json()})
			.then(res => {
				//console.log(res)
				if(res.status == 200 || res.status == undefined){
					if(res.username == undefined){
						apresentaMensagemRetorno("Acesso não autorizado");
					}else{
						login(res.username, res.password)
					}
				}else{
					let msg = "Acesso não autorizado";
					if(res.detail != null && res.detail != undefined){
						msg = res.detail;
					}
					
					apresentaMensagemRetorno(msg);
				}
			})
			.catch( error => {
				apresentaMensagemRetorno("Acesso não autorizado");
			} )		
	}else{
		document.getElementById("div-form-login-inputs").style = 'display:block';
		document.getElementById("div-form-login-btn").style = 'display:block';
	}
}

function obterChaveUnica() {
    const url = window.location.href
    let result = url.matchAll(/chaveUnica%3D(.*?)&/g)
    return Array.from(result, x => x[1])
}

function apresentaMensagemRetorno(msg) {
    document.getElementById("loading").style = 'display:none';
	let divAlerta = document.getElementById("div-login-alert-retorno")
	divAlerta.setAttribute('style', 'display:block')
	divAlerta.innerHTML = msg;		
}