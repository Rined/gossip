<#import "macros/base.ftlh" as b>

<@b.page>
    <h3>Change profile: <u>${username}</u></h3>
    <div>${message!}</div>
    <form method="post">
        <div class="form-group row">
            <label for="pwd" class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-6">
                <input id="pwd" class="form-control" aria-describedby="pwdHelp" placeholder="Enter password"
                       type="password" name="password"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="email" class="col-sm-2 col-form-label">Email:</label>
            <div class="col-sm-6">
                <input id="email" class="form-control" aria-describedby="pwdHelp" placeholder="Enter email"
                       type="email" name="email" value="${email!''}"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mb-2">Save</button>
    </form>
</@b.page>