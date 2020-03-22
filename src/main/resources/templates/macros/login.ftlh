<#include "security.ftlh">

<#macro login path btnText isRegistration>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label for="username" class="col-sm-2 col-form-label">User Name :</label>
            <div class="col-sm-6">
                <input id="username" class="form-control" aria-describedby="usernameHelp" placeholder="Enter username"
                       type="text" name="username"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="pwd" class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-6">
                <input id="pwd" class="form-control" aria-describedby="pwdHelp" placeholder="Enter password"
                       type="password" name="password"/>
            </div>
        </div>
        <#if isRegistration>
            <div class="form-group row">
                <label for="email" class="col-sm-2 col-form-label">Email:</label>
                <div class="col-sm-6">
                    <input id="email" class="form-control" aria-describedby="pwdHelp" placeholder="Enter email"
                           type="email" name="email"/>
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