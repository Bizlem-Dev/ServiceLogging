<%@page import="com.jindal.auction.domain.xsd.ProductConfigDetails"%>
<%@page import="com.jindal.auction.domain.xsd.CustomerServiceStatus"%>
<%@page import="com.userPanel.log.TestService"%>
<%@ taglib uri="http://www.extremecomponents.org" prefix="ec"%>
<%@page
	import="java.util.Arrays,java.util.ArrayList,java.util.Iterator,java.util.List,java.text.SimpleDateFormat,java.util.Date"%>


<html>

<head>
<title>Service Status</title>

<link rel="stylesheet" href="css/jquery-ui.css" type="text/css" />

<!--STYLESHEETS-->
<link href="css/table_css.css" rel="stylesheet" type="text/css" />

<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery-ui.js"></script>

<style type="text/css">
.eXtremeTable {
	margin: 0;
	padding: 0;
}

.eXtremeTable select {
	font-family: Verdana;
	font-size: 9px;
	border: solid 1px #EEE;
	width: 75px;
}

.eXtremeTable .tableRegion {
	border: 1px solid silver;
	padding: 2px;
	font-family: Verdana;
	font-size: 10px;
	margin-top: 7px;
}

.eXtremeTable .filter {
	background-color: #efefef;
}

.eXtremeTable .filter input {
	font-family: Verdana;
	font-size: 10px;
	width: 100%;
}

.eXtremeTable .filter select {
	font-family: Verdana;
	font-size: 9px;
	border: solid 1px #EEE;
	width: 100%;
}

.eXtremeTable .tableHeader {
	background-color: #308dbb;
	color: white;
	font-family: Verdana;
	font-size: 11px;
	font-weight: bold;
	text-align: left;
	padding-right: 3px;
	padding-left: 3px;
	padding-top: 4;
	padding-bottom: 4;
	margin: 0;
	border-right-style: solid;
	border-right-width: 1px;
	border-color: white;
}

.eXtremeTable .tableHeaderSort {
	background-color: #3a95c2;
	color: white;
	font-family: Verdana;
	font-size: 11px;
	font-weight: bold;
	text-align: left;
	padding-right: 3px;
	padding-left: 3px;
	padding-top: 4;
	padding-bottom: 4;
	border-right-style: solid;
	border-right-width: 1px;
	border-color: white;
}

.eXtremeTable .odd a,.even a {
	color: Black;
	font-size: 10px;
}

.eXtremeTable .odd td,.eXtremeTable .even td {
	padding-top: 2px;
	padding-right: 3px;
	padding-bottom: 2px;
	padding-left: 3px;
	vertical-align: middle;
	font-family: Verdana;
	font-size: 10px;
}

.eXtremeTable .odd {
	background-color: #FFFFFF;
}

.eXtremeTable .even {
	background-color: #dfe4e8;
}

.eXtremeTable .highlight td {
	color: black;
	font-size: 10px;
	padding-top: 2px;
	padding-right: 3px;
	padding-bottom: 2px;
	padding-left: 3px;
	vertical-align: middle;
	background-color: #fdecae;
}

.eXtremeTable .highlight a,.highlight a {
	color: black;
	font-size: 10px;
}

.eXtremeTable .toolbar {
	background-color: #F4F4F4;
	font-family: Verdana;
	font-size: 9px;
	margin-right: 1px;
	border-right: 1px solid silver;
	border-left: 1px solid silver;
	border-top: 1px solid silver;
	border-bottom: 1px solid silver;
}

.eXtremeTable .toolbar td {
	color: #444444;
	padding: 0px 3px 0px 3px;
	text-align: center;
}

.eXtremeTable .separator {
	width: 7px;
}

.eXtremeTable .statusBar {
	background-color: #F4F4F4;
	font-family: Verdana;
	font-size: 10px;
}

.eXtremeTable .filterButtons {
	background-color: #efefef;
	text-align: right;
}

.eXtremeTable .title {
	color: #444444;
	font-weight: bold;
	font-family: Verdana;
	font-size: 15px;
	vertical-align: middle;
}

.eXtremeTable .title span {
	margin-left: 7px;
}

.eXtremeTable .formButtons {
	display: block;
	margin-top: 10px;
	margin-left: 5px;
}

.eXtremeTable .formButton {
	cursor: pointer;
	font-family: Verdana;
	font-size: 10px;
	font-weight: bold;
	background-color: #308dbb;
	color: white;
	margin-top: 5px;
	border: outset 1px #333;
	vertical-align: middle;
}

.eXtremeTable .tableTotal {
	background-color: #FFFFFF;
	border-top: solid 1px Silver;
}

.eXtremeTable .tableTotalEmpty {
	background-color: #FFFFFF;
}
</style>


</head>


<body style="margin: 25px;">


	<form action="SServiceConsumption.jsp" method="post" name="myForm"
		id="myForm">

		<table>
			<tr>
				<td>Customer Id</td>
				<td><input type="text" name="cid" id="cid" /></td>
			</tr>
			<tr>
				<td>Product Id</td>
				<td><input type="text" name="pid" id="pid" /></td>
			</tr>

			<tr>
				<td>From Date</td>
				<td><input type="text" name="frmdate" id="frmdate" /></td>
			</tr>
			<tr>
				<td>To Date</td>
				<td><input type="text" name="todate" id="todate" /></td>

			</tr>
			<tr>
				<td>Status</td>
				<td><select name="status" id="status">
						<option value="">Select Status</option>
						<option value="active">active</option>
						<option value="inactive">inactive</option>

				</select><input type="submit" value="submit" />
			</tr>

		</table>
	</form>



	<%
		String customerId = "", productId = "", fromDate = "", toDate = "", status = "";
		if (request.getParameter("cid") != null)
			customerId = request.getParameter("cid");
		if (request.getParameter("pid") != null)
			productId = request.getParameter("pid");
		if (request.getParameter("frmdate") != null)
			fromDate = request.getParameter("frmdate");
		if (request.getParameter("todate") != null)
			toDate = request.getParameter("todate");
		if (request.getParameter("status") != null)
			status = request.getParameter("status");

		TestService ts = new TestService();
		List<CustomerServiceStatus> list = ts.getCustomerServiceStatus(
				customerId, productId, fromDate, toDate, status);
		request.setAttribute("pres", list);
		int i = 0;
	%>
	<ec:table items="pres" var="check"
		action="${pageContext.request.contextPath}/SServiceConsumption.jsp"
		imagePath="${pageContext.request.contextPath}/images/table/*.gif"
		title="Service Consumption" width="100%" rowsDisplayed="20"
		showExports="false">
		<!-- <ec:exportXls fileName="ServiceConsumption.xls" tooltip="Export Excel" />
		<ec:exportPdf fileName="ServiceConsumption.pdf" tooltip="Export PDF" 
			headerColor="white" headerBackgroundColor="gray"
			headerTitle="Service Consumption" />
		<ec:exportCsv fileName="ServiceConsumption.txt" tooltip="Export CSV"
			delimiter="|" />-->
		<ec:row>
			<ec:column property="customerId" sortable="false" />
			<ec:column property="customerName" sortable="false" />
			<ec:column property="productCode" sortable="false" />
			<ec:column property="productDescription" sortable="false" />
			<ec:column property="quantity" sortable="false" />
			<ec:column property="uom" sortable="false" />
			<ec:column property="consumptionQuantity" sortable="false" />
			<ec:column property="lastConsumptionDate" sortable="false" />
			<ec:column property="serviceEndDate" sortable="false" />
			<ec:column property="serviceStartDate" sortable="false" />
			<ec:column property="status" sortable="false" />
			<ec:column property="productConfigList" sortable="false"
				filterable="false" alias="Profuct config details">
				<%
					ProductConfigDetails[] ps = list.get(i)
										.getProductConfigList();
								List<ProductConfigDetails> pp = new ArrayList<ProductConfigDetails>(
										Arrays.asList(ps));
								request.setAttribute("press", pp);
				%>
				<ec:table items="press" var="checkk" showPagination="false"
					showStatusBar="false"
					action="${pageContext.request.contextPath}/SServiceConsumption.jsp"
					width="100%" rowsDisplayed="20" showExports="false">
					<ec:row>
						<ec:column property="categoryName" sortable="false"
							filterable="false"></ec:column>
						<ec:column property="productName" sortable="false"
							filterable="false"></ec:column>
						<ec:column property="quantity" filterable="false" sortable="false"></ec:column>
						<ec:column property="uomName" filterable="false" sortable="false"></ec:column>

					</ec:row>
				</ec:table>
			</ec:column>

		</ec:row>
	</ec:table>


</body>
</html>