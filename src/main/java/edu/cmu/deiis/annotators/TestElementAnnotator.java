package edu.cmu.deiis.annotators;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.Question;

/**
 * This class is for the TestElementAnnotator required in hw2.
 * @author wwy
 */
public class TestElementAnnotator extends JCasAnnotator_ImplBase {
  
  /* (non-Javadoc)
   * @see org.apache.uima.analysis_component.JCasAnnotator_ImplBase#process(org.apache.uima.jcas.JCas)
   */
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    String docText = aJCas.getDocumentText();
    String[] lines = docText.split("\n");
    int index = 0;
    
    // read every line
    for (int countLine = 0; countLine < lines.length; countLine++) {
      String line = lines[countLine];
//      System.out.println(line);
      
      // process questions
      if (line.startsWith("Q ")) {
        Question ques = new Question(aJCas);
        ques.setBegin(index + 2); // skip "Q and space"
        ques.setEnd(index + line.length());
        ques.setCasProcessorId(this.getClass().getName());
        ques.addToIndexes();
      }
      
      // process answers
      if (line.startsWith("A ")) {
        Answer ans = new Answer(aJCas);
        ans.setBegin(index + 4); // skip "A, score and spaces"
        ans.setEnd(index + line.length());
        ans.addToIndexes();

        String label = line.substring(2, 3);
        if (label.equals("1")) {
          ans.setIsCorrect(true);
        } else {
          ans.setIsCorrect(false);
        }
      }
      index += line.length() + 1; // either one question or one answer in a line, so go to next line.
    }

  }

}
