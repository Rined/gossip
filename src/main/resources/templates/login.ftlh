<#import "macros/base.ftlh" as b>
<#import "macros/login.ftlh" as l>

<@b.page>
    <h3>Sign in to Just-Talk</h3>
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
    <#if message??>
        <div class="alert alert-${messageType}" role="alert">
            ${message}
        </div>
    </#if>
    <@l.login "/login" "Sign in" false/>
</@b.page>