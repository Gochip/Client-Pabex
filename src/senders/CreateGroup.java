package senders;

/**
 *
 * @author Parisi Germán
 * @version 1.0
 */
public class CreateGroup {

    private String groupName, password;
    private int maxNum;
    private boolean forceToDelete;

    public CreateGroup(String groupName) {
        this.groupName = groupName;
        this.password = null;
        this.maxNum = -1;
        this.forceToDelete = false;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public boolean isForceToDelete() {
        return forceToDelete;
    }

    public void setForceToDelete(boolean forceToDelete) {
        this.forceToDelete = forceToDelete;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("'").append(groupName).append("' ");
        if (password != null) {
            sb.append("-p '").append(password).append("' ");
        }
        if (maxNum != -1) {
            sb.append("-n '").append(password).append("' ");
        }
        sb.append("-f '").append(forceToDelete).append("'");

        return sb.toString();
    }
}
