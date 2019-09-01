package com.javalanguage.chapter4;


/**
 * Created by cjf 2019-08-24 16:01
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {

    //root
    private BinaryNode<AnyType> root;

    public BinarySearchTree() {
        root = null;
    }

    // Test program
    public static void main(String[] args) {
        BinarySearchTree<Integer> t = new BinarySearchTree<>();
        final int NUMS = 4000;
        final int GAP = 37;

        System.out.println("Checking... (no more output means success)");

        for (int i = GAP; i != 0; i = (i + GAP) % NUMS)
            t.insert(i);

        for (int i = 1; i < NUMS; i += 2)
            t.remove(i);

        if (NUMS < 40)
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

    public void makeEmpty() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(AnyType anyType) {
        return contains(anyType, root);
    }

    public void insert(AnyType anyType) {
        if (isEmpty()) {
            root = new BinaryNode<>(anyType, null, null);
        } else {
            insert(anyType, root);
        }
    }

    public void remove(AnyType anyType) {
        if (isEmpty()) {
            throw new RuntimeException("该树是空树");
        }
        remove(anyType, root);

    }

    public AnyType findMax() {
        if (isEmpty()) {
            throw new RuntimeException("该树是空树");
        }

        return findMax(root).value;
    }

    public AnyType findMin() {
        if (isEmpty()) {
            throw new RuntimeException("该树是空树");
        }

        return findMin(root).value;
    }

    public void printTree() {
        if (isEmpty()) {
            System.out.println("Empty tree");
        } else {
            printTree(root);
        }
    }

    private boolean contains(AnyType anyType, BinaryNode<AnyType> binaryNode) {
        if (null == binaryNode) {
            return false;
        }

        int compareTo = anyType.compareTo(binaryNode.value);
        if (compareTo < 0) {
            //递归左子树
            return contains(anyType, binaryNode.left);
        } else if (compareTo > 0) {
            //递归右子树
            return contains(anyType, binaryNode.right);
        } else {
            return true;
        }
    }

    private void insert(AnyType anyType, BinaryNode<AnyType> binaryNode) {
        int compareTo = anyType.compareTo(binaryNode.value);
        if (compareTo < 0) {
            if (binaryNode.left != null) {
                //入左子树
                insert(anyType, binaryNode.left);
            } else {
                binaryNode.left = new BinaryNode<>(anyType, null, null);
            }
        } else if (compareTo > 0) {
            if (binaryNode.right != null) {
                //入右子树
                insert(anyType, binaryNode.right);
            } else {
                binaryNode.right = new BinaryNode<>(anyType, null, null);
            }
        } else {
            //do nothing
        }
    }

    private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
        if (t == null) {
            return t;
        }
        int compareResult = x.compareTo(t.value);

        if (compareResult < 0) {
            t.left = remove(x, t.left);
        } else if (compareResult > 0) {
            t.right = remove(x, t.right);
        } else if (t.left != null && t.right != null) {
            t.value = findMin(t.right).value;
            t.right = remove(t.value, t.right);
        } else {
            t = (t.left != null) ? t.left : t.right;
        }
        return t;
    }

    private BinaryNode<AnyType> findMax(BinaryNode<AnyType> binaryNode) {
        //递归查询右子树
        if (binaryNode.right != null) {
            return findMax(binaryNode.right);
        }
        return binaryNode;
    }

    private BinaryNode<AnyType> findMin(BinaryNode<AnyType> binaryNode) {
        //递归查询左子树
        if (binaryNode.left != null) {
            return findMin(binaryNode.left);
        }
        return binaryNode;
    }

    private void printTree(BinaryNode<AnyType> t) {
        if (t != null) {
            printTree(t.left);
            System.out.println(t.value);
            printTree(t.right);
        }
    }

    private static class BinaryNode<E> {
        private E value;
        private BinaryNode<E> left;
        private BinaryNode<E> right;

        public BinaryNode(E value, BinaryNode<E> left, BinaryNode<E> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }
}
