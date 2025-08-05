import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

 interface Derivatives { //DERIVATIVES INTERFACE
   String takeDerivative();
   }

public class DerivativeApp {

    static ActionListener shared;
    static ActionListener correct;
    static ActionListener incorrect;
    static ActionListener answerChecker;
    static String currentAnswer = ""; 
    static JButton lastClickedButton;

    
    
    

public static void main(String[] args) {
//----------------------------------UI---------------------------------------------------------------
   JFrame frame = new JFrame();
   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   frame.setSize(1080, 1080); 
   frame.setVisible(true);

   frame.getContentPane().setBackground(Color.PINK);
   frame.setLayout(null);
   JButton b1 = new JButton("Power Rule");
        b1.setBounds(400, 120, 800, 100); // x, y, width, height
        b1.setName("PowRule");
        frame.add(b1);

        JButton b2 = new JButton("Log Derivatives");
        b2.setName("LogRule");
        b2.setBounds(400, 240, 800, 100);
        frame.add(b2);

        JButton b3 = new JButton("Exponential Derivatives");
        b3.setName("ExpRule");
        b3.setBounds(400, 360, 800, 100);
        frame.add(b3);
        
        JButton b4 = new JButton("Chain Rule v1");
        b4.setName("Chain Rule v1");
        b4.setBounds(400, 480, 800, 100);
        frame.add(b4);
        
        JButton b5 = new JButton("Chain rule");
        b5.setName("Chain Rule");
        b5.setBounds(400, 600, 800, 100);
        frame.add(b5);
        
        JButton[] buttons = {b1, b2, b3, b4, b5};
        for (JButton button : buttons) {
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        }
                
        
        b1.setActionCommand("PowRule");


        //------------------------------------end main menu page--------------------------------
        
        
        //---------------------------------------start page2------------------------------------
        
        
        JFrame frame2 = new JFrame();  
                       frame2.setLayout(null);
                       frame2.setSize(1080, 1080);
                       frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                       frame2.getContentPane().setBackground(Color.CYAN);//frame customization
                       
                       JLabel prompt = new JLabel(""); //PROMPT LABEL FOR QUESTIONS
                       prompt.setBounds(700, 100, 400, 200); //x,y,width,height
                       prompt.setText("This is for the questions");
                       prompt.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
                       frame2.add(prompt);
                       prompt.setVisible(true);
                       
                       JButton opt1 = new JButton(""); //clickable option 1
                       JButton opt2 = new JButton(""); //option 2
                       opt1.setBounds(400, 300, 400, 200);
                       opt1.setVisible(true);
                       frame2.add(opt1);
                       opt2.setBounds(900, 300, 400, 200);
                       opt2.setVisible(true);
                       frame2.add(opt2);
                       
                       JButton homeButton = new JButton();//Back button
                       homeButton.setBounds(700, 600, 200, 100);
                       homeButton.setText("Click me to go back!");
                       homeButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
                       frame2.add(homeButton);
                       
                       JButton nextQ = new JButton("");//NEXT QUESTION BUTTON
                       nextQ.setBounds(900, 600, 200, 100);
                       nextQ.setText("Next question");
                       nextQ.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
                       frame2.add(nextQ);

                       

        
        
 //-----------------------------------------end page2 layout, action listener-----------------------
 
                     nextQ.addActionListener(new ActionListener() { //NEXT BUTTON ACTIONLISTENER, refresh questions
    public void actionPerformed(ActionEvent e) {
        if (lastClickedButton != null) {
            ActionEvent fakeEvent = new ActionEvent(
                lastClickedButton, 
                ActionEvent.ACTION_PERFORMED, 
                lastClickedButton.getActionCommand()
            );
            shared.actionPerformed(fakeEvent);
        }
    }
});

        
         shared = new ActionListener() { //----------------ACTIONLISTENER FOR QUESTIONS *************************************
            public void actionPerformed(ActionEvent e) {    

                       frame2.setVisible(true);//activate page                         
                       String buttonName = ((JButton) e.getSource()).getName(); //what type of derivative
                       lastClickedButton = (JButton) e.getSource(); 
                       
                       System.out.println(buttonName);
                       
                       if(buttonName.equals("PowRule")) {
                           PowRule p = new PowRule(random(), random());
                           currentAnswer = p.takeDerivative();
                           String trickAnswer = currentAnswer.substring(0, currentAnswer.length()-1) + (p.toString().substring(p.toString().length()-1));
                           
                           updateUI(prompt, opt1, opt2, p.toString(), currentAnswer, trickAnswer); 
                       
                       
                       }//powrule case
                       else if(buttonName.equals("LogRule")) {
                          LogRule p = null;
                           if(random() % 2 == 0){ //randomize ln or log of a
                               p = new LogRule("e");
                           } else {
                               p = new LogRule(String.valueOf(random()));
                           }
                           
                           currentAnswer = p.takeDerivative();
                           String trickAnswer = "1/ln" + (p.toString().substring(p.toString().length()-1));
                           updateUI(prompt, opt1, opt2, p.toString(), currentAnswer, trickAnswer);
                       
                       } //LOG DERIVSTIVES CASE
                       else if(buttonName.equals("ExpRule")) {
                           ExpRule p = null;
                           if(random() % 2 == 0){ //randomize e^x or a^x
                               p = new ExpRule("e");
                           } else {
                               p = new ExpRule(String.valueOf(random()));
                           }
                           
                           currentAnswer = p.takeDerivative();
                           String trickAnswer = "x*" + p.toString().substring(0, p.toString().length());
                           updateUI(prompt, opt1, opt2, p.toString(), currentAnswer, trickAnswer);
                           
                       
                       }else if(buttonName.equals("Chain Rule")) {
                       
                           PowRule p = new PowRule(random(), random());
                           LogRule l = new LogRule(String.valueOf(random()));
                           
                           if(random() % 2 == 0) { //decide which is inner vs outer function
                              String form = chainForm(p.toString(), l.toString());//make chain form
                              currentAnswer = chainRule(p.takeDerivative(), l.toString(), l.takeDerivative());
                              String trickAnswer = form + "* " + l.takeDerivative();
                              updateUI(prompt, opt1, opt2, form, currentAnswer, trickAnswer);
                           }else{
                              String form = chainForm(l.toString(), p.toString());
                              currentAnswer = chainRule(l.takeDerivative(), p.toString(), p.takeDerivative());
                              String trickAnswer = form + "* " + p.takeDerivative();
                              updateUI(prompt, opt1, opt2, form, currentAnswer, trickAnswer);
                           }
                         
                            
                       }else if(buttonName.equals("Chain Rule v1")){
                          
                           PowRule p = new PowRule(random(), random());
                           TrigRule l = new TrigRule((random() % 2));
                          
                           if(random() % 2 == 0) { //decide which is inner vs outer function
                              String form = chainForm(p.toString(), l.toString());//make chain form
                              currentAnswer = chainRule(p.takeDerivative(), l.toString(), l.takeDerivative());
                              String trickAnswer = form + "* " + l.takeDerivative();
                              updateUI(prompt, opt1, opt2, form, currentAnswer, trickAnswer);
                           }else{
                              String form = chainForm(l.toString(), p.toString());
                              currentAnswer = chainRule(l.takeDerivative(), p.toString(), p.takeDerivative());
                              String trickAnswer = form + "* " + p.takeDerivative();
                              updateUI(prompt, opt1, opt2, form, currentAnswer, trickAnswer);
                           }
                         
                           
                       
                       
                       }//end if elses
                       
                       

                    }
           }; //end actionlistener
                    
           
        ActionListener back = new ActionListener() { //------------------ACTIONLISTENER FOR BACK BUTTON
           public void actionPerformed(ActionEvent e) {    
                       frame2.setVisible(false);//deactivate page
                       frame.setVisible(true);                   
                    }
           }; //end actionlistener
           
           
       ActionListener delete = new ActionListener() { //-----------ACTIONLISTENER FOR DELETING COMPONENT
           public void actionPerformed(ActionEvent e) {    
                        JButton clicked = (JButton) e.getSource();
                        frame2.remove(clicked);     
                        frame2.revalidate();
                        frame2.repaint();                                          
                    }
           }; //end actionlistener   
           
       answerChecker = new ActionListener() { //--------ACTIONLISTENER FOR CHOICE BUTTONS
          public void actionPerformed(ActionEvent e) {
            JButton clicked = (JButton) e.getSource();
               if (clicked.getText().equals(currentAnswer)) {
                  clicked.setText("Correct!");
               } else {
                  clicked.setText("Incorrect!");
              }
            }
        };//end actionlistener
         
           
        b1.addActionListener(shared);
        b2.addActionListener(shared);
        b3.addActionListener(shared);
        b4.addActionListener(shared);
        b5.addActionListener(shared);
        //page 1 actions
        
        //page 2 below
        homeButton.addActionListener(back);
        opt1.addActionListener(answerChecker);
        opt2.addActionListener(answerChecker);
        
       
       
       
        
//-------------------------------------------------------------------------------UI--  
   System.out.println(new PowRule(2, 3)); //2x^3
   System.out.println((new PowRule(2, 3)).takeDerivative()); //2x^3
   System.out.println((new PowRule(2, 0)).takeDerivative()); //2
   System.out.println((new PowRule(2, 1)).takeDerivative()); //2x
   
   System.out.println(new ExpRule("2")); //2^x
   System.out.println((new ExpRule("2")).takeDerivative()); //2^x (ONLY STRINg)
   System.out.println((new ExpRule("e")).takeDerivative()); //e^x
   
   System.out.println(new LogRule("2"));//logbase2(x)
   System.out.println((new LogRule("2")).takeDerivative()); //logbase2(x) (ONLY STRINg) and of x only
   System.out.println((new LogRule("e")).takeDerivative()); //log e (x)
   
   }//END MAIN METHOD
   
   
//----------------------------chain, prod, other funcs---------------------------------------------------
 
   public static void updateUI(JLabel que, JButton one, JButton two, String question, String right, String wrong){ //RANDOMIZER TO SET WHERE CHOICES ARE
      
      que.setText(question); //set quesiton text
      
      if(random() % 2 == 0) { //randomize answer choices
        one.setText(right);
        two.setText(wrong);
      }else {
        one.setText(wrong);
        two.setText(right);
         }//else
         
         one.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
         two.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
   }//END METHOD UPDATEUI

   public static int random() {
        return (int)(Math.random() * 9) + 1; // 1 to 9 inclusive
    }
    
    public static String chainRule(String fPrime, String gOfX, String gPrime){ //TAKES CHAIN RULE OF 2 FUNCS
         String result = "";
         int index = 0;
         for(int i = 0; i<fPrime.length(); i++) { //find x in f(x)'
            
            if(fPrime.charAt(i) == 'x') {
               index = i;
            }//end if
         }//for loop
         
         result = fPrime.substring(0, index) + "( " + gOfX + " )" + fPrime.substring(index+1); //makes f'(g(x))
         
         result = result + " * " + gPrime;
         System.out.print(result);
          return result;
          
      
    }
    
    public static String chainForm(String fx, String gx){ //RETURNS STRING OF CHAIN RULE FORM
    //OF 2 FUNCS         
          int index = fx.indexOf('x');
         if (index == -1) return fx; // No 'x' found, return as-is

         return fx.substring(0, index) + "( " + gx + " )" + fx.substring(index + 1);
       
    }

   
      
}//END MAIN CLASS

//------------------------classes-----------------------------------------


   class PowRule implements Derivatives { //POWRULE, assign CONSTANT then EXP. ax^b
   int exponent;
   int constant;
   
   public PowRule(int c, int e) {
      exponent = e;
      constant = c;
   }
   
   public String takeDerivative() {
      if(exponent>1) { //ax^b
         return (constant*exponent) + "x^" + (exponent-1);
      }else if(exponent == 1) { //ax
         return constant + "";
      }else { //a
         return "0";
      }
   }//end Deriv()
   
   public String toString(){
      return constant + "x^" + exponent;
   
   }//end ToSTring

  }//end powrule class
  
  
  class LogRule implements Derivatives{ //INPUTS STRIng only - loga( x) ONLY of x
   String exponent;
   
   public LogRule(String e){
      exponent = e;
   }
   
   public String takeDerivative(){
      if(!exponent.equals("e")){
         return "1/(x ln " + exponent;
         }else{
            return "1/x";
      }
   }//end Deriv()
   
   public String toString(){
      return "log" + exponent + "(x)";
   }//end toString
  
  }//end LogRule
  
  
  class ExpRule implements Derivatives{ //INPUTS STRIng only - a^x
   String exponent;
   
   public ExpRule(String e){
      exponent = e;
   }
   
   public String takeDerivative(){
      if(!exponent.equals("e")){
         return exponent + "^x *ln(" + exponent + ")";
         }else{
            return "e^x";
      }
   }//end Deriv()
   
   public String toString(){
      return exponent + "^x";
   }//end toString()
  
  }//end ExpRule
  
  
  class TrigRule implements Derivatives{//SIN AND COS
  String func;//name of func
  int type; //0 for sin, 1 for cos
  
      public TrigRule(int e){
      type = e;   
         if(e == 0){
            func = "sin(x)";
         } else {
            func = "cos(x)";
         }
      
      }//end constructor
      
      public String takeDerivative(){
         if(type == 0){ //FOR SDERIVATIVE OF SIN
            return "cos(x)";
         }
         else{//DERIVATIVE OF COS
            return "-sin(x)";
         }
      }//endDeriv()
      
      public String toString(){
         return func;
      }
  
  
  }//end TrigRule

