package receivers;

import executors.response.CommandResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Parisi Germán
 */
public class ShowGroupsInterpreter {

    private final CommandResponse commandResponse;

    public ShowGroupsInterpreter(CommandResponse commandResponse) {
        this.commandResponse = commandResponse;
    }

    public List<String> getGroups() {
        ArrayList<String> groups = new ArrayList<>();
        String groupsId = this.commandResponse.getValue("id_groups");
        if (groupsId != null) {
            String[] eachGroup = groupsId.split(",");
            for (int i = 0; i < eachGroup.length; i++) {
                groups.add(eachGroup[i]);
            }
        }
        return groups;
    }

    public String getGroupName(String groupId) {
        return this.commandResponse.getValue(groupId + "_group_name");
    }

    public String getAdminId(String groupId) {
        return this.commandResponse.getValue(groupId + "_id_admin");
    }

    public String getMaxNum(String groupId) {
        return this.commandResponse.getValue(groupId + "_max_num");
    }

    public String isPrivate(String groupId) {
        return this.commandResponse.getValue(groupId + "_is_private");
    }

    public String getNumberOfConnectedClients(String groupId) {
        return this.commandResponse.getValue(groupId + "_nocc");
    }
}
