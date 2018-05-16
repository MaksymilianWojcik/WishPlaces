# WishPlaces
WishPlaces Android App

Aplikacja służy do zapisywania miejsc, które użytkownik chciałby zobaczyć. Użytkownik ma możliwość dodawania nowych lokalizacji oraz
podglądu już dodanych.

Aplikacja składa się z 4 Activity, 3 fragmentów, 4 klas (dto i dao) oraz z 2 Adapterów.
Używane dodatkowe API: Google Maps, Google Places, Material Design, ButterKnife oraz EventBus (wykorzystany w jednym miejscu jako przykład działania ułatiwającego przekazywanie danych, komunikację między zasobami aplikacji)
Dane zapisywane są w bazie danych telefonu (SQLiteDatabase).
Aplikacja wygląda inaczej na telefonie i inaczej na tablecie.

Activity:
1. SplashActivity - ekran startowy. Po 3 sekundach znika i pojawia się MainActivity.
2. MainActivity - główne Activity, składające się z paska zakładek na dole (bottom navigate bar), oraz toolbara z tytułem i możliwością
dodania nowej lokalizacji.
Po wybraniu odpowiedniej zakładki na pasku, ładowany jest odpowiedni fragment: Lista lokalizacji lub mapa lokalizacji.
3. NewWishPlaceActivity - Activity do tworzenia nowej lokalizacji. Oprócz pól, które muszą być wypełnione aby możliwe było zapisanie
miejsca w bazie danych, składa się także z odnośnika (button) do pobrania lokalizacji za pomocą Google Places API (ActivityForResult)
4. WishPlaceDetailActivity - Służy do pokazania szczegółow danej lokalizacji (pobranych z bazy danych) na ekranie. Widok składa się 
z małej mapki z danym miejscem oraz pól opisu danej lokalizacji. Activity szczegółow ładowane jest wtedy, kiedy nie jesteśmy na tablecie.

Fragmenty:
1. WishPlaceListFragment - lista zapisanych w bazie danych lokalizacji (dodawanych za pomocą NewWishPlaceActivity). Po kliknięciu danej pozycji
przejście do jej szczegółow (widok albo WishPlaceDetailACtivity, albo WishPlaceDetailFragment)
2. MapFragment - fragment z pełną mapą (wyświetlany na drugiej zakładce MainActivity. Na mapie zaznaczone są pobrane z bazy danych lokalizacje.
3. WishPlaceDetailFragment - tak jak WishPlaceDetailActivity, jednak ładowane jest gdy używamy tableta, tuż obok listy lokalizacji.

Dodatkowe klasy:
1. DatabaseHelper - klasa pomocnicza przy pracy z bazą danych.
2. WishPlaceDao - klasa pomocnicza przy pracy z zapytaniami dotyczącymi modelu lokalizacji (WishPlace)
3. WishPlace - model lokalizacji, składający się z pól (id), name, summary, description, lat, lon.
4. AppValues - klasa pomocnicza, przechowująca różne wartości Aplikacji

Adaptery:
1. ViewPagerAdapter - adapter do określania zakładek na głównym widoku (zakładki Bottom Navigation bara)
2. WishPlaceListAdapter - adapter do określania widoku i zachowania listy z lokalizacjami.


WAŻNE!
Przed uruchomieniem aplikacji proszę w Google Developer API Console na swoje konto googlowskie (na które jesteśmy na danym telefonie zalogowani) zezwolić używanie googlowskich API: Google Maps for Android SDK oraz Google Places. Następnie należy wygenerować klucz (w zakładce dane logowania) dla biblioteki map oraz następnie ten klucz skopiować i wpisać w pliku AndroidManifest.xml.


TODO:
1. dodać EventBusa
2. dodać usuwanie / edycja elementów
3. refaktoring kodu
4. ustawienie wszystkich tekstów w values/string
5. ustawienie rozmiarów czcionek w values/dim dla różnych urządzeń
6. ustawienie wszystkich kolorów w values/colors
7. dodać internet access permission
