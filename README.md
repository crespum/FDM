# FDM

This repository contains the source code of the FDM App available on the Play Store:

<a href="https://play.google.com/store/apps/details?id=com.caracocha.fdm">
<img alt="Get it on Google Play" src="http://steverichey.github.io/google-play-badge-svg/img/en_get.svg" />
</a>

This app will show over a ```RecyclerView``` a list of ```CardView```s representing the items downloaded from a JSON file. In case you want to use this app to display your own items (events) you only need to modify the ```JSON_LINK``` variable at ```EventListFragment.java```.

Each item is a JSON object such as the described below:

```json
{
    "EVENT_NAME": "This contains the title of the event or the information to be displayed on an INFO/AD item",
    "DAY": "dd/MM/yyyy",
    "TYPE": "EVENT/INFO/AD",
    "PLACE": "Place",
    "PRICE": "2€",
    "START_TIME": "hh:mm",
    "DESCRIPTION": "Brief description of the event",
    "LATITUDE": "41.899915",
    "LONGITUDE": "-8.873203",
    "CATEGORY": "art/music/band/fire/food/pregon",
    "URL": "URL where you can get more information"
  }
  ```
