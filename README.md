# Beadandó 2.28 feladat (többjátékos)

## FELADAT

Egy 10x10 mezőből álló táblára az egyik játékosnak egy sőtét, a másiknak
pedig egy világos huszár figurát helyezünk. A játékosok felváltva lépnek a
saját figurájukkal a sakk szabályainak megfelelően, huszárlépésben. Miután
egy figura elmozdul egsy mezőről, azt a játékos a saját színével megjelöli.

Lépni csak üres nem megjelölt területre lehet. Az nyer, akinek egy sorban,
oszlopban vagy átlóban sikerül 5 saját jelet elhelyezni egymás mellett. 


###### Fontosabb megszórítások:

- Tesztelhető üzleti logika implementálása.
- JavaFX (OpenJFX) keretrendszert kellett alkalmazni.
- MVC tervezési minta követése.
- Az adatokat **JSON**, XML vagy Adatbázisban kell tárolni.

## Funkciók

1. report készítés __**mvn site**__ parancsal
2. JAR állomány készítése __**mvn package**__ parancsal
3. Junit 5 test készítése __**mvn test**__ parancsal

## Futatás

JAR elkészítése után **java -jar** utasítással. Az elején
a két játékos nevét dialógus ablakon keresztül bekérésre kerül.

A játékosok adatai, a játékok és az 5 legtöbb pontott elért játékosok adatai
JSON állományokban elmentésre kerülnek. 
