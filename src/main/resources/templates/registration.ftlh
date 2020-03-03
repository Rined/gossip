<#import "macros/base.ftlh" as b>
<#import "macros/login.ftlh" as l>

<@b.page>
    <h1>Add new user</h1>
    <div style="color: red">${message!}</div>
    <@l.login "/registration" "Sign up"/>
</@b.page>