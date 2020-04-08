<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Emoji Movie Game</title>
</head>
<body>
<h2>
    <#list clue.clueComponents as clueComponent>
        <#if clueComponent.emoji?has_content>
            ${clueComponent.emoji.character}
            <#else>
            X
        </#if>
    </#list>
</h2>
<h1>${clue.film.title}</h1>

</body>
</html>