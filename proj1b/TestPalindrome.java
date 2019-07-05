import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        String d2 = "racecar";
        String d3 = "a";
        String d4 = "horse";
        String d5 = "";

        assertTrue(palindrome.isPalindrome(d2));
        assertTrue(palindrome.isPalindrome(d3));
        assertFalse(palindrome.isPalindrome(d4));
        assertTrue(palindrome.isPalindrome(d5));
    }

    static CharacterComparator offByOne = new OffByOne();
    @Test
    public void testOverloadIsPalindrome() {
        assertTrue(palindrome.isPalindrome("flake", offByOne));
        assertTrue(palindrome.isPalindrome("a", offByOne));
        assertFalse(palindrome.isPalindrome("racecar", offByOne));
        assertFalse(palindrome.isPalindrome("Ab", offByOne));
    }
}
