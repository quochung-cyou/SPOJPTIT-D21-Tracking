package com.quochung.spojproptit_d21.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.quochung.spojproptit_d21.Adapter.ParseAdapter;
import com.quochung.spojproptit_d21.Model.Member;
import com.quochung.spojproptit_d21.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private TextView dem;
    private ArrayList<Member> memberlist = new ArrayList<>();
    private ParseAdapter parseadapter;
    private LottieAnimationView progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Content content = new Content();
        content.execute();
    }

    private void demupdate(int x, int y) {
        new Handler(Looper.getMainLooper()).post(() -> dem.setText(x + "/" + y));

    }

    private void init() {
        progressBar = findViewById(R.id.progressbar);
        dem = findViewById(R.id.dem);
        RecyclerView viewmemberlist = findViewById(R.id.memberlist);
        viewmemberlist.setHasFixedSize(true);
        viewmemberlist.setLayoutManager(new LinearLayoutManager(this));
        parseadapter = new ParseAdapter(memberlist, this);
        viewmemberlist.setAdapter(parseadapter);

    }



    private class Content extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            dem.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            dem.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));
            Collections.sort(memberlist, (b, a) -> Integer.compare(a.getProblemamount(), b.getProblemamount()));
            int count = 1;
            for (Member member : memberlist) {
                member.setXephang(String.valueOf(count));
                count++;
            }
            parseadapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String url = "https://www.spoj.com/PTIT/users/";
            String[] name = new String[]{
                    "quochung_cyou",
                    "leetristaam",
                    "lom123ga",
                    "thietdongggggggggg",
                    "team1_danghuy",
                    "nickken_253",
                    "wos2610",
                    "d21proletrung",
                    "luuphuongthao",
                    "maiphuong294",
                    "manhdung25703",
                    "linh_tran2022",
                    "dzuong33",
                    "proptit_nnaadd",
                    "linhmyx1512",
                    "danghan6623",
                    "lordierclaw",
                    "monkeydminh49",
                    "ProPTIT_ZERO",
                    "gacode93",
                    "lew_203",
                    "quynh_le909",
                    "nguyetminh212",
                    "team5_minhanh",
            };
            int countxong = 0;
            for (String s : name) {
                try {
                    String urls = url + s;
                    int count = 0;
                    Document data = Jsoup.connect(urls).get();
                    String avatarurl = data.select(".img-thumbnail").attr("src");
                    Element list = data.select("table.table-condensed").first();
                    if (list == null) continue;
                    Elements list1 = list.select("td > a");
                    if (list1.isEmpty()) continue;
                    //Log.d("DEBUG1", String.valueOf(list1.size()) + " " + avatarurl);
                    for (Element link : list1) {
                        if (list1.isEmpty()) continue;
                        //Log.d("DEBUG1", link.attr("href"));
                        if (link.attr("href").contains(s) && link.attr("href").contains("/PTIT/status/")) {
                            int index1 = String.valueOf(link).indexOf("/PTIT/status/");
                            int index2 = String.valueOf(link).indexOf(s);
                            if (String.valueOf(link).substring(index1, index2).length() > 14) {
                                count++;
                            }
                        }
                    }
                    Member newmember = new Member(avatarurl, s, count);
                    memberlist.add(newmember);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                countxong++;
                demupdate(countxong, name.length);

            }
            return null;
        }
    }


}

