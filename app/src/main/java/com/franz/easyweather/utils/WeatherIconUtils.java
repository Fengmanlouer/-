package com.franz.easyweather.utils;

import android.graphics.drawable.Drawable;

import com.franz.easyweather.R;

public class WeatherIconUtils {
    /**
     * 图标代码	天气	白天	夜晚
     * 100	晴	    ✅	❌
     * 101	多云	    ✅	❌
     * 102	少云  	✅	❌
     * 103	晴间多云	✅	❌
     * 104	阴	    ✅	✅
     * 150	晴	    ❌	✅
     * 151	多云	    ❌	✅
     * 152	少云   	❌	✅
     * 153	晴间多云	❌	✅
     * 154	阴	    ✅	✅
     * 300	阵雨  	✅	❌
     * 301	强阵雨	✅	❌
     * 302	雷阵雨	✅	✅
     * 303	强雷阵雨	✅	✅
     * 304	雷阵雨伴有冰雹	✅	✅
     * 305	小雨	    ✅	✅
     * 306	中雨	    ✅	✅
     * 307	大雨	    ✅	✅
     * 308	极端降雨	✅	✅
     * 309	毛毛雨	✅	✅
     * 310	暴雨	    ✅	✅
     * 311	大暴雨	✅	✅
     * 312	特大暴雨	✅	✅
     * 313	冻雨	    ✅	✅
     * 314	小到中雨	✅	✅
     * 315	中到大雨	✅	✅
     * 316	大到暴雨	✅	✅
     * 317	暴雨到大暴雨	✅	✅
     * 318	大暴雨到特大暴雨	✅	✅
     * 350	阵雨	    ❌	✅
     * 351	强阵雨	❌	✅
     * 399	雨	    ✅	✅
     * 400	小雪  	✅	✅
     * 401	中雪	    ✅	✅
     * 402	大雪	    ✅	✅
     * 403	暴雪  	✅	✅
     * 404	雨夹雪	✅	✅
     * 405	雨雪天气	✅	✅
     * 406	阵雨夹雪	✅	❌
     * 407	阵雪  	✅	❌
     * 408	小到中雪	✅	✅
     * 409	中到大雪	✅	✅
     * 410	大到暴雪	✅	✅
     * 456	阵雨夹雪	❌	✅
     * 457	阵雪	    ❌	✅
     * 499	雪	    ✅	✅
     * 500	薄雾	    ✅	✅
     * 501	雾	    ✅	✅
     * 502	霾	    ✅	✅
     * 503	扬沙  	✅	✅
     * 504	浮尘	    ✅	✅
     * 507	沙尘暴	✅	✅
     * 508	强沙尘暴	✅	✅
     * 509	浓雾	    ✅	✅
     * 510	强浓雾	✅	✅
     * 511	中度霾	✅	✅
     * 512	重度霾	✅	✅
     * 513	严重霾	✅	✅
     * 514	大雾	    ✅	✅
     * 515	特强浓雾	✅	✅
     * 800	新月	    ✅	✅
     * 801	蛾眉月	✅	✅
     * 802	上弦月	✅	✅
     * 803	盈凸月	✅	✅
     * 804	满月	    ✅	✅
     * 805	亏凸月	✅	✅
     * 806	下弦月	✅	✅
     * 807	残月	    ✅	✅
     * 900	热	    ✅	✅
     * 901	冷	    ✅	✅
     * 999	未知  	✅	✅
     * */

    public static int getWeatherCode(String status){
        int icon = 0;
        switch (status){
            case "晴": icon =  R.drawable.vector_drawable_100_fill;break;
            case "多云": icon =  R.drawable.vector_drawable_101_fill;break;
            case "少云": icon =  R.drawable.vector_drawable_102_fill;break;
            case "晴间多云": icon =  R.drawable.vector_drawable_103_fill;break;
            case "阴": icon =  R.drawable.vector_drawable_104_fill;break;
            case "阵雨": icon =  R.drawable.vector_drawable_300_fill;break;
            case "强阵雨": icon =  R.drawable.vector_drawable_301_fill;break;
            case "雷阵雨": icon =  R.drawable.vector_drawable_302_fill;break;
            case "强雷阵雨": icon =  R.drawable.vector_drawable_303_fill;break;
            case "雷阵雨伴有冰雹": icon =  R.drawable.vector_drawable_304_fill;break;
            case "小雨": icon =  R.drawable.vector_drawable_305_fill;break;
            case "中雨": icon =  R.drawable.vector_drawable_306_fill;break;
            case "大雨": icon =  R.drawable.vector_drawable_307_fill;break;
            case "极端降雨": icon =  R.drawable.vector_drawable_308_fill;break;
            case "毛毛雨": icon =  R.drawable.vector_drawable_309_fill;break;
            case "暴雨": icon =  R.drawable.vector_drawable_310_fill;break;
            case "大暴雨": icon =  R.drawable.vector_drawable_311_fill;break;
            case "特大暴雨": icon =  R.drawable.vector_drawable_312_fill;break;
            case "冻雨": icon =  R.drawable.vector_drawable_313_fill;break;
            case "小到中雨": icon =  R.drawable.vector_drawable_314_fill;break;
            case "中到大雨": icon =  R.drawable.vector_drawable_315_fill;break;
            case "大到暴雨": icon =  R.drawable.vector_drawable_316_fill;break;
            case "暴雨到大暴雨": icon =  R.drawable.vector_drawable_317_fill;break;
            case "大暴雨到特大暴雨": icon =  R.drawable.vector_drawable_318_fill;break;
            case "雨": icon =  R.drawable.vector_drawable_399_fill;break;
            case "小雪": icon =  R.drawable.vector_drawable_400_fill;break;
            case "中雪": icon =  R.drawable.vector_drawable_401_fill;break;
            case "大雪": icon =  R.drawable.vector_drawable_402_fill;break;
            case "暴雪": icon =  R.drawable.vector_drawable_403_fill;break;
            case "雨夹雪": icon =  R.drawable.vector_drawable_404_fill;break;
            case "雨雪天气": icon =  R.drawable.vector_drawable_405_fill;break;
            case "阵雨夹雪": icon =  R.drawable.vector_drawable_406_fill;break;
            case "阵雪": icon =  R.drawable.vector_drawable_407_fill;break;
            case "小到中雪": icon =  R.drawable.vector_drawable_408_fill;break;
            case "中到大雪": icon =  R.drawable.vector_drawable_409_fill;break;
            case "大到暴雪": icon =  R.drawable.vector_drawable_410_fill;break;
            case "456": icon =  R.drawable.vector_drawable_456_fill;break;
            case "457": icon =  R.drawable.vector_drawable_457_fill;break;
            case "雪": icon =  R.drawable.vector_drawable_499_fill;break;
            case "薄雾": icon =  R.drawable.vector_drawable_500_fill;break;
            case "雾": icon =  R.drawable.vector_drawable_501_fill;break;
            case "霾": icon =  R.drawable.vector_drawable_502_fill;break;
            case "扬沙": icon =  R.drawable.vector_drawable_503_fill;break;
            case "浮尘": icon =  R.drawable.vector_drawable_504_fill;break;
            case "沙尘暴": icon =  R.drawable.vector_drawable_507_fill;break;
            case "强沙尘暴": icon =  R.drawable.vector_drawable_508_fill;break;
            case "浓雾": icon =  R.drawable.vector_drawable_509_fill;break;
            case "强浓雾": icon =  R.drawable.vector_drawable_510_fill;break;
            case "中度霾": icon =  R.drawable.vector_drawable_511_fill;break;
            case "重度霾": icon =  R.drawable.vector_drawable_512_fill;break;
            case "严重霾": icon =  R.drawable.vector_drawable_513_fill;break;
            case "大雾": icon =  R.drawable.vector_drawable_514_fill;break;
            case "特强浓雾": icon =  R.drawable.vector_drawable_515_fill;break;
            case "新月": icon =  R.drawable.vector_drawable_800;break;
            case "蛾眉月": icon =  R.drawable.vector_drawable_801;break;
            case "上弦月": icon =  R.drawable.vector_drawable_802;break;
            case "盈凸月": icon =  R.drawable.vector_drawable_803;break;
            case "满月": icon =  R.drawable.vector_drawable_804;break;
            case "亏凸月": icon =  R.drawable.vector_drawable_805;break;
            case "下弦月": icon =  R.drawable.vector_drawable_806;break;
            case "残月": icon =  R.drawable.vector_drawable_807;break;
            case "热": icon =  R.drawable.vector_drawable_900_fill;break;
            case "冷": icon =  R.drawable.vector_drawable_901_fill;break;
            default:  icon =  R.drawable.vector_drawable_999_fill;break;
        }
        return icon;
    }

    public static int getWeatherIcon(int code){
        int icon = 0;
       switch (code){
           case 100: icon =  R.drawable.vector_drawable_100_fill;break;
           case 101: icon =  R.drawable.vector_drawable_101_fill;break;
           case 102: icon =  R.drawable.vector_drawable_102_fill;break;
           case 103: icon =  R.drawable.vector_drawable_103_fill;break;
           case 104: icon =  R.drawable.vector_drawable_104_fill;break;
           case 150: icon =  R.drawable.vector_drawable_150_fill;break;
           case 151: icon =  R.drawable.vector_drawable_151_fill;break;
           case 152: icon =  R.drawable.vector_drawable_152_fill;break;
           case 153: icon =  R.drawable.vector_drawable_153_fill;break;
           case 154: icon =  R.drawable.vector_drawable_153_fill;break;//haven't
           case 300: icon =  R.drawable.vector_drawable_300_fill;break;
           case 301: icon =  R.drawable.vector_drawable_301_fill;break;
           case 302: icon =  R.drawable.vector_drawable_302_fill;break;
           case 303: icon =  R.drawable.vector_drawable_303_fill;break;
           case 304: icon =  R.drawable.vector_drawable_304_fill;break;
           case 305: icon =  R.drawable.vector_drawable_305_fill;break;
           case 306: icon =  R.drawable.vector_drawable_306_fill;break;
           case 307: icon =  R.drawable.vector_drawable_307_fill;break;
           case 308: icon =  R.drawable.vector_drawable_308_fill;break;
           case 309: icon =  R.drawable.vector_drawable_309_fill;break;
           case 310: icon =  R.drawable.vector_drawable_310_fill;break;
           case 311: icon =  R.drawable.vector_drawable_311_fill;break;
           case 312: icon =  R.drawable.vector_drawable_312_fill;break;
           case 313: icon =  R.drawable.vector_drawable_313_fill;break;
           case 314: icon =  R.drawable.vector_drawable_314_fill;break;
           case 315: icon =  R.drawable.vector_drawable_315_fill;break;
           case 316: icon =  R.drawable.vector_drawable_316_fill;break;
           case 317: icon =  R.drawable.vector_drawable_317_fill;break;
           case 318: icon =  R.drawable.vector_drawable_318_fill;break;
           case 350: icon =  R.drawable.vector_drawable_350_fill;break;
           case 351: icon =  R.drawable.vector_drawable_351_fill;break;
           case 399: icon =  R.drawable.vector_drawable_399_fill;break;
           case 400: icon =  R.drawable.vector_drawable_400_fill;break;
           case 401: icon =  R.drawable.vector_drawable_401_fill;break;
           case 402: icon =  R.drawable.vector_drawable_402_fill;break;
           case 403: icon =  R.drawable.vector_drawable_403_fill;break;
           case 404: icon =  R.drawable.vector_drawable_404_fill;break;
           case 405: icon =  R.drawable.vector_drawable_405_fill;break;
           case 406: icon =  R.drawable.vector_drawable_406_fill;break;
           case 407: icon =  R.drawable.vector_drawable_407_fill;break;
           case 408: icon =  R.drawable.vector_drawable_408_fill;break;
           case 409: icon =  R.drawable.vector_drawable_409_fill;break;
           case 410: icon =  R.drawable.vector_drawable_410_fill;break;
           case 456: icon =  R.drawable.vector_drawable_456_fill;break;
           case 457: icon =  R.drawable.vector_drawable_457_fill;break;
           case 499: icon =  R.drawable.vector_drawable_499_fill;break;
           case 500: icon =  R.drawable.vector_drawable_500_fill;break;
           case 501: icon =  R.drawable.vector_drawable_501_fill;break;
           case 502: icon =  R.drawable.vector_drawable_502_fill;break;
           case 503: icon =  R.drawable.vector_drawable_503_fill;break;
           case 504: icon =  R.drawable.vector_drawable_504_fill;break;
           case 507: icon =  R.drawable.vector_drawable_507_fill;break;
           case 508: icon =  R.drawable.vector_drawable_508_fill;break;
           case 509: icon =  R.drawable.vector_drawable_509_fill;break;
           case 510: icon =  R.drawable.vector_drawable_510_fill;break;
           case 511: icon =  R.drawable.vector_drawable_511_fill;break;
           case 512: icon =  R.drawable.vector_drawable_512_fill;break;
           case 513: icon =  R.drawable.vector_drawable_513_fill;break;
           case 514: icon =  R.drawable.vector_drawable_514_fill;break;
           case 515: icon =  R.drawable.vector_drawable_515_fill;break;
           case 800: icon =  R.drawable.vector_drawable_800;break;
           case 801: icon =  R.drawable.vector_drawable_801;break;
           case 802: icon =  R.drawable.vector_drawable_802;break;
           case 803: icon =  R.drawable.vector_drawable_803;break;
           case 804: icon =  R.drawable.vector_drawable_804;break;
           case 805: icon =  R.drawable.vector_drawable_805;break;
           case 806: icon =  R.drawable.vector_drawable_806;break;
           case 807: icon =  R.drawable.vector_drawable_807;break;
           case 900: icon =  R.drawable.vector_drawable_900_fill;break;
           case 901: icon =  R.drawable.vector_drawable_901_fill;break;
           case 999: icon =  R.drawable.vector_drawable_999_fill;break;
           default:  icon =  R.drawable.vector_drawable_999_fill;break;
       }
       return icon;
    }
}
