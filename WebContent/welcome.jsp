<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome Page</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript">
	function getList() {
		
		$("#loaderDiv").attr("style", "display: block");
		
		var request = $.ajax({
			url : "rest/employees/list",
			type : "GET"
		});

		request.done(function(msg) {
			console.log("Request successful!");
			$("#loaderDiv").attr("style", "display: none");
			$("#selectionDiv").attr("style", "display: block");
			$("#drop").empty();
			$("#drop").append(
					$("<option></option>").val(0).html(
							"<i> -- Employee Names -- <i>"))
			for (i in msg) {
				$("#drop").append(
						$("<option></option>").val(msg[i].id).html(
								msg[i].firstName + " " + msg[i].lastName));
			}
		});

		request.fail(function(jqXHR, textStatus) {
			$("#loaderDiv").attr("style", "display: none");
			console.log("Request failed: " + textStatus);
		});
	}

	function prepareEdit() {
		$("#editDiv").attr("style", "display: block");
		console.log('The option with value ' + $("#drop").val() + ' and text '
				+ $("#drop option:selected").text() + ' was selected.');
		$("#editId").val($("#drop").val());
		var fullName = $("#drop option:selected").text();
		var splittedSource = fullName.replace(/\s{2,}/g, ' ').split(' ');
		$("#editFName").val(splittedSource[0]);
		$("#editLName").val(splittedSource[1]);
		$("#storageInput").val(
				$("#editId").val() + ":" + $("#editFName").val() + ":"
						+ $("#editLName").val());
		console.log('value stored -- ' + $("#storageInput").val());
	}

	function updateName() {
		var temp = $("#storageInput").val();
		var request = $.ajax({
			url : "rest/employees/update",
			type : 'PUT',
			contentType : 'application/json; charset=utf-8',
			data : {
				id : $("#editId").val(),
				fname : $("#editFName").val(),
				lname : $("#editLName").val()
			}
		});

		request.done(function(msg) {
			console.log("Request successful: " + msg);
			getList();
			$("#editDiv").attr("style", "display: none");
		});

		request.fail(function(jqXHR, textStatus) {
			console.log("Request failed: " + textStatus);
		});
	}
	
	function deleteName() {
		var request = $.ajax({
			url : "rest/employees/delete",
			type : 'DELETE',
			contentType : 'application/json; charset=utf-8',
			data : {
				id : $("#editId").val()
			}
		});

		request.done(function(msg) {
			console.log("Request successful: " + msg);
			getList();
			$("#editDiv").attr("style", "display: none");
		});

		request.fail(function(jqXHR, textStatus) {
			console.log("Request failed: " + textStatus);
		});
	}
	
	function addName() {
		$("#addName").attr("style", "display: block");
	}
	
	function hide() {
		$("#addName").attr("style", "display: none");
	}
	
	function hide2() {
		$("#editDiv").attr("style", "display: none");
		$("#selectionDiv").attr("style", "display: none");		
	}
		
	function add() {
		var request = $.ajax({
			url : "rest/employees/save",
			type : 'POST',
			contentType : 'application/json; charset=utf-8',
			data : {
				fname : $("#addFName").val(),
				lname : $("#addLName").val()
			}
		});

		request.done(function(msg) {
			console.log("Request successful: " + msg);
			hide(); 
		});

		request.fail(function(jqXHR, textStatus) {
			console.log("Request failed: " + textStatus);
		});
	}
</script>
</head>
<body>
	<div class="container" style="background-image:url(green2.jpg); background-position: center center; 
	            background-repeat: no-repeat; background-size: cover">
		<div class="row" align="center" style="color:darkgreen"><h3>Hello Buddies</h3></div>
		<div class="row" align="center" style="color:gray"><h4>Let's try some RESTFul CRUDs</h4></div>
		<div class="row" align="center" style="display: none" id="loaderDiv"><img alt="loading" src="loadinfo.gif"></div>
		<hr>
		<div class="row" align="center">
			<input type="button" class="btn-success" value="Get List" onclick="getList()">
			<input type="button" class="btn-success" value="Add Name" onclick="addName()">
		</div>
		<hr>
		<div class="row" style="display: none" id="selectionDiv"
			align="center" class="btn-success">
			<select name="Employee List" id="drop" onchange="prepareEdit()"></select>
			<!-- <input type="button" class="btn-danger" onclick="hide2()" value="Click to Hide"><br> -->
			<a href="#" onclick="hide2()" style="color: red; font-size: 18px">
          		<span class="glyphicon glyphicon-remove-sign"></span>
        	</a> <br>
		</div>
		<hr>
		<div class="row" style="display: none" id="editDiv" align="center">
			<label>EmpID : </label>
				<input id="editId" disabled="disabled"><br>
			<label>FNAME : </label>
				<input id="editFName"><br> 
			<label>LNAME : </label>
				<input id="editLName"><br> 
			<label>EDIT : </label>
				<input type="button" class="btn-success" onclick="updateName()" value="Click to Save"><br>
			<label>DELETE : </label>
				<input type="button" class="btn-success" onclick="deleteName()" value="Click to Delete"><br>
				<!-- option to use for using hidden variable as local storage -->
			<input type="text" style="display: none" id="storageInput">
		</div>
		<hr>
		<div class="row" style="display: none" id="addName" align="center">
			<label>FNAME :</label>
				<input id="addFName"><br> 
			<label>LNAME :</label>
				<input id="addLName"><br> 
			<label>ADD : </label>
				<input type="button" class="btn-success" onclick="add()" value="Click to Add">
			<!-- <input type="button" class="btn-danger" onclick="hide()" value="Click to Hide"><br> -->
			<a href="#" onclick="hide()" style="color: red; font-size: 18px">
          		<span class="glyphicon glyphicon-remove-sign"></span>
        	</a> <br>
		</div>
	</div>
</body>
</html>