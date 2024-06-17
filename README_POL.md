# Czym jest Gaminatorium?

**Najprościej rzecz ujmując:**

Nasz projekt to strona internetowa/webaplikacja która skupia się na agregacji, udostępnianiu oraz ocenie minigier odpalanych w przeglądarce.

Gaminatorium to miejsce gdzie można wejść na chwilę i zagrać w różne implementacje gier karcianych, planszowych czy dowolnych innych, solo albo wieloosobowo.

### Ale Gaminatorium to również coś więcej.

Jest to portal stworzony dla programistów, miejsce gdzie można podejrzeć kod dowolnej gry i nazwiązać kontakt z jego twórcą. Mottem Gaminatorium jest:

### "Experiment. Learn. Play."

Chcemy aby nasza strona stała się miejscem gdzie można bawić się technologią, zobaczyć jak coś jest stworzone, zapytać o szczegóły techniczne, oceniać kreacje innych deweloperów i uczyć się od siebie nawzajem.

---

## Funkcjonalności

- Użytkownik ma możliwość utworzenia swojego konta i personalizacji elementów profilu.
- Użytkownik może przeglądać listę aktualnie dostępnych gier, a po kliknięciu na wybraną pozycję otrzymuje informacje takie jak (między innymi): opis gry, screenshot, link do kodu gry (GitHub), kontakt do autora (np. LinkedIn) i uśrednioną ocenę.
- Jedynie będąc zalogowanym można uruchomić wybraną grę, po czym użytkownik zostanie przekierowany i obsłużony przez serwis danej gry.
- Zalogowany użytkownik może dodać własną grę do naszej bazy przy użyciu załączonego formularza (pod warunkiem, że gra przejdzie moderację).
- Udostępniane przez Gaminatorium gry mogą być produkcjami typu "stand-alone" i nie mieć żadnego związku z naszym serwisem. Ale mogą również korzystać z publicznego API Gaminatorium, np. w celu zaciągania informacji o nowym graczu.
- Gry podległe pod Gaminatorium mogą być tworzone jako odrębne mikroserwisy, niezależne od technologii użytych w głównym serwisie.
- Regularne korzystanie z serwisu jest nagradzane punktami, które podlegają wymianie na pewne elementy dekoracji profilu (tj. skórki).
- Możliwości interacji pomiędzy użytkownikami: dodawanie do znajomych, wysyłanie zaproszeń do wspólnej gry itp.

---

## Technikalia 

Backend głównego serwisu Gaminatorium stworzony jest z użyciem Javy 21, Spring Boota 3.2.6 oraz Gradle 8.7 (Groovy). Dodatkowo:

- baza danych na Postgres + Hibernate + Liquibase
- autoryzację ogarnia dla nas Keycloak
- standardowy stack testowy czyli JUnit/Mockito uzupełniony przez Spocka i WireMock
- konteneryzacja: Docker + Docker Compose
- messaging: RabbitMQ
- CI/CD: Jenkins
- REST API wystawione przy użyciu Swaggera (OpenAPI)

Repozytorium frontendu Gaminatorium dostępne jest [tutaj](https://github.com/keeeper85/Gaminatorium-frontend).

---

## Status projektu: *W fazie rozwoju*

Prace nad Gaminatorium ruszyły w maju 2024, projekt jest zarządzany w Jirze i obecnie pracuje przy nim 5 osób. Aktualną wersję aplikacji możecie zobaczyć na stronie [gaminatorium.eu](https://www.gaminatorium.eu)

Aby podejrzeć działanie serwisów lokalnie, najprościej będzie pobrać plik docker-compose.yml (jest w katalogu głównym tego repozytorium) i odpalić go komendą:

`docker-compose up -d`

Warunkiem jest posiadanie zainstalowanego Dockera z pluginem Docker Compose. 

---

## Twórcy

- Backend/DevOps: [Jakub Raczkowski](https://www.linkedin.com/in/jakub-raczkowski-81401b231)
- Backend: [Michał Grot]()
- Frontend: [Michał Wojnowski]()
- Frontend: [Kacper Heluszka](https://www.linkedin.com/in/krystian-heluszka/)
- Frontend: [Michał Momot](https://www.linkedin.com/in/micha%C5%82-momot-8381222a1)

---

## Licencja

Gaminatorium jest projektem open-source na licencji [GPL v3.0](https://choosealicense.com/licenses/gpl-3.0/). 
