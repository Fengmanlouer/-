package com.franz.easyweather.param

class WeatherParam {
    companion object{
        const val weatherToken : String = "Sn48_UTg-eC_vtz_l"  //试用版
        const val NowWeatherURL : String = "https://api.seniverse.com/v3/weather/now.json?key=Sn48_UTg-eC_vtz_l"
        const val Future7WeatherURL : String = " https://api.seniverse.com/v3/weather/daily.json?key=Sn48_UTg-eC_vtz_l"
        const val Hourly24WeatherURL : String = " https://api.seniverse.com/v3/weather/hourly.json?key=Sn48_UTg-eC_vtz_l"
    }
}