package jlcmoore.whatsprivacy.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jared on 11/12/17.
 *
 *
 *
 */

@Entity (tableName = "responses",
        primaryKeys = {"pid", "qid"},
        foreignKeys = {@ForeignKey(entity = Participant.class,
                                    parentColumns = "id",
                                    childColumns = "pid"),
                        @ForeignKey(entity = Question.class,
                                parentColumns = "id",
                                childColumns = "qid")})
public class Response {
    public final int pid;
    public final int qid;
    // TODO: modify based on number of possible responses?
    public final Set<Integer> groups;

    public Response(int qid, int pid, Set<Integer> groups) {
        this.qid = qid;
        this.pid = pid;
        this.groups = new HashSet<>(groups);
    }

    public static String getResponseGroups(Set<Integer> groups) {
        return Arrays.toString(groups.toArray());
    }

        /* TODO: Possibly do this if want individual row access
    public final boolean group1;
    public final boolean group2;
    public final boolean group3;
    public final boolean group4;

    public Response(int qid, int pid, Set<Integer> groups) {
        this(qid, pid, groups.contains(1), groups.contains(2), groups.contains(3), groups.contains(4));
    }

    public Response(int qid, int pid, boolean group1, boolean group2, boolean group3, boolean group4) {
        this.qid = qid;
        this.pid = pid;
        this.group1 = group1;
        this.group2 = group2;
        this.group3 = group3;
        this.group4 = group4;
    }
    */
}
