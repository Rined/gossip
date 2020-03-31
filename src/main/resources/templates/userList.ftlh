<#import "macros/base.ftlh" as b>

<@b.page>
    <h3>List of users:</h3>
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
        <#list users as user>
            <tr>
                <td class="table-column">
                    <a href="/users/${user.id}"><img src="edit.svg" alt="edit"></a>
                </td>
                <td class="table-column">
                    <form action="/users/${user.id}/kick" method="post">
                        <button class="none-border-btn" type="submit"><img src="kick.svg" alt="kick"></button>
                    </form>
                </td>
                <td class="table-column">
                    ${user.username}
                </td>
                <td class="table-column">
                    <#list user.roles as role>
                        ${role} <#sep>,
                    </#list>
                </td>
                <td class="table-column">
                    online
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</@b.page>