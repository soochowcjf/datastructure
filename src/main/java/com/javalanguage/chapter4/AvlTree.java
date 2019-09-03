package com.javalanguage.chapter4;


import org.junit.Assert;

/**
 * @author cjf on 2019/9/2 11:09
 */
public class AvlTree<AnyType extends Comparable<? super AnyType>> {

    private static final int ALLOWED_IMBALANCE = 1;

    private AvlNode<AnyType> root;

    public AvlTree() {
        root = null;
    }

    // Test program
    public static void main(String[] args) {
        AvlTree<Integer> t = new AvlTree<>();
        final int SMALL = 40;
        final int NUMS = 1000000;  // must be even
        final int GAP = 37;

        System.out.println("Checking... (no more output means success)");

        for (int i = GAP; i != 0; i = (i + GAP) % NUMS) {
            //    System.out.println( "INSERT: " + i );
            t.insert(i);
            if (NUMS < SMALL)
                t.checkBalance();
        }

        for (int i = 1; i < NUMS; i += 2) {
            //   System.out.println( "REMOVE: " + i );
            t.remove(i);
            if (NUMS < SMALL)
                t.checkBalance();
        }
        if (NUMS < SMALL)
            t.printTree();
        if (t.findMin() != 2 || t.findMax() != NUMS - 2)
            System.out.println("FindMin or FindMax error!");

        for (int i = 2; i < NUMS; i += 2)
            if (!t.contains(i))
                System.out.println("Find error1!");

        for (int i = 1; i < NUMS; i += 2) {
            if (t.contains(i))
                System.out.println("Find error2!");
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void makeEmpty() {
        root = null;
    }

    public boolean contains(AnyType anyType) {
        return contains(anyType, root);
    }

    public AnyType findMin() {
        Assert.assertNotNull("树为空", root);

        return findMin(root).value;
    }

    public AnyType findMax() {
        Assert.assertNotNull("树为空", root);

        return findMax(root).value;
    }

    public void insert(AnyType anyType) {
        root = insert(anyType, root);
    }

    public void remove(AnyType anyType) {
        root = remove(anyType, root);
    }

    public void printTree() {
        if (isEmpty())
            System.out.println("Empty tree");
        else
            printTree(root);
    }

    private boolean contains(AnyType anyType, AvlNode<AnyType> avlNode) {
        if (avlNode == null) {
            return false;
        }

        int compareTo = anyType.compareTo(avlNode.value);
        if (compareTo == 0) {
            return true;
        } else if (compareTo < 0) {
            return contains(anyType, avlNode.left);
        } else {
            return contains(anyType, avlNode.right);
        }
    }

    private AvlNode<AnyType> findMin(AvlNode<AnyType> avlNode) {
        if (avlNode.left == null) {
            return avlNode;
        }
        return findMin(avlNode.left);
    }

    private AvlNode<AnyType> findMax(AvlNode<AnyType> avlNode) {
        if (avlNode.right == null) {
            return avlNode;
        }
        return findMax(avlNode.right);
    }

    private AvlNode<AnyType> insert(AnyType anyType, AvlNode<AnyType> avlNode) {
        if (null == avlNode) {
            return new AvlNode<>(anyType, null, null);
        }

        int compareTo = anyType.compareTo(avlNode.value);
        if (compareTo < 0) {
            avlNode.left = insert(anyType, avlNode.left);
        } else if (compareTo > 0) {
            avlNode.right = insert(anyType, avlNode.right);
        } else {
            //nothing to do
        }
        return balance(avlNode);
    }

    private AvlNode<AnyType> remove(AnyType anyType, AvlNode<AnyType> avlNode) {
        if (null == avlNode) {
            return null;
        }
        int compareTo = anyType.compareTo(avlNode.value);
        if (compareTo < 0) {
            avlNode.left = remove(anyType, avlNode.left);
        } else if (compareTo > 0) {
            avlNode.right = remove(anyType, avlNode.right);
        } else if (avlNode.left != null && avlNode.right != null) {
            avlNode.value = findMin(avlNode.right).value;
            avlNode.right = remove(avlNode.value, avlNode.right);
        } else {
            avlNode = avlNode.left != null ? avlNode.left : avlNode.right;
        }
        return balance(avlNode);
    }

    private int height(AvlNode<AnyType> t) {
        return t == null ? -1 : t.height;
    }

    private void printTree(AvlNode<AnyType> t) {
        if (t != null) {
            printTree(t.left);
            System.out.println(t.value);
            printTree(t.right);
        }
    }

    public void checkBalance() {
        checkBalance(root);
    }

    private int checkBalance(AvlNode<AnyType> t) {
        if (t == null)
            return -1;

        int hl = checkBalance(t.left);
        int hr = checkBalance(t.right);
        if (Math.abs(height(t.left) - height(t.right)) > 1 || height(t.left) != hl || height(t.right) != hr) {
            System.out.println("OOPS!!");
        }

        return height(t);
    }

    private AvlNode<AnyType> balance(AvlNode<AnyType> t) {
        if (null == t) {
            return null;
        }
        int hl = height(t.left);
        int hr = height(t.right);
        //左子树高度高
        if (hl - hr > ALLOWED_IMBALANCE) {
            if (height(t.left.left) > height(t.left.right)) {
                t = rotateWithLeftChild(t);
            } else {
                t = doubleWithLeftChild(t);
            }
            //右子树高度高
        } else if (hr - hl > ALLOWED_IMBALANCE) {
            if (height(t.right.left) > height(t.right.right)) {
                t = doubleWithRightChild(t);
            } else {
                t = rotateWithRightChild(t);
            }
        }
        t.height = Math.max(height(t.left), height(t.right)) + 1;
        return t;
    }

    //左旋
    private AvlNode<AnyType> rotateWithLeftChild(AvlNode<AnyType> k2) {
        AvlNode<AnyType> left = k2.left;
        AvlNode<AnyType> right = left.right;
        left.right = k2;
        k2.left = right;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        left.height = Math.max(height(left.left), k2.height) + 1;
        return left;
    }

    //右旋
    private AvlNode<AnyType> rotateWithRightChild(AvlNode<AnyType> k1) {
        AvlNode<AnyType> right = k1.right;
        AvlNode<AnyType> left = right.left;
        right.left = k1;
        k1.right = left;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
        right.height = Math.max(height(right.right), k1.height) + 1;
        return right;
    }

    //双旋转
    private AvlNode<AnyType> doubleWithLeftChild(AvlNode<AnyType> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    private AvlNode<AnyType> doubleWithRightChild(AvlNode<AnyType> k1) {
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }

    static class AvlNode<E> {
        private E value;
        private AvlNode<E> left;
        private AvlNode<E> right;
        private int height;

        public AvlNode(E value) {
            this.value = value;
            this.left = null;
            this.right = null;
            height = 0;
        }

        public AvlNode(E value, AvlNode<E> left, AvlNode<E> right) {
            this.value = value;
            this.left = left;
            this.right = right;
            height = 0;
        }
    }
}
