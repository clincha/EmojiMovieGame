<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="css/clue.css">

    <title>Movie Emoji Quiz</title>
</head>
<body>
<form method="post" action="/guess">
    <div class="container-fluid">
        <div class="row mt-3">
            <div class="col-12">
                <h1 id="clue-title">Guess which movie these emoji represent</h1>
            </div>
        </div>
        <div class="row">
            <div class="col"></div>
            <div class="col centred-container">
                <span class="badge badge-info mx-1">Generation ${clue.generation}</span>
                <span class="badge badge-warning mx-1">Computer Generated</span>
            </div>
            <div class="col"></div>
        </div>
        <div class="row mt-5">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <h1 id="clue">
                    <#list clue.clueComponents as clueComponent>
                        <#if clueComponent.emoji?has_content>
                            ${clueComponent.emoji.character}
                        </#if>
                    </#list>
                </h1>
            </div>
            <div class="col-md-3"></div>
        </div>
        <div class="row mt-5">
            <div class="col-md-3"></div>
            <#list options as option>
                <div class="col-md-2 centred-container">
                    <button <#if option?index = 1>autofocus</#if> name="option" type="submit"
                            class="movie-option-button btn btn-primary btn-block"
                            value="${option.id?c}">${option.title}</button>
                </div>
            </#list>
            <div class="col-md-3"></div>
        </div>
    </div>
    <input name="clueId" type="hidden" value="${clue.id?c}">
</form>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
</body>
</html>