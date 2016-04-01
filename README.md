# Wunderlist SDK for android 

This SDK wraps the Wunderlist API(https://developer.wunderlist.com/documentation) and provides OAuth authentication functionality. 

#Prerequisites

You must register your application(https://developer.wunderlist.com/apps/new) before getting started. Registration assigns a unique client ID and client secret for your application’s use. This should be provided while building the wunderlist session

##Setup Wunderlist session
```java
private static final String CLIENT_ID = "Your client id";
private static final String CLIENT_SECRET = "Your client secret";
private static final String REDIRECT_URI="Dummy Url";
```

When your app starts, initialize the WunderlistSession singleton that has all of the information that is needed to authenticate. The session instance of saved statically and does not need to be passed between activities. A better option is to build the instance in your onCreate() of the Application object or your parent Activity object.

```java
new WunderlistSession.Builder(this).
                build(CLIENT_ID, CLIENT_SECRET, REDIRECT_URI);
```

##Access Notes using Client
The Wunderlist client gives access to the user profile as well as list of user notes. The client can be accessed as below
```java
WunderlistSession.getInstance().getWunderlistClient();
```

##Screenshots
The login screen is shown below


![alt tag](https://github.com/satyajeetgawas/wunderlist-sdk-android/blob/master/Screenshot_1.png)


I am working on this sdk and will update as per my availability. Currently it is able to access user notes. Will add code to create, update notes.  


