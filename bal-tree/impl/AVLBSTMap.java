package impl;

/**
 * AVLBSTMap
 * 
 * A BST map using the AVL approach for maintaining a balanced
 * tree. This inherits most of the code for manipulating the
 * BST from AbstractRecursiveBSTMap and inherits the code for
 * verifying the AVL property from AbstractAVLBSTMap. 
 * This class's purpose is to house the code for fixing up an
 * AVL tree when out of balance. See documentation for 
 * AbstractAVLBSTMap.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * July 1, 2015
 * @param <K> The key type
 * @param <V> The value type
 * @param <N> A super type of nodes in whatever child class is refining this class
 */
public class AVLBSTMap<K extends Comparable<K>, V> extends
        AbstractAVLBSTMap<K, V> {

    
    /**
     * Class for real, "non-null" nodes, containing the code for
     * enforcing the AVL property.
     */
   private class AVLRealNode extends AbstractAVLRealNode {

       /**
        * Plain constructor.
        */
        public AVLRealNode(K key, V val,
                AVLNode<K,V> left,
                AVLNode<K,V> right) {
            super(key, val, left, right);
        }

        /**
         * Fix this subtree to conform to the constraints of
         * AVL trees. 
         * PRECONDITION: The subtrees rooted at the left and
         * right each satisfy the AVL constraints.
         * POSTCONDITION: This tree has been modified to
         * contain the same information but also to satisfy the
         * AVL constraints. The node on which this method is called,
         * currently the root of this subtree, might no longer be
         * the root; the root of the modified tree is returned.
         * @return The root of the tree like this one but
         * satisfying the constraints.
         */
       protected AVLNode<K,V> fixup() {
            AbstractAVLRealNode replace = this;
//          soft recompute	
            
            //only the single node 
            //check by height/balance
            AbstractAVLRealNode oldLeft, oldRight, oldLeftRight, oldRightLeft;
            // When there is a violation
            // Right rotate
            if(){
            oldLeft = (AbstractAVLRealNode)this.left;
            //this.left = null;
            oldLeft.right = this;
            return oldLeft;
            }
            else if(){
            // Left rotate
            oldRight = (AbstractAVLRealNode)this.right;
            //this.right = null;
            oldRight.left = this;
            return oldRight;
            }
            else if(){
            // Right-left
                oldRight = (AbstractAVLRealNode)this.right;
                oldRightLeft = (AbstractAVLRealNode) (oldRight.left);
                this.right = oldRightLeft;
                oldRight.left = oldRightLeft.left;
                oldRightLeft.right = oldRight;
                //should be the line now
                this.right = oldRightLeft.left;
                oldRightLeft.left = this;
                return oldRightLeft;
            
            }
            else if(){
            // Left-right
            oldLeft = (AbstractAVLRealNode)this.left;
            oldLeftRight = (AbstractAVLRealNode) (oldLeft.right);
            this.left = oldLeftRight;
            oldLeft.right = oldLeftRight.left;
            oldLeftRight.left = oldLeft;
            //should be the line now
            this.left = oldLeftRight.right;
            oldLeftRight.right = this;
            return oldLeftRight;
            }	
            
            
            // left left
            //
            return replace;
        }
        
    }
    
   /**
    * Factory method for making new real nodes, used by the
    * code in the parent class which does not have direct access
    * to the class AVLRealNode defined here.
    */
    protected AVLNode<K,V> realNodeFactory(K key, V val,
            AVLNode<K, V> left,
            AVLNode<K, V> right) {
        return new AVLRealNode(key, val, left, right);
    }

    
}
