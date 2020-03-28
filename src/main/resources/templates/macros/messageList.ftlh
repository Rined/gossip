<#include "security.ftlh" />
<#import "pager.ftlh" as p />

<@p.pager url page/>
<div class="card-columns" id="message-list">
    <#list page.content as message>
        <div class="card my-3" style="width: 18rem;" data-id="${message.id}">
            <#if message.filename??>
                <div>
                    <img src="/img/${message.filename}" alt="${message.filename}" class="card-img-top"/>
                </div>
            </#if>
            <div class="m2">
                <span>${message.text}</span>
                <br/>
                <i>#${message.tag}</i>
            </div>
            <div class="card-footer text-muted">
                <a href="/user-messages/${message.author.id}">${message.authorName}</a>
                <#if message.author.id == currentUserId>
                    <a href="/user-messages/${message.author.id}?message=${message.id}" class="btn btn-primary">
                        Edit
                    </a>
                </#if>
            </div>
        </div>
    <#else>
        <div>No messages</div>
    </#list>
</div>
<@p.pager url page/>