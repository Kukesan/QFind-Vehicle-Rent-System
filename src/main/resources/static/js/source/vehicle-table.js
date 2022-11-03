//! apps.js
$(document).ready(function() {
	$('#appData').DataTable({
		"ajax": {
			"type": "GET",
			"url": serviceUrl + "/books",
			"dataSrc": ""
		},
		"columns": [
			{ "data": "id" },
			{ "data": "number" },
			{ "data": "name" },
			{ "data": "owner" },
			{ "data": "availability" },
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
			{ className: "material-icons edit", "targets": [5] },
			{ className: "material-icons add", "targets": [6] },
			{ className: "material-icons delete", "targets": [7] }

		],

	});


	$(document).on("click", ".edit", function() {
		$(this).parents("tr").find("td:not(:first-child,:nth-child(8),:nth-child(6),:nth-child(5),:nth-child(7),:last-child)").each(function() {
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

		var bookId = $(this).parents("tr").find("td:nth-child(1)	").text();

		$.ajax({
			url: serviceUrl + "books/" + bookId,
			type: 'DELETE',
			async: false,
			success: function(result) {
				console.log("Deleted");
				$('.success').message('Deleting Book Success: ' + bookId + ' | ');
			},
			error: function(e) {
				console.log(e);
				$('.error').message('Deleting Book Failed: ' + bookId + ' | ');
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
			var number = null;
			var name = null;
			var owner = null;
			var availability = null;
			input.each(function() {
				$(this).parent("td").html($(this).val());

				console.log($(this).val());
				count = count + 1;


				switch (count) {
					case 1:
						number = $(this).val();
						break;
					case 2:
						// code block
						name = $(this).val();
						break;
					case 3:
						owner = $(this).val();
						break;
					case 4:
						availability = $(this).val();
						break;

				}

			});


			var obj = {};
			if (id == "") {
				obj['number'] = number;
				obj['name'] = name;
				obj['owner'] = owner;
				obj['availability'] = availability;
				
				$.ajax({
				url: serviceUrl + "books",
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
					$('.success').message('Created Book Success: ' + number + ' | ');
window.location.reload();

					
				},
				error: function(e) {
					console.log(e);
					$('.error').message('Created Book Failed: ' + number + ' | ');
				}
			});
				
				
				
				
				
			} else {
				obj['number'] = number;
				obj['name'] = name;
				obj['owner'] = owner;
				obj['availability'] = availability;

$.ajax({
				url: serviceUrl + "books/"+id,
				type: 'put',
				async: false,
				contentType: "application/json",
				data: JSON.stringify(obj),
				success: function(result) {

					console.log("Upadated");
					$(this).parents("tr").find(".add, .edit").toggle();
					$('.edit').css("display", "inline-block");
					$('.add').css("display", "none");

					$(".add-new").removeAttr("disabled");
					$('.success').message('Updating App Success: ' + number + ' | ');
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
			'<td><input type="text" class="form-control" name="number" id="appId"></td>' +
			'<td><input type="text" class="form-control" name="title" id="code"></td>' +
			'<td><input type="text" class="form-control" name="owner" id="hosting"></td>' +
			'<td></td>' +

			'<td> <a class="add"><i class="material-icons"> &#xE03B; </i></i></a> <a class="edit"><i class="material-icons"> &#xE254; </i></a> <a class="delete"><i class="material-icons"> &#xE872; </i></a></td>' +
			'</tr>';
		$("table").prepend(row);

		$("table tbody tr").eq(index).find(".add, .edit").toggle();
		//$('[data-toggle="tooltip"]').tooltip();
	});



});