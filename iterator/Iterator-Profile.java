
/**

Design Pattern - Iterator

All classes and interfaces are in a single file for quick copy-paste and test run of the code.

You may represent the classes and interfaces in separate Java files:

iterators
|_ FacebookIterator.java
|_ ProfileIterator.java

profile
|_ Profile.java

social_networks
|_ Facebook.java

**/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*********

iterators 

**********/
interface ProfileIterator {
  boolean hasNext();
  Profile getNext();
  void reset();
}

class FacebookIterator implements ProfileIterator {
  private Facebook facebook;
  private String type;
  private String email;
  private int currentPosition = 0;
  private List<String> emails = new ArrayList<>();
  private List<Profile> profiles = new ArrayList<>();

  public FacebookIterator(Facebook facebook, String type, String email) {
    this.facebook = facebook;
    this.type = type;
    this.email = email;
  }

  private void lazyLoad() {
    if (emails.size() == 0) {
      List<String> profiles = facebook.requestProfileFriendsFromFacebook(this.email, this.type);
      for (String profile : profiles) {
        this.emails.add(profile);
        this.profiles.add(null);
      }
    }
  }

  @Override
  public boolean hasNext() {
    lazyLoad();
    return currentPosition < emails.size();
  }

  @Override
  public Profile getNext() {
    if (!hasNext()) {
      return null;
    }

    String friendEmail = emails.get(currentPosition);
    Profile friendProfile = profiles.get(currentPosition);
    if (friendProfile == null) {
      friendProfile = facebook.requestProfileFromFacebook(friendEmail);
      profiles.set(currentPosition, friendProfile);
    }
    currentPosition++;
    return friendProfile;
  }

  @Override
  public void reset() {
    currentPosition = 0;
  }
}


/*********

profile 

**********/
class Profile {
  private String name;
  private String email;
  private Map<String, List<String>> contacts = new HashMap<>();

  public Profile(String email, String name, String... contacts) {
    this.email = email;
    this.name = name;

      // Parse contact list from a set of "friend:email@gmail.com" pairs.
    for (String contact : contacts) {
      String[] parts = contact.split(":");
      String contactType = "friend", contactEmail;
      if (parts.length == 1) {
        contactEmail = parts[0];
      }
      else {
        contactType = parts[0];
        contactEmail = parts[1];
      }
      if (!this.contacts.containsKey(contactType)) {
        this.contacts.put(contactType, new ArrayList<>());
      }
      this.contacts.get(contactType).add(contactEmail);
    }
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public List<String> getContacts(String contactType) {
    if (!this.contacts.containsKey(contactType)) {
      this.contacts.put(contactType, new ArrayList<>());
    }
    return contacts.get(contactType);
  }
}

/*********

social_networks 

**********/
interface SocialNetwork {
  ProfileIterator createFriendsIterator(String profileEmail);
  ProfileIterator createCoworkersIterator(String profileEmail);
}

class Facebook implements SocialNetwork {
  private List<Profile> profiles;

  public Facebook(List<Profile> cache) {
    if (cache != null) {
      this.profiles = cache;
    } else {
      this.profiles = new ArrayList<>();
    }
  }

  public Profile requestProfileFromFacebook(String profileEmail) {
    simulateNetworkLatency();
    System.out.println("Facebook: Loading profile '" + profileEmail + "' over the network...'");
    return findProfile(profileEmail);
  }

  public List<String> requestProfileFriendsFromFacebook(String profileEmail, String contactType) {
    simulateNetworkLatency();
    System.out.print("Facebook: Loading '" + contactType + "' list of '" + profileEmail + "' over the network...");

    Profile profile = findProfile(profileEmail);
    if(profile != null) {
      return profile.getContacts(contactType);
    }
    return null;
  }

  private Profile findProfile(String profileEmail) {
    for (Profile profile : profiles) {
      if (profile.getEmail().equals(profileEmail)) {
        return profile;
      }
    }
    return null;
  }

  private void simulateNetworkLatency() {
    try {
      Thread.sleep(2500);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public ProfileIterator createFriendsIterator(String profileEmail) {
    return new FacebookIterator(this, "friends", profileEmail);
  }

  @Override
  public ProfileIterator createCoworkersIterator(String profileEmail) {
    return new FacebookIterator(this, "coworkers", profileEmail);
  }
}

/*********

spammer

**********/
class SocialSpammer {
  public SocialNetwork network;
  public ProfileIterator iterator;

  public SocialSpammer(SocialNetwork network) {
    this.network = network;
  }

  public void sendSpamToFriends(String profileEmail, String message) {
    System.out.println("\nIterating over friends...\n");
    iterator = network.createFriendsIterator(profileEmail);
    while (iterator.hasNext()) {
      Profile profile = iterator.getNext();
      sendMessage(profile.getEmail(), message);
    }
  }

  public void sendSpamToCoworkers(String profileEmail, String message) {
    System.out.println("\nIterating over coworkers...\n");
    iterator = network.createCoworkersIterator(profileEmail);
    while (iterator.hasNext()) {
      Profile profile = iterator.getNext();
      sendMessage(profile.getEmail(), message);
    }
  }

  public void sendMessage(String email, String message) {
    System.out.println("Sent message to: '" + email + "'. Message body: '" + message + "'");
  }
}

// Client code
class Main {
  public static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    System.out.println("Please specify social network to target spam tool (default:Facebook):");
    System.out.println("1. Facebook");
    String choice = scanner.nextLine();

    SocialNetwork network;
    if (choice.equals("1")) {
      network = new Facebook(createTestProfiles());
    }
    else {
      network = new Facebook(createTestProfiles());
    }

    SocialSpammer spammer = new SocialSpammer(network);
    spammer.sendSpamToFriends("anna.smith@bing.com",
          "Hey! This is Anna's friend Josh. Can you do me a favor and like this post [link]?");
    spammer.sendSpamToCoworkers("anna.smith@bing.com",
          "Hey! This is Anna's boss Jason. Anna told me you would be interested in [link].");
  }

  public static List<Profile> createTestProfiles() {
    List<Profile> data = new ArrayList<Profile>();
    data.add(new Profile("anna.smith@bing.com", "Anna Smith", "friends:mad_max@ya.com", "friends:catwoman@yahoo.com", "coworkers:sam@amazon.com"));
    data.add(new Profile("mad_max@ya.com", "Maximilian", "friends:anna.smith@bing.com", "coworkers:sam@amazon.com"));
    data.add(new Profile("bill@microsoft.eu", "Billie", "coworkers:avanger@ukr.net"));
    data.add(new Profile("avanger@ukr.net", "John Day", "coworkers:bill@microsoft.eu"));
    data.add(new Profile("sam@amazon.com", "Sam Kitting", "coworkers:anna.smith@bing.com", "coworkers:mad_max@ya.com", "friends:catwoman@yahoo.com"));
    data.add(new Profile("catwoman@yahoo.com", "Liza", "friends:anna.smith@bing.com", "friends:sam@amazon.com"));
    return data;
  }
}
