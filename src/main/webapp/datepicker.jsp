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
                    maxDate: "+2w"
                });
            });
        </script>
    </head>

    <body>
        <h1>Hello ${name}</h1>
        <Pre>
            <form action="DatePicker">
                <label for="datepicker">Выберите день и время для записи:</label>
                    <input type="text" name="dob" id="datepicker">
                <label for="from">Время:</label>
                    <select name="time" id="time">
                        <option value="1">10:00</option>
                        <option value="2">11:00</option>
                        <option value="3">12:00</option>
                        <option value="4">13:00</option>
                        <option value="5">14:00</option>
                        <option value="6">15:00</option>
                        <option value="7">16:00</option>
                        <option value="8">17:00</option>
                        <option value="9">18:00</option>
                  </select>
                <input type="submit" value="Submit">
            </form>
        </pre>
    </body>

</html>