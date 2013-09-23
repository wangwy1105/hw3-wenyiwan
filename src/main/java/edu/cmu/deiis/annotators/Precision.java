package edu.cmu.deiis.annotators;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.Question;

public class Precision extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas arg0) throws AnalysisEngineProcessException {
    Question ques = null;
    List<Answer> ansList = new ArrayList<Answer>();

    // iterate questions
    FSIndex<?> questionIndex = arg0.getAnnotationIndex(Question.type);
    Iterator<?> questionIter = questionIndex.iterator();
    while (questionIter.hasNext()) {
      ques = (Question) questionIter.next();
      System.out.println(String.format("Q: %s", ques.getCoveredText()));

      // iterate answers
      FSIndex<?> answerIndex = arg0.getAnnotationIndex(Answer.type);
      Iterator<?> answerIter = answerIndex.iterator();
      Answer answer;
      while (answerIter.hasNext()) {
        answer = (Answer) answerIter.next();
        ansList.add(answer);
      }
    }

    // calculate precision
    double totalCorrect = 0.0;
    for (Answer answer : ansList) {
      if (answer.getIsCorrect()) {
        totalCorrect++;
      }
    }

    int countCorrect = 0;
    for (int i = 0; i < totalCorrect; i++) {
      if (ansList.get(i).getIsCorrect()) {
        countCorrect++;
      }
    }

    // sort scores of answers
    Collections.sort(ansList, new ScoreComparator());
    for (Answer answer : ansList) {
      String correctInd = null;
      if (answer.getIsCorrect() == true) {
        correctInd = "+";
      } else {
        correctInd = "-";
      }
      System.out.println(String.format("A: %s %.2f %s", correctInd, answer.getConfidence(),
              answer.getCoveredText()));
    }
    double precision = countCorrect / totalCorrect;
    System.out.println(String.format("Precision at %d: %.2f ", (int) totalCorrect, precision));
  }
}
