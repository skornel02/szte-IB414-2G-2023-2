# cURL Cheat Sheet

## cURL
man curl

Különféle protokollokon keresztüli üzenetküldés, pl. http

### GET

A get az alap metódus amit használ, így a get kérés küldése igen egyszerű:

```bash
curl "http://127.0.0.1:8080/api/list"
```
### GET paraméterek
Ugyanúgy az URL-ben adhatjuk meg.

```bash
curl "http://127.0.0.1:8080/api/list?besorolas=16" -i
curl "http://127.0.0.1:8080/api/list?fejleszto=RidleyScott" -i
```

### Cookie küldése
Most a GET kérésen mutatjuk be, mert az egyszerű, de ugyanígy kell megadni a többi kérésnél is!

Ha szeretnénk Cookie-kat is használni, akkor:
- a Cookie csak egy header, amiben extra adat található,így megadható headerként (-H)
```bash
curl "http://127.0.0.1:8080/api/list" -H "Cookie: request-number=1"
```
- vagy megadható külön kapcsolóval is (-b)
```bash
curl "http://127.0.0.1:8080/alista" -b "request-number=1"
```

### POST

-X paraméterrel adható meg a metódus.
Adjuk meg, hogy JSON-t akarunk küldeni a már látott -H kapcsolóval.
A -d kapcsolóval tudunk küldeni a kérés törzsében adatot.

```bash
curl -X POST -H "Content-Type: application/json" -d '{"cim":"Blade", "fejleszto":"JamesCameron", "ar":130, "besorolas":18}' "http://127.0.0.1:8080/api/add"
```
