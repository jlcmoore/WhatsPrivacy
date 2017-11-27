package jlcmoore.whatsprivacy.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase ;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.RawRes;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;
import jlcmoore.whatsprivacy.R;

import static java.lang.Integer.parseInt;

/**
 * Created by jared on 11/12/17.
 */

@Database(entities = {Participant.class, Response.class, Question.class, Domain.class, Scenario.class},
        version = 1)
@TypeConverters({AppDatabase.Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public static final String NAME = "surveydb";
    private static AppDatabase INSTANCE = null;

    public abstract AppDao appDao();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            // TODO: only here for development
            context.deleteDatabase(NAME);
            INSTANCE =
                    Room.databaseBuilder(context, AppDatabase.class, NAME)
                            .allowMainThreadQueries()
                            .build();
            // populate it with sample data from csv
            if (INSTANCE.appDao().loadDomains().length < 1) {
                INSTANCE.appDao().insertDomains(getDomains(context));
            }
            if (INSTANCE.appDao().loadScenarios().length < 1) {
                INSTANCE.appDao().insertScenarios(getScenarios(context));
            }
            if (INSTANCE.appDao().loadQuestions().length < 1) {
                INSTANCE.appDao().insertQuestions(getQuestions(context));
            }
        }
        return INSTANCE;
    }

    private static List<String[]> getRows(Context context, @RawRes int id) {
        List<String[]> results;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(context.getResources().openRawResource(id));
            CSVReader reader = new CSVReader(inputStreamReader);
            results = reader.readAll();
            // Assume the first line is the schema
            results.remove(0);
        } catch (IOException e) {
            throw new Resources.NotFoundException();
        }
        return results;
    }

    private static List<Question> getQuestions(Context context) {
        List<String[]> rows = getRows(context, R.raw.sample_questions);
        List<Question> result = new ArrayList<>(rows.size());
        for (String[] row : rows) {
            result.add(new Question(row));
        }
        return result;
    }

    private static List<Domain> getDomains(Context context) {
        List<String[]> rows = getRows(context, R.raw.sample_domains);
        List<Domain> result = new ArrayList<>(rows.size());
        for (String[] row : rows) {
            result.add(new Domain(row));
        }
        return result;
    }

    private static List<Scenario> getScenarios(Context context) {
        List<String[]> rows = getRows(context, R.raw.sample_scenarios);
        List<Scenario> result = new ArrayList<>(rows.size());
        for (String[] row : rows) {
            result.add(new Scenario(row));
        }
        return result;
    }

    public static class Converters {
        @TypeConverter
        public Set<Integer> fromGroupString(String value) {
            if (value == null) {
                return null;
            }
            Set<Integer> result = new HashSet<>();
            value = value.substring(1, value.length() - 1);
            // TODO: check if this splits appropriately
            String[] numbers = value.split(",\\W*");
            for (String number : numbers) {
                if (!number.isEmpty()) {
                    result.add(parseInt(number));
                }
            }
            return result;
        }

        @TypeConverter
        public String groupsToString(Set<Integer> groups) {
            if (groups == null) {
                return null;
            } else {
                return Response.getResponseGroups(groups);
            }
        }
    }
}
