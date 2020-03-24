<#import "macros/base.ftlh" as b>

<@b.page>
    <#if isCurrentUser>
        <#include "macros/messageEdit.ftlh" />
    </#if>
    <#include "macros/messageList.ftlh" />
</@b.page>