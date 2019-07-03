<html>
<head>
<script src="js/jquery-1.9.1.js"></script>
<script>

function getUserId(type)
{

var id='userName='+$("#userName").val()+'&productCode='+$("#productCode").val()+'&type='+type;

 $.ajax({
    type: 'GET',
    url: '<%=request.getContextPath()%>/userPanel?type=userId',

				data : id,
				dataType : 'html',
				success : function(data) {
					$("#reset").show();
					$("#submit").hide();
				 $("#userId").show().val(data);
         
				}
			}); 
	}
function formReset()
{
	 $("#userId").hide();
	 $("#submit").show();
	 $("#reset").hide();
     $("#formId").reset();	


}

</script>


</head>
<body>
<form id="formId">
<table>
<tr>
<th>Product Code</th>
<td><input type="text" name="productCode" id="productCode"/></td>

</tr>
<tr>
<th>User Name</th>
<td><input type="text" name="userName" id="userName"/></td>
</tr>
<tr><th>UserId</th><td><input type="text" name="userId" id="userId" style="display:none"/></td></tr>
<tr><td><input type="button" name="submit" value="submit" id="submit" onclick="getUserId('userId');"/></td><td><input type="reset" name="reset" value="reset" id="reset" onclick="formReset();" style="display:none"/></td></tr>
</table>
</form>

</body>
</html>