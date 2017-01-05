$(document).ready(function() {
    getAuthors("https://api.github.com/repos/decorators-squad/eva/contributors");
});
function getAuthors(contributorsUrl) {
	$.support.cors = true;
	$.ajax({
		type : "GET",
		url : contributorsUrl,
		dataType : 'json',
		contentType : "application/json; charset=utf-8",
		headers : {
			Accept : "application/json; charset=utf-8"
		},
		success : function(contributors, status) {
			if(status == "success") {
			    $("#nogithub").hide();
                $("#githubok").show();
				for(var index in contributors) {
				    var authorTableEntry =
					"<tr><td><img class='avatar' src='" + contributors[index].avatar_url + "'/></td><td><a target='_blank' href='" + contributors[index].html_url + "'>" + contributors[index].login + "</a></td><td>" + contributors[index].contributions + "</td></tr>";
					$('#authorsTable').append(authorTableEntry);
				}
			} else {
			    $("#nogithub").show();
			    $("#githubok").hide();
			}
		},
		statusCode: {
			404: function() {
			    $("#nogithub").show();
			    $("#githubok").hide();
			},
			500: function() {
			    $("#nogithub").show();
			    $("#githubok").hide();
			}
		},
		error : function(e) {
			$("#nogithub").show();
			$("#githubok").hide();
		}
	});
}
