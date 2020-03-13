package com.robestone.robot.keyboard;

import java.awt.Robot;
import java.awt.event.KeyEvent;
//import java.lang.reflect.Field;

import javax.swing.KeyStroke;

public class KeyEventUtilities {

    public static void main(String[] args) throws Exception {
        Robot r = new Robot();
        type("h3llo w0rld", r);
        type("H3ll0 w0rlD", r);
    }
    /**
     * Very simple implementation - doesn't work for any symbols, just
     * numbers and letters
     */
    public static void type(String s, Robot r) throws Exception {
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            char u = Character.toUpperCase(c);
            int code = KeyStroke.getKeyStroke(u, 0).getKeyCode();
            boolean shift = Character.isUpperCase(c);
            if (shift) {
                r.keyPress(KeyEvent.VK_SHIFT);
            }
            r.keyPress(code);
            Thread.sleep(10); // @todo fix this...
            r.keyRelease(code);
            if (shift) {
                r.keyRelease(KeyEvent.VK_SHIFT);
            }
        }
    }
    /*
    private static void dump(char c) {
        dump(c, KeyStroke.getKeyStroke(new Character(c), 0));
        dump(c, KeyStroke.getKeyStroke(String.valueOf(c)));
        dump(c, KeyStroke.getKeyStroke(c));
        dump(c, KeyStroke.getKeyStroke((int) c, 0));
        dump(c, KeyStroke.getKeyStroke((int) c, KeyEvent.VK_SHIFT & KeyEvent.VK_CONTROL));
        logger.info("-----");
    }
    private static void dump(char c, KeyStroke ks) {
        if (ks == null) {
            logger.info(c + ": null");   
        } else {
            logger.info(c + ": " + ks.getKeyChar() + ", " + ks.getKeyCode() + ", " + ks.getKeyEventType() + " -- " + ks);
        }
    }
    
    private static void init() {
        Field[] fields = KeyEvent.class.getDeclaredFields();
        for (Field f: fields) {
            String name = f.getName();
            if (name.startsWith("VK_")) {
                String sub = name.substring(3);
                if (sub.length() == 1) {
                    char c = sub.charAt(0);
                    init(c, f);
                }
            }
        }
    }
    private static void init(char c, Field f){
        try {
            logger.info(f);
            logger.info(c);
            int i = f.getInt(null);
            logger.info(i);
            logger.info("-----");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    */
}
