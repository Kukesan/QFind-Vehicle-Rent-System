//! apps.js
$(document).ready(function() {
	$('#appData').DataTable({
		"ajax": {
			"type": "GET",
			"url": serviceUrl + "/bookings",
			"dataSrc": ""
		},
		"columns": [
			{ "data": "id" },
			{ "data": "vehicle.id" },
			{ "data": "vehicle.name" },
			{ "data": "user.id" },
			{ "data": "user.name" },
			{ "data": "bookedDate" },
			{ "data": "returnDate" },

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
			{ className: "material-icons edit", "targets": [7] },
			{ className: "material-icons add", "targets": [8] },
			{ className: "material-icons delete", "targets": [9] }

		],

	});


	$(document).on("click", ".edit", function() {
		$(this).parents("tr").find("td:not(:first-child,:nth-child(3),:nth-child(5),:nth-child(6),:nth-child(8),:nth-child(9),:last-child)").each(function() {
			$(this).html('<input type="text" class="form-control" value="' + $(this).text() + '">');
		});
		$(this).parents("tr").find("td:nth-child(7)").each(function() {
			$(this).html('  <input type="checkbox" id="checks1" name="checks"> <label for="checks1">Returned</label>');
		});
		$(this).parents("tr").find(".add, .edit").toggle();
		$(".add-new").attr("disabled", "disabled");
	});


	// Delete row on delete button click
	$(document).on("click", ".delete", function() {
		$(this).parents("tr").remove();
		$(".add-new").removeAttr("disabled");

		var borrowId = $(this).parents("tr").find("td:nth-child(1)	").text();

		$.ajax({
			url: serviceUrl + "bookings/" + borrowId,
			type: 'DELETE',
			async: false,
			success: function(result) {
				console.log("Deleted");
				$('.success').message('Deleting Book Success: ' + borrowId + ' | ');
			},
			error: function(e) {
				console.log(e);
				$('.error').message('Deleting Book Failed: ' + borrowId + ' | ');
			}
		});

	});

	// Add row on add button click
	$(document).on("click", ".add", function() {
		var empty = false;
		var input = $(this).parents("tr").find('input[type="text"]');
		var count = 0;
		var id = $(this).parents("tr").find("td:first-child").text();
		var returnOrnot = !($(this).prop('checked')==true);

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
			var bookId = null;
			var memId = null;

			input.each(function() {
				$(this).parent("td").html($(this).val());

				console.log($(this).val());
				count = count + 1;


				switch (count) {
					case 1:
						bookId = $(this).val();
						break;
					case 2:
						// code block
						memId = $(this).val();
						break;
				}
			});


			var obj = {};
			if (id == "") {
				obj['bookId'] = bookId;
				obj['memberId'] = memId;
				

				$.ajax({
					url: serviceUrl + "bookings",
					type: 'post',
					async: false,
					contentType: "application/json",
					data: JSON.stringify(obj),
					success: function(result) {

						console.log("Created " + result.id);

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
						$('.error').message('Created Book Failed: ' + bookId + ' | ');
					}
				});

			} else {
				obj['bookId'] = bookId;
				obj['memberId'] = memId;
				obj['returnOrnot'] = returnOrnot;

				console.log("#######################"+returnOrnot);

				$.ajax({
					url: serviceUrl + "bookings/" + id,
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
		console.log("############" + index)
		var row = '<tr>' +
			'<td></td>' +
			'<td><input type="text" class="form-control" name="bookId" id="bookId"></td>' +
			'<td></td>' +
			'<td><input type="text" class="form-control" name="memId" id="memId"></td>' +
			'<td></td>' +
			'<td></td>' +
			'<td></td>' +

			'<td> <a class="add"><i class="material-icons"> &#xE03B; </i></i></a> <a class="edit"><i class="material-icons"> &#xE254; </i></a> <a class="delete"><i class="material-icons"> &#xE872; </i></a></td>' +
			'</tr>';
		$("table").prepend(row);

		$("table tbody tr").eq(index).find(".add, .edit").toggle();
		//$('[data-toggle="tooltip"]').tooltip();
	});
});