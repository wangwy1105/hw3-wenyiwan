

/* First created by JCasGen Wed Sep 11 13:44:28 EDT 2013 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



import org.apache.uima.jcas.cas.FSArray;


/** 
 * Updated by JCasGen Fri Oct 04 21:18:04 EDT 2013
 * XML source: /Users/Apple/Documents/workspace_Java/hw3-wenyiwan/src/main/resources/hw3-wenyiwan-aae.xml
 * @generated */
public class Question extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Question.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Question() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Question(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Question(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Question(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
  //*--------------*
  //* Feature: TokenList

  /** getter for TokenList - gets 
   * @generated */
  public FSArray getTokenList() {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_TokenList == null)
      jcasType.jcas.throwFeatMissing("TokenList", "edu.cmu.deiis.types.Question");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Question_Type)jcasType).casFeatCode_TokenList)));}
    
  /** setter for TokenList - sets  
   * @generated */
  public void setTokenList(FSArray v) {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_TokenList == null)
      jcasType.jcas.throwFeatMissing("TokenList", "edu.cmu.deiis.types.Question");
    jcasType.ll_cas.ll_setRefValue(addr, ((Question_Type)jcasType).casFeatCode_TokenList, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for TokenList - gets an indexed value - 
   * @generated */
  public Annotation getTokenList(int i) {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_TokenList == null)
      jcasType.jcas.throwFeatMissing("TokenList", "edu.cmu.deiis.types.Question");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Question_Type)jcasType).casFeatCode_TokenList), i);
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Question_Type)jcasType).casFeatCode_TokenList), i)));}

  /** indexed setter for TokenList - sets an indexed value - 
   * @generated */
  public void setTokenList(int i, Annotation v) { 
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_TokenList == null)
      jcasType.jcas.throwFeatMissing("TokenList", "edu.cmu.deiis.types.Question");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Question_Type)jcasType).casFeatCode_TokenList), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Question_Type)jcasType).casFeatCode_TokenList), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    