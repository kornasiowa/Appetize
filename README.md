# Appetize
The mobile application for Android allowing for storage recipes and creating shopping lists. </br>
It works with my [Appetize Spring Boot Server](github.com/kornasiowa/AppetizeServer).

## About
The application is addressed to individual users who want to digitize their recipes in the form of traditional paper notes. 
The main functionality is storage and manage recipes with photos, any number of ingredients and preparation steps. 
Adding (or editing) recipes is possible via manual form completion or using optical character recognition (OCR). 
The application allows you to set an alarm that informs you of the expiry of the cooking time. 
Additionally, the user can create shopping lists and add items to them directly or on the basis of a list of recipe ingredients.

The application supports full CRUD operations for recipes and shopping lists.

The full functionality of the application is available to logged in users via an account set up in the application or authorization with a Google account.

## Overwiew
The login screen allows the user to authorize with an application account or a Google platform account. 
Additionally, the user can also go to the registration form or send a request to reset a forgotten password.

<p float="left">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-121017_Appetize.jpg" width="250">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-121042_Appetize.jpg" width="250">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-121028_Appetize.jpg" width="250">
</p>

The recipe can be assigned to one of four categories: appetizer, dessert, main course, other. 
The recipes in each category can be reviewed on the main screen of the application.

<p float="left">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-121120_Appetize.jpg" width="200">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-121128_Appetize.jpg" width="200">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-121135_Appetize.jpg" width="200">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-121143_Appetize.jpg" width="200">
</p>

Selecting a recipe displays details of calories, cooking time, portion sizes, ingredient list and preparation steps.

<p float="left">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-120617_Appetize.jpg" width="250">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-120634_Appetize.jpg" width="250">
</p>

From this screen, the user can mark the recipe as favorite by clicking the star in the corner of the photo 
or set a timer that measures the cooking time of the entire dish (the timer can also be set for each step separately if it has a defined duration). 

A list of all your favorite recipes is available in the Favorites tab.

<p float="left">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-125009_Appetize.jpg" width="250">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-131156_Appetize.jpg" width="250">
</p>

## App themes
The application provides two UI color themes: Sky Blue and Sunset Orange. 

<p float="left">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-121017_Appetize.jpg" width="250">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-132402_Appetize.jpg" width="250">
</p>

<p float="left">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-121120_Appetize.jpg" width="250">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-132301_Appetize.jpg" width="250">
</p>

The application's default theme is Sky Blue. The user can change it in the Account tab.

<p float="left">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-132251_Appetize.jpg" width="250">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-172608_Appetize.jpg" width="250">
</p>

## Tools
- Java 8
- Android 
- Google Firebase Authentication and Storage
- Google Play Services
- Retrofit
- OkHTTP
- Glide v4
