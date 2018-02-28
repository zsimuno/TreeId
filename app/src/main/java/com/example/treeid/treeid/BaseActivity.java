package com.example.treeid.treeid;

import android.graphics.Typeface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Stjepan on 2/28/18.
 */

public class BaseActivity extends AppCompatActivity {

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return MenuChoice(item);
    }

    private boolean MenuChoice(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.about:
                createDialog();
                return true;
            default:
                break;
        }
        return false;
    }

    private void createDialog(){
        final TextView message = new TextView(this);
        message.setPadding(10, 5, 2, 0);

        String bnum = getString(R.string.bVerzija);
        String num = getString(R.string.verzija);
        String bauthor = getString(R.string.bAutori);
        String author = getString(R.string.autori);
        String bemail = getString(R.string.bEmail);
        String email = getString(R.string.emailAndInfo);

        final SpannableString s =
                new SpannableString(bnum + num + bauthor + author + bemail + email);

        //Making text bold
        int start = 0;
        int end = bnum.length();
        s.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = bnum.length() + num.length();
        end = start + bauthor.length();
        s.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = end + author.length();
        end = start + bemail.length();
        s.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //Finding email
        Linkify.addLinks(s, Linkify.EMAIL_ADDRESSES);

        message.setText( s );
        message.setMovementMethod(LinkMovementMethod.getInstance());

        //Creating AlertDialog
        final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        alertbox.setTitle(R.string.about)
                .setCancelable(true)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton(R.string.closeDialog, null)
                .setView(message)
                .create();

        alertbox.show();
    }
}
