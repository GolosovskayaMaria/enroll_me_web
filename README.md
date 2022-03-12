# Enroll me - web server

### API
Мы используем REST API https://ru.wikipedia.org/wiki/REST  
Параметры https://ru.wikipedia.org/wiki/URL#параметры    

#### /api/clients/add - добавить нового клиента 
Параметры: 
1. app_id - id установленной на телефон программы (GUID 36 символов)
1. name - contact name
1. phone - phone number
1. social - заметкак как можно связаться с клиентом, напрмер whatsup или vk 
1. location - адрес клиента  
Возвращает ошибку или нового клиента как json 
Пример:      
Request:  
http://192.168.1.41:8888/api/clients/add?app_id=1&name=Maria&phone=12345&social=whatsup&location=Gatchina  
Response:  
{"id":6,"app_id":"1","name":"Maria","phone":"12345","socilaMedia":"whatsup","location":"Gatchina"}  


#### /api/clients/get_all_clients - получить весь список клиентов 
Параметры:  
1. app_id - id установленной на телефон программы 
Возвращает ошибку или спискок всех клиентов как json 
Пример:      
Request:  
http://localhost:8888/api/clients/get_clients?app_id=1  
Response:  
{"id":6,"app_id":"1","name":"Maria","phone":"12345","socilaMedia":"whatsup","location":"Gatchina"}  
  
  
#### /api/clients/invite - сформировать приглашение на запись
1. user_id - id клиента из базы данных  
1. app_id - id установленной на телефон программы 
Сервер по этой команде прочитает из базы данных данные клиента и создаст запись в таблице визитов.
В записи будут данные клиента и время создания приглашения. 
Клиенту останется только проставить дату и коментарий.  
Команда вернет id приглашения.  
http://localhost:8888/api/clients/invite?app_id=1&user_id=2
Response:    
"3"  
  
  
#### /enroll/invite - записать себя на визит к мастеру
Параметры
1. invite - (guid) id приглашения на запись, ее генерирует клиентское пришашение запросом /invite  
Пример:  
http://localhost:8888/api/enroll?invite=3  
Update DB:  
mysql> select * from meetings;  
+----+---------------+--------+---------------------+---------------------+  
| ID | ApplicationId | UserId | MeetupDate          | CreateDate          |  
+----+---------------+--------+---------------------+---------------------+  
|  1 | 1             |      2 | NULL                | 2022-02-21 16:30:34 |  
|  2 | 1             |      2 | 2022-03-15 00:00:00 | 2022-02-21 16:31:46 |  
Если запись уже была показывает информацию о записи      
Если запись протухла, Пишет что 2 дня прошли и ты не можешь записаться по этой ссылке. Попросите мастера новую.    
  
  
#### /enroll/schedule - получить все свои записи (- 1 день и вперед)
1. app_id - id установленной на телефон программы (мастера?)
Сервер по этой команде прочитает из базы данных все запси на прием и вернет их как json  
http://localhost:8888/api/enroll/schedule?app_id=1
Response:  
```   
[
   {
      "id":1,
      "applicationId":"1",
      "userId":2,
      "createDate":"02/21/2022 00:00:00"
   },
   {
      "id":2,
      "applicationId":"1",
      "userId":2,
      "meetupDate":"03/15/2022 00:00:00",
      "createDate":"02/21/2022 00:00:00"
   },
   {
      "id":3,
      "applicationId":"1",
      "userId":2,
      "createDate":"03/03/2022 00:00:00"
   }
] 
```

TODO - предлагать для записи только свободное время. А не все продряд.  
TODO - push нотификации
TODO - удаление записи на прием   
TODO - создание записи на прием

  
### Описание Android приложения 
Это Android приложение для записи клиентов на прием к частному мастеру.  
Особенность приложения является возвможность "самозаписи". Клиенту посылается линк на запись. Он открывает его и сам выбирает время для записи.

### Функциолнал Android приложения
1. Создать клиента
1. Удалить клиента
1. Создать и выслать приглашение на самозапись по (смс, whatsup ...  share link)
1. Записать клиента на прием
1. Удалить запись на прием и выслать смс об этом
1. Просмотр записи на прием (разный показ прошедших и тех чтоо еще будут)
1. Получение и показ пуш нотификаций с переходом на запись
1. Диалог разрешения конфликтов по времени записи (самозапись и запись с приложения) 


#### Use cases
#### Самозапись клиента на прием
1. Мастер выбирает клиента и создает через сервер приглашение на запись
1. Мастер высылает приглашение на запись и посылает ссылку на приглашение клиенту через смс.
1. Клиент переходит по ссылке из смс и проставляет время визита к мастеру
#### Просмотр клиентом записи на прием
1. Клиент переходит по ссылке из смс по которой он ранее создавал запись
1. Клиент видит свою запись
#### Ссылки с истекщим временем
1. Клиент переходит по ссылке из смс 
1. Клиент видит надпись - ваша ссылка устарела, приглашение на запись действительно только 2 дня

