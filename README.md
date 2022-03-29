# Учебный проект курса Otus Kotlin Backend. Приложение для работы с коллекционными монетами.

## Модули
- **hello-world** - пример gradle подмодуля с Kotlin JVM
- **dsl-example** - реализация Kotlin DSL
- **web-ui** - React Web фронтенд

## Запуск
### WEB-UI
- `./gradlew web-ui:shadowJar -DORG_GRADLE_PROJECT_isProduction=true` - собрать `JAR` файл со всеми необходимыми зависимостями
- `java -jar web-ui/build/libs/web-ui-0.0.1-all.jar` - запуск приложения

## TODO
- добавить GitLab CI

## Для UI
- описать способ запуска
- Для прода нужна переменная ORG_GRADLE_PROJECT_isProduction=true