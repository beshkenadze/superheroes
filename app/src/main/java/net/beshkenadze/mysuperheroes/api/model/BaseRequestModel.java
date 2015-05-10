package net.beshkenadze.mysuperheroes.api.model;

/**
 * Created by Aleksandr Beshkenadze <beshkenadze@gmail.com> on 09/05/15.
 */
public class BaseRequestModel {
    private int code = -1;
    private String status;

    private String attributionText;
    private String attributionHTML;
    private String etag;

    public int getCode () {
        return code;
    }

    public String getStatus () {
        return status;
    }

    public String getAttributionText () {
        return attributionText;
    }

    public String getAttributionHTML () {
        return attributionHTML;
    }

    public String getEtag () {
        return etag;
    }
}
