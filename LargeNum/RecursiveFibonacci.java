//Name: Hussein Abdallah
//Student #: 251164702
public class RecursiveFibonacci {
    public static int fib(int n) { //base
        if (n==1) return 1;
        if (n==0) return 0;

        return fib(n-1)+fib(n-2);
    }

    public static LargeNum[] fib2(int n) { //O(n) time complexity
        //in these matrices, the first value is the number at value n, and the second is at n-1
        if (n==1) return new LargeNum[] {new LargeNum(1), new LargeNum(0)};

        LargeNum[] prev = fib2(n-1); //get n-1 and n-2 values
        LargeNum prevNum = new LargeNum(prev[0].getNum()); //this will be the second number in the matrix
        prev[0].addToThis(prev[1]); //adding together both elements of last matrix will give us the first element of the new one
        return new LargeNum[] {prev[0],prevNum};
    }
    public static void main(String[] args) {
        for (int i = 0; i <= 10; i++) { //Q8a
            System.out.print("i*5 = "+Integer.toString(i*5)+": ");
            System.out.println(fibLikeSeq(i*5));
            System.out.println(fibLikeSeqB(i*20)[0].getNum());
        }
    }
}
