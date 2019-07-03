<!DOCTYPE html>
<%@page import="java.util.Arrays,java.util.ArrayList,java.util.Iterator,java.util.List,java.text.SimpleDateFormat,java.util.Date"%>
<html>
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<title>Doctiger</title>
	<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
    <meta name="viewport" content="width=device-width" />
    <link href="https://fonts.googleapis.com/css?family=Raleway:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">
	<link rel="stylesheet" href="css/font-awesome-4.7.0/css/font-awesome.min.css" />
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/material-bootstrap-wizard-custom.css">
	<link rel="stylesheet" type="text/css" href="css/custom.css">
</head>
<body> 
    <div class="container">
        <div class="row">
	        <div class="col-sm-12">
	            <div class="wizard-container">
	                <div class="card wizard-card" data-color="red" id="wizard">
                    	<div class="wizard-header">
                    		<div class="row">
                    			<div class="col-sm-12 heading">
                    				<h2>Doctiger</h2>
                    			</div>
                    		</div>
                    	</div>
                    	<div class="wizard-body">
	                		<div class="row">
	                			<div class="col-sm-12">
	                				<table class="table table-bordered table-clause-library">
	                					<thead>
	                						<tr>
	                							<th width="100px">SR No</th>
	                							<th>User</th>
	                							<th>Email Id</th>
	                							<th>Password</th>
	                							<th>Roles</th>
	                						</tr>
	                					</thead>
	                					<tbody id="user-list">
	                					<%
			List<String> list = (List<String>) request.getAttribute("assignedUserList");
		%>

		<%
			int val = Integer.parseInt(request.getParameter("val"));

			for (int i = 0; i < val; i++) {
				if (!list.isEmpty() && list.size() > i) {
		%>
		<tr>
	                							<td><input type="text" name=""  class="form-control" value="<%=list.get(i).toString() != null ? list.get(i) : ""%>"
			disabled="true"  ></td>
	                							<td><input type="text" name=""  class="form-control" value="<%=list.get(i).toString() != null ? list.get(i) : ""%>"
			disabled="true" ></td>
	                							<td><input type="text" name=""  class="form-control" value="<%=list.get(i).toString() != null ? list.get(i) : ""%>"
			disabled="true" ></td>
	                							<td><a href="#">Reset Password</a></td>
	                							<td>
	                								<select class="form-control">
	                                					<option>-- Role --</option>
	                                					<option value="Admin">Admin</option>
	                                					<option class="Setup">Setup</option>
	                                					<option class="User">User</option>
	                                				</select>
	                							</td>
	                						</tr>
	                						
		
	                					<%
			} else {
		%>
		<tr>
	                							<td><input type="text" name="" value="<%= i %>"class="form-control"></td>
	                							<td><input type="text" name="" value="" class="form-control"></td>
	                							<td><input type="text" name="" value="" class="form-control"></td>
	                							<td><a href="#">Reset Password</a></td>
	                							<td>
	                								<select class="form-control">
	                                					<option>-- Role --</option>
	                                					<option value="Admin">Admin</option>
	                                					<option class="Setup">Setup</option>
	                                					<option class="User">User</option>
	                                				</select>
	                							</td>
	                						</tr>
	                						
		
	                						<%
			}
			}
		%>
	                						
	                						
	                						<!--  <tr>
	                							<td><input type="text" name="" value="2" class="form-control"></td>
	                							<td><input type="text" name="" value="" class="form-control"></td>
	                							<td><input type="text" name="" value="" class="form-control"></td>
	                							<td><a href="#">Reset Password</a></td>
	                							<td>
	                								<select class="form-control">
	                                					<option>-- Role --</option>
	                                					<option value="Admin">Admin</option>
	                                					<option class="Setup">Setup</option>
	                                					<option class="User">User</option>
	                                				</select>
	                							</td>
	                						</tr>
	                						<tr>
	                							<td><input type="text" name="" value="3" class="form-control"></td>
	                							<td><input type="text" name="" value="" class="form-control"></td>
	                							<td><input type="text" name="" value="" class="form-control"></td>
	                							<td><a href="#">Reset Password</a></td>
	                							<td>
	                								<select class="form-control">
	                                					<option>-- Role --</option>
	                                					<option value="Admin">Admin</option>
	                                					<option class="Setup">Setup</option>
	                                					<option class="User">User</option>
	                                				</select>
	                							</td>
	                						</tr>
	                						<tr>
	                							<td><input type="text" name="" value="4" class="form-control"></td>
	                							<td><input type="text" name="" value="" class="form-control"></td>
	                							<td><input type="text" name="" value="" class="form-control"></td>
	                							<td><a href="#">Reset Password</a></td>
	                							<td>
	                								<select class="form-control">
	                                					<option>-- Role --</option>
	                                					<option value="Admin">Admin</option>
	                                					<option class="Setup">Setup</option>
	                                					<option class="User">User</option>
	                                				</select>
	                							</td>
	                						</tr>
	                						<tr>
	                							<td><input type="text" name="" value="5" class="form-control"></td>
	                							<td><input type="text" name="" value="" class="form-control"></td>
	                							<td><input type="text" name="" value="" class="form-control"></td>
	                							<td><a href="#">Reset Password</a></td>
	                							<td>
	                								<select class="form-control">
	                                					<option>-- Role --</option>
	                                					<option value="Admin">Admin</option>
	                                					<option class="Setup">Setup</option>
	                                					<option class="User">User</option>
	                                				</select>
	                							</td>
	                						</tr> -->
	                						
	                					</tbody>
	                				</table>
	                			</div>
	                    	</div>
	                    	<input type="button" value="Save" onclick="save('save');" />
                		</div>	
	                </div>
	            </div> <!-- wizard container -->
	        </div>
    	</div> <!-- row -->
	</div>
    <script src="javascript/app.js"></script>
	<script src="js/jquery-2.2.4.min.js" type="text/javascript"></script>
	<script src="js/bootstrap.min.js" type="text/javascript"></script>
	<script src="js/jquery.bootstrap.js" type="text/javascript"></script>
	<script src="js/material-bootstrap-wizard.js"></script>
	<script src="js/jquery.validate.min.js"></script>
	<script src="js/jquery-ui-1.12.1.js"></script>
</body>
</html>