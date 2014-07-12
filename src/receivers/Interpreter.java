package receivers;

import executors.response.InformationResponse;

/**
 *
 * @author Parisi Germán
 * @version 1.0
 */
public class Interpreter {

    private final InformationResponse informationResponse;

    public Interpreter(InformationResponse informationResponse) {
        this.informationResponse = informationResponse;
    }

    public boolean isJoin() {
        return informationResponse.getSource().equals("join");
    }

    public boolean isDisconect() {
        return informationResponse.getSource().equals("disconnect");
    }

    public boolean isLeaveGroup() {
        return informationResponse.getSource().equals("leave_group");
    }

    public boolean isDeletedGroup() {
        return Boolean.parseBoolean(informationResponse.getValue("deleted_group"));
    }
    
    public boolean isAddAttribute(){
        return informationResponse.getSource().equals("add_attribute");
    }
}
