<%@ page contentType="text/html;charset=UTF-8" language="java"

%>

<% String ss1=(String)application.getAttribute("name"); %>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Java Date Picker</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script language="javascript">
function getclient_id(){
<% String str =(String)request.getAttribute("client_id"); %>
return "<%=str%>";
}
function getapp_id(){
<% String str2 =(String)request.getAttribute("app_id"); %>
return "<%=str2%>";
}
function invite_id(){
<% String stw =(String)request.getAttribute("invite_id"); %>
return "<%=stw%>";
}
function user_name(){
<% String stu =(String)request.getAttribute("name"); %>
return "<%=stu%>";
}
</script>
        <script>
            $(function () {
                $("#datepicker").datepicker({
                    maxDate: "+2w"
                });
            });
        </script>
    </head>

    <body>
        <h1>Hello  <%
                   String ss =(String)request.getAttribute( "name");
                   out.print(ss);
                   %> </h1>
        <Pre>
            <form action="date_picker" method="POST">
                <input hidden  id="client_id"  type="text" name="client_id"/>
                <input hidden   type="text" name="app_id" id="app_id"/>
                <input hidden  type="text" name="invite_id" id="invite_id"  />
                <input hidden  type="text" name="user_name" id="user_name"  />
                <label for="datepicker">Выберите день и время для записи:</label>
                <input type="text" name="dob" id="datepicker">
                <label for="from">Время:</label>
                <select name="time" id="time">
                    <option value="10:00:00">10:00</option>
                    <option value="11:00:00">11:00</option>
                    <option value="12:00:00">12:00</option>
                    <option value="13:00:00">13:00</option>
                    <option value="14:00:00">14:00</option>
              </select>

                <input type="submit" value="Submit">
            </form>
            <script type="text/javascript">
              document.getElementById("invite_id").setAttribute('value', invite_id());
              document.getElementById("app_id").setAttribute('value', getapp_id());
               document.getElementById("client_id").setAttribute('value', getclient_id());
                document.getElementById("user_name").setAttribute('value', user_name());
            </script>
        </pre>
    </body>

</html>