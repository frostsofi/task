package Detector;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ChangeDetector
{
    private ChangeDetector(){};

    public static HashMap<URL, String> findAddedUrls(HashMap<URL, String> yesTDPages,
                                                     HashMap<URL, String> TDPages)
    {
        return  (HashMap<URL, String>)TDPages.keySet().stream()                 //find pages, which user has today, but not yesterday
                .filter(x -> !yesTDPages.containsKey(x))
                .collect(Collectors.toMap(p -> p, TDPages::get));
    }

    public static HashMap<URL, String> findDeletedUrls(HashMap<URL, String> yesTDPages,
                                                       HashMap<URL, String> TDPages)
    {
        return  (HashMap<URL, String>)yesTDPages.keySet().stream().filter(x -> !TDPages.containsKey(x))   //find pages, which user has yesterday, but not today
                .collect(Collectors.toMap(p -> p, yesTDPages::get));
    }

    public static HashMap<URL, String> findChangedUrls(HashMap<URL, String> yesTDPages,
                                                       HashMap<URL, String> TDPages)
    {
        return  (HashMap<URL, String>)TDPages.entrySet().stream().filter(x ->
        {
            if (yesTDPages.containsKey(x.getKey()))
                return  !yesTDPages.get(x.getKey()).equals(x.getValue());
            return false;
        }
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}