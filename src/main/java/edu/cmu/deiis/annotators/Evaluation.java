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

/**
 *
 * This class is for calculating the precision @ N, from hw2.
 * @author wwy
 */
public class Evaluation extends JCasAnnotator_ImplBase {
  double sumPrecision = 0.0;
  int countQuestion = 0;

  @Override
  public void process(JCas arg0) throws AnalysisEngineProcessException {

    Question ques = null;
    List<Answer> ansList = new ArrayList<Answer>();

    // iterate questions
    FSIndex<?> questionIndex = arg0.getAnnotationIndex(Question.type);
    Iterator<?> questionIter = questionIndex.iterator();
    while (questionIter.hasNext()) {
      ques = (Question) questionIter.next();
      System.out.println(String.format("Question: %s", ques.getCoveredText()));

      // iterate answers
      FSIndex<?> answerIndex = arg0.getAnnotationIndex(Answer.type);
      Iterator<?> answerIter = answerIndex.iterator();
      Answer answer;
      while (answerIter.hasNext()) {
        answer = (Answer) answerIter.next();
        ansList.add(answer);
      }
    }

    // calculate precision@N
    double N = 0.0;
    for (Answer answer : ansList) {
      if (answer.getIsCorrect()) {
        N++;
      }
    }

    int countCorrect = 0;
    for (int i = 0; i < N; i++) {
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
      System.out.println(String.format("%s %.2f %s", correctInd, answer.getConfidence(),
              answer.getCoveredText()));
    }
    double precision = countCorrect / N;
    System.out.println(String.format("Precision at %.1f: %.2f ", N, precision));
    sumPrecision += precision;
    countQuestion +=1 ;
  }
  
  @Override
  public void collectionProcessComplete()
      throws AnalysisEngineProcessException {
    System.out.println(String.format("Average Precision: %.2f", sumPrecision/countQuestion));
  }
}
