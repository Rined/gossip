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
            <div class="card-footer text-muted container">
                <div class="row">
                    <a class="col align-self-center"
                       href="/user-messages/${message.author.id}">${message.authorName}</a>
                    <a class="col align-self-center" href="/messages/${message.id}/like">
                        <#if message.meLiked>
                            <i class="fas fa-heart"></i>
                        <#else>
                            <i class="far fa-heart"></i>
                        </#if>
                        ${message.likes}
                    </a>
                    <#if message.author.id == currentUserId>
                        <a href="/user-messages/${message.author.id}?message=${message.id}" class="col btn btn-primary">
                            Edit
                        </a>
                    </#if>
                </div>
            </div>
        </div>
    <#else>
        <div>No messages</div>
    </#list>
</div>