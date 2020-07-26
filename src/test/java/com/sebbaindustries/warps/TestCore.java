package com.sebbaindustries.warps;

import com.sebbaindustries.warps.settings.ESettings;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <b>MIT License</b><br>
 * <br>
 * <b>Copyright (c) 2020 SebbaIndustries</b><br>
 * <br>
 * Permission is hereby granted, free of charge, to any person obtaining a copy <br>
 * of this software and associated documentation files (the "Software"), to deal <br>
 * in the Software without restriction, including without limitation the rights <br>
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell <br>
 * copies of the Software, and to permit persons to whom the Software is <br>
 * furnished to do so, subject to the following conditions: <br>
 * <br>
 * The above copyright notice and this permission notice shall be included in all <br>
 * copies or substantial portions of the Software. <br>
 * <br>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR <br>
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, <br>
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE <br>
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER <br>
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, <br>
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE <br>
 * SOFTWARE. <br>
 * <br>
 * @author <b>sebbaindustries</b>
 * @version <b>1.0</b>
 */
public class TestCore {

    public Logger logger = Logger.getLogger("testCore");

    @Test
    public void testSumLamb() {
       List<Integer> visits = new ArrayList<>();
       visits.add(5);
       visits.add(5);
       visits.add(5);
       visits.add(10);

       int result = visits.stream().reduce(0, Integer::sum);

        Assert.assertEquals(25, result);
    }

    @Test
    public void resetVisits() {
        List<Integer> visits = new ArrayList<>();
        visits.add(5);
        visits.add(5);
        visits.add(5);
        visits.add(10);
        visits.clear();
        /*for (int i = 0; i < 7; i++) {
            visits.add(0);
        }
         */

        visits = Collections.nCopies(3, 0);
        visits.forEach(visit -> logger.info(String.valueOf(visit)));
    }

    @Test
    public void jsonTest() {
        List<Integer> visits = new ArrayList<>();
        HashMap<String, Integer> playerVisits = new HashMap<>();
        visits.add(11);
        visits.add(12);
        visits.add(13);
        visits.add(14);
        playerVisits.put("Nzd_1", 2);
        playerVisits.put("Nzd_3", 3);
        playerVisits.put("Nzd_2", 0);
        playerVisits.put("Frosty", 1);

        JSONObject json = new JSONObject();
        json.put("visits", visits);
        json.put("players", playerVisits);

        logger.info(json.toJSONString());

        playerVisits = (HashMap<String, Integer>) json.get("players");
        playerVisits.forEach((k, v) -> logger.info(k + ":" + v));
    }


    @Test
    public void shiftRight() {
        List<Integer> visits = new ArrayList<>();
        visits.add(11);
        visits.add(12);
        visits.add(13);
        visits.add(14);

        //make a loop to run through the array list
        for(int i = visits.size()-1; i > 0; i--) {
            //set the last element to the value of the 2nd to last element
            visits.set(i, visits.get(i - 1));
        }
        //set the first element to be the last element
        visits.set(0, 0);

        visits.forEach(visit -> logger.info(visit.toString()));
    }

}
