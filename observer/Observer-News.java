/**
Design Pattern - Observer
All classes and interfaces are in a single file for quick copy-paste and test run of the code.

You may represent the classes and interfaces in separate Java files:

observers
|_ Channel.java
|_ NewsChannel.java

observables
|_ Agency.java
|_ NewsAgency.java

Main.java

Reference: https://www.baeldung.com/java-observer-pattern
**/

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/* Observerables */
interface Agency {
  public void addObserver(Channel channel);
  public void removeObserver(Channel channel);
}

// a news agency can notify channels when it receives news. 
// Receiving news is what changes the state of the news agency, 
// and it causes the channels to be notified.

// NewsAgency is an observable, and when news gets updated, 
// the state of NewsAgency changes. When the change happens, 
// NewsAgency notifies the observers about this fact 
// by calling their update() method.
class NewsAgency implements Agency {
  private String news;
  private List<Channel> channels = new ArrayList<>();

  @Override
  public void addObserver(Channel channel) {
    this.channels.add(channel);
  }

  @Override
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

/* Observers */
interface Channel {
  public void update(Object o);
}

// To be able to do that, the observable object needs 
// to keep references to the observers, and in our case, 
// it's the channels variable.
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

class Main {
  public static void main(String[] args) {
    // if we add an instance of NewsChannel to the 
    // list of observers, and change the state of 
    // NewsAgency, the instance of NewsChannel 
    // will be updated:
    NewsAgency observable = new NewsAgency();
    NewsChannel observer = new NewsChannel();
    
    observable.addObserver(observer);
    observable.setNews("news");
    assertEquals(observer.getNews(), "news");
  }
}
