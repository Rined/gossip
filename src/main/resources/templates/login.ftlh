<#import "macros/base.ftlh" as b>
<#import "macros/login.ftlh" as l>

<@b.page>
<h3>Sign in to Just-Talk</h3>
<div>${message!}</div>
<@l.login "/login" "Sign in" false/>
</@b.page>