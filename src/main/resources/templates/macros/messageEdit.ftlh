<a class="btn btn-primary" data-toggle="collapse" href="#newMessage" role="button" aria-expanded="false"
   aria-controls="collapseExample">
    Message editor
</a>
<div class="collapse <#if message??>show</#if>" id="newMessage">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="messageField" class="sr-only">Message</label>
                <input type="text" id="messageField" name="text"
                       class="form-control ${(textError??)?string('is-invalid', '')}"
                       value="<#if message??>${message.text}</#if>"
                       placeholder="Input message..."/>
                <#if textError??>
                    <div class="invalid-feedback">${textError}</div>
                </#if>
            </div>
            <div class="form-group">
                <label for="tagField" class="sr-only">Message</label>
                <input type="text" id="tagField" name="tag" class="form-control" placeholder="Input tag..."
                       value="<#if message??>${message.tag}</#if>"/>
                <#if tagError??>
                    <div class="invalid-feedback">${tagError}</div>
                </#if>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file" id="fileField"/>
                    <label class="custom-file-label" for="fileField">Choose file...</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <#if message??>
                <input type="hidden" name="id" value="${message.id}"/>
            </#if>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Save message</button>
            </div>
        </form>
    </div>
</div>