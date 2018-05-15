# MyGooglePlaceApp
A simple Android project that help users search places that are near their location and show their details in a list. They can also see these places on a map and see the details on a new page. The project uses Google services for the search results and consumes REST api. 

<h2>Components and Features</h2>

Following are the prominent features in the app.

* **Place Search Widget**: An attractive search widget where users can type the name or type of a place they need to search. An Edittext widget with custom style is used with a search icon button. 
* **Customized ListView**: Displays as many search results for the places that are found in near 1000meter radius of the user's current location. It shows the icon,full names of the places, type of place eg. cafe, restuarant, pub etc and the address of the place.
* **Look-up places on Map**: The search results in the list can be viewed in a Map as pointers. Clicking on each pointer takes users to a new page with the description of the respected place.
* **Offline results**: The app works if the internet is not available and shows the results of the last query searched.

<h3> APIs used</h3>

* *Google Place API*
* *Google Maps Android API V2*

<h3>Building using Android Studio...</h3>

1. Open Android Studio and launch the Android SDK manager from it (Tools | Android | SDK Manager)
1. Ensure the following components are installed and updated to the latest version.
   1. *Android SDK Platform-Tools*
   1. *Android Support Repository*
   1. *Google Repository*
1. Return to Android Studio and select *Open an existing Android Studio project*
1. Select the **MyGooglePlaceApp** directory.

## Authors

* **Sabahat Qazi Immad** 

<h3>Reference</h3>
The app was made for Buzzmove as a code show sample.
