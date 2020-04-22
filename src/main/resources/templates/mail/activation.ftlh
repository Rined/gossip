<html lang="en">
<head>
    <style>
        .container {
            padding: 2px 16px;
            margin-left: 5%;
        }
    </style>
    <title>Mail</title>
</head>
<body>
<div class="card">
    <div class="container">
        <h3><b>Dear ${user.username}</b></h3>
        <div>Welcome to Gossip!</div>
        <p>
            Please, visit next <a href="http://localhost:8080/activate/${user.activationCode}">link</a> for activation!
        </p>
        <div>
            <p>Best regards : <br> <b>Gossip</b></p>
        </div>
    </div>
</div>
</body>
</html>