<%@page import="com.jindal.auction.domain.xsd.ProductConfigDetails"%>
<%@page import="com.jindal.auction.domain.xsd.CustomerServiceStatus"%>
<%@page import="com.userPanel.log.TestService"%>
<%@page import="com.userPanel.service.UserPanelService"%>
<%@page import="com.userPanel.service.impl.UserPanelServiceImpl"%>
<%@ taglib uri="http://www.extremecomponents.org" prefix="ec"%>
<%@page
	import="java.util.Arrays,java.util.ArrayList,java.util.Iterator,java.util.List,java.text.SimpleDateFormat,java.util.Date"%>


<html>

<head>
<title>Service Status</title>

<link rel="stylesheet" href="css/jquery-ui.css" type="text/css" />

<!--STYLESHEETS-->
<link href="css/datatable/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="css/datatable/dataTables.jqueryui.min.css" rel="stylesheet" type="text/css" />

<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery-ui.js"></script>
<script src="css/datatable/jquery-1.11.3.min.js"></script>
<script src="css/datatable/jquery.dataTables.min.js"></script>
<script src="css/datatable/dataTables.jqueryui.min.js"></script>



</head>
<script>
$(document).ready(function() {
    $('#example').DataTable();
} );
</script>

<body style="margin: 25px;">


				<table id="example" class="display" cellspacing="0" width="100%">
<thead>
            <tr>
                <th>Customer Name</th>
                <th>Product Code</th>
                <th>Quantity</th>
                <th>UOM</th>
                <th>Consumed Quantity</th>
                <th>Consumed Date</th>
            </tr>
        </thead>
					<%
						try {
							 UserPanelService userPanelService = new UserPanelServiceImpl();
							String a = 
				                    userPanelService.getReport(
				                            request.getParameter("userId"),
				                            request.getParameter("pid"),request.getParameter("fdate"),request.getParameter("tdate"));
				        	org.json.simple.JSONArray a1 = (org.json.simple.JSONArray) new org.json.simple.parser.JSONParser().parse(a);
				        							
							
							
										//out.print("kamal" + ps);
										if (a1 != null) {

											//out.print("enter"+ps[0]);
											//	List<ProductConfigDetails> pp = new ArrayList<ProductConfigDetails>(
											//			Arrays.asList(ps));
											//out.print(pp);
											//if(pp.size() >0){
											//int j = 1;
											out.println("<tbody>");
											org.json.simple.JSONObject a2 = null;
											for (int i=0;i<a1.size();i++) {
												a2 = (org.json.simple.JSONObject) new org.json.simple.parser.JSONParser().parse(a1.get(i).toString());
												out.println("<tr>");
												if (a2 != null) {
													
													if (a2.get("customer") != null)
														out.print("<td>"
																+ a2.get("customer")
																+ "</td>");
													else
														out.print("<td>-</td>");

													if (a2.get("service") != null)
														out.print("<td>"
																+ a2.get("service")
																+ "</td>");
													else
														out.print("<td>-</td>");

													if (a2.get("quantity") != null)
														out.print("<td>" + a2.get("quantity")
																+ "</td>");
													else
														out.print("<td>-</td>");

													if (a2.get("uom") != null)
														out.print("<td>" + a2.get("uom")
																+ "</td>");
													else
														out.print("<td>-</td>");
													if (a2.get("conquantity") != null)
														out.print("<td>" + a2.get("conquantity")
																+ "</td>");
													else
														out.print("<td>-</td>");
													
													if (a2.get("cndate") != null)
														out.print("<td>" + a2.get("cndate")
																+ "</td>");
													else
														out.print("<td>-</td>");

												} else
													out.print("<td>-</td>");

												out.println("</tr>");
											//	j++;
											}
											out.println("</tbody>");
											//	}

										}
									} catch (Exception e) {
										out.print(e);
										//	e.printStackTrace();
										//out.print(e.getMessage());
									}

								
					%>
				</table>

</body>
</html>