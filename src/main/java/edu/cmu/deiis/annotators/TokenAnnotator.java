package edu.cmu.deiis.annotators;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;

import java.io.StringReader;
import java.util.Iterator;

import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.Question;
import edu.cmu.deiis.types.Token;

import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.objectbank.TokenizerFactory;
import edu.stanford.nlp.process.PTBTokenizer.PTBTokenizerFactory;
import edu.stanford.nlp.process.Tokenizer;

/**
 * This class is for token annotation.
 * @author wwy
 */
public class TokenAnnotator extends JCasAnnotator_ImplBase {

  /* (non-Javadoc)
   * @see org.apache.uima.analysis_component.JCasAnnotator_ImplBase#process(org.apache.uima.jcas.JCas)
   */
  @Override
  public void process(JCas arg0) throws AnalysisEngineProcessException {
    String text = arg0.getDocumentText();
    TokenizerFactory<Word> factory = PTBTokenizerFactory.newTokenizerFactory();
    
    // iterate all the questions and retrieve the tokens
    FSIndex<?> qIndex = arg0.getAnnotationIndex(Question.type);
    Iterator<?> qIter = qIndex.iterator();
    while (qIter.hasNext()) {
      Question question = (Question) qIter.next();
      Tokenizer<Word> tokenizer = factory.getTokenizer(new StringReader(text.substring(
              question.getBegin(), question.getEnd() - 2)));
      while (tokenizer.hasNext()) {
        Token annotation = new Token(arg0);
        Word temp = tokenizer.next();
        annotation.setBegin(temp.beginPosition() + question.getBegin());
        annotation.setEnd(temp.endPosition() + question.getBegin());
        annotation.setCasProcessorId(this.getClass().getName());
        annotation.setConfidence(1);
        annotation.addToIndexes();
      }
    }

    // iterate all the answers and retrieve the tokens
    FSIndex<?> aIndex = arg0.getAnnotationIndex(Answer.type);
    Iterator<?> aIter = aIndex.iterator();
    while (aIter.hasNext()) {
      Answer answer = (Answer) aIter.next();
      Tokenizer<Word> tokenizer = factory.getTokenizer(new StringReader(text.substring(
              answer.getBegin(), answer.getEnd() - 2)));
      while (tokenizer.hasNext()) {
        Token annotation = new Token(arg0);
        Word temp = tokenizer.next();
        annotation.setBegin(temp.beginPosition() + answer.getBegin());
        annotation.setEnd(temp.endPosition() + answer.getBegin());
        annotation.setCasProcessorId(this.getClass().getName());
        annotation.setConfidence(1);
        annotation.addToIndexes();
      }
    }
  }
}