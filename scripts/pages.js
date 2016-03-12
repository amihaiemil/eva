$(document).ready(function() {
    $("#homeButton").on("click", function() {
        $("#authors").hide();
        $("#releases").hide();
        $("#usage").hide();
        $("#intro").show();
        $("#usageButton").removeClass("selected");
        $("#releasesButton").removeClass("selected");
        $("#authorsButton").removeClass("selected");

        $(this).addClass("selected");
        $("#homeButton img").attr("src","images/icons_light/home_selected.png");
        $("#usageButton img").attr("src","images/icons_light/usage.png");
        $("#releasesButton img").attr("src","images/icons_light/releases.png");
        $("#authorsButton img").attr("src","images/icons_light/authors.png");
    });

    $("#usageButton").on("click", function() {
        $("#authors").hide();
        $("#releases").hide();
        $("#intro").hide();
        $("#usage").show();
        $("#homeButton").removeClass("selected");
        $("#releasesButton").removeClass("selected");
        $("#authorsButton").removeClass("selected");

        $(this).addClass("selected");
        $("#homeButton img").attr("src","images/icons_light/home.png");
        $("#usageButton img").attr("src","images/icons_light/usage_selected.png");
        $("#releasesButton img").attr("src","images/icons_light/releases.png");
        $("#authorsButton img").attr("src","images/icons_light/authors.png");
    });

    $("#releasesButton").on("click", function() {
        $("#authors").hide();
        $("#intro").hide();
        $("#usage").hide();
        $("#releases").show();
        $("#usageButton").removeClass("selected");
        $("#homeButton").removeClass("selected");
        $("#authorsButton").removeClass("selected");

        $(this).addClass("selected");
        $("#homeButton img").attr("src","images/icons_light/home.png");
        $("#usageButton img").attr("src","images/icons_light/usage.png");
        $("#releasesButton img").attr("src","images/icons_light/releases_selected.png");
        $("#authorsButton img").attr("src","images/icons_light/authors.png");
    });

    $("#authorsButton").on("click", function() {
        $("#intro").hide();
        $("#releases").hide();
        $("#usage").hide();
        $("#authors").show();
        $("#usageButton").removeClass("selected");
        $("#releasesButton").removeClass("selected");
        $("#homeButton").removeClass("selected");

        $(this).addClass("selected");
        $("#homeButton img").attr("src","images/icons_light/home.png");
        $("#usageButton img").attr("src","images/icons_light/usage.png");
        $("#releasesButton img").attr("src","images/icons_light/releases.png");
        $("#authorsButton img").attr("src","images/icons_light/authors_selected.png");
    });
});