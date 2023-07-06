<!DOCTYPE html> <!-- HTML5 -->
<html lang="pt_br"> <!-- Atributo de idioma-->

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Proffy | Login</title>

    <!--icone na barra do navegador-->
    <link rel="shortcut icon" href="${url.resourcesPath}/images/favicon.png" type="image/png">

    <!--Para lincar o arquivo CSS no HTML-->
    <link rel="stylesheet" href="${url.resourcesPath}/styles/main.css">
    <link rel="stylesheet" href="${url.resourcesPath}/styles/partials/page-landing.css">
    <link rel="stylesheet" href="${url.resourcesPath}/styles/partials/header.css">
    <link rel="stylesheet" href="${url.resourcesPath}/styles/partials/forms.css">
    <link rel="stylesheet" href="${url.resourcesPath}/styles/partials/give-classes.css">

    <link
        href="https://fonts.googleapis.com/css2?family=Archivo:wght@400;700&amp;family=Poppins:wght@400;600&amp;display=swap"
        rel="stylesheet">

</head>

<body id="page-landing" style="background-color: #04D361;">

    <div id="container">
        <div class="logo-container">
            <img src="${url.resourcesPath}/images/logo.svg" alt="Proffy">
        </div>
        <div class="hero-image">

        </br>
        <div id="create-class" style="margin-top: 40px;">
            <h1 style="color: blueviolet;">Login</h1>
        </br>
                <div class="input-block">
                    <label for="username" style="color: blueviolet;" >Username</label>
                    <input type="text" name="username" id="username" required placeholder="Digite seu username">
                </div>
                </br>
                <div class="input-block" >
                    <label for="password" style="color: blueviolet;">Senha</label>
                    <input name="password" id="password" type="password" required placeholder="Digite sua Senha">
                </div>

                <div class="buttons-container" style="margin: 30px 100px 50px;">
                    <a class="study" onclick="realizarLogin()" style="width: 150px;">
                        <img src="${url.resourcesPath}\images\icons\cadeado.svg" alt="Estudar">
                        Entrar
                    </a>
                    <a class="study" href="http://localhost:5500/src/views/student.html" style="width: 150px;">
                        <img src="${url.resourcesPath}\images\icons\study.svg" alt="Cadastrar" >
                        Cadastrar
                    </a>
                </div>
                <input type="hidden" name="grant_type" value="password">
                <input type="hidden" name="client_id" value="proffy">
            </div>
        </div>
    </div>
    <script >
    function realizarLogin() {
    	  var url = 'http://localhost:8082/realms/Proffy/protocol/openid-connect/token';
    	  var params = new URLSearchParams();
    	  
    	  const username = document.getElementById('username');
    	  const password = document.getElementById('password');
    	  
    	  params.append('grant_type', 'password');
    	  params.append('client_id', 'seu_client_id');
    	  params.append('username', username);
    	  params.append('password', password);

    	  fetch(url, {
    	    method: 'POST',
    	    headers: {
    	      'Content-Type': 'application/x-www-form-urlencoded'
    	    },
    	    body: params
    	  })
    	  .then(response => response.json())
    	  .then(data => {
    	    var token = data.access_token;
    	    localStorage.setItem('token', token);
    	    console.log('Token armazenado no localStorage:', token);
    	    console.log(localStorage.getItem('token'))
    	    alert(locallocalStorage.getItem('token'))
//     	    	window.location.href = 'http://localhost:5500/src/views/index.html';
    	  })
    	  .catch(error => {
    		  alert(error)
    	    console.error('Erro ao realizar o login:', error);
    	  });
    	}
    </script>
</body>
</html>