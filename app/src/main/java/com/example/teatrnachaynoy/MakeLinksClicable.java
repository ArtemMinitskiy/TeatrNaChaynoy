package com.example.teatrnachaynoy;

import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class MakeLinksClicable {
    private final static String LOG = MakeLinksClicable.class.getSimpleName();

    public static class CustomerTextClick extends ClickableSpan {
        String mUrl;

        public CustomerTextClick(String url) {
            mUrl = url;
        }

        @Override
        public void onClick(View widget) {
            Log.i(LOG, "url clicked: " + this.mUrl);

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(mUrl));
            widget.getContext().startActivity(i);
        }
    }

    public static SpannableStringBuilder reformatText(CharSequence text) {
        int end = text.length();
        Spannable sp = (Spannable) text;
        URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        for (URLSpan url : urls) {
            style.removeSpan(url);
            MakeLinksClicable.CustomerTextClick click = new MakeLinksClicable.CustomerTextClick(url.getURL());
            style.setSpan(click, sp.getSpanStart(url), sp.getSpanEnd(url),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return style;
    }

    public static void textEditor(TextView textView, String str){
        textView.setText(Html.fromHtml(str, null, null));
        textView.setLinksClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
            CharSequence charSequence = textView.getText();
            if (charSequence instanceof Spannable) {
                textView.setText(MakeLinksClicable.reformatText(charSequence));
            }
    }
}