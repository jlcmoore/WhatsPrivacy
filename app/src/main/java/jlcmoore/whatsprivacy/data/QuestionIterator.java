package jlcmoore.whatsprivacy.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jared on 11/12/17.
 */

public class QuestionIterator{
    private Map<Integer, Map<Integer,List<Question>>> questions;
    private Map<Integer, List<Scenario>> domainToScenarios;
    private Domain[] domainOrder;
    private int currentQuestionIndex;
    private int currentScenarioIndex;
    private int currentDomainIndex;

    public QuestionIterator(Question[] questions, Scenario[] scenarios, Domain[] domains) {
        currentScenarioIndex = 0;
        currentDomainIndex = 0;
        currentQuestionIndex = 0;
        // not randomizing domain order
        // TODO: verify that domains corresponds to the domains noted in questions?
        domainOrder = domains;
        domainToScenarios = addDomainsToScenarios(scenarios);
        shuffleListInsideMap(this.domainToScenarios);
        this.questions = setUpQuestions(questions);
        for (int domain : this.questions.keySet()) {
            shuffleListInsideMap(this.questions.get(domain));
        }
    }

    public String getCurrentScenarioDescription() {
        return getCurrentScenarioList().get(currentScenarioIndex).description;
    }

    public String getCurrentDomainDescription() {
        return domainOrder[currentDomainIndex].name;
    }

    public Question next() {
        List<Question> currentQuestions = questions.get(currentDomainIndex).get(currentScenarioIndex);
        Question result = currentQuestions.get(currentQuestionIndex);
        if (hasNextQuestionInScenario()) {
            currentQuestionIndex++;
        } else if (hasNextScenarioInDomain()){
            currentScenarioIndex++;
            currentQuestionIndex = 0;
        } if (hasNextDomain()) {
            currentDomainIndex++;
            currentScenarioIndex = 0;
            currentQuestionIndex = 0;
        }
        return result;
    }

    public boolean hasNext() {
        return hasNextQuestionInScenario() || hasNextScenarioInDomain() || hasNextDomain();
    }

    private boolean hasNextDomain() {
        return  currentDomainIndex + 1 < domainOrder.length;
    }

    private boolean hasNextScenarioInDomain() {
        return currentScenarioIndex + 1 < getCurrentScenarioList().size();
    }

    private boolean hasNextQuestionInScenario() {
        return currentQuestionIndex + 1 < questions.get(currentDomainIndex).get(currentScenarioIndex).size();
    }

    private List<Scenario> getCurrentScenarioList() {
        return domainToScenarios.get(currentDomainIndex);
    }

    private static Map<Integer, List<Scenario>> addDomainsToScenarios(Scenario[] scenarios) {
        Map<Integer, List<Scenario>> result = new HashMap<>();
        for (Scenario scenario : scenarios) {
            if (!result.containsKey(scenario.domain)) {
                result.put(scenario.domain, new ArrayList<Scenario>());
            }
            result.get(scenario.domain).add(scenario);
        }
        return result;
    }

    private static Map<Integer, Map<Integer,List<Question>>> setUpQuestions(Question[] questions) {
        Map<Integer, Map<Integer,List<Question>>> result = new HashMap<>();
        // Determine the group types
        for (Question q : questions) {
            if (!result.containsKey(q.domain)) {
                result.put(q.domain, new HashMap<Integer, List<Question>>());
            }
            Map<Integer, List<Question>> scenarioMap = result.get(q.domain);
            if (!scenarioMap.containsKey(q.scenario)) {
                scenarioMap.put(q.scenario, new ArrayList<Question>());
            }
            scenarioMap.get(q.scenario).add(q);
        }
        return result;
    }

    private static <T> void shuffleListInsideMap(Map<Integer, List<T>> list) {
        for (int item: list.keySet()) {
            Collections.shuffle(list.get(item));
        }
    }
}
