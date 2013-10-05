package edu.cmu.deiis.annotators;

import java.util.Comparator;

import edu.cmu.deiis.types.Answer;

/**
 * Score comparator definition
 * @author wwy
 */
public class ScoreComparator implements Comparator<Answer> {
  public int compare(Answer ans1, Answer ans2) {
    if (ans1.getConfidence() > ans2.getConfidence()) {
      return -1;
    } else {
      return 1;
    }
  }
}