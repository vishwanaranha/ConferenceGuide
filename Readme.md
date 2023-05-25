# ConferenceDirectory

## Table Of Contents

[Overview](#overview)    
[Libraries Used](#libraries-used)

## Overview

Conference Guide is an app that lists out conferences around the world with it's  
name and starting date. I have used MVVM design pattern. It consumes REST APIs
to fetch the data  over the network. There are two build variants "buildA" and "buildB"
which will show up as title on the app according to build variant chosen during build.
Also the app shows loading, success and error states based off the response received in the api
response. Error state also has retry button to try the network operation again.
It contains a few unit tests too to cover the conference list api call and states.
Also could have added network connectivity check before api call and auto load data when lost 
network is connected and data was not loaded due to network availability.
Also, could have added pagination to load conferences in batches instead of loading the entire
list at once, filtering and sorting options to allow users to customize the displayed conference list,
search feature to search for conferences based on name or other criteria,push notifications,
enhanced UI and custom animations for visual improvements etc.,

## Libraries Used

**Jetpack Compose**: This app is using latest declarative UI toolkit for android - Jetpack Compose
**Jetpack Navigation**: For managing app's navigation 
**Coroutines**: Used for asynchronous operations  
**Retrofit**: I have used Retrofit for networking along with Okhttp  
**Gson**: Gson for parsing JSON data  
**Mockito**: To mock classes in unit testing
**Glide**: To add support for images