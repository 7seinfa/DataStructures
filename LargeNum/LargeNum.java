//Name: Hussein Abdallah
//Student #: 251164702
public class LargeNum {
    private String num; //stores my number

    ////constructors
    public LargeNum (int i) {
        num = Integer.toString(i);
    }
    public LargeNum (String i) {
        num = i;
    }

    ////getters and setters
    public String getNum () {
        return this.num;
    }
    public void setNum (int i) {
        this.num = Integer.toString(i);
    }
    public void setNum (String i) {
        this.num = i;
    }

    //compare this num to other.num
    public int compareTo (LargeNum other) {
        if(this.num.length() < other.getNum().length()) return -1;
        if(this.num.length() > other.getNum().length()) return 1;

        for (int i = this.num.length()-1; i>=0; i++) {
            if (this.num.charAt(i) > other.getNum().charAt(i)) return 1;
            if (this.num.charAt(i) < other.getNum().charAt(i)) return -1;
        }

        return 0;
    }

    //add other.num to this num
    public void addToThis (LargeNum other) {
        int diffInLength = this.num.length()-other.getNum().length(); //get difference in string length
        String otherNum = "";

        //add zeros to beginning of whichever string is shorter
        if (diffInLength > 0) {
            for (int i = 0; i < diffInLength; i++) {
                otherNum+="0";
            }
            otherNum+=other.getNum();
        } else if (diffInLength < 0) {
            String thisNum = "";
            for (int i = 0; i < diffInLength; i++) {
                thisNum+="0";
            }
            thisNum+=this.num;
            this.num = thisNum;
            otherNum = other.getNum();
        } else {
            otherNum = other.getNum();
        }


        char[] ans = new char[this.num.length()+1];
        ans[0] = '0';
        int carryOn = 0;
        for (int i = this.num.length()-1; i >=0; i--) { //from right to left, add this num to other num
            int curAns = Character.getNumericValue(this.num.charAt(i)) + Character.getNumericValue(otherNum.charAt(i)) + carryOn;
            ans[i+1] = Integer.toString(curAns % 10).charAt(0); //get last digit of this num
            carryOn = curAns / 10; //carry the digits too large for this position
        }
        if (carryOn > 0) ans[0] = Integer.toString(carryOn).charAt(0); //add last carry if needed
        if (ans[0] == '0') this.num = new String(ans).substring(1); //if first number is zero, remove it set
        else this.num = new String(ans).toString(); //otherwise just set
    }
}
