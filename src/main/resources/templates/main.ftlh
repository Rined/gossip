<#import "macros/base.ftlh" as b>

<@b.page>
    <form method="get" action="/main" class="form-inline mb-3">
        <div class="form-group mr-3">
            <label for="searchField" class="sr-only">Filter</label>
            <input type="text" class="form-control" id="searchField" name="filter" value="${filter!}"
                   placeholder="Search by tag">
        </div>
        <button type="submit" class="btn btn-primary">Search</button>
    </form>

    <a class="btn btn-primary" data-toggle="collapse" href="#newMessage" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Add new message
    </a>
    <div class="collapse" id="newMessage">
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="messageField" class="sr-only">Message</label>
                    <input type="text" id="messageField" name="text" class="form-control"
                           placeholder="Input message...">
                </div>
                <div class="form-group">
                    <label for="tagField" class="sr-only">Message</label>
                    <input type="text" id="tagField" name="tag" class="form-control" placeholder="Input tag...">
                </div>
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file" id="fileField">
                        <label class="custom-file-label" for="fileField">Choose file...</label>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Add</button>
                </div>
            </form>
        </div>
    </div>

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
                    <i>${message.tag}</i>
                </div>
                <div class="card-footer text-muted">
                    ${message.authorName}
                </div>
            </div>
        <#else>
            <div>No messages</div>
        </#list>
    </div>
</@b.page>