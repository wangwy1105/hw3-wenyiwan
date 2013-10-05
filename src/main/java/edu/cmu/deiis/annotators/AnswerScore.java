package edu.cmu.deiis.annotators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.Question;
import edu.cmu.deiis.types.Token;

/**
 * This class is for assigning scores to each answer.
 * @author wwy
 */
public class AnswerScore extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas arg0) throws AnalysisEngineProcessException {
    String text = arg0.getDocumentText();

    FSIndex<?> questionIndex = arg0.getAnnotationIndex(Question.type);
    Iterator<?> questionIter = questionIndex.iterator();

    // iterate all the questions
    while (questionIter.hasNext()) {
      Question question = (Question) questionIter.next();
      List<String> qTokenList = new ArrayList<String>();
      FSIndex<?> qTokenIndex = arg0.getAnnotationIndex(Token.type);
      Iterator<?> qTokenIter = qTokenIndex.iterator();
      Token qToken = (Token) qTokenIter.next();

      // get list of tokens in a question
      while (qToken.getEnd() < question.getEnd()) {
        String tmp = text.substring(qToken.getBegin(), qToken.getEnd());
        qTokenList.add(tmp);
        qToken = (Token) qTokenIter.next();
      }

      // use a hash map to store the tokens together with their # of appearance in a Q or A
      Map<String, Integer> qTokenCount = token2count(qTokenList);

      FSIndex<?> answerIndex = arg0.getAnnotationIndex(Answer.type);
      Iterator<?> answerIter = answerIndex.iterator();

      Answer answer;
      while (answerIter.hasNext()) {
        //        System.out.println("answer #" + countAnswer + " :");
        answer = (Answer) answerIter.next();
        List<String> aTokenList = new ArrayList<String>();
        FSIndex<?> aTokenIdx = arg0.getAnnotationIndex(Token.type);
        Iterator<?> aTokenIter = aTokenIdx.iterator();

        // get list of tokens in an answer
        while (aTokenIter.hasNext()) {
          Token aToken = (Token) aTokenIter.next();
          if (aToken.getBegin() >= answer.getBegin() && aToken.getBegin() < answer.getEnd()) {
            String tmp = text.substring(aToken.getBegin(), aToken.getEnd());
            aTokenList.add(tmp);
          }
        }

        // store the tokens together with their # of appearance
        Map<String, Integer> aTokenCount = token2count(aTokenList);

        // calculate and set a score
        double score = getScore(qTokenCount, aTokenCount);
//        System.out.println(aTokenList);
//        System.out.println("score: " + score);
        answer.setConfidence(score);
        answer.setCasProcessorId(this.getClass().getName());
      }
    }
  }

  /**
   * This method generates a score based on the overlapping between tokens in answer and tokens in question
   * @param question
   * @param answer
   * @return
   */
  private double getScore(Map<String, Integer> ques, Map<String, Integer> ans) {
    double score = 0.0;
    for (Entry<String, Integer> tok : ques.entrySet()) {
      if (ans.containsKey(tok.getKey())) {
        score += 1.0;
      }
    }
    double length = 0;
    for (Entry<String, Integer> tok : ques.entrySet()) {
      length += tok.getValue();
    }
    return score / length;
  }

  /**
   * This method counts the # of appearance of tokens in a Q or A
   * @param list of strings
   * @return
   */
  private Map<String, Integer> token2count(List<String> list) {
    Map<String, Integer> questionCounts = new HashMap<String, Integer>();
    for (String token : list) {
      if (questionCounts.containsKey(token)) {
        int tmp = questionCounts.get(token);
        tmp++;
        questionCounts.put(token, tmp);
      } else {
        questionCounts.put(token, 1);
      }
    }
    return questionCounts;
  }
}
