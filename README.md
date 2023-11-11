# java-explore-with-me
Template repository for ExploreWithMe project.

https://github.com/Ilia28593/java-explore-with-me/pull/22

Данное приложение — это афиша, в которой можно запланировать какое-либо событие для себя или компании.
Ежедневно мы планируем мероприятия, куда и с кем сходить. Сложнее всего в таком планировании поиск информации и переговоры. 
Нужно учесть много переменных:
- какие намечаются мероприятия
- свободны ли в этот момент друзья
- как всех пригласить и где собраться.


-               Реализация основного сервиса.
API основного сервиса состоит из 3 частей:

- публичной(доступна без регистрации)
- приватная (доступна только авторизованным пользователям)
- административная (для администраторов)

Публичный - предоставляет возможности поиска и фильтрации событий.
Приватная - реализовывает возможности зарегистрированных пользователей продукта.
Административная - предоставляет возможности настройки и конфигурации работы сервиса.

-              Реализация сервиса статистики.
Функционал сервиса статистики содержит:

- запись информации о обработки запроса к API;
- предоставление статистики за выбраный период и выброному поинту.
___
Используемые технологии : Maven, Spring-boot, jpa-hibernate, Docker, PostgreSQL, Lombok.
___
 Разработчи Ситник И.О.