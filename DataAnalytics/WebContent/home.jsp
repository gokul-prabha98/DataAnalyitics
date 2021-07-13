<!DOCTYPE html>
<html lang="en">
<title>Data Analytics</title>
<link rel="icon" href="img/logo.png" type="image/x-icon">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Lato">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css">
	    
<%
	Object isAdmin = session.getAttribute("isadmin");
	String settingsClass = "";
	if (isAdmin != null) {
		out.println(isAdmin);
		String isAdminStr = isAdmin.toString();
		out.println(isAdminStr);
		if (isAdmin != null && isAdmin.toString().equals("false"))
			settingsClass = "hide";
	}
%>
<style>

#DataTables_Table_0_info
{
	margin-left:110px !important;
}
#DataTables_Table_0_filter
{
	margin-right:-110px !important;
	margin-bottom: 10px;
}
#DataTables_Table_0_paginate
{
	margin-right:-110px !important;
}
#DataTables_Table_0_length
{
	margin-left:110px !important;
}

.paging-nav {
  text-align: right;
  padding-top: 2px;
}

.paging-nav a {
  margin: auto 1px;
  text-decoration: none;
  display: inline-block;
  padding: 1px 7px;
  background: #91b9e6;
  color: white;
  border-radius: 3px;
}

.paging-nav .selected-page {
  background: #187ed5;
  font-weight: bold;
}
.add_new_client td {
	padding: 5px;
}

#data-table table {
	border: 1px solid #ccc;
	border-collapse: collapse;
	margin-left: 10px;
	max-width: 850px;
	width: 1000px !important;
	table-layout: fixed !important;
	overflow-y: scroll;
	overflow-x: scroll;
	height: 390px;
	display: block;
	margin-top: 20px !important;
}

.setting-table table {
	border: 1px solid #ccc;
	border-collapse: collapse;
	margin-left: 100px;
	margin-top: 55px !important;
}

#data-table table td {
	padding: 5px;
	border: 1px solid #ccc;
}

.setting-table table td {
	border: 1px solid #ccc;
	padding: 20px;
}

.setting-table table th {
	padding: 30px;
	border: 1px solid #ccc;
}

#data-table table tr, .setting-table table tr {
	border: 1px solid #ccc;
}

.hide {
	display: none !important;
}

td {
	max-width: 100%;
	white-space: nowrap;
}

.center {
	margin-left: auto;
	margin-right: auto;
	text-align: center;
}

body {
	font-family: "Lato", sans-serif
}

.mySlides {
	display: none
}

input[type='file'] {
	color: rgba(0, 0, 0, 0)
}
}
</style>
<body>

	<nav class="w3-sidebar w3-bar-block w3-white w3-collapse w3-top"
		style="z-index: 3; width: 250px" id="mySidebar">
		<div class="w3-container w3-display-container w3-padding-16">
			<i onclick="w3_close()"
				class="fa fa-remove w3-hide-large w3-button w3-display-topright"></i>
			<h3 class="w3-wide">
				<b class="list-heading">Data List</b>
			</h3>
		</div>
		<div id="data-source-list" class=" w3-large w3-text-grey"
			style="font-weight: bold"></div>

	</nav>

	<!-- Navbar -->
	<div class="w3-top">
		<div class="w3-bar w3-black w3-card">
			<a
				class="w3-bar-item w3-button w3-padding-large w3-hide-medium w3-hide-large w3-right"
				href="javascript:void(0)" title="Toggle Navigation Menu"><i
				class="fa fa-bars"></i></a> <a style="margin-left: 19%;"
				class="w3-bar-item w3-button w3-padding-large" id="dashboard">DashBoard</a>
			<a id="settings"
				class="w3-bar-item w3-button w3-padding-large w3-hide-small <%=settingsClass%>">Setting</a>
			<div class="w3-dropdown-hover w3-right">
				<button class="w3-padding-large w3-button" title="More">
					<i class="fa fa-user" aria-hidden="true"></i>
				</button>
				<div class="w3-dropdown-content w3-bar-block w3-card-4">
					<a href="#" class="w3-bar-item w3-button logout">logout</a>
				</div>
			</div>
			<a
				class="hide view_data_source w3-bar-item w3-button w3-padding-large w3-right w3-hide-small"
				id="add-new-data">Add New</a> <a
				class="hide w3-bar-item w3-button w3-padding-large w3-right w3-hide-small"
				id="add-new-client">Add Client</a>
		</div>
	</div>


	<!-- Page content -->
	<div class="add_data_source vieww3-content"
		style="max-width: 2000px; margin-top: 46px">

		<!-- The Band Section -->
		<div class="w3-container w3-content w3-center w3-padding-64"
			style="max-width: 800px" id="band">
			<h4 class="w3-wide">Add Data Source</h4>

			<div>

				<form method="POST" class="form-file-upload"
					enctype='multipart/form-data'>
					<table class="center" style="width: 50%">

						<tr>
							<td><a>Data Source Name</a></td>
							<td><input type="text" name="filename" id="source-name"
								multiple></td>
						</tr>
						<tr>
							<td><a type="text">upload CSV file</a></td>
							<td><input type="file" name="file" id='browse'
								style="margin-top: 10px; max-width: 35%;" /> <br> <a
								id="file-upload-filename"></a></td>
						</tr>
						<tr>
							<td colspan='2'><input style="margin-top: 10px;"
								type="submit" value="save" id="btn-save" class="floatleft">
								<input style="margin-left: 10px;" value="cancel" type="submit"
								id="btn-cancel" class="floatleft"></td>
						</tr>
					</table>
				</form>
			</div>
			<div></div>
		</div>
	</div>
	<div class="add_new_client hide vieww3-content"
		style="max-width: 2000px; margin-top: 46px">

		<!-- The Band Section -->
		<div class="w3-container w3-content w3-center w3-padding-64"
			style="max-width: 800px" id="band">
			<h4 class="w3-wide">Add New Client</h4>

			<div>

				<form method="POST" class="add-client">
					<table class="center" style="width: 50%">
						<tr>
							<td><a>Client Name</a></td>
							<td><input type="text" class="clear-client-input"
								name="clientname"></td>
						</tr>
						<tr>
							<td><a type="text">LoginID</a></td>
							<td><input type="text" class="clear-client-input"
								name="loginid"></td>
						</tr>
						<tr>
							<td><a type="text">Password</a></td>
							<td><input type="text" class="clear-client-input"
								name="Password"></td>
						</tr>
						<tr>
							<td><a type="text">Role</a></td>
							<td><select name="role">
									<option value="A">Admin</option>
									<option value="G">Guest</option>
							</select></td>
						</tr>
						<tr>
							<td colspan='2'><input style="margin-top: 10px;"
								type="submit" value="save" id="btn-client-save"
								class="floatleft"> <input style="margin-left: 10px;"
								value="cancel" type="submit" id="btn-client-cancel"
								class="floatleft"></td>
						</tr>
					</table>
				</form>
			</div>
			<div></div>
		</div>
	</div>


	<div class="w3-content view_data_source hide"
		style="max-width: 2000px; margin-top: 46px">
		<div class="w3-container w3-content w3-center"
			style="max-width: 800px" id="band">
			<div id="data-table">
			
				<table id="data-source-datatable">  </table>
			
			</div>
			
			<div class="w3-left" style="margin-top: 15px; margin-left: 5px">
				<div>
					Data added by : <a id="data-added-by"> </a>
				</div>
				<div>
					Data added on : <a id="data-added-on"> </a>
				</div>
				<br>
				<h3>
					<u>Actions</u>
				</h3>
				<br>
				<div>
					<a> <u id="remove-data-sorce">Remove</u>
					</a> the data source
				</div>
				<br>
				<div class="visibility <%=settingsClass%>">
					<a> <u id="visibility"></u>
					</a> the data to guest
				</div>
				<br>
			</div>
		</div>
	</div>
	<div class="w3-content view_client_detail hide setting-table"
		style="max-width: 2000px; margin-top: 46px">
		<div class="w3-container w3-content w3-center"
			style="max-width: 800px" id="band">
			<div>

				<table class="setting-data-table">
					<thead>
						<tr>

							<th>S.No</th>
							<th>User Name</th>
							<th>Added date</th>
							<th>Data sources</th>
							<th>Role</th>

						</tr>
					</thead>

					<tbody id="setting-table">

					</tbody>


				</table>


			</div>
		</div>
	</div>

	<table class="hide" id="settings-table-clone">
		<tbody>
			<tr class="">
				<td><span class="setting-sno"></span></td>
				<td class="setting-client-name"></td>
				<td class="setting-added-on"></td>
				<td class="setting-Data-sources"></td>
				<td class="setting-role"></td>
			</tr>
		</tbody>
	</table>


	<script>
		if (window.history.replaceState) {
			window.history.replaceState(null, null, window.location.href);
		}

		var input = document.getElementById('browse');
		var infoArea = document.getElementById('file-upload-filename');

		input.addEventListener('change', showFileName);

		function showFileName(event) {

			// the change event gives us the input it occurred in 
			var input = event.srcElement;

			// the input has an array of files in the `files` property, each one has a name that you can use. We're just using the name here.
			var fileName = input.files[0].name;

			// use fileName however fits your app best, i.e. add it into a div
			infoArea.textContent = 'File name: ' + fileName;
		}
	</script>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="js/cookies.js"></script>
	<script src="js/home.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
	<script src="https://cdn.datatables.net/1.10.25/js/jquery.dataTables.min.js"></script>
</body>
</html>
