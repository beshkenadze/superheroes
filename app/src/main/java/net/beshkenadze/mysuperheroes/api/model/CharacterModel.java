package net.beshkenadze.mysuperheroes.api.model;

import org.joda.time.DateTime;

import java.net.URI;

/**
 * Created by Aleksandr Beshkenadze <beshkenadze@gmail.com> on 09/05/15.
 */
public class CharacterModel {
    private int id;
    private String name;
    private String description;
    private DateTime modified;
    private ThumbnailModel thumbnail;
    private URI resourceURI;

    public int getId () {
        return id;
    }

    public String getName () {
        return name;
    }

    public String getDescription () {
        return description;
    }

    public DateTime getModified () {
        return modified;
    }

    public ThumbnailModel getThumbnail () {
        return thumbnail;
    }

    public URI getResourceURI () {
        return resourceURI;
    }
}
