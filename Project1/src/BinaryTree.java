import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by nxc141130 on 9/29/16.
 */
// Description: Have a linked list data structure
// we only need root node ( like a list needs head or tail)
// one reference to a node the root node.
// Each node needs
////          *
//           /  \
//           *   *
//          /\   /\
//    1) A value
//    2) A parent
//    3) A left child
//    4) A right child


public class BinaryTree<E> {

    //private =null;
    private TreeNode<E> root;

    public TreeNode <E>  getroot(){
        return this.root;
    }



    BinaryTree(){
            TreeNode<Integer> root= new TreeNode<>(1,null);
//        TreeNode<Integer> node20=new TreeNode<Integer>(20,null);
//        TreeNode<Integer> node10=new TreeNode<Integer>(10,null);
//        TreeNode<Integer> node30=new TreeNode<Integer>(30,null);
//        TreeNode<Integer> node60=new TreeNode<Integer>(60,null);
//        TreeNode<Integer> node50=new TreeNode<Integer>(50,null);
//        TreeNode<Integer> node70=new TreeNode<Integer>(70,null);

        root.addRightChild(2);

           root.addLeftChild(10);


    }

    public static void main(String[] args) {

        BinaryTree b= new BinaryTree();



        System.out.println(b.getroot().getLeftChild());

    }








    // Preorder Traversal
    // Visit Yourself
    // Recursively
    // Then Visit all your left subtree
    // Then Visit all your subtree
    private void preOrder(TreeNode<E> node){
        if (node!=null){
            //node.visit();
            preOrder(node.getLeftChild());
            preOrder(node.getRightChild());
        }
    }
    public void preOrder(){
        this.preOrder(root);
    }
    //POSTORDER TRAVERSAl
    // Visit all your left subtree
    // Visit all your right subtree
    // Visit youtself

    // postOrder and preOrder are DFS


    // Level order is also BFS
    public void levelOrder(){
        Queue <TreeNode<E>> queue=new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            TreeNode<E> curr= queue.remove();
            if (curr!=null){
                curr.visit();
                if (curr.getLeftChild()!=null){
                    queue.add(curr.getLeftChild());
                }
                if (curr.getRightChild()!=null){
                    queue.add(curr.getRightChild());
                }

            }

        }
    }



}
class TreeNode<E>{
    private E value;
    private TreeNode<E> parent;
    private TreeNode<E> left;
    private TreeNode<E> right;
    private boolean visit=false;
    public TreeNode(E val,TreeNode<E> par){
        this.value=val;
        this.parent=par;
        this.left=null;
        this.right=null;
    }

    public TreeNode<E> getLeftChild() {
        return this.left;
    }
    public TreeNode<E> getRightChild(){
        return this.right;
    }

    public TreeNode<E> addLeftChild(E val){
        // this itself is the parent of iteself
        this.left=new TreeNode<E>(val,this);
        return this.left;
    }

    public TreeNode<E> addRightChild(E val){
        // this itself is the parent of iteself
        this.right=new TreeNode<E>(val,this);
        return this.right;
    }
    public void visit(){
        this.visit=true;
    }

}


