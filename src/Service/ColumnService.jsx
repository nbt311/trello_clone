import axios from "axios";
import workspace from "../Pages/WorkspacePage/Workspace";
import workspaceSidebar from "../Components/SideBar/WorkspaceSidebar";

const API_URL = "http://localhost:8080/api/columns/";

class ColumnService {
    createNewColumn(email, workspaceId, title, boardId) {
        return axios.post(API_URL + "create", {
            email: email,
            workspaceId: workspaceId,
            title: title,
            boardId: boardId
        })
    }

    getListColumn() {
        return axios.get(API_URL + "all")
    }

}
export default new ColumnService();