//! apps.js
$(document).ready(function() {
	$('#appData').DataTable({
		"ajax": {
			"type": "GET",
			"url": serviceUrl + "/users",
			"dataSrc": ""
		},
		"columns": [
			{ "data": "id" },
			{ "data": "name" },
			{ "data": "city" },
			{
				"data": "",
				"defaultContent": "<a><i> &#xE254;</i></a>"
			},
			{
				"data": "",
				"defaultContent": "<a><i> &#xE03B; </i> </a>"
			},
			{
				"data": "",
				"defaultContent": "<a><i> &#xE872; </i> </a>"
			}
		],
		columnDefs: [
			{ className: "material-icons edit", "targets": [3] },
			{ className: "material-icons add", "targets": [4] },
			{ className: "material-icons delete", "targets": [5] }

		],

	});


	$(document).on("click", ".edit", function() {
		$(this).parents("tr").find("td:not(:first-child,:nth-child(4),:nth-child(6),:nth-child(5),:nth-child(7),:last-child)").each(function() {
			$(this).html('<input type="text" class="form-control" value="' + $(this).text() + '">');
			console.log("textsio");
		});
		$(this).parents("tr").find(".add, .edit").toggle();
		$(".add-new").attr("disabled", "disabled");
	});


	// Delete row on delete button click
	$(document).on("click", ".delete", function() {
		$(this).parents("tr").remove();
		$(".add-new").removeAttr("disabled");

		var memberId = $(this).parents("tr").find("td:nth-child(1)	").text();

		$.ajax({
			url: serviceUrl + "users/" + memberId,
			type: 'DELETE',
			async: false,
			success: function(result) {
				console.log("Deleted");
				$('.success').message('Deleting Book Success: ' + memberId + ' | ');
			},
			error: function(e) {
				console.log(e);
				$('.error').message('Deleting Book Failed: ' + memberId + ' | ');
			}
		});

	});

	// Add row on add button click
	$(document).on("click", ".add", function() {
		var empty = false;
		var input = $(this).parents("tr").find('input[type="text"]');
		var count = 0;
		var id = $(this).parents("tr").find("td:first-child").text();


		input.each(function() {
			if (!$(this).val()) {
				$(this).addClass("error");
				empty = true;
			} else {
				$(this).removeClass("error");
			}
		});
		$(this).parents("tr").find(".error").first().focus();
		if (!empty) {
			var name = null;
			var city = null;
			
			input.each(function() {
				$(this).parent("td").html($(this).val());

				console.log($(this).val());
				count = count + 1;


				switch (count) {
					case 1:
						name = $(this).val();
						break;
					case 2:
						// code block
						city = $(this).val();
						break;
		

				}

			});


			var obj = {};
			if (id == "") {
				obj['name'] = name;
				obj['city'] = city;
				
				$.ajax({
				url: serviceUrl + "users",
				type: 'post',
				async: false,
				contentType: "application/json",
				data: JSON.stringify(obj),
				success: function(result) {

					console.log("Created "+result.id);
					
					$(this).parents("tr").find(".add, .edit").toggle();
				    $(this).parents("tr").find("td:first-child").text(result.id);

					$('.edit').css("display", "inline-block");
					$('.add').css("display", "none");

					$(".add-new").removeAttr("disabled");
					$('.success').message('Created Book Success: ' + result.id + ' | ');
window.location.reload();

					
				},
				error: function(e) {
					console.log(e);
					$('.error').message('Created Book Failed: ' + number + ' | ');
				}
			});
				
				
				
				
				
			} else {
				obj['name'] = name;
				obj['city'] = city;

$.ajax({
				url: serviceUrl + "users/"+id,
				type: 'put',
				async: false,
				contentType: "application/json",
				data: JSON.stringify(obj),
				success: function(result) {

					console.log("Updated");
					$(this).parents("tr").find(".add, .edit").toggle();
					$('.edit').css("display", "inline-block");
					$('.add').css("display", "none");

					$(".add-new").removeAttr("disabled");
					$('.success').message('Updating App Success: ' + name + ' | ');
window.location.reload();

					
				},
				error: function(e) {
					console.log(e);
					$('.error').message('Updating App Failed: ' + number + ' | ');
				}
			});



			}

			
		}
	});


	$(".add-new").click(function() {
		$(this).attr("disabled", "disabled");
		var index = $("table tbody tr:first-child").index();
		console.log("############"+index)
		var row = '<tr>' +
			'<td></td>' +
			'<td><input type="text" class="form-control" name="name" id="appId"></td>' +
			'<td><input type="text" class="form-control" name="faculty" id="code"></td>' +

			'<td> <a class="add"><i class="material-icons"> &#xE03B; </i></i></a> <a class="edit"><i class="material-icons"> &#xE254; </i></a> <a class="delete"><i class="material-icons"> &#xE872; </i></a></td>' +
			'</tr>';
		$("table").prepend(row);

		$("table tbody tr").eq(index).find(".add, .edit").toggle();
		//$('[data-toggle="tooltip"]').tooltip();
	});



});