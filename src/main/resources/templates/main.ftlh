<#import "macros/base.ftlh" as b>
<#import "macros/login.ftlh" as l>

<@b.page>
    <div>
        <@l.logout />
    </div>
    <h3>Create message:</h3>
    <div>
        <form method="post" enctype="multipart/form-data">
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
            <div>
                <input type="file" name="file">
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
            <div>
                <#if message.filename??>
                    <img src="/img/${message.filename}" alt="${message.filename}">
                </#if>
            </div>
        </div>
    <#else>
        <div>No messages</div>
    </#list>
</@b.page>