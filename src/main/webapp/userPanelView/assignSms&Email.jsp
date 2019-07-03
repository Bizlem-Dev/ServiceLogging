
<%@page
	import="java.util.Arrays,java.util.ArrayList,java.util.Iterator,java.util.List,java.text.SimpleDateFormat,java.util.Date"%>

<html>
<head>

<script src="js/jquery-1.9.1.js"></script>
<link rel="stylesheet" href="css/style.css" />
<link rel="stylesheet" href="css/form.css" />
<script src="js/jquery.autocomplete.min.js"></script>
<script>
var currencies = <%=request.getAttribute("list")%>;
$(function(){

	$('.autocomplete').autocomplete({
	    lookup: currencies,
	    onSelect: function (suggestion) {
	      var thehtml = '<strong>Currency Name:</strong> ' + suggestion.value + ' <br> <strong>Symbol:</strong> ' + suggestion.data;
	      $('#outputcontent').html(thehtml);
	    }
	  });
});

function addMore()
{
	/* $('#tableId tr:last').clone(true).insertAfter('#tableId tr:last'); */
	
	 $("#tableId").append("<tr><td>User Name</td><td><input type='text' name='name' class='autocomplete listVal'/></td></tr>"); 
	 $('.autocomplete').autocomplete({
		    lookup: currencies,
		    onSelect: function (suggestion) {
		      var thehtml = '<strong>Currency Name:</strong> ' + suggestion.value + ' <br> <strong>Symbol:</strong> ' + suggestion.data;
		      $('#outputcontent').html(thehtml);
		    }
		  });
	
	
	}

function save(type)
{
	
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
		if(personList!=null && personList!="")
			{
		var id='type='+type+'&person='+personList+'&productCode='+$("#productCode").val();
		
		 $.ajax({
             type: 'GET',
             url: '<%=request.getContextPath()%>/userPanel?type=save',

						data : id,
						dataType : 'html',
						success : function(data) {
							window.close();

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
	<input type="hidden" name="productCode"
		value="<%=request.getParameter("productCode")%>" id="productCode" />
	<div>
		<!-- OUR PopupBox DIV-->
		<h1>Assignd User</h1>
		<!-- a href="<%=request.getContextPath()%>/userPanel?type=callService" class="popupBoxClose"><img src="<%=request.getContextPath()%>/images/p.png" alt=""></a-->
		<form name="form" id="form">
			<div class="scroll-box">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					id="tableId" class="grid-table">
					<tr>
						<td>User Name</td>
						<td><input type="text" name="hh" class="listVal"
							value="<%=request.getRemoteUser()%>" disabled /></td>
					</tr>

					<%
			List<String> list = (List<String>) request.getAttribute("assignedUserList");
		%>

					<%
			
		if (!list.isEmpty() && list.size() > 0)
		{
			for (int i = 0; i < list.size(); i++) {
				 
		%>
					<tr>
						<td>User Name</td>
						<td><input type="text" name="hh" class="listVal"
							value="<%=list.get(i).toString() != null ? list.get(i) : ""%>"
							disabled="true" /></td>
					</tr>
					<%
			}}
		%>

					<tr>
						<td></td>
						<td><input type="button" value="Add more"
							onclick="addMore();" /></td>
					</tr>
					<!-- <tr>
		 <td>User Name</td>
		<td>
		<input type="text" name="name" class="autocomplete listVal" /></td></tr> -->
				</table>
			</div>
			<input type="button" value="submit" onclick="save('save');" />
		</form>
	</div>
</body>
</html>