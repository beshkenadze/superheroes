package net.beshkenadze.mysuperheroes.api;

import com.squareup.okhttp.OkHttpClient;

import net.beshkenadze.mysuperheroes.BuildConfig;
import net.beshkenadze.mysuperheroes.api.model.CharacterModel;
import net.beshkenadze.mysuperheroes.api.model.CharacterResult;
import net.beshkenadze.mysuperheroes.util.CryptUtils;
import net.beshkenadze.mysuperheroes.util.GsonUtil;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Aleksandr Beshkenadze <beshkenadze@gmail.com> on 09/05/15.
 */
public class MarvelService {
    private static MarvelService sInstance;
    private final IMarvelApi mApi;

    public MarvelService () {
        mApi = getAdapter().create(IMarvelApi.class);
    }

    public static MarvelService getInstance () {
        if (sInstance == null) {
            synchronized (GsonUtil.class) {
                if (sInstance == null) {
                    sInstance = new MarvelService();
                }
            }
        }
        return sInstance;
    }


    private static RestAdapter getAdapter () {
        OkHttpClient client = new OkHttpClient();

        return new RestAdapter.Builder()
                .setClient(new OkClient(client))
                .setEndpoint(BuildConfig.API_URL)
                .setErrorHandler(new MarvelErrorHandler())
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setConverter(new GsonConverter(GsonUtil.getInstance()))
                .setRequestInterceptor(new MarvelRequestInterceptor())
                .build();
    }

    public Observable<CharacterResult> listCharacters () {
        return mApi
                .listCharacters()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<CharacterModel> getCharacter (int id) {
        return mApi
                .getCharacter(id)
                .flatMap(new Func1<CharacterResult, Observable<CharacterModel>>() {
                    @Override public Observable<CharacterModel> call (CharacterResult characterResult) {
                        List<CharacterModel> result = characterResult.getData().getResults();
                        if (result.size() > 0) {
                            return Observable.just(result.get(0));
                        }
                        return Observable.just(new CharacterModel());
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static class MarvelErrorHandler implements ErrorHandler {
        @Override
        public Throwable handleError (RetrofitError cause) {
            if (cause.getKind() == RetrofitError.Kind.NETWORK) {
                return cause;
            } else {
                switch (cause.getResponse().getStatus()) {
                    case 409: // todo: process MissingParameter
                        return cause;
                }
            }
            return cause;
        }
    }

    private static class MarvelRequestInterceptor implements RequestInterceptor {
        @Override public void intercept (RequestFacade request) {
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            request.addQueryParam("apikey", BuildConfig.API_PUBLIC_KEY);
            String hash = "";
            try {
                hash = CryptUtils.MD5(timestamp + BuildConfig.API_PRIVATE_KEY + BuildConfig.API_PUBLIC_KEY);
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            request.addQueryParam("hash", hash); // md5(ts+privateKey+publicKey)
            request.addQueryParam("ts", timestamp);
        }
    }
}
