package com.example.test.util;

import java.util.*;

/**
 * @author mpf
 * @version 1.0
 * @date 2020/2/15 20:16
 */
public class CalculateUtil {
    public static final boolean validate(String str){
        Stack<Character> sc=new Stack<Character>();
        char[] c=str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i]=='('||c[i]=='['||c[i]=='{') {
                sc.push(c[i]);
            }
            else if (c[i]==')') {
                if (sc.peek()=='(') {
                    sc.pop();
                }
            }else if (c[i]==']') {
                if (sc.peek()=='[') {
                    sc.pop();
                }
            }else if (c[i]=='}') {
                if (sc.peek()=='{') {
                    sc.pop();
                }
            }
        }
        if (sc.empty()) {
            return true;
        }else {
            return false;
        }
    }
}
