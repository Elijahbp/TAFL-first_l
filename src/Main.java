import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Main {


    public static void main(String[] args) {
        String[] leftPart =  {"S","S","Q","Q"};//"S","AD","F","F","Cb","AB","bBA","CB","Ab","bCD"};
        String[] rightPart = {"bQ","accb","cS","cB"};//"aaCFD","D","AFB","AB","bC","bBA","C","bA","eps"};



        int indType = 0;
        // Тип 0 =0; КЗ = 1; КС = 2; P левая = 3; P правая = 4;


        int sizeLP = 0;
        int indRP = 0;
        for (int i = 0; i < leftPart.length; i++) {
            if (leftPart[i].length() > sizeLP) {
                sizeLP = leftPart[i].length();
            }
        }
        //
        if (sizeLP == 1){
            // Проверка на КС, либо на P
            indType = 2;

            for (int i = 0; i < rightPart.length; i++) {
                boolean k = checkOnContentRule(leftPart[i],leftPart,rightPart);
                //Проверка на левое выравнивание
                if ((checkOnNonTerminal(rightPart[i].charAt(0)) && checkOnTerminal(rightPart[i].charAt(rightPart[i].length()-1))
                        && k )||(checkOnTerminalString(rightPart[i]))) {
                    indType = 3;
                }
                else {
                    indType = 2;
                    break;
                }
            }
            if (indType !=3 ){
                for (int i = 0; i < rightPart.length; i++) {
                    boolean k = checkOnContentRule(leftPart[i],leftPart,rightPart);
                    //Проверка на правое выравнивание
                    if ((checkOnNonTerminal( rightPart[i].charAt(rightPart[i].length()-1) ) && checkOnTerminal(rightPart[i].charAt(0))
                            && k) ||(checkOnTerminalString(rightPart[i]))) {
                        indType = 4;
                    }
                    else {
                        indType = 2;
                        break;
                    }
                }
            }


        }
        if (sizeLP>1){
            //Проверка на КЗ, либо 0
            for (int i = 0; i < leftPart.length; i++) {
                if (leftPart[i].length() <= rightPart[i].length()){
                    indType = 1;
                }else {
                    indType = 0;
                    break;
                }

            }
        }

        if (indType ==0 ) System.out.println("Грамматика Типа 0");
        if (indType ==1 )System.out.println("Контекстно-зависимая грамматика");
        if (indType ==2 )System.out.println("Контекстно-свободная грамматика");
        if (indType ==3 ) System.out.println("Регулярная грамматика с левым выравниванием");
        if (indType ==4 )System.out.println("Регулярная грамматика с правым выравниванием");


    }

    private static boolean checkOnTerminal(char c){
        return ((int)c >=97 && (int)c <=122);
    }
    private static boolean checkOnNonTerminal(char c){
        return ((int)c >=65 && (int)c <=90);
    }



    //Проверка правил с одной левой частью на содержание в правой части терминальной, и нетерминальной строки.
    private static boolean checkOnContentRule(String leftRule,String[] leftPart, String[] rightPart ){

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < leftPart.length ; i++) {
            if (leftPart[i] ==  leftRule ){
                strings.add(rightPart[i]);
            }
        }
        boolean termStr=false;
        for (String rule:strings) {
            termStr = checkOnTerminalString(rule);
        }
        return termStr;

    }
    private static boolean checkOnTerminalString(String rule){
        boolean termStr=false;
        for (String s1 : rule.split("")) {
            if (checkOnTerminal(s1.charAt(0))) {
                termStr =true;
            }else {
                termStr =false;
                break;
            }
        }
        return termStr;
    }

}
