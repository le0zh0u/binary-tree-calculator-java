public class Calculator {

  public static void main(String[] args){
    Calculator ca = new Calculator();
    ca.calculator();
  }

  public void calculator() {
    double a = new TreeNode("1*2*(3+4+5+6)").calculate();
    System.out.println(a);
  }

  class TreeNode {
    double data;
    char operation;
    TreeNode left;
    TreeNode right;

    /**
     * recursively construct the Tree
     */
    public TreeNode(String expression) {
      char[] exc = toCharArrayTrimOutParenthes(expression);
      if (exc == null) {
        return;
      }
      exc = syntaxCheck(exc);
      int length = exc.length;

      int index = 0;

      if(hasOperation(exc)){
        int parenthes = 0;

        for (int i = length - 1; i>=0; i--) {
          if (exc[i] == '(') {
            parenthes--;
          }else if (exc[i] == ')') {
            parenthes++;
          }
          if (parenthes > 0) {
            continue;
          }

          if (exc[i] == '*' || exc[i] == '/') {
            index =i;
          }
          else if (exc[i] == '+' || exc[i] == '-') {
            index = i;
            break;
          }
        }

        if (isOperation(exc[index])) {
          operation = exc[index];
        }
        StringBuilder sbleft = new StringBuilder();
        StringBuilder sbright = new StringBuilder();

        for (int i=0; i < index; i++) {
          sbleft.append(exc[i]);
        }
        for (int i=index + 1; i<length; i++) {
          sbright.append(exc[i]);
        }

        left = new TreeNode(sbleft.toString());
        right = new TreeNode(sbright.toString());
      }else {
        StringBuilder value = new StringBuilder();
        value.append(exc);
        data = Double.parseDouble(value.toString());
      }

    }

    public boolean hasOperation(char[] cArray) {
      for (int i = 0; i<cArray.length; i++) {
        if (isOperation(cArray[i])) {
          return true;
        }
      }
      return false;
    }

/**
 * check the expression's syntax if thre is two or ore continuous operation,
 * print out syntax error add '0' before the '-' if it's a negative
 */
    public char[] syntaxCheck(char[] cArray) {
      boolean flag = false;
      if (isOperation(cArray[0])) {
        char[] checkedArray = new char[cArray.length + 1];
        checkedArray[0] = '0';
        for (int i=0; i < cArray.length; i++) {
          checkedArray[i + 1] = cArray[i];
        }
        cArray = checkedArray;
      }
      for (int i = 0; i < cArray.length; i++) {
        if(isOperation(cArray[i])){
          if(flag == true){
            System.out.println("syntaxError");
          }
          flag = true;
        }else {
          flag = false;
        }
      }
       return cArray;
    }

    public boolean isOperation(char c){
      return (c == '+' || c == '-' || c == '*' || c == '/');
    }

    public char[] toCharArrayTrimOutParenthes(String src) {
      if (src.length() == 0 ) {
        return null;
      }
      String result = src;
      while(result.charAt(0) == '(' && result.charAt(result.length()-1) == ')'){
        int parenthes = 0;
        for (int i=0; i < result.length() - 1; i++) {
          if (result.charAt(i) == '(') {
            parenthes++;
          }else if (result.charAt(i) == ')') {
            parenthes--;
          }
          if (parenthes == 0) {
            return result.toCharArray();
          }
        }
        result = result.substring(1, result.length() - 1 );
      }

      return result.toCharArray();
    }

    //recursively calculate
    public double calculate() {
      double result = 0;
      if (left == null && right == null) {
        result = data;
      }else {
        double leftResult = left.calculate();
        double rightResult = right.calculate();
        switch(operation) {
          case '+':
            result = leftResult + rightResult;
            break;
          case '-':
            result = leftResult - rightResult;
            break;
          case '*':
            result = leftResult * rightResult;
            break;
          case '/':
            result = leftResult / rightResult;
            break;
          default:
            break;
        }
      }
      return result;
    }
  }
}
