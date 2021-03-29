package testing3.PageObject;

import org.apache.commons.math3.util.Pair;
import testing3.AbstractPageObjects.PageComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

//import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogElement implements PageComponent {
    @FindBy(className = "panel-body-list")
    WebElement logList;

    private String[] rawLogs() {
        return logList.getText().split("\n");
    }

    public List<String> logs() {
        return Arrays.asList(rawLogs());
    }

    public int numberOfLogs() {
        return rawLogs().length;
    }

    public List<Pair<String, String>> elementValueLogs() {
        String[] logs = rawLogs();
        List<Pair<String, String>> res = new ArrayList<>(logs.length);
        for(String log: logs) {
            String[] parsed = log.split("\\s");
            res.add(new Pair<String, String>(
                    parsed[1].substring(0, parsed[1].length() - 1),
                    parsed[parsed.length - 1]
            ));
        }
        return res;
    }

    @Override
    public void initElements(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}