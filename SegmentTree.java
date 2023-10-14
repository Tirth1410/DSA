package Tree;

public class SegmentTree {

    public static void main(String[] args) {
        int [] arr = new int[] {3, 8, 6, 7, -2, -8, 4, 9};

        SegmentTree tree = new SegmentTree(arr);
        tree.Display();
    }
    private static class Node{
        int data;
        int st;
        int ed;
        Node left;
        Node right;

        public Node(int start, int end){
            this.st = start;
            this.ed = end;
        }
    }

    Node root;

    public SegmentTree(int [] arr){
        this.root = ConstructTree(arr, 0, arr.length-1);
    }

//    FOR MAKING THE TREE OF THE GIVEN ARRAY
//    TIME COMPLEXITY : O(N);
    private Node ConstructTree(int [] arr, int s, int e){
//      LEAF NODE
        if(s == e){
            Node leaf = new Node(s, e);
            leaf.data = arr[s];

            return leaf;
        }

        Node node = new Node(s, e);

        int mid = s + (e - s) / 2;

//        CREATES LEFT NODE
        Node left = ConstructTree(arr, s, mid);
//        CREATE RIGHT NODE
        Node right = ConstructTree(arr, mid + 1, e);

        node.data = left.data + right.data;
        node.left = left;
        node.right = right;

        return node;
    }


//    DISPLAY TREE
    public void Display(){
        Display(root);
    }

    private void Display(Node node){
        String str = "";

//        LEFT
        if(node.left != null){
            str = str + "Interval = [" + node.left.st + ", " + node.left.ed + "]    Data : " + node.left.data;
        } else {
            str = str + "no left child";
        }

//        CURRENT
        str = str + "Interval = [" + node.st + ", " + node.ed + "]    Data : " + node.data;

//        RIGHT
        if(node.right != null){
            str = str + "Interval = [" + node.right.st + ", " + node.right.ed + "]    Data : " + node.right.data;
        } else {
            str = str + "no right child";
        }


        System.out.println(str);

        if(node.left != null){
            Display(node.left);
        }

        if(node.right != null){
            Display(node.right);
        }
    }


//    QUERY
    public int query(int s, int e){
        return this.query(this.root, s, e);
    }

    public int query(Node node, int qs, int qe){

//        LYING INSIDE THE QUERY

        if(node.st >= qs && node.ed <= qe){
            return node.data;
        }

//        LYING OUTSIDE THE QUERY INTERVAL
        else if (node.st > qe || node.ed < qs){
            return 0;
        }

//        OVERLAPPING

        return this.query(node.left, qs,  qe) + this.query(node.right, qs, qe);
    }

    public void Update(int idx, int value){
        root.data = this.Update(idx, value, root);
    }

    private int Update(int idx, int val, Node node){
        if(idx >= node.st && idx <= node.ed){
            if(idx == node.st && idx == node.ed){
                node.data = val;
                return node.data;
            } else {
                int left = Update(idx, val, node.left);
                int right = Update(idx, val, node.right);

                node.data = left + right;
                return node.data;
            }
        }
        return node.data;
    }
}
