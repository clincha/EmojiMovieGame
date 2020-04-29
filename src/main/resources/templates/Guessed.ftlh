<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="css/guessed.css"/>

    <title>Movie Emoji Quiz</title>
</head>
<body>

<div class="container-fluid">
    <div class="row mt-3">
        <div class="col"></div>
        <div class="col-4">
            <#if correct>
                <h1 class="guess-result">Correct</h1>
            <#else >
                <h1 class="guess-result">Wrong</h1>
            </#if>
        </div>
        <div class="col"></div>
    </div>
    <div class="row mt-3">
        <div class="col-6 film-content-container">
            <img id="film-poster" class="film-content" src="https://image.tmdb.org/t/p/w342/${film.posterPath}"
                 alt="${film.title} poster"/>
            <p id="film-overview" class="film-content">${film.overview}</p>
        </div>
        <div id="family-tree-container" class="col-6"></div>
    </div>
    <div class="row mt-3">
        <div class="col"></div>
        <div id="next-clue-button-container" class="col-4">
            <button autofocus class="btn btn-primary" onclick="location.href='/clue'">Next Clue</button>
        </div>
        <div class="col"></div>
    </div>
</div>

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