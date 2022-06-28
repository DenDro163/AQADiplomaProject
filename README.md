
# AQADiplomaProject
___
## Документация.

[Задание](https://github.com/netology-code/qa-diploma)

[План тестирования](https://github.com/DenDro163/AQADiplomaProject/blob/master/docs/Plan.md)

## Процедура запуска авто-тестов.

### Перед запуском тестов необходимо установить окружение:

* [AdoptOpenJDK 11.0.11+9](https://adoptopenjdk.net/index.html)
* [Docker Desktop](https://www.docker.com/products/docker-desktop)
* Работа выполнена на ПК с ОС Windows 10 Pro.

### Запуск:

* Скачать код проекта с [репозитория](https://github.com/DenDro163/AQADiplomaProject.git)
* Запустить Docker Desktop.
* Открыть терминал в папке с проектом.
* Выполнить: 
  `docker-compose up -d --force-recreate`
* Запустить SUT aqa-shop.jar командой:
  `java -jar artifacts\aqa-shop.jar &`
* Запустить авто тесты командой  
  `./gradlew clean test --info`

### По умолчанию подключается MySQL.
### Для работы с PostgreSQL:
* перезапустить SUT aqa-shop.jar командой: 
  `java -jar artifacts\aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app &`