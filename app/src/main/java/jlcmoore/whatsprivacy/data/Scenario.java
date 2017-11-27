package jlcmoore.whatsprivacy.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import static java.lang.Integer.parseInt;

/**
 * Created by jared on 11/14/17.
 */

@Entity(tableName = "scenarios",
        primaryKeys = {"id", "domain"},
                foreignKeys = {
                    @ForeignKey(entity = Domain.class,
                                parentColumns = "id",
                                childColumns = "domain")})
public class Scenario {
    public final String description;
    public final boolean negative;
    public final int domain;
    public final int id;

    public Scenario(int domain, int id, boolean negative, String description) {
        this.description = description;
        this.negative = negative;
        this.id = id;
        this.domain = domain;
    }

    public Scenario(String[] row) {
        if (row.length != 4) {
            throw new IllegalArgumentException();
        }
        this.id = parseInt(row[1]);
        this.domain = parseInt(row[0]);
        this.negative = Boolean.parseBoolean(row[2]);
        this.description = row[3];
    }
}
