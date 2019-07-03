
<%@page import="java.util.Arrays,java.util.ArrayList,java.util.Iterator,java.util.List,java.text.SimpleDateFormat,java.util.Date"%>

<html>
<head>

<script src="js/jquery-1.9.1.js"></script>
<link rel="stylesheet" href="css/style.css" />
<link rel="stylesheet" href="css/form.css" /> 

<script>


function verifyUser(type,obj)
{
	if($(obj).val().indexOf('@')==-1)
		{
		
	var id='type='+type+'&userName='+$(obj).val();
	
	$.ajax({
        type: 'GET',
        url: '<%=request.getContextPath()%>/userPanel?type=verify',

					data : id,
					dataType : 'html',
					success : function(data) {
				if($(obj).val()!=null && $(obj).val()!="")	
					{
			if(data!='true')
				{	
				
			   $(obj).parent().next('td').next('td').html("Exist");	 
			  $(obj).parent().next('td').next('td').find('input[name="status"]').val("Exist");	 
				}
			else
				{			
				$(obj).parent().next('td').next('td').html("Not Exist");
				  $(obj).parent().next('td').next('td').find('input[name="status"]').val("Not Exist");	 
				}
					}
					}
				});
		}
	else
		{
		
		$(obj).parent().next('td').next('td').html("Invalid");
		
		}
	}

 
 function save(type)
{
	 var invalidUsers = [];
	var userValues = [];
	var existUsers = [];
	$('.user').each(function() {
		if ($(this).val() != "" && $(this).val() != null) {
			userValues.push($(this).val());
		}
	});
	
	 $('.userList').each(function() {
		if ($(this).val().indexOf('@')!=-1) {
			invalidUsers.push($(this).val());
		}
	}); 
	 $('.status').each(function() {
			if ($(this).val()=='Exist') {
				existUsers.push($(this).val());
			}
		}); 
	var hasDups = !userValues.every(function(v,i) {
		  return userValues.indexOf(v) == i;
		});
	
	if(hasDups==true)
	{
	alert("duplicate value exist please change it");	
		
	}
	else
		{
		
		var userList = [];
		$('.userList').each(function() {
			if ($(this).val() != "" && $(this).val() != null) {
				userList.push($(this).val());
			}
		});
		
		var passwordList = [];
		$('.passList').each(function() {
			if ($(this).val() != "" && $(this).val() != null) {
				passwordList.push($(this).val());
			}
		});
		
		if(invalidUsers.length==0)
			{
		if(existUsers==0)
			{
		if(userList!=null && userList!="")
			{
			if(passwordList!=null &&  passwordList!="" && (userList.length==passwordList.length))
				{
		var id='type='+type+'&person='+userList+'&password='+passwordList+'&productCode='+$("#productCode").val();
		
		 $("#submit").attr("disabled",true);
		 $.ajax({
             type: 'GET',
             url: '<%=request.getContextPath()%>/userPanel?type=saveAccount',

						data : id,
						dataType : 'html',
						success : function(data) {
							window.close();
            
						}
					});
				}
			else
				{
				alert("Please Enter Password");
				}
			}
		else
			{
			alert("Please Enter UserName");
			}
		
			}
		
		
		else
			{
			
			alert(" User Already Exist in Database");
			}
		
			}
		else
			{
			alert("Invalid User Exist");
			}
		}

	} 
</script>

<title>Insert title here</title>
</head>
<body>

<div>	
	<h1>Email Accounts</h1>
	
	<form name="form" id="form" method="GET">
	
	<input type="hidden" name="productCode" value="<%=request.getParameter("productCode")%>" id="productCode" />
	<div class="scroll-box">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="grid-table">
    <tr>
    <th>User Name</th>
    <th>Password</th>
    <th>Status</th>
    </tr>
	
		 <%
			List<String> list = (List<String>) request.getAttribute("assignedUserList");
		%>
 
		<%
			int val = Integer.parseInt(request.getParameter("val"));

			for (int i = 0; i < val; i++) {
				if (!list.isEmpty() && list.size() > i)
				{
		%>
		<tr>
		
		<td>
		<input type="text" name="Name" class="listVal user"  value="<%=list.get(i).toString() != null ? list.get(i) : ""%>" disabled="true"/></td>
		
		
		
	<td>
		<input type="password" name="Password" class="listVal" disabled="true" value="XXXXXXXXXX"/></td>
		<td>Added</td>
		</tr>
		<%} else {%>
		
		
		
		<tr>
		
		<td>
		<input type="text" name="userName" class="listVal user userList" onblur="verifyUser('verify',this)" /></td>
		
		
		
		<td>
		<input type="password" name="password" class="listVal  passList"/></td>
		<td><input type="hidden" class="status" name="status"/></td>
		
		</tr>
		<%
		
			}}
		%>
	
	</table>
	</div>
	<input type="button" value="submit" id="submit" onclick="save('saveAccount')" />
	</form>
	</div>
</body>
</html>