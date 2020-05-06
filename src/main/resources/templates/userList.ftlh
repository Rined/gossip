<#import "macros/base.ftlh" as b>
<#import "macros/pager.ftlh" as p />

<@b.page>
    <h3>List of users:</h3>
    <@p.pager url page/>
    <table class="table">
        <thead>
        <tr>
            <th class="table-column-icon-th">Edit</th>
            <th class="table-column-icon-th">Kick</th>
            <th class="table-column-th">Name</th>
            <th class="table-column-th">Role</th>
            <th class="table-column-th">Status</th>
        </tr>
        </thead>
        <tbody>
        <#list page.content as user>
            <tr>
                <td class="table-column">
                    <a href="/users/${user.id}"><img src="edit.svg" alt="edit"></a>
                </td>
                <td class="table-column">
                    <form action="/users/${user.id}/kick" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <button class="none-border-btn" type="submit"><img src="kick.svg" alt="kick"></button>
                    </form>
                </td>
                <td class="table-column">
                    <a href="/user-messages/${user.id}">${user.username}</a>
                </td>
                <td class="table-column">
                    <#list user.roles as role>
                        ${role} <#sep>,
                    </#list>
                </td>
                <td class="table-column">
                    <#if user.active>
                        <img src="online.svg" alt="online"> online
                        <#else>
                        <img src="offline.svg" alt="offline"> offline
                    </#if>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</@b.page>