Приложение на Ktor Client + Clean Architecture, которое:
•	показывает список лауреатов Нобелевской премии;
•	позволяет фильтровать по году и/или категории;
•	при клике на лауреата открывает экран с подробной информацией;
•	API: https://api.nobelprize.org/2.1/nobelPrizes?limit=25&offset=0  (можно добавить параметры: nobelPrizeYear=2023, nobelPrizeCategory=physics и т.д.)

1.	Экран списка лауреатов (LazyColumn или LazyVerticalGrid)
o	Для каждого элемента:
	Год премии;
	Категория (physics, chemistry, literature, peace, medicine, economics);
	Имя лауреата (fullName);
	Краткое описание (motivation) — первые 100 символов.
o	Поля для фильтра: год (например, от 1901 до текущего) и категория (выпадающий список).
2.	Экран детализации лауреатов: 
o	Полное имя;
o	Год и категория;
o	Полное описание (motivation);
o	Страна (birthCountry / birthPlace);
o	Фото (если есть в ответе — поле portraitUrl или аналогичное).
3.	Clean Architecture;
4.	Обработка состояний: Loading (CircularProgressIndicator); Success (список); Error (текст ошибки + кнопка «Повторить»).
