package executors.response;

import executors.Response;
import java.util.HashMap;

/**
 *
 * @author Parisi Germán &  Bertola Federico
 * @version 1.1
 */
public class CommandResponse extends Response<String> {

    public CommandResponse() {
    }

    public String getError() {
        return super.getValue("error");
    }

    public String getErrorInfo() {
        return super.getValue("error_info");
    }

    public String getWarning() {
        return super.getValue("warning");
    }

    public String getWarningInfo() {
        return super.getValue("warning_info");
    }

    public String getOption(String option) {
        return super.getValue(option);
    }

    public void addError(int number, String info) {
        super.addValue("error", String.valueOf(number));
        super.addValue("error_info", info);
    }

    public void appendWarning(int number, String info) {
        String previousError = super.getValue("warning");
        if (previousError == null) {
            super.addValue("warning", String.valueOf(number));
            super.addValue("warning_info", info);
        } else {
            super.addValue("warning", previousError + ", " + number);
            String previousErrorInfo = super.getValue("warning_info");
            super.addValue("warning_info", previousErrorInfo + ", " + info);
        }
    }

    public void addOption(String key, String value) {
        super.addValue(key, value);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        CommandResponse resp = new CommandResponse();
        resp.mapa = (HashMap<String, String>) mapa.clone();
        return resp;
    }
}
