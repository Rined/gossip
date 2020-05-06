<#macro page isRegistration=false>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="/static/style.css"/>
        <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/css/bootstrap.min.css"/>
        <script src="https://kit.fontawesome.com/caaa7e48d5.js" crossorigin="anonymous"></script>
        <script src="/webjars/jquery/3.4.1/jquery.min.js"></script>
        <script src="/webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/turbolinks/5.2.0/turbolinks.js"></script>
        <#if isRegistration>
            <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        </#if>
        <title>Just talk</title>
    </head>
    <body>
    <#include "navbar.ftlh">
    <div class="container mt-5">
        <#nested>
    </div>
    </body>
    </html>
</#macro>