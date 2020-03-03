<#import "macros/base.ftlh" as b>
<#import "macros/login.ftlh" as l>

<@b.page>
    <div>
        <@l.logout />
    </div>
    <h3>Create message:</h3>
    <div>
        <form method="post">
            <div>
                <label>
                    Message: <br/>
                    <input type="text" name="text" placeholder="Input message...">
                </label>
            </div>
            <div>
                <label>
                    Tag:<br/>
                    <input type="text" name="tag" placeholder="Input tag...">
                </label>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Add</button>
        </form>
    </div>
    <h3>Message list:</h3>
    <form method="get" action="/main">
        <label>
            Фильтр: <br/>
            <input type="text" name="filter" value="${filter!}">
        </label>
        <input type="submit" value="Find">
    </form>
    <#list messages as message>
        <div>
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
            <strong>${message.authorName}</strong>
        </div>
    <#else>
        <div>No messages</div>
    </#list>
</@b.page>