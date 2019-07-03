
<%@page import="java.util.Arrays,java.util.ArrayList,java.util.Iterator,java.util.List,java.text.SimpleDateFormat,java.util.Date"%>

<html>
<head>

<script src="js/jquery-1.9.1.js"></script>
<link rel="stylesheet" href="css/style.css" />
<link rel="stylesheet" href="css/form.css" /> 
<script src="js/jquery.autocomplete.min.js"></script>
<script>
$(function(){
	var currencies = <%=request.getAttribute("list")%>;
	$('.autocomplete').autocomplete({
	    lookup: currencies,
	    onSelect: function (suggestion) {
	      var thehtml = '<strong>Currency Name:</strong> ' + suggestion.value + ' <br> <strong>Symbol:</strong> ' + suggestion.data;
	      $('#outputcontent').html(thehtml);
	    }
	  });
});




function save(type)
{
	var companyid= document.getElementById('companyId').value;
	//alert(companyid);
	var sel = document.getElementById("companyId");
	var companyname= sel.options[sel.selectedIndex].text;
	//alert(companyname);
	//have to call ajex to get company having given service id
	
	
	var values = [];
	$('.listVal').each(function() {
		if ($(this).val() != "" && $(this).val() != null) {
			values.push($(this).val());
		}
	});
	
	
	var hasDups = !values.every(function(v,i) {
		  return values.indexOf(v) == i;
		});
	
	if(hasDups==true)
	{
	alert("duplicate value exist please change it");	
		
	}
	else
		{
				
		var personList = [];
		$('.autocomplete').each(function() {
			if ($(this).val() != "" && $(this).val() != null) {
				personList.push($(this).val());
			}
		});
		
		alert("1111");	

		if(personList!=null && personList!="")
			{
		//	alert("inside if");
			
		var id='type='+type+'&person='+personList+'&productCode='+$("#productCode").val()+'&serviceId='+$("#serviceId").val()+'&companyname='+companyname+'&companyId='+companyid;
		//alert("person----"+personList);
		//alert("productCode----"+$("#productCode").val());
		//alert("companyid----"+companyid);
		//alert("serviceId----"+$("#serviceId").val());
		//alert("companyname----"+companyname);
		 $.ajax({
             type: 'GET',
             url: '<%=request.getContextPath()%>/userPanel?type=saveCallMB',

						data : id,
						dataType : 'html',
						success : function(data) {
							//alert(data);
							//window.close();
							console.log("accessflag"+data);
							//{"accessFlag":"true"}
							var res = JSON.parse(data);
							console.log("result"+res.accessFlag);
							console.log("result"+res.emailid);
							//if(){
								//if(confirm('Successful Message')){
								  //  window.location.reload();  
								//}	
						//	}else(){
								
						//	}
							//accessflag{"accessFlag":"true","emailid":[]}
							//alert("emailid----  "+ res.emailid);
							if(res.accessFlag == "true" && res.emailid.length !=0){
							var d = confirm("this emailid "+res.emailid + " is already configured please add another email id");
							if(d){
								location.reload();	
							}else{
								location.reload();
							}
							
							}else if(res.accessFlag == "false"){
								alert("your limit is exceeded");
							}else{
								if(res.accessFlag == "true"){
									location.reload();
								}else{
									alert("Sorry currently we are facing issue please try later");
								}
								
							}
						//	if(data == "true"){
							//	location.reload();	
					//		}else if(data == "false"){
							//	alert("Not Configured");
						//	}
							

						}
					});
			}
		else
			{
			alert("Please Select user");
			}

		}


	}
</script>

<title>Insert title here</title>
</head>
<body>



<input type="hidden" name="productCode" value="<%=request.getParameter("productCode")%>" id="productCode" />
<input type="hidden" name="serviceId" value="<%=request.getParameter("serviceId")%>" id="serviceId" />
<div>	<!-- OUR PopupBox DIV-->
	<h1>Assignd User</h1>
	<!-- a href="<%=request.getContextPath()%>/userPanel?type=callService" class="popupBoxClose"><img src="<%=request.getContextPath()%>/images/p.png" alt=""></a-->
	<form name="form" id="form">
	<div class="scroll-box">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="grid-table">
    
    <tr>
         <td>Select Company</td>
        <td><%out.print(request.getAttribute("companylist"));%></td>
    </tr>
        
	<tr>
         <td>User Name</td>
        <td>
        <input type="text" name="hh" class="listVal"
            value="<%=request.getRemoteUser()%>"
            disabled /></td></tr>
	
		<%
			List<String> list = (List<String>) request.getAttribute("assignedUserList");
		%>

		<%
			int val = Integer.parseInt(request.getParameter("val"));

			for (int i = 0; i < val; i++) {
				if (!list.isEmpty() && list.size() > i) {
		%>
		<tr>
		 <td>User Name</td>
		<td>
		<input type="text" name="hh" class="listVal"
			value="<%=list.get(i).toString() != null ? list.get(i) : ""%>"
			disabled="true" /></td></tr>
		<%
			} else {
		%>
		<tr>
		 <td>User Name</td>
		<td>
		<input type="text" name="name" class="autocomplete listVal" /></td></tr>
		<%
			}
			}
		%>
	
	</table>
	</div>
	<input type="button" value="Save" onclick="save('save');" />
	
	</form>
	</div>
</body>
</html>