<#import "template.ftl" as layout>
<@layout.registrationLayout; section>
    <#if section = "header">
        ${msg("confirmLinkIdpTitle")}
    <#elseif section = "form">
		<style>
               form {
                    display: block;
                    margin-top: 0em;
                    height: inherit;
                }

                *, ::after, ::before {
                    box-sizing: border-box;
                }

                div {
                    display: block;
                }

                body {
                    margin: 0;
                    font-size: 1rem;
                    font-weight: 400;
                    line-height: 1.5;
                    text-align: center;
                    background-color: #fff;
                    color: #555;
                }

                label {
                    display: inline-block;
                    margin-bottom: 0.5rem;
                    font-family: inherit;
                    text-transform: uppercase;
                    line-height: 20px;
                    font-size: 14px;
                    color: #727272;
                    letter-spacing: 1px;
                    font-weight: 400;
                }


                .footerTemplate {
                    font-size: 16px;
                    text-align: center;
                    border-top: 1px solid black;
                    width: inherit;
                    display: block;
                }


                .alert-info {
                    font-size: 16px;
                    margin-top: 10px;
                    color: #721c24;
                    background-color: #f8d7da;
                    border-color: #f5c6cb
                }

                .centralize {
                    text-align: center;
                }

                .container-fluid {
                    width: 100%;
                    padding-right: 15px;
                    padding-left: 15px;
                    margin-right: auto;
                    margin-left: auto;
                    height: inherit;
                }

                .row {
                    display: -webkit-box;
                    display: -ms-flexbox;
                    display: flex;
                    -ms-flex-wrap: wrap;
                    flex-wrap: wrap;
                    margin-right: -15px;
                    margin-left: -15px;
                }

                .instrucoesIndex {
                    font-size: 16px;
                    margin-top: 30px;
                }

                .iconesIndex {
                    padding-top: 25px;
                    padding-bottom: 25px;
                }

                .textoMaiorIndex {
                    font-size: 29px;
                    margin-top: 8px !important;
                }

                .newjune-medium {
                    font-family: inherit;
                }

                .logoIndex {
                    margin-top: 10rem;
                    height: 75px;
                }

                .img-fluid {
                    max-width: 100%;
                }

                .instrucoesIndex {
                    font-size: 16px;
                    margin-top: 30px;
                }

                .username-box {
                    margin-left: 25%;
                }

                .form-group {
                    margin-bottom: 1rem;
                }

                .bradseg-btn {
                    background-color: #f4f4f6;
                    color: #a50021 !important;
                    font-size: 14px !important;
                    font-family: inherit;
                    border-radius: 2px;
                    border: 1px solid #d2d4da;
                    margin-right: 15px;
                    height: 36px;
                    text-align: center;
                    outline: 0;
                    -webkit-box-shadow: 0 1px 2px 0 rgba(0, 0, 0, .5), inset 0 1px 0 rgba(255, 255, 255, .2);
                    box-shadow: 0 1px 2px 0 rgba(0, 0, 0, .5), inset 0 1px 0 rgba(255, 255, 255, .2);
                    transition: all ease-in-out .15s, all ease-in-out .15s;
                    border-radius: 5px;
                    border: 1px solid #a50021 !important;
                }
                .bradseg-btn:hover {
                    cursor: pointer
                }


                .container-logo {
                    position: relative;
                    width: 100%;
                    min-height: 1px;
                    padding-right: 15px;
                    padding-left: 15px;
                }

                .mainContainer {
                    width: 100%;
                    height: 100%;
                }

                .btn-entrar {
                    display: table-row;
                    text-align: center;
                    display: block;
                    margin-right: auto;
                    margin-left: auto;
                    margin-top: 20px;
                    margin-bottom: 30px;


                }

                .upperCase {
                    text-transform: uppercase;
                }

                @media screen and (max-width: 1440px) {
                    .logoIndex {
                        margin-top: 5.5rem;
                        height: 75px;
                    }
                }

                @media screen and (max-width: 920px) {
                    .row {
                        display: flex;
                        align-items: center;
                        flex-direction: column;
                        flex-wrap: wrap;
                        margin-right: -15px;
                        margin-left: -15px;
                    }

                    .username-box {
                        margin-left: 0px;
                    }

                    .btn-entrar {
                        display: table-row;
                        text-align: center;
                        display: block;
                        margin-right: auto;
                        margin-left: auto;
                        margin-top: 20px;
                        margin-bottom: 30px;

                    }
                }

        </style>
		<div class="container-fluid mainContainer" id="tela-principal" >
			<form id="kc-register-form" action="${url.loginAction}" method="post">
				<div class="container-fluid">
                    <div class="row centralize">
						<div class="container-logo">
							<img src="${url.resourcesPath}/fonts/logo.svg" alt="logo" class="logoIndex img-fluid"/>
						</div>
						<div class="col-sm-12 col-md-12">
							<h3 class="textoMaiorIndex newjune-medium">Portal de Cotadores</h3>
						</div>
						<div class="col-sm-12 col-md-12">
							<img class="iconesIndex" src="${url.resourcesPath}/fonts/icones_login.png" alt="logo"/>
						</div>
						<div class="col-sm-12 col-md-12">
							<p class="instrucoesIndex">O usuário informado já existe. Como você quer continuar?</p>
						</div>
						<div id="div-form-login-inputs" class="col-sm-12 col-md-12" >
							<div id="div-form-login-btn" class="btn-entrar" >
								<div class="${properties.kcFormGroupClass!}">
									<button type="submit" class="${properties.kcButtonClass!} ${properties.kcButtonDefaultClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}" name="submitAction" id="updateProfile" value="updateProfile">Revisar informações do perfil</button>
									<button type="submit" class="${properties.kcButtonClass!} ${properties.kcButtonDefaultClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}" name="submitAction" id="linkAccount" value="linkAccount">Vincular à conta existente</button>
								</div>							
							</div>
						</div>	
					</div>
				</div>	
			</form>
			<span class="footerTemplate">© Ebix Latin America - Todos os direitos reservados - Copyright 2023</span>
		</div>	
    </#if>
</@layout.registrationLayout>