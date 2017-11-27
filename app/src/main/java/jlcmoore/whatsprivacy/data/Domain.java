package jlcmoore.whatsprivacy.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import static java.lang.Integer.parseInt;

/**
 * Created by jared on 11/12/17.
 */

@Entity(tableName = "domains")
public class Domain {
    @PrimaryKey
    public final int id;
    public final String name;

    public Domain(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Domain(String[] row) {
        if (row.length != 2) {
            throw new IllegalArgumentException();
        }
        id = parseInt(row[0]);
        name = row[1];
    }
}