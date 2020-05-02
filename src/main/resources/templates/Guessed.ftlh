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
    <link rel="stylesheet" href="css/Treant.css"/>
    <link rel="stylesheet" href="css/perfect-scrollbar.css"/>
    <link rel="stylesheet" href="css/treant-custom.css">

    <title>Movie Emoji Quiz</title>
</head>
<body>

<div class="container-fluid">
    <div class="row mt-3">
        <div class="col">
            <#if correct>
                <h1 id="guess-result-correct" class="guess-result">Correct</h1>
            <#else >
                <h1 id="guess-result-wrong" class="guess-result">Wrong</h1>
            </#if>
        </div>
    </div>
    <div class="row mt-3">
        <div class="col-md-4"></div>
        <div id="next-clue-button-container" class="col-md-4">
            <button autofocus class="btn btn-primary btn-block" onclick="location.href='/clue'">Next Clue</button>
        </div>
        <div class="col-md-4"></div>
    </div>
    <div class="row mt-3">
        <div class="col-md-6">
            <h2 class="section-header">Correct film</h2>
            <div class="film-content-container">
                <img id="film-poster" class="film-content" src="https://image.tmdb.org/t/p/w342/${film.posterPath}"
                     alt="${film.title} poster"/>
                <p id="film-overview" class="film-content">${film.overview}</p>
            </div>
        </div>
        <div class="col-md-6">
            <h2 class="section-header">Clue family tree</h2>
            <div id="family-tree-container"></div>
        </div>
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
<script src="js/vendor/raphael.js"></script>
<script src="js/Treant.js"></script>
<script>
    let nodeStructure = ${clueFamilyTree?no_esc}
</script>
<script src="js/clueHistoryTree.js"></script>
</body>
</html>