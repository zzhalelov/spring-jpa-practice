# Описание проекта

Цель проекта — разработка REST API-приложения для управления товарами (Products), категориями (Categories), характеристиками (Options) и значениями характеристик (Values) в онлайн-магазине.

Проект построен с использованием:
* Spring Boot
* Spring Web
* Spring Data JPA
* Lombok
* PostgreSQL
* Mockito

## Основные сущности
Category (Категория):
* id: идентификатор (int)
* name: название категории (String)
* options: список характеристик, привязанных к категории (List<Option>)

Product (Товар):
* id: идентификатор (int)
* name: название (String)
* price: цена (Double)
* category: ссылка на категорию
* values: список значений характеристик (List<Value>)

Option (Характеристика):
* id: идентификатор (int)
* name: название (String)
* category: ссылка на категорию

Value (Значение характеристики):
* id: идентификатор (int)
* name: значение (String)
* product: товар, к которому относится значение
* option: характеристика, к которой относится значение

## DTO и мапперы
CategoryDto

Используется для отображения краткой информации о категории (id, name).
Статический метод of(Category) маппит Entity в DTO.

ProductDto
Отображает товар с полем category как строку.
Используется ProductMapper для конвертации.

ProductCreateDto
Используется для создания товара. Содержит: name, price, categoryId.

ProductMapper
Методы:
* fromCreate(ProductCreateDto): создает сущность Product из DTO.
* toDto(Product): маппит Product → ProductDto.
* toDto(List<Product>): маппит список товаров.

## REST-контроллеры

CategoryController:
* GET /categories — получить все категории.
* GET /categories/{id} — получить категорию по ID.
* GET /categories/find-by-name/{name} — найти категорию по имени (без учёта регистра).
* GET /categories/find-by-name-containing/{name} — поиск по части имени.
* POST /categories — создать категорию.
* PUT /categories/{id} — обновить категорию.
* DELETE /categories/{id} — удалить категорию.

![image](https://github.com/user-attachments/assets/2aa7b716-d623-4dd4-896d-2f8973f64fe4)


ProductController
* GET /products — получить список всех товаров.
* GET /products/{id} — получить товар по ID.
* GET /products/find-by-price-between?priceMin=X&priceMax=Y — товары в диапазоне цен.
* GET /products/find-by-name-containing/{name} — поиск по части названия.
* GET /products/find-with-max-price — товар с максимальной ценой.
* POST /products — создать товар.
* PUT /products/{id} — обновить товар.
* DELETE /products/{id} — удалить товар.

![image](https://github.com/user-attachments/assets/cbe830b2-af01-478f-8f69-a4e21d8dadb2)


## Сервисы

CategoryService:
* create(Category) — проверка уникальности имени категории и сохранение.

ProductService:
* create(Product, categoryId) — создание товара с привязкой к категории.
* update(Product, productId) — обновление данных товара.
* deleteById(productId) — удаление товара.
* findById(productId) — получить товар по ID.

ValueService:
* create(valueName, productId, optionId) — создание значения характеристики, связанного с товаром и характеристикой.

## Репозитории

1. CategoryRepository: расширяет JpaRepository<Category, Integer>, добавлены методы поиска по имени.
1. ProductRepository: включает методы для поиска по цене, названию и характеристикам.
1. OptionRepository: базовый JpaRepository.
1. ValueRepository: базовый JpaRepository.

## Бизнес-логика
* Название категории не может быть пустым.
* Название категории должно быть уникальным.
* При создании товара необходимо указать categoryId, который должен существовать.
* Значение характеристики (Value) должно быть связано с существующим Product и Option.
* Обновление товара и категории происходит только если сущность существует.
