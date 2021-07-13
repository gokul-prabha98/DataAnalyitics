var dataTableSource = null;
$( document ).ready(function(e) {
	$.fn.dataTable.ext.errMode = 'none';
	loadDataSource();
    
});

function loadDataSource(e)
{
	$.ajax({
		type: 'POST',
		url : 'load-data-list',
		success: function(data)
		{
			var response = $.parseJSON(data);
			if(response.responseData == "-1" )
			{
				window.location.replace("index.jsp");
				return;
			}
			if(response.isErrorExist != true)
			{
				if(response.responseData != null)
					$.each(response.responseData, function (key, value) {
						$('#data-source-list').append('<a class="datalist w3-bar-item w3-button" fid='+key+'>'+value+'</a>');
					});
			}
			else
			{
				if(response.responseData == "-1")
				{
					window.location.replace("home.jsp");
				}
				alert("can't load data source");
			}
		}
	});
}

$('#btn-save').click(function(e)
		{
	e.preventDefault();
	var form = $('.form-file-upload')[0];
	var data = new FormData(form);
	var fileName = $('#source-name').val();
	$.ajax({
		type: 'POST',
		url : 'upload-data?filename='+fileName,
		enctype :'multipart/form-data',
		data:data ,
		contentType :false,
		processData : false,
		success: function(result)
		{
			var response = $.parseJSON(result);
			alert(response.responseData);
			if(response.isErrorExist != true)
			{
				location.reload();
			}
		}
	});
		});
$('#btn-client-save').click(function(e)
		{
	e.preventDefault();
	$.ajax({
		type: 'POST',
		url : 'add-new-client',
		data : $('.add-client').serialize(),
		success: function(result)
		{
			var response = $.parseJSON(result);
			if(response == null || response.isErrorExist == true)
			{
				alert("can't add new client")
			}
			else
			{
				alert("success..");
				$('#settings').trigger('click');
			}
		}
	});
		});


function loadFileData(fid)
{
	$.ajax({
		type: 'POST',
		url : 'load-file-detail?fid='+fid,
		success: function(result)
		{
			var response = $.parseJSON(result);
			if( response.isErrorExist != true)
			{
				$('.add_data_source').addClass('hide');
				$('.view_data_source').removeClass('hide');
				$('.view_client_detail').addClass('hide');

				var data = response.responseData.data;
				createTable(data);
				$('#data-added-by').html(response.responseData.clientName);
				$('#data-added-on').html(response.responseData.timeCreatedStr);
				$('#remove-data-sorce').html("Remove ");
				$('#remove-data-sorce').attr("active" , 'true');
				$('#remove-data-sorce').attr("fid" , response.responseData.id);
				$('#visibility').attr("fid" , response.responseData.id);
				if(response.responseData.isPublic != true)
				{
					$('#visibility').html("Show ");
					$('#visibility').attr('ispublic' , "false");
				}
				else
				{
					$('#visibility').html("Hide ");
					$('#visibility').attr('ispublic' , "true");
				}

				if($('#settings').hasClass('hide'))
					$('.visibility').addClass("hide");
				else
					$('.visibility').removeClass("hide");

			}
		}
	});
}

$(document).on('click', "a.datalist", function () {
	loadFileData($(this).attr('fid') );
	
});


function createTable(data) {
	
	data = $.parseJSON(data)
	var columns =[];
	for(var i in data[0])
	{
		var obj = {};
		obj["title"] = data[0][i];
		columns.push(obj);
	}
	
	if(dataTableSource  != null)
	{
		dataTableSource .destroy();
		$('#data-source-datatable').empty();
	}
	
	dataTableSource = $('#data-source-datatable').DataTable( {
		destroy: true,
        data: data,
        columns: columns
    } );
	
	/*data = $.parseJSON(data);
	var table = $("<table />");
	for (var i = 0; i < data.length; i++) {
		var row = $("<tr />");
		var cells = data[i];
		if (data.length > 1) {
			for (var j = 0; j < cells.length; j++) {
				var cell = $("<td />");
				cell.html(cells[j]);
				row.append(cell);
			}
			table.append(row);
		}
	}
	$("#data-table").html('');
	$("#data-table").append(table);*/
	
}



$('#remove-data-sorce').click(function(e)
		{
	var status = $(this).attr('active');
	var fid = $(this).attr('fid');
	performFileOperation(fid, status , "R");
		});
$('#visibility').click(function(e)
		{
	var status = $(this).attr('ispublic');
	var fid = $(this).attr('fid');
	performFileOperation(fid , status , "V");
		});

function performFileOperation(fid,  status , type)
{
	$.ajax({
		type: 'POST',
		url : 'update-file?fid='+fid+'&status='+status+'&type='+type,
		success: function(result)
		{
			var response = $.parseJSON(result);

			if(response.isErrorExist !=true)
			{
				if(type == "R")
				{
					if($('#remove-data-sorce').attr('active') == "true")
					{
						$('#remove-data-sorce').attr('active' , "false");
						$('#remove-data-sorce').html('Recover ');
					}
					else
					{
						$('#remove-data-sorce').attr('active' , "true");
						$('#remove-data-sorce').html('Remove ');
					}

				}
				else
				{
					if($('#visibility').attr('ispublic') == "true")
					{
						$('#visibility').attr('ispublic' , "false");
						$('#visibility').html('Show ');
					}
					else
					{
						$('#visibility').attr('ispublic' , "true");
						$('#visibility').html('Hide ');
					}

				}
				alert("updated successfully");
			}
			else
				alert("can't perform operation");
		}
	});
}

$('#btn-client-cancel').click(function(e)
{
	e.preventDefault();
	$('#settings').trigger('click');
});
$('#add-new-data').click(function(e)
{
	clearAddClient();
	$('#dashboard').trigger('click');
});

function clearAddClient()
{
	$('.clear-client-input').val('');
}
$('.logout').click(function(e)
{	
	$.ajax({
		url:'logout',
		type:'POST',
		success: function(data)
		{
			location.reload();
		}
	});
});
$('#dashboard').click(function(e)
		{
	$('.view_data_source').addClass('hide');
	$('.view_client_detail').addClass('hide');
	$('.add_new_client ').addClass('hide');
	$('#add-new-client').addClass('hide');
	$('.add_data_source').removeClass('hide');
	$('#data-source-list').removeClass('hide');
	$('.list-heading').html('Data List');

		});
$('#settings').click(function(e)
		{
	$('.view_data_source').addClass('hide');
	$('.add_data_source').addClass('hide');
	$('#data-source-list').addClass('hide');
	$('.add_new_client').addClass('hide');
	$('.view_client_detail').removeClass('hide');
	$('.list-heading').html('Client List');
	$('#add-new-client').removeClass('hide');

	$.ajax({
		url:'load-setting-detail',
		type:'POST',
		success: function(data)
		{
			var response = $.parseJSON(data);
			if(response == null || response.responseData == null || response.isErrorExist == true)
			{	
				alert("can't load data..");
				return;
			}

			$('#setting-table').html('');
			var responseData = response.responseData;
			$.each(response.responseData, function (index ,value) {
				var obj = $('#settings-table-clone').clone();
				obj.removeClass('hide');
				obj.find('.setting-sno').html(index+1);
				obj.find('.setting-client-name').html(value.clientName);
				obj.find('.setting-added-on').html(value.addedDate);
				obj.find('.setting-Data-sources').html(value.sources + " sources");
				obj.find('.setting-role').html(value.role);
				$('#setting-table').append(obj.find('tr'));
			});
			$('.setting-data-table').dataTable();

		}
	});


		});

$('#add-new-client').click(function(e)
{
	$('.view_client_detail').addClass('hide');
	$('.add_new_client').removeClass('hide');
});
