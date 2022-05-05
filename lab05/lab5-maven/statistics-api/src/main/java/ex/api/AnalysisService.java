package ex.api;

import ex.api.observer.Observable;

public interface AnalysisService extends Observable {
    void setOptions(String[] options) throws AnalysisException;
    String getName();
    // metoda przekazująca dane do analizy, wyrzucająca wyjątek jeśli aktualnie trwa przetwarzanie danych
    void submit(DataSet ds) throws AnalysisException;
    // metoda pobierająca wynik analizy, zwracająca null jeśli trwa jeszcze przetwarzanie lub nie przekazano danych do analizy
    // wyrzucająca wyjątek - jeśli podczas przetwarzania doszło do jakichś błędów
    // clear = true - jeśli wyniki po pobraniu mają zniknąć z serwisu
    DataSet retrieve(boolean clear) throws AnalysisException;
}