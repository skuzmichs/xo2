package interfaces;

import model.ResponseServer;

//Obserwator
public interface MyObservable {
    void registerObserver(MyObserver myObserver);

    void notifyObservers(ResponseServer responseServer);
}
