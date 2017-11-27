package jlcmoore.whatsprivacy.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by jared on 11/12/17.
 */

@Entity (tableName = "participants")
public class Participant {
    @PrimaryKey
    public int id;
    public final String name;
    public final String groupOne;
    public final String groupTwo;
    public final String groupThree;
    public final String groupFour;

    public Participant(int id, String name, String groupOne, String groupTwo, String groupThree,
                       String groupFour) {
        this.id = id;
        this.name = name;
        this.groupOne = groupOne;
        this.groupTwo = groupTwo;
        this.groupThree = groupThree;
        this.groupFour = groupFour;
    }

    public Participant(int id, String name, String... groups) {
        this.id = id;
        this.name = name;
        groupOne = groups[0];
        groupTwo = groups[1];
        groupThree = groups[2];
        groupFour = groups[3];
    }
}
