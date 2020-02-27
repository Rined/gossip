<#import "macros/base.ftlh" as b>
<#import "macros/login.ftlh" as l>

<@b.page>
<h1>Login page</h1>
<@l.login "/login"/>
<a href="/registration">Add new user</a>
</@b.page>