<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page
	import="org.json.JSONArray,org.json.JSONObject;import com.userPanel.service.impl.ReadXlsImpl;import com.mongodb.DB"%>

<%@page
	import="java.util.Arrays,java.util.ArrayList,java.util.Iterator,java.util.List,java.text.SimpleDateFormat,java.util.Date"%>

<%
	ReadXlsImpl objReadXls = new ReadXlsImpl();
%>

<html>
<head>


<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700'
	rel='stylesheet' type='text/css'>


<style>
.nav-tabs>li.active>a, .nav-tabs>li.active>a:focus, .nav-tabs>li.active>a:hover
	{
	color: #ffffff;
	cursor: default;
	background-color: #4dbdbd;
	border: 1px solid #ddd;
	border-bottom-color: transparent;
}

h1, h2, h3 {
	font-weight: 700;
	font-size: 20px;
	color: #777;
}

.cd-form {
	max-width: 600px;
	padding: 2%;
	margin: 10px auto;
}

.cd-form .fieldset {
	position: relative;
	margin: 0.4em 0 !important;
}

.cd-form .fieldset:first-child {
	margin-top: 0;
}

.cd-form .fieldset:last-child {
	margin-bottom: 0;
}

.cd-form label {
	font-size: 14px;
	font-size: 0.875rem;
}

.cd-form label.image-replace {
	/* replace text with an icon */
	display: inline-block;
	color: #4dbdbd;
	position: absolute;
	left: 15px;
	top: 50%;
	bottom: auto;
	-webkit-transform: translateY(-50%);
	-moz-transform: translateY(-50%);
	-ms-transform: translateY(-50%);
	-o-transform: translateY(-50%);
	transform: translateY(-50%);
	height: 20px;
	width: 20px;
	overflow: hidden;
	text-indent: 100%;
	white-space: nowrap;
	color: transparent;
	text-shadow: none;
	background-repeat: no-repeat;
	background-position: 50% 0;
}

.cd-form label.cd-username {
	background-image:
		url("https://s3-us-west-2.amazonaws.com/s.cdpn.io/148866/cd-icon-message.svg");
}

.cd-form label.cd-email {
	background-image:
		url("https://s3-us-west-2.amazonaws.com/s.cdpn.io/148866/cd-icon-email.svg");
}

.cd-form input {
	margin: 0;
	padding: 0;
	border-radius: 0.25em;
}

.cd-form input.full-width {
	width: 100%;
}

.cd-form input.has-padding {
	padding: 12px 20px 12px 50px;
}

.cd-form input.has-border {
	border: 1px solid #d2d8d8;
	-webkit-appearance: none;
	-moz-appearance: none;
	-ms-appearance: none;
	-o-appearance: none;
	appearance: none;
}

.cd-form input.has-border:focus {
	border: 1px solid #4dbdbd;
	box-shadow: 5px 5px 10px rgba(52, 54, 66, 0.1);
	outline: none;
	background-color: #edf8f8;
}

.cd-form input[type=submit] {
	padding: 16px 0;
	cursor: pointer;
	background: #4dbdbd;
	color: #FFF;
	font-weight: bold;
	border: none;
	-webkit-appearance: none;
	-moz-appearance: none;
	-ms-appearance: none;
	-o-appearance: none;
	appearance: none;
}

.no-touch .cd-form input[type=submit]:hover, .no-touch .cd-form input[type=submit]:focus
	{
	background: #3599ae;
	outline: none;
}

@media only screen and (min-width: 600px) {
	.cd-form {
		padding: 2em;
	}
	.cd-form .fieldset {
		margin: 2em 0;
	}
	.cd-form .fieldset:first-child {
		margin-top: 0;
	}
	.cd-form .fieldset:last-child {
		margin-bottom: 0;
	}
	.cd-form input.has-padding {
		padding: 16px 20px 16px 50px;
	}
	.cd-form input[type=submit] {
		padding: 16px 0;
	}
}
</style>

<style>

/*table css*/
.responstable {
  margin: 1em 0;
  width: 100%;
  overflow: hidden;
  background: #FFF;
  color: #024457;
  border-radius: 5px;
  border: 1px solid #4dbdbd;
  box-shadow: 1px 1px 1px 1px #c2c2dc;

}

.responstable tr {
  border: 1px solid #4dbdbd;
}
.responstable tr:nth-child(odd) {
  background-color: #EAF3F3;
}
.responstable th {
  display: none;
  border: 1px solid #FFF;
  background-color: #4dbdbd;
  color: #FFF;
  padding: 0.3em;
}
.responstable th:first-child {
  display: table-cell;
  text-align: center;
}
.responstable th:nth-child(2) {
  display: table-cell;
}
.responstable th:nth-child(2) span {
  display: none;
}
.responstable th:nth-child(2):after {
  content: attr(data-th);
}
@media (min-width: 480px) {
  .responstable th:nth-child(2) span {
    display: block;
  }
  .responstable th:nth-child(2):after {
    display: none;
  }
}
.responstable td {
  display: block;
  word-wrap: break-word;
  max-width: 7em;
}
.responstable td:first-child {
  display: table-cell;
  text-align: center;
  border-right: 1px solid #D9E4E6;
}
@media (min-width: 480px) {
  .responstable td {
    border: 1px solid #D9E4E6;
  }
}
.responstable th, .responstable td {
  text-align: center;
  margin: .5em 1em;
}
@media (min-width: 480px) {
  .responstable th, .responstable td {
    display: table-cell;
    padding: 0.1em;

  }
}

  table {
    width: 100%;

  }

.glyphicon
{
    font-size: 12px;
}

td {
 padding:2px;   
}

#formdealer {
  display: none;
}
#Distributor {
  display: none;
}
#consumer {
  display: none;
}
</style>
<style>
    .nav-tabs>li.active>a, .nav-tabs>li.active>a:focus, .nav-tabs>li.active>a:hover {
    color: #ffffff;
    cursor: default;
    background-color: #4dbdbd;
    border: 1px solid #ddd;
    border-bottom-color: transparent;
}

    h1,h2,h3 {
      font-weight: 700;
      font-size :20px;
      color: #777;
    }
    .cd-form {
      max-width: 600px;
  padding : 2% ;
  margin :10px auto;
}
.cd-form .fieldset {
  position: relative;
  margin: 0.4em 0 !important; 
}
.cd-form .fieldset:first-child {
  margin-top: 0;
}
.cd-form .fieldset:last-child {
  margin-bottom: 0;
}
.cd-form label {
  font-size: 14px;
  font-size: 0.875rem;
}
.cd-form label.image-replace {
  /* replace text with an icon */
  display: inline-block;
  color: #4dbdbd ;
  position: absolute;
  left: 15px;
  top: 50%;
  bottom: auto;
  -webkit-transform: translateY(-50%);
  -moz-transform: translateY(-50%);
  -ms-transform: translateY(-50%);
  -o-transform: translateY(-50%);
  transform: translateY(-50%);
  height: 20px;
  width: 20px;
  overflow: hidden;
  text-indent: 100%;
  white-space: nowrap;
  color: transparent;
  text-shadow: none;
  background-repeat: no-repeat;
  background-position: 50% 0;

}
.cd-form label.cd-username {
  background-image: url("https://s3-us-west-2.amazonaws.com/s.cdpn.io/148866/cd-icon-message.svg");
 
}
.cd-form label.cd-email {
  background-image: url("https://s3-us-west-2.amazonaws.com/s.cdpn.io/148866/cd-icon-email.svg");
}

.cd-form input {
  margin: 0;
  padding: 0;
  border-radius: 0.25em;
}
.cd-form input.full-width {
  width: 100%;
}
.cd-form input.has-padding {
  padding: 12px 20px 12px 50px;
}
.cd-form input.has-border {
  border: 1px solid #d2d8d8;
  -webkit-appearance: none;
  -moz-appearance: none;
  -ms-appearance: none;
  -o-appearance: none;
  appearance: none;
}
.cd-form input.has-border:focus {
  border:1px solid #4dbdbd;
  box-shadow: 5px 5px 10px rgba(52, 54, 66, 0.1);
  outline: none;
  background-color: #edf8f8;
}

.cd-form input[type=submit] {
  padding: 16px 0;
  cursor: pointer;
  background: #4dbdbd;
  color: #FFF;
  font-weight: bold;
  border: none;
  -webkit-appearance: none;
  -moz-appearance: none;
  -ms-appearance: none;
  -o-appearance: none;
  appearance: none;
}
.no-touch .cd-form input[type=submit]:hover, .no-touch .cd-form input[type=submit]:focus {
  background: #3599ae;
  outline: none;
}

@media only screen and (min-width: 600px) {
  .cd-form {
    padding: 2em;
  }
  .cd-form .fieldset {
    margin: 2em 0;
  }
  .cd-form .fieldset:first-child {
    margin-top: 0;
  }
  .cd-form .fieldset:last-child {
    margin-bottom: 0;
  }
  .cd-form input.has-padding {
    padding: 16px 20px 16px 50px;
  }
  .cd-form input[type=submit] {
    padding: 16px 0;
  }
}

/*table css*/
.responstable {
  margin: 1em 0;
  width: 100%;
  overflow: hidden;
  background: #FFF;
  color: #024457;
  border-radius: 5px;
  border: 1px solid #4dbdbd;
  box-shadow: 1px 1px 1px 1px #c2c2dc;

}

.responstable tr {
  border: 1px solid #4dbdbd;
}
.responstable tr:nth-child(odd) {
  background-color: #EAF3F3;
}
.responstable th {
  display: none;
  border: 1px solid #FFF;
  background-color: #4dbdbd;
  color: #FFF;
  padding: 0.7em;
}
.responstable th:first-child {
  display: table-cell;
  text-align: center;
}
.responstable th:nth-child(2) {
  display: table-cell;
}
.responstable th:nth-child(2) span {
  display: none;
}
.responstable th:nth-child(2):after {
  content: attr(data-th);
}
@media (min-width: 480px) {
  .responstable th:nth-child(2) span {
    display: block;
  }
  .responstable th:nth-child(2):after {
    display: none;
  }
}
.responstable td {
  display: block;
  word-wrap: break-word;
  max-width: 7em;
}
.responstable td:first-child {
  display: table-cell;
  text-align: center;
  border-right: 1px solid #D9E4E6;
}
@media (min-width: 480px) {
  .responstable td {
    border: 1px solid #D9E4E6;
  }
}
.responstable th, .responstable td {
  text-align: center;
  margin: .5em 1em;
}
@media (min-width: 480px) {
  .responstable th, .responstable td {
    display: table-cell;
    padding: 0.1em;

  }
}

  table {
    width: 100%;

  }

.glyphicon
{
    font-size: 12px;
}

td {
 padding:2px;   
}

#formdealer {
  display: none;
}
#Distributor {
  display: none;
}
#consumer {
  display: none;
}

/*upload css*/
i {
  color: #4dbdbd;
}

i:hover {
  opacity: 0.8;
}
 h1,h2,h3,h4,h5,h6 {
  color: #777;

 }

.results tr[visible='false'],
.no-result1,.no-result2{
  display:none;
}

.results tr[visible='true']{
  display:table-row;
}

.counter1,.counter2{
  padding:8px; 
  color:#ccc;
}



/*scrollbar css*/


.scrollbar
{
 /*margin-left: 22px;*/
  float: left;
  height: 500px;
  width: 100%;
  /*background: #F5F5F5;*/
  overflow:auto;
  /*margin-bottom: 25px;*/
  border: 1px solid rgba(255,255,255,0.3);
}

/* for custom scrollbar for webkit browser*/

::-webkit-scrollbar {
    width: 6px;
} 
::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3); 
} 
::-webkit-scrollbar-thumb {
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3); 
}
</style>
</head>
<body onload="personcontacts()">
	<div class="container">
		<div class="row">
			<h2 class="text-center">Invite</h2>
			<div class="bs-example bs-example-tabs"
				style="height: 500px; display: flex; flex-direction: column; border-radius: 15px:">

				<ul id="myTab" class="nav nav-tabs" style="flex: 0 0 auto">

					<li  class="" id="pcontact"><a href="#Personalcontact"
						data-toggle="tab"> Personal contact</a></li>

					<li id="newcontMain" class="active"><a href="#NewContact" data-toggle="tab">
							New Contact</a></li>
					<li id="addcontMain" class=""><a href="#AddedContacts" data-toggle="tab">
							Invited Contacts</a></li>
				</ul>



				<div class="tab-content">
					<div id="Personalcontact" class="tab-page tab-pane fade in  ">
						<h3 class="text-center">Person contact</h3>
						<div class="container">

 <div style="margin-top: 2px;" class=" col-xs-4 col-md-offset-4 col-xs-offset-1">
                          <table class="responstable ">
                            <tbody class="text-center">
                              
                          
                              
                              <tr>
                                <td><a id="total" href="#">Total Contact:500</a></td>
                                <td><a id="invited" href="#">Invited:100</a></td>
                              </tr>                          
                            </tbody>
                          </table>
                </div><!--end of table div -->
                <div class="clearfix"></div>
                <form action="<%=request.getContextPath()%>/userPanel?type=Addcontactmongo" method="post">
                          <input type="hidden" name="compid" value="<%=request.getParameter("compid")%>" id="compid" />
       <input type="hidden" name="userid" value="<%=request.getRemoteUser()%>" id="userid" />     
        <input type="hidden" name="compname" value="<%=request.getParameter("compname")%>" id="compname" />   
                          
                <div id="totalTable" class="row" style="display: none">
                  <center>
                    <div style="width: 50%">
                      <table class="">
                        <tr>
                          <td><h4>Selected contacts: <span id="check"> </span></h4></td>
                          
                          <td><select class="form-control" name='comp_type' id='comp_type' placeholder='type'>
                                              <option value="Dealer">Dealer</option>
                                              <option value="Distributor">Distributor</option>
                                              <option value="Consumer">Consumer</option>
                                            </select></td>
                          <!-- <td><a href="#" class="btn btn-info">Save</a></td> -->
                          <td> <input class="btn btn-info" type="submit" value="Save"></td>
                        </tr>
                      </table>
                    
                      <br>
                    </div>
                  </center>
                  
                  <div class="form-group pull-left">
                      <input id="myInput1" type="text" class="search form-control" placeholder="Search EmailID" onkeyup="myFunction1()">
                  </div><span class="counter1 pull-left"></span>
                  
                  <div class="scrollbar">
                    <table id="myTable1" class="responstable results" cellspacing="0" width="100%" style="margin-top: 0;margin-bottom: 0">
                        <thead>  <tr>
                            <th style="width: 32px"></th>
                            <th>Company Name</th>
                            <th>Name</th>
                            <th>Website</th>
                            <th>EmailID</th>
                            <th>Contact Number</th>
                            <th>Type</th>
                            <th>Creation Date</th>
                           <th>Status</th>
                           </tr>
                           <tr class="warning no-result1">
                              <td colspan="8"><i class="fa fa-warning"></i> No result</td>
                            </tr>
                            </thead>
                            <tbody id="LoadDiv">
                          
                          
                       </tbody >
                  </table>
                  
                   </div>
                   </div>
                    </form>
                 

             

<div id="invitedTable" class="row" style="display: none">
                  <div class="form-group pull-left">
                      <input id="myInput2" type="text" class="search form-control" placeholder="Search EmailID" onkeyup="myFunction2()">
                  </div><span class="counter2 pull-left"></span>
                  
                  <div class="scrollbar">
                    <table id="myTable2" class="responstable results " cellspacing="0" width="100%">
                    
                            <tr>
                              <th>Company Name</th>
                              <th>Name</th>
                              <th>Website</th>
                              <th>EmailID</th>
                              <th>Contact Number</th>
                              <th>Type</th>
                              <th>Creation Date</th>
                              <th>Status</th>
                              <tr class="warning no-result2">
                                <td colspan="7"><i class="fa fa-warning"></i> No result</td>
                              </tr>
                            </tr>
                            <tr>
                  </thead>
                            <tbody id="invitedDiv">
                         
                          
                       </tbody >
                  </table>

							
						</div>


					</div>
					
					</div>
   </div>


					<div id="NewContact" class="tab-page tab-pane fade in active ">
						<h3 class="text-center">New Contacts</h3>
						<ul id="myTab" class="nav nav-tabs" style="flex: 0 0 auto">
							<li id="uploadChild" class=""><a href="#upload" data-toggle="tab"> Upload
									Excel</a></li>
							<li id="formChild" class="active"><a href="#form" data-toggle="tab">
									Form</a></li>
							<li id="socialChild" class=" "><a href="#social" data-toggle="tab">
									Social</a></li>
						</ul>

						<div class="tab-content">
							
							<div id="upload" class="tab-pane fade in ">
            <div class="container">
              <div class="row">
                <h2 class="text-center"></h2>
                  
                  <div class="row">
                    <div class="container">
                     <h4 class=""><span style="margin-right:20px;"> Download Templates </span><a href="http://35.236.154.164:8078/ServiceLogging/xls/invitecompany.xls" download="invitecompany.xls" ><i class="fa fa-download fa-2x" aria-hidden="true"></i></a></h4>  <br>
                  <div class="panel panel-info">
                    <div class="panel-heading"><strong>Upload Files</strong></div>
                    <div class="panel-body">

                      <!-- Standar Form -->
                    <form action="<%=request.getContextPath()%>/invitecompany"
												method="post" enctype="multipart/form-data"> 
						<input type="hidden" name="compid" value="<%=request.getParameter("compid")%>" id="compid" />
						<input type="hidden" name="userid" value="<%=request.getRemoteUser()%>" id="userid" /> 
						<input type="hidden" name="compname" value="<%=request.getParameter("compname")%>" id="compname" />    
                       <table class="table text-center">
                          <tbody id="userbody1">
                            <tr>
                              <td ><div style="display: inline-block" class="text-left"><input type="file" name="file"></div><div style="display: inline-block" class="text-left"><button id="processbtn" class="btn btn-info" type="submit">Process 
                                </button></div>
                              </td>
                              
                            </tr>
                          </tbody>
                          </table>
                        </form>
                        <div class="clearfix"></div>
                       <%
                       
                       
                    session=request.getSession(false);  
                       // out.print(session);
                   if(session.getAttribute("result") != null){
                   String result=(String)session.getAttribute("result");
                  // out.print(result);
                   String[] res=result.split("~");
                   String successfullcount=res[0];
                   String unsuccessfullcount=res[1];
                   int total = Integer.parseInt(successfullcount) + Integer.parseInt(unsuccessfullcount); 
                  // out.print(successfullcount);
                  // out.print(unsuccessfullcount);
                  // out.print(res[2]);
                   JSONObject errordatajson = new JSONObject(res[2]);
                  // out.print("**"+errordatajson);
                  // out.print(unsuccessfullcount);
                   JSONObject errorinsidejson = new JSONObject();
                  // JSONArray jerrorarray=new JSONArray();
					
					 

                       %>
                        <div id="processtable" class="container"  style="display:block;margin-top: 20px">
                          <div class="row text-center">
                          <% JSONArray jerrorarray  = errordatajson.getJSONArray("data");
                    //        out.print("$$$$$$"+jerrorarray.length());
                            if(jerrorarray.length() > 0){
                            
                      //      	out.print("inside if");
                            %>
                            <label class=""> <%=successfullcount %>/<%=String.valueOf(total) %> records uploaded successfully</label>
                            <h6 class="text-left"><%=unsuccessfullcount %>Unsuccesful records</h6>              
                            
                            <table  class="responstable" style="width: 94.5%;margin-top: 5px">
                              <thead>
                                <th>Company Name</th>
                                <th>Contact Person</th>
                                <th>Email</th>
                                <th>Website</th>
                                <th>Contact No.</th>
                                <th>Type</th>
                              </thead>
                              <tbody>
                             <%  for(int i=0 ; i<jerrorarray.length(); i++){
                            	 
                            	 //errorinsidejson = jerrorarray.getJSONObject(i);
                             %>
                                <tr>
                                  <td><%=jerrorarray.getJSONObject(i).getString("strcompanyname") %></td>
                                  <td><%=jerrorarray.getJSONObject(i).getString("strcontactperson") %></td>
                                  <td><%=jerrorarray.getJSONObject(i).getString("stremailid") %></td>
                                  <td><%=jerrorarray.getJSONObject(i).getString("strwebsite") %></td>
                                  <td><%=jerrorarray.getJSONObject(i).getString("strcontactdetail") %></td>
                                  <td><%=jerrorarray.getJSONObject(i).getString("strtype") %></td>
                                </tr>
                                
                            <% }%>
                              </tbody>
                            </table>
                            
                          </div>
                          <h5 class="text-center"><label>Reupload file with proper records </label></h5>
                        </div>
                        
                   <%}  

                   request.getSession().setAttribute("result",null);
                                      }
                   
                   %>

                   
               
                    </div>
                  </div>
                 </div> <!-- /container -->
                </div>
              </div>
            </div>
           </div>
							
							
							
							<div id="form" class="tab-pane fade in active">
								<div class="container">
									<div class="row ">
										<div id="cd-signup">
											<!-- sign up form -->
											<form class="cd-form"
												action="<%=request.getContextPath()%>/userPanel?type=form"
												method="post">

												<input type="hidden" name="compid"
													value="<%=request.getParameter("compid")%>" id="compid" />
												<input type="hidden" name="userid"
													value="<%=request.getRemoteUser()%>" id="userid" /> <input
													type="hidden" name="compname"
													value="<%=request.getParameter("compname")%>" id="compname" />

												<p class="fieldset">
													<label class="image-replace cd-email" for="signup-username">Company
														Name</label> <input class="full-width has-padding has-border"
														name="cname" id="signup-username" type="text"
														placeholder="Company Name">

												</p>

												<p class="fieldset">
													<label class="image-replace cd-email" for="signup-email">Contact
														Person</label> <input class="full-width has-padding has-border"
														name="conperson" type="text" placeholder="Contact Person">

												</p>
												<p class="fieldset">
													<label class="image-replace cd-email" for="signup-email">EmailID</label>
													<input class="full-width has-padding has-border"
														name="email" type="email" placeholder="E-mail">

												</p>
												<p class="fieldset">
													<label class="image-replace cd-email" for="signup-email">Website</label>
													<input class="full-width has-padding has-border"
														name="website" type="text" placeholder="Website">

												</p>
												<p class="fieldset">
													<label class="image-replace cd-email" for="signup-email">Contact
														Detailes</label> <input class="full-width has-padding has-border"
														name="contact" type="text" placeholder="Contact Detailes">

												</p>



												<p class="fieldset ">
													<select
														class="form-control full-width has-padding has-border"
														name="type">
														<option value="Dealer" >Dealer</option>
														<option value="Distributor">Distributor</option>
														<option value="Consumer">Consumer</option>

													</select>

												</p>




												<p class="fieldset">
													<input class="full-width has-padding" type="submit"
														value="submit">
												</p>
											</form>

											<!-- <a href="#0" class="cd-close-form">Close</a> -->
										</div>
										<!-- cd-signup -->

									</div>
								</div>
							</div>

<div id="social" class="tab-pane fade in">
								<div class="container">
									<div class="row ">
									<center> <p><span class="text-blue"><h3>Import contacts using</h3></span> </center>
                                   
    <a target="_blank" href="http://www.bizlem.com/portal/socialauthContact.html?id=googleplus"><img src="<%=request.getContextPath()%>/apps/images/gmail-icon.jpg" height="12" alt="Google" title="Google" border="0"></img></a></br>
	<a target="_blank" href="http://www.bizlem.com/portal/socialauthContact.html?id=yahoo"><img src="<%=request.getContextPath()%>/apps/images/yahoomail_icon.jpg" height="16" alt="YahooMail" title="YahooMail" border="0"></img></a>
	<!--a href="http://www.bizlem.com/portal/socialauthContact.html?id=hotmail"><img src="<%=request.getContextPath()%>/apps/images/hotmail.jpeg" height="16" alt="HotMail" title="HotMail" border="0"></img></a-->
	
                          </p>
									
									
									
									
									
</div>

</div></div>
						</div>
					</div>


					<div id="AddedContacts" class="tab-page tab-pane fade in ">
						<h3 class="text-center">Invited Contact</h3>
						
						<div style="margin-top: 2px;" class=" col-xs-4 col-md-offset-4 col-xs-offset-1">
                  <table class="responstable ">
                    <tr>
                      <th class="text-center" colspan="4">Summary</th>
                      <%
                      int iDealer = 0;
						int iDealerA = 0;
						int iDealerNA = 0;
						int iDist = 0;
						int iDistA = 0;
						int iDistNA = 0;
						int iCons = 0;
						int iConsA = 0;
						int iConsNA = 0;
                      
	                      JSONObject alldatajson = new JSONObject();

                      
                      try{
                      String compid = request.getParameter("compid");
                      JSONObject insidejson = new JSONObject();
						alldatajson = objReadXls.getInvitedCompany(compid);
					//	out.print(alldatajson);
						JSONArray array = alldatajson.getJSONArray("data");
						
						for(int i=0;i<array.length();i++){
							insidejson = array.getJSONObject(i);
							if(insidejson.getString("type").equals("Dealer")){
								iDealer++;
								
								if(insidejson.getString("flag").equals("1")){iDealerA++; }
								else { iDealerNA++;}
								
							}else if(insidejson.getString("type").equals("Distributor")){
								iDist++;
								
								if(insidejson.getString("flag").equals("1")){iDistA++; }
								else { iDistNA++;}
								
							}else if(insidejson.getString("type").equals("Consumer")){
								iCons++;
								if(insidejson.getString("flag").equals("1")){iConsA++; }
								else { iConsNA++;}
							}
						}}catch(Exception e){}
                      %>
                    </tr>
                    <tbody class="text-center">
                      <tr>
                        <th>Type</th>
                        <th>Sent Invitation</th>
                        <th>Accepted</th>
                        <th>Not Accepted</th>
                      </tr>
                      
                      <tr>
                        <td>Dealer</td>
                        <td><a id="deal1" class="" href="#"  onclick="displaytable('Dealer', 'total')" ><%=iDealer%></a></td>
                        <td><a href="#" onclick="displaytable('Dealer', 'Accepted')" ><%=iDealerA%></a></td>
                        <td><a href="#" onclick="displaytable('Dealer', 'notAccepted')"><%=iDealerNA %></a></td>

                      </tr>
                      <tr>
                        <td>Distributor</td>
                        <td><a id="dis1" class="" href="#"  onclick="displaytable('Distributor', 'total')"><%=iDist%></a></td>
                        <td><a class="" href="#" onclick="displaytable('Distributor', 'Accepted')"><%=iDistA%></a></td>
                        <td><a class="" href="#" onclick="displaytable('Distributor', 'notAccepted')"><%=iDistNA %></a></td>

                      </tr>
                      <tr>
                        <td>Consumer</td>
                        <td><a id="con1" href="#" onclick="displaytable('Consumer', 'total')"><%=iCons%></a></td>
                        <td><a href="#" onclick="displaytable('Consumer', 'Accepted')" ><%=iConsA%></a></td>
                        <td><a href="#" onclick="displaytable('Consumer', 'notAccepted')" ><%=iConsNA%></a></td>
                      </tr>
                    </tbody>
                  </table>
                </div><!--end of table div -->
						
				<div class="clearfix"></div>
              	
			<div class="container">
                  <div class="row" id="totaldiv"> </div>
                   <!-- <div class="row" id="Distributortotaldiv"> </div>
                     <div class="row" id="Consumertotaldiv"> </div> -->
                      <div class="row" id="Accepteddiv"> </div>
                   <!--<div class="row" id="DistributorAccepteddiv"> </div>
                     <div class="row" id="ConsumerAccepteddiv"> </div> -->
                      <div class="row" id="notAccepteddiv"> </div>
                   <!--<div class="row" id="DistributornotAccepteddiv"> </div>
                     <div class="row" id="ConsumernotAccepteddiv"> </div> -->
              
                 
                </div>

					</div>



</div>
					<script type="text/javascript"
						src="https://code.jquery.com/jquery-1.12.4.min.js" /></script>
					<script type="text/javascript"
						src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" /></script>
</body>
<script>
function displaytable(a,b){
	var type1=a;
	var flag1=b;
	console.log(type1);
	console.log(flag1);
	var jsonString = '<%=alldatajson%>';
	console.log(jsonString);
	var objJson = JSON.parse(jsonString);
	console.log(objJson);
	var objArray = objJson.data;
	console.log(objArray.length);
	var d = "";
	var ddiv = "";
	var contperson1="";
	var status="";
	var cou=0;
	
	
	if(flag1=="total"){
		document.getElementById("totaldiv").innerHTML = "";
		document.getElementById("Accepteddiv").innerHTML = "";
		document.getElementById("notAccepteddiv").innerHTML = "";
		$("#totaldiv").show();
		$("#Accepteddiv").hide();
		$("#notAccepteddiv").hide();
	for(var i =0;i<objArray.length;i++){
		if(objArray[i].contperson=="null"){contperson1="";}
		else{ contperson1=objArray[i].contperson; }
	
		if(objArray[i].type==type1){
			if(objArray[i].flag=="1"){status="Accepted";}
			else{status="notAccepted";}
			
			d=d+"<tr><td>"+objArray[i].compname+"</td><td>"+contperson1+"</td><td>"+objArray[i].website+"</td><td>"+objArray[i].email+"</td><td>"+objArray[i].contactnumber+"</td><td>"+objArray[i].createddate+"</td><td>"+objArray[i].type+"</td><td>"+status+"</td></tr>";	
		}
	}
	ddiv="<center><p>"+type1+"</p><table class='responstable'><thead><th>Company Name</th><th>Name</th><th>Website</th><th>EmailID</th><th>Contact Number</th><th style='display: none'>Type</th><th>Creation Date</th><th>Type</th><th>Status</th></thead><tbody class='text-center'>"+d+"</tbody></table></center>";
	document.getElementById(flag1+"div").innerHTML = ddiv;
	d==null;
	ddiv = "";
	
	}else if(flag1=="notAccepted") {
		document.getElementById("totaldiv").innerHTML = "";
		document.getElementById("Accepteddiv").innerHTML = "";
		document.getElementById("notAccepteddiv").innerHTML = "";
		$("#totaldiv").hide();
		$("#Accepteddiv").hide();
		$("#notAccepteddiv").show();
		for(var i =0;i<objArray.length;i++){
			
			if(objArray[i].contperson=="null"){contperson1="";}
			else{ contperson1=objArray[i].contperson; }
		
				if(objArray[i].flag=="1"){status="Accepted";}
				else{status="notAccepted";}
				
				if(objArray[i].type==type1 && objArray[i].flag=="0"){
					
					
					d=d+"<tr><td><input type='checkbox' name='select' value='"+cou+"' checked></td><td><input type='text'  readOnly name='comp_name' id='comp_nameId' value='"+objArray[i].compname+"'/></td><td><input type='text'  readOnly name='contperson_nameId' id='contperson_nameId' value='"+contperson1+"'/></td><td><input type='text'  readOnly name='comp_website' id='comp_website' value='"+objArray[i].website+"'/></td><td><input type='text'  readOnly name='comp_email' id='comp_emailId' value='"+objArray[i].email+"'/></td><td><input type='text'  readOnly name='comp_numb' id='comp_numbId' value='"+objArray[i].contactnumber+"'/></td><td><input type='text'  readOnly name='comp_date' id='comp_dateId' value='"+objArray[i].createddate+"'/></td><td><input type='text'  readOnly name='comp_type' id='comp_typeId' value='"+objArray[i].type+"'/></td><td><input type='text'  readOnly name='comp_flag' id='comp_flagId' value='"+status+"'/></td></tr>";		
                 cou++;
				}}
			ddiv="<center><p>"+type1+"</p><form class='' id='sendrem' action='<%=request.getContextPath()%>/userPanel?type=sendreminder' method='POST'><input type='hidden' name='compid' value='<%=request.getParameter("compid")%>' id='compid' /><input type='hidden' name='userid' value='<%=request.getRemoteUser()%>' id='userid' /><input type='hidden' name='companyname' value='<%=request.getParameter("compname")%>' id='companyname' /><table class='responstable'><thead><th></th><th>Company Name</th><th>Name</th><th>Website</th><th>EmailID</th><th>Contact Number</th><th style='display: none'>Type</th><th>Creation Date</th><th>Type</th><th>Remark</th></thead><tbody class='text-center' >"+d+"</tbody></table><input class='btn btn-info' type='submit' name='' value='Send reminder'></form></center>";
			document.getElementById(flag1+"div").innerHTML = ddiv;
			d=null;
			ddiv = "";
			
		}else if(flag1=="Accepted"){
			document.getElementById("totaldiv").innerHTML = "";
			document.getElementById("Accepteddiv").innerHTML = "";
			document.getElementById("notAccepteddiv").innerHTML = "";
			$("#totaldiv").hide();
			$("#Accepteddiv").show();
			$("#notAccepteddiv").hide();
			for(var i =0;i<objArray.length;i++){
			
			if(objArray[i].contperson=="null"){contperson1="";}
			else{ contperson1=objArray[i].contperson; }
		
				if(objArray[i].flag=="1"){status="Accepted";}
				else{status="notAccepted";}
				
				if(objArray[i].type==type1 && objArray[i].flag=="1"){
					d=d+"<tr><td>"+objArray[i].compname+"</td><td>"+contperson1+"</td><td>"+objArray[i].website+"</td><td>"+objArray[i].email+"</td><td>"+objArray[i].contactnumber+"</td><td>"+objArray[i].createddate+"</td><td>"+objArray[i].type+"</td><td>"+status+"</td></tr>";
				}
	           }
			ddiv="<center><p>"+type1+"</p><table class='responstable'><thead><th>Company Name</th><th>Name</th><th>Website</th><th>EmailID</th><th>Contact Number</th><th>Creation Date</th><th>Type</th><th>Status</th></thead><tbody class='text-center' >"+d+"</tbody></table></center>";
			document.getElementById(flag1+"div").innerHTML = ddiv;
			d=null;
			ddiv = "";
			}
}
	
function myFunction1() {
	  // Declare variables 
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("myInput1");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("myTable1");
	  tr = table.getElementsByTagName("tr");
	  var count = 0;
	  // Loop through all table rows, and hide those who don't match the search query
	  for (i = 0; i < tr.length; i++) {
	    td = tr[i].getElementsByTagName("td")[4];
	    if (td) {
	      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	   //      var jobCount = td.innerHTML.toUpperCase().indexOf(filter).length;
	   //      
	     count++;
	        // document.getElementsByClassName("counter").innerHTMl=len;
	      }else{
	        tr[i].style.display = "none";
	        // count--;
	      }
	    } 
	  }
	  $('.counter1').text(count + ' item');
				if(count == '0') {
					$('.no-result1').show();}
			    else {
			    	$('.no-result1').hide();
			    }
	}

	// search for table 2

	function myFunction2() {
	  // Declare variables 
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("myInput2");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("myTable2");
	  tr = table.getElementsByTagName("tr");
	  var count = 0;
	  // Loop through all table rows, and hide those who don't match the search query
	  for (i = 0; i < tr.length; i++) {
	    td = tr[i].getElementsByTagName("td")[3];
	    if (td) {
	      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	   //      var jobCount = td.innerHTML.toUpperCase().indexOf(filter).length;
	   //      
	     count++;
	        // document.getElementsByClassName("counter").innerHTMl=len;
	      } else {
	        tr[i].style.display = "none";
	        // count--;
	      }
	    } 
	  }
	  $('.counter2').text(count + ' item');
	      if(count == '0') {
	        $('.no-result2').show();}
	        else {
	          $('.no-result2').hide();
	        }
	} 



	var countChecked = function() {
	  var n = $( "input:checked" ).length;
	  $( "#check" ).text( n + (n === 1 ? "" : "") + "" );
	};
	countChecked();
	 
	$( "input[type=checkbox]" ).on( "click", countChecked );





	$("#total").on('click', function() {
	   $("#totalTable").show();
	   $("#invitedTable").hide();
	});
	$("#invited").on('click', function() {
	   $("#invitedTable").show();
	   $("#totalTable").hide();
	});



	
</script>
<script>

$('#hideshowdiv').click(function(){
	myFunction();
});

function myFunction() {
    var x = document.getElementById('fileuploadcontainer');
    if (x.style.display === 'none') {
        x.style.display = 'block';
    } else {
        x.style.display = 'none';
    }
}

function personcontacts(){
	var totalCount = 0;
	var inviteCount = 0;
	 //alert("start");
	 //document.getElementById("getQuoteid").value=a;
	//  getQuoteProduct();
	 var remoteUser = '<%=request.getRemoteUser().replace("@","_")%>';
	 var compid1='<%=request.getParameter("compid")%>';
	 //if(remoteUser == "null" || remoteUser == "anonymous" || remoteUser == "admin" ){

	  $.ajax
	    ({
	      type: 'GET',
	     url:'<%=request.getContextPath()%>/userPanel?type=personalcontact',
	        data:{
	         user:remoteUser, compid:compid1
	     },
	           success: function(data) {
	         console.log("succ--"+data);
	          if(data != "false"){
	            var objJson = JSON.parse(data);
	           var objArray = objJson.data;
	           var vdiv = "";
	           var idiv = "";
	          // var chk="<tr><td><input type='checkbox' name='select' value='"+i+"'>";
	           var cname="";
	           var pname="";
	           var email="";
	           var website="";
	           var cno="";
	           var type="";
	           var cdate="";
	           var status="";
	           var tabtype="";
	           
	           
	           //document.getElementById("modalLoadDiv").innerHTML
	           document.getElementById("LoadDiv").innerHTML = "" ;
	           document.getElementById("invitedDiv").innerHTML = "" ;
	           
	           for(var i =0;i<objArray.length;i++){
	            
	        	/*if(objArray[i].flag=="0"){chk="<tr><td></td>"; status="notAccepted";}
	            else if(objArray[i].flag=="1"){chk="<tr><td></td>"; status="Accepted";}*/
	            
	            if(objArray[i].compname=="null"){cname="";}else{cname=objArray[i].compname;}
	            if(objArray[i].contactpersonname=="null"){pname="";}else{pname=objArray[i].contactpersonname;}
	            if(objArray[i].email=="null"){email="";}else{email=objArray[i].email;}
	            if(objArray[i].website=="null"){website="";}else{website=objArray[i].website;}
	            if(objArray[i].contact=="null"){cno="";}else{cno=objArray[i].contact;}
	            if(objArray[i].type=="null"){type="";}else{type=objArray[i].type;}
	            if(objArray[i].cdate=="null"){cdate="";}else{cdate=objArray[i].cdate;}
	            
	            //if(objArray[i].flag=="null"){status="";}else{status=objArray[i].flag;}
	            
	           // console.log(chk);
	            //console.log(cname);
	           // console.log(pname);
	           // console.log(email);
	          // console.log(website);
	           // console.log(type);
	            //console.log(cdate);
	           // console.log(status);
	            //console.log(tabtype);
	            
	            
	            
	            if(objArray[i].tabtype == "personalcontact"){
	            	
	            	if(objArray[i].flag=="0"){
	            		console.log("if 0");
	            		vdiv = vdiv+"<tr><td></td><td><input class='form-control' readonly name='comp_name' id='comp_nameId' value='"+cname+"' type='text' placeholder='Name' /></td><td><input class='form-control' readonly name='contperson_nameId' id='contperson_nameId' value='"+pname+"' type='text' placeholder='Name' /></td><td><input class='form-control' name='comp_website' readonly id='comp_websiteId' value='"+website+"' type='text' placeholder='Website' /></td><td><input class='form-control' readOnly name='comp_email' id='comp_emailId' value='"+email+"'type='text' placeholder='admin@example.com' /></td><td><input class='form-control'  width='3' name='comp_numb' value='"+cno+"' readonly id='comp_numbId' type='text' placeholder='contact'></td><td><input class='form-control'  width='3' name='comp_type1' value='"+type+"'  id='comp_type1'type='text' readonly placeholder='type'></td><td><input class='form-control'  width='3' name='comp_date1' value='"+cdate+"' id='comp_date1'type='text' readonly placeholder='Date'></td><td><input class='form-control'  width='3' name='comp_flag' value='notAccepted' id='comp_flag' type='text' readonly placeholder='status'></td></tr>";
	            		idiv=  idiv+"<tr><td><input class='form-control' readonly name='comp_name' id='comp_nameId' value='"+cname+"' type='text' placeholder='Name' /></td><td><input class='form-control' name='contperson_nameId' readonly id='contperson_nameId' value='"+pname+"' type='text' placeholder='Name' /></td><td><input class='form-control' name='comp_website' id='comp_websiteId' value='"+website+"' readonly type='text' placeholder='Website' /></td><td><input class='form-control' readOnly name='comp_email' id='comp_emailId' value='"+email+"'type='text' placeholder='admin@example.com' /></td><td><input class='form-control'  width='3' name='comp_numb' value='"+cno+"' readonly id='comp_numbId' type='text' placeholder='contact'></td><td><input class='form-control'  width='3' name='comp_type1' value='"+type+"' id='comp_type1'type='text' readonly placeholder='type'></td><td><input class='form-control'  width='3' name='comp_date1' value='"+cdate+"' id='comp_date1'type='text' readonly placeholder='Date'></td><td><input class='form-control'  width='3' name='comp_flag' value='notAccepted' id='comp_flag' type='text' readonly placeholder='status'></td></tr>";
	            		totalCount++;
	            		inviteCount++;
	            	}else if(objArray[i].flag=="1"){
	            		console.log("if 1");
	            		vdiv = vdiv+"<tr><td></td><td><input class='form-control' readonly name='comp_name' id='comp_nameId' value='"+cname+"' type='text' placeholder='Name' /></td><td><input class='form-control' readonly name='contperson_nameId' id='contperson_nameId' value='"+pname+"' type='text' placeholder='Name' /></td><td><input class='form-control' name='comp_website' readonly id='comp_websiteId' value='"+website+"' type='text' placeholder='Website' /></td><td><input class='form-control' readOnly name='comp_email' id='comp_emailId' value='"+email+"'type='text' placeholder='admin@example.com' /></td><td><input class='form-control'  width='3' name='comp_numb' value='"+cno+"' readonly id='comp_numbId' type='text' placeholder='contact'></td><td><input class='form-control'  width='3' name='comp_type1' value='"+type+"'  id='comp_type1'type='text' readonly placeholder='type'></td><td><input class='form-control'  width='3' name='comp_date1' value='"+cdate+"' id='comp_date1'type='text' readonly placeholder='Date'></td><td><input class='form-control'  width='3' name='comp_flag' value='Accepted' id='comp_flag' type='text' readonly placeholder='status'></td></tr>";
	            		idiv=  idiv+"<tr><td><input class='form-control' readonly name='comp_name' id='comp_nameId' value='"+cname+"' type='text' placeholder='Name' /></td><td><input class='form-control' name='contperson_nameId' readonly id='contperson_nameId' value='"+pname+"' type='text' placeholder='Name' /></td><td><input class='form-control' name='comp_website' id='comp_websiteId' value='"+website+"' readonly type='text' placeholder='Website' /></td><td><input class='form-control' readOnly name='comp_email' id='comp_emailId' value='"+email+"'type='text' placeholder='admin@example.com' /></td><td><input class='form-control'  width='3' name='comp_numb' value='"+cno+"' readonly id='comp_numbId' type='text' placeholder='contact'></td><td><input class='form-control'  width='3' name='comp_type1' value='"+type+"' id='comp_type1'type='text' readonly placeholder='type'></td><td><input class='form-control'  width='3' name='comp_date1' value='"+cdate+"' id='comp_date1'type='text' readonly placeholder='Date'></td><td><input class='form-control'  width='3' name='comp_flag' value='Accepted' id='comp_flag' type='text' readonly placeholder='status'></td></tr>";
	            		totalCount++;
	            		inviteCount++;
	            	}else{
	            		console.log("else");
	            		vdiv = vdiv+"<tr><td><input type='checkbox' name='select' value='"+i+"'></td><td><input class='form-control' name='comp_name' id='comp_nameId' value='"+cname+"' type='text' placeholder='Name' /></td><td><input class='form-control' name='contperson_nameId' id='contperson_nameId' value='"+pname+"' type='text' placeholder='Name' /></td><td><input class='form-control' name='comp_website' id='comp_websiteId'value='"+website+"' type='text' placeholder='Website' /></td><td><input class='form-control' readOnly name='comp_email' id='comp_emailId' value='"+email+"'type='text' placeholder='admin@example.com' /></td><td><input class='form-control'  width='3' name='comp_numb' value='"+cno+"' id='comp_numbId' type='text' placeholder='contact'></td><td><input class='form-control'  width='3' name='comp_type1' value='"+type+"' id='comp_type1'type='text' readonly placeholder='type'></td><td><input class='form-control'  width='3' name='comp_date1' value='"+cdate+"' id='comp_date1'type='text' readonly placeholder='Date'></td><td><input class='form-control'  width='3' name='comp_flag' value='' id='comp_flag' type='text' readonly placeholder='status'></td></tr>";
	            		totalCount++;
	            	}
	               
	             
	            }
	           
	           }
	           console.log(idiv);
	           document.getElementById("LoadDiv").innerHTML = vdiv;
	           document.getElementById("invitedDiv").innerHTML = idiv;
	           document.getElementById("total").innerHTML = "Total Contact:"+totalCount;
	           document.getElementById("invited").innerHTML = "Invited:"+inviteCount;
	         
	          }else{
	           document.getElementById('pcontact').style.visibility = 'hidden';
	          }
	        
	          },
	          error: function(data) {
	      //     alert("error--"+data);
	         
	           }
	       });
	  // }
	// alert("end");
	   }

$(function()
		{
		   if(<%=request.getParameter("maintab")%> != null){
		       //alert("if");
		       var maintab = '<%=request.getParameter("maintab")%>';
		       if(maintab == "1"){
		           $("#pcontact").addClass("active");
		           $("#newcontMain").removeClass("active");
		           $("#addcontMain").removeClass("active");
		           $("#Personalcontact").addClass("active in");
		           $("#NewContact").removeClass("active in");
		           $("#AddedContacts").removeClass("active in");
		       }else if(maintab == "2"){
		    	   $("#pcontact").removeClass("active");
		           $("#newcontMain").addClass("active");
		           $("#addcontMain").removeClass("active");
		           $("#Personalcontact").removeClass("active in");
		           $("#NewContact").addClass("active in");
		           $("#AddedContacts").removeClass("active in");
		           if(<%=request.getParameter("childtab")%> != null){
		        	   var childtab = '<%=request.getParameter("childtab")%>';
		        	   if(childtab == "1"){
		        	   $("#uploadChild").addClass("active");
			           $("#formChild").removeClass("active");
			           $("#socialChild").removeClass("active");
			           $("#upload").addClass("active in");
			           $("#form").removeClass("active in");
			           $("#id").removeClass("active in");
		        	   }
		           }
		       }else if(maintab == "3"){
		    	   $("#pcontact").removeClass("active");
		           $("#newcontMain").removeClass("active");
		           $("#addcontMain").addClass("active");
		           $("#Personalcontact").removeClass("active in");
		           $("#NewContact").removeClass("active in");
		           $("#AddedContacts").addClass("active in");
		       }
		   } else{
		       //alert("else");
		   }
		});
</script>
</html>