# Automaty komórkowe

## Szybki start

1. Przed pierwszym uruchomieniem należy pobrać zależności (uruchomić skrypt setup-dependencies.cmd)
2. Aby uruchomić program, najlepiej użyć skryptu uruchomieniowego (run.cmd).
3. Program znajduje wszystkie pliki z rozszerzeniem .js w folderze, z którego go uruchomiono i jego podfolderach.

![Screenshot](lab12/pics/Obraz1.png)

4. Aby użyć skryptu, należy go wybrać z listy i kliknąć przycisk "Use chosen"
    1. Wybrany skrypt znajduje się w polu na górze.

![Screenshot](lab12/pics/Obraz2.png)


5. Wciśnięcie przycisku "Start" otwiera okno z automatem.

![Screenshot](lab12/pics/Obraz3.png)


6. Klikając na komórkę zmieniamy jej stan; w ten sposób ustawiamy stan początkowy.
7. Aby wystartować automat należy kliknąć "Start".

![Screenshot](lab12/pics/Obraz4.png)

![Screenshot](lab12/pics/Obraz5.png)

## Dodawanie nowych automatów

Automat jest implementowany jako plik z rozszerzeniem .js nazwany zgodnie z nazwą automatu.

Skrypt musi zawierać implementacje następujących metod:

1. Metoda zwracająca nazwę silnika.


    function engineName(){
        return "The Game of Life";
    }
    
2. Metoda wyznaczająca kolejny stan dla danej komórki w oparciu o jej stan i stan jej sąsiadów.

Stan komórki i sąsiadów jest przekazywany jako jednowymiarowa tablica liczb indeksowana jak poniżej (indeks 4 to rozważana komórka).

    function evaluateState(neighbourhood){
        // [0][1][2]
        // [3][4][5]
        // [6][7][8]
        return 0;
    }
    
3. Metoda porządkująca stany, pozwalająca przeklikać się i ustawić porządany stan komórki.


    function cycleThroughStates(state){
        return (state + 1) % 2;
    }
    
4. Metoda mapująca stan komórki na kolor.


    function colorForState(state){
        if(state === 1){
            return "0x000000";
        } else {
            return "0xFFFFFF";
        }
    }
