package com.triputriutami201103644.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;

public class ForexMainActivity extends AppCompatActivity {
    private ProgressBar loadingProgressBar;
    private SwipeRefreshLayout swipeRefreshLayout1;
    private TextView eurTextView, idrTextView, usdTextView, impTextView, inrTextView, iqdTextView, irrTextView, thbTextView, wstTextView, xauTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forex_main);

        swipeRefreshLayout1 = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout1);
        eurTextView = (TextView) findViewById(R.id.eurTextView);
        idrTextView = (TextView) findViewById(R.id.idrTextView);
        usdTextView = (TextView) findViewById(R.id.usdTextView);
        impTextView = (TextView) findViewById(R.id.impTextView);
        inrTextView = (TextView) findViewById(R.id.inrTextView);
        iqdTextView = (TextView) findViewById(R.id.iqdTextView);
        irrTextView = (TextView) findViewById(R.id.irrTextView);
        thbTextView = (TextView) findViewById(R.id.thbTextView);
        wstTextView = (TextView) findViewById(R.id.wstTextView);
        xauTextView = (TextView) findViewById(R.id.xauTextView);
        loadingProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);
        initSwipeRefreshLayout();
        initForex();
    }
    private void initSwipeRefreshLayout() {
        swipeRefreshLayout1.setOnRefreshListener(() -> {
            initForex();

            swipeRefreshLayout1.setRefreshing(false);
        });
    }

    public String formatNumber(double number, String format){
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(number);
    }

    private void initForex() {

        loadingProgressBar.setVisibility(TextView.VISIBLE);

        String url = "https://openexchangerates.org/api/latest.json?app_id=09c15920db6e46289f49f0b0bc5848ef";
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //Log.d("*tw*", new String(responseBody));
                Gson gson = new Gson();
                ForexRootModel rootModel = gson.fromJson(new String(responseBody), ForexRootModel.class);
                ForexRatesModel ratesModel = rootModel.getForexRatesModel();

                double eur  = ratesModel.getIDR() / ratesModel.getEUR();
                double idr = ratesModel.getIDR() / ratesModel.getIDR();
                double usd = ratesModel.getIDR() / ratesModel.getUSD();
                double imp = ratesModel.getIDR() / ratesModel.getIMP();
                double inr = ratesModel.getIDR() / ratesModel.getINR();
                double iqd = ratesModel.getIDR() / ratesModel.getIQD();
                double irr = ratesModel.getIDR() / ratesModel.getIRR();
                double thb = ratesModel.getIDR() / ratesModel.getTHB();
                double wst = ratesModel.getIDR() / ratesModel.getWST();
                double xau = ratesModel.getIDR() / ratesModel.getXAU();



                eurTextView.setText(formatNumber(eur, "###,##0.##"));
                idrTextView.setText(formatNumber(idr, "###,##0.##"));
                usdTextView.setText(formatNumber(usd, "###,##0.##"));
                impTextView.setText(formatNumber(imp, "###,##0.##"));
                inrTextView.setText(formatNumber(inr, "###,##0.##"));
                iqdTextView.setText(formatNumber(iqd, "###,##0.##"));
                irrTextView.setText(formatNumber(irr, "###,##0.##"));
                thbTextView.setText(formatNumber(thb, "###,##0.##"));
                wstTextView.setText(formatNumber(wst, "###,##0.##"));
                xauTextView.setText(formatNumber(xau, "###,##0.##"));

                loadingProgressBar.setVisibility(TextView.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                loadingProgressBar.setVisibility(TextView.INVISIBLE);

            }
        });
    }
}