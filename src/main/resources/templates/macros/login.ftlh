<#include "security.ftlh">

<#macro login path btnText isRegistration>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label for="username" class="col-sm-2 col-form-label">User Name :</label>
            <div class="col-sm-6">
                <input id="username" aria-describedby="usernameHelp" placeholder="Enter username"
                       value="<#if user??>${user.username}</#if>"
                       class="form-control ${(usernameError??)?string('is-invalid', '')}"
                       type="text" name="username"/>
                <#if usernameError??>
                    <div class="invalid-feedback">${usernameError}</div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label for="pwd" class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-6">
                <input id="pwd" aria-describedby="pwdHelp" placeholder="Enter password"
                       class="form-control ${(passwordError??)?string('is-invalid', '')}"
                       type="password" name="password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">${passwordError}</div>
                </#if>
            </div>
        </div>
        <#if isRegistration>
            <div class="form-group row">
                <label for="passwordConfirmation" class="col-sm-2 col-form-label">Password:</label>
                <div class="col-sm-6">
                    <input id="passwordConfirmation" aria-describedby="pwdHelp" placeholder="Retype password"
                           class="form-control ${(passwordConfirmationError??)?string('is-invalid', '')}"
                           type="password" name="passwordConfirmation"/>
                    <#if passwordConfirmationError??>
                        <div class="invalid-feedback">${passwordConfirmationError}</div>
                    </#if>
                </div>
            </div>

            <div class="form-group row">
                <label for="email" class="col-sm-2 col-form-label">Email:</label>
                <div class="col-sm-6">
                    <input id="email" aria-describedby="pwdHelp" placeholder="Enter email"
                           class="form-control ${(emailError??)?string('is-invalid', '')}"
                           value="<#if user??>${user.email}</#if>"
                           type="email" name="email"/>
                    <#if emailError??>
                        <div class="invalid-feedback">${emailError}</div>
                    </#if>
                </div>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mb-2">${btnText}</button>
        <#if !isRegistration><a class="btn btn-primary mb-2" href="/registration">Sign up</a></#if>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <#if know>
            <button type="submit" class="btn btn-primary mb-2">Sign Out</button>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</#macro>