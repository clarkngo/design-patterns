/**
Design Pattern - Observer
All classes and interfaces are in a single file for quick copy-paste and test run of the code.

Note: Observer interface isn't perfect and is deprecated since Java 9. One of its cons is that Observable isn't an interface but a class, that's why subclasses can't be used as observables.

You may represent the classes and interfaces in separate Java files:

channels
|_ Channel.java
|_ NewsChannel.java

observable 
|_ ONewsAgency.java

observer
|_ ONewsChannel.java

NewsAgency

Main.java

Source: 
**/

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static org.junit.Assert.*;

interface Channel {
  public void update(Object o);
}

class NewsChannel implements Channel {
  private String news;

  @Override
  public void update(Object news) {
    this.setNews((String) news);
  }

  public String getNews() {
    return news;
  }

  public void setNews(String news) {
    this.news = news;
  }
}

// NewsAgency is an observable, and when news gets updated, 
// the state of NewsAgency changes. When the change happens, 
// NewsAgency notifies the observers about this fact by 
// calling their update() method.
class NewsAgency {
  private String news;
  private List<Channel> channels = new ArrayList<>();

  public void addObserver(Channel channel) {
    this.channels.add(channel);
  }

  public void removeObserver(Channel channel) {
    this.channels.remove(channel);
  }

  public void setNews(String news) {
    this.news = news;
    for (Channel channel : this.channels) {
      channel.update(this.news);
    }
  }
}

// To define the observable, we need to extend 
// Java's Observable class:
class ONewsAgency extends Observable {
  private String news;

  public void setNews(String news) {
    this.news = news;
    setChanged();
    notifyObservers(news);
  }
}

// The java.util.Observer interface defines the 
// update() method, so there's no need to define it ourselves
class ONewsChannel implements Observer {
  private String news;

  @Override
  public void update(Observable o, Object news) {
    this.setNews((String) news);
  }

  public String getNews() {
    return news;
  }

  public void setNews(String news) {
    this.news = news;
  }
}

// if we add an instance of NewsChannel to the 
// list of observers, and change the state of 
// NewsAgency, the instance of NewsChannel 
// will be updated:
class Main {
  public static void main(String[] args) {
    NewsAgency observable = new NewsAgency();
    NewsChannel observer = new NewsChannel();

    observable.addObserver(observer);
    observable.setNews("news");
    assertEquals(observer.getNews(), "news");
  }
}
