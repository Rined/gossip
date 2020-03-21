<#import "macros/base.ftlh" as b>
<#import "macros/login.ftlh" as l>

<@b.page>
    <h3>Create your account</h3>
    <div style="color: red">${message!}</div>
    <@l.login "/registration" "Sign up" true/>
</@b.page>