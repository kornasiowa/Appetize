# Appetize
The mobile application for Android allowing for storage recipes and creating shopping lists. </br>
It works with [Appetize Spring Boot Server](https://github.com/kornasiowa/AppetizeServer).

## About
The application is addressed to individual users who want to digitize their recipes in the form of traditional paper notes. 
The main functionality is storage and manage recipes with photos, any number of ingredients and preparation steps. 
Adding (or editing) recipes is possible via manual form completion or using Optical Character Recognition (OCR). 
The application allows you to set an alarm that informs you of the expiry of the cooking time. 
Additionally, the user can create shopping lists and add items to them directly or on the basis of a list of recipe ingredients.

The application supports full CRUD operations for recipes and shopping lists.

The full functionality of the application is available to logged in users via an account set up in the application or authorization with a Google account.

The application has been implemented in the MVP architecture.

## Overview
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

A list of all user's favorite recipes is available in the Favorites tab.

<p float="left">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-125009_Appetize.jpg" width="250">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-131156_Appetize.jpg" width="250">
</p>

The add / edit recipe form contains basic information about the recipe such as title, category, calories, portions and cooking time. 
The user can enter the overall cooking time or choose to automatically count the cooking time based on the duration of all cooking steps.

The user can add any number of ingredients and preparation steps.

Optical Character Recognition (OCR) allows the user to add recipe preparation steps.

<p float="left">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-125107_Appetize.jpg" width="250">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-131120_Appetize.jpg" width="250">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/show_ocr.gif" width="250">
</p>

The user can add items to the created shopping lists from the ingredients included in the recipes or add them manually.

<p float="left">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-131326_Appetize.jpg" width="250">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-131707_Appetize.jpg" width="250">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-131602_Appetize.jpg" width="250">
</p>

The user can add more items to the shopping list at any time, mark already purchased items or delete the entire list.

The number of items on each list is counted (including items already purchased).

<p float="left">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-131815_Appetize.jpg" width="250">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-131822_Appetize.jpg" width="250">
</p>

From the Account tab, the user can change the account password, application theme or log out.

<p float="left">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-132245_Appetize.jpg" width="250">
<img src="https://github.com/kornasiowa/Appetize/blob/master/screenshots/Screenshot_20210217-132238_Appetize.jpg" width="250">
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
