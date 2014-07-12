package receivers;

import executors.EAW;
import executors.response.CommandResponse;

/**
 * Clase que valida un Command Response
 *
 * @author Parisi Germán
 * @version 1.0
 */
public class ValidatorReceiver {

    private final CommandResponse commandResponse;
    private StringBuilder buffer;

    /**
     *
     * @param commandResponse es el commandResponse que se va a validar
     */
    public ValidatorReceiver(CommandResponse commandResponse) {
        this.commandResponse = commandResponse;
    }

    /**
     *
     * @return si el command response es válido
     */
    public boolean isValid() {
        boolean valid = true;

        if (commandResponse.getError() != null) {
            buffer = new StringBuilder();
            valid = false;
            for (int errorNumber = 1; errorNumber <= EAW.ERRORS_COUNT; errorNumber++) {
                if (commandResponse.getError().equals(errorNumber)) {
                    buffer.append(commandResponse.getErrorInfo());
                    break;
                }
            }
        }
        return valid;
    }

    /**
     * Este método debe llamarse luego de isValid
     *
     * @return información asociada al error. Si retorna null entonces no hubo
     * error.
     */
    public String getError() {
        if (buffer != null) {
            return buffer.toString();
        } else {
            return null;
        }
    }
}
