$(document).ready(function() {
    var pageFromUrl = getPageFromUrl();
    if(pageFromUrl == 'authors') {
        goToAuthorsPage();
    } else if (pageFromUrl == 'usage') {
        goToUsagePage();
    } else if(pageFromUrl == 'releases') {
        goToReleasesPage();
    }
    $("#homeButton").on("click", function() {
        goToHomePage();
    });

    $("#usageButton").on("click", function() {
        goToUsagePage();
    });

    $("#releasesButton").on("click", function() {
        goToReleasesPage();
    });

    $("#authorsButton").on("click", function() {
        goToAuthorsPage();
    });
});

function getPageFromUrl()
{
    var paramsMap = [], param;
    var params = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < params.length; i++)
    {
        param = params[i].split('=');
        if(param[0]=='page') {
            return param[1];
        }
    }
    return 'intro';
}

function goToHomePage() {
    $("#authors").hide();
    $("#releases").hide();
    $("#usage").hide();
    $("#intro").show();
    $("#usageButton").removeClass("selected");
    $("#releasesButton").removeClass("selected");
    $("#authorsButton").removeClass("selected");

    $("#homeButton").addClass("selected");
    $("#homeButton img").attr("src","images/icons_light/home_selected.png");
    $("#usageButton img").attr("src","images/icons_light/usage.png");
    $("#releasesButton img").attr("src","images/icons_light/releases.png");
    $("#authorsButton img").attr("src","images/icons_light/authors.png");
}

function goToAuthorsPage() {
    $("#intro").hide();
    $("#releases").hide();
    $("#usage").hide();
    $("#authors").show();
    $("#usageButton").removeClass("selected");
    $("#releasesButton").removeClass("selected");
    $("#homeButton").removeClass("selected");

    $("#authorsButton").addClass("selected");
    $("#homeButton img").attr("src","images/icons_light/home.png");
    $("#usageButton img").attr("src","images/icons_light/usage.png");
    $("#releasesButton img").attr("src","images/icons_light/releases.png");
    $("#authorsButton img").attr("src","images/icons_light/authors_selected.png");
}

function goToUsagePage() {
    $("#authors").hide();
    $("#releases").hide();
    $("#intro").hide();
    $("#usage").show();
    $("#homeButton").removeClass("selected");
    $("#releasesButton").removeClass("selected");
    $("#authorsButton").removeClass("selected");

    $("#usageButton").addClass("selected");
    $("#homeButton img").attr("src","images/icons_light/home.png");
    $("#usageButton img").attr("src","images/icons_light/usage_selected.png");
    $("#releasesButton img").attr("src","images/icons_light/releases.png");
    $("#authorsButton img").attr("src","images/icons_light/authors.png");
}

function goToReleasesPage() {
    $("#authors").hide();
    $("#intro").hide();
    $("#usage").hide();
    $("#releases").show();
    $("#usageButton").removeClass("selected");
    $("#homeButton").removeClass("selected");
    $("#authorsButton").removeClass("selected");

    $("#releasesButton").addClass("selected");
    $("#homeButton img").attr("src","images/icons_light/home.png");
    $("#usageButton img").attr("src","images/icons_light/usage.png");
    $("#releasesButton img").attr("src","images/icons_light/releases_selected.png");
    $("#authorsButton img").attr("src","images/icons_light/authors.png");
}