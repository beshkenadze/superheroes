package net.beshkenadze.mysuperheroes.api.model;

/**
 * Created by Aleksandr Beshkenadze <beshkenadze@gmail.com> on 09/05/15.
 */
public class ThumbnailModel {
    private String path;
    private String extension;

    public String getPath () {
        return path;
    }

    public String getExtension () {
        return extension;
    }

    public String getUrl () {
        return path.concat(".").concat(extension);
    }
}
