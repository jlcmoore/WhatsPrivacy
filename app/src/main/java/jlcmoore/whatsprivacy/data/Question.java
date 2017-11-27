package jlcmoore.whatsprivacy.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static java.lang.Integer.parseInt;

/**
 * Created by jared on 11/12/17.
 */

@Entity (tableName = "questions",
        foreignKeys = {@ForeignKey(entity = Domain.class,
                                    parentColumns = "id",
                                    childColumns = "domain")})
public class Question {
    @PrimaryKey
    public final int id;
    public final String question;
    public final int domain;
    public final int scenario;
    public final int expectedGroup;

    public Question(String question, int domain, int scenario, int expectedGroup, int id) {
        this.question = question;
        this.domain = domain;
        this.scenario = scenario;
        this.expectedGroup = expectedGroup;
        this.id = id;
    }

    public Question(String[] row) {
        if (row.length != 5) {
            throw new IllegalArgumentException();
        }
        id = parseInt(row[0]);
        domain = parseInt(row[1]);
        scenario = parseInt(row[2]);
        expectedGroup = parseInt(row[3]);
        question = row[4];
    }
}
