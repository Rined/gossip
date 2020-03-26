<#import "macros/base.ftlh" as b>

<@b.page>
    <h3>User editor</h3>
    <form action="/user" method="post">
        <label>
            Username:
            <input type="text" value="${user.username}" name="username"/>
        </label>
        <br/>
        <#list roles as role>
            <label>
                <input type="checkbox" name="${role}" ${user.roles ? seq_contains(role) ? string("checked", "")}/>
                ${role}
            </label>
        </#list>
        <input type="hidden" value="${user.id}" name="id"/>
        <input type="hidden" value="${_csrf.token}" name="_csrf"/>
        <br/>
        <button type="submit">Save</button>
    </form>
</@b.page>