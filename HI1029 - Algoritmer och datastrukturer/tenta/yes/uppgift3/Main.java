/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uppgift3;

/**
 * @author bfelt
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HashTableBucket<String, String> table = new HashTableBucket<>(40);
        String[] s = new String[30];
        for (int i = 0; i < 30; i++) {
            s[i] = "s" + i;
        }
        for (int i = 0; i < 30; i++) {
            System.out.println("PUT " + table.put(s[i], s[i]));
        }
        for (int i = 0; i < 30; i++) {
            System.out.println(table.get(s[i]));
        }
    }

}
