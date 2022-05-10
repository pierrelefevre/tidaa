///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package uppgift5;
//
///**
// *
// * @author bfelt
// */
//import javax.swing.JOptionPane;
//
//public class Main {
//
//	public static void buildTree(BinarySearchTree<String> b){
//		b.add("H");
//		b.add("B");
//		b.add("N");
//		b.add("A");
//		b.add("E");
//		b.add("C");
//		b.add("F");
//		b.add("D");
//	}
//	public static void main(String[] args) {
//
//		while(true){
//			BinarySearchTree<String> bst= new BinarySearchTree<>();
//			buildTree(bst);
//			bst.printTree();
//                        System.out.println(bst);
//			System.out.println();
//			String s = JOptionPane.showInputDialog("Ta bort:");
//			if(s.equals("")) break;
//			String d = bst.delete(s);
//			System.out.println("Tar bort: "+d+" Nytt träd:");
//			bst.printTree();
//			System.out.println();
//
//		}
//
//	}
//
//}
