# ConferenceGuide

## Table Of Contents

[Overview](#overview)    
[Libraries Used](#libraries-used)

## Overview

Description:
Conference Guide is an app that lists out conferences around the world with it's  
name and starting date. I have used MVVM design pattern. It consumes REST APIs
to fetch the data over the network. There are two build variants "buildA" and "buildB"
which will show up as title on the app according to build variant chosen during build.
Also the app shows loading, success and error states based off the response received in the api
response. Error state also has retry button to try the network operation again.

It contains a 2 unit tests too to cover the conference list api call and states.

If I had more time, 
1. I could have added network connectivity check before api call and auto load data when lost
network is connected and data was not loaded due to network availability.
2. Added pagination to load conferences in batches instead of loading the entire
list at once
3. Added filtering and sorting options to allow users to customize the displayed conference
list
4. Added a search feature to search for conferences based on name or other criteria
5. Added push notifications,
6. enhanced UI and custom animations for visual improvements
7. Would run health check for better performance and optimization
8. Would have added more unit tests and a11y tests as needed.

## Libraries Used

**Jetpack Compose**: This app is using latest declarative UI toolkit for android - Jetpack Compose
**Jetpack Navigation**: For managing app's navigation
**Coroutines**: Used for asynchronous operations  
**Retrofit**: I have used Retrofit for networking along with Okhttp  
**Gson**: Gson for parsing JSON data  
**Mockito**: To mock classes in unit testing
**Glide**: To add support for images