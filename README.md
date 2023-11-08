<h1>Сервис по доставке еды. Общая структура.</h1>
Общая структура сервиса по доставке еды состоит из 9 модулей:
<br>

- Dependency Bom
- Common
- Migration
- Security Service
- Order Service
- Kitchen Service
- Delivery Service
- Cloud Gateway
- Notification Service

<h3>Тестовый сценарий работы сервиса</h3>
- Клонировать проект себе на ПК
- Запустить <b>MigrationApplication</b> в модуле Migration для создания необходимых таблиц работы приложения(предварительно изменив данные для входа в Postgres как в Migration, так и во всех сервисах)
- Запустить Rabbit в Docker <b>docker run -lt --rm --name rabbitmq -p 5672:5672
  -p 15672:15672 rabbitmq:3-management</b>
- Запустить <b>Notification Service</b>
- Запустить все сервисы для работы с бизнес логикой приложения(<b>Order-Service, Kitchen-Service, Delivery-Service</b>)
- Запустить <b>Security-Service</b>
- Запустить <b>Cloud-Gateway</b>
<br>
<b>Сервис готов к использованию!</b>
<br>
Все взаимодействие с сервисами происходит через Gateway на порту 127.0.0.1:8080/, для того чтобы посылать запросы через Postman необходимо сначала авторизоваться в браузере(<b>login:admin, password:admin</b>), после чего из Cookie взять <b>Session</b> и использовать его в теле запроса.
<br>
Создание заказа происходит через метод <b>Post по адресу http://127.0.0.1:8080/orders </b>
в который передается DTO создания нового заказа, в котором указываются Id ресторана, Id клиента а также список позиций, их Id, взятых из Restaurant_menu_item, а также количества.
Все последующие действия и end-point выполнены согласно Техническому заданию, а вызываются также на Gateway(порт 8080). 
<br>
Каждый вызов метода каждого контроллера логируется при стартовом вызове, а также при окончании работы метода с выполненным результатом.
При выполнении определенных задач происходит отправка DTO, содержащей id заказа, обозначения цели сообщения, а также самого сообщения, которые направляются в <b>Notification-Service</b> и благодаря цели сообщения перенаправляются на нужный сервис.
<br> Сообщения, отмеченные целью "notification" не перенаправляются никуда, а уведомляют путем вывода на консоль в самом Notification-Service.
<br>
В каждом сервисе происходит проверка при попытке установить статус заказа, для того, чтобы, например, ресторан не имел возможности принять уже отмененный заказ, либо курьер доставить заказ, который отменил сам пользователь.
Следовательно всё движение заказа происходит в строго определенном порядке:
- CUSTOMER_CREATED
- CUSTOMER_PAID
- KITCHEN_ACCEPTED
- DELIVERY_PENDING
- DELIVERY_PICKING
- DELIVERY_COMPLETED
<br>
При отмене заказа на любом сервисе в дальнейшем установка других статусов на заказ невозможна.
<br>
При установке статуса DELIVERY_PENDING в Kitchen-Service происходит отправка асинхронного сообщения через RabbitMQ, в результате получения которого происходит поиск ближайшего активного курьера и оповещения его о том, что он направлен на данный заказ.
<h4>Пара заметок на полях :)</h4>
- Order_Id - UUID, при этом primary_key остальных сущностей типа лонг и создаются с Sequence. Сделано намеренно, т.к. заказов может быть очень много, для остального же посчитал лучшим использование long
- Т.к. мы не используем Push-уведомления, сообщения в Rabbit банально отображаются в консоли
- Столкнулся с ошибкой, которую не успел поправить из-за ограниченности времени, методы Kitchen-Service почему-то выбрасывают 404 ошибку в Postman, при этом отрабатывают, логи пишутся корректно, и вся бизнес-логика отрабатывает, только отображения почему-то нет.
- Это первый мой проект, заранее спасибо за обратную связь, прошу не судить строго :)
<h3>Использованные технологии:</h3>

- Java 11 
- Spring Boot
- Spring Data JPA
- Spring Security
- Spring Web
- LiquidBase
- PostgreSQL
- MyBatis
- Lombok
- RabbitMQ
- Feign
- Swagger
- Slf4j + Log4j
- JUnit 


<h2>Dependency Bom</h2>
В dependency модуле указаны все зависимости, которые используются в проекте и остальных модулях, а также их актуальные версии.
<br>
Все зависимости храняться в <b>pom.xml</b>
<br>
<h2>Common</h2>
В модуле Common находятся сущности, которые используются во всех Rest модулях, а также репозитории, через которые идет обращение к БД и кастомная ошибка <b>NoSuchEntityException</b>
которая используется для проверки наличия сущности при обращении к бд.
<h2>Migration</h2>
Модуль Migration взаимодействует с Базой данный с помощью <b>LiquidBase.</b>
<br>
Модуль создает базовые таблицы в БД, необходимые для работы всего проекта, а также отдельную схему <b>auth</b> в которой находятся таблицы необходимые для обеспечения авторизации и аутентификации пользователей.
<h2>Security Service</h2>
Данный модуль отвечает за регистрацию, авторизацию и аутентификацию пользователей. 
<br>
Каждый Rest service проекта(Delivery, Kitchen и Order services) для аутентификации перенаправляет запрос на <b>Localhost:9000</b> на котором и находится Security Service, который и ощуествляет OAuth2 аутентификацию.
<h2>Order Service</h2>
Сервис заказов спроектирован для работы с клиентской стороной. В сервисе предоставлено 3 контроллера: OrderRestController, OrderItemRestController и CustomerController. 
<br> 
Вся документация по работе с Order-service доступна в swagger'e.
<br>
При оплате клиентом заказа (т.е. выставления статуса <b>CUSTOMER_PAID</b>)
Сервис отправляет запрос через RabbitMQ на <b>Notification Service</b> который перенаправляет сообщение с id заказа на сервис ресторана(<b>Kitchen Service</b>)
<h2>Kitchen Service</h2>
Сервис ресторана содержит в себе 3 контроллера и, соответственно, сервиса-
<br>
<b>OrderController</b> для работы с заказами
<br>
<b>RestaurantRestController</b> для занесения, изменение и получения информации о ресторанах, присутствующих в базе данных
<br>
<b>RestaurantMenuItemRestController</b> для управления и редактирования меню ресторанов.
<br> 
При работе с заказами, если ресторан принял заказ и приготовил его (изменил статус заказа на <b>DELIVERY_PENDING</b>
Идет отправка асинхронного сообщения через <b>RabbitMQ</b> через <b>Notification Service</b> на сервис курьеров(<b>Delivery Service</b>) с ДТО, содержащей id заказа и координаты ресторана.
<br>
При отказе от заказа (выставления статуса <b>KITCHEN_DENIED</b>) отправляется уведомление на сервис заказов с оповещением, что ресторана отказался от заказа.
<h2>Delivery Service</h2>
Сервис курьеров предоставляет доступ к контроллеру доставок (<b>DeliveryRestController</b>)
а также контроллер и сервис для создания/изменения/удаления курьеров из БД(<b>CourierController</b>) 
При получении запроса из <b>Kitchen Service</b> Происходит mapping DTO содержащего информацию о заказе и на основании координат выбирается ближайший активный курьер который и назначается на доставку. <br>
<a>Метод не доделан чисто математичеки, в данный момент лишь пытается найти курьера координаты которого полностью совпадают с координатами ресторана</a>
<br>
При попытке установить статус заказа идет обращение через <b>Feign</b> к сервису заказов и соответвтующему методу(сделан для демонстрации полученных знаний)
<h2>Cloud Gateway</h2>
Данный модуль перенаправляет запросу к общему порту <b>http://127.0.0.1:8080</b>
на необходимый сервис по его порту. 
<br>
Вся информация о настройка Gateway прописана в <b>application.yml</b>
<h2>Notification Service</h2>
Является информационным сервисом, в котором реализовано 3 <b>Queue Listener'a</b> и 3 очереди для перенаправления асинхронных обращений на нужный сервис. 
