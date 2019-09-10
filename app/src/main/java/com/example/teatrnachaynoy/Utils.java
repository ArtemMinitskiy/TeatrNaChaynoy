package com.example.teatrnachaynoy;

import android.content.Context;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teatrnachaynoy.R;
import com.squareup.picasso.Picasso;

public class Utils {
    public final static String THEATER_URL = "http://tea-atr.com";
    public final static String GOOGLE_MAPS_URL = "https://www.google.com/maps/place/%D0%A2%D0%B5%D0%B0%D1%82%D1%80+%D0%BD%D0%B0+%D0%A7%D0%B0%D0%B9%D0%BD%D0%BE%D0%B9/@46.4780209,30.7480462,15z/data=!4m5!3m4!1s0x0:0x43aa6c1fb0d64eec!8m2!3d46.4787744!4d30.7496772?hl=ru-RU";
    public final static String VK_URL = "https://vkontakte.ru/club16711428";
    public final static String FACEBOOK_URL = "https://www.facebook.com/teatrnachaynoy";
    public final static String INSTAGRAM_URL = "https://www.instagram.com/tea_atr/";

    public static String getImageSrc(String name) {
        String src = null;

        switch (name) {
            case "Александр Бойко":
                src = "http://tea-atr.com/images/u/person/4.jpg?v=1404166837";
                break;
            case "Александр Онищенко":
                src = "http://tea-atr.com/images/u/person/1.jpg?v=1383865774";
                break;
            case "Ангелина Смиян":
                src = "http://tea-atr.com/images/u/person/46.jpg?v=1494348313";
                break;
            case "Анна Милешина":
                src = "http://tea-atr.com/images/u/person/49.jpg?v=1553116373";
                break;
            case "Валерия Задумкина":
                src = "http://tea-atr.com/images/u/person/9.jpg?v=1502195911";
                break;
            case "Владимир Ангел":
                src = "http://tea-atr.com/images/u/person/42.jpg?v=1489614336";
                break;
            case "Владислав Костыка":
                src = "http://tea-atr.com/images/u/person/10.jpg?v=1540412793";
                break;
            case "Денис Фалюта":
                src = "http://tea-atr.com/images/u/person/32.jpg?v=1411661405";
                break;
            case "Егор Карельский":
                src = "http://tea-atr.com/images/u/person/47.jpg?v=1494347263";
                break;
            case "Елена Юзвак":
                src = "http://tea-atr.com/images/u/person/5.jpg?v=1380663572";
                break;
            case "Ирина Костырко":
                src = "http://tea-atr.com/images/u/person/7.jpg?v=1380745782";
                break;
            case "Милена Компаниец":
                src = "http://tea-atr.com/images/u/person/50.jpg?v=1547163941";
                break;
            case "Олег Симоненко":
                src = "http://tea-atr.com/images/u/person/40.jpg?v=1467660103";
                break;
            case "Олег Фендюра":
                src = "http://tea-atr.com/images/u/person/41.jpg?v=1467144625";
                break;
            case "Олег Шевчук":
                src = "http://tea-atr.com/images/u/person/13.jpg?v=1383679196";
                break;
            case "Ольга Белоконь":
                src = "http://tea-atr.com/images/u/person/51.jpg?v=1556533542";
                break;
            case "Ольга Салтыкова":
                src = "http://tea-atr.com/images/u/person/12.jpg?v=1378967052";
                break;
            case "Руслана Рудая":
                src = "http://tea-atr.com/images/u/person/3.jpg?v=1395664011";
                break;
            case "Сергей Деньга":
                src = "http://tea-atr.com/images/u/person/43.jpg?v=1554714954";
                break;
            case "Татьяна Параскева":
                src = "http://tea-atr.com/images/u/person/2.jpg?v=1379877683";
                break;
            case "Филипп Азаренко":
                src = "http://tea-atr.com/images/u/person/14.jpg?v=1379878307";
                break;
            case "Юлия Амелькина":
                src = "http://tea-atr.com/images/u/person/39.jpg?v=1447941593";
                break;
            case "Юрий Невгамонный":
                src = "http://tea-atr.com/images/u/person/44.jpg?v=1502391146";
                break;
            default:
                break;
        }

        return src;
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String imageUrl) {
        Picasso.get().load(imageUrl).into(imageView);
    }

    @BindingAdapter({"imageActorsUrl"})
    public static void loadActorsImage(ImageView imageView, String imageUrl) {
        Picasso.get().load(imageUrl).into(imageView);
    }

    public static void recyclerAnimated(RecyclerView recyclerView, RecyclerView.Adapter adapter, Context context){
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_right);
        recyclerView.setLayoutAnimation(controller);
        adapter.notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

}
