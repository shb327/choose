package com.example.choose;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {
    private static final String TYPE_ACCOUNT = "com.example.choose";
    private static final RetrofitUtils INSTANCE = new RetrofitUtils();
    private Retrofit retrofit;
    private AccountManager accountManager;


    private RetrofitUtils() {
    }

    public static RetrofitUtils getInstance() {
        return INSTANCE;
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            updateRetrofit();
        }
        return retrofit;
    }

    public void updateRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://choose.teheidoma.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .followRedirects(false)
                        .addInterceptor(chain -> {
                            Request.Builder builder = chain
                                    .request()
                                    .newBuilder();
                            if (hasLogin()) {
                                builder.addHeader("Authorization", getAuthHeader());
                            }
                            return chain.proceed(builder.build());
                        }).build())
                .build();
    }

    @SuppressLint("NewApi")
    private String getAuthHeader() {
        Account account = accountManager.getAccountsByType(TYPE_ACCOUNT)[0];
        String value = account.name + ":" + accountManager.getPassword(account);
        return "Basic " + Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.US_ASCII));
    }

    public boolean hasLogin() {
        return accountManager.getAccountsByType(TYPE_ACCOUNT).length > 0;
    }

    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void login(String login, String password) {
        Account account = new Account(login, TYPE_ACCOUNT);
        accountManager.addAccountExplicitly(account, password, null);
        accountManager.setAuthToken(account, TYPE_ACCOUNT, null);
    }
}
