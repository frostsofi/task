package Detector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;


public class ChangeDetectorTest extends Assert
{
    private HashMap<URL, String> yesTDPages;
    private HashMap<URL, String> TDPages;

    @Before
    public void setUpMaps() throws MalformedURLException
    {
        yesTDPages = new HashMap<>();
        yesTDPages.put(new URL("https://table.nsu.ru/group/17201"), "<td><b>1 пара</b></td>");
        yesTDPages.put(new URL("https://table.nsu.ru/group/17202"), "<td><b>2 пара</b></td>");
        yesTDPages.put(new URL("https://table.nsu.ru/group/17203"), "<td><b>3 пара</b></td>");
        yesTDPages.put(new URL("https://table.nsu.ru/group/17204"), "<td><b>4 пара</b></td>");
        yesTDPages.put(new URL("https://table.nsu.ru/group/17205"), "<td><b>5 пара</b></td>");

        TDPages = new HashMap<>();
        TDPages.put(new URL("https://table.nsu.ru/group/17201"), "<td><b>1 пара</b></td>");
        TDPages.put(new URL("https://table.nsu.ru/group/17203"), "<td><b>1222 пара</b></td>");
        TDPages.put(new URL("https://table.nsu.ru/group/17205"), "<td><b>1221 пара</b></td>");
        TDPages.put(new URL("https://table.nsu.ru/group/17206"), "<td><b>6 пара</b></td>");
        TDPages.put(new URL("https://table.nsu.ru/group/17207"), "<td><b>7 пара</b></td>");
    }

    @Test
    public void findAddedUrls() throws MalformedURLException {
        HashMap<URL, String> expectedPages = new HashMap<>();
        expectedPages.put(new URL("https://table.nsu.ru/group/17206"), "<td><b>6 пара</b></td>");
        expectedPages.put(new URL("https://table.nsu.ru/group/17207"), "<td><b>7 пара</b></td>");

        HashMap<URL, String> actualPages = ChangeDetector.findAddedUrls(yesTDPages, TDPages);
        assertEquals(expectedPages, actualPages);
    }

    @Test
    public void findDeletedUrls() throws MalformedURLException
    {
        HashMap<URL, String> expectedPages = new HashMap<>();
        expectedPages.put(new URL("https://table.nsu.ru/group/17202"), "<td><b>2 пара</b></td>");
        expectedPages.put(new URL("https://table.nsu.ru/group/17204"), "<td><b>4 пара</b></td>");

        HashMap<URL, String> actualPages = ChangeDetector.findDeletedUrls(yesTDPages, TDPages);
        assertEquals(expectedPages, actualPages);
    }

    @Test
    public void findChangedUrls() throws MalformedURLException {
        HashMap<URL, String> expectedPages = new HashMap<>();
        expectedPages.put(new URL("https://table.nsu.ru/group/17203"), "<td><b>1222 пара</b></td>");
        expectedPages.put(new URL("https://table.nsu.ru/group/17205"), "<td><b>1221 пара</b></td>");

        HashMap<URL, String> actualPages = ChangeDetector.findChangedUrls(yesTDPages, TDPages);
        assertEquals(expectedPages, actualPages);
    }
}