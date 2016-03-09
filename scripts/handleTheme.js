$(document).ready(function() {
	var themeFilePath = getThemeCookie("amihaiemilTheme");
	if(themeFilePath != "") {
		changeCSSTheme(themeFilePath, 1);
	}
	$("#themeSelect").on("change", function() {
		setThemeCookie("amihaiemilTheme",$("#themeSelect").val(),365);
		changeCSSTheme($("#themeSelect").val(), 1);
	});
	
});

function setThemeCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}
function getThemeCookie(cookieName) {
    var name = cookieName + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
    }
    return "";
}
function changeCSSTheme(newCssFile, oldCssFileIndex) {

    var oldlink = document.getElementsByTagName("link").item(oldCssFileIndex);

    var newlink = document.createElement("link");
    newlink.setAttribute("rel", "stylesheet");
    newlink.setAttribute("type", "text/css");
    newlink.setAttribute("href", newCssFile);

    document.getElementsByTagName("head").item(0).replaceChild(newlink, oldlink);
}