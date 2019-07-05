public class Palindrome {
    static Palindrome palindrome = new Palindrome();

    /** convert a string to a list, which store char in every node's item */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> listString = new LinkedListDeque<Character>();

        for (int i = 0; i < word.length(); i += 1) {
            char ithLetter = word.charAt(i);
            listString.addLast(ithLetter);
        }
        return listString;
    }

    /** return true if the string is palindrome, false otherwise
     *  for instance: "" "a" "racecar" is palindrome, "horse" is not
     * */

    public boolean isPalindrome(String word) {
        Deque<Character> listString = palindrome.wordToDeque(word);
        return isPalindromeHelper(listString);
        /* fine, the Autograder told me that I need to implement this method recursively
           and without the help of the get() method

        for (int i = 0; i < word.length() / 2; i += 1) {
            char front = listString.get(i);
            char behind = listString.get(word.length() - 1 - i);
            // char is primitive type, so it can be compared by "=="
            if (front != behind){
                return false;
            }
        }
        return true;
         */
    }

    private boolean isPalindromeHelper(Deque word) {
        if (word.size() == 0 || word.size() == 1) {
            return true;
        }
        if (word.removeFirst() == word.removeLast()) {
            return isPalindromeHelper(word);
        } else {
            return false;
        }
    }


    /** overload the method of isPalindrome */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> listString = palindrome.wordToDeque(word);
        return isPalindromeHelper(listString, cc);
    }

    private boolean isPalindromeHelper(Deque word, CharacterComparator cc) {
        if (word.size() == 0 || word.size() == 1) {
            return true;
        }
        char first = (char) word.removeFirst();
        char last = (char) word.removeLast();
        if (cc.equalChars(first, last)) {
            return isPalindromeHelper(word, cc);
        } else {
            return false;
        }

    }
}
