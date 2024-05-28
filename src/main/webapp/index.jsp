<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Форматирование файлов</title>
</head>
<body>
<h2>Загрузите файл</h2>
<form action="upload" method="post" enctype="multipart/form-data">
    <input type="file" name="file" accept=".txt" required>
    <input type="submit" value="Загрузить">
</form>

<% if (request.getAttribute("downloadPath") != null) { %>
<p>Файл успешно отформатирован. <a href="download">Скачать отформатированный файл</a></p>
<% } %>
</body>
</html>
