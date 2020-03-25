<#import "macros/base.ftlh" as b>

<@b.page>
    <h3>${userChannel.username}</h3>
    <div>${type}</div>
    <ul class="list-group">
        <#list users as user>
            <li class="list-group-item">
                <a href="/user-messages/${user.id}">${user.getUsername()}</a>
            </li>
        </#list>
    </ul>
</@b.page>