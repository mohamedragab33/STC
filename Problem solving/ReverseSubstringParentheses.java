import java.util.Stack;

public class ReverseSubstringParentheses {
    public static String reverseParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '(') {
                stack.push(i);
            } else if (chars[i] == ')') {
                int start = stack.pop();
                int end = i;
                while (start < end) {
                    char temp = chars[start];
                    chars[start] = chars[end];
                    chars[end] = temp;
                    start++;
                    end--;
                }
            }
        }

        // Remove parentheses from the string
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (c != '(' && c != ')') {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(reverseParentheses("abd(jnb)asdf"));  // abd(bnj)asdf
        System.out.println(reverseParentheses("abdjnbasdf"));    // abdjnbasdf
        System.out.println(reverseParentheses("dd(df)a(ghhh)")); // dd(fd)a(hhhg)
    }
}

