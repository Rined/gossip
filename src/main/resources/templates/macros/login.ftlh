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
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </div>
        <button type="submit" class="btn btn-primary mb-2">${btnText}</button>
        <#if !isRegistration><a class="btn btn-primary mb-2" href="/registration">Sign up</a></#if>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="submit" value="Sign Out"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</#macro>