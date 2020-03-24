<#include "security.ftlh" />
<div class="card-columns">
    <#list messages as message>
        <div class="card my-3" style="width: 18rem;">
            <div>
                <#if message.filename??>
                    <img src="/img/${message.filename}" alt="${message.filename}" class="card-img-top">
                </#if>
            </div>
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