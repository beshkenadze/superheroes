package net.beshkenadze.mysuperheroes.api;

import net.beshkenadze.mysuperheroes.api.model.CharacterResult;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Aleksandr Beshkenadze <beshkenadze@gmail.com> on 09/05/15.
 */
public interface IMarvelApi {
    @GET("/characters") Observable<CharacterResult> listCharacters ();

    @GET("/characters/{characterId}") Observable<CharacterResult> getCharacter (@Path("characterId") int characterId);
}
