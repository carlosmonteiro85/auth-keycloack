<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=false displayMessage=false; section>
    <#if section = "title">
        ${msg("loginTitle",(realm.displayName!''))}
    <#elseif section = "form">
        <#if realm.password>
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
                    color: #612F74 !important;
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
                    border: 1px solid #612F74 !important;
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
                <form id="kc-form-login"
                      onsubmit="login.disabled = true; return true;"
                      action="${url.loginAction?keep_after('^[^#]*?://.*?[^/]*', 'r')}" method="post" >
                    <div class="container-fluid">
                        <div class="row centralize">
                            <div class="container-logo">
                                <img src="${url.resourcesPath}/fonts/logo.png"
                                     alt="logo" class="logoIndex img-fluid"/>
                            </div>

                            <div class="col-sm-12 col-md-12">
                                <h3 class="textoMaiorIndex newjune-medium">Portal de Proffy</h3>
                            </div>

                            <div class="col-sm-12 col-md-12">
                                <img class="iconesIndex" src="${url.resourcesPath}/fonts/icones_login.png"
                                     alt="logo"/>
                            </div>

							<div class="col-sm-12 col-md-12">
								<p class="instrucoesIndex">Para acessar o sistema, acesse o Portal do Proffy</p>
							</div>

							<div id="div-form-login-inputs" class="col-sm-12 col-md-12" style="display:none;" >
								<div class="row">
									<div class="offset-md-3 col-sm-12 col-md-3 form-group username-box">
										<label>USUÁRIO</label>
										<#if usernameEditDisabled??>
											<input tabindex="1" id="username"
												   class="upperCase form-control ${properties.kcInputClass!}"
												   name="username" value="${(login.username!'')}"
												   type="text" disabled
												   placeholder="<#if !realm.loginWithEmailAllowed>${msg("username")}<#elseif !realm.registrationEmailAsUsername>${msg("")}<#else>${msg("email")}</#if>"/>
										<#else>
											<input tabindex="1" id="username"
												   class="upperCase form-control ${properties.kcInputClass!}"
												   name="username" value="${(login.username!'')}" type="text"
												   autofocus
												   autocomplete="off"
												   placeholder="<#if !realm.loginWithEmailAllowed>${msg("username")}<#elseif !realm.registrationEmailAsUsername>${msg("")}<#else>${msg("email")}</#if>"/>
										</#if>
									</div>

									<div class="col-sm-12 col-md-3 form-group">
										<label>SENHA</label>
										<input tabindex="2"
											   id="password"
											   class="form-control"
											   name="password"
											   type="password"
											   autocomplete="off"
											   placeholder="${msg("")}"/>
									</div>

								</div>
							</div>

							<div id="div-form-login-btn" class="btn-entrar" style="display:none;">
								<input tabindex="4" class="form-control bradseg-btn"
									   name="login" id="kc-login" type="submit" value="${msg("ENTRAR")}"
									   style="background-color: #612F74 !important; color: white !important"/>
								<#if realm.password && social.providers??>
									<#list social.providers as p>
										<a href="${p.loginUrl}" id="zocial-${p.alias}"
										   class="form-control bradseg-btn">${msg("doLogIn")}
											With ${p.displayName}</a>
									</#list>
								</#if>
							</div>
							
							<div id="loading" class="col-sm-12 col-md-12" style="display: none;">
								<p class="instrucoesIndex"><h5>Processando...</h5></p>
							</div>
							
                            <div class="col-sm-12 col-md-12">
								 <div id="div-login-alert-retorno" class="alert-info" style="display:none;">

                                 </div>
									
                                <#if message?has_content>
                                    <div id="login-alert" class="alert-info">
                                        <span class="kc-feedback-text">${kcSanitize(message.summary)?no_esc}</span>
                                    </div>
                                </#if>
                            </div>
                        </div>
                    </div>
                </form>
                <span class="footerTemplate">© Carlos Monteiro - Todos os direitos reservados - Copyright 2024</span>
                <span class="version-number">Versão: ${properties.versionNumber}</span>
            </div>
			
			<link href="resources/js/script.js" >
			
			<script>
				validaChave('${properties.usuarioAppContexPath}');
			</script>
        </#if>
    </#if>

</@layout.registrationLayout>