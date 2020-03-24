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

    <#include "macros/messageEdit.ftlh" />

    <#include "macros/messageList.ftlh" />

</@b.page>