<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<%@page
	import="java.util.ArrayList,java.util.Iterator,java.text.SimpleDateFormat,java.util.Date"%>

<head>
<meta charset="utf-8" />
<title>Logs</title>
<link rel="stylesheet" href="css/jquery-ui.css" />
<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery-ui.js"></script>
<script>
	$(function() {

		$("#tabs").tabs({

			beforeLoad : function(event, ui) {
				ui.jqXHR.error(function() {
					ui.panel.html("Loading...");
				});
			}
		});

	});
</script>
</head>
<body>

	<%
		String fromdate, todate;
		if (request.getParameter("fromDate") != null)
			fromdate = request.getParameter("fromDate");
		else {
			String pattern = "dd-MM-yyyy";
			fromdate = new SimpleDateFormat(pattern).format(new Date(System
					.currentTimeMillis() - 21L * 24 * 3600 * 1000));

		}
		if (request.getParameter("toDate") != null)
			todate = request.getParameter("toDate");
		else {
			String pattern = "dd-MM-yyyy";
			todate = new SimpleDateFormat(pattern).format(new Date());
		}
	%>

	<div id="tabs">
		<ul>

			<li><a href="#tabs-1">Service</a></li>
			


		</ul>

		<div id="tabs-1">
			<iframe width="100%" height="700" frameborder="0"
				src="ServiceConsumption.jsp"></iframe>
		</div>

		
	</div>


</body>
</html>