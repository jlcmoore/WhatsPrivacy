package jlcmoore.whatsprivacy.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by jared on 11/12/17.
 */

@Dao
public interface AppDao {
    @Insert
    void insertParticipant(Participant participant);

    @Insert
    void insertQuestions(List<Question> questions);

    @Insert
    void insertResponses(List<Response> responses);

    @Insert
    void insertDomains(List<Domain> categories);

    @Insert
    void insertScenarios(List<Scenario> scenarios);

    @Query("SELECT * FROM responses r, participants p"
            + " WHERE p.id = :pid AND r.pid = p.id")
    Response[] loadParticipantResponses(int pid);

    @Query("SELECT * FROM questions")
    Question[] loadQuestions();

    @Query("SELECT * FROM domains")
    Domain[] loadDomains();

    @Query("SELECT * FROM scenarios")
    Scenario[] loadScenarios();

    @Query("SELECT * FROM participants")
    Participant[] loadParticipants();

    @Query("SELECT max(id) FROM participants")
    int getMaxParticipantID();
}
