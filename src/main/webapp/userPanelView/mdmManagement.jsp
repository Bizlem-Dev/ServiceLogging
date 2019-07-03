<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="org.json.JSONArray,org.json.JSONObject;import com.userPanel.service.impl.ReadXlsImpl;import com.mongodb.DB;"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>MDM Service</title>
	<link rel="stylesheet" href="mdmscript/bootstrap.min.css" >
	<link href="mdmscript/fSelect.css" rel="stylesheet">
	<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.3/jquery.min.js"></script>
    <script src="mdmscript/fSelect.js"></script>
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<style type="text/css">
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
/* layout.css Style */
.upload-drop-zone {
  height: 200px;
  border-width: 2px;
  margin-bottom: 20px;
}

/* skin.css Style*/
.upload-drop-zone {
  color: #ccc;
  border-style: dashed;
  border-color: #ccc;
  line-height: 200px;
  text-align: center;
}
.upload-drop-zone.drop {
  color: #222;
  border-color: #222;
} 


</style>
<script>

function unassigned(type,smeuser){
	
	
	 $.ajax({
         type: 'GET',
         url: '<%=request.getContextPath()%>/userPanel?type=unassignsmeuser',

					data : {
						smetype : type,
						smeuser : smeuser,
						serviceId : $("#serviceId").val()
					},
					dataType : 'html',
					success : function(data) {
						//alert(data);
						//window.close();
						console.log("unassigned=="+data);
						var url      = window.location.href; 
						 url = url + "&tab=3";
						// alert(url);
						 location.replace(url);
						//location.reload();
					}
				});
	 

}

function allocateByPercentType() {
	//alert("start");
    var assignedUserId = document.getElementsByName("assignuserid");
    var assignedUserName = document.getElementsByName("assignusername");
    var assignedUserEmail = document.getElementsByName("assignuseremail");
    var assignedUserPercent = document.getElementsByName("assignpercent");
    var assignedUserType = document.getElementsByName("assigntype1");
 //   alert(assignedUserId.length+"~"+assignedUserName.length+"~"+assignedUserEmail.length+"~"+assignedUserPercent.length+"~"+assignedUserType.length);
  //  alert(assignedUserId[0].value+"~"+assignedUserName[0].innerHTML+"~"+assignedUserEmail[0].innerHTML+"~"+assignedUserPercent[0].innerHTML+"~"+assignedUserType[0].innerHTML);
   for(var i = 0;i < assignedUserId.length;i++){
    var percenttype =  'userId='+assignedUserId[i].value+'&userName='+assignedUserName[i].innerHTML+'&userEmail='+assignedUserEmail[i].innerHTML+'&userPercent='+assignedUserPercent[i].value+'&userPercentType='+assignedUserType[i].innerHTML+'&serviceId='+$("#serviceId").val();
 //  alert(percenttype);
	 $.ajax({
         type: 'GET',
         url: '<%=request.getContextPath()%>/userPanel?type=assignsmepercenttype',

					data : percenttype,
					dataType : 'html',
					async: false,
					success : function(data) {
						//alert(data);
						//window.close();
						console.log("percent type=="+data);
						
						//location.reload();
					}
				});
  
    }
   var url      = window.location.href; 
	 url = url + "&tab=3";
	// alert(url);
	 location.replace(url);
}

function getPercentPreviewData() {
	//alert("start");
    var assignedUserId = document.getElementsByName("assignuserid");
    var assignedUserName = document.getElementsByName("assignusername");
    var assignedUserEmail = document.getElementsByName("assignuseremail");
    var assignedUserPercent = document.getElementsByName("assignpercent");
    var assignedUserType = document.getElementsByName("assigntype1");
 //   alert(assignedUserId.length+"~"+assignedUserName.length+"~"+assignedUserEmail.length+"~"+assignedUserPercent.length+"~"+assignedUserType.length);
  //  alert(assignedUserId[0].value+"~"+assignedUserName[0].innerHTML+"~"+assignedUserEmail[0].innerHTML+"~"+assignedUserPercent[0].innerHTML+"~"+assignedUserType[0].innerHTML);
   for(var i = 0;i < assignedUserId.length;i++){
    var percenttype =  'userId='+assignedUserId[i].value+'&userName='+assignedUserName[i].innerHTML+'&userEmail='+assignedUserEmail[i].innerHTML+'&userPercent='+assignedUserPercent[i].value+'&userPercentType='+assignedUserType[i].innerHTML+'&serviceId='+$("#serviceId").val();
 //  alert(percenttype);
	 $.ajax({
         type: 'GET',
         url: '<%=request.getContextPath()%>/userPanel?type=assignsmepercenttypepreview',

					data : percenttype,
					dataType : 'html',
					async: false,
					success : function(data) {
						//alert(data);
						//window.close();
						console.log("percent preview type=="+data);
						if(data != "false"){
						var result = data.split("~");
						var email = result[0];
						email = email.replace("@","_");
						var prodAssn = parseInt(result[1]);
						var addrAssn = parseInt(result[2]);
						var custAssn = parseInt(result[3]);
						var venAssn = parseInt(result[4]);
						var assignedTotal = prodAssn + addrAssn + custAssn + venAssn;
						//alert(assignedTotal);
						document.getElementById("assignpercentId").innerHTML = parseInt(document.getElementById("assignpercentId").innerHTML) + parseInt(assignedTotal);
						document.getElementById("unassignpercentId").innerHTML = parseInt(document.getElementById("unassignpercentId").innerHTML) - parseInt(assignedTotal);
						var perpro = document.getElementById(email+"product").innerHTML;
						var strperpro = perpro.split("/");
						document.getElementById(email+"product").innerHTML = (parseInt(strperpro[0]) + prodAssn) + "/" +  strperpro[1];
						var perproA = document.getElementById(email+"addr").innerHTML;
						var strperproA = perproA.split("/");
						document.getElementById(email+"addr").innerHTML = (parseInt(strperproA[0]) + addrAssn) + "/" +  strperproA[1];
						var perproC = document.getElementById(email+"customer").innerHTML;
						var strperproC = perproC.split("/");
						document.getElementById(email+"customer").innerHTML = (parseInt(strperproC[0]) + custAssn) + "/" +  strperproC[1];
						var perproV = document.getElementById(email+"vendor").innerHTML;
						var strperproV = perproV.split("/");
						document.getElementById(email+"vendor").innerHTML = (parseInt(strperproV[0]) + venAssn) + "/" +  strperproV[1];
						//location.reload();
						}
					}
				});
  
    }	
}

function getCatPreviewData() {
	//alert("start");
	var assignedUserId = document.getElementsByName("assigncatuserid");
    var assignedUserName = document.getElementsByName("assigncatusername");
    var assignedUserEmail = document.getElementsByName("assigncatemail");
    var assignedCat1 = document.getElementsByName("assigncategory11");
    var assignedCat2 = document.getElementsByName("assigncategory21");
    var assignedCat3 = document.getElementsByName("assigncategory31");
    var assignedCat4 = document.getElementsByName("assigncategory41");
 //   alert(assignedUserId.length+"~"+assignedUserName.length+"~"+assignedUserEmail.length+"~"+assignedUserPercent.length+"~"+assignedUserType.length);
  //  alert(assignedUserId[0].value+"~"+assignedUserName[0].innerHTML+"~"+assignedUserEmail[0].innerHTML+"~"+assignedUserPercent[0].innerHTML+"~"+assignedUserType[0].innerHTML);
   for(var i = 0;i < assignedUserId.length;i++){
	   var cat1="";
	   var cat2="";
	   var cat3="";
	   var cat4="";
	   if(assignedCat1[i].innerHTML != "Select some options"){
		   cat1 = assignedCat1[i].innerHTML;
	   }
	   if(assignedCat2[i].innerHTML != "Select some options"){
		   cat2 = assignedCat2[i].innerHTML;
	   }
	   if(assignedCat3[i].innerHTML != "Select some options"){
		   cat3 = assignedCat3[i].innerHTML;
	   }
	   if(assignedCat4[i].innerHTML != "Select some options"){
		   cat4 = assignedCat4[i].innerHTML;
	   }
	   
    var smecat =  'userId='+assignedUserId[i].value+'&userName='+assignedUserName[i].innerHTML+'&userEmail='+assignedUserEmail[i].innerHTML+'&assignCat1='+cat1+'&assignCat2='+cat2+'&assignCat3='+cat3+'&assignCat4='+cat4+'&serviceId='+$("#serviceId").val();
//   alert(smecat);
    $.ajax({
        type: 'GET',
        url: '<%=request.getContextPath()%>/userPanel?type=assignsmecategorypreview',

					data : smecat,
					dataType : 'html',
					async: false,
					success : function(data) {
						//alert(data);
						//window.close();
						console.log("cat preview type=="+data);
						if(data != "false"){
						var result = data.split("~");
						var email = result[0];
						email = email.replace("@","_");
						var prodAssn = parseInt(result[1]);
						var assignedTotal = prodAssn;
						document.getElementById("assignpercentIdC").innerHTML = parseInt(document.getElementById("assignpercentIdC").innerHTML) + parseInt(assignedTotal);
						document.getElementById("unassignpercentIdC").innerHTML = parseInt(document.getElementById("unassignpercentIdC").innerHTML) - parseInt(assignedTotal);
						var perpro = document.getElementById(email+"product").innerHTML;
						var strperpro = perpro.split("/");
						document.getElementById(email+"productc").innerHTML = (parseInt(strperpro[0]) + prodAssn) + "/" +  strperpro[1];
						}
						//location.reload();
					}
				});
  
    }	
}


function allocateByCategory() {
	//alert("start");
    var assignedUserId = document.getElementsByName("assigncatuserid");
    var assignedUserName = document.getElementsByName("assigncatusername");
    var assignedUserEmail = document.getElementsByName("assigncatemail");
    var assignedCat1 = document.getElementsByName("assigncategory11");
    var assignedCat2 = document.getElementsByName("assigncategory21");
    var assignedCat3 = document.getElementsByName("assigncategory31");
    var assignedCat4 = document.getElementsByName("assigncategory41");
 //   alert(assignedUserId.length+"~"+assignedUserName.length+"~"+assignedUserEmail.length+"~"+assignedUserPercent.length+"~"+assignedUserType.length);
  //  alert(assignedUserId[0].value+"~"+assignedUserName[0].innerHTML+"~"+assignedUserEmail[0].innerHTML+"~"+assignedUserPercent[0].innerHTML+"~"+assignedUserType[0].innerHTML);
   for(var i = 0;i < assignedUserId.length;i++){
	   var cat1="";
	   var cat2="";
	   var cat3="";
	   var cat4="";
	   if(assignedCat1[i].innerHTML != "Select some options"){
		   cat1 = assignedCat1[i].innerHTML;
	   }
	   if(assignedCat2[i].innerHTML != "Select some options"){
		   cat2 = assignedCat2[i].innerHTML;
	   }
	   if(assignedCat3[i].innerHTML != "Select some options"){
		   cat3 = assignedCat3[i].innerHTML;
	   }
	   if(assignedCat4[i].innerHTML != "Select some options"){
		   cat4 = assignedCat4[i].innerHTML;
	   }
	   
    var smecat =  'userId='+assignedUserId[i].value+'&userName='+assignedUserName[i].innerHTML+'&userEmail='+assignedUserEmail[i].innerHTML+'&assignCat1='+cat1+'&assignCat2='+cat2+'&assignCat3='+cat3+'&assignCat4='+cat4+'&serviceId='+$("#serviceId").val();
 //  alert(smecat);
    $.ajax({
        type: 'GET',
        url: '<%=request.getContextPath()%>/userPanel?type=assignsmecategory',

					data : smecat,
					dataType : 'html',
					async: false,
					success : function(data) {
						//alert(data);
						//window.close();
						console.log("allocateByCategory type=="+data);
						
						//location.reload();
					}
				});

    }	
   var url      = window.location.href; 
	 url = url + "&tab=3";
	// alert(url);
	 location.replace(url);
}

function saveCompany(){
	var infoData = 'comp_name='+$("#comp_nameId").val()+'&comp_website='+$("#comp_websiteId").val()+'&comp_email='+$("#comp_emailId").val()+'&comp_numb='+$("#comp_numbId").val()+'&serviceId='+$("#serviceId").val();
	
	 $.ajax({
         type: 'GET',
         url: '<%=request.getContextPath()%>/userPanel?type=mdmservicesavecompany',

					data : infoData,
					dataType : 'html',
					success : function(data) {
						//alert(data);
						//window.close();
						console.log("save company"+data);
						location.reload();
					}
				});
	
}

function saveUser(){
	var username = document.getElementsByName("username");
	var useremail = document.getElementsByName("useremail");
	var usermobile = document.getElementsByName("usermobile");
	var useradmin = document.getElementsByName("useradmin");
	var nameList = [];
	var personList = [];
	var mobileList = [];
	var adminList = [];
	for(var i=0;i<username.length;i++){
	nameList.push(username[i].value);
	personList.push(useremail[i].value);
	mobileList.push(usermobile[i].value);
	//alert(useradmin[i].checked);
	if(useradmin[i].checked){
	adminList.push(1);
	}else{
	adminList.push(0);
	}

	}

	var persondata = 'name='+nameList+'&person='+personList+'&number='+mobileList+'&admin='+adminList+'&serviceId='+$("#serviceId").val();
	//alert(persondata);
	
	 $.ajax({
         type: 'GET',
         url: '<%=request.getContextPath()%>/userPanel?type=mdmservicesaveperson',

					data : persondata,
					dataType : 'html',
					success : function(data) {
						//alert(data);
						//window.close();
						console.log("save person"+data);
						location.reload();
					}
				});

	
	}
	
function updateUser(){
	var userid = document.getElementsByName("userid");
	var useradmin = document.getElementsByName("useradmin");
	var idList = [];
	var adminList = [];
	for(var i=0;i<userid.length;i++){
	idList.push(userid[i].value);
	if(useradmin[i].checked){
	adminList.push(1);
	}else{
	adminList.push(0);
	}

	}

	var persondata = 'userid='+idList+'&admin='+adminList+'&serviceId='+$("#serviceId").val();
	//alert(persondata);
	
	 $.ajax({
         type: 'GET',
         url: '<%=request.getContextPath()%>/userPanel?type=mdmserviceupdateperson',

					data : persondata,
					dataType : 'html',
					success : function(data) {
						//alert(data);
						//window.close();
						console.log("update person"+data);
						location.reload();
					}
				});

	
	}

</script>
<body>
<header>
<input type="hidden" name="serviceId" value="<%=request.getParameter("serviceId")%>" id="serviceId" />
	<div class="container">
		<div class="row">
			
			<div class="bs-example bs-example-tabs"  style="height:500px; display: flex; flex-direction: column;border-radius: 15px: ">
      <ul id="myTab" class="nav nav-tabs" style="flex: 0 0 auto">
        <li id="basicli" class="active"><a href="#mdmservice" data-toggle="tab"> MDM Service</a></li>
        <li id="uploadli" class=""><a href="#upload" data-toggle="tab"> Upload</a></li>
        <li id="assignli" class=""><a href="#assignsmes" data-toggle="tab"> Assign SMEs</a></li>
        <!-- <li id="processli" class=""><a href="#process" data-toggle="tab"> Process</a></li>
        <li id="viewli" class=""><a href="#view" data-toggle="tab">View</a></li> -->

      </ul>
			<div class="tab-content">
				<div id="mdmservice" class='tab-page tab-pane fade in active'>
					<h2 class="text-center">Configure MDM Service</h2>
			<h3>Company Details</h3>
					<div class="table-responsive">

           <form action="" method="post">  
           <table>
    		<thead>
    			<tr>
    				<th><label>Name</label></th>
    				<th><label>Website</label></th>
    				<th><label>EmailID</label></th>
    				<th><label>Contact Number</label></th>
    				<!-- <th><label>company ID</label></th> -->
    				<th></th>
    			</tr>
    		</thead>
    		
			<tbody>
			<%
			
			//JSONObject ProdJson1 = new JSONObject(jsonProdRes1);
			//out.println(ProdJson1.getJSONArray("matchedinfo"));
		    //out.println(ProdJson1.getJSONArray("unmatchedinfo"));
		    //out.println(ProdJson1.getJSONArray("partmatchedinfo"));
		    //String jsonCatRes =(String) request.getAttribute("categoryData");
			//out.print(jsonCatRes);
			String jsonCompRes =(String) request.getAttribute("companyData");
			
			JSONObject compJson = new JSONObject(jsonCompRes);
			//out.print(compJson.toString());
			if(!compJson.toString().equals("{}")){
				JSONArray comparray = compJson.getJSONArray("mdmcompinfo");
			//	out.print(comparray);
				if(comparray.length() > 0){
					for(int i=0;i<comparray.length();i++){
						%>
					<tr>
					<td><input class="form-control" readOnly name="comp_name" id="comp_nameId" value="<%=comparray.getJSONObject(i).getString("compname") %>" type="text" placeholder="Name" /></td>
					<td><input class="form-control" readOnly name="comp_website" id="comp_websiteId" value="<%=comparray.getJSONObject(i).getString("compweb") %>" type="text" placeholder="Website" /></td>
					<td><input class="form-control" readOnly name="comp_email" id="comp_emailId" value="<%=comparray.getJSONObject(i).getString("compemail") %>" type="text" placeholder="admin@example.com" /></td>
					<td><input class="form-control" readOnly width="3" name="comp_numb" value="<%=comparray.getJSONObject(i).getString("compnumber") %>" id="comp_numbId" type="text" placeholder="contact"></td>
					<td><input class="form-control" readOnly width="3" name="compid" value="<%=comparray.getJSONObject(i).getString("compid") %>" id="companyPathId" type="hidden" placeholder="contact"></td>
					<td>
						<!-- <button class="btn btn-success btn-add" type="button">
							<i class="glyphicon glyphicon-plus gs"></i>
						</button> -->
					</td>
				</tr>	
				<% 	}
				}
				}else{%>
				<tr>
					<td><input class="form-control" name="comp_name" id="comp_nameId" type="text" placeholder="Name" /></td>
					<td><input class="form-control" name="comp_website" id="comp_websiteId" type="text" placeholder="Website" /></td>
					<td><input class="form-control" name="comp_email" id="comp_emailId" type="text" placeholder="admin@example.com" /></td>
					<td><input class="form-control" width="3" name="comp_numb" id="comp_numbId" type="text" placeholder="contact"></td>
					<td><input class="form-control" width="3" name="compid" id="companyPathId" type="hidden" placeholder="contact"></td>
					<td>
						<!-- <button class="btn btn-success btn-add" type="button">
							<i class="glyphicon glyphicon-plus gs"></i>
						</button> -->
					</td>
				</tr>
				
			<% }
			%>
							</tbody>
    	</table>
    	<%if(compJson.toString().equals("{}")){ %>
    	<input class="btn btn-info" type="button" value="Save Company" onclick="saveCompany()">
    	<%} %>
    	</form>
    	<!-- table 2 -->
    	<hr>
    	<h3>User</h3>

					<div class="table-responsive">

            <form action="">
            	
                
           <table class="text-center">
    		<thead>
    			<tr>
    				<th><label style="display:none">ID</label></th>
    				<th><label>Name</label></th>
    				<th><label>EmailID</label></th>
    				<th><label>Mobile</label></th>
    				<th><label>Admin</label></th>
    				
    				<th></th>
    			</tr>
    		</thead>
    		
			<tbody id="userbody">
			<%
			String jsonUserRes =(String) request.getAttribute("userList");
		//	out.print("jsonUserRes  "+jsonUserRes);
			JSONObject userJson = new JSONObject(jsonUserRes);
			
			if(!userJson.toString().equals("{}")){
		//		out.print("jsonUserRes if ");
				JSONArray userarray = userJson.getJSONArray("mdminfo");
				if(userarray.length() > 0){
					for(int k=0;k<userarray.length();k++){
						%>
						<tr class="">
						<td class=""><input class="form-control"  name="userid" value="<%=userarray.getJSONObject(k).getString("id") %>" type="hidden" placeholder="id" /></td>
					<td class=""><input class="form-control" disabled="true" name="username" value="<%=userarray.getJSONObject(k).getString("name") %>" type="text" placeholder="Name" /></td>
					<td><input class="form-control lg" disabled="true" name="useremail" value="<%=userarray.getJSONObject(k).getString("email") %>" type="email" placeholder="admin@example.com" /></td>
					<td><input class="form-control" disabled="true" name="usermobile" type="number" value="<%=userarray.getJSONObject(k).getString("number") %>" placeholder="Mobile"></td>
					<td> <div class="" >
				    <label class="">
				    <%
				    if(userarray.getJSONObject(k).getString("isadmin").equals("1")){ %>
				    <input type="radio" name="useradmin" checked value="yes">
				    <% }else{%>
				    <input type="radio" name="useradmin"  value="yes">
				    <%} %> 
				    </label>
				    
				  </div></td>
					<td>
					<%if(k == userarray.length() - 1){ %>
						<button class="btn btn-success btn-add" type="button">
							<i class="glyphicon glyphicon-plus gs"></i>
						</button>
						<%}else{ %>
						<button class="btn btn-remove btn-danger" type="button">
							<i class="glyphicon glyphicon-minus gs"></i>
						</button>
						<%} %>
					</td>
				</tr>
					<%	}
						}
				}else{%>
				<tr class="">
				<td class=""><input class="form-control"  name="userid" value="" type="hidden" placeholder="id" /></td>
					<td class=""><input class="form-control"  name="username" type="text" placeholder="Name" /></td>
					<td><input class="form-control lg"  name="useremail" type="email" placeholder="admin@example.com" /></td>
					<td><input class="form-control"  name="usermobile" type="number" placeholder="Mobile"></td>
					<td> <div class="" >
				    <label class="">
				    <input type="radio" name="useradmin" value="yes"> 
				    </label>
				    
				  </div></td>
					<td>
						<button class="btn btn-success btn-add" type="button">
							<i class="glyphicon glyphicon-plus gs"></i>
						</button>
					</td>
				</tr>
				
			<%}
			%>
							</tbody>
    	</table>
    	<input class="btn btn-info" type="button" onclick="saveUser()" value="Save User" name="">
    	<input class="btn btn-info" type="button" onclick="updateUser()" value="Update User" name="">
    	</form>
</div>

				</div>
			</div>

			<div id="upload" class='tab-page tab-pane fade in '>
					<h2 class="text-center">Master Data Upload</h2>
			
			<div class="row">
			<h3 class="">Download Templates</h3>
				<a class="btn btn-info" href="http://35.236.154.164:8078/ServiceLogging/xls/product.xls" download="product.xls" >Product</a>
				<a class="btn btn-info" href="http://35.236.154.164:8078/ServiceLogging/xls/Address.xls"  download="Address.xls">Address</a>
				<a class="btn btn-info" href="http://35.236.154.164:8078/ServiceLogging/xls/customervendor.xls"  download="customervendor.xls">Customer/Vendor</a>
				<a class="btn btn-info" href="http://35.236.154.164:8078/ServiceLogging/xls/vendoritem.xls"  download="vendoritem.xls">VendorItem Mapping</a>
				
			</div>
			<div class="row">
				<h3>Upload Data</h3>
				<div class="container">
      <div class="panel panel-default">
        <div class="panel-heading"><strong>Upload Files</strong></div>
        <div class="panel-body">

          <!-- Standar Form -->
          <h4 class="text-center">Select files from your computer</h4>
          <!-- <form action="" method="post" enctype="multipart/form-data" id="js-upload-form">
            <div class="form-inline">
              <div class="form-group">
                <input type="file" name="myFile" id="js-upload-files" multiple>
              </div>
              <button type="submit" class="btn btn-sm btn-info" id="js-upload-submit">Upload files</button>
            </div>
          </form> -->
				<!-- IMPORTANT:  FORM's enctype must be "multipart/form-data" -->
	<form action="<%=request.getContextPath()%>/mdmUploadRecords" method="post" enctype="multipart/form-data">
            	
           <input type="hidden" name="serviceId1" value="<%=request.getParameter("serviceId")%>" id="serviceId1" />    
           <input type="hidden" name="userId1" value="<%=request.getRemoteUser()%>" id="userId1" />
           <input type="hidden" name="quantity1" value="<%=request.getParameter("val")%>" id="quantity1" />
           <input type="hidden" name="productCode1" value="<%=request.getParameter("productCode")%>" id="productCode1" />             
           <table class="table text-center">
    		
    		
			<tbody id="userbody1">
				<tr>
					<td ><div style="display: inline-block" class="text-left"><input type="file" name="mdmupload"></div><div style="display: inline-block" class="text-left"><button id="btn-add" class="btn btn-success btn-add1" type="button"><i class="glyphicon glyphicon-plus gs"></i>
						</button></div>
					</td>
					
				</tr>
			</tbody>
    	</table>
   
	
          <!-- Drop Zone -->
         <!--  <h4>Or drag and drop files below</h4>
          <div class="upload-drop-zone" id="drop-zone">
            Just drag and drop files here
          </div> -->

          <!-- Progress Bar -->
          

          <!-- Upload Finished -->
          <div style="display:none" class="js-upload-finished">
            <h3 class="text-center">Uploaded Files</h3>
            <div class=" table">
              <table id="listfiles" class=" table stripped">
              	<tr class="info">
              		<td>
              			<div class="pull-left">
              				<span><i class="fa fa-file-text" aria-hidden="true"></i>
							</span> <strong>File Name</strong>
              			</div>
              			<div class="pull-right">
              				<span><strong>Remove</strong></span>
              			</div>
              		</td>
              		
              	</tr>
              	
              </table>

            </div>
          </div>
          <div>
          	<h3 class="text-center ">Summary</h3>
			<div class="row table">
				<table class="table table-condensed">
					<tr class="info">
						<td>MDM Service Balance</td>
						<td><%=request.getParameter("val")%> Record</td>
						
					</tr>
					<%
					String totalR = "";
					int total = 0;
					int remaining = 0;
					String productCount = "0";
					String addrCount = "0";
					String cvCount = "0";
					String vendorItemCount = "0";
					ReadXlsImpl objReadXlsImpl = null;
					DB db = null;
					try{
					 objReadXlsImpl = new ReadXlsImpl();
					//out.println(request.getParameter("serviceId"));
					db = objReadXlsImpl.getMongoDb();
					//out.println(db);
					totalR = objReadXlsImpl.getProcessLength(db,request.getParameter("serviceId"));
					String[] processlength = totalR.split(",");
					 productCount = processlength[0];
					 addrCount = processlength[1];
					 cvCount = processlength[2];
					vendorItemCount = processlength[3];
					total = Integer.parseInt(productCount) + Integer.parseInt(addrCount) + Integer.parseInt(cvCount) + Integer.parseInt(vendorItemCount); 
					//out.println(total);
					if(total > 0){
					 remaining = Integer.parseInt(request.getParameter("val")) - total;
					}else {
						remaining = Integer.parseInt(request.getParameter("val"));
					}
					}catch(Exception e){
						//out.println("message "+e.getMessage());
					}
					%>
					<tr class="info">
						<td>Data Processed</td>
						<td><%=total %> Record</td>
					</tr>
					<tr class="info">
						<td>Remaining Balance</td>
						<td><%=remaining %> Record</td>
					</tr>
				</table>
			</div>
				<div class="row text-center">	
				<a class="btn btn-info" href="">Cancel</a>
				<input class="btn btn-info" type="submit" value="Process">
				<!-- <a class="btn btn-info" href="">Process</a> -->
             </div>
              </form>
          </div>
        </div>
      </div>
    </div> <!-- /container -->
			</div>
			
				
			</div>


<div id="assignsmes" class='tab-page tab-pane fade in'>
				<div class="container">
					<div class="row">
						<h2 class="text-center">Assign SMEs</h2>
						<br>
						<br>
						<div class="table-responsive col-xs-4 col-md-offset-4 col-xs-offset-1">
							 		<table >
							 			<tr>
							 				
							 				
							 			 <tr>
									        <td>    
									            <table class=" table-bordered">
									                <tr><th class="text-center" colspan="2">SME Allocation</th></tr>
									                <%
									                //DB db = objReadXlsImpl.getMongoDb();
													//out.println(db);
													String smecount = objReadXlsImpl.getAssignedLength(db,request.getParameter("serviceId"));
													String[] assignedSme = smecount.split("~");
									                %>
									                <tr>
									                    <td>Assigned</td>
									                    <td><%=assignedSme[0] %></td>
									                </tr>
									                <tr>
									                    <td>Unassigned</td>
									                    <td><%=assignedSme[1] %></td>
									                </tr>
									            </table><!--end of inner table-->
									        </td>
									        <td style="vertical-align:middle;">
									            <a class="btn btn-info btn-xs btnviewrecords" href="">view assigned records</a>
									            <!-- <button class="btn btn-info btn-xs"></button> -->
									        </td>
									    </tr>
							 		</table>

							 	</div><!--end of table div -->
							 	<div class="clearfix"></div>
							 	<div id="assignrecords" hidden>
	<!-- view assign records will display in this div after click-->
									<table class="table">
									  <tr>
									    <th>UserId</th>
									    <th>Product</th>
									    <th>address</th>
									    <th>customer</th>
									    <th>vendor-item</th>
									    <th>total</th>
									  </tr>
									  <%
									  String jsonAssignedRes = objReadXlsImpl.getAssignedSummary(db,request.getParameter("serviceId"));
									 // out.print(jsonAssignedRes);
									  JSONObject obJsonAssign = new JSONObject(jsonAssignedRes);
									  String userId = "";
									  String productassign = "";
									  String productappr = "";
									  String addressassign = "";
									  String addressappr = "";
									  String customerassign = "";
									  String customerappr = "";
									  String vendoritemassign = "";
									  String vendoritemappr = "";
										if(!obJsonAssign.toString().equals("{}")){
											JSONArray arrJsonAssign = obJsonAssign.getJSONArray("users");
											if(arrJsonAssign.length() > 0){
											for(int asgn = 0;asgn<arrJsonAssign.length();asgn++){
												 userId = arrJsonAssign.getJSONObject(asgn).getString("userId");
												 productassign = arrJsonAssign.getJSONObject(asgn).getString("productassign");
												 productappr = arrJsonAssign.getJSONObject(asgn).getString("productappr");
												 addressassign = arrJsonAssign.getJSONObject(asgn).getString("addressassign");
												 addressappr = arrJsonAssign.getJSONObject(asgn).getString("addressappr");
												 customerassign = arrJsonAssign.getJSONObject(asgn).getString("customerassign");
												 customerappr = arrJsonAssign.getJSONObject(asgn).getString("customerappr");
												 vendoritemassign = arrJsonAssign.getJSONObject(asgn).getString("vendoritemassign");
												 vendoritemappr = arrJsonAssign.getJSONObject(asgn).getString("vendoritemappr");
										//		int totalAssign = Integer.parseInt(productassign)+Integer.parseInt(addressassign)+Integer.parseInt(customerassign)+Integer.parseInt(vendoritemassign);
										//		int totalAppr = Integer.parseInt(productappr)+Integer.parseInt(addressappr)+Integer.parseInt(customerappr)+Integer.parseInt(vendoritemappr);
												%>
									 <tr>
									    <td><%=arrJsonAssign.getJSONObject(asgn).getString("userId") %></td>
									    <td><%=productassign %>/<%=productappr %> <input class="btn btn-info btn-xs" onclick="unassigned('products','<%=userId %>')" value = "Unassigned"/> </td>
									    <td><%=addressassign %>/<%=addressappr %> <input class="btn btn-info btn-xs" onclick="unassigned('address','<%=userId %>')" value = "Unassigned"/></td>
									    <td><%=customerassign %>/<%=customerappr %> <input class="btn btn-info btn-xs" onclick="unassigned('customervendor','<%=userId %>')" value = "Unassigned"/></td>
									    <td><%=vendoritemassign %>/<%=vendoritemappr %> <input class="btn btn-info btn-xs" onclick="unassigned('vendormapping','<%=userId %>')" value = "Unassigned"/></td>
									    
									    </tr>
									  <%}
											}	
											}%>
									  <!-- <tr>
									    <td>ak</td>
									    <td>0/100 </td>
									    <td></td>
									    <td></td>
									    <td></td>
									    <td></td>
									  </tr>
									  <tr>
									    <td>AKMISHRA</td>
									    <td>50/50 </td>
									    <td></td>
									    <td></td>
									    <td></td>
									    <td></td>
									  </tr>
									  <tr>
									    <td>Total</td>
									    <td>250/750 </td>
									    <td></td>
									    <td></td>
									    <td></td>
									    <td></td>
									  </tr> -->
									</table>
							 	</div>
							 <hr>
						<br>
<!-- assignment inner tabs start here -->
				<div class="bs-example bs-example-tabs "  style="height:500px; display: flex; flex-direction: column;border-radius: 15px: ">
			      <ul id="myTab" class="nav nav-tabs " style="flex: 0 0 auto">
			        <li class="active"><a href="#percent" data-toggle="tab"> Assign By Percentage</a></li>
			        <li class=""><a href="#category" data-toggle="tab"> Assign By Category</a></li>
			        <!-- <li class=""><a href="#type" data-toggle="tab"> Assign by Type</a></li> -->
			        
      			  </ul>
      			<div class="tab-content">
 <!-- assign by percent -->
				  <div id="percent" class='tab-page tab-pane fade in active'>
					<br><br><br>
				  	<div class="table-responsive">
					<table class="table">
			    		<thead>
			    			<tr>
			    				<th colspan="3" class="text-center"><label>assign by percentage</label></th>
			    			</tr>
			    		</thead>
    				
						<tbody>
							
							<%
							
			String jsonUserResAssignPercent =(String) request.getAttribute("userList");
			//out.print(jsonUserRes);
			JSONObject userJsonAssignPercent = new JSONObject(jsonUserResAssignPercent);
			
			if(!userJsonAssignPercent.toString().equals("{}")){
				JSONArray userarrayAssignPercent = userJsonAssignPercent.getJSONArray("mdminfo");
				if(userarrayAssignPercent.length() > 0){
					for(int k=0;k<userarrayAssignPercent.length();k++){
						%>
						<tr class="">
								<td>
								<input class="form-control"  name="assignuserid" value="<%=userarrayAssignPercent.getJSONObject(k).getString("id") %>" type="hidden" placeholder="id" />
									<label name="assignusername"><%=userarrayAssignPercent.getJSONObject(k).getString("name") %></label>
								</td>
								<td class=""><label name="assignuseremail"><%=userarrayAssignPercent.getJSONObject(k).getString("email") %></label></td>
								<td>
									<input class="form-control "  name="assignpercent" type="text" placeholder="percentage" />
								</td>
								<td>
									<!--<input class="form-control "  name="assigntype" type="text" placeholder="product,address etc" />  -->
									<select class="type form-control" name="assigntype" multiple="multiple">
									<option value="product">product</option>
									<option value="address">address</option>
									<option value="customer/vendor">customer/vendor</option>
									<option value="vendoritem">vendoritem</option>
        							</select>
        							<script type="text/javascript">
    								$('.type').fSelect();
    								</script>
								</td>
							</tr>
						<%	}
						}
				}else{%>
				<tr class="">
								<td>
									<label>No User found.Please add user and assign.</label>
								</td>
								
				</tr>
				<%}%>
							<!--  <tr class="">
								<td>
									<label>AK</label>
								</td>
								<td class=""><label>akashmishra@gmail.com</label></td>
								<td>
									<input class="form-control "  name="percent" type="text" placeholder="%percentage%" />
								</td>
							</tr>
							<tr class="">
								<td>
									<label>Akmishra</label>
								</td>
								<td class=""><label>akashmishra@gmail.com</label></td>
								<td>
									<input class="form-control "  name="percent" type="text" placeholder="%percentage%" />
								</td>
							</tr>-->
						</tbody>
    				</table>
    				</div>
    				         <!--  <div class="">	
								
								<input class="btn btn-info" type="button" value="Allocate" onclick="allocateByPercentType()">
								
				             </div>-->
				     
				     		  	<div class="row text-center">	
						<input class="btn btn-info" type="button" value="Allocate" onclick="allocateByPercentType()"/>
						<button class="btn btn-info" data-toggle="modal" data-target="#previewpercent" onclick="getPercentPreviewData()">Preview</button>
						  <!--assign by percent Modal -->
  <div class="modal fade" id="previewpercent" role="dialog">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
        <table class="table-bordered table-hover table-striped ">
 			<tr>
 				<th class="text-center" colspan="2">SME Allocation</th>
 				
 			</tr>
 			<tbody>
 				<tr>
 					<td>Assigned</td>
 					<td id="assignpercentId"><%=assignedSme[0] %></td>
 				</tr>
 				<tr>
 					<td>Unassigned</td>
 					<td id="unassignpercentId"><%=assignedSme[1] %></td>
 				</tr>
 				</tbody>
 				
 		</table>
 	
 	
 <hr>
          <table class="table-bordered table-responsive">
			  <tr>
			    <th class="text-center">#</th>
			    <th class="text-center">Product</th>
			    <th class="text-center">address</th>
			    <th class="text-center">customer</th>
			    <th class="text-center">Vendor-item</th>
			    <th class="text-center">Total</th>
			  </tr>
								 <% if(!obJsonAssign.toString().equals("{}")){
											JSONArray arrJsonAssign = obJsonAssign.getJSONArray("users");
											if(arrJsonAssign.length() > 0){
											for(int asgn = 0;asgn<arrJsonAssign.length();asgn++){
												 userId = arrJsonAssign.getJSONObject(asgn).getString("userId");
												 String emailIdP = userId.replaceAll("@","_");
												 productassign = arrJsonAssign.getJSONObject(asgn).getString("productassign");
												 productappr = arrJsonAssign.getJSONObject(asgn).getString("productappr");
												 addressassign = arrJsonAssign.getJSONObject(asgn).getString("addressassign");
												 addressappr = arrJsonAssign.getJSONObject(asgn).getString("addressappr");
												 customerassign = arrJsonAssign.getJSONObject(asgn).getString("customerassign");
												 customerappr = arrJsonAssign.getJSONObject(asgn).getString("customerappr");
												 vendoritemassign = arrJsonAssign.getJSONObject(asgn).getString("vendoritemassign");
												 vendoritemappr = arrJsonAssign.getJSONObject(asgn).getString("vendoritemappr");
										//		int totalAssign = Integer.parseInt(productassign)+Integer.parseInt(addressassign)+Integer.parseInt(customerassign)+Integer.parseInt(vendoritemassign);
										//		int totalAppr = Integer.parseInt(productappr)+Integer.parseInt(addressappr)+Integer.parseInt(customerappr)+Integer.parseInt(vendoritemappr);
												%>
									 <tr>
									    <td><%=arrJsonAssign.getJSONObject(asgn).getString("userId") %></td>
									    <td id="<%=emailIdP%>product"><%=productassign %>/<%=productappr %></td>
									    <td id="<%=emailIdP%>addr"><%=addressassign %>/<%=addressappr %></td>
									    <td id="<%=emailIdP%>customer"><%=customerassign %>/<%=customerappr %></td>
									    <td id="<%=emailIdP%>vendor"><%=vendoritemassign %>/<%=vendoritemappr %></td>
									    
									    </tr>
									  <%}
											}	
											}%>
			</table>
        </div>
        <div class="modal-footer">
          <input class="btn btn-info" type="button" value="Allocate" onclick="allocateByPercentType()">
          <button type="button" class="btn btn-info" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
				     
				             
				             	
				  	</div>
				  </div>
<!-- assign by category -->
				  <div id="category" class='tab-page tab-pane fade in '>
					<br><br><br>
				  	<div class="table-responsive">
					<table class="table">
			    		<thead>
			    			<tr>
			    				<!-- <th colspan="2" class="text-center"><label>assign by percentage</label></th> -->
			    				<th>Name</th>
			    				<th>Email</th>
			    				<th>Level 4</th>
			    				<th>Level 3</th>
			    				<th>Level 2</th>
			    				<th>Level 1</th>
			    			</tr>
			    		</thead>
    				
						<tbody>
							
											<%
											
			String jsonUserResAssignCat =(String) request.getAttribute("userList");
			//out.print(jsonUserRes);
			JSONObject userJsonAssignCat = new JSONObject(jsonUserResAssignCat);
			
			if(!userJsonAssignCat.toString().equals("{}")){
				JSONArray userarrayAssignCat = userJsonAssignCat.getJSONArray("mdminfo");
				if(userarrayAssignCat.length() > 0){
					for(int k=0;k<userarrayAssignCat.length();k++){
						%>
						<tr class="">
								<td>
								<input class="form-control"  name="assigncatuserid" value="<%=userarrayAssignCat.getJSONObject(k).getString("id") %>" type="hidden" placeholder="id" />
									<label name="assigncatusername"><%=userarrayAssignCat.getJSONObject(k).getString("name") %></label>
								</td>
								<td class="" ><label name="assigncatemail"><%=userarrayAssignCat.getJSONObject(k).getString("email") %></label></td>
								<td>
									<!--  <input class="form-control "  name="assigncategory3" type="text" placeholder="" />-->
									<%
									String jsonCatRes3 =(String) request.getAttribute("categoryData");
									//out.print(jsonCatRes);
									JSONObject jsonCatArr3 = new JSONObject(jsonCatRes3);
									if(!jsonCatRes3.toString().equals("{}")){
									if(jsonCatRes3.indexOf("level4") != -1){
									JSONArray catarray3 = jsonCatArr3.getJSONArray("level4");
							//	out.print(comparray);
									if(catarray3.length() > 0){%>
									<select class="test test4 form-control" name="assigncategory4" multiple="multiple" >
									<%for(int level4=0;level4<catarray3.length();level4++){ %>
        							<option value="<%=catarray3.getJSONObject(level4).getString("level4desc")%>"><%=catarray3.getJSONObject(level4).getString("level4desc")%></option>
        							<%} %>
    								</select>
    								<%}
									}
									}
									%>
    								<script type="text/javascript">
    								$('.test4').fSelect();
    								</script>
								</td>
								<td>
									<!--  <input class="form-control "  name="assigncategory3" type="text" placeholder="" />-->
									<%
									String jsonCatRes2 =(String) request.getAttribute("categoryData");
									//out.print(jsonCatRes);
									JSONObject jsonCatArr2 = new JSONObject(jsonCatRes2);
									if(!jsonCatRes2.toString().equals("{}")){
									if(jsonCatRes2.indexOf("level3") != -1){
									JSONArray catarray2 = jsonCatArr2.getJSONArray("level3");
							//	out.print(comparray);
									if(catarray2.length() > 0){%>
									<select class="test test3 form-control" name="assigncategory3" multiple="multiple">
									<%for(int level3=0;level3<catarray2.length();level3++){ %>
        							<option value="<%=catarray2.getJSONObject(level3).getString("level3desc")%>"><%=catarray2.getJSONObject(level3).getString("level3desc")%></option>
        							<%} %>
    								</select>
    								<%}
									}
									}
									%>
    								<script type="text/javascript">
    								$('.test3').fSelect();
    								</script>
								</td>
								<td>
									<!-- <input class="form-control "  name="assigncategory2" type="text" placeholder="" /> -->
									<%
									String jsonCatRes1 =(String) request.getAttribute("categoryData");
									//out.print(jsonCatRes);
									JSONObject jsonCatArr1 = new JSONObject(jsonCatRes1);
									if(!jsonCatRes1.toString().equals("{}")){
									if(jsonCatRes1.indexOf("level2") != -1){
									JSONArray catarray1 = jsonCatArr1.getJSONArray("level2");
							//	out.print(comparray);
									if(catarray1.length() > 0){%>
									<select class="test test2 form-control" name="assigncategory2" multiple="multiple">
									<%for(int level2=0;level2<catarray1.length();level2++){ %>
        							<option value="<%=catarray1.getJSONObject(level2).getString("level2desc")%>"><%=catarray1.getJSONObject(level2).getString("level2desc")%></option>
        							<%} %>
    								</select>
    								<%}
									}
									}
									%>
    								<script type="text/javascript">
    								$('.test2').fSelect();
    								</script>
								</td>
								<td>
									<!-- <input class="form-control "  name="assigncategory1" type="text" placeholder="" /> -->
									<%
									String jsonCatResD =(String) request.getAttribute("categoryData");
									//out.print(jsonCatResD);
									JSONObject jsonCatArrD = new JSONObject(jsonCatResD);
									if(!jsonCatResD.toString().equals("{}")){
									if(jsonCatResD.indexOf("level1") != -1){
									JSONArray catarrayD = jsonCatArrD.getJSONArray("level1");
							//	out.print(comparray);
									if(catarrayD.length() > 0){%>
									<select class="test form-control" name="assigncategory1" multiple="multiple">
									<%for(int level1=0;level1<catarrayD.length();level1++){ %>
        							<option value="<%=catarrayD.getJSONObject(level1).getString("level1desc")%>"><%=catarrayD.getJSONObject(level1).getString("level1desc")%></option>
        							<%} %>
    								</select>
    								<%}
									}
									}
									%>
    								<script type="text/javascript">
    								$('.test').fSelect();
    								</script>
								</td>
								
								
								
							</tr>
								<%	}
						}
				}else{%>
				<tr class="">
								<td>
									<label>No User found.Please add user and assign.</label>
								</td>
								
				</tr>
				<%}%>
							<!--  <tr class="">
								<td>
									<label>AK</label>
								</td>
								<td class=""><label>akashmishra@gmail.com</label></td>
								<td>
									<input class="form-control "  name="percent" type="text" placeholder="" />
								</td>
								<td>
									<input class="form-control "  name="percent" type="text" placeholder="" />
								</td>
								<td>
									<input class="form-control "  name="percent" type="text" placeholder="" />
								</td>
								<td>
									<input class="form-control "  name="percent" type="text" placeholder="" />
								</td>
							</tr>
							<tr class="">
								<td>
									<label>Akmishra</label>
								</td>
								<td class=""><label>akashmishra@gmail.com</label></td>
								<td>
									<input class="form-control "  name="percent" type="text" placeholder="" />
								</td>
								<td>
									<input class="form-control "  name="percent" type="text" placeholder="" />
								</td>
								<td>
									<input class="form-control "  name="percent" type="text" placeholder="" />
								</td>
								<td>
									<input class="form-control "  name="percent" type="text" placeholder="" />
								</td>
							</tr>-->
						</tbody>
    				</table>
    				<div class="">	
								<!--<a class="btn btn-info" href="">Allocate</a>
								<a class="btn btn-info" href="">Reject</a>  -->
								<input class="btn btn-info" type="button" value="Allocate" onclick="allocateByCategory()"/>
						<button class="btn btn-info" data-toggle="modal" data-target="#previewcat" onclick="getCatPreviewData()">Preview</button>
											  <!--assign by percent Modal -->
  <div class="modal fade" id="previewcat" role="dialog">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
        <table class="table-bordered table-hover table-striped ">
 			<tr>
 				<th class="text-center" colspan="2">SME Allocation</th>
 				
 			</tr>
 			<tbody>
 				<tr>
 					<td>Assigned</td>
 					<td id="assignpercentIdC"><%=assignedSme[0] %></td>
 				</tr>
 				<tr>
 					<td >Unassigned</td>
 					<td id="unassignpercentIdC"><%=assignedSme[1] %></td>
 				</tr>
 				</tbody>
 				
 		</table>
 	
 	
 <hr>
          <table class="table-bordered table-responsive">
			  <tr>
			    <th class="text-center">#</th>
			    <th class="text-center">Product</th>
			    <th class="text-center">address</th>
			    <th class="text-center">customer</th>
			    <th class="text-center">Vendor-item</th>
			    <th class="text-center">Total</th>
			  </tr>
			 <% if(!obJsonAssign.toString().equals("{}")){
											JSONArray arrJsonAssign = obJsonAssign.getJSONArray("users");
											if(arrJsonAssign.length() > 0){
											for(int asgn = 0;asgn<arrJsonAssign.length();asgn++){
												 userId = arrJsonAssign.getJSONObject(asgn).getString("userId");
												 String emailIdC = userId.replaceAll("@","_");
												 productassign = arrJsonAssign.getJSONObject(asgn).getString("productassign");
												 productappr = arrJsonAssign.getJSONObject(asgn).getString("productappr");
												 addressassign = arrJsonAssign.getJSONObject(asgn).getString("addressassign");
												 addressappr = arrJsonAssign.getJSONObject(asgn).getString("addressappr");
												 customerassign = arrJsonAssign.getJSONObject(asgn).getString("customerassign");
												 customerappr = arrJsonAssign.getJSONObject(asgn).getString("customerappr");
												 vendoritemassign = arrJsonAssign.getJSONObject(asgn).getString("vendoritemassign");
												 vendoritemappr = arrJsonAssign.getJSONObject(asgn).getString("vendoritemappr");
										//		int totalAssign = Integer.parseInt(productassign)+Integer.parseInt(addressassign)+Integer.parseInt(customerassign)+Integer.parseInt(vendoritemassign);
										//		int totalAppr = Integer.parseInt(productappr)+Integer.parseInt(addressappr)+Integer.parseInt(customerappr)+Integer.parseInt(vendoritemappr);
												%>
									 <tr>
									    <td><%=arrJsonAssign.getJSONObject(asgn).getString("userId") %></td>
									    <td id="<%=emailIdC%>productc"><%=productassign %>/<%=productappr %></td>
									    <td id="<%=emailIdC%>addrc"><%=addressassign %>/<%=addressappr %></td>
									    <td id="<%=emailIdC%>customerc"><%=customerassign %>/<%=customerappr %></td>
									    <td id="<%=emailIdC%>vendorc"><%=vendoritemassign %>/<%=vendoritemappr %></td>
									    
									    </tr>
									  <%}
											}	
											}%>	
													</table>
        </div>
        <div class="modal-footer">
          <input class="btn btn-info" type="button" value="Allocate" onclick="allocateByCategory()">
          <button type="button" class="btn btn-info" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
						
						
				             </div>	
				  	</div>
				  </div>
				   <!-- assign by percent -->
				  <!--<div id="type" class='tab-page tab-pane fade in '>
					<br><br><br>
				  	<div class="table-responsive">
					<table class="table">
			    		<thead>
			    			<tr>
			    				<th colspan="3" class="text-center"><label>assign by type</label></th>
			    			</tr>
			    		</thead>
    				
						<tbody>
											<%--
			String jsonUserResAssignType =(String) request.getAttribute("userList");
			//out.print(jsonUserRes);
			JSONObject userJsonAssignType = new JSONObject(jsonUserResAssignType);
			
			if(!userJsonAssignType.toString().equals("{}")){
				JSONArray userarrayAssignType = userJsonAssignType.getJSONArray("mdminfo");
				if(userarrayAssignType.length() > 0){
					for(int k=0;k<userarrayAssignType.length();k++){
						%>
						<tr class="">
								<td>
								<input class="form-control"  name="userid" value="<%=userarrayAssignType.getJSONObject(k).getString("id") %>" type="hidden" placeholder="id" />
									<label><%=userarrayAssignType.getJSONObject(k).getString("name") %></label>
								</td>
								<td class=""><label><%=userarrayAssignType.getJSONObject(k).getString("email") %></label></td>
								<td>
									<input class="form-control "  name="assigntype" type="text" placeholder="product,address etc" />
								</td>
							</tr>
										<%	}
						}
				}else{%>
				<tr class="">
								<td>
									<label>No User found.Please add user and assign.</label>
								</td>
								
				</tr>
				<%}--%>
							
						</tbody>
    				</table>	
				  	</div>
				  </div>-->
					</div>
				</div>
				
				
				</div>  
			</div>
			</div><!-- end ofassignsmes-->
			
					</div>
	</div>
</header>
<section>
	
</section>	
















<!-- <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.3/jquery.min.js"></script> -->
<script  src="mdmscript/jquery.js"></script>
<script src="mdmscript/bootstrap.min.js"></script>


	<script type="text/javascript">
		$(function()
{
    $(document).on('click', '.btn-add', function(e)
    {
        e.preventDefault();

      //  var controlForm = $(this).closest('table'),
      //      currentEntry = $(this).parents('tr:first'),
      //      newEntry = $(currentEntry.clone()).appendTo(controlForm);
	var controlForm = $(this).closest('table'),

 newEntry = $('#userbody').append('<tr class=""><td class=""><input class="form-control"  name="userid" value="" type="hidden" placeholder="id" /></td><td class=""><input class="form-control"  name="username" type="text" placeholder="Name" /></td><td><input class="form-control lg"  name="useremail" type="email" placeholder="admin@example.com" /></td><td><input class="form-control"  name="usermobile" type="number" placeholder="Mobile"></td><td> <div class="" ><label class=""><input type="radio" name="useradmin" value="yes"></label></div></td><td><button class="btn btn-success btn-add" type="button"><i class="glyphicon glyphicon-plus gs"></i></button></td></tr>');

  controlForm.find('tr:not(:last) .btn-add')
            .removeClass('btn-add').addClass('btn-remove')
            .removeClass('btn-success').addClass('btn-danger')
            .html('<span class="glyphicon glyphicon-minus gs"></span>');
      
      //  newEntry.find('input').val('');
        controlForm.find('tr:not(:last) .btn-add')
            .removeClass('btn-add').addClass('btn-remove')
            .removeClass('btn-success').addClass('btn-danger')
            .html('<span class="glyphicon glyphicon-minus gs"></span>');
    }).on('click', '.btn-remove', function(e)
    {
		$(this).parents('tr:first').remove();

		e.preventDefault();
		return false;
	});
});

	</script>
	<script>
	$('#listfiles').on('click', 'a', function(){
	    $(this).closest ('tr').remove ();
	});
	
function myFunction(){
    var x = document.getElementById("filesToUpload");
    var txt = "";
    if ('files' in x) {
        if (x.files.length == 0) {
            txt = "Select one or more files.";
        } else {
            for (var i = 0; i < x.files.length; i++) {
                // txt += "<br><strong>" + (i+1) + ". file</strong><br>";
                var file = x.files[i];
                if ('name' in file) {
                    txt =  file.name + "<br>";
                    $('#listfiles').append('<tr class="info"><td><div class="pull-left" name="list"><span><i class="fa fa-file-text" aria-hidden="true"></i></span>'+txt+'</div><div class="pull-right"><a class="btn btn-info " data-toggle="tooltip" title="Delete"><i class=" fa fa-times" aria-hidden="true"></i> </a></div></td>')

                }
                // if ('size' in file) {
                //     txt += "size: " + file.size + " bytes <br>";
                // }
            }
        }
    } 
    else {
        if (x.value == "") {
            txt += "Select one or more files.";
        } else {
            txt += "The files property is not supported by your browser!";
            txt  += "<br>The path of the selected file: " + x.value; // If the browser does not support the files property, it will return the path of the selected file instead. 
        }
    }
}

</script>
<script type="text/javascript">
// upload button add

	$(function()
{

    $(document).on('click', '.btn-add1', function(e)
    {
        e.preventDefault();
 

         var controlForm = $(this).closest('table'),
            currentEntry = $(this).parents('tr:first'),
            newEntry = $(currentEntry.clone()).appendTo(controlForm);
				// newEntry = $('#userbody1').append('<tr class=""><td class=""><input type="file" name=""></td><td><button class="btn btn-success btn-add" type="button"><i class="glyphicon glyphicon-plus gs"></i></button></td></tr>');
        newEntry.find('input').val('');
        controlForm.find('tr:not(:last) .btn-add1')
            .removeClass('btn-add1').addClass('btn-remove1')
            .removeClass('btn-success').addClass('btn-danger')
            .html('<span class="glyphicon glyphicon-minus gs"></span>');
    }).on('click', '.btn-remove1', function(e)
    {
		$(this).parents('tr:first').remove();

		e.preventDefault();
		return false;
	});


});

	$(function()
			{
			   if(<%=request.getParameter("tab")%> != null){
			       //alert("if");
			       var tab = '<%=request.getParameter("tab")%>';
			       if(tab == "1"){
			           $("#basicli").addClass("active");
			           $("#uploadli").removeClass("active");
			           $("#assignli").removeClass("active");
			           $("#processli").removeClass("active");
			           $("#viewli").removeClass("active");
			           $("#mdmservice").addClass("active in");
			           $("#upload").removeClass("active in");
			           $("#assignsmes").removeClass("active in");
			           $("#process").removeClass("active in");
			           $("#view").removeClass("active in");
			       }else if(tab == "2"){
			           $("#uploadli").addClass("active");
			           $("#basicli").removeClass("active");
			           $("#assignli").removeClass("active");
			           $("#processli").removeClass("active");
			           $("#viewli").removeClass("active");
			           $("#upload").addClass("active in");
			           $("#mdmservice").removeClass("active in");
			           $("#assignsmes").removeClass("active in");
			           $("#process").removeClass("active in");
			           $("#view").removeClass("active in");
			       }else if(tab == "3"){
			           $("#assignli").addClass("active");
			           $("#basicli").removeClass("active");
			           $("#uploadli").removeClass("active");
			           $("#processli").removeClass("active");
			           $("#viewli").removeClass("active");
			           $("#assignsmes").addClass("active in");
			           $("#mdmservice").removeClass("active in");
			           $("#upload").removeClass("active in");
			           $("#process").removeClass("active in");
			           $("#view").removeClass("active in");
			       }else if(tab == "4"){
			           $("#processli").addClass("active");
			           $("#basicli").removeClass("active");
			           $("#assignli").removeClass("active");
			           $("#uploadli").removeClass("active");
			           $("#viewli").removeClass("active");
			           $("#process").addClass("active in");
			           $("#mdmservice").removeClass("active in");
			           $("#assignsmes").removeClass("active in");
			           $("#upload").removeClass("active in");
			           $("#view").removeClass("active in");
			       }else if(tab == "5"){
			           $("#viewli").addClass("active");
			           $("#basicli").removeClass("active");
			           $("#assignli").removeClass("active");
			           $("#processli").removeClass("active");
			           $("#uploadli").removeClass("active");
			           $("#view").addClass("active in");
			           $("#mdmservice").removeClass("active in");
			           $("#assignsmes").removeClass("active in");
			           $("#process").removeClass("active in");
			           $("#upload").removeClass("active in");
			       }else{
			           $("#basicli").addClass("active");
			           $("#uploadli").removeClass("active");
			           $("#assignli").removeClass("active");
			           $("#processli").removeClass("active");
			           $("#viewli").removeClass("active");
			           $("#mdmservice").addClass("active in");
			           $("#upload").removeClass("active in");
			           $("#assignsmes").removeClass("active in");
			           $("#process").removeClass("active in");
			           $("#view").removeClass("active in");
			       }
			   } else{
			       //alert("else");
			   }
			});

</script>
<script type="text/javascript">
	
	$(document).ready(function(){
//---------------------------------//
// checkall code for adrress subtabs
//--------------------------------//

		//checkbox address matched records
		$("#mytable1 #checkall1").click(function () {
	
	        if ($("#mytable1 #checkall1").is(':checked')) {
	            $("#mytable1 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable1 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });
		//checkbox for address Unmatched
    		$("#mytable2 #checkall2").click(function () {
	
	        if ($("#mytable2 #checkall2").is(':checked')) {
	            $("#mytable2 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable2 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });
    	//checkbox for address Partially matched records
    		$("#mytable3 #checkall3").click(function () {
	
	        if ($("#mytable3 #checkall3").is(':checked')) {
	            $("#mytable3 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable3 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });

//---------------------------------//
// checkall code for PRODUCT subtabs
//--------------------------------//

		//checkbox address matched records
		$("#mytable4 #checkall4").click(function () {
	
	        if ($("#mytable4 #checkall4").is(':checked')) {
	            $("#mytable4 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable4 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });
		//checkbox for address Unmatched
    		$("#mytable5 #checkall5").click(function () {
	
	        if ($("#mytable5 #checkall5").is(':checked')) {
	            $("#mytable5 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable6 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });
    	//checkbox for address Partially matched records
    		$("#mytable6 #checkall6").click(function () {
	
	        if ($("#mytable6 #checkall6").is(':checked')) {
	            $("#mytable6 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable6 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });

//---------------------------------//
// checkall code for VENDOR subtabs
//--------------------------------//

		//checkbox vendor matched records
		$("#mytable7 #checkall7").click(function () {
	
	        if ($("#mytable7 #checkall7").is(':checked')) {
	            $("#mytable7 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable7 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });
		//checkbox for vendor Unmatched
    		$("#mytable8 #checkall8").click(function () {
	
	        if ($("#mytable8 #checkall8").is(':checked')) {
	            $("#mytable8 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable8 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });
    	//checkbox for vendor Partially matched records
    		$("#mytable9 #checkall9").click(function () {
	
	        if ($("#mytable9 #checkall9").is(':checked')) {
	            $("#mytable9 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable9 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });

//---------------------------------//
// checkall code for CUSTOMER subtabs
//--------------------------------//

		//checkbox address matched records
		$("#mytable10 #checkall10").click(function () {
	
	        if ($("#mytable10 #checkall10").is(':checked')) {
	            $("#mytable10 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable10 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });
		//checkbox for address Unmatched
    		$("#mytable11 #checkall11").click(function () {
	
	        if ($("#mytable11 #checkall11").is(':checked')) {
	            $("#mytable11 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable11 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });
    	//checkbox for address Partially matched records
    		$("#mytable12 #checkall12").click(function () {
	
	        if ($("#mytable12 #checkall12").is(':checked')) {
	            $("#mytable12 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable12 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });


   
});

</script>
<script type="text/javascript">
	$(".btnviewrecords").click(function(e){
		e.preventDefault();
    $("#assignrecords").removeAttr("hidden");
});
	
	$('#previewpercent').on('hidden.bs.modal', function () {
		 //location.reload();
		 var url      = window.location.href; 
		 url = url + "&tab=3";
		 //alert(url);
		 location.replace(url);
		 
		});
	$('#previewcat').on('hidden.bs.modal', function () {
		 //location.reload();
		 var url      = window.location.href; 
		 url = url + "&tab=3";
		// alert(url);
		 location.replace(url);
		});
</script>

</body>
</html>