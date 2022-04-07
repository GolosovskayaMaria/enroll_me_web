<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Java Date Picker</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script>
            $(function () {
                $("#datepicker").datepicker({
                    maxDate: "+1w"
                });
            });
        </script>
    </head>

    <body>
        <h1>Hello ${name}</h1>
        <Pre>
            <form action="date_picker" method="POST">
                <input hidden value=${client_id}  type="text" name="client_id"/>
                <input hidden value=${app_id}  type="text" name="app_id"/>
                <input hidden value=${invite_id}  type="text" name="invite_id"/>
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
        </pre>
    </body>

</html>