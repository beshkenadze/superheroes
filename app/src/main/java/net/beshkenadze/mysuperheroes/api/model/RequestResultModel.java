package net.beshkenadze.mysuperheroes.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandr Beshkenadze <beshkenadze@gmail.com> on 09/05/15.
 */
public class RequestResultModel<T> {
    private int offset = -1;
    private int limit = -1;
    private int total = -1;
    private int count = -1;

    private List<T> results = new ArrayList<>();

    public int getOffset () {
        return offset;
    }

    public int getLimit () {
        return limit;
    }

    public int getTotal () {
        return total;
    }

    public int getCount () {
        return count;
    }

    public List<T> getResults () {
        return results;
    }
}
