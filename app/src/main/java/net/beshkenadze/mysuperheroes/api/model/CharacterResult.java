package net.beshkenadze.mysuperheroes.api.model;

/**
 * Created by Aleksandr Beshkenadze <beshkenadze@gmail.com> on 09/05/15.
 */
public class CharacterResult extends BaseRequestModel {
    private RequestResultModel<CharacterModel> data = new RequestResultModel<>();

    public RequestResultModel<CharacterModel> getData () {
        return data;
    }
}
