package com.quochung.spojproptit_d21.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
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
    RelativeLayout phantren;
    private TextView dem, tentop1, sobaitop1;
    private ImageView avatartop1;
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

    private void demupdate(int x, int y, String ten) {
        new Handler(Looper.getMainLooper()).post(() -> dem.setText(x + "/" + y + " - " + ten));

    }

    private void updatetop1(String avatar, String ten, int sobai) {
        tentop1.setText(ten);
        sobaitop1.setText(String.valueOf(sobai) + " bài");
        new Handler(Looper.getMainLooper()).post(() -> Glide.with(this).load(avatar).placeholder(R.drawable.pro).into(avatartop1));

    }

    private void init() {
        progressBar = findViewById(R.id.progressbar);
        phantren = findViewById(R.id.header);
        tentop1 = findViewById(R.id.usernametop1);
        sobaitop1 = findViewById(R.id.sobailamduoc);
        avatartop1 = findViewById(R.id.avatartop1);
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
            phantren.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            dem.setVisibility(View.VISIBLE);

            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            dem.setVisibility(View.GONE);
            phantren.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_out_right));
            Collections.sort(memberlist, (b, a) -> Integer.compare(a.getProblemamount(), b.getProblemamount()));
            int count = 1;
            for (Member member : memberlist) {
                member.setXephang(String.valueOf(count));
                count++;
            }
            Log.d("DEBUG D21", memberlist.get(0).getUsername());
            Log.d("DEBUG D21", String.valueOf(memberlist.get(0).getProblemamount()));
            updatetop1(memberlist.get(0).getAvatarurl(), memberlist.get(0).getUsername(), memberlist.get(0).getProblemamount());
            memberlist.remove(0);
            parseadapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String url = "https://www.spoj.com/PTIT/users/";
            String[] name = new String[]{
                    "leetristaam",
                    "lom123ga",
                    "thietyeuthao",
                    "team1_danghuy",
                    "nickken_235",
                    "wos2610",
                    "d21proletrung",
                    "luuphuongthao",
                    "maiphuong294",
                    "manhdung25703",
                    "greed_3",
                    "linh_tran2022",
                    "dzuong33",
                    "quochung_cyou",
                    "proptit_nnaadd",
                    "linhmyx1512",
                    "danghan6623",
                    "lordierclaw",
                    "monkeydminh49",
                    "quocanh_2003",
                    "gacode93",
                    "lew_203",
                    "quynh_le909",
                    "nguyetminh212",
                    "team5_minhanh",
                    "team1_dthieu",
            };
            String[] tenthat = new String[]{
                    "Lê Trí Tâm",
                    "Triệu Ngọc Tâm",
                    "Vũ Đình Thiết",
                    "Nguyễn Đăng Huy",
                    "Đinh Hoàng Anh",
                    "Nguyễn Thị Ngọc Thúy",
                    "Lê Quốc Trung",
                    "Lưu Phương Thảo",
                    "Nguyễn Mai Phương",
                    "Hoàng Mạnh Dũng",
                    "Lê Trọng Đạt",
                    "Trần Khánh Linh",
                    "Đỗ Hoàng Dương",
                    "Nguyễn Quốc Hưng",
                    "Nguyễn Anh Đức",
                    "Phạm Thị Linh Mỹ",
                    "Ngô Đăng Hán",
                    "Nguyễn Nam Hải",
                    "Nguyễn Đăng Minh",
                    "Hoàng Quốc Anh",
                    "Lê Anh Tuấn",
                    "Lưu Minh Hiếu",
                    "Lê Như Quỳnh",
                    "Đặng Nguyệt Minh",
                    "Đàm Minh Anh",
                    "Đặng Trung Hiếu",
            };
            int countxong = 0;
            for (int index = 0; index < name.length; index++) {
                String s = name[index];
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
                    Member newmember = new Member(avatarurl, s, count, tenthat[index]);
                    memberlist.add(newmember);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                countxong++;
                demupdate(countxong, name.length, tenthat[index]);

            }
            return null;
        }
    }


}

